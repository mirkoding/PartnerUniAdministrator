package de.fhws.fiw.fds.suttondemo.server.database.hibernate;

import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.suttondemo.server.PersonDao;
import de.fhws.fiw.fds.suttondemo.server.api.models.Person;
import de.fhws.fiw.fds.suttondemo.server.database.hibernate.dao.PersonDaoHibernate;
import de.fhws.fiw.fds.suttondemo.server.database.hibernate.dao.PersonDaoHibernateImpl;
import de.fhws.fiw.fds.suttondemo.server.database.hibernate.datafaker.PersonDataFaker;
import de.fhws.fiw.fds.suttondemo.server.database.hibernate.models.PersonDB;
import org.modelmapper.ModelMapper;

import java.util.stream.IntStream;

/**
 * This class is the adapter between the business logic layer and the database layer.
 * <p>
 * The business logic must use this class to access the database and it can use its own
 * model classes. This class takes care of translating models from the business logic into
 * models of the database layer and vice-versa.
 */
public class PersonDaoAdapter implements PersonDao {

    private PersonDaoHibernate dao = new PersonDaoHibernateImpl();
    private final PersonDataFaker faker = new PersonDataFaker();
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public NoContentResult create(Person model) {
        final PersonDB dbModel = createFrom(model);
        final NoContentResult returnValue = this.dao.create(dbModel);
        model.setId(dbModel.getId());
        return returnValue;
    }

    @Override
    public SingleModelResult<Person> readById(long id) {
        return createResult(this.dao.readById(id));
    }

    @Override
    public CollectionModelResult<Person> readAll() {
        return this.readAll(new SearchParameter());
    }

    @Override
    public CollectionModelResult<Person> readAll(SearchParameter searchParameter) {
        return createResult(this.dao.readAll(searchParameter));
    }

    @Override
    public NoContentResult update(Person model) {
        return this.dao.update(createFrom(model));
    }

    @Override
    public CollectionModelResult<Person> readByFirstNameAndLastName(String firstName, String lastName,
                                                                    SearchParameter searchParameter) {
        return createResult(this.dao.readByFirstNameAndLastName(firstName, lastName, searchParameter));
    }

    @Override
    public NoContentResult delete(long id) {
        return this.dao.delete(id);
    }

    @Override
    public void resetDatabase() {
        readAll().getResult().stream().map(Person::getId).forEach(this::delete);
    }

    private Person createFrom(PersonDB model) {
        return modelMapper.map(model, Person.class);
    }

    private PersonDB createFrom(Person model) {
        return modelMapper.map(model, PersonDB.class);
    }

    private SingleModelResult<Person> createResult(SingleModelHibernateResult<PersonDB> result) {
        if (result.isEmpty()) {
            return new SingleModelResult<>();
        }
        if (result.hasError()) {
            final var returnValue = new SingleModelResult<Person>();
            returnValue.setError();
            return returnValue;
        } else {
            return new SingleModelResult<>(createFrom(result.getResult()));
        }
    }

    private CollectionModelResult<Person> createResult(CollectionModelHibernateResult<PersonDB> result) {
        final var returnValue = new CollectionModelResult<Person>();
        if (result.hasError()) {
            returnValue.setError();
        } else {
            returnValue.setTotalNumberOfResult(result.getTotalNumberOfResult());
        }
        return returnValue;
    }

    private void populateDatabase() {
        IntStream.range(0, 2).forEach(i -> this.dao.create(createPerson()));
    }

    private PersonDB createPerson() {
        return this.faker.createPerson();
    }
}
