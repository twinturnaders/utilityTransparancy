package wgu.edu.BrinaBright.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wgu.edu.BrinaBright.Entities.Municipality;

import java.util.List;
import java.util.Optional;

public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
    @Query(value = """
        SELECT m.id         AS id,
               m.name       AS name,
               m.county     AS county,
               m.state      AS state,
               ST_Distance(CAST(m.zip_center AS geography), CAST(z.zip_center AS geography)) AS meters
        FROM municipalities m
        JOIN zip_codes z ON z.zip_code = :zip
        WHERE ST_DWithin(CAST(m.zip_center AS geography), CAST(z.zip_center AS geography), :radius)
        ORDER BY meters ASC
        LIMIT :limit
        """, nativeQuery = true)
    List<MunicipalityNearProjection> findNearZip(
            @Param("zip") String zip,
            @Param("radius") double radius,
            @Param("limit") int limit
    );

    // Nearest (no radius); you can still reject if too far in service/controller
    @Query(value = """
        SELECT m.id         AS id,
               m.name       AS name,
               m.county     AS county,
               m.state      AS state,
               ST_Distance(CAST(m.zip_center AS geography), CAST(z.zip_center AS geography)) AS meters
        FROM municipalities m
        JOIN zip_codes z ON z.zip_code = :zip
        ORDER BY meters ASC
        LIMIT 1
        """, nativeQuery = true)
    List<MunicipalityNearProjection> findNearestByZip(@Param("zip") String zip);


    //query by distance(user configured)
    @Query(value = """
    SELECT m.*
    FROM municipalities m
    JOIN zip_codes z ON ST_DWithin(
         CAST(m.zip_center AS geography),
         CAST(z.zip_center AS geography),
         :radius
     )
    WHERE z.zip_code = :zip
""", nativeQuery = true)
    List<Municipality> findMunicipalitiesNearZip(
            @Param("zip") String zip,
            @Param("radius") double radiusMeters
    );
//Order by distance:

    @Query(value = """
    SELECT m.*
    FROM municipalities m
    JOIN zip_codes z ON z.zip_code = :zip
    WHERE ST_DWithin(
        CAST(m.zip_center AS geography),
        CAST(z.zip_center AS geography),
        :radius
    )
    ORDER BY CAST(m.zip_center AS geography) <-> CAST(z.zip_center AS geography)
""", nativeQuery = true)
    List<Municipality> findMunicipalitiesNearZipOrderedByDistance(
            @Param("zip") String zip,
            @Param("radius") double radiusMeters
    );

    List<Municipality> findTop10ByNameContainingIgnoreCaseOrderByNameAsc(String q);
        @Query(value = """
        SELECT m.id, ST_Distance(
            CAST(m.zip_center AS geography),
            CAST(z.zip_center AS geography)
        ) AS distance
        FROM municipalities m
        JOIN zip_codes z ON z.zip_code = :zip
        WHERE ST_DWithin(
            CAST(m.zip_center AS geography),
            CAST(z.zip_center AS geography),
            :radius
        )
        ORDER BY distance ASC
        """, nativeQuery = true)
        List<Object[]> findMunicipalityIdsAndDistance(@Param("zip") String zip, @Param("radius") double radiusMeters);
    }


