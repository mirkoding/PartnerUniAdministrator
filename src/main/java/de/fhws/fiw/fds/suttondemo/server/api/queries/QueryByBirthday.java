package de.fhws.fiw.fds.suttondemo.server.api.queries;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.server.DaoFactory;
import de.fhws.fiw.fds.suttondemo.server.api.models.Person;

public class QueryByBirthday<R> extends AbstractQuery<R, Person> {
    private String birthday;

    public QueryByBirthday(String birthday, int offset, int size) {
        this.birthday = birthday;
        this.pagingBehavior = new PagingBehaviorUsingOffsetSize<>(offset, size);
    }

    @Override
    protected CollectionModelResult<Person> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
        return DaoFactory.getInstance().getPersonDao().readByBirthday(
                this.birthday,
                searchParameter);
    }
}
