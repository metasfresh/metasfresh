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

package de.metas.cucumber.stepdefs.material.dispo;

import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.product.ProductId;
import io.cucumber.datatable.TableTransformer;
import io.cucumber.java.DataTableType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MD_Candidate_StepDefTableTransformer implements TableTransformer<MD_Candidate_StepDefTable>
{
	private final M_Product_StepDefData productTable;
	private final M_Warehouse_StepDefData warehouseTable;

	@DataTableType
	@Override
	public MD_Candidate_StepDefTable transform(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		final MD_Candidate_StepDefTable.MD_Candidate_StepDefTableBuilder materialDispoTableBuilder = MD_Candidate_StepDefTable.builder();

		// NOTE: don't call dataTable.asMaps() here because that will fail, instead get the raw (not converted/transformed) rows.
		final List<Map<String, String>> rawRowsList = dataTable.entries();

		DataTableRows.ofListOfMaps(rawRowsList).forEach((row) -> {
			final StepDefDataIdentifier identifier = CoalesceUtil.coalesceSuppliersNotNull(
					() -> row.getAsOptionalIdentifier("MD_Candidate_ID").orElse(null),
					() -> row.getAsOptionalIdentifier().orElse(null),
					StepDefDataIdentifier::nextUnnamed
			);

			final CandidateType type = row.getAsEnum(I_MD_Candidate.COLUMNNAME_MD_Candidate_Type, CandidateType.class);

			final CandidateBusinessCase businessCase = row.getAsOptionalEnum(I_MD_Candidate.COLUMNNAME_MD_Candidate_BusinessCase, CandidateBusinessCase.class).orElse(null);

			final StepDefDataIdentifier productIdentifier = row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID);
			final ProductId productId = productTable.getIdOptional(productIdentifier)
					.orElseGet(() -> productIdentifier.getAsId(ProductId.class));

			final Instant dateProjected = row.getAsOptionalLocalDateTime("DateProjected_LocalTimeZone")
					.map(localDateTime -> localDateTime.atZone(SystemTime.zoneId()).toInstant())
					.orElseGet(() -> row.getAsInstant("DateProjected"));

			BigDecimal qty = row.getAsBigDecimal("Qty");

			if (type.equals(CandidateType.DEMAND) || type.equals(CandidateType.INVENTORY_DOWN) || type.equals(CandidateType.UNEXPECTED_DECREASE))
			{
				qty = qty.negate();
			}

			final BigDecimal atp = row.getAsBigDecimal("Qty_AvailableToPromise");

			final StepDefDataIdentifier attributeSetInstanceIdentifier = row.getAsOptionalIdentifier("M_AttributeSetInstance_ID").orElse(null);

			final boolean simulated = row.getAsOptionalBoolean("simulated").orElseFalse();

			final WarehouseId warehouseId = row.getAsOptionalIdentifier("M_Warehouse_ID")
					.flatMap(warehouseTable::getIdOptional)
					.orElse(null);

			final MD_Candidate_StepDefTable.MaterialDispoTableRow tableRow = MD_Candidate_StepDefTable.MaterialDispoTableRow.builder()
					.identifier(identifier)
					.type(type)
					.businessCase(businessCase)
					.productId(productId)
					.time(dateProjected)
					.qty(qty)
					.atp(atp)
					.attributeSetInstanceId(attributeSetInstanceIdentifier)
					.simulated(simulated)
					.warehouseId(warehouseId)
					.build();
			materialDispoTableBuilder.row(identifier, tableRow);
		});

		return materialDispoTableBuilder.build();
	}
}
