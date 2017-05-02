import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private LineSegment lineSegments[];
    private int size;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("points is null");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException("point is null");
            }
        }

        if (points.length < 4) {
            size = 0;
            lineSegments = null;
            return;
        }

        LineSegment[] tmp = new LineSegment[points.length * (points.length-3) ];
        size = 0;

        Point[] tmpPoints = new Point[points.length];

        for (int i = 0; i<points.length; i++) {
            tmpPoints[0] = points[i];
            int cursor = i+1;
            for (int j = 1; j<tmpPoints.length; j++) {
                tmpPoints[j] = points[(cursor++) % tmpPoints.length];
            }
            Arrays.sort(tmpPoints, 1, tmpPoints.length, tmpPoints[0].slopeOrder());

            double slope = tmpPoints[0].slopeTo(tmpPoints[1]);
            int numberOfAdjacentPoints = 1;
            for (int j = 2; j<tmpPoints.length; j++) {
                if (tmpPoints[0].slopeTo(tmpPoints[j]) == slope) {
                    numberOfAdjacentPoints ++;
                    if ((j+1) == tmpPoints.length && numberOfAdjacentPoints >= 3) {
                        // found line segment
                        // Store the min => max point
                        Point[] result = new Point[numberOfAdjacentPoints + 1];
                        result[0] = tmpPoints[0];
                        for (int k=1; k<result.length; k++) {
                            numberOfAdjacentPoints--;
                            result[k] = tmpPoints[j - numberOfAdjacentPoints];
                        }

                        java.util.Arrays.sort(result);

                        tmp[size++] = new LineSegment(result[0], result[result.length-1]);
                    }
                } else {
                    if (numberOfAdjacentPoints >= 3) {
                        // found line segment
                        // Store the min => max point
                        Point[] result = new Point[numberOfAdjacentPoints + 1];
                        result[0] = tmpPoints[0];
                        for (int k=1; k<result.length; k++) {
                            result[k] = tmpPoints[j - numberOfAdjacentPoints];
                            numberOfAdjacentPoints--;
                        }

                        java.util.Arrays.sort(result);

                        tmp[size++] = new LineSegment(result[0], result[result.length-1]);
                    } else {
                        numberOfAdjacentPoints = 1; // reset
                        slope = tmpPoints[0].slopeTo((tmpPoints[j]));
                    }
                }
            }
        }

        Arrays.sort(tmp, 0, size, new Comparator<LineSegment>() {
            @Override
            public int compare(LineSegment l1, LineSegment l2) {
                return l1.toString().compareTo(l2.toString());
            }
        });

        String currentLineSegment = "";
        int uniqueCount = 0;
        int uniqueIndex[] = new int[size];
        for (int i=0; i<size; i++) {
            if (!currentLineSegment.equals(tmp[i].toString())) {
                uniqueIndex[uniqueCount++] = i;
                currentLineSegment = tmp[i].toString();
            }
        }

        lineSegments = new LineSegment[uniqueCount];
        for (int i = 0; i < uniqueCount; i++) {
            lineSegments[i] = tmp[uniqueIndex[i]];
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments == null ? 0 : lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments;
    }
}