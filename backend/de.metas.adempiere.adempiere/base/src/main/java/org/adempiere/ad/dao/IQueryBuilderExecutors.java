package org.adempiere.ad.dao;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import org.compiere.model.IQuery;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public interface IQueryBuilderExecutors<T>
{
	IQuery<T> create();

	default ImmutableList<T> list() {return create().listImmutable();}

	@Nullable
	default T first() {return create().first();}

	default Optional<T> firstOptional() {return create().firstOptional();}

	default T firstOnly() {return create().firstOnly();}

	default Optional<T> firstOnlyOptional() {return create().firstOnlyOptional();}

	default boolean anyMatch() {return create().anyMatch();}

	default Stream<T> iterateAndStream() {return create().iterateAndStream();}

	default Stream<T> stream() {return create().stream();}

	default void forEach(@NonNull final Consumer<T> action) {create().forEach(action);}

	default int count() {return create().count();}

	default <K> ImmutableMap<K, T> toMap(final Function<T, K> keyMapper) {return create().map(keyMapper);}
}
