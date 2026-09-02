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

package de.metas.cucumber.stepdefs.deliveryplanning;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.shipment.M_ShipperTransportation_StepDefData;
import de.metas.shipping.model.I_M_ShipperTransportation;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Asserts what the three views over the delivery-planning-to-delivery-instruction allocation return for one
 * delivery instruction. They are database VIEWS with no Java model class, so the steps read them with raw SQL:
 * a join defect in a view exists only in SQL, out of reach of an in-memory test.
 * <p>
 * Every step asserts the same three-part row identity an instruction owes its plannings - <b>as many rows as it
 * has allocations and no more</b>, <b>each row carrying its OWN planning's product and quantities</b>, and <b>a
 * row key that is unique across them</b>. A package joined to the instruction instead of to its allocation row
 * breaks all three at once: it multiplies the rows into the N x N cross product.
 */
@RequiredArgsConstructor
public class Delivery_Instructions_V_StepDef
{
	@NonNull private final M_ShipperTransportation_StepDefData deliveryInstructionTable;
	@NonNull private final M_Delivery_Planning_StepDefData deliveryPlanningTable;
	@NonNull private final M_Product_StepDefData productTable;

	/**
	 * The rows {@code M_Delivery_Planning_Delivery_Instructions_V} returns for one delivery instruction: the
	 * given ones and nothing else, keyed - as the view itself is - by {@code M_ShippingPackage_ID}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning this consignment row belongs to<br>
	 *   <b>M_Product_ID</b> — (optional, identifier-ref) expected product of the row<br>
	 *   <b>ActualLoadQty</b> — (optional) expected loaded quantity of the row<br>
	 *   <b>ActualDischargeQuantity</b> — (optional) expected discharge quantity of the row<br>
	 * @cucumber.depends StepDefData: M_ShipperTransportation_StepDefData, M_Delivery_Planning_StepDefData,
	 * M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the M_ShipperTransportation identified by deliveryInstruction has exactly the following rows in M_Delivery_Planning_Delivery_Instructions_V:
	 *   | M_Delivery_Planning_ID | M_Product_ID | ActualLoadQty | ActualDischargeQuantity |
	 *   | deliveryPlanning_1     | product_1    | 7             | 0                      |
	 *   | deliveryPlanning_2     | product_2    | 3             | 0                      |
	 * </pre>
	 */
	@Then("^the M_ShipperTransportation identified by (.*) has exactly the following rows in M_Delivery_Planning_Delivery_Instructions_V:$")
	public void validate_M_Delivery_Planning_Delivery_Instructions_V(
			@NonNull final String deliveryInstructionIdentifier,
			@NonNull final DataTable dataTable)
	{
		validateView(
				"M_Delivery_Planning_Delivery_Instructions_V",
				"M_ShippingPackage_ID",
				"ActualLoadQty",
				"ActualDischargeQuantity",
				deliveryInstructionIdentifier,
				dataTable);
	}

	/**
	 * The rows {@code M_ShipperTransportation_Delivery_Instructions_V} returns for one delivery instruction: the
	 * given ones and nothing else. Its row key is composed - ({@code M_ShipperTransportation_ID},
	 * {@code M_Delivery_Planning_ID}) - because the instruction id alone repeats across the rows of an
	 * aggregated instruction.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning this consignment row belongs to<br>
	 *   <b>M_Product_ID</b> — (optional, identifier-ref) expected product of the row<br>
	 *   <b>PlannedLoadedQuantity</b> — (optional) expected loaded quantity of the row<br>
	 *   <b>PlannedDischargeQuantity</b> — (optional) expected discharge quantity of the row<br>
	 * @cucumber.depends StepDefData: M_ShipperTransportation_StepDefData, M_Delivery_Planning_StepDefData,
	 * M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the M_ShipperTransportation identified by deliveryInstruction has exactly the following rows in M_ShipperTransportation_Delivery_Instructions_V:
	 *   | M_Delivery_Planning_ID | M_Product_ID | PlannedLoadedQuantity | PlannedDischargeQuantity |
	 *   | deliveryPlanning_1     | product_1    | 7                     | 0                       |
	 *   | deliveryPlanning_2     | product_2    | 3                     | 0                       |
	 * </pre>
	 */
	@Then("^the M_ShipperTransportation identified by (.*) has exactly the following rows in M_ShipperTransportation_Delivery_Instructions_V:$")
	public void validate_M_ShipperTransportation_Delivery_Instructions_V(
			@NonNull final String deliveryInstructionIdentifier,
			@NonNull final DataTable dataTable)
	{
		validateView(
				"M_ShipperTransportation_Delivery_Instructions_V",
				// no single key column: the composed row key of this view
				"M_ShipperTransportation_ID || '-' || M_Delivery_Planning_ID",
				"PlannedLoadedQuantity",
				"PlannedDischargeQuantity",
				deliveryInstructionIdentifier,
				dataTable);
	}

