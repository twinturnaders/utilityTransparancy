package wgu.edu.BrinaBright.DTOs;

public record MunicipalityOption(
        Long id,
        String name,
        String county,
        String state,
        double distanceMiles
) {
    public static MunicipalityOption from(wgu.edu.BrinaBright.Repos.MunicipalityNearProjection p) {
        return new MunicipalityOption(
                p.getId(), p.getName(), p.getCounty(), p.getState(),
                p.getMeters() != null ? p.getMeters() / 1609.344 : Double.NaN
        );
    }
}