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

package de.metas.ui.web.kpi.descriptor.elasticsearch;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CtxName;
import org.elasticsearch.action.search.SearchType;

import java.util.List;
import java.util.Set;

@Value
public class ElasticsearchDatasourceDescriptor
{
	@NonNull String esSearchIndex;
	@NonNull SearchType esSearchTypes;
	@NonNull IStringExpression esQuery;

	@Getter(AccessLevel.NONE)
	@NonNull ImmutableMap<String, ElasticsearchDatasourceFieldDescriptor> fields;

	@Builder
	private ElasticsearchDatasourceDescriptor(
			@NonNull final String esSearchIndex,
			@NonNull final SearchType esSearchTypes,
			@NonNull final String esQuery,
			@NonNull final List<ElasticsearchDatasourceFieldDescriptor> fields)
	{
		Check.assumeNotEmpty(esSearchIndex, "esSearchIndex is not empty");
		Check.assumeNotEmpty(esQuery, "esQuery is not empty");
		Check.assumeNotEmpty(fields, "fields is not empty");

		this.esSearchIndex = esSearchIndex;
		this.esSearchTypes = esSearchTypes;
		this.esQuery = StringExpressionCompiler.instance.compile(esQuery);
		this.fields = Maps.uniqueIndex(fields, ElasticsearchDatasourceFieldDescriptor::getFieldName);
	}

	public ElasticsearchDatasourceFieldDescriptor getFieldByName(@NonNull final String fieldName)
	{
		final ElasticsearchDatasourceFieldDescriptor field = fields.get(fieldName);
		if (field == null)
		{
			throw new AdempiereException("Field `" + fieldName + "` was not found in " + this);
		}
		return field;
	}

	public Set<CtxName> getRequiredContextParameters()
	{
		return esQuery.getParameters();
	}
}
