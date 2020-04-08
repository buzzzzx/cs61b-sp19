/**
 * Created by hug.
 */
public class ExperimentHelper {

    /** Returns the internal path length for an optimum binary search tree of
     *  size N. Examples:
     *  N = 1, OIPL: 0
     *  N = 2, OIPL: 1
     *  N = 3, OIPL: 2
     *  N = 4, OIPL: 4
     *  N = 5, OIPL: 6
     *  N = 6, OIPL: 8
     *  N = 7, OIPL: 10
     *  N = 8, OIPL: 13
     */
    public static int optimalIPL(int N) {
        return (int) (optimalAverageDepth(N) * N);
    }

    /** Returns the average depth for nodes in an optimal BST of
     *  size N.
     *  Examples:
     *  N = 1, OAD: 0
     *  N = 5, OAD: 1.2
     *  N = 8, OAD: 1.625
     * @return
     */
    public static double optimalAverageDepth(int N) {
        int height = (int) (Math.log(N) / Math.log(2));
        double sum = 0;
        int countNodes = 0;
        int d;
        for (d = 0; d < height; d += 1) {
            sum += d * Math.pow(2, d);
            countNodes += Math.pow(2, d);
        }

        sum += height * (N - countNodes);
        double res = sum / N;
        return res;
    }

    public static void main(String[] args) {
        System.out.println((int) (Math.log(9) / Math.log(2)));
        System.out.println(optimalAverageDepth(14));
        System.out.println(optimalIPL(4));
    }
}
