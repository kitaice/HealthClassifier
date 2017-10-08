public class DecisionTreeNode {
    //If leaf, label to return
    int class_label;
    //Attribute split label
    String attribute;
    //Threshold that attributes are split on
    public double threshold;
    //Left child, can directly access and update, <= threshold
    public DecisionTreeNode left = null;
    //Right child, can directly access and update, > threshold
    public DecisionTreeNode right = null;

    DecisionTreeNode(int class_label, String attribute, double threshold) {
        this.class_label = class_label;
        this.attribute = attribute;
        this.threshold = threshold;
    }
    
    public boolean isLeaf(){
        return this.left == null && this.right == null;
    }
}
