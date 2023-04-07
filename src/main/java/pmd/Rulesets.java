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
    CONCURRENT("rulesets/java/ali-comment.xml"),
    CONSTANT("rulesets/java/ali-comment.xml"),
    EXCEPTION("rulesets/java/ali-comment.xml"),
    FLOWCONTROL("rulesets/java/ali-comment.xml"),
    NAMING("rulesets/java/ali-comment.xml"),
    OOP("rulesets/java/ali-comment.xml"),
    ORM("rulesets/java/ali-comment.xml"),
    OTHER("rulesets/java/ali-comment.xml"),
    SET("rulesets/java/ali-comment.xml");



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
