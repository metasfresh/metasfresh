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

package de.metas.cucumber.stepdefs.qtyreservation;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.handlingunits.QtyTU;
import de.metas.inoutcandidate.qty_reservation.MakeQtyReservationCommand;
import de.metas.inoutcandidate.qty_reservation.MaterialCockpitV2RowVO;
import de.metas.inoutcandidate.qty_reservation.QtyReservationId;
import de.metas.inoutcandidate.qty_reservation.QtyReservationService;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.project.service.ProjectRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Step definitions for querying {@code QtyDemand_QtySupply_V} (or its customer-specific extension like {@code SE203_MaterialCockpit_V})
 * and making reservations from view rows.
 *
 * <p>Uses raw SQL to query the view because this is a DB view without a Java model class.
 * The reservation command delegates to {@link MakeQtyReservationCommand}.
 *
 * <h2>Steps</h2>
 *
 * <h3>{@code QtyDemand_QtySupply_V is queried for product {string} and warehouse {string}:}</h3>
 * <p>Queries the view and stores results internally, keyed by the Identifier column.
 * <pre>
 * When QtyDemand_QtySupply_V is queried for product "product" and warehouse "warehouse":
 *   | Identifier | SupplyType | AvailabilityStatus |
 *   | row_oh     | OH         | A                  |
 * </pre>
 * <ul>
 *   <li><b>Identifier</b> — (required) alias for cross-step reference of this view row</li>
 *   <li><b>SupplyType</b> — (optional) filter: only rows with this SupplyType</li>
 *   <li><b>AvailabilityStatus</b> — (optional) filter: only rows with this AvailabilityStatus</li>
 * </ul>
 *
 * <h3>{@code make qty reservation from QtyDemand_QtySupply_V row {string} for order line {string} with QtyTU {int}}</h3>
 * <p>Executes a reservation using a previously queried view row.
 *
 * <h3>{@code make qty reservation from QtyDemand_QtySupply_V row {string} for order line {string} with QtyTU {int} expecting error}</h3>
 * <p>Same as above but expects the reservation to fail with an exception.
 *
 * <h3>{@code validate QtyDemand_QtySupply_V:}</h3>
 * <p>Re-queries the view and validates field values against expectations.
 * <pre>
 * Then validate QtyDemand_QtySupply_V:
 *   | Identifier | M_Product_ID | M_Warehouse_ID | SupplyType | AvailabilityStatus | QtyTU | QtyStock |
 *   | row_oh     | product      | warehouse      | OH         | A                  | 2     | 20       |
 * </pre>
 */
@RequiredArgsConstructor
public class QtyDemand_QtySupply_V_StepDef
{
	@NonNull private final QtyReservationService qtyReservationService = SpringContextHolder.instance.getBean(QtyReservationService.class);

	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final M_QtyReservation_StepDefData qtyReservationTable;

	private final Map<String, MaterialCockpitV2RowVO> viewRowsByIdentifier = new LinkedHashMap<>();

	/**
	 * Queries the {@code QtyDemand_QtySupply_V} view for a given product and warehouse.
	 * Each DataTable row specifies an Identifier for the result row, plus optional SupplyType/AvailabilityStatus filters.
	 * Matched view rows are stored for later use by the reservation and validation steps.
	 */
	@When("QtyDemand_QtySupply_V is queried for product {string} and warehouse {string}:")
	public void queryView(
			@NonNull final String productIdentifier,
			@NonNull final String warehouseIdentifier,
			@NonNull final DataTable dataTable) throws SQLException
	{
		final I_M_Product product = productTable.get(productIdentifier);
		final I_M_Warehouse warehouse = warehouseTable.get(warehouseIdentifier);

		final List<MaterialCockpitV2RowVO> allRows = queryViewRows(
				ProductId.ofRepoId(product.getM_Product_ID()),
				WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()));

		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier identifier = row.getAsIdentifier();
			final String expectedSupplyType = row.getAsOptionalString("SupplyType").orElse(null);
			final String expectedAvailability = row.getAsOptionalString("AvailabilityStatus").orElse(null);

			final MaterialCockpitV2RowVO matched = allRows.stream()
					.filter(viewRow -> matchesFilters(viewRow, expectedSupplyType, expectedAvailability))
					.findFirst()
					.orElseThrow(() -> new AdempiereException("No QtyDemand_QtySupply_V row found for product="
							+ product.getM_Product_ID()
							+ ", warehouse=" + warehouse.getM_Warehouse_ID()
							+ ", supplyType=" + expectedSupplyType
							+ ", availability=" + expectedAvailability));