	/**
	 * The rows {@code M_ShipperTransportation_Delivery_Planning_History_V} returns for one delivery instruction:
	 * one per RETIRED ({@code IsActive='N'}) allocation, keyed by {@code M_Delivery_Planning_Alloc_ID}.
	 * <p>
	 * The quantities are the planning's CURRENT ones ({@code M_Delivery_Planning.PlannedLoadedQuantity}), not a
	 * snapshot from the retired shipping package - this view joins no package at all.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning this history row belongs to<br>
	 *   <b>M_Product_ID</b> — (optional, identifier-ref) expected product of the row<br>
	 *   <b>PlannedLoadedQuantity</b> — (optional) expected loaded quantity of the row<br>
	 *   <b>PlannedDischargeQuantity</b> — (optional) expected discharge quantity of the row<br>
	 * @cucumber.depends StepDefData: M_ShipperTransportation_StepDefData, M_Delivery_Planning_StepDefData,
	 * M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the M_ShipperTransportation identified by deliveryInstruction has exactly the following rows in M_ShipperTransportation_Delivery_Planning_History_V:
	 *   | M_Delivery_Planning_ID | M_Product_ID | PlannedLoadedQuantity | PlannedDischargeQuantity |
	 *   | deliveryPlanning_1     | product_1    | 7                     | 0                       |
	 *   | deliveryPlanning_2     | product_2    | 3                     | 0                       |
	 * </pre>
	 */
	@Then("^the M_ShipperTransportation identified by (.*) has exactly the following rows in M_ShipperTransportation_Delivery_Planning_History_V:$")
	public void validate_M_ShipperTransportation_Delivery_Planning_History_V(
			@NonNull final String deliveryInstructionIdentifier,
			@NonNull final DataTable dataTable)
	{
		validateView(
				"M_ShipperTransportation_Delivery_Planning_History_V",
				"M_Delivery_Planning_Alloc_ID",
				"PlannedLoadedQuantity",
				"PlannedDischargeQuantity",
				deliveryInstructionIdentifier,
				dataTable);
	}

	private void validateView(
			@NonNull final String viewName,
			@NonNull final String keySql,
			@NonNull final String qtyLoadedColumnName,
			@NonNull final String qtyDischargedColumnName,
			@NonNull final String deliveryInstructionIdentifier,
			@NonNull final DataTable dataTable)
	{
		final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionTable.get(deliveryInstructionIdentifier);
		assertThat(deliveryInstruction).isNotNull();

		final List<ViewRow> viewRows = queryViewRows(viewName, keySql, qtyLoadedColumnName, qtyDischargedColumnName,
													 deliveryInstruction.getM_ShipperTransportation_ID());

		final ImmutableList<DataTableRow> expectedRows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());

		// the row count IS the cartesian-product guard: N plannings owe N rows, never N x N
		assertThat(viewRows)
				.as("rows of %s for M_ShipperTransportation %s", viewName, deliveryInstructionIdentifier)
				.hasSize(expectedRows.size());

		final Set<String> keys = new LinkedHashSet<>();
		for (final ViewRow viewRow : viewRows)
		{
			assertThat(keys.add(viewRow.getKey()))
					.as("row key %s of %s is not shared with another row", viewRow.getKey(), viewName)
					.isTrue();
		}

