package techdebt;

import com.google.gson.Gson;

public class PrintDesign {
    public int designsCount;
    public int multithreadingsCount;
    public int performancesCount;
    public int errorPronesCount;

    public PrintDesign(){}

    public PrintDesign(Design d){
        this.designsCount = d.designOriginData.size();
        this.multithreadingsCount = d.multithreadingOriginData.size();
        this.performancesCount = d.performanceOriginData.size();
        this.errorPronesCount = d.errorProneOriginData.size();
    }

    

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }


}