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
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.material.dispo.MaterialDispoTableRow.DDOrderRefIdentifiers;
import de.metas.cucumber.stepdefs.material.dispo.MaterialDispoTableRow.Distribution;
import de.metas.cucumber.stepdefs.material.dispo.MaterialDispoTableRow.PPOrderRefIdentifiers;
import de.metas.cucumber.stepdefs.material.dispo.MaterialDispoTableRow.Production;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.product.ProductId;
import io.cucumber.datatable.TableTransformer;
import io.cucumber.java.DataTableType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Transforms Cucumber DataTables into {@link MD_Candidate_StepDefTable} for MD_Candidate assertion steps.
 *
 * <h3>Required columns</h3>
 * <ul>
 *   <li>{@code Identifier} — record identifier for cross-step references (auto-generated if absent)</li>
 *   <li>{@code MD_Candidate_Type} — e.g. DEMAND, SUPPLY, STOCK, STOCK_UP</li>
 *   <li>{@code M_Product_ID} — product identifier</li>
 *   <li>{@code DateProjected} — candidate date in ISO-8601 UTC (e.g. {@code 2024-09-22T21:00:00Z}).
 *       <b>Mandatory</b> — used both as query filter and assertion.
 *       Alternative: {@code DateProjected_LocalTimeZone} (converted via system timezone).</li>
 *   <li>{@code Qty} — expected quantity (automatically negated for stock-decreasing types)</li>
 * </ul>
 *
 * <h3>Optional columns</h3>
 * <ul>
 *   <li>{@code MD_Candidate_BusinessCase} — e.g. SHIPMENT, PRODUCTION, PURCHASE, DISTRIBUTION</li>
 *   <li>{@code ATP} or {@code Qty_AvailableToPromise} — expected ATP (defaults to 0)</li>
 *   <li>{@code M_Warehouse_ID} — warehouse identifier</li>
 *   <li>{@code M_AttributeSetInstance_ID} — ASI identifier</li>
 *   <li>{@code simulated} — whether the candidate is simulated (defaults to false)</li>
 *   <li>Production detail columns: {@code PP_Order_Candidate_ID}, {@code PP_OrderLine_Candidate_ID},
 *       {@code PP_Order_ID}, {@code PP_Order_BOMLine_ID}</li>
 *   <li>Distribution detail columns (stored in MD_Candidate_Dist_Detail):
 *       {@code DD_Order_Candidate_ID} — identifier of the DD_Order_Candidate that created this candidate,
 *       {@code DD_Order_ID} — identifier of the completed DD_Order,
 *       {@code DD_OrderLine_ID} — identifier of the DD_OrderLine</li>
 *   <li>Distribution forward PP_Order columns:
 *       {@code Forward_PP_Order_Candidate_ID}, {@code Forward_PP_OrderLine_Candidate_ID},
 *       {@code Forward_PP_Order_ID}, {@code Forward_PP_Order_BOMLine_ID}</li>
 * </ul>
 */
@RequiredArgsConstructor
public class MD_Candidate_StepDefTableTransformer implements TableTransformer<MD_Candidate_StepDefTable>
{
	private final M_Product_StepDefData productTable;
	private final M_Warehouse_StepDefData warehouseTable;

	@DataTableType
	@Override
	public MD_Candidate_StepDefTable transform(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		// NOTE: don't call dataTable.asMaps() here because that will fail, instead get the raw (not converted/transformed) rows.
		final List<Map<String, String>> rawRowsList = dataTable.entries();

		return DataTableRows.ofListOfMaps(rawRowsList)
				.setAdditionalRowIdentifierColumnName("MD_Candidate_ID")
				.stream()
				.map(this::toMaterialDispoTableRow)
				.collect(MD_Candidate_StepDefTable.collect());
	}

	private MaterialDispoTableRow toMaterialDispoTableRow(final DataTableRow row)
	{
		return MaterialDispoTableRow.builder()
				.identifier(extractIdentifier(row))
				.type(extractType(row))
				.businessCase(row.getAsOptionalEnum(I_MD_Candidate.COLUMNNAME_MD_Candidate_BusinessCase, CandidateBusinessCase.class).orElse(null))
				.productId(extractProductId(row))
				.time(extractDateProjected(row))
				.qty(extractQty(row))
				.atp(extractATP(row))
				.attributeSetInstanceId(row.getAsOptionalIdentifier("M_AttributeSetInstance_ID").orElse(null))
				.simulated(row.getAsOptionalBoolean("simulated").orElseFalse())
				.warehouseId(row.getAsOptionalIdentifier("M_Warehouse_ID").map(warehouseTable::getId).orElse(null))
				.distribution(extractDistribution(row))
				.production(extractProduction(row))
				//
				.rawValues(row)
				//
				.build();
	}

	public static BigDecimal extractATP(final DataTableRow row)
	{
		return CoalesceUtil.coalesceSuppliersNotNull(
				() -> row.getAsOptionalBigDecimal("ATP").orElse(null),
				() -> row.getAsOptionalBigDecimal("Qty_AvailableToPromise").orElse(null),
				() -> BigDecimal.ZERO
		);
	}

