package com.trendyol.linkconverter.db.entity;

import com.trendyol.linkconverter.types.LinkType;
import lombok.Data;

import javax.persistence.*;

/**
 * LinksStorage which is Object-Relational Mapping of <b>links_storage</b> table.
 */
@Entity
@Table(name = "links_storage")
@Data
public class LinksStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true, length = 1024)
    private String link;

    @Column
    private Long relatedLinkId;

    @Column(nullable = false, unique = true)
    private String linkHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LinkType linkType;
}
