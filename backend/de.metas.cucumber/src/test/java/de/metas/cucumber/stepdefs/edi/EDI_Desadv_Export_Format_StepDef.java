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
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.compiere.model.I_EXP_Format;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions that verify which EDI_Desadv_Pack_Item records the live
 * {@code EXP_Format} with Name {@code EDI_Exp_Desadv_Pack_Item} WhereClause
 * would select for a given DESADV.
 *
 * <p>The invariant under test: the export format must select pack items by the
 * item's own {@code MovementQty>0}, not the parent line's {@code QtyDeliveredInUOM}.
 * A pack item with {@code MovementQty=0} on a line whose total
 * {@code QtyDeliveredInUOM>0} must be excluded from the export.
 */
@RequiredArgsConstructor
public class EDI_Desadv_Export_Format_StepDef
{
	/**
	 * Name of the {@code EXP_Format} row for pack-item export.
	 * Used to look up the record via IQueryBL rather than hardcoding its AD_ID.
	 */
	private static final String EXP_FORMAT_NAME_PACK_ITEM = "EDI_Exp_Desadv_Pack_Item";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final EDI_Desadv_StepDefData desadvTable;
	@NonNull private final EDI_Desadv_Pack_Item_StepDefData packItemTable;

	/**
	 * Loads the live WhereClause from the {@code EXP_Format} named
	 * {@value #EXP_FORMAT_NAME_PACK_ITEM}, applies it as a
	 * {@link TypedSqlQueryFilter} on {@link I_EDI_Desadv_Pack_Item} records belonging to the
	 * given DESADV (via the pack), and asserts that the resulting set of records equals exactly
	 * the listed pack-item identifiers.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code EDI_Desadv_ID} – identifier of the DESADV whose packs are examined</li>
	 *   <li>{@code EDI_Desadv_Pack_Item_ID} – identifiers expected to be selected (one row per item)</li>
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
		final I_EXP_Format expFormat = queryBL.createQueryBuilder(I_EXP_Format.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EXP_Format.COLUMNNAME_Name, EXP_FORMAT_NAME_PACK_ITEM)
				.create()
				.firstOnlyNotNull(I_EXP_Format.class);
		final String whereClause = expFormat.getWhereClause();
		assertThat(whereClause).as("EXP_Format '%s' WhereClause must not be blank", EXP_FORMAT_NAME_PACK_ITEM).isNotBlank();

		final DataTableRows rows = DataTableRows.of(dataTable);
		// Guard: all DataTable rows must reference the same DESADV; a caller passing rows for
		// different DESADVs would be silently broken (only the first row's DESADV is used).
		final Set<StepDefDataIdentifier> distinctDesadvIdentifiers = rows.stream()
				.map(row -> row.getAsIdentifier(I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID))
				.collect(ImmutableSet.toImmutableSet());
		assertThat(distinctDesadvIdentifiers)
				.as("All DataTable rows must reference the same EDI_Desadv_ID identifier, but found multiple: %s", distinctDesadvIdentifiers)
				.hasSize(1);

		final DataTableRow firstRow = rows.getFirstRow();
		final StepDefDataIdentifier desadvIdentifier = firstRow.getAsIdentifier(I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID);
		final I_EDI_Desadv desadvRecord = desadvTable.get(desadvIdentifier);
		final int desadvId = desadvRecord.getEDI_Desadv_ID();

		final Set<StepDefDataIdentifier> expectedIdentifiers = rows.stream()
				.map(row -> row.getAsIdentifier(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_Item_ID))
				.collect(ImmutableSet.toImmutableSet());

		final Set<Integer> expectedPackItemIds = expectedIdentifiers.stream()
				.map(id -> packItemTable.get(id).getEDI_Desadv_Pack_Item_ID())
				.collect(ImmutableSet.toImmutableSet());

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
				.as("Pack items selected by EXP_Format '%s' WhereClause for EDI_Desadv_ID=%s must match exactly the expected identifiers.\n"
								+ "WhereClause used: %s\n"
								+ "Expected pack-item IDs: %s\n"
								+ "Actual pack-item IDs:   %s",
						EXP_FORMAT_NAME_PACK_ITEM,
						desadvId,
						whereClause,
						expectedPackItemIds,
						actualPackItemIds)
				.isEqualTo(expectedPackItemIds);
	}
}
