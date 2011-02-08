package com.angelo.androidprova.applet;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Path.FillType;
import android.util.Log;

import com.angelo.androidprova.core.CCustomColours;
import com.angelo.androidprova.core.CDasherScreen;
import com.angelo.androidprova.core.CDasherView;
import com.angelo.androidprova.core.EColorSchemes;
import com.angelo.androidprova.core.Point;
import com.angelo.androidprova.graphic.PaintActivity2;

/**
 * This implementation of JDasherScreen is an extension of the Swing basic
 * object JPanel, and works by drawing a frame of Dasher onto the panel whenever
 * its paintComponent method is called.
 * <p>
 * For a general description of the Screen's contract and specifications of what
 * its methods ought to do, see CDasherScreen. Only details of the
 * implementation will be noted here.
 */
public class JDasherScreen implements CDasherScreen {

	/**
	 * Graphics context in which to draw
	 */
	// private Graphics paint;

	private Canvas canvas;

	private Paint paintFill;
	private Paint paintBorder;
	private Paint paintText;
	private Paint paintBlank;
	private RectF mScratchRect;
	private Rect rectBlank = new Rect(0, 0, 0, 0);
	Path path = new Path();
	/**
	 * Dasher instance which draws onto this Screen
	 */
	private JDasher m_Dasher;

	/**
	 * Custom colour scheme against which colour indices are resolved
	 */
	private CCustomColours m_Colours;

	/**
	 * Map of known sizes of differing characters at different font sizes.
	 */
	// private HashMap<TextSize, Point> TextSizes;

	/**
	 * Map from font sizes to the Font objects used to draw them.
	 */
	// private HashMap<Long, DasherFont> DrawFonts;

	/**
	 * Width of this screen in pixels
	 */
	protected int m_iWidth;

	/**
	 * Height of this screen in pixels
	 */
	protected int m_iHeight;

	/**
	 * Creates a new Screen tied to a given Dasher and with given dimensions
	 * 
	 * @param Dasher
	 *            Dasher which is displaying using this screen.
	 * @param width
	 *            Width of this screen
	 * @param height
	 *            Height of this screen
	 */
	public JDasherScreen(JDasher Dasher, int width, int height) {

		m_Dasher = Dasher;

		/*
		 * We need this back-reference so that we can request new frames to be
		 * drawn when a paint event arises.
		 */

		m_iWidth = width;
		m_iHeight = height;

		// TextSizes = new HashMap<TextSize, Point>();

		paintBorder = new Paint();
		paintFill = new Paint();
		paintText = new Paint();
		paintBlank = new Paint();

		paintBlank.setColor(Color.WHITE);
		paintBlank.setStyle(Paint.Style.FILL);
		paintFill.setStyle(Paint.Style.FILL);
		paintText.setColor(Color.BLACK);
		paintBorder.setStyle(Paint.Style.STROKE);
		mScratchRect = new RectF(0, 0, 0, 0);
	}

	public int GetWidth() {

		return m_iWidth;

	}

	public int GetHeight() {

		return m_iHeight;

	}

	/**
	 * Clears the screen using the clearRect method
	 */
	public void Blank() {

		if (canvas != null) {
			rectBlank.set(0, 0, canvas.getWidth(), canvas.getHeight());
			canvas.drawRect(rectBlank, paintBlank);

		}
		// paint.fillRect(0,0,m_iWidth, m_iHeight);
		// canvas.drawRect(new Rect(0,0,m_iWidth, m_iHeight), paint);
	}

	public void setSize(int width, int height) {
		m_iWidth = width;
		m_iHeight = height;
	}

	/**
	 * Method ignored; as we're drawing to Swing's provided surface, the actual
	 * displaying of the image will be taken care of for us.
	 */
	public void Display() {
		/*
		 * No need to do anything; we've been drawing to the content surface all
		 * along, and Swing will take care of showing it.
		 */
	}

	public void DrawCircle(int iCX, int iCY, int iR, int iColour, boolean bFill) {

		/*
		 * CSFS: This is probably still wrong for reasons described in
		 * DrawRectangle. I've left it alone for the time being since it's very
		 * rarely used by Dasher.
		 */
		/*
		 * if (bFill) { // paint.fillOval(iCX - iR, iCY - iR, iR, iR);
		 * setColour(iColour, paintFill); canvas.drawOval(new RectF(iCX - iR,
		 * iCY - iR, iR, iR), paintFill); } else { // paint.drawOval(iCX - iR,
		 * iCY - iR, iR, iR); setColour(iColour, paintBorder);
		 * canvas.drawOval(new RectF(iCX - iR, iCY - iR, iR, iR), paintBorder);
		 * }
		 */
	}

