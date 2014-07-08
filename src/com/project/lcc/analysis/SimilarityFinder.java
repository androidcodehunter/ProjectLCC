package com.project.lcc.analysis;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Color;

public class SimilarityFinder {
	private Bitmap croppedImage;
	private Bitmap inputImage;
	int row;
	int column;

	static final public double Y0 = 100;
	static final public double gamma = 3;
	static final public double Al = 1.4456;
	static final public double Ach_inc = 0.16;

	public SimilarityFinder(Bitmap croppedImage, Bitmap inputImage)
			throws IOException {
		this.croppedImage = croppedImage;
		this.inputImage = inputImage;
		row = inputImage.getWidth() > croppedImage.getWidth() ? croppedImage
				.getWidth() : inputImage.getWidth();
		column = inputImage.getHeight() > croppedImage.getHeight() ? croppedImage
				.getHeight() : inputImage.getHeight();
	}

	public int calculateDistance() {
		int distance = comparator();
		return distance;
	}

	public int comparator() {
		int distacne = 0;

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				int pixel1 = croppedImage.getPixel(i, j);
				int r1 = Color.red(pixel1);
				int g1 = Color.green(pixel1);
				int b1 = Color.blue(pixel1);

				int pixel2 = inputImage.getPixel(i, j);
				int r2 = Color.red(pixel2);
				int g2 = Color.green(pixel2);
				int b2 = Color.blue(pixel2);

				int tempDistacne = (int) distance_hcl(rgb2hcl(r1, g1, b1),
						rgb2hcl(r2, g2, b2));
				distacne += tempDistacne;
			}
		}

		return distacne;
	}

	public double[] rgb2hcl(int r, int g, int b) {

		double min = Math.min(Math.min(r, g), b);
		double max = Math.max(Math.max(r, g), b);

		double[] returnarray = new double[3];

		if (max == 0) {
			returnarray[0] = 0;
			returnarray[1] = 0;
			returnarray[2] = 0;
			return returnarray;
		}

		double alpha = (min / max) / Y0;
		double Q = Math.exp(alpha * gamma);
		double rg = r - g;
		double gb = g - b;
		double br = b - r;
		double L = ((Q * max) + ((1 - Q) * min)) / 2;
		double C = Q * (Math.abs(rg) + Math.abs(gb) + Math.abs(br)) / 3;
		double H = Math.toDegrees(Math.atan2(gb, rg));

		if (rg < 0) {
			if (gb >= 0)
				H = 90 + H;
			else {
				H = H - 90;
			}
		}

		returnarray[0] = H;
		returnarray[1] = C;
		returnarray[2] = L;

		return returnarray;
	}

	public double distance_hcl(double[] hcl1, double[] hcl2) {
		double c1 = hcl1[1];
		double c2 = hcl2[1];
		
		double Dh = Math.abs(hcl1[0] - hcl2[0]);

		if (Dh > 180)
			Dh = 360 - Dh;

		double AlDl = Al * Math.abs(hcl1[2] - hcl2[2]);
		return Math.sqrt(AlDl
				* AlDl
				+ (c1 * c1 + c2 * c2 - 2 * c1 * c2
						* Math.cos(Math.toRadians(Dh))));
	}


}