	private static @NonNull StepDefDataIdentifier extractIdentifier(final DataTableRow row)
	{
		return CoalesceUtil.coalesceSuppliersNotNull(
				() -> row.getAsOptionalIdentifier().orElse(null),
				StepDefDataIdentifier::nextUnnamed
		);
	}

	private static BigDecimal extractQty(final DataTableRow row)
	{
		BigDecimal qty = row.getAsBigDecimal("Qty");
		final CandidateType type = extractType(row);
		if (type.isDecreasingStock())
		{
			qty = qty.negate();
		}
		return qty;
	}

	private static CandidateType extractType(final DataTableRow row)
	{
		return row.getAsEnum(I_MD_Candidate.COLUMNNAME_MD_Candidate_Type, CandidateType.class);
	}

	/**
	 * Extracts the projected date from the data table row.
	 * Tries {@code DateProjected_LocalTimeZone} first (converted via system timezone),
	 * falls back to {@code DateProjected} (parsed as UTC instant).
	 * <p>
	 * <b>At least one of these columns must be present</b> — otherwise an {@link org.adempiere.exceptions.AdempiereException} is thrown.
	 */
	private static Instant extractDateProjected(final DataTableRow row)
	{
		return row.getAsOptionalLocalDateTime("DateProjected_LocalTimeZone")
				.map(localDateTime -> localDateTime.atZone(SystemTime.zoneId()).toInstant())
				.orElseGet(() -> row.getAsInstant("DateProjected"));
	}

	private ProductId extractProductId(final DataTableRow row)
	{
		final StepDefDataIdentifier productIdentifier = row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID);
		return productTable.getIdOptional(productIdentifier)
				.orElseGet(() -> productIdentifier.getAsId(ProductId.class));
	}

	@Nullable
	private static Distribution extractDistribution(final DataTableRow row)
	{
		return Distribution.builder()
				.ddOrderRef(extractDistribution_DDOrderRef(row))
				.forwardPPOrderRef(extractDistribution_ForwardPPOrderRef(row))
				.build()
				.toNullIfEmpty();
	}

	@Nullable
	private static DDOrderRefIdentifiers extractDistribution_DDOrderRef(final DataTableRow row)
	{
		return DDOrderRefIdentifiers.builder()
				.ddOrderCandidateId(row.getAsOptionalIdentifier(I_MD_Candidate_Dist_Detail.COLUMNNAME_DD_Order_Candidate_ID).orElse(null))
				.ddOrderId(row.getAsOptionalIdentifier(I_MD_Candidate_Dist_Detail.COLUMNNAME_DD_Order_ID).orElse(null))
				.ddOrderLineId(row.getAsOptionalIdentifier(I_MD_Candidate_Dist_Detail.COLUMNNAME_DD_OrderLine_ID).orElse(null))
				.build()
				.toNullIfEmpty();
	}

	@Nullable
	private static PPOrderRefIdentifiers extractDistribution_ForwardPPOrderRef(final DataTableRow row)
	{
		return PPOrderRefIdentifiers.builder()
				.ppOrderCandidateId(row.getAsOptionalIdentifier("Forward_PP_Order_Candidate_ID").orElse(null))
				.ppOrderLineCandidateId(row.getAsOptionalIdentifier("Forward_PP_OrderLine_Candidate_ID").orElse(null))
				.ppOrderId(row.getAsOptionalIdentifier("Forward_PP_Order_ID").orElse(null))
				.ppOrderBOMLineId(row.getAsOptionalIdentifier("Forward_PP_Order_BOMLine_ID").orElse(null))
				.build()
				.toNullIfEmpty();
	}

	@Nullable
	private static Production extractProduction(final DataTableRow row)
	{
		return Production.builder()
				.ppOrderRef(extractProduction_PPOrderRef(row))
				.build()
				.toNullIfEmpty();
	}

	@Nullable
	private static PPOrderRefIdentifiers extractProduction_PPOrderRef(final DataTableRow row)
	{
		return PPOrderRefIdentifiers.builder()
				.ppOrderCandidateId(row.getAsOptionalIdentifier(I_MD_Candidate_Prod_Detail.COLUMNNAME_PP_Order_Candidate_ID).orElse(null))
				.ppOrderLineCandidateId(row.getAsOptionalIdentifier(I_MD_Candidate_Prod_Detail.COLUMNNAME_PP_OrderLine_Candidate_ID).orElse(null))
				.ppOrderId(row.getAsOptionalIdentifier(I_MD_Candidate_Prod_Detail.COLUMNNAME_PP_Order_ID).orElse(null))
				.ppOrderBOMLineId(row.getAsOptionalIdentifier(I_MD_Candidate_Prod_Detail.COLUMNNAME_PP_Order_BOMLine_ID).orElse(null))
				.build()
				.toNullIfEmpty();
	}

}
