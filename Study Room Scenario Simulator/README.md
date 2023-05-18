# Study Room Simulation Model

Study Room Simulation Model is used by schools to simulate demands of study rooms, and simulate results of various policy to open study rooms. The goal of this model is to reduce financial loss and maximize profit and to find the best policy possible.

## Start simulation

A sample simulation process is predefined. Run all the blocks to see how one would go about using this model to simulate the study room scenario.

## Forcast methods

4 forecast methods and a simple method have been predefined to be used for determining the expectation of demands of future periods in the model. Change the defined forecast methods in the configuration blocks or add new forecast methods to explore how different forecast methods would affect the result.

## Parameters

After running all the configuration block prior to the simulation process block, you can poke around the parameters block to see how the results change:

```python
# decision variables
buffer: int = 0                             # number to open more than demand to ensure that demand is always met
forecast_method: int = 1                    # which forecast method to adopt

# tuition variables
tuition_per_student: float = 160000               # how much school would gain from one student increase next 
α: float = 0.01                                 # decrease in next year student per demand not satified

# Room cost variables
daily_room_cost: float = 200                  # how much opening one study room for a day would cost

# Begin the simulation process for a year beginning from the second semester of 2022/2023
start_date = date(2023, 1, 30)
end_date = date(2024, 1, 29)
```

start_date and end_date don't support change.

