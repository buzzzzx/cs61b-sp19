import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {

    private List<Bear> sortedBears;
    private List<Bed> sortedBeds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {

        Pair<List<Bear>, List<Bed>> res = quickSort(bears, beds);
        sortedBears = res.first();
        sortedBeds = res.second();

    }


    /**
     * Returns a list that catenate two given list.
     */
    private <Item extends Comparable> List<Item> catenate(List<Item> q1, List<Item> q2) {
        List<Item> catenated = new ArrayList<>();
        for (Item item : q1) {
            catenated.add(item);
        }
        for (Item item : q2) {
            catenated.add(item);
        }
        return catenated;
    }

    /**
     * Partition bears into three parts: less than pivot, equal to pivot, greater than pivot.
     */
    private void partitionBear(List<Bear> bears, Bed pivot,
                               List<Bear> less, List<Bear> equal, List<Bear> greater) {
        for (Bear item : bears) {
            int cmp = item.compareTo(pivot);
            if (cmp == 0) {
                equal.add(item);
            } else if (cmp < 0) {
                less.add(item);
            } else {
                greater.add(item);
            }
        }
    }

    /**
     * Partition beds into three parts: less than pivot, equal to pivot, greater than pivot.
     */
    private void partitionBed(List<Bed> beds, Bear pivot,
                              List<Bed> less, List<Bed> equal, List<Bed> greater) {
        for (Bed item : beds) {
            int cmp = item.compareTo(pivot);
            if (cmp == 0) {
                equal.add(item);
            } else if (cmp < 0) {
                less.add(item);
            } else {
                greater.add(item);
            }
        }
    }

    /**
     * Helper method that sort list with order that from smallest to largest by quickSort.
     */
    private Pair<List<Bear>, List<Bed>> quickSort(List<Bear> bears, List<Bed> beds) {
        if (bears.size() < 2 || beds.size() < 2) {
            return new Pair<>(bears, beds);
        }

        Bed pivotBed = beds.get(0);

        List<Bear> lessBear = new ArrayList<>();
        List<Bear> equalBear = new ArrayList<>();
        List<Bear> greaterBear = new ArrayList<>();
        partitionBear(bears, pivotBed, lessBear, equalBear, greaterBear);

        Bear pivotBear = equalBear.get(0);
        List<Bed> lessBed = new ArrayList<>();
        List<Bed> equalBed = new ArrayList<>();
        List<Bed> greaterBed = new ArrayList<>();
        partitionBed(beds, pivotBear, lessBed, equalBed, greaterBed);

        Pair<List<Bear>, List<Bed>> less =  quickSort(lessBear, lessBed);
        Pair<List<Bear>, List<Bed>> greater =  quickSort(greaterBear, greaterBed);

        bears = catenate(catenate(less.first(), equalBear), greater.first());
        beds = catenate(catenate(less.second(), equalBed), greater.second());
        return new Pair<>(bears, beds);
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return sortedBears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return sortedBeds;
    }
}
