package com.zylex.votingsystem.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "restaurant", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "restaurant_unique_name_idx")
})
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    private Set<Menu> menus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    private Set<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(Restaurant r) {
        this(r.getId(), r.getName());
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        if (menus.isEmpty()) {
            this.menus = Collections.emptySet();
        }
        this.menus = menus;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        if (votes.isEmpty()) {
            this.votes = Collections.emptySet();
        }
        this.votes = votes;
    }
}