	/**
	 * Dasher specifies its co-ordinates like
	 * 
	 * <pre>
	 * y2---------x2
	 * |           |
	 * |           |
	 * x1,y1-------/
	 * </pre>
	 * <p>
	 * Whereas Java wants
	 * 
	 * <pre>
	 * 			width
	 * x,y---------------\
	 * |				 |
	 * |				 | height
	 * |				 |
	 * \-----------------/
	 * </pre>
	 * <p>
	 * Therefore, I use (x1, y2) as the point to feed to Java, and calculate
	 * height and width.
	 * 
	 */

	public void DrawRectangle(int x1, int y1, int x2, int y2, int Color,
			int iOutlineColour, EColorSchemes ColorScheme,
			boolean bDrawOutline, boolean bFill, int iThickness) {

		mScratchRect.set(x1, y2, x2, y1);
		// if(((x2-x1)<8)|((y1-y2)<16))return;
		if (bFill) {
			// setColour(Color);
			// paint.fillRect(x1, y2, x2 - x1, y1 - y2);

			// paint.setColor(Color.BLACK);
			// paintFill.setColor(Color);
			// 139
			// Log.e("JDaasherScreen","Color"+Color);

			setColour(Color, paintFill);
			// paintFill.setColor(Color);
			// mScratchRect.set(x1, y2, x2, y1);
			if (canvas != null)
				canvas.drawRect(mScratchRect, paintFill);
		}

		if (bDrawOutline) {

			// setColour(iOutlineColour);
			// paint.drawRect(x1, y2, x2 - x1, y1 - y2); // xtop, yleft, width,
			// height

			// paintBorder.setColor(iOutlineColour);
			setColour(iOutlineColour, paintBorder);
			if (canvas != null)
				canvas.drawRect(mScratchRect, paintBorder);
		}
	}

	/**
	 * Here be more trouble! Dasher is specifying its strings by the
	 * co-ordinates of the top-left corner of a rectangle in which the text will
	 * be drawn. So,
	 * 
	 * <pre>
	 *   x,y------------\
	 *   | Some String  |
	 *   \--------------/
	 * </pre>
	 * <p>
	 * Java's DrawString method however specifies the baseline of the first
	 * character, which is the bottom in the case of non-descenders such as a
	 * and b, but is not for descenders such as g.
	 * <p>
	 * Therefore we must figure out the height and adjust x appropriately before
	 * drawing the string.
	 * <p>
	 * It's important to note also that some work in this direction is done by
	 * DasherView calling the Screen's TextSize method. This supplies a height
	 * and width of a given string in pixels, using a HashMap to do so
	 * efficiently.
	 * 
	 */

	public void DrawString(String string, int x1, int y1, long Size) {

		/*
		 * int thisOffset;
		 * 
		 * if(DrawFonts.containsKey(Size)) {
		 * paint.setFont(DrawFonts.get(Size).font); thisOffset =
		 * DrawFonts.get(Size).drawOffset; } else { //java.awt.font. Font
		 * newFont = new Font("sans", 0, (int)Size); paint.setFont(newFont);
		 * FontMetrics fm = paint.getFontMetrics(); thisOffset = fm.getAscent();
		 * 
		 * DasherFont newDasherFont = new DasherFont(newFont, thisOffset,
		 * (int)Size);
		 * 
		 * DrawFonts.put(Size, newDasherFont); }
		 */

		/*
		 * CSFS: Since it is necessary to generate lots of fonts in the course
		 * of Dashing AND I need to store a drawing offset for each one,
		 * (although I may address this later by modifying the drawing code),
		 * I've adapted the hashmap method found in the GetFont method in
		 * Screen.inl to save work.
		 */

		// paint.drawString(string, x1, y1 + (thisOffset / 2));
		if (canvas != null) {
			long newSize = (byte) Size;
			if (paintText.getTextSize() != newSize)
				paintText.setTextSize(Size);
			canvas
					.drawText(string, x1, y1 /* + (thisOffset / 2) */, paintText);
		}
	}

	public void Polygon(Point[] Points, int Number, int Color) {

		/* angelo uncomment Polygon(Points, Number, Color, 0); */
		// Log.e("JDAsherScreen","Polygon1");
	}

	public void Polygon(Point[] Points, int Number, int Color, int iWidth) {
		/*
		 * angelo uncomment paint.setColor(Color); setColour(Color); Path path =
		 * new Path(); int[] xs = new int[Points.length]; int[] ys = new
		 * int[Points.length]; for (int i = 0; i < xs.length; i++) { xs[i] =
		 * Points[i].x; ys[i] = Points[i].y;
		 * 
		 * path.moveTo((float) xs[i], (float) ys[i]); }
		 * 
		 * path.setFillType(FillType.EVEN_ODD); canvas.drawPath(path, paint);
		 */
		// Log.e("JDAsherScreen","Polygon2");
	}

