/* program: Online Shopping Platform
 * Author:  Leo, LUO JUNLIN, BB906647 (001)
 *			Natalie, LEI UN IENG, BC000522 (001)
 *			Duchess, LIU SHUTONG, BC004629 (002)
 *			Clarie, BC002251, WONG SOKI (001)
 * 
 * This is where our program should be run.
 */

package Customer_interface;


import frame.AllData;
import frame.UserLoginFrame;

public class main {
	

	public static void main(String[] args) {
		AllData.createProductlist();
	//	mainmenu frame = new mainmenu();
		AllData.initData();
		new UserLoginFrame();

	}	
	
	
}


