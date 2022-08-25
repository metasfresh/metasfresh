package org.adempiere.ad.dao;

import com.google.common.collect.ImmutableList;
import org.compiere.model.IQuery;

import java.util.Optional;
import java.util.stream.Stream;

public interface IQueryBuilderExecutors<T>
{
	IQuery<T> create();

	default ImmutableList<T> list() {return create().listImmutable();}

	default T first() {return create().first();}

	default Optional<T> firstOptional() {return create().firstOptional();}

	default T firstOnly() {return create().firstOnly();}

	default Optional<T> firstOnlyOptional() {return create().firstOnlyOptional();}

	default boolean anyMatch() {return create().anyMatch();}

	default Stream<T> iterateAndStream() {return create().iterateAndStream();}

	default Stream<T> stream() {return create().stream();}
}
