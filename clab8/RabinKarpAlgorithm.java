public class RabinKarpAlgorithm {


    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        int lengthPattern = pattern.length();
        int lenghtInput = input.length();
        if (lengthPattern > lenghtInput) {
            return -1;
        }

        RollingString rsPattern = new RollingString(pattern, lengthPattern);
        int hpattern = rsPattern.hashCode();

        int i;
        StringBuilder strBuilder = new StringBuilder();
        for (i = 0; i < lengthPattern; i += 1) {
            strBuilder.append(input.charAt(i));
        }
        RollingString subS = new RollingString(strBuilder.toString(), lengthPattern);
        int hsub = subS.hashCode();

        for (i = 0; i < lenghtInput - lengthPattern + 1; i += 1) {
            if (hsub == hpattern) {
                if (subS.equals(rsPattern)) {
                    return i;
                }
            }

            if (i + lengthPattern < lenghtInput) {
                subS.addChar(input.charAt(i + lengthPattern));
                hsub = subS.hashCode();
            }
        }

        return -1;
    }

}
