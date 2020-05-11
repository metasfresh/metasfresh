package de.metas.ui.web.board;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlOrderByValue;
import de.metas.ui.web.window.descriptor.sql.SqlSelectDisplayValue;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Builder
@Value
public class BoardCardFieldDescriptor
{
	@NonNull
	private final ITranslatableString caption;

	@NonNull
	private final String fieldName;
	@NonNull
	private final DocumentFieldWidgetType widgetType;
	/** Set of "select value" SQLs required to load the value */
	@NonNull
	private final ImmutableSet<String> sqlSelectValues;

	private final boolean usingDisplayColumn;
	private final SqlSelectDisplayValue sqlSelectDisplayValue;

	@NonNull
	private final SqlOrderByValue sqlOrderBy;

	/** Retrieves a particular field from given {@link ResultSet}. */
	@FunctionalInterface
	public interface BoardFieldLoader
	{
		Object retrieveValueAsJson(ResultSet rs, String adLanguage) throws SQLException;
	}

	@NonNull
	private final BoardFieldLoader fieldLoader;
}
