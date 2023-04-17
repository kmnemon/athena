package pmd;

public enum Rulesets {
    //pmd
    DESIGN("category/java/design.xml"),
    MULTITHREADING("category/java/multithreading.xml"),
    PERFORMANCE("category/java/performance.xml"),
    CYCLOMATIC("category/java/design.xml/CyclomaticComplexity"),

    //p3c
    COMMENT("rulesets/java/ali-comment.xml"),
    CONSTANT("rulesets/java/ali-constant.xml"),
    EXCEPTION("rulesets/java/ali-exception.xml"),
    FLOWCONTROL("rulesets/java/ali-flowcontrol.xml"),
    NAMING("rulesets/java/ali-naming.xml"),
    OOP("rulesets/java/ali-oop.xml"),
    OTHER("rulesets/java/ali-other.xml"),
    SET("rulesets/java/ali-set.xml");



    private final String text;

    Rulesets(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