		final SoftAssertions softly = new SoftAssertions();

		for (final DataTableRow row : expectedRows)
		{
			final I_M_Delivery_Planning deliveryPlanning = row.getAsIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID)
					.lookupNotNullIn(deliveryPlanningTable);
			final int deliveryPlanningId = deliveryPlanning.getM_Delivery_Planning_ID();

			final List<ViewRow> rowsOfPlanning = new ArrayList<>();
			for (final ViewRow viewRow : viewRows)
			{
				if (viewRow.getDeliveryPlanningId() == deliveryPlanningId)
				{
					rowsOfPlanning.add(viewRow);
				}
			}

			// exactly one, not "at least one": the cross product hands each planning N rows, only one of which
			// carries its own quantities - so a search for the matching row would pass on a broken view
			softly.assertThat(rowsOfPlanning)
					.as("rows of %s for M_Delivery_Planning %s", viewName, deliveryPlanningId)
					.hasSize(1);
			if (rowsOfPlanning.size() != 1)
			{
				continue;
			}

			final ViewRow viewRow = rowsOfPlanning.get(0);

			row.getAsOptionalIdentifier(I_M_Product.COLUMNNAME_M_Product_ID)
					.filter(StepDefDataIdentifier::isNotNullPlaceholder)
					.ifPresent(id -> {
						final I_M_Product product = id.lookupNotNullIn(productTable);
						softly.assertThat(viewRow.getProductId())
								.as("%s of the %s row of M_Delivery_Planning %s", I_M_Product.COLUMNNAME_M_Product_ID, viewName, deliveryPlanningId)
								.isEqualTo(product.getM_Product_ID());
					});

			row.getAsOptionalBigDecimal(qtyLoadedColumnName)
					.ifPresent(expected -> softly.assertThat(viewRow.getQtyLoaded())
							.as("%s of the %s row of M_Delivery_Planning %s", qtyLoadedColumnName, viewName, deliveryPlanningId)
							.isEqualByComparingTo(expected));

			row.getAsOptionalBigDecimal(qtyDischargedColumnName)
					.ifPresent(expected -> softly.assertThat(viewRow.getQtyDischarged())
							.as("%s of the %s row of M_Delivery_Planning %s", qtyDischargedColumnName, viewName, deliveryPlanningId)
							.isEqualByComparingTo(expected));
		}

		softly.assertAll();
	}

	private static List<ViewRow> queryViewRows(
			@NonNull final String viewName,
			@NonNull final String keySql,
			@NonNull final String qtyLoadedColumnName,
			@NonNull final String qtyDischargedColumnName,
			final int deliveryInstructionId)
	{
		final String sql = "SELECT M_Delivery_Planning_ID, M_Product_ID,"
				+ " " + qtyLoadedColumnName + ", " + qtyDischargedColumnName + ","
				+ " (" + keySql + ")::text AS RowKey"
				+ " FROM " + viewName
				+ " WHERE M_ShipperTransportation_ID=?"
				+ " ORDER BY M_Delivery_Planning_ID";

		final List<ViewRow> rows = new ArrayList<>();
		try (final PreparedStatement pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None))
		{
			pstmt.setInt(1, deliveryInstructionId);
			try (final ResultSet rs = pstmt.executeQuery())
			{
				while (rs.next())
				{
					rows.add(new ViewRow(
							rs.getInt(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID),
							rs.getInt(I_M_Product.COLUMNNAME_M_Product_ID),
							rs.getBigDecimal(qtyLoadedColumnName),
							rs.getBigDecimal(qtyDischargedColumnName),
							rs.getString("RowKey")));
				}
			}
		}
		catch (final SQLException e)
		{
			throw new AdempiereException("Failed to query " + viewName, e);
		}
		return rows;
	}

	@Value
	private static class ViewRow
	{
		int deliveryPlanningId;
		int productId;
		BigDecimal qtyLoaded;
		BigDecimal qtyDischarged;
		String key;
	}
}
