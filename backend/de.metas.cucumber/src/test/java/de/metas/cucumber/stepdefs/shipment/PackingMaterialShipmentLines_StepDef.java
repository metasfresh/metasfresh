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

package de.metas.cucumber.stepdefs.shipment;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.project.C_Project_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.IInOutDAO;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.fail;

/**
 * Assertions over shipments generated from {@link de.metas.inoutcandidate.model.I_M_ShipmentSchedule}s, focused on the
 * per-{@code C_Project_ID} split of the shipment's packing-material lines.
 */
@RequiredArgsConstructor
public class PackingMaterialShipmentLines_StepDef
{
	private final M_InOut_StepDefData inoutTable;
	private final M_InOutLine_StepDefData inoutLineTable;
	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	private final M_Product_StepDefData productTable;
	private final C_Project_StepDefData projectTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);

	/**
	 * Waits (up to 60s) until every given shipment schedule's picked quantity is linked to exactly one {@code M_InOut},
	 * then registers that shipment. Fails immediately if the schedules are already split across more than one shipment.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns (none — parameters are in the step text, not a DataTable)
	 * @cucumber.depends StepDefData: M_ShipmentSchedule_StepDefData, M_InOut_StepDefData
	 * @cucumber.example <pre>
	 * And the shipment schedules schedule_P1,schedule_P2,schedule_P3 are shipped in exactly one M_InOut identified by shipment_1
	 * </pre>
	 */
	@And("^the shipment schedules (.*) are shipped in exactly one M_InOut identified by (.*)$")
	public void the_shipment_schedules_are_shipped_in_exactly_one_M_InOut(
			@NonNull final String scheduleIdentifiersCSV,
			@NonNull final String shipmentIdentifierString) throws InterruptedException
	{
		final Set<Integer> shipmentScheduleIds = StepDefDataIdentifier.ofCommaSeparatedString(scheduleIdentifiersCSV)
				.stream()
				.map(shipmentScheduleTable::getId)
				.map(ShipmentScheduleId::getRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final StepDefDataIdentifier shipmentIdentifier = StepDefDataIdentifier.ofString(shipmentIdentifierString);

		final Supplier<Boolean> isShippedInExactlyOneInOut = () -> {

			final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = queryBL
					.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds)
					.addNotNull(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_InOutLine_ID)
					.create()
					.list(I_M_ShipmentSchedule_QtyPicked.class);

			if (qtyPickedRecords.isEmpty())
			{
				return false;
			}

			final Set<Integer> linkedScheduleIds = qtyPickedRecords.stream()
					.map(I_M_ShipmentSchedule_QtyPicked::getM_ShipmentSchedule_ID)
					.collect(ImmutableSet.toImmutableSet());
			if (!linkedScheduleIds.containsAll(shipmentScheduleIds))
			{
				return false;
			}

			final Set<Integer> inOutLineIds = qtyPickedRecords.stream()
					.map(I_M_ShipmentSchedule_QtyPicked::getM_InOutLine_ID)
					.collect(ImmutableSet.toImmutableSet());

			final Set<Integer> inOutIds = queryBL
					.createQueryBuilder(I_M_InOutLine.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID, inOutLineIds)
					.create()
					.stream()
					.map(I_M_InOutLine::getM_InOut_ID)
					.collect(ImmutableSet.toImmutableSet());

			if (inOutIds.size() > 1)
			{
				throw new AdempiereException("Expected the shipment schedules identified by `" + scheduleIdentifiersCSV
						+ "` to be shipped in exactly one M_InOut, but found " + inOutIds.size() + ": " + inOutIds);
			}

			if (inOutIds.isEmpty())
			{
				return false;
			}

			final I_M_InOut inOut = inOutDAO.getById(InOutId.ofRepoId(inOutIds.iterator().next()));
			inoutTable.putOrReplace(shipmentIdentifier, inOut);
			return true;
		};

		StepDefUtil.tryAndWait(60, 500, isShippedInExactlyOneInOut);
	}

	/**
	 * Asserts that the shipment's packing-material lines ({@code IsPackagingMaterial='Y'}) are exactly the given set,
	 * matched by (product, project, quantity), order-independent. On a mismatch it fails with the full list of the
	 * shipment's actual packing-material lines. Every matched line is registered under its {@code Identifier}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias to store the matched line under, in M_InOutLine_StepDefData<br>
	 *   <b>M_Product_ID</b> — (required, identifier-ref) expected packing-material product<br>
	 *   <b>C_Project_ID</b> — (required, identifier-ref, null-allowed) expected project, or literal "null" for none<br>
	 *   <b>MovementQty</b> — (required) expected quantity<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData, M_InOutLine_StepDefData, M_Product_StepDefData, C_Project_StepDefData
	 * @cucumber.example <pre>
	 * And the packing material lines of shipment shipment_1 are exactly:
	 *   | Identifier | M_Product_ID | C_Project_ID | MovementQty |
	 *   | pm_line_P1 | p_pm         | p1           | 3           |
	 *   | pm_line_P2 | p_pm         | p2           | 3           |
	 *   | pm_line_P3 | p_pm         | null         | 3           |
	 * </pre>
	 */
	@And("^the packing material lines of shipment (.*) are exactly:$")
	public void the_packing_material_lines_of_shipment_are_exactly(
			@NonNull final String shipmentIdentifierString,
			@NonNull final DataTable dataTable)
	{
		final I_M_InOut shipment = inoutTable.get(StepDefDataIdentifier.ofString(shipmentIdentifierString));

		final List<de.metas.handlingunits.model.I_M_InOutLine> remainingActualLines =
				new ArrayList<>(huInOutDAO.retrievePackingMaterialLines(shipment));

		DataTableRows.of(dataTable).forEach(row -> matchAndRegisterPackingLine(shipmentIdentifierString, shipment, row, remainingActualLines));

		if (!remainingActualLines.isEmpty())
		{
			fail("Shipment " + shipmentIdentifierString + " has " + remainingActualLines.size()
					+ " unexpected extra packing material line(s). Actual packing material lines of shipment "
					+ shipmentIdentifierString + ":\n" + formatActualLines(huInOutDAO.retrievePackingMaterialLines(shipment)));
		}
	}

	private void matchAndRegisterPackingLine(
			@NonNull final String shipmentIdentifierString,
			@NonNull final I_M_InOut shipment,
			@NonNull final DataTableRow row,
			@NonNull final List<de.metas.handlingunits.model.I_M_InOutLine> remainingActualLines)
	{
		final StepDefDataIdentifier lineIdentifier = row.getAsIdentifier("Identifier");
		final ProductId expectedProductId = productTable.getId(row.getAsIdentifier("M_Product_ID"));
		final ProjectId expectedProjectId = projectTable.getIdOfNullable(row.getAsIdentifier("C_Project_ID"));
		final BigDecimal expectedQty = row.getAsBigDecimal("MovementQty");

		final de.metas.handlingunits.model.I_M_InOutLine matchedLine = remainingActualLines.stream()
				.filter(line -> line.getM_Product_ID() == expectedProductId.getRepoId())
				.filter(line -> projectMatches(line.getC_Project_ID(), expectedProjectId))
				.filter(line -> line.getMovementQty().compareTo(expectedQty) == 0)
				.findFirst()
				.orElseGet(() -> {
					fail("No packing material line of shipment " + shipmentIdentifierString
							+ " matches Identifier=" + lineIdentifier.getAsString()
							+ " (M_Product_ID=" + expectedProductId + ", C_Project_ID=" + expectedProjectId + ", MovementQty=" + expectedQty
							+ "). Actual packing material lines of shipment " + shipmentIdentifierString + ":\n"
							+ formatActualLines(huInOutDAO.retrievePackingMaterialLines(shipment)));
					return null; // unreachable, fail() always throws
				});

		remainingActualLines.remove(matchedLine);
		inoutLineTable.putOrReplace(lineIdentifier, matchedLine);
	}

	private static boolean projectMatches(final int actualProjectRepoId, @Nullable final ProjectId expectedProjectId)
	{
		return expectedProjectId == null
				? actualProjectRepoId <= 0
				: actualProjectRepoId == expectedProjectId.getRepoId();
	}

	private static String formatActualLines(@NonNull final List<de.metas.handlingunits.model.I_M_InOutLine> actualLines)
	{
		if (actualLines.isEmpty())
		{
			return "(none)";
		}

		return actualLines.stream()
				.map(line -> "M_Product_ID=" + line.getM_Product_ID()
						+ ", C_Project_ID=" + (line.getC_Project_ID() > 0 ? line.getC_Project_ID() : "none")
						+ ", MovementQty=" + line.getMovementQty())
				.collect(Collectors.joining("\n"));
	}

	/**
	 * Asserts the packing-material section of the shipment report — the {@code de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU(M_InOut_ID, AD_Language)}
	 * DB function, which the shipment's Jasper report calls to render its packing-material lines — groups the shipment's per-{@code C_Project_ID}-split
	 * packing-material {@code M_InOutLine}s back down to one summed row per product (the function groups by product name/UOM/description, not by project),
	 * regardless of how many project-scoped lines {@code HUShipmentPackingMaterialLinesBuilder} split the shipment into.
	 * <p>
	 * Queries the DB function directly via JDBC (no Java model class exists for its result type). On a mismatch this step fails, printing every
	 * actual row the function returned. <b>If the DB function does not exist (e.g. dropped/renamed), the underlying {@link SQLException} propagates
	 * and this step FAILS — it never skips or passes silently.</b>
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) expected packing-material product<br>
	 *   <b>MovementQty</b> — (required) expected quantity, summed across every row the function returns for this product<br>
	 *   <b>RowCount</b> — (required) expected number of raw rows the function returns for this product (normally 1 — proving the per-project HU split is re-aggregated by the report)<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData, M_Product_StepDefData
	 * @cucumber.example <pre>
	 * And the shipment report packing section of shipment_1 in language de_DE has exactly:
	 *   | M_Product_ID | MovementQty | RowCount |
	 *   | p_pm         | 9           | 1        |
	 * </pre>
	 */
	@And("^the shipment report packing section of (.*) in language (.*) has exactly:$")
	public void the_shipment_report_packing_section_has_exactly(
			@NonNull final String shipmentIdentifierString,
			@NonNull final String adLanguage,
			@NonNull final DataTable dataTable) throws SQLException
	{
		final I_M_InOut shipment = inoutTable.get(StepDefDataIdentifier.ofString(shipmentIdentifierString));

		final List<Map<String, Object>> allRows = queryShipmentReportPackingSection(shipment.getM_InOut_ID(), adLanguage);
		final List<Map<String, Object>> remainingRows = new ArrayList<>(allRows);

		DataTableRows.of(dataTable).forEach(row -> matchAndConsumeReportRows(shipmentIdentifierString, row, remainingRows, allRows));

		if (!remainingRows.isEmpty())
		{
			fail("Shipment report packing section of " + shipmentIdentifierString + " has " + remainingRows.size()
					+ " unexpected extra row(s). Actual rows:\n" + formatReportRows(allRows));
		}
	}

	private void matchAndConsumeReportRows(
			@NonNull final String shipmentIdentifierString,
			@NonNull final DataTableRow row,
			@NonNull final List<Map<String, Object>> remainingRows,
			@NonNull final List<Map<String, Object>> allRows)
	{
		final StepDefDataIdentifier productIdentifier = row.getAsIdentifier("M_Product_ID");
		final I_M_Product product = productTable.get(productIdentifier);
		final String expectedProductName = product.getName();
		final BigDecimal expectedQty = row.getAsBigDecimal("MovementQty");
		final int expectedRowCount = row.getAsInt("RowCount");

		final List<Map<String, Object>> matchedRows = new ArrayList<>();
		final Iterator<Map<String, Object>> remainingRowsIterator = remainingRows.iterator();
		while (remainingRowsIterator.hasNext())
		{
			final Map<String, Object> candidate = remainingRowsIterator.next();
			if (expectedProductName.equals(candidate.get("name")))
			{
				matchedRows.add(candidate);
				remainingRowsIterator.remove();
			}
		}

		if (matchedRows.size() != expectedRowCount)
		{
			fail("Expected " + expectedRowCount + " row(s) in the packing section of shipment " + shipmentIdentifierString
					+ " for product " + productIdentifier.getAsString() + " (name=" + expectedProductName + "), but found " + matchedRows.size()
					+ ". Actual rows:\n" + formatReportRows(allRows));
		}

		final BigDecimal actualQty = matchedRows.stream()
				.map(matchedRow -> (BigDecimal)matchedRow.get("movementqty"))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (actualQty.compareTo(expectedQty) != 0)
		{
			fail("Expected summed MovementQty=" + expectedQty + " in the packing section of shipment " + shipmentIdentifierString
					+ " for product " + productIdentifier.getAsString() + " (name=" + expectedProductName + "), but found " + actualQty
					+ ". Actual rows:\n" + formatReportRows(allRows));
		}
	}

	/**
	 * Raw JDBC call — no Java model class exists for {@code de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU}'s result type.
	 * A missing/renamed DB function surfaces as a {@link SQLException} thrown from {@code executeQuery()}, which this method does NOT catch,
	 * so the calling step fails instead of silently returning no rows.
	 * <p>
	 * Filters {@code IsPrintWhenPackingMaterial='Y'} to mirror the real report's own query
	 * ({@code report_details_hu.jrxml}'s {@code queryString}), so this step asserts exactly what the printed report shows.
	 */
	private List<Map<String, Object>> queryShipmentReportPackingSection(final int inOutId, @NonNull final String adLanguage) throws SQLException
	{
		final List<Map<String, Object>> rows = new ArrayList<>();
		final String sql = "SELECT * FROM de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU(?, ?) WHERE IsPrintWhenPackingMaterial='Y'";

		try (final PreparedStatement pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None))
		{
			pstmt.setInt(1, inOutId);
			pstmt.setString(2, adLanguage);

			try (final ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					final Map<String, Object> row = new LinkedHashMap<>();
					row.put("movementqty", rs.getBigDecimal("movementqty"));
					row.put("name", rs.getString("name"));
					row.put("uomsymbol", rs.getString("uomsymbol"));
					row.put("description", rs.getString("description"));
					row.put("isprintwhenpackingmaterial", rs.getString("isprintwhenpackingmaterial"));
					rows.add(row);
				}
			}
		}

		return rows;
	}

	private static String formatReportRows(@NonNull final List<Map<String, Object>> rows)
	{
		if (rows.isEmpty())
		{
			return "(none)";
		}

		return rows.stream()
				.map(String::valueOf)
				.collect(Collectors.joining("\n"));
	}
}
