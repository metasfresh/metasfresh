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
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeUse;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class M_AttributeUse_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final M_Attribute_StepDefData attributeTable;
	@NonNull private final M_AttributeSet_StepDefData attributeSetTable;
	@NonNull private final M_AttributeUse_StepDefData attributeUseTable;

	@And("add M_AttributeUse:")
	public void add_M_AttributeUse(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_AttributeUse.COLUMNNAME_M_AttributeUse_ID)
				.forEach(row -> {
					final AttributeSetId attributeSetId = row.getAsIdentifier(I_M_AttributeUse.COLUMNNAME_M_AttributeSet_ID).lookupNotNullIdIn(attributeSetTable);
					final AttributeId attributeId = row.getAsIdentifier(I_M_AttributeUse.COLUMNNAME_M_Attribute_ID).lookupNotNullIdIn(attributeTable);

					final I_M_AttributeUse attributeUse = CoalesceUtil.coalesceSuppliers(
							() -> queryBL.createQueryBuilder(I_M_AttributeUse.class)
									.addEqualsFilter(I_M_AttributeUse.COLUMNNAME_M_AttributeSet_ID, attributeSetId)
									.addEqualsFilter(I_M_AttributeUse.COLUMNNAME_M_Attribute_ID, attributeId)
									.create()
									.firstOnly(I_M_AttributeUse.class),
							() -> InterfaceWrapperHelper.newInstance(I_M_AttributeUse.class));
					assertThat(attributeUse).isNotNull();

					attributeUse.setM_AttributeSet_ID(attributeSetId.getRepoId());
					attributeUse.setM_Attribute_ID(attributeId.getRepoId());

					attributeUse.setSeqNo(row.getAsInt(I_M_AttributeUse.COLUMNNAME_SeqNo));

					InterfaceWrapperHelper.saveRecord(attributeUse);

					row.getAsOptionalIdentifier().ifPresent(identifier -> attributeUseTable.putOrReplace(identifier, attributeUse));
				});
	}
}
