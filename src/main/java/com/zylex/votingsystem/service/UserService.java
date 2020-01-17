package com.zylex.votingsystem.service;

import com.zylex.votingsystem.AuthorizedUser;
import com.zylex.votingsystem.model.User;
import com.zylex.votingsystem.repository.UserRepository;
import com.zylex.votingsystem.to.UserTo;
import com.zylex.votingsystem.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.zylex.votingsystem.util.UserUtil.prepareToSave;
import static com.zylex.votingsystem.util.ValidationUtil.checkNotFound;
import static com.zylex.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @CacheEvict(value = "user", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "User must be not null");
        return repository.save(prepareToSave(user, passwordEncoder));
    }

    @CacheEvict(value = "user", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "Email must be not null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public User getWithVotes(int id) {
        return checkNotFoundWithId(repository.findByIdWithVotes(id), id);
    }

    @Cacheable("user")
    public List<User> getAll(Sort sort) {
        return repository.findAll(sort);
    }

    @CacheEvict(value = "user", allEntries = true)
    public void update(User user) {
        Assert.notNull(user, "User must be not null");
        repository.save(prepareToSave(user, passwordEncoder));
    }

    @CacheEvict(value = "user", allEntries = true)
    @Transactional
    public void update(UserTo userTo) {
        Assert.notNull(userTo, "UserTo must be not null");
        User user = get(userTo.getId());
        User updated = UserUtil.updateFromTo(user, userTo);
        repository.save(prepareToSave(updated, passwordEncoder));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s is not found", email));
        }
        return new AuthorizedUser(user);
    }

    @CacheEvict(value = "user", allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled) {
        User user = get(id);
        user.setEnabled(enabled);
    }
}
