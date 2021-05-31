package com.alore.booking.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    private String email;
    private String gender;
    private Integer isDeleted=0;
    private String city;
    private Timestamp createdTs;
    private Timestamp lastUpdatedTs;
}
