package contest_2008.qualification;

import abtric.utility.Solution;

/**
 * Fly Swatter
 * 
 * @author Ferry Abt
 *
 */
public class C extends Solution {

	@Override
	protected String solveCase(Case c) {
		String[] input = c.lines.get(0).split(" ");
		double f = Double.parseDouble(input[0]); // radius of fly
		double R = Double.parseDouble(input[1]); // outer radius
		double t = Double.parseDouble(input[2]); // ring thickness
		double r = Double.parseDouble(input[3]); // radius of string
		double g = Double.parseDouble(input[4]); // gap between strings

		// special case: fly too big -> 1.0
		if (g < 2 * f) {
			return "1.000000";
		}

		double solution = 0.0;
		double innerRadius = R - t - f;
		double innerRadiusSquared = Math.pow(innerRadius, 2);
		for (double x = r + f; x < innerRadius; x += g + r * 2) {
			double rightX = x + g - 2 * f;
			double rightXSquared = Math.pow(rightX, 2);
			double xSquared = Math.pow(x, 2);
			double y2 = Math.sqrt(innerRadiusSquared - xSquared);
			for (double y = r + f; y < innerRadius; y += g + r * 2) {
				double rightY = y + g - 2 * f;
				double rightYSquared = Math.pow(rightY, 2);
				if (Math.sqrt(rightXSquared + rightYSquared) < innerRadius) {
					solution += 4 * Math.pow(g - 2 * f, 2);
				} else {
					double ySquared = Math.pow(y, 2);
					double lowerRightCorner = Math.sqrt(rightXSquared + ySquared);
					double upperLeftCorner = Math.sqrt(xSquared + rightYSquared);
					double x1 = Math.sqrt(innerRadiusSquared - rightYSquared);
					double x2 = Math.sqrt(innerRadiusSquared - ySquared);
					double y1 = Math.sqrt(innerRadiusSquared - rightXSquared);
					double triangle1 = 0;
					double triangle2 = 0;
					double triangle3 = 0;
					double circularArea = 0;
					if (lowerRightCorner < innerRadius && upperLeftCorner < innerRadius) {
						triangle1 = ((rightY - y) * (rightX - x)) / 2;
						triangle2 = ((rightY - y) * (x1 - x)) / 2;
						triangle3 = ((y1 - y) * (rightX - x1)) / 2;
						circularArea = getCircularArea(innerRadius, rightY - y1, rightX - x1);
						// missing corner
					} else if (lowerRightCorner < innerRadius) {
						// missing top
						triangle1 = ((y2 - y) * ((rightX) - x)) / 2;
						triangle2 = ((y1 - y) * ((rightX) - x)) / 2;
						circularArea = getCircularArea(innerRadius, y2 - y1, g - 2 * f);
					} else if (upperLeftCorner < innerRadius) {
						// missing side
						triangle1 = ((x2 - x) * ((rightY) - y)) / 2;
						triangle2 = ((x1 - x) * ((rightY) - y)) / 2;
						circularArea = getCircularArea(innerRadius, x2 - x1, g - 2 * f);
					} else if (Math.sqrt(xSquared + ySquared) < innerRadius) {
						// only corner
						triangle1 = ((x2 - x) * (y2 - y)) / 2;
						circularArea = getCircularArea(innerRadius, x2 - x, y2 - y);
					}
					solution += 4 * (triangle1 + triangle2 + triangle3 + circularArea);
				}
			}
		}
		solution = 1.0 - (solution / (Math.PI * Math.pow(R, 2)));
		return String.format("%06f", solution);
	}

	private double getCircularArea(double innerRadius, double x, double y) {
		double phi = Math.asin(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * innerRadius)) * 2;
		return (Math.pow(innerRadius, 2) / 2) * (phi - Math.sin(phi));
	}
}
