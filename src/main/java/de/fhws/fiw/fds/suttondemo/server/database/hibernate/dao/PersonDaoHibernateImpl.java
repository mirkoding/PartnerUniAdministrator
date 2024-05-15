package de.fhws.fiw.fds.suttondemo.server.database.hibernate.dao;

import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.suttondemo.server.database.hibernate.models.PersonDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PersonDaoHibernateImpl implements PersonDaoHibernate {

    public PersonDaoHibernateImpl() {
        super();
    }

    @Override
    public NoContentResult create(PersonDB model) {
        final EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(model);
            transaction.commit();

            return new NoContentResult();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return errorNocontentResult();
        } finally {
            em.close();
        }
    }

    @Override
    public SingleModelHibernateResult<PersonDB> readById(long id) {

        try (EntityManager em = JpaUtils.getEntityManager()) {
            final PersonDB result = em.find(PersonDB.class, id);
            return new SingleModelHibernateResult<>(result);
        } catch (Exception e) {
            return errorSingleResult();
        }
    }

    @Override
    public CollectionModelHibernateResult<PersonDB> readAll(SearchParameter searchParameter) {

        try (EntityManager em = JpaUtils.getEntityManager()) {
            final var cb = em.getCriteriaBuilder();
            final var cq = cb.createQuery(PersonDB.class);
            final var rootEntry = cq.from(PersonDB.class);

            final var all = cq.select(rootEntry).where();
            final var allQuery = em.createQuery(all);

            final var result = allQuery
                    .setFirstResult(searchParameter.getOffset())
                    .setMaxResults(searchParameter.getSize())
                    .getResultList();

            return new CollectionModelHibernateResult<>(result);
        } catch (Exception e) {
            return errorCollectionResult();
        }
    }

    @Override
    public CollectionModelHibernateResult<PersonDB> readByFirstNameAndLastName(String firstName, String lastName,
                                                                               SearchParameter searchParameter) {
        try (EntityManager em = JpaUtils.getEntityManager()) {
            var result = filterByFirstNameAndLastName(em, firstName, lastName, searchParameter);
            result.setTotalNumberOfResult(totalCountByFirstNameAndLastName(em, firstName, lastName, searchParameter));
            return result;
        } catch (Exception e) {
            return errorCollectionResult();
        }
    }

    private CollectionModelHibernateResult<PersonDB> filterByFirstNameAndLastName(
            EntityManager em, String firstName, String lastName,
            SearchParameter searchParameter) throws Exception {
        final var cb = em.getCriteriaBuilder();
        final var query = cb.createQuery(PersonDB.class);
        final var root = query.from(PersonDB.class);
        final var predicate = createPredicate(cb, root, firstName, lastName);

        query.select(root).where(predicate);

        final var result = em.createQuery(query)
                .setFirstResult(searchParameter.getOffset())
                .setMaxResults(searchParameter.getSize())
                .getResultList();

        return new CollectionModelHibernateResult<>(result);
    }


    private int totalCountByFirstNameAndLastName(
            EntityManager em, String firstName, String lastName,
            SearchParameter searchParameter) throws Exception {
        final var cb = em.getCriteriaBuilder();
        final var query = cb.createQuery(Long.class);
        final var root = query.from(PersonDB.class);
        final var predicate = createPredicate(cb, root, firstName, lastName);

        query.select(cb.count(root)).where(predicate);

        return em.createQuery(query).getSingleResult().intValue();
    }


    private Predicate createPredicate(CriteriaBuilder cb, Root<PersonDB> root, String firstName, String lastName) {
        final Predicate matchFirstName = cb.like(cb.lower(root.get("firstName")), firstName.toLowerCase() + "%");
        final Predicate matchLastName = cb.like(cb.lower(root.get("lastName")), lastName.toLowerCase() + "%");
        return cb.and(matchFirstName, matchLastName);
    }

    @Override
    public CollectionModelHibernateResult<PersonDB> readByBirthday(String birthday, SearchParameter searchParameter) {
        /* We omit paging in this example */
        try (EntityManager em = JpaUtils.getEntityManager()) {
            TypedQuery<PersonDB> query = em.createQuery("SELECT p FROM PersonDB p WHERE p.birthDate = :birthday", PersonDB.class);
            query.setParameter("birthday", LocalDate.parse(birthday, DateTimeFormatter.ISO_DATE));
            var result = query.getResultList();
            return new CollectionModelHibernateResult<>(result);
        } catch (Exception e) {
            return errorCollectionResult();
        }
    }

    @Override
    public NoContentResult update(PersonDB model) {
        try (EntityManager em = JpaUtils.getEntityManager()) {
            final var transaction = em.getTransaction();
            try {
                transaction.begin();
                em.merge(model);
                transaction.commit();
                return new NoContentResult();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                return errorNocontentResult();
            }
        } catch (Exception e) {
            return errorNocontentResult();
        }
    }

    @Override
    public NoContentResult delete(long id) {
        try (EntityManager em = JpaUtils.getEntityManager()) {
            final var transaction = em.getTransaction();
            try {
                transaction.begin();

                final var cb = em.getCriteriaBuilder();
                final var delete = cb.createCriteriaDelete(PersonDB.class);
                final var rootEntry = delete.from(PersonDB.class);

                delete.where(cb.equal(rootEntry.get("id"), id));
                em.createQuery(delete).executeUpdate();
                transaction.commit();
                return new NoContentResult();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                return errorNocontentResult();
            }
        } catch (Exception e) {
            return errorNocontentResult();
        }
    }

    protected NoContentResult errorNocontentResult() {
        final NoContentResult returnValue = new NoContentResult();
        returnValue.setError();
        return returnValue;
    }

    protected SingleModelHibernateResult<PersonDB> errorSingleResult() {
        final SingleModelHibernateResult<PersonDB> returnValue = new SingleModelHibernateResult<PersonDB>();
        returnValue.setError();
        return returnValue;
    }

    protected CollectionModelHibernateResult<PersonDB> errorCollectionResult() {
        final CollectionModelHibernateResult<PersonDB> returnValue = new CollectionModelHibernateResult<PersonDB>();
        returnValue.setError();
        return returnValue;
    }
}
