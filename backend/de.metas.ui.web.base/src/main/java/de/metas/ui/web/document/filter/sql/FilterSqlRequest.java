/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.ui.web.document.filter.sql;

import com.google.common.collect.ImmutableList;
import de.metas.security.UserRolePermissionsKey;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
public class FilterSqlRequest
{
	@NonNull DocumentFilter filter;
	@NonNull SqlOptions sqlOpts;
	@NonNull SqlDocumentFilterConverterContext context;
	@NonNull DocumentFilterList filters;

	@Nullable
	public String getFilterParameterValueAsString(@NonNull final String parameterName) {return filter.getParameterValueAsString(parameterName);}

	@Nullable
	public String getFilterParameterValueAsString(@NonNull final String parameterName, @Nullable final String defaultValue) {return filter.getParameterValueAsString(parameterName, defaultValue);}

	public boolean getFilterParameterValueAsBoolean(@NonNull final String parameterName, final boolean defaultValue) {return filter.getParameterValueAsBoolean(parameterName, defaultValue);}

	@Nullable
	public DocumentFilterParam getFilterParameterOrNull(@NonNull final String parameterName) {return filter.getParameterOrNull(parameterName);}

	@Nullable
	public <T> T getFilterParameterValueAs(@NonNull final String parameterName) {return filter.getParameterValueAs(parameterName);}

	@NonNull
	public String getFilterId() {return filter.getFilterId();}

	public boolean hasFilterParameters() {return filter.hasParameters();}

	@NonNull
	public ImmutableList<DocumentFilterParam> getFilterParameters() {return filter.getParameters();}

	@Nullable
	public UserRolePermissionsKey getUserRolePermissionsKey() {return context.getUserRolePermissionsKey();}

	public int getContextPropertyAsInt(@NonNull final String name, final int defaultValue) {return context.getPropertyAsInt(name, defaultValue);}

	@NonNull
	public String getTableNameOrAlias() {return sqlOpts.getTableNameOrAlias();}

	@NonNull
	public Optional<DocumentFilter> getFilterById(final String id)
	{
		return filters.getFilterById(id);
	}
}
