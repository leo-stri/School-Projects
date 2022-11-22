/*
 * This class holds helpful functions to use.
 */

package model;

import frame.AllData;
import frame.UserLoginFrame;

public class Functions {
	public static void logout(){
		new UserLoginFrame();
		AllData.setCurrentUser(null);
	}
}
