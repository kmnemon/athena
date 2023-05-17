package techdebt;

import com.google.gson.GsonBuilder;

import java.util.Objects;

public class Duplication {
    String file1;
    String file2;

    public Duplication(String file1, String file2) {
        this.file1 = file1;
        this.file2 = file2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Duplication d)) {
            return false;
        }

        return d.file1.equals(this.file1)
                && d.file2.equals(this.file2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file1, file2);
    }

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
