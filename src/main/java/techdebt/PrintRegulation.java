package techdebt;

import com.google.gson.Gson;


public class PrintRegulation {
    public int commentsCount;
    public int concurrentsCount;
    public int constantsCount;
    public int exceptionsCount;
    public int flowControlsCount;
    public int namingsCount;
    public int oopsCount;
    public int ormsCount;
    public int othersCount;
    public int setsCount;

    public PrintRegulation(){}

    public PrintRegulation(Regulation r){
        this.commentsCount = r.commentOriginData.size();
        this.concurrentsCount = r.concurrentOriginData.size();
        this.constantsCount = r.constantOriginData.size();
        this.exceptionsCount = r.exceptionOriginData.size();
        this.flowControlsCount = r.flowControlOriginData.size();
        this.namingsCount = r.namingOriginData.size();
        this.oopsCount = r.oopOriginData.size();
        this.ormsCount = r.ormOriginData.size();
        this.othersCount = r.otherOriginData.size();
        this.setsCount = r.setOriginData.size();
    }

    

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }


}