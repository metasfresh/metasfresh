/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

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
