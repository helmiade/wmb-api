package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.entity.Role;
import com.enigmacamp.warung_makan_bahari_api.repository.RoleRepository;
import com.enigmacamp.warung_makan_bahari_api.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(Role role) {
        Optional<Role> roleOptional = roleRepository.findByName(role.getName());
        if (!roleOptional.isEmpty()) {
            return roleOptional.get();
        }
        return roleRepository.save(role);
    }
}
