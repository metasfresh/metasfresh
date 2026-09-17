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
import de.metas.order.model.I_C_CompensationGroup_SchemaLine;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_M_Product;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Step definitions for creating {@link I_C_CompensationGroup_SchemaLine} records.
 * <p>
 * A schema line is the <b>compensation</b> (e.g. discount / surcharge) counterpart of a
 * {@link de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine}: while a template line adds a
 * regular product line to the generated compensation group, a schema line adds a compensation line
 * (tracked on the order line via {@code IsGroupCompensationLine=Y}). It is read by
 * {@code GroupTemplateRepository.retrieveCompensationLines}; the compensation type / amount type default
 * from the referenced product (Discount / Percent), and {@code CompleteOrderDiscount} supplies the discount
 * percentage. Leaving {@code Type} unset yields an always-matching compensation line.
 */
@RequiredArgsConstructor
public class C_CompensationGroup_SchemaLine_StepDef
{
	private final @NonNull C_CompensationGroup_Schema_StepDefData schemaTable;
	private final @NonNull C_CompensationGroup_SchemaLine_StepDefData schemaLineTable;
	private final @NonNull M_Product_StepDefData productTable;

	/**
	 * Creates {@link I_C_CompensationGroup_SchemaLine} records that add a compensation (discount / surcharge)
	 * line to a compensation-group schema.
	 * <p>
	 * DataTable columns:
	 * <ul>
	 *     <li>{@code Identifier} (required) — identifier for later reference</li>
	 *     <li>{@code C_CompensationGroup_Schema_ID} (required) — identifier of the parent schema</li>
	 *     <li>{@code M_Product_ID} (required) — identifier of the compensation product (its GroupCompensationType /
	 *         GroupCompensationAmtType default to Discount / Percent)</li>
	 *     <li>{@code OPT.CompleteOrderDiscount} (optional) — the whole-order discount percentage (e.g. {@code 10})</li>
	 *     <li>{@code OPT.SeqNo} (optional) — sequence number for ordering</li>
	 *     <li>{@code OPT.Type} (optional) — matcher type; leave unset for an always-matching line</li>
	 * </ul>
	 * <pre>
	 * And metasfresh contains C_CompensationGroup_SchemaLine:
	 *   | Identifier          | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.SeqNo |
	 *   | schemaDiscount | compGroupSchema                        | discountProduct         | 10                        | 30        |
	 * </pre>
	 */
	@Given("metasfresh contains C_CompensationGroup_SchemaLine:")
	public void createSchemaLines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_CompensationGroup_Schema schema = row.getAsIdentifier(I_C_CompensationGroup_SchemaLine.COLUMNNAME_C_CompensationGroup_Schema_ID)
					.lookupNotNullIn(schemaTable);

			final I_M_Product product = row.getAsIdentifier(I_C_CompensationGroup_SchemaLine.COLUMNNAME_M_Product_ID)
					.lookupNotNullIn(productTable);

			final I_C_CompensationGroup_SchemaLine record = newInstance(I_C_CompensationGroup_SchemaLine.class);
			record.setAD_Org_ID(schema.getAD_Org_ID());
			record.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
			record.setM_Product_ID(product.getM_Product_ID());

			row.getAsOptionalBigDecimal(I_C_CompensationGroup_SchemaLine.COLUMNNAME_CompleteOrderDiscount)
					.ifPresent(record::setCompleteOrderDiscount);
			row.getAsOptionalInt(I_C_CompensationGroup_SchemaLine.COLUMNNAME_SeqNo)
					.ifPresent(record::setSeqNo);
			row.getAsOptionalString(I_C_CompensationGroup_SchemaLine.COLUMNNAME_Type)
					.ifPresent(record::setType);

			saveRecord(record);

			schemaLineTable.putOrReplace(row.getAsIdentifier(), record);
		});
	}
}
