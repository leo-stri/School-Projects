package frame;

/* AllData stores all the global data that will be used through out various functions */

import java.util.ArrayList;
import java.util.List;

import Customer_interface.product;
import model.UserInfo;

public class AllData {

	// UserInfo list
	static List<UserInfo> userInfoList = new ArrayList<UserInfo>();
	
	
	static ArrayList<product> productlist=new ArrayList<product> (); //create product list
	private static String[] productNamearray = {"AirTag(1 pack)", "AirTag(4 pack)", "AirTag Herm Key Ring",
			     "iPad Air5(256GB)","iPad mini6(256GB)", "iPadPro (5th gen)(256GB)","iPad (9th-gen) (256GB)", 
			     "Apple Pencil (2nd generation)","Magic Keyboard for iPad Pro",
			      "iPhone 13 (512GB)", "iPhone 13 mini (256GB)", "iPhone 13 Pro (512GB)","iPhone 13 Pro Max (512GB)",
			      "Macbook Pro 14 inch (512GB)","Macbook Pro 16 inch (512GB)","Macbook Pro 13 inch (512GB)","Macbook Air (256GB)",
			      "AirPods Pro","AirPods (3rd generation)","AirPods (2rd generation)","AirPods Max"
			    };
	
	private static double[] productPricearray = {249, 849,100,   6099,5399,9799,3949,1028,2368, 9699,6999, 11399, 12299,
            16499,20499,10318,7999,      2049,1549,1129,4699};
    private static int[] productInventaryarray = {32,20,60,    10,15,20,30,100,25,  30,30,60,30,   10,10,15,20, 36,42,12,20};
    private static String[] productDescrition = new String[21];
    
