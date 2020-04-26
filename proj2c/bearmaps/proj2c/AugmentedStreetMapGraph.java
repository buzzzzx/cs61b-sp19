package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.MyTrieSet;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    Map<Point, Long> pointIdMap;
    Map<String, List<Node>> cleanedNameMapToListNode;
    KDTree kdTree;
    MyTrieSet trieSet;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        List<Point> points = new LinkedList<>();
        pointIdMap = new HashMap<>();
        cleanedNameMapToListNode = new HashMap<>();
        trieSet = new MyTrieSet();

        for (Node node : nodes) {
            // only consider nodes with neighbors
            if (!neighbors(node.id()).isEmpty()) {
                Point point = new Point(node.lon(), node.lat());
                pointIdMap.put(point, node.id());
                points.add(point);
            }

            // set up trieSet;
            if (node.name() != null) {
                String name = node.name();
                String cleanedName = cleanString(name);
                trieSet.add(cleanedName);
                if (!cleanedNameMapToListNode.containsKey(cleanedName)) {
                    cleanedNameMapToListNode.put(cleanedName, new LinkedList<>());
                }
                cleanedNameMapToListNode.get(cleanedName).add(node);
            }
        }

        kdTree = new KDTree(points);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point res = kdTree.nearest(lon, lat);
        return pointIdMap.get(res);
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String cleanedPrefix = cleanString(prefix);
        List<String> allCleaned = trieSet.keysWithPrefix(cleanedPrefix);
        Set<String> res = new HashSet<>();

        for (String cleaned : allCleaned) {
            for (Node node : cleanedNameMapToListNode.get(cleaned)) {
                res.add(node.name());
            }
        }

        return new LinkedList<>(res);
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> res = new LinkedList<>();

        List<Node> nodes = cleanedNameMapToListNode.get(cleanString(locationName));
        if (nodes != null) {
            for (Node node : nodes) {
                Map<String, Object> nodeInfo = new HashMap<>();
                nodeInfo.put("lat", node.lat());
                nodeInfo.put("lon", node.lon());
                nodeInfo.put("name", node.name());
                nodeInfo.put("id", node.id());
                res.add(nodeInfo);
            }
        }
        return res;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
