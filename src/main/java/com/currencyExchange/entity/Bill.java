package com.currencyExchange.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "bill_name")
    private String billName;

    @Column(name = "bill_value")
    private BigDecimal billValue;

    @Column(name = "createdAt")
    @CreationTimestamp
    private Timestamp createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @OneToMany(mappedBy = "bill")
    @Fetch(FetchMode.SUBSELECT)
    private List<BillChange> billChange = new ArrayList<>();

    public enum StatusType {
        SUCCESS, FAIL
    }
}