			viewRowsByIdentifier.put(identifier.getAsString(), matched);
		});
	}

	/**
	 * Makes a qty reservation using a previously queried view row.
	 */
	@And("make qty reservation from QtyDemand_QtySupply_V row {string} for order line {string} with QtyTU {int}")
	public void makeReservation(
			@NonNull final String viewRowIdentifier,
			@NonNull final String orderLineIdentifier,
			final int qtyTU)
	{
		final MaterialCockpitV2RowVO rowVO = getViewRow(viewRowIdentifier);
		final OrderAndLineId orderAndLineId = orderLineTable.getOrderAndLineId(StepDefDataIdentifier.ofString(orderLineIdentifier));

		final ProjectRepository projectRepository = SpringContextHolder.instance.getBeanOr(ProjectRepository.class, null);

		final QtyReservationId reservationId = MakeQtyReservationCommand.builder()
				.qtyReservationService(qtyReservationService)
				.projectRepository(projectRepository)
				.rowVO(rowVO)
				.salesOrderAndLineId(orderAndLineId)
				.qtyToReserveTU(QtyTU.ofInt(qtyTU))
				.build()
				.execute();

		qtyReservationTable.putOrReplace(viewRowIdentifier, reservationId);
	}

	/**
	 * Re-queries the view for the same product/warehouse as the given row identifier
	 * and asserts that no row with AvailabilityStatus=A and positive QtyTU exists.
	 * This validates that the over-reservation prevention in the cockpit view works:
	 * once all TU are reserved, the view no longer shows available stock.
	 */
	@And("QtyDemand_QtySupply_V row {string} is no longer available for reservation")
	public void assertNotAvailableForReservation(@NonNull final String viewRowIdentifier) throws SQLException
	{
		final MaterialCockpitV2RowVO originalRow = getViewRow(viewRowIdentifier);

		final List<MaterialCockpitV2RowVO> freshRows = queryViewRows(
				originalRow.getProductId(),
				originalRow.getWarehouseId());

		final boolean anyAvailable = freshRows.stream()
				.anyMatch(MaterialCockpitV2RowVO::isAvailableForReservation);

		if (anyAvailable)
		{
			final MaterialCockpitV2RowVO availableRow = freshRows.stream()
					.filter(MaterialCockpitV2RowVO::isAvailableForReservation)
					.findFirst()
					.orElseThrow(() -> new AdempiereException("unreachable"));
			throw new AssertionError("Expected no available rows for reservation but found row with QtyTU="
					+ availableRow.getQtyTU().toInt()
					+ ", AvailabilityType=" + availableRow.getAvailabilityType()
					+ ", SupplyType=" + availableRow.getSupplyType());
		}
	}

	/**
	 * Re-queries the view for each row's product/warehouse and validates the expected column values.
	 *
	 * <p>Required columns: Identifier, M_Product_ID, M_Warehouse_ID.
	 * Optional columns: SupplyType, AvailabilityStatus, QtyTU, QtyStock.
	 */
	@Then("validate QtyDemand_QtySupply_V:")
	public void validateView(@NonNull final DataTable dataTable) throws SQLException
	{
		DataTableRows.of(dataTable).forEach(row -> {
			try
			{
				validateViewRow(row);
			}
			catch (final SQLException e)
			{
				throw new AdempiereException("Failed to validate QtyDemand_QtySupply_V", e);
			}
		});
	}

	private void validateViewRow(@NonNull final DataTableRow row) throws SQLException
	{
		final I_M_Product product = productTable.get(row.getAsIdentifier("M_Product_ID"));
		final I_M_Warehouse warehouse = warehouseTable.get(row.getAsIdentifier("M_Warehouse_ID"));
		final String expectedSupplyType = row.getAsOptionalString("SupplyType").orElse(null);
		final String expectedAvailability = row.getAsOptionalString("AvailabilityStatus").orElse(null);

		final List<MaterialCockpitV2RowVO> allRows = queryViewRows(
				ProductId.ofRepoId(product.getM_Product_ID()),
				WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()));

		final MaterialCockpitV2RowVO viewRow = allRows.stream()
				.filter(r -> matchesFilters(r, expectedSupplyType, expectedAvailability))
				.findFirst()
				.orElse(null);

		final SoftAssertions softly = new SoftAssertions();

		row.getAsOptionalInt("QtyTU").ifPresent(expectedQtyTU ->
		{
			softly.assertThat(viewRow).as("View row not found").isNotNull();
			if (viewRow != null)
			{
				softly.assertThat(viewRow.getQtyTU().toInt())
						.as("QtyTU")
						.isEqualTo(expectedQtyTU);
			}
		});

		row.getAsOptionalBigDecimal("QtyStock").ifPresent(expectedQtyStock ->
		{
			softly.assertThat(viewRow).as("View row not found").isNotNull();
			if (viewRow != null)
			{
				softly.assertThat(viewRow.getQtyStock().toBigDecimal())
						.as("QtyStock")
						.isEqualByComparingTo(expectedQtyStock);
			}
		});

		softly.assertAll();
	}

	// --- helpers ---

	private List<MaterialCockpitV2RowVO> queryViewRows(
			@NonNull final ProductId productId,
			@NonNull final WarehouseId warehouseId) throws SQLException
	{
		final String sql = "SELECT * FROM QtyDemand_QtySupply_V"
				+ " WHERE M_Product_ID=? AND M_Warehouse_ID=?"
				+ " ORDER BY SupplyType, AvailabilityStatus";

		final List<MaterialCockpitV2RowVO> rows = new ArrayList<>();
		try (final PreparedStatement pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None))
		{
			pstmt.setInt(1, productId.getRepoId());
			pstmt.setInt(2, warehouseId.getRepoId());
			try (final ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					rows.add(MaterialCockpitV2RowVO.ofResultSet(rs));
				}
			}
		}
		return rows;
	}

	private static boolean matchesFilters(
			@NonNull final MaterialCockpitV2RowVO row,
			@Nullable final String expectedSupplyType,
			@Nullable final String expectedAvailability)
	{
		if (expectedSupplyType != null && !row.getSupplyType().getCode().equals(expectedSupplyType))
		{
			return false;
		}
		if (expectedAvailability != null && !row.getAvailabilityType().getCode().equals(expectedAvailability))
		{
			return false;
		}
		return true;
	}

	@NonNull
	private MaterialCockpitV2RowVO getViewRow(@NonNull final String identifier)
	{
		final MaterialCockpitV2RowVO row = viewRowsByIdentifier.get(identifier);
		if (row == null)
		{
			throw new AdempiereException("No QtyDemand_QtySupply_V row found with identifier: " + identifier);
		}
		return row;
	}
}
