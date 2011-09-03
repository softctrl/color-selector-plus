package colorSelector;

import java.awt.Color;
import processing.core.*;

/* 
 * The Color Mixer applet
 * Code borowed from ColorSelector.java - part of the processing distribution
 * loadPixels() and updatePixels() are more smooth at updating the panel
 * that redrawing the paint area. 
 */
@SuppressWarnings("serial")
class MixerApplet extends PApplet {

	static final int WIDE = 256;
	static final int HIGH = 256;
	boolean firstRun = true;
	int lastX, lastY;
	int hue = 200, brightness = 110, saturation = 0;
	ColorSelectorPlus parent;

	public void setup() {
		size(WIDE, HIGH); // , P3D);

		colorMode(HSB, 360, 256, 256);
		noFill();
		rectMode(CENTER);
		noLoop();
		loadPixels();
		
	}

	public Color getSelectedColorRGB() {

		Color c = new Color(Color.HSBtoRGB((float) hue / 359f,
				(float) saturation / 255f, (float) brightness / 255f));
		// println(" RGB: " + c.getBlue() + "," + c.getGreen() + ","
		// + c.getRed());
		return c;
	}

	public void draw() {
//		if(parent.tabColorMixer.isVisible()==false)
//			return;
		if ((width != WIDE) || (height < HIGH)) {
			System.out.println("bad size " + width + " " + height);
			return;
		}
		int index = 0;
		for (int j = 0; j < 256; j++) {
			for (int i = 0; i < 256; i++) {
				g.pixels[index++] = color(hue, i, 255 - j);
			}
		}

		updatePixels();
		stroke((brightness > 100) ? 0 : 255);
		fill(0);
		strokeWeight(2);
		strokeCap(PROJECT);
//		if (firstRun) {
//			lastX = lastY = 20;
//			firstRun = false;
//		}
		line(lastX - 10, lastY, lastX - 5, lastY);
		line(lastX + 10, lastY, lastX + 5, lastY);
		line(lastX, lastY - 10, lastX, lastY - 5);
		line(lastX, lastY + 10, lastX, lastY + 5);
		noFill();
		//rect(lastX, lastY, 10, 10);
		strokeWeight(1);
	}

	public void mousePressed() {
		updateMouse();
	}

	public void mouseDragged() {
		updateMouse();
	}

	public void setColor(int s, int b) {
		if (s >= 0 && s <= 255 && b >= 0 && b <= 255) {
			lastX = s;
			lastY = b;
			brightness = height - lastY - 1;
		} else
			println("Illegal arguments to setColor():" + s + "," + b);
	}

	public void setColor(int h, int s, int b) {
		if (h >= 0 && h <= 359 && s >= 0 && s <= 255 && b >= 0 && b <= 255) {
			hue = h;
			lastX = s;
			lastY = b;
			brightness = height - lastY - 1;
		} else
			println("Illegal arguments to setColor():" + s + "," + b);
	}
	
	public void setHue(int hue) {
		this.hue = hue;
		redraw();
	}

	public void updateMouse() {
		if ((mouseX >= 0) && (mouseX < 256) && (mouseY >= 0) && (mouseY < 256)) {
			brightness = height - mouseY - 1;
			saturation = mouseX;			
			lastX = mouseX;
			lastY = mouseY;
			parent.setColorValueFromMixer(getSelectedColorRGB());
			//parent.txtH.setText(parent.hueSlider.getValue() + "");
			redraw();
		}
	}
}
