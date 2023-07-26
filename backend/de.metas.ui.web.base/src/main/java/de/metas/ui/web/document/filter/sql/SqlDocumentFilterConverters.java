package de.metas.ui.web.document.filter.sql;

import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Static methods to create and manipulate {@link SqlDocumentFilterConverter} instances.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public final class SqlDocumentFilterConverters
{
	/**
	 * Convenient method to create the effective converter instance from the given <code>entityBinding</code>.
	 * It will use
	 * <ul>
	 * <li>entity binding's registered converters list: {@link SqlEntityBinding#getFilterConverters()}
	 * <li>{@link SqlDefaultDocumentFilterConverter} as a fallback/default converter.
	 * </ul>
	 */
	public static SqlDocumentFilterConverter createEntityBindingEffectiveConverter(@NonNull final SqlEntityBinding entityBinding)
	{
		final SqlDocumentFilterConvertersList converters = entityBinding.getFilterConverters();
		final SqlDocumentFilterConverter fallBackConverter = SqlDefaultDocumentFilterConverter.newInstance(entityBinding);

		final SqlDocumentFilterConvertersListWithFallback sqlDocumentFilterConverter = //
				SqlDocumentFilterConvertersListWithFallback.newInstance(converters, fallBackConverter);

		final SqlDocumentFilterConverterDecorator decoratorOrNull = entityBinding.getFilterConverterDecorator().orElse(null);
		if (decoratorOrNull == null)
		{
			return sqlDocumentFilterConverter;
		}
		return decoratorOrNull.decorate(sqlDocumentFilterConverter);
	}

	public static SqlDocumentFilterConvertersList.Builder listBuilder()
	{
		return SqlDocumentFilterConvertersList.builder();
	}

	public static SqlDocumentFilterConvertersList emptyList()
	{
		return SqlDocumentFilterConvertersList.EMPTY;
	}

}
