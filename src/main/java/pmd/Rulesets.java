package pmd;

public enum Rulesets {
    //pmd
    DESIGN("category/java/design.xml"),
    MULTITHREADING("category/java/multithreading.xml"),
    PERFORMANCE("category/java/performance.xml"),
    ERROR_PRONE("category/java/errorprone.xml"),
    CYCLOMATIC("category/java/design.xml/CyclomaticComplexity"),

    //p3c
    COMMENT("rulesets/java/ali-comment.xml"),
    CONCURRENT("rulesets/java/ali-concurrent.xml"),
    CONSTANT("rulesets/java/ali-constant.xml"),
    EXCEPTION("rulesets/java/ali-exception.xml"),
    FLOWCONTROL("rulesets/java/ali-flowcontrol.xml"),
    NAMING("rulesets/java/ali-naming.xml"),
    OOP("rulesets/java/ali-oop.xml"),
    ORM("rulesets/java/ali-orm.xml"),
    OTHER("rulesets/java/ali-other.xml"),
    SET("rulesets/java/ali-set.xml");



    private final String text;

    Rulesets(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
