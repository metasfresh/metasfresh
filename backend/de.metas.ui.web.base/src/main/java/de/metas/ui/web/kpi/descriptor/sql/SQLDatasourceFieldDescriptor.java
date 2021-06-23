/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.kpi.descriptor.sql;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class SQLDatasourceFieldDescriptor
{
	@NonNull String fieldName;
	@NonNull String sqlSelect;

	@Builder
	private SQLDatasourceFieldDescriptor(
			@NonNull final String fieldName,
			@NonNull final String sqlSelect)
	{
		Check.assumeNotEmpty(fieldName, "fieldName shall be set");
		Check.assumeNotEmpty(sqlSelect, "sqlSelect shall be set");

		this.fieldName = fieldName;
		this.sqlSelect = sqlSelect;
	}
}
