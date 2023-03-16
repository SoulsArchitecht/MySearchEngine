package searchengine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(columnList = "path", unique = true))
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name = "site_id", nullable = false)
    @ManyToOne
    @JoinColumn(name ="user_id", nullable = false)
    private Site site;

    @Column(name = "path", nullable = false)
    private String path; //index in db

    @Column(name = "code", nullable = false)
    private int code;

    @Column(name = "content", nullable = false)
    private String content;
}
