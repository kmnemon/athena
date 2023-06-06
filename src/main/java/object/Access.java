package object;

public enum Access {
    PUBLIC("public"), PRIVATE("private"), PROTECTED("protected"), NONE("");

    private String codeRepresenation;

    Access(String codeRepresentation) {
        this.codeRepresenation = codeRepresentation;
    }

    public String asString() {
        return this.codeRepresenation;
    }

}

