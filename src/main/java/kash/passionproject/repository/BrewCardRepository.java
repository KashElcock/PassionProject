package kash.passionproject.repository;

import java.util.List;
import java.util.Optional;
import kash.passionproject.domain.BrewCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BrewCard entity.
 */
@Repository
public interface BrewCardRepository extends JpaRepository<BrewCard, Long> {
    @Query("select brewCard from BrewCard brewCard where brewCard.user.login = ?#{principal.username}")
    List<BrewCard> findByUserIsCurrentUser();

    //    @Query("SELECT * FROM BREW_CARD WHERE USER_ID = 2")
    //    List<BrewCard> findByUserIsCurrentUser();

    default Optional<BrewCard> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BrewCard> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BrewCard> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct brewCard from BrewCard brewCard left join fetch brewCard.user",
        countQuery = "select count(distinct brewCard) from BrewCard brewCard"
    )
    Page<BrewCard> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct brewCard from BrewCard brewCard left join fetch brewCard.user")
    List<BrewCard> findAllWithToOneRelationships();

    @Query("select brewCard from BrewCard brewCard left join fetch brewCard.user where brewCard.id =:id")
    Optional<BrewCard> findOneWithToOneRelationships(@Param("id") Long id);
}
