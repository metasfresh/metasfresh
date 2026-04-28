/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.order;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_M_Product;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Step definitions for creating {@link I_C_CompensationGroup_Schema_TemplateLine} records.
 * <p>
 * Template lines define which sub-article products (and their quantities) are created
 * when a compensation group is generated from a schema template.
 * <p>
 * DataTable columns:
 * <ul>
 *     <li>{@code Identifier} (required) — identifier for later reference</li>
 *     <li>{@code C_CompensationGroup_Schema_ID} (required) — identifier of the parent schema</li>
 *     <li>{@code M_Product_ID} (required) — identifier of the sub-article product</li>
 *     <li>{@code Qty} (required) — quantity per template line</li>
 *     <li>{@code C_UOM_ID} (required) — X12DE355 code of the UOM (e.g., "PCE", "KGM")</li>
 *     <li>{@code SeqNo} (required) — sequence number for ordering</li>
 * </ul>
 */
@RequiredArgsConstructor
public class C_CompensationGroup_Schema_TemplateLine_StepDef
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final @NonNull C_CompensationGroup_Schema_StepDefData schemaTable;
	private final @NonNull C_CompensationGroup_Schema_TemplateLine_StepDefData templateLineTable;
	private final @NonNull M_Product_StepDefData productTable;

	/**
	 * Creates template line records that define which sub-article products (and quantities) are added
	 * when a compensation group is generated from the parent schema.
	 * <p>
	 * Note: {@code C_UOM_ID} expects an X12DE355 code (e.g., "PCE", "KGM"), not an identifier reference.
	 */
	@Given("metasfresh contains C_CompensationGroup_Schema_TemplateLine:")
	public void createTemplateLines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_CompensationGroup_Schema schema = row.getAsIdentifier(I_C_CompensationGroup_Schema_TemplateLine.COLUMNNAME_C_CompensationGroup_Schema_ID)
					.lookupNotNullIn(schemaTable);

			final I_M_Product product = row.getAsIdentifier(I_C_CompensationGroup_Schema_TemplateLine.COLUMNNAME_M_Product_ID)
					.lookupNotNullIn(productTable);

			final String x12de355 = row.getAsString(I_C_CompensationGroup_Schema_TemplateLine.COLUMNNAME_C_UOM_ID);
			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355));

			final I_C_CompensationGroup_Schema_TemplateLine record = newInstance(I_C_CompensationGroup_Schema_TemplateLine.class);
			record.setAD_Org_ID(schema.getAD_Org_ID());
			record.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
			record.setM_Product_ID(product.getM_Product_ID());
			record.setC_UOM_ID(uomId.getRepoId());
			record.setQty(row.getAsBigDecimal(I_C_CompensationGroup_Schema_TemplateLine.COLUMNNAME_Qty));
			record.setSeqNo(row.getAsInt(I_C_CompensationGroup_Schema_TemplateLine.COLUMNNAME_SeqNo));

			saveRecord(record);

			templateLineTable.putOrReplace(row.getAsIdentifier(), record);
		});
	}
}
