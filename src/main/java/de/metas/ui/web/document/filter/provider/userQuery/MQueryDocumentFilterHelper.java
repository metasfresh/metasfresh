package de.metas.ui.web.document.filter.provider.userQuery;

import java.util.ArrayList;
import java.util.UUID;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MQuery;

import com.google.common.collect.ImmutableMap;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
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
 * Helper class used to convert {@link MQuery}s to {@link DocumentFilter}.s
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public final class MQueryDocumentFilterHelper
{
	public static DocumentFilter createDocumentFilterFromMQuery(
			@NonNull final MQuery mquery,
			@NonNull final ITranslatableString caption)
	{
		final ArrayList<DocumentFilterParam> parameters = new ArrayList<>();
		for (int restrictionIdx = 0, restrictionsCount = mquery.getRestrictionCount(); restrictionIdx < restrictionsCount; restrictionIdx++)
		{
			final DocumentFilterParam param = createDocumentFilterParamFromMQueryRestriction(mquery, restrictionIdx);
			parameters.add(param);
		}

		final String filterId;
		if (parameters.size() == 1 && !parameters.get(0).isSqlFilter())
		{
			filterId = parameters.get(0).getFieldName();
		}
		else
		{
			filterId = "MQuery-" + UUID.randomUUID(); // FIXME: find a better filterId
		}

		return DocumentFilter.builder()
				.setFilterId(filterId)
				.setCaption(caption)
				.setParameters(parameters)
				.build();
	}

	private static DocumentFilterParam createDocumentFilterParamFromMQueryRestriction(final MQuery mquery, final int restrictionIndex)
	{
		try
		{
			if (mquery.isDirectWhereClause(restrictionIndex))
			{
				final boolean joinAnd = mquery.isJoinAnd(restrictionIndex);
				final String sqlWhereClause = mquery.getDirectWhereClause(restrictionIndex);

				return DocumentFilterParam.ofSqlWhereClause(joinAnd, sqlWhereClause);
			}

			final boolean joinAnd = mquery.isJoinAnd(restrictionIndex);
			final String fieldName = mquery.getColumnName(restrictionIndex);
			final Operator operator = MQueryDocumentFilterHelper.fromMQueryOperator(mquery.getOperator(restrictionIndex));
			final Object value = mquery.getCode(restrictionIndex);
			final Object valueTo = mquery.getCodeTo(restrictionIndex);

			return DocumentFilterParam.builder()
					.setJoinAnd(joinAnd)
					.setFieldName(fieldName)
					.setOperator(operator)
					.setValue(value)
					.setValueTo(valueTo)
					.build();
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed converting MQuery's restriction to " + DocumentFilterParam.class
					+ "\n MQuery: " + mquery
					+ "\n Restriction index: " + restrictionIndex //
					, ex);
		}
	}

	public static Operator fromMQueryOperator(final MQuery.Operator mqueryOperator)
	{
		final Operator operator = MQueryOperator2Operator.get(mqueryOperator);
		if (operator == null)
		{
			throw new AdempiereException("Unsupported MQuery operator: " + mqueryOperator);
		}
		return operator;
	}

	private static final ImmutableMap<MQuery.Operator, Operator> MQueryOperator2Operator = ImmutableMap.<MQuery.Operator, Operator> builder()
			.put(MQuery.Operator.EQUAL, Operator.EQUAL)
			.put(MQuery.Operator.NOT_EQUAL, Operator.NOT_EQUAL)
			.put(MQuery.Operator.LIKE, Operator.LIKE)
			.put(MQuery.Operator.LIKE_I, Operator.LIKE_I)
			.put(MQuery.Operator.NOT_LIKE, Operator.NOT_LIKE)
			.put(MQuery.Operator.NOT_LIKE_I, Operator.NOT_LIKE_I)
			.put(MQuery.Operator.GREATER, Operator.GREATER)
			.put(MQuery.Operator.GREATER_EQUAL, Operator.GREATER_OR_EQUAL)
			.put(MQuery.Operator.LESS, Operator.LESS)
			.put(MQuery.Operator.LESS_EQUAL, Operator.LESS_OR_EQUAL)
			.put(MQuery.Operator.BETWEEN, Operator.BETWEEN)
			.build();

}
