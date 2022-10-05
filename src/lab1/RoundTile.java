package lab1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * A round tile manages painting of a circle
 * in a specified area of the screen.
 * 
 * Whenever the object should paint itself,
 * it is told what size and position that
 * should be used to paint it.
 */
public class RoundTile extends GameTile {

	/** The color of the circle */
	private final Color strokeColor;
	private final Color fillColor;
	private final Stroke stroke;
	private final double scale;

	/**
	 * Creates a circular game tile.
	 * 
	 * @param fillColor
	 *            the color of the interior of the circle.
	 */
	public RoundTile(final Color fillColor) {
		this(fillColor, fillColor);
	}

	/**
	 * Creates a circular game tile with a stroke around it.
	 * 
	 * @param strokeColor
	 *            the color of the stroke around the circle.
	 * @param fillColor
	 *            the color of the interior of the circle.
	 */
	public RoundTile(final Color strokeColor, final Color fillColor) {
		this(strokeColor, fillColor, 1.0);
	}

	/**
	 * Creates a circular game tile with a stroke around it.
	 * 
	 * @param strokeColor
	 *            the color of the stroke around the circle.
	 * @param fillColor
	 *            the color of the interior of the circle.
	 * @param thickness
	 *            the thickness of the stroke.
	 */
	public RoundTile(final Color strokeColor, final Color fillColor,
			final double thickness) {
		this(strokeColor, fillColor, thickness, 1.0);
	}

	/**
	 * Creates a circular game tile with a stroke around it.
	 * 
	 * @param strokeColor
	 *            the color of the stroke around the circle.
	 * @param fillColor
	 *            the color of the interior of the circle.
	 * @param thickness
	 *            the thickness of the stroke.
	 * @param scale
	 *            size of the circle relative to the tile size.
	 */
	public RoundTile(final Color strokeColor, final Color fillColor,
			final double thickness, final double scale) {
		this.strokeColor = strokeColor;
		this.fillColor = fillColor;
		this.stroke = new BasicStroke((float) thickness);
		this.scale = scale;
	}

	/**
	 * Draws itself in a given graphics context, position and size.
	 * 
	 * @param g
	 *            graphics context to draw on.
	 * @param x
	 *            pixel x coordinate of the tile to be drawn.
	 * @param y
	 *            pixel y coordinate of the tile to be drawn.
	 * @param d
	 *            size of this object in pixels.
	 */
	@Override
	public void draw(final Graphics g, final int x, final int y,
			final Dimension d) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(this.fillColor);
		double xOffset = (d.width * (1.0 - this.scale)) / 2.0;
		double yOffset = (d.height * (1.0 - this.scale)) / 2.0;
		g2.fillOval((int) (x + xOffset), (int) (y + yOffset),
				(int) (d.width - xOffset * 2),
				(int) (d.height - yOffset * 2));
		g2.setStroke(this.stroke);
		g2.setColor(this.strokeColor);
		g2.drawOval((int) (x + xOffset), (int) (y + yOffset),
				(int) (d.width - xOffset * 2),
				(int) (d.height - yOffset * 2));
	}
}
