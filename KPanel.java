import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 */

/**
 * KPanel extends JPanel and by default uses a BorderLayout. This is to make
 * creation and handling of the grid display easier.
 * 
 * @author Aaron Carson
 * @version May 13, 2014
 */
public class KPanel extends JPanel {
	private static final long serialVersionUID = -6155459401090631351L;
	public static final String KNIGHT_STRING = "\u265E"; // chess piece
	private static boolean DISPLAY_DOT = false;
	private JLabel label;
	private String labelText;
	private Color defaultColor;

	/**
	 * Create a new KPanel that will display the assigned text in the center of
	 * the panel.
	 * 
	 * @param text The text for the label to display.
	 */
	public KPanel(String text) {
		super(new BorderLayout(), true);
		setLabel(text);
	}

	/**
	 * Create a new KPanel with an empty label.
	 */
	public KPanel() {
		this(null);
	}

	/**
	 * Set the text for this KPanel's label.
	 * 
	 * @param text The Text to be used for this Label.
	 */
	public void setLabel(String text) {
		if (text != null) {
			if (label == null) {
				label = new JLabel();
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);
				this.add(label, BorderLayout.CENTER);
			}
			if (text.length() != 0 && DISPLAY_DOT) {
				label.setText(KNIGHT_STRING);
			}
			else {
				label.setText(text);
			}
			labelText = text;
		}
	}

	/**
	 * Returns the labelText of this KPanel, regardless of if the Panel is
	 * displayed as a Dot or text.
	 * @return The label text.
	 */
	public String getLabelText() {
		return labelText;
	}

	/**
	 * Assign the font to be used for this KPanel's Label.
	 * 
	 * @param f The Font to use.
	 */
	public void setLabelFont(Font f) {
		label.setFont(f);
	}

	/**
	 * Assign the font to be used for this KPanel's Label.
	 * 
	 * @param f The Font to use.
	 */
	public Font getLabelFont() {
		return label.getFont();
	}

	/**
	 * Update the label font according to the KPanel's size.
	 */
	public void updateLabelFont() {
		if (DISPLAY_DOT) {
			updateLabelFont(1.8f);
		}
		else {
			updateLabelFont(1.8f);
		}
	}

	/**
	 * Update the label font according to the KPanel's size and the input
	 * divisor to scale the Font.
	 * @param divisor the float value to scale by (inverse relationship, ≠ 0).
	 */
	private void updateLabelFont(float divisor) {
		if (getHeight() < getWidth()) {
			label.setFont(label.getFont().deriveFont(getHeight() / divisor));
		}
		else {
			label.setFont(label.getFont().deriveFont(getWidth() / divisor));
		}
	}

	/**
	 * Sets the background color AND the default color.
	 * @param c The color to assign to this KPanel.
	 */
	public void setDefaultColor(Color c) {
		setBackground(c);
		defaultColor = c;
	}

	/**
	 * Returns the default color (last value assigned with setDefaultColor()).
	 * @return The default color.
	 */
	public Color getDefaultColor() {
		return defaultColor;
	}

	/**
	 * Updates the GUI by redrawing the panel as specified by the input index.
	 * @param s The string to assign to the panel.
	 */
	public void repaint(String s) {
		setLabel(s);
		paintAll(getGraphics());
	}

	/**
	 * Updates the GUI by redrawing the panel as specified by the input index.
	 * @param s The string to assign to the panel.
	 * @param waitTime the number of milliseconds to wait after repainting. If
	 *            set to zero or less, wait is ignored.
	 */
	public void repaint(String s, int waitTime) {
		setLabel(s);
		paintAll(getGraphics());

	}

	public static void displayVisitedAsDot() {
		DISPLAY_DOT = true;
	}

	public static void displayVisitedAsText() {
		DISPLAY_DOT = false;
	}

}
