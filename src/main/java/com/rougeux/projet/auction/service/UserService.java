package com.rougeux.projet.auction.service;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.api.ApiResponseFactory;
import com.rougeux.projet.auction.bo.User;
import com.rougeux.projet.auction.dal.IUserDao;
import com.rougeux.projet.auction.dto.user.UserDto;
import com.rougeux.projet.auction.mapper.entity.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rougeux.projet.auction.service.utils.CodeService.CD_DATA_SUCCESS;
import static com.rougeux.projet.auction.service.utils.CodeService.CD_ERR_NOTFOUND;

@Service
public class UserService {

    private final IUserDao userDao;

    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }

    public ApiResponse<List<UserDto>> getAllUsers() {
        return ApiResponseFactory.success(CD_DATA_SUCCESS, "users.findAll.success",
                userDao.findAll().stream().map(UserMapper::toDto).toList());
    }

    public ApiResponse<Object> getUser(String slug) {
        User user = userDao.findBySlug(slug);

        if(user == null){
            return ApiResponseFactory.error(CD_ERR_NOTFOUND, "users.findBy.error.notFound");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isCurrentUser = auth.getName().equals(user.getUsername());
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Object dto = (isCurrentUser || isAdmin)
                ? UserMapper.toDto(user)
                : UserMapper.toDtoSummary(user);

        return ApiResponseFactory.success(CD_DATA_SUCCESS, "users.findBy.success", dto);
    }

    public User getEntityByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public void save(User user) {
        userDao.save(user);
    }
}
