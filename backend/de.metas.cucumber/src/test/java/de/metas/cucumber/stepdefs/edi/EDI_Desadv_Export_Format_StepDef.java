/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.cucumber.stepdefs.edi;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack_Item;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_EXP_Format;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions that verify which EDI_Desadv_Pack_Item records the live
 * {@code EXP_Format_ID=540418} (Name: {@code EDI_Exp_Desadv_Pack_Item}) WhereClause
 * would select for a given DESADV.
 *
 * <p>This is used by the RED test to prove that the current WhereClause
 * (which filters on the <em>parent line's</em> QtyDeliveredInUOM>0 rather than the
 * pack item's own MovementQty>0) incorrectly includes pack items with MovementQty=0.
 */
public class EDI_Desadv_Export_Format_StepDef
{
	/**
	 * AD_ID of the EXP_Format row for pack-item export.
	 * Name: {@code EDI_Exp_Desadv_Pack_Item}.
	 */
	private static final int EXP_FORMAT_ID_PACK_ITEM = 540418;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final EDI_Desadv_StepDefData desadvTable;
	private final EDI_Desadv_Pack_Item_StepDefData packItemTable;

	public EDI_Desadv_Export_Format_StepDef(
			@NonNull final EDI_Desadv_StepDefData desadvTable,
			@NonNull final EDI_Desadv_Pack_Item_StepDefData packItemTable)
	{
		this.desadvTable = desadvTable;
		this.packItemTable = packItemTable;
	}

	/**
	 * Loads the live WhereClause from {@code EXP_Format_ID=540418}, applies it as a
	 * {@link TypedSqlQueryFilter} on {@link I_EDI_Desadv_Pack_Item} records belonging to the
	 * given DESADV (via the pack), and asserts that the resulting set of records equals exactly
	 * the listed pack-item identifiers.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code EDI_Desadv_ID} – identifier of the DESADV whose packs are examined</li>
	 *   <li>{@code EDI_Desadv_Pack_Item_ID} – comma-separated identifiers expected to be selected (one row per item)</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Then the DESADV pack-item export-format selects only:
	 *   | EDI_Desadv_ID | EDI_Desadv_Pack_Item_ID |
	 *   | myDesadv      | pi_nonzero              |
	 * </pre>
	 */
	@Then("the DESADV pack-item export-format selects only:")
	public void desadv_pack_item_export_format_selects_only(@NonNull final DataTable dataTable)
	{
		// Load the WhereClause from the live EXP_Format record
		final I_EXP_Format expFormat = InterfaceWrapperHelper.load(EXP_FORMAT_ID_PACK_ITEM, I_EXP_Format.class);
		assertThat(expFormat).as("EXP_Format with ID=%s must exist", EXP_FORMAT_ID_PACK_ITEM).isNotNull();
		final String whereClause = expFormat.getWhereClause();
		assertThat(whereClause).as("EXP_Format.WhereClause must not be blank").isNotBlank();

		final DataTableRow firstRow = DataTableRows.of(dataTable).getFirstRow();
		final StepDefDataIdentifier desadvIdentifier = firstRow.getAsIdentifier(I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID);
		final I_EDI_Desadv desadvRecord = desadvTable.get(desadvIdentifier);
		final int desadvId = desadvRecord.getEDI_Desadv_ID();

		// Collect all expected pack-item identifiers from every row in the table
		final Set<StepDefDataIdentifier> expectedIdentifiers = DataTableRows.of(dataTable)
				.stream()
				.map(row -> row.getAsIdentifier(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_Item_ID))
				.collect(ImmutableSet.toImmutableSet());

		// Resolve expected IDs from the StepDefData registry
		final Set<Integer> expectedPackItemIds = expectedIdentifiers.stream()
				.map(id -> packItemTable.get(id).getEDI_Desadv_Pack_Item_ID())
				.collect(ImmutableSet.toImmutableSet());

		// Find all packs that belong to the given DESADV
		final Set<Integer> packIdsForDesadv = queryBL.createQueryBuilder(I_EDI_Desadv_Pack.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_Desadv_Pack.COLUMNNAME_EDI_Desadv_ID, desadvId)
				.create()
				.list(I_EDI_Desadv_Pack.class)
				.stream()
				.map(I_EDI_Desadv_Pack::getEDI_Desadv_Pack_ID)
				.collect(ImmutableSet.toImmutableSet());

		assertThat(packIdsForDesadv)
				.as("There must be at least one EDI_Desadv_Pack for EDI_Desadv_ID=%s", desadvId)
				.isNotEmpty();

		// Apply the live WhereClause filter to pack items that are in those packs
		final Set<Integer> actualPackItemIds = queryBL.createQueryBuilder(I_EDI_Desadv_Pack_Item.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_ID, packIdsForDesadv)
				.filter(TypedSqlQueryFilter.of(whereClause))
				.create()
				.list(I_EDI_Desadv_Pack_Item.class)
				.stream()
				.map(I_EDI_Desadv_Pack_Item::getEDI_Desadv_Pack_Item_ID)
				.collect(ImmutableSet.toImmutableSet());

		assertThat(actualPackItemIds)
				.as("Pack items selected by EXP_Format WhereClause for EDI_Desadv_ID=%s must match exactly the expected identifiers.\n"
						+ "WhereClause used: %s\n"
						+ "Expected pack-item IDs: %s\n"
						+ "Actual pack-item IDs:   %s",
						desadvId,
						whereClause,
						expectedPackItemIds,
						actualPackItemIds)
				.isEqualTo(expectedPackItemIds);
	}
}
