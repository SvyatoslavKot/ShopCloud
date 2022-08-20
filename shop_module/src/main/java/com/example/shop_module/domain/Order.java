package com.example.shop_module.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime update;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal sum;
    private String address;

    @OneToMany
    private List<OrderDetails> orders_details;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", created=" + created +
                ", update=" + update +
                ", user=" + user +
                ", sum=" + sum +
                ", address='" + address + '\'' +
                ", orders_details=" + orders_details +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(created, order.created) && Objects.equals(update, order.update) && Objects.equals(user, order.user) && Objects.equals(sum, order.sum) && Objects.equals(address, order.address) && Objects.equals(orders_details, order.orders_details) && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, update, user, sum, address, orders_details, status);
    }
}

