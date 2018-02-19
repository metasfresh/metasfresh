package de.metas.order.compensationGroup;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.order.model.I_C_CompensationGroup_SchemaLine;

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
	private final CCache<Integer, GroupTemplate> groupTemplatesById = CCache.<Integer, GroupTemplate> newCache(I_C_CompensationGroup_Schema.Table_Name, 10, CCache.EXPIREMINUTES_Never)
			.addResetForTableName(I_C_CompensationGroup_SchemaLine.Table_Name);

	public GroupTemplate getById(final int groupTemplateId)
	{
		return groupTemplatesById.getOrLoad(groupTemplateId, () -> retrieveById(groupTemplateId));
	}

	private GroupTemplate retrieveById(final int groupTemplateId)
	{
		Check.assume(groupTemplateId > 0, "groupTemplateId > 0");

		final I_C_CompensationGroup_Schema schemaPO = InterfaceWrapperHelper.load(groupTemplateId, I_C_CompensationGroup_Schema.class);

		final List<GroupTemplateLine> lines = retrieveSchemaLines(groupTemplateId)
				.stream()
				.map(this::toGroupTemplateLine)
				.collect(ImmutableList.toImmutableList());

		return GroupTemplate.builder()
				.id(schemaPO.getC_CompensationGroup_Schema_ID())
				.name(schemaPO.getName())
				.lines(lines)
				.build();
	}

	private List<I_C_CompensationGroup_SchemaLine> retrieveSchemaLines(final int groupTemplateId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_CompensationGroup_SchemaLine.class)
				.addEqualsFilter(I_C_CompensationGroup_SchemaLine.COLUMN_C_CompensationGroup_Schema_ID, groupTemplateId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_CompensationGroup_SchemaLine.COLUMN_C_CompensationGroup_SchemaLine_ID) // TODO add SeqNo
				.create()
				.list(I_C_CompensationGroup_SchemaLine.class);
	}

	private GroupTemplateLine toGroupTemplateLine(final I_C_CompensationGroup_SchemaLine schemaLinePO)
	{
		return GroupTemplateLine.builder()
				.id(schemaLinePO.getC_CompensationGroup_SchemaLine_ID())
				.productId(schemaLinePO.getM_Product_ID())
				.build();
	}
}
