package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.entity.Role;

public interface RoleService {
    Role getOrSave(Role role);
}
