package techdebt;

import com.google.gson.Gson;

public class DesignStatistics {
    public int designsCount;
    public int multithreadingsCount;
    public int performancesCount;

    public DesignStatistics(Design d){
        this.designsCount = d.designOriginData.size();
        this.multithreadingsCount = d.multithreadingOriginData.size();
        this.performancesCount = d.performanceOriginData.size();
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }

    public DesignStatistics() {
    }
}