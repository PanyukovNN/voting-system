package com.zylex.votingsystem.web.vote;

import com.zylex.votingsystem.AuthorizedUser;
import com.zylex.votingsystem.model.Vote;
import com.zylex.votingsystem.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static com.zylex.votingsystem.web.SecurityUtil.authUserId;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class VoteRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteService service;

    @Autowired
    public VoteRestController(VoteService service) {
        this.service = service;
    }

    @GetMapping(value = "/rest/admin/vote", produces = APPLICATION_JSON_VALUE)
    public List<Vote> getAll() {
        log.info("get all votes");
        return service.getAll(Sort.by(Sort.Direction.ASC, "date"));
    }

    @GetMapping(value = "/rest/admin/vote/{id}", produces = APPLICATION_JSON_VALUE)
    public Vote get(@PathVariable Integer id) {
        log.info("get vote with id={}", id);
        return service.get(id);
    }

    @RequestMapping(value = "/rest/vote/{restaurantId}", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<Vote> vote(@AuthenticationPrincipal AuthorizedUser authUser, @PathVariable int restaurantId) {
        int userId = authUserId();
        log.info("user with id={} vote for restaurant with id={}", userId, restaurantId);

        Vote created = service.vote(userId, restaurantId, LocalDateTime.now().toLocalDate(), LocalDateTime.now().toLocalTime());
        URI newResourceUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(newResourceUri).body(created);
    }

    @DeleteMapping(value = "/rest/admin/vote/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        log.info("delete vote with id={}", id);
        service.delete(id);
    }
}
