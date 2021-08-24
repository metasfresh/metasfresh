package de.metas.order.compensationGroup;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.contracts.ConditionsId;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.order.model.I_C_CompensationGroup_SchemaLine;
import de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

@Repository
public class GroupTemplateRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final CCache<GroupTemplateId, GroupTemplate> //
			groupTemplatesById = CCache.<GroupTemplateId, GroupTemplate>builder()
			.tableName(I_C_CompensationGroup_Schema.Table_Name)
			.additionalTableNameToResetFor(I_C_CompensationGroup_Schema_TemplateLine.Table_Name)
			.additionalTableNameToResetFor(I_C_CompensationGroup_SchemaLine.Table_Name)
			.initialCapacity(10)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	private final Map<GroupTemplateCompensationLineType, GroupMatcherFactory> groupMatcherFactoriesByType;

	public GroupTemplateRepository(final Optional<List<GroupMatcherFactory>> groupMatcherFactories)
	{
		this.groupMatcherFactoriesByType = Maps.uniqueIndex(
				groupMatcherFactories.orElse(ImmutableList.of()),
				GroupMatcherFactory::getAppliesToLineType);
	}

	public GroupTemplate getById(@NonNull final GroupTemplateId groupTemplateId)
	{
		return groupTemplatesById.getOrLoad(groupTemplateId, this::retrieveById);
	}

	private GroupTemplate retrieveById(@NonNull final GroupTemplateId groupTemplateId)
	{
		final I_C_CompensationGroup_Schema schemaRecord = InterfaceWrapperHelper.load(groupTemplateId, I_C_CompensationGroup_Schema.class);

		final ImmutableList<GroupTemplateRegularLine> mainLines = retrieveRegularLines(groupTemplateId);
		final ImmutableList<GroupTemplateCompensationLine> compensationLines = retrieveCompensationLines(groupTemplateId);

		return GroupTemplate.builder()
				.id(GroupTemplateId.ofRepoId(schemaRecord.getC_CompensationGroup_Schema_ID()))
				.name(schemaRecord.getName())
				.activityId(ActivityId.ofRepoIdOrNull(schemaRecord.getC_Activity_ID()))
				.regularLinesToAdd(mainLines)
				.compensationLines(compensationLines)
				.build();
	}

	private ImmutableList<GroupTemplateRegularLine> retrieveRegularLines(final GroupTemplateId groupTemplateId)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_C_CompensationGroup_Schema_TemplateLine.class)
				.addEqualsFilter(I_C_CompensationGroup_Schema_TemplateLine.COLUMNNAME_C_CompensationGroup_Schema_ID, groupTemplateId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_CompensationGroup_Schema_TemplateLine.COLUMN_SeqNo)
				.orderBy(I_C_CompensationGroup_Schema_TemplateLine.COLUMN_C_CompensationGroup_Schema_TemplateLine_ID)
				.create()
				.stream(I_C_CompensationGroup_Schema_TemplateLine.class)
				.map(this::fromRecord)
				.collect(ImmutableList.toImmutableList());

	}

	private GroupTemplateRegularLine fromRecord(@NonNull final I_C_CompensationGroup_Schema_TemplateLine record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());
		final I_C_UOM uom = uomDAO.getById(uomId);
		return GroupTemplateRegularLine.builder()
				.id(GroupTemplateRegularLineId.ofRepoId(record.getC_CompensationGroup_Schema_TemplateLine_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qty(Quantity.of(record.getQty(), uom))
				.contractConditionsId(ConditionsId.ofRepoIdOrNull(record.getC_Flatrate_Conditions_ID()))
				.build();
	}

	private ImmutableList<GroupTemplateCompensationLine> retrieveCompensationLines(final @NonNull GroupTemplateId groupTemplateId)
	{
		final List<I_C_CompensationGroup_SchemaLine> compensationLineRecords = retrieveCompensationLineRecords(groupTemplateId);
		return compensationLineRecords.stream()
				.map(compensationLineRecord -> fromRecord(compensationLineRecord, compensationLineRecords))
				.collect(ImmutableList.toImmutableList());
	}

	private List<I_C_CompensationGroup_SchemaLine> retrieveCompensationLineRecords(final GroupTemplateId groupTemplateId)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_C_CompensationGroup_SchemaLine.class)
				.addEqualsFilter(I_C_CompensationGroup_SchemaLine.COLUMN_C_CompensationGroup_Schema_ID, groupTemplateId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_CompensationGroup_SchemaLine.COLUMN_SeqNo)
				.orderBy(I_C_CompensationGroup_SchemaLine.COLUMN_C_CompensationGroup_SchemaLine_ID)
				.create()
				.list(I_C_CompensationGroup_SchemaLine.class);
	}

	private GroupTemplateCompensationLine fromRecord(
			@NonNull final I_C_CompensationGroup_SchemaLine compensationLineRecord,
			@NonNull final List<I_C_CompensationGroup_SchemaLine> allCompensationLineRecords)
	{
		final BigDecimal percentage = compensationLineRecord.getCompleteOrderDiscount();
		return GroupTemplateCompensationLine.builder()
				.id(GroupTemplateLineId.ofRepoIdOrNull(compensationLineRecord.getC_CompensationGroup_SchemaLine_ID()))
				.groupMatcher(createGroupMatcher(compensationLineRecord, allCompensationLineRecords))
				.productId(ProductId.ofRepoId(compensationLineRecord.getM_Product_ID()))
				.percentage(percentage != null && percentage.signum() != 0 ? percentage : null)
				.build();
	}

	private GroupMatcher createGroupMatcher(
			final I_C_CompensationGroup_SchemaLine compensationLineRecord,
			final List<I_C_CompensationGroup_SchemaLine> allCompensationLineRecords)
	{
		final GroupTemplateCompensationLineType type = GroupTemplateCompensationLineType.ofNullableCode(compensationLineRecord.getType());
		if (type == null)
		{
			return GroupMatchers.ALWAYS;
		}
		else
		{
			final GroupMatcherFactory groupMatcherFactory = groupMatcherFactoriesByType.get(type);
			if (groupMatcherFactory == null)
			{
				throw new AdempiereException("No " + GroupMatcherFactory.class + " found for type=" + type)
						.setParameter("schemaLine", compensationLineRecord);
			}

			return groupMatcherFactory.createPredicate(compensationLineRecord, allCompensationLineRecords);
		}
	}
}
