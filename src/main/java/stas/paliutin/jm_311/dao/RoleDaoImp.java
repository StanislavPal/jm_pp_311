package stas.paliutin.jm_311.dao;

import org.springframework.stereotype.Repository;
import stas.paliutin.jm_311.model.Role;

@Repository
public class RoleDaoImp extends AbstractJpaDao<Role> implements Dao<Role> {
    public RoleDaoImp() {
        setClazz(Role.class );
    }

    @Override
    public Role findOne(String role) {
        return entityManager.createQuery(
                "select r " +
                        "from Role r " +
                        "where r.name = :name", Role.class )
                .setParameter("name", role)
                .getSingleResult();
    }

    @Override
    public void deleteById( long entityId ){
        int id = entityManager.createQuery(
                "delete Role r where r.id = :id")
                .setParameter("id", entityId)
                .executeUpdate();
    }
}