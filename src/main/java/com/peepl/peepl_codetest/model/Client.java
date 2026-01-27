package com.peepl.peepl_codetest.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "my_client")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "slug", nullable = false, length = 100)
    private String slug;

    @Column(name = "is_project", nullable = false, length = 30, columnDefinition = "varchar(30) default '0'")
    private String isProject = "0";

    @Column(name = "self_capture", nullable = false, length = 1, columnDefinition = "char(1) default '1'")
    private String selfCapture = "1";

    @Column(name = "client_prefix", nullable = false, length = 4)
    private String clientPrefix;

    @Column(name = "client_logo", nullable = false, length = 255, columnDefinition = "varchar(255) default 'no-image.jpg'")
    private String clientLogo = "no-image.jpg";

    @Column(name = "address", columnDefinition = "text")
    private String address;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