    public static void createProductlist() {
		  //AirTags
		  //1 pack
		      productDescrition[0] = "Lose your knack for losing things.\r\n"
			  		+ "AirTag is an easy way to keep track of your stuff.\nAttach one to your keys, "
					+ "slip another one in your backpack. And just like that, they’re on your radar in the Find My app. "
					+ "AirTag has your back.\r\n";
		  //4 pack
			  productDescrition[1]= productDescrition[0];
	      //keyRing
			  productDescrition[2]="Apple AirTag Herm key ring in Swift calfskin.\r\nYou can easily find your keys with the "
			  		+ "\"Find My\" application on your iPhone or iPad, whether it's at hand or on the other side of the world.\r\n"
			  		+ "Exclusive AirTag Herm etched with the iconic Clou de Selle signature.\r\nLeather accessory made in France\r\n"
			  		+ "Height: 5.1\" | Width: 2\"";
		   //iPad
		   //ipadair5
			  productDescrition[3]="10.9-inch Liquid Retina display with True Tone, P3 wide color, and an antireflective coating\r\n"
			  		+ "Apple M1 chip with Neural Engine\r\n"
			  		+ "12MP Wide camera\r\n"
			  		+ "12MP Ultra Wide front camera with Center Stage\r\n"
			  		+ "Up to 256GB of storage\r\n"
			  		+ "Available in blue, purple, pink, starlight, and space gray\r\n"
			  		+ "Stereo landscape speakers\r\n"
			  		+ "Touch ID for secure authentication and Apple Pay\r\n"
			  		+ "All-day battery life\r\n"
			  		+ "5G capable\r\n"
			  		+ " \r\n"
			  		+ "What’s in the Box:\r\n"
			  		+ "iPad Air\r\n"
			  		+ "USB-C Charge Cable\r\n"
			  		+ "20W USB-C Power Adapter";
		   //ipadmini6
			  productDescrition[4]="8.3-inch Liquid Retina display with True Tone and wide color\r\n"
			  		+ "A15 Bionic chip with Neural Engine\r\n"
			  		+ "Touch ID for secure authentication and Apple Pay\r\n"
			  		+ "12MP Wide back camera, 12MP Ultra Wide front camera with Center Stage\r\n"
			  		+ "Available in purple, starlight, pink, and space gray\r\n"
			  		+ "Stay connected with ultrafast Wi-Fi 6\r\n"
			  		+ "Up to 10 hours of battery life\r\n"
			  		+ "USB-C connector for charging and accessories\r\n"
			  		+ "Works with Apple Pencil (2nd generation)\r\n"
			  		+ "\r\n"
			  		+ "What’s in the Box:\r\n"
			  		+ "iPad mini\r\n"
			  		+ "USB-C Charge Cable\r\n"
			  		+ "20W USB-C Power Adapter";
		   //ipadpro
			  productDescrition[5]="Apple M1 chip for next-level performance\r\n"
			  		+ "Brilliant 12.9-inch Liquid Retina XDR display with ProMotion, True Tone, and P3 wide color\r\n"
			  		+ "TrueDepth camera system featuring Ultra Wide camera with Center Stage\r\n"
			  		+ "12MP Wide camera, 10MP Ultra Wide camera, and LiDAR Scanner for immersive AR\r\n"
			  		+ "Stay connected with ultrafast Wi-Fi\r\n"
			  		+ "Go further with all-day battery life\r\n"
			  		+ "Thunderbolt port for connecting to fast external storage, displays, and docks\r\n"
			  		+ "Face ID for secure authentication and Apple Pay\r\n"
			  		+ "Four speaker audio and five studio-quality microphones\r\n"
			  		+ "Support for Apple Pencil (2nd generation), Magic Keyboard, and Smart Keyboard Folio\r\n"
			  		+ " \r\n"
			  		+ "What’s in the Box:\r\n"
			  		+ "iPad Pro\r\n"
			  		+ "USB-C Charge Cable\r\n"
			  		+ "20W USB-C Power Adapter";
		   //ipad(9th gen)
			  productDescrition[6]="Gorgeous 10.2-inch Retina display with True Tone\r\n"
			  		+ "A13 Bionic chip with Neural Engine\r\n"
			  		+ "8MP Wide back camera, 12MP Ultra Wide front camera with Center Stage\r\n"
			  		+ "Up to 256GB storage\r\n"
			  		+ "Stereo speakers\r\n"
			  		+ "Touch ID for secure authentication and Apple Pay\r\n"
			  		+ "802.11ac Wi-Fi\r\n"
			  		+ "Up to 10 hours of battery life\r\n"
			  		+ "Lightning connector for charging and accessories\r\n"
			  		+ "Works with Apple Pencil (1st generation) and Smart Keyboard\r\n"
			  		+ " \r\n"
			  		+ "What’s in the Box:\r\n"
			  		+ "iPad\r\n"
			  		+ "USB-C to Lightning Cable\r\n"
			  		+ "20W USB-C Power Adapter";
			  //pencil
			   productDescrition[7]="Apple Pencil (2nd generation) delivers pixel-perfect precision and industry-leading low latency, making it great for drawing, sketching, coloring, taking notes, marking up PDFs, and more. And it’s as easy and natural to use as a pencil.\r\n"
					    + "Apple Pencil (2nd generation) also allows you to change tools without setting it down, thanks to its intuitive touch surface that supports double-tapping.\r\n"
					    + "Designed for iPad Pro, iPad Air, and iPad mini, it features a flat edge that attaches magnetically for automatic charging and pairing.\r\n"
					    + " \r\nLength: 6.53 inches (166 mm)\r\nDiameter: 0.35 inch (8.9 mm)\r\nWeight: 0.73 ounce (20.7 grams)\r\n"
					    + "Connections: Bluetooth\r\nOther Features\r\nMagnetically attaches and pairs\r\n";
			 //keyboard
			  productDescrition[8]="The Magic Keyboard is an amazing companion for iPad Pro 12.9-inch. It features an incredible typing experience, "
			  		+ "a trackpad that opens up new ways to work with iPadOS,"
			  		+ " a USB‑C port for passthrough charging, and front and back protection. The Magic Keyboard has a floating cantilever design, allowing you to attach iPad "
			  		+ "Pro magnetically and to smoothly adjust it to the perfect viewing angle for you.";
			  //iPhone
			  //13
			  productDescrition[9]="15 cm (6.1-inch) Super Retina XDR display\r\n"
			  		+ "Cinematic mode adds shallow depth of field and shifts focus automatically in your videos\r\n"
			  		+ "Advanced dual-camera system with 12MP Wide and Ultra Wide cameras; Photographic Styles, Smart HDR 4, Night mode, 4K Dolby Vision HDR recording\r\n"
			  		+ "12MP TrueDepth front camera with Night mode, 4K Dolby Vision HDR recording\r\n"
			  		+ "A15 Bionic chip for lightning-fast performance\r\n"
			  		+ "Up to 19 hours of video playback\r\n"
			  		+ "Durable design with Ceramic Shield\r\n"
			  		+ "Industry-leading IP68 water resistance\r\n"
			  		+ "iOS 15 packs new features to do more with iPhone than ever before\r\n"
			  		+ "Supports MagSafe accessories for easy attachment and faster wireless charging\r\n";
			  //mini
			  productDescrition[10]="6.1″ Super Retina XDR display\r\nSuperfast 5G cellular\r\nTelephoto\r\n"
				  		+ "Wide\r\nUltra Wide\r\nCinematic mode in 1080p at 30 fps\r\nDolby Vision HDR video recording up to "
				  		+ "4K at 60 fps\r\n6x Optical zoom range\r\nLiDAR Scanner for Night mode portraits, faster autofocus in low "
				  		+ "light, and next-level AR experiences\r\nA15 Bionic chip\r\nNew 6-core CPU with 2 performance and 4 efficiency cores\r\n"
				  		+ "New 5-core GPU\r\nNew 16-core Neural Engine\r\nUp to 22 hours video playback\r\n"
				  		+ "Face ID\r\nCeramic Shield front\r\nSurgical-grade stainless steel\r\nWater resistant to a depth of 6 meters for up to"
				  		+ " 30 minutes4\r\nCompatible with MagSafe accessories and wireless chargers\r\n"
				  		+ "Height: 5.78 inches (146.7 mm)\r\nWidth: 2.82 inches (71.5 mm)\r\n"
				  		+ "Depth: 0.30 inch (7.65 mm)\r\nWeight: 7.19 ounces (204 grams)\r\n";
			 //pro
			  productDescrition[11]="6.1-inch Super Retina XDR display with ProMotion for a faster, more responsive feel\r\n"
			  		+ "Cinematic mode adds shallow depth of field and shifts focus automatically in your videos\r\n"
			  		+ "Pro camera system with new 12MP Telephoto, Wide, and Ultra Wide cameras; LiDAR Scanner; 6x optical zoom range; macro photography; Photographic Styles, ProRes video, Smart HDR 4, Night mode, Apple ProRAW, 4K Dolby Vision HDR recording\r\n"
			  		+ "12MP TrueDepth front camera with Night mode, 4K Dolby Vision HDR recording\r\n"
			  		+ "A15 Bionic chip for lightning-fast performance\r\n"
			  		+ "Up to 22 hours of video playback\r\nDurable design with Ceramic Shield\r\n"
			  		+ "Industry-leading IP68 water resistance\r\n5G capable\r\niOS 15 packs new features to do more with iPhone than ever before\r\n";	
			 //pro max
			  productDescrition[12]="6.7-inch Super Retina XDR display with ProMotion for a faster, more responsive feel\r\n"
			  		+ "Cinematic mode adds shallow depth of field and shifts focus automatically in your videos\r\n"
			  		+ "Pro camera system with new 12MP Telephoto, Wide, and Ultra Wide cameras; LiDAR Scanner; 6x optical zoom range; macro photography; Photographic Styles, ProRes video, Smart HDR 4, Night mode, Apple ProRAW, 4K Dolby Vision HDR recording\r\n"
			  		+ "12MP TrueDepth front camera with Night mode, 4K Dolby Vision HDR recording\r\n"
			  		+ "A15 Bionic chip for lightning-fast performance\r\n"
			  		+ "Up to 28 hours of video playback, the best battery life ever in an iPhone\r\n"
			  		+ "Durable design with Ceramic Shield\r\n";			  
			 //macBook 
			 //Pro 14
			  productDescrition[13]="Apple M1 Pro chip with 8-Core CPU 14-Core GPU\r\n"
			  		+ "16GB Unified Memory\r\n"
			  		+ "512GB SSD Storage\r\n"
			  		+ "16-core Neural Engine\r\n"
			  		+ "14-inch Liquid Retina XDR display\r\n"
			  		+ "Three Thunderbolt 4 ports, HDMI port, SDXC card slot, MagSafe 3 port\r\n"
			  		+ "Magic Keyboard with Touch ID\r\n"
			  		+ "Force Touch trackpad\r\n"
			  		+ "67W USB-C Power Adapter\r\n"
			  		+ " \r\n"
			  		+ "What’s in the Box:\r\n"
			  		+ "14-inch MacBook Pro\r\n"
			  		+ "USB-C to MagSafe 3 Cable (2 m)\r\n"
			  		+ "USB-C Power Adapter\r\n"
			  		+ "";
			 //Pro 16
			  productDescrition[14]="Apple M1 Pro chip with 10-Core CPU16-Core GPU \r\n"
			  		+ "16GB Unified Memory\r\n512GB SSD Storage\r\n"
			  		+ "16-core Neural Engine\r\n16-inch Liquid Retina XDR display\r\n"
			  		+ "Three Thunderbolt 4 ports, HDMI port, SDXC card slot, MagSafe 3 port\r\n"
			  		+ "Magic Keyboard with Touch ID\r\n"
			  		+ "Force Touch trackpad\r\n"
			  		+ "140W USB-C Power Adapter\r\n"
			  		+ " \r\nWhat’s in the Box:\r\n"
			  		+ "16-inch MacBook Pro\r\n"
			  		+ "USB-C Charge Cable (2 m)\r\n"
			  		+ "140W USB-C Power Adapter";
			  //Pro 13
			  productDescrition[15]="Apple M1 Pro chip with 8-core CPU, 8-core GPU, and 16-core Neural Engine\r\n"
			  		+ "8GB unified memory\r\n"
			  		+ "256GB/ 512GB SSD storage\r\n"
			  		+ "13-inch Retina display with True Tone\r\n"
			  		+ "Magic Keyboard\r\n"
			  		+ "Touch Bar and Touch ID\r\n"
			  		+ "Force Touch trackpad\r\n"
			  		+ "Two Thunderbolt / USB 4 ports\r\n"
			  		+ " \r\n"
			  		+ "What’s in the Box:\r\n"
			  		+ "13-inch MacBook Pro\r\n"
			  		+ "USB-C Charge Cable (2 m)\r\n"
			  		+ "61W USB-C Power Adapter\r\n";
			  //air
			  productDescrition[16]="Apple M1 chip with 8-core CPU, 8-core GPU, and 16-core6 Neural Engine\r\n8GB unified memory\r\n"
			  		+ "256GB/ 512GB SSD storage¹\r\nRetina display with True Tone\r\nMagic Keyboard\r\nTouch ID\r\n"
			  		+ "Force Touch trackpad\r\nTwo Thunderbolt / USB 4 ports\r\n"
			  		+ " \r\nWhat’s in the Box:\r\nMacBook Air.\r\n"
			  		+ "USB-C Charge Cable (2 m)\r\n30W USB-C Power Adapter\r\n";
			  //airpods
			  //pro
			  productDescrition[17]="Magic like you’ve never heard\r\n"
			  		+ "AirPods Pro have been designed to deliver Active Noise Cancellation for immersive sound, Transparency mode so you can hear your surroundings, and a customizable fit for all-day comfort. Just like AirPods, AirPods Pro connect magically to your Apple devices. And they’re ready to use right out of the case.\r\n"
			  		+ "Active Noise Cancellation\r\n"
			  		+ "Incredibly light noise-cancelling headphones, AirPods Pro block out your environment so you can focus on what you’re listening to. AirPods Pro use two microphones, an outward-facing microphone and an inward-facing microphone, to create superior noise cancellation. By continuously adapting to the geometry of your ear and the fit of the ear tips, Active Noise Cancellation silences the world to keep you fully tuned in to your music, podcasts, and calls.\r\n"
			  		+ "Transparency mode\r\n"
			  		+ "Switch to Transparency mode and AirPods Pro let the outside sound in, allowing you to hear and connect to your surroundings. Outward- and inward-facing microphones enable AirPods Pro to undo the sound-isolating effect of the silicone tips so things sound and feel natural, like when you’re talking to people around you. And Conversation Boost makes it easier to hear people during face-to-face conversations in noisy environments by focusing your AirPods Pro on the person talking directly in front of you.\r\n"
			  		+ "Customizable fit\r\n"
			  		+ "AirPods Pro offer a more customizable fit with three sizes of flexible silicone tips to choose from. With an internal taper, they conform to the shape of your ear, securing your AirPods Pro in place and creating an exceptional seal for superior noise cancellation.\r\n"
			  		+ "MagSafe Charging Case\r\n"
			  		+ "AirPods Pro with the MagSafe Charging Case deliver more than 24 hours of battery life. When it’s time to charge, just set the case down on your MagSafe charger, or a wireless charging mat, and let it charge. And when you’re away from a wireless charger, you can use the Lightning port to charge.\r\n"
			  		+ "Amazing audio quality\r\n"
			  		+ "A custom-built high-excursion, low-distortion driver delivers powerful bass. A superefficient high dynamic range amplifier produces pure, incredibly clear sound while also extending battery life. Spatial audio with dynamic head tracking places sound all around you.² And Adaptive EQ automatically tunes music to suit the shape of your ear for a rich, consistent listening experience.\r\n"
			  		+ "Even more magical\r\n"
			  		+ "The Apple-designed H1 chip delivers incredibly low audio latency. A force sensor on the stem makes it easy to control music and calls and switch between Active Noise Cancellation and Transparency mode. Announce Messages with Siri gives you the option to have Siri read your messages through your AirPods. And with Audio Sharing, you and a friend can share the same audio stream on two sets of AirPods — so you can play a game, watch a movie, or listen to a song together.\r\n"
			  		+ " \r\nWhat’s in the Box\r\nAirPods Pro\r\n"
			  		+ "MagSafe Charging Case\r\n"
			  		+ "Silicone ear tips (three sizes)\r\n"
			  		+ "Lightning to USB-C Cable\r\n"
			  		+ "Documentation\r\n"
			  		+ "";
			  //3 gen
			  productDescrition[18]="All-new design\r\n"
			  		+ "AirPods are lightweight and offer a contoured design. They sit at just the right angle for comfort and to better direct audio to your ear. The stem is 33 percent shorter than AirPods (2nd generation) and includes a force sensor to easily control music and calls.\r\n"
			  		+ "Spatial audio with dynamic head tracking\r\n"
			  		+ "Sound is placed all around you to create an immersive, three-dimensional listening experience for music, TV shows, and movies. Gyroscopes and accelerometers in AirPods work together to track your head movements — so it sounds like you’re in the center of songs and scenes.\r\n"
			  		+ "Adaptive EQ\r\n"
			  		+ "Music is automatically tuned to suit the shape of your ear. Inward-facing microphones detect what you’re hearing, then adjust low- and mid-range frequencies to deliver the rich details in every song.\r\n"
			  		+ "Longer battery life\r\n"
			  		+ "AirPods have an extra hour of battery life compared with AirPods (2nd generation) for up to 6 hours of listening time and up to 4 hours of talk time. With just 5 minutes of charge, you’ll get around an hour of listening⁶ or talk time. And with the MagSafe Charging Case, you can enjoy up to 30 hours of total listening time and charge with compatible MagSafe and wireless chargers.\r\n"
			  		+ "Sweat and water resistant\r\n"
			  		+ "Both AirPods and the MagSafe Charging Case are rated IPX4 water resistant — so they’ll withstand anything from rain to heavy workouts.\r\n"
			  		+ "Magical in every way\r\n"
			  		+ "Setup is effortless — pull them out of the case and they’re ready to use. Automatically switch between your Apple devices. In-ear detection knows the difference between your ear and other surfaces. Announce Notifications with Siri gives you the option to have Siri read your notifications through your AirPods. And with Audio Sharing, you and a friend can easily share a song or show between any two sets of AirPods.\r\n"
			  		+ "What’s in the Box\r\n"
			  		+ "AirPods\r\n"
			  		+ "\r\n"
			  		+ "MagSafe Charging Case\r\n"
			  		+ "\r\n"
			  		+ "Lightning to USB-C Cable\r\n"
			  		+ "\r\n"
			  		+ "Documentation";
			  //2 gen
        	  productDescrition[19]="More magical than ever.\r\nAirPods deliver the wireless headphone experience, reimagined. Just pull them out "
			  		+ "of the charging case and they’re ready to use with your iPhone, Apple Watch, iPad, or Mac.\r\nAfter a simple one-tap setup, "
			  		+ "AirPods work like magic. They’re automatically on and always connected. AirPods can even sense when they’re in your ears and "
			  		+ "pause when you take them out.\r\nTo adjust the volume, change the song, make a call, or even get directions, simply say “Hey "
			  		+ "Siri” and make your request. You have the freedom to wear one or both AirPods, and you can play or skip forward with a double-tap"
			  		+ " when listening to music or podcasts.\r\nAirPods deliver 5 hours of listening time and 3 hours of talk time on a single charge. "
			  		+ "And they’re made to keep up with you, thanks to a charging case that holds multiple charges for more than 24 hours of listening "
			  		+ "time. Need a quick charge? Just 15 minutes in the case gives you 3 hours of listening time or 2 hours of talk time.\r\n"
			  		+ "Powered by the all-new Apple H1 headphone chip, AirPods use optical sensors and motion accelerometers to detect when they’re in "
			  		+ "your ears. Whether you’re using both AirPods or just one, the H1 chip automatically routes the audio and engages the microphone. "
			  		+ "And when you’re on a call or talking to Siri, an additional speech-detecting accelerometer works with beamforming microphones to "
			  		+ "filter out external noise and focus on the sound of your voice.\r\n"
			  		+ "What’s in the Box:\r\nAirPods\r\nCharging Case\r\nLightning to USB-A Cable\r\nDocumentation\r\n";
        	  //max
			  productDescrition[20]="Designed for an Exceptional Fit:\r\nKnit mesh canopy and acoustically engineered memory foam ear cushions.\r\n"
			  		+ "High-Fidelity Audio:\r\nThe Apple-designed driver delivers high-fidelity playback with ultra-low distortion across the entire"
			  		+ " audible range.\r\nNoise Control:\r\nActive Noise Cancellation for immersive sound. Transparency mode to hear the world around"
			  		+ " you.\r\nSpatial Audio:\r\nFor theater-like sound that surrounds you.\r\n \r\nWhat’s in the Box:\r\n"
			  		+ "AirPods Max\r\nSmart Case\r\nLightning to USB-C Cable\r\n";
		     //add all product to product object arraylist
				    for (int i=0;i<productNamearray.length;i++) {
				    	product p=new product(productNamearray[i],productPricearray[i],productInventaryarray[i],productDescrition[i]);
				    	productlist.add(p);
				    }
	  }
    private static UserInfo currentUser;
    
	
	public static void initData() {
		UserInfo c = new UserInfo("admin", "123456", "62345678", "China",
				"female", 500);
		userInfoList.add(c);
	}

	public static List<UserInfo> getUserInfoList() {
		return userInfoList;
	}
	
	public static List<product> getProductlist() {
		return productlist;
	}

	public static void setUserInfoList(List<UserInfo> userInfoList) {
		AllData.userInfoList = userInfoList;
	}
	
	public static void setCurrentUser(UserInfo info){
		AllData.currentUser = info;
	}
	
	public static UserInfo getCurrentUser(){
		return AllData.currentUser;
	}

}
