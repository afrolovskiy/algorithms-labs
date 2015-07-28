public class Brute {
    public static void main(String[] args) {
        // read and draw points
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            points[i] = p;
            p.draw();
        }

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        Merge.sort(points);

        double slope1, slope2, slope3;
        for (int i = 0; i < N - 3; i++) {
            for (int j = i + 1; j < N - 2; j++) {
                for (int k = j + 1; k < N - 1; k++) {
                    for (int l = k + 1; l < N; l++) {
                        slope1 = points[i].slopeTo(points[j]);
                        slope2 = points[i].slopeTo(points[k]);
                        if (slope1 != slope2) {
                            continue;
                        }

                        slope3 = points[i].slopeTo(points[l]);
                        if (slope1 != slope3) {
                            continue;
                        }

                        Point[] res = new Point[]{points[i], points[j], points[k], points[l]};
                        Insertion.sort(res);
                        StdOut.printf("" + res[0] + "->" + res[1] + "->" + res[2] + "->" + res[3] + "\n");
                        res[0].drawTo(res[3]);
                    }
                }
            }
        }
    }
}
