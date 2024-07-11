/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.attribute;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeUse;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class M_AttributeUse_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Attribute_StepDefData attributeTable;
	private final M_AttributeSet_StepDefData attributeSetTable;
	private final M_AttributeUse_StepDefData attributeUseTable;

	public M_AttributeUse_StepDef(
			@NonNull final M_Attribute_StepDefData attributeTable,
			@NonNull final M_AttributeSet_StepDefData attributeSetTable,
			@NonNull final M_AttributeUse_StepDefData attributeUseTable)
	{
		this.attributeTable = attributeTable;
		this.attributeSetTable = attributeSetTable;
		this.attributeUseTable = attributeUseTable;
	}

	@And("add M_AttributeUse:")
	public void add_M_AttributeUse(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String attributeSetIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_AttributeUse.COLUMNNAME_M_AttributeSet_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_AttributeSet attributeSet = attributeSetTable.get(attributeSetIdentifier);

			final String attributeIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_AttributeUse.COLUMNNAME_M_Attribute_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Attribute attribute = attributeTable.get(attributeIdentifier);

			final I_M_AttributeUse attributeUse = CoalesceUtil.coalesceSuppliers(
					() -> queryBL.createQueryBuilder(I_M_AttributeUse.class)
							.addEqualsFilter(I_M_AttributeUse.COLUMNNAME_M_AttributeSet_ID, attributeSet.getM_AttributeSet_ID())
							.addEqualsFilter(I_M_AttributeUse.COLUMNNAME_M_Attribute_ID, attribute.getM_Attribute_ID())
							.create()
							.firstOnly(I_M_AttributeUse.class),
					() -> InterfaceWrapperHelper.newInstance(I_M_AttributeUse.class));
			assertThat(attributeUse).isNotNull();

			attributeUse.setM_AttributeSet_ID(attributeSet.getM_AttributeSet_ID());
			attributeUse.setM_Attribute_ID(attribute.getM_Attribute_ID());

			final int seqNo = DataTableUtil.extractIntForColumnName(row, I_M_AttributeUse.COLUMNNAME_SeqNo);
			attributeUse.setSeqNo(seqNo);

			InterfaceWrapperHelper.saveRecord(attributeUse);

			final String attributeUseIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_AttributeUse.COLUMNNAME_M_AttributeUse_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			attributeUseTable.putOrReplace(attributeUseIdentifier, attributeUse);
		}
	}
}
