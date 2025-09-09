package wgu.edu.BrinaBright.DTOs;



import wgu.edu.BrinaBright.Entities.Municipality;

public record MunicipalityLiteDTO(Long id, String name, String state, String county, org.locationtech.jts.geom.Geometry zipCenter) {
    public static MunicipalityLiteDTO from(Municipality m) {
        return new MunicipalityLiteDTO(m.getId(), m.getName(), m.getState(), m.getCounty(), m.getZipCenter());
    }
}
