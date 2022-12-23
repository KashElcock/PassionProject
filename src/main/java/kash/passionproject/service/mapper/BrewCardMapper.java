package kash.passionproject.service.mapper;

import kash.passionproject.domain.BrewCard;
import kash.passionproject.domain.User;
import kash.passionproject.service.dto.BrewCardDTO;
import kash.passionproject.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BrewCard} and its DTO {@link BrewCardDTO}.
 */
@Mapper(componentModel = "spring")
public interface BrewCardMapper extends EntityMapper<BrewCardDTO, BrewCard> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    BrewCardDTO toDto(BrewCard s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
