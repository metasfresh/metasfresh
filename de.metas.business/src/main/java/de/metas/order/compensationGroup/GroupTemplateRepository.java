package de.metas.order.compensationGroup;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.CCache;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.order.model.I_C_CompensationGroup_SchemaLine;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class GroupTemplateRepository
{
	private final CCache<GroupTemplateId, GroupTemplate> //
	groupTemplatesById = CCache.<GroupTemplateId, GroupTemplate> newCache(I_C_CompensationGroup_Schema.Table_Name, 10, CCache.EXPIREMINUTES_Never)
			.addResetForTableName(I_C_CompensationGroup_SchemaLine.Table_Name);

	private final Map<String, GroupMatcherFactory> groupMatcherFactoriesByType;

	public GroupTemplateRepository(final Optional<List<GroupMatcherFactory>> groupMatcherFactories)
	{
		final List<GroupMatcherFactory> groupMatcherFactoriesToUse = groupMatcherFactories.orElse(ImmutableList.of());
		this.groupMatcherFactoriesByType = Maps.uniqueIndex(
				groupMatcherFactoriesToUse,
				GroupMatcherFactory::getAppliesToLineType);
	}

	public GroupTemplate getById(@NonNull final GroupTemplateId groupTemplateId)
	{
		return groupTemplatesById.getOrLoad(groupTemplateId, () -> retrieveById(groupTemplateId));
	}

	private GroupTemplate retrieveById(@NonNull final GroupTemplateId groupTemplateId)
	{
		final I_C_CompensationGroup_Schema schemaPO = InterfaceWrapperHelper.load(groupTemplateId, I_C_CompensationGroup_Schema.class);

		final List<I_C_CompensationGroup_SchemaLine> schemaLinePOs = retrieveSchemaLines(groupTemplateId);
		final List<GroupTemplateLine> lines = schemaLinePOs.stream()
				.map(schemaLinePO -> toGroupTemplateLine(schemaLinePO, schemaLinePOs))
				.collect(ImmutableList.toImmutableList());

		return GroupTemplate.builder()
				.id(GroupTemplateId.ofRepoId(schemaPO.getC_CompensationGroup_Schema_ID()))
				.name(schemaPO.getName())
				.lines(lines)
				.build();
	}

	private List<I_C_CompensationGroup_SchemaLine> retrieveSchemaLines(final GroupTemplateId groupTemplateId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_CompensationGroup_SchemaLine.class)
				.addEqualsFilter(I_C_CompensationGroup_SchemaLine.COLUMN_C_CompensationGroup_Schema_ID, groupTemplateId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_CompensationGroup_SchemaLine.COLUMN_SeqNo)
				.orderBy(I_C_CompensationGroup_SchemaLine.COLUMN_C_CompensationGroup_SchemaLine_ID)
				.create()
				.list(I_C_CompensationGroup_SchemaLine.class);
	}

	private GroupTemplateLine toGroupTemplateLine(final I_C_CompensationGroup_SchemaLine schemaLinePO, final List<I_C_CompensationGroup_SchemaLine> allSchemaLinePOs)
	{
		final BigDecimal percentage = schemaLinePO.getCompleteOrderDiscount();
		return GroupTemplateLine.builder()
				.id(GroupTemplateLineId.ofRepoIdOrNull(schemaLinePO.getC_CompensationGroup_SchemaLine_ID()))
				.groupMatcher(createGroupMatcher(schemaLinePO, allSchemaLinePOs))
				.productId(ProductId.ofRepoId(schemaLinePO.getM_Product_ID()))
				.percentage(percentage != null && percentage.signum() != 0 ? percentage : null)
				.build();
	}

	private GroupMatcher createGroupMatcher(final I_C_CompensationGroup_SchemaLine schemaLinePO, final List<I_C_CompensationGroup_SchemaLine> allSchemaLinePOs)
	{
		final String type = schemaLinePO.getType();
		if (type == null)
		{
			return GroupMatchers.ALWAYS;
		}

		final GroupMatcherFactory groupMatcherFactory = groupMatcherFactoriesByType.get(type);
		if (groupMatcherFactory == null)
		{
			throw new AdempiereException("No " + GroupMatcherFactory.class + " found for type=" + type)
					.setParameter("schemaLine", schemaLinePO);
		}

		return groupMatcherFactory.createPredicate(schemaLinePO, allSchemaLinePOs);
	}
}