	public void Polyline(Point[] Points, int Number, int iWidth) {

		/* angelo uncomment Polyline(Points, Number, iWidth, 0); */
		// Log.e("JDAsherScreen","Polyline1");
	}

	public void Polyline(Point[] points, int Number, int iWidth, int Colour) {
		setColour(Colour, paintBorder);
		path.reset();
		path.moveTo((float) points[0].x, (float) points[0].y);
		for (int i = 1; i < points.length; i++) {
			path.lineTo((float) points[i].x, (float) points[i].y);
		}
		if (canvas != null)
			canvas.drawPath(path, paintBorder);
	}

	public void SetColourScheme(CCustomColours ColourScheme) {

		m_Colours = ColourScheme;

	}

	/**
	 * Graphics.getFontMetrics().getStringBounds is used to determine a probable
	 * text size.
	 * <p>
	 * Results, referenced by a struct containing information about both the
	 * character and font size concerned, are stored in a HashMap for quick
	 * access in the future.
	 * <p>
	 * At present, StringBounds' returned answer is augmented by one pixel in
	 * the x direction in the interests of readability.
	 * 
	 * @param string
	 *            String whose size we want to determine
	 * @param Size
	 *            Font size to use
	 * 
	 * @return Point defining its size.
	 */
	public Point TextSize(String string, int Size) {
		/*
		 * TextSize testValue = new TextSize(); testValue.glyph = string;
		 * testValue.size = Size;
		 * 
		 * if(TextSizes.containsKey(testValue)) { return
		 * TextSizes.get(testValue); } else {
		 * paint.setFont(paint.getFont().deriveFont((float)Size)); Rectangle2D
		 * newsize = paint.getFontMetrics().getStringBounds(string ,paint);
		 * CDasherView.Point newpoint = new CDasherView.Point(); newpoint.x =
		 * (int)newsize.getWidth() + 1; newpoint.y = (int)newsize.getHeight();
		 * 
		 * TextSizes.put(testValue, newpoint);
		 * 
		 * //
		 * System.out.printf("Glyph %s at size %d (%d) has dimensions (%dx%d)%n"
		 * , string, Size, paint.getFont().getSize(), newpoint.x, newpoint.y);
		 * 
		 * return newpoint;
		 * 
		 * 
		 * }
		 */
		Point newpoint = Point.acquire();
		newpoint.x = 0;
		newpoint.y = 0;
		return newpoint;
	}

	/**
	 * Sets the current Graphics context's colour by querying m_Colours for its
	 * RGB values.
	 * <p>
	 * A colour of -1 is read as being the same as colour 3.
	 * 
	 * @param iColour
	 *            Colour to set
	 */
	private void setColour(int iColour, Paint paint) {
		if (iColour == -1)
			iColour = 3; // Special value used in Dasher, seems to mean 3.

		paint.setColor(0xff000000 + (m_Colours.GetRed(iColour) << 16)
				+ (m_Colours.GetGreen(iColour) << 8)
				+ (m_Colours.GetBlue(iColour)));
		// paint.setColor(new Color(m_Colours.GetRed(iColour),
		// m_Colours.GetGreen(iColour), m_Colours.GetBlue(iColour)));
	}

	public void SendMarker(int iMarker) {

		// Stub: This method is, for the time being, useless since Display() and
		// Blank() serve the same purpose.

	}

	/**
	 * Sets our Graphics context and calls NewFrame; Dasher will do the rest.
	 */
	public void drawToComponent(Canvas canvas) {

		// paint = g;

		this.canvas = canvas;

		// canvas.drawBitmap(bitmap, 0, 0, paint);
		m_Dasher.NewFrame(System.currentTimeMillis());
		// PaintActivity2.mActivity.textView.postInvalidate();
		// PaintActivity2.mActivity.t
		// Log.w("JDasherScreen", "canvas null " + (canvas == null));

	}

}

/**
 * Small struct used to store a glyph and font-size pair in the
 * character-to-drawn-size map.
 */
class TextSize {
	/**
	 * Character(s)
	 */
	String glyph;

	/**
	 * Font size
	 */
	int size;

	/**
	 * Returns true if the String and font size both match.
	 */
	public boolean equals(Object otherone) {
		if (otherone == null) {
			return false;
		}
		if (otherone instanceof TextSize) {
			TextSize theother = (TextSize) otherone;
			return (this.glyph.equals(theother.glyph) && this.size == theother.size);
		} else {
			return false;
		}
	}

	/**
	 * Overridden to use the String's hashCode plus the size.
	 */
	public int hashCode() {
		return this.glyph.hashCode() + size;
	}
}
