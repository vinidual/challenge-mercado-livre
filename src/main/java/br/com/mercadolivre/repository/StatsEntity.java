package br.com.mercadolivre.repository;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "stats_dna")
public class StatsEntity {

    @Id
    @Column(insertable = false)
    private Integer id;

    @Column(insertable = false, nullable = false)
    private Long countMutantDna;

    @Column(insertable = false, nullable = false)
    private Long countHumanDna;

    @Column(insertable = false, nullable = false)
    private BigDecimal ratio;

    @CreationTimestamp
    private Timestamp creationDate;

    @UpdateTimestamp
    private Timestamp updateDate;

}
