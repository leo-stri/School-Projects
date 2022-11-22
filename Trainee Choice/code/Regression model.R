# Prepare environment
if (!require(summarytools)) {install.packages("summarytools"); library(summarytools)} 
library(caret)

# Understand the data
trainee.df <- read.csv("data//Trainee choice.csv")
str(trainee.df)
summary(trainee.df)
dfSummary(trainee.df)
view(dfSummary(trainee.df))

#process NA values
trainee.df[trainee.df$education_level == "", 7] <- "Graduate"
trainee.df[trainee.df$gender == "", 4] <- "Male"
trainee.df[trainee.df$enrolled_university == "", 6] <- "no_enrollment"
trainee.df[trainee.df$major_discipline == "", 8] <- "STEM"
trainee.df[trainee.df$company_size == "", 10] <- "Unknown"
trainee.df[trainee.df$company_type == "", 11] <- "Pvt Ltd"
trainee.df[trainee.df$last_new_job == "", 12] <- "1"
trainee.df[trainee.df$experience == "", 9] <- "5"

# Remove ID and city columns
trainee.df <- trainee.df[, -c(1, 2)]
view(dfSummary(trainee.df))


# Data type conversion
trainee.df$education_level <- as.factor(trainee.df$education_level)
trainee.df$gender <- as.factor(trainee.df$gender)
trainee.df$relevent_experience <- as.factor(trainee.df$relevent_experience)
trainee.df$enrolled_university <- as.factor(trainee.df$enrolled_university)
trainee.df$major_discipline <- as.factor(trainee.df$major_discipline)
trainee.df$company_size <- as.factor(trainee.df$company_size)
trainee.df$company_type <- as.factor(trainee.df$company_type)
trainee.df$last_new_job <- as.factor(trainee.df$last_new_job)

trainee.df$experience[trainee.df$experience == ">20"] <- "20"
trainee.df$experience[trainee.df$experience == "<1"] <- "1"
trainee.df$experience[as.integer(trainee.df$experience) > 1 & as.integer(trainee.df$experience) <= 10] <- "1~10"
trainee.df$experience[as.integer(trainee.df$experience) > 10 & as.integer(trainee.df$experience) < 20] <- "10~20"
trainee.df$experience[trainee.df$experience == "20"] <- ">20"
trainee.df$experience[trainee.df$experience == "1"] <- "<1"
trainee.df$experience<- as.factor(trainee.df$experience)

view(dfSummary(trainee.df))

# Partition data
set.seed(111)
train.index <- sample(c(1:nrow(trainee.df)), nrow(trainee.df) * 0.6)
train.df <- trainee.df[train.index,]
valid.df <- trainee.df[-train.index,]

# Train logistic regression
logit.reg <-
    glm(target ~ ., data = train.df, family = "binomial")
options(scipen = 999)
summary(logit.reg)


# Calculate odds
coef(logit.reg)
result.with.odds <- data.frame(summary(logit.reg)$coefficients,
                          Odds = exp(coef(logit.reg))) 
round(result.with.odds, 3)


# Predict
logit.reg.pred <-
    predict(logit.reg, 
            valid.df[, -12],  
            type = "response") 

# first 20 actual and predicted records
data.frame(actual = valid.df$target[1:20], 
           predicted = logit.reg.pred[1:20])


# Evaluation
ifelse(logit.reg.pred > 0.5, 1, 0)
confusionMatrix(as.factor(ifelse(logit.reg.pred > 0.5, 1, 0)), 
                as.factor(valid.df$target), 
                mode = "prec_recall",
                positive = "1")

#Recall is low. The model mistakenly categorize many 1 into 0.

table(valid.df$target)

library(ggplot2)
ggplot(valid.df, aes(factor(Personal.Loan), fill = Personal.Loan))+
    geom_bar(aes(y=(..count..)/sum(..count..))) +
    ylab("Ratio") 

# Cross-validating LR
trainee.df$target <- as.factor(trainee.df$target) 
summary(trainee.df)

# Split data using caret package
train.index.cv <- createDataPartition(trainee.df$target, p=0.9, list=FALSE)
train.df.cv <- trainee.df[ train.index.cv, ] # for cross-validation
test.df.cv <- trainee.df[ -train.index.cv, ] # for testing!

# Define training control
train.ctrl.cv <- trainControl(method = "cv", number = 6)

lr.model <- train(
    target ~ .,
    data = train.df.cv,
    trControl = train.ctrl.cv,
    method = "glm",
    family = binomial()
)

summary(lr.model)

#Performance on testing set
(pred <- predict(lr.model, newdata=test.df.cv[, -12]))
confusionMatrix(data=pred, 
                test.df.cv$target, 
                mode = "prec_recall",
                positive = "1")

#Recall is too low. The model mistakenly categorize many 1 into 0.

#Choose a different cutoff

ifelse(logit.reg.pred > 0.2, 1, 0)
confusionMatrix(as.factor(ifelse(logit.reg.pred > 0.2, 1, 0)), 
                as.factor(valid.df$target), 
                mode = "prec_recall",
                positive = "1")
#Precision dropped from 0.597 to 0.447, but recall boosted significantly from
#0.263 to 0.810. From a business perspective, we want to include as many prospective
#trainees as possible, so it's better to adjust the cut-off to 0.2 so as to improve
#recall enormously at the expense of some precision.

#Try again without pruning the experience column
trainee.df <- read.csv("data//Trainee choice.csv")
trainee.df[trainee.df$education_level == "", 7] <- "Graduate"
trainee.df[trainee.df$gender == "", 4] <- "Male"
trainee.df[trainee.df$enrolled_university == "", 6] <- "no_enrollment"
trainee.df[trainee.df$major_discipline == "", 8] <- "STEM"
trainee.df[trainee.df$company_size == "", 10] <- "Unknown"
trainee.df[trainee.df$company_type == "", 11] <- "Pvt Ltd"
trainee.df[trainee.df$last_new_job == "", 12] <- "1"
trainee.df[trainee.df$experience == "", 9] <- "5"

trainee.df <- trainee.df[, -c(1, 2)]

trainee.df$education_level <- as.factor(trainee.df$education_level)
trainee.df$gender <- as.factor(trainee.df$gender)
trainee.df$relevent_experience <- as.factor(trainee.df$relevent_experience)
trainee.df$enrolled_university <- as.factor(trainee.df$enrolled_university)
trainee.df$major_discipline <- as.factor(trainee.df$major_discipline)
trainee.df$company_size <- as.factor(trainee.df$company_size)
trainee.df$company_type <- as.factor(trainee.df$company_type)
trainee.df$last_new_job <- as.factor(trainee.df$last_new_job)
trainee.df$experience<- as.factor(trainee.df$experience)

view(dfSummary(trainee.df))

set.seed(111)
train.index <- sample(c(1:nrow(trainee.df)), nrow(trainee.df) * 0.6)
train.df <- trainee.df[train.index,]
valid.df <- trainee.df[-train.index,]

logit.reg <-
  glm(target ~ ., data = train.df, family = "binomial")
options(scipen = 999)

logit.reg.pred <-
  predict(logit.reg, 
          valid.df[, -12],  
          type = "response")

confusionMatrix(as.factor(ifelse(logit.reg.pred > 0.5, 1, 0)), 
                as.factor(valid.df$target), 
                mode = "prec_recall",
                positive = "1")
