package com.techup.travel_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "trips")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // PostgreSQL TEXT[] → ใช้ @Column(columnDefinition = "TEXT[]")
    @Column(name = "photos", columnDefinition = "TEXT[]")
    private List<String> photos;

    @Column(name = "tags", columnDefinition = "TEXT[]")
    private List<String> tags;

    @Column(columnDefinition = "DOUBLE PRECISION")
    private Double latitude;

    @Column(columnDefinition = "DOUBLE PRECISION")
    private Double longitude;

    // author_id BIGINT REFERENCES users(id) ON DELETE SET NULL
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import jakarta.persistence.Column;

// @Entity
// @Table(name = "notes")
// public class Note {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(nullable = false)
//     private String title;

//     @Column(columnDefinition = "TEXT")
//     private String content;

//     public Note() {}

//     public Note(Long id, String title, String content) {
//         this.id = id;
//         this.title = title;
//         this.content = content;
//     }

//     public Long getId() { return id;}
//     public void setId(Long id) { this.id = id; }

//     public String getTitle() { return title; }
//     public void setTitle(String title) { this.title = title; }

//     public String getContent() { return content; }
//     public void setContent(String content) { this.content = content;}
// }

