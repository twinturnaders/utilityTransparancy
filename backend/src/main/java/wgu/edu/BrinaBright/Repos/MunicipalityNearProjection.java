package wgu.edu.BrinaBright.Repos;

public interface MunicipalityNearProjection {
    Long getId();
    String getName();
    String getCounty();
    String getState();
    Double getMeters(); // distance in meters
}