package searchengine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sites")
public class Site implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "status_time", nullable = false)
    private LocalDateTime statusTime;

    @Column(name = "last_error")
    private String lastError;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "site", cascade = CascadeType.ALL)
    private List<Page> pageList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "site", cascade = CascadeType.ALL)
    private List<Lemma> lemmaList = new ArrayList<>();


}
