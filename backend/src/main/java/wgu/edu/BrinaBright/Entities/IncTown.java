package wgu.edu.BrinaBright.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wgu.edu.BrinaBright.Repos.IncTownRepository;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mt_inc_towns")
public class IncTown {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "town_name")
    private String townName;

    @Column(name = "county")
    private String county;


}
