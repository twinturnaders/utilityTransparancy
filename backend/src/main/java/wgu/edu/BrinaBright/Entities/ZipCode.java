package wgu.edu.BrinaBright.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "zip_codes")
@Data
@NoArgsConstructor
public class ZipCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "county_name")
    private String countyName;

    @Column( name = "zip_center")
    private Geometry zipCenter;


}