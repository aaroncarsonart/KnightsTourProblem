import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * 
 */

/**
 * 
 * @author Aaron Carson
 * @version May 13, 2014
 */
public class Lab2Main {

	/**
	 * Run the gui, attempting to display using Apple System layout first.
	 * @param args
	 */
	public static void main(String[] args) {
		// tries to display as Apple UI first
		try {
			 System.setProperty("apple.laf.useScreenMenuBar", "true");
			 System.setProperty(
			 "com.apple.mrj.application.apple.menu.about.name", "Test");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {
			// ignore the exception
		}
		finally {
			SwingUtilities.invokeLater(new Lab2GUI());
		}
	}

}
