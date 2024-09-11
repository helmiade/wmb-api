package com.enigmacamp.warung_makan_bahari_api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@jakarta.persistence.Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;

    @Column(name="trans_date")
    private LocalDateTime transDate;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<OrderDetail> orderDetails;

}
