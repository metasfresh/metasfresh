package org.adempiere.ad.validationRule.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.cache.CCache;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.ad.validationRule.IValidationRuleDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Val_Rule;
import org.compiere.model.I_AD_Val_Rule_Dep;
import org.compiere.model.I_AD_Val_Rule_Included;

public class ValidationRuleDAO implements IValidationRuleDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ValidationRuleDescriptorsMap> cache = CCache.<Integer, ValidationRuleDescriptorsMap>builder()
			.tableName(I_AD_Val_Rule.Table_Name)
			.additionalTableNameToResetFor(I_AD_Val_Rule_Included.Table_Name)
			.additionalTableNameToResetFor(I_AD_Val_Rule_Dep.Table_Name)
			.initialCapacity(1)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	@Override
	public ValidationRuleDescriptor getById(@NonNull final AdValRuleId adValRuleId)
	{
		return getValidationRuleDescriptorsMap().getById(adValRuleId);
	}

	private ValidationRuleDescriptorsMap getValidationRuleDescriptorsMap()
	{
		return cache.getOrLoad(0, this::retrieveValidationRuleDescriptorsMap);
	}

	private ValidationRuleDescriptorsMap retrieveValidationRuleDescriptorsMap()
	{
		final ImmutableSetMultimap<AdValRuleId, AdValRuleId> includedValRuleIdsByParentId = queryBL
				.createQueryBuilder(I_AD_Val_Rule_Included.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						record -> AdValRuleId.ofRepoId(record.getAD_Val_Rule_ID()),
						record -> AdValRuleId.ofRepoId(record.getIncluded_Val_Rule_ID())
				));

		final ImmutableSetMultimap<AdValRuleId, @NonNull String> dependsOnTableNamesByValRuleId = queryBL
				.createQueryBuilder(I_AD_Val_Rule_Dep.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						record -> AdValRuleId.ofRepoId(record.getAD_Val_Rule_ID()),
						record -> TableIdsCache.instance.getTableName(AdTableId.ofRepoId(record.getAD_Table_ID()))
				));

		final ImmutableMap<AdValRuleId, ValidationRuleDescriptor> valRuleDescriptorsById = queryBL
				.createQueryBuilder(I_AD_Val_Rule.class)
				//.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> AdValRuleId.ofRepoId(record.getAD_Val_Rule_ID()),
						record -> fromRecord(record, includedValRuleIdsByParentId, dependsOnTableNamesByValRuleId)
				));

		return new ValidationRuleDescriptorsMap(valRuleDescriptorsById);
	}

	private static ValidationRuleDescriptor fromRecord(
			@NonNull I_AD_Val_Rule record,
			@NonNull final ImmutableSetMultimap<AdValRuleId, AdValRuleId> includedValRuleIdsByParentId,
			@NonNull final ImmutableSetMultimap<AdValRuleId, String> dependsOnTableNamesByValRuleId)
	{
		final AdValRuleId adValRuleId = AdValRuleId.ofRepoId(record.getAD_Val_Rule_ID());
		final ValidationRuleType type = ValidationRuleType.ofCode(record.getType());
		final ValidationRuleDescriptor.ValidationRuleDescriptorBuilder builder = ValidationRuleDescriptor.builder()
				.id(adValRuleId)
				.name(record.getName())
				.active(record.isActive())
				.type(type)
				.dependsOnTableNames(dependsOnTableNamesByValRuleId.get(adValRuleId));

		if (type == ValidationRuleType.SQL)
		{
			builder.sqlWhereClause(extractSqlWhereClause(record));
		}
		else if (type == ValidationRuleType.JAVA)
		{
			builder.javaClassname(StringUtils.trimBlankToNull(record.getClassname()));
		}
		else if (type == ValidationRuleType.COMPOSITE)
		{
			builder.includedValRuleIds(includedValRuleIdsByParentId.get(adValRuleId));
		}
		else
		{
			throw new AdempiereException("Unknown type: " + type);
		}

		return builder.build();
	}

	private static IStringExpression extractSqlWhereClause(@NonNull final I_AD_Val_Rule record)
	{
		final String sqlWhereClauseString = StringUtils.trimBlankToNull(record.getCode());
		if (sqlWhereClauseString == null)
		{
			return IStringExpression.NULL;
		}

		return IStringExpression.compileOrDefault(sqlWhereClauseString, IStringExpression.NULL);
	}

}
