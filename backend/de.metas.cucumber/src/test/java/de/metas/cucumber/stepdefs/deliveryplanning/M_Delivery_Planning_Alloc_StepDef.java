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
import de.metas.cucumber.stepdefs.shipment.M_ShipperTransportation_StepDefData;
import de.metas.cucumber.stepdefs.shipment.pickingterminal.M_ShippingPackage_StepDefData;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Delivery_Planning_Alloc;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Asserts the {@code M_Delivery_Planning_Alloc} rows that link a delivery planning to the delivery instruction
 * it is booked on - the record aggregation is actually made of, as opposed to the denormalised
 * {@code M_Delivery_Planning.M_ShipperTransportation_ID} mirror that
 * {@link M_Delivery_Planning_StepDef} validates.
 * <p>
 * Two questions, two steps: what an instruction holds NOW (active rows, in {@code LineNo} order), and what one
 * named planning/instruction pair looks like - including a RETIRED ({@code IsActive='N'}) row, which is how the
 * re-booking trail survives a move, a removal or a void.
 */
@RequiredArgsConstructor
public class M_Delivery_Planning_Alloc_StepDef
{
	@NonNull private final M_Delivery_Planning_Alloc_StepDefData deliveryPlanningAllocTable;
	@NonNull private final M_Delivery_Planning_StepDefData deliveryPlanningTable;
	@NonNull private final M_ShipperTransportation_StepDefData deliveryInstructionTable;
	@NonNull private final M_ShippingPackage_StepDefData shippingPackageTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Asserts the COMPLETE set of active allocations of one delivery instruction, in {@code LineNo} order: the
	 * given rows and nothing else. Together with the per-row assertions this is what tells an aggregation of N
	 * plannings apart from N separate instructions - a row count, a distinct {@code LineNo} and its OWN shipping
	 * package per planning, all under one instruction.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning this allocation belongs to<br>
	 *   <b>LineNo</b> — (required) expected {@code LineNo}; the rows are given in ascending {@code LineNo} order<br>
	 *   <b>M_Delivery_Planning_Alloc_ID</b> — (optional, identifier-ref) alias to store the allocation under<br>
	 *   <b>M_ShippingPackage_ID</b> — (optional, identifier-ref) alias to store the allocation's shipping
	 *   package under, for later {@code validate M_Shipping_Package} steps<br>
	 *   <b>ActualLoadQty</b> — (optional) expected {@code ActualLoadQty} on the allocation's shipping package<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData, M_ShipperTransportation_StepDefData,
	 * M_Delivery_Planning_Alloc_StepDefData, M_ShippingPackage_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the M_ShipperTransportation identified by deliveryInstruction holds exactly the following active M_Delivery_Planning_Alloc:
	 *   | M_Delivery_Planning_ID | LineNo | M_ShippingPackage_ID |
	 *   | deliveryPlanning_1     | 10     | shippingPackage_1    |
	 *   | deliveryPlanning_2     | 20     | shippingPackage_2    |
	 * </pre>
	 */
	@Then("^the M_ShipperTransportation identified by (.*) holds exactly the following active M_Delivery_Planning_Alloc:$")
	public void validate_active_allocations_of(
			@NonNull final String deliveryInstructionIdentifier,
			@NonNull final DataTable dataTable)
	{
		final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionTable.get(deliveryInstructionIdentifier);

		final List<I_M_Delivery_Planning_Alloc> allocRecords = retrieveActiveAllocations(deliveryInstruction);
		final ImmutableList<DataTableRow> expectedRows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());

		assertThat(allocRecords)
				.as("active M_Delivery_Planning_Alloc records of M_ShipperTransportation %s", deliveryInstructionIdentifier)
				.hasSize(expectedRows.size());

		final SoftAssertions softly = new SoftAssertions();

		for (int rowIndex = 0; rowIndex < expectedRows.size(); rowIndex++)
		{
			final int allocationIndex = rowIndex;
			final DataTableRow row = expectedRows.get(allocationIndex);
			final I_M_Delivery_Planning_Alloc allocRecord = allocRecords.get(allocationIndex);

			final I_M_Delivery_Planning deliveryPlanning = row.getAsIdentifier(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_ID).lookupNotNullIn(deliveryPlanningTable);
			softly.assertThat(allocRecord.getM_Delivery_Planning_ID())
					.as("%s of allocation #%s", I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_ID, allocationIndex)
					.isEqualTo(deliveryPlanning.getM_Delivery_Planning_ID());

			softly.assertThat(allocRecord.getLineNo())
					.as("%s of allocation #%s", I_M_Delivery_Planning_Alloc.COLUMNNAME_LineNo, allocationIndex)
					.isEqualTo(row.getAsInt(I_M_Delivery_Planning_Alloc.COLUMNNAME_LineNo));

			// each planning gets its OWN shipping package, and that package hangs off the same instruction
			final I_M_ShippingPackage shippingPackage = loadShippingPackageOf(allocRecord);
			softly.assertThat(shippingPackage.getM_ShipperTransportation_ID())
					.as("%s of the shipping package of allocation #%s", I_M_ShippingPackage.COLUMNNAME_M_ShipperTransportation_ID, allocationIndex)
					.isEqualTo(deliveryInstruction.getM_ShipperTransportation_ID());

			// the package carries THIS planning's load, which is what tells a correct pairing apart from one that
			// puts the right number of packages under the instruction against the wrong plannings
			row.getAsOptionalBigDecimal(I_M_ShippingPackage.COLUMNNAME_ActualLoadQty)
					.ifPresent(actualLoadQty -> softly.assertThat(shippingPackage.getActualLoadQty())
							.as("%s of the shipping package of allocation #%s", I_M_ShippingPackage.COLUMNNAME_ActualLoadQty, allocationIndex)
							.isEqualByComparingTo(actualLoadQty));

			row.getAsOptionalIdentifier(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_Alloc_ID)
					.ifPresent(identifier -> identifier.putOrReplace(deliveryPlanningAllocTable, allocRecord));
			row.getAsOptionalIdentifier(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_ShippingPackage_ID)
					.ifPresent(identifier -> identifier.putOrReplace(shippingPackageTable, shippingPackage));
		}

		softly.assertAll();

		// no two plannings share one package - the defect that makes an aggregated instruction print one
		// planning's quantities against another planning's line
		assertThat(allocRecords.stream().map(I_M_Delivery_Planning_Alloc::getM_ShippingPackage_ID).distinct().count())
				.as("distinct %s across the allocations of M_ShipperTransportation %s", I_M_Delivery_Planning_Alloc.COLUMNNAME_M_ShippingPackage_ID, deliveryInstructionIdentifier)
				.isEqualTo(allocRecords.size());
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the M_ShipperTransportation identified by deliveryInstruction holds no active M_Delivery_Planning_Alloc
	 * </pre>
	 */
	@Then("^the M_ShipperTransportation identified by (.*) holds no active M_Delivery_Planning_Alloc$")
	public void validate_no_active_allocations_of(@NonNull final String deliveryInstructionIdentifier)
	{
		final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionTable.get(deliveryInstructionIdentifier);

		assertThat(retrieveActiveAllocations(deliveryInstruction))
				.as("active M_Delivery_Planning_Alloc records of M_ShipperTransportation %s", deliveryInstructionIdentifier)
				.isEmpty();
	}

	/**
	 * Asserts the one allocation row linking the given planning to the given delivery instruction, ACTIVE OR
	 * RETIRED - which is how a scenario asserts that taking a planning off an instruction left the record of it
	 * having been there behind, rather than erasing it.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the allocated planning<br>
	 *   <b>M_ShipperTransportation_ID</b> — (required, identifier-ref) the delivery instruction<br>
	 *   <b>IsActive</b> — (required) expected {@code IsActive}: {@code false} for a retired allocation<br>
	 *   <b>LineNo</b> — (optional) expected {@code LineNo}<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData, M_ShipperTransportation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate M_Delivery_Planning_Alloc:
	 *   | M_Delivery_Planning_ID | M_ShipperTransportation_ID | IsActive |
	 *   | deliveryPlanning_1     | deliveryInstruction_source | false    |
	 * </pre>
	 */
	@Then("validate M_Delivery_Planning_Alloc:")
	public void validate_M_Delivery_Planning_Alloc(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Delivery_Planning deliveryPlanning = row.getAsIdentifier(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_ID).lookupNotNullIn(deliveryPlanningTable);
			final I_M_ShipperTransportation deliveryInstruction = row.getAsIdentifier(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_ShipperTransportation_ID).lookupNotNullIn(deliveryInstructionTable);

			final List<I_M_Delivery_Planning_Alloc> allocRecords = queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
					.addEqualsFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanning.getM_Delivery_Planning_ID())
					.addEqualsFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstruction.getM_ShipperTransportation_ID())
					.create()
					.list();

			assertThat(allocRecords)
					.as("M_Delivery_Planning_Alloc records linking M_Delivery_Planning %s to M_ShipperTransportation %s",
							deliveryPlanning.getM_Delivery_Planning_ID(), deliveryInstruction.getM_ShipperTransportation_ID())
					.hasSize(1);

			final I_M_Delivery_Planning_Alloc allocRecord = allocRecords.get(0);

			final SoftAssertions softly = new SoftAssertions();
			softly.assertThat(allocRecord.isActive())
					.as(I_M_Delivery_Planning_Alloc.COLUMNNAME_IsActive)
					.isEqualTo(row.getAsBoolean(I_M_Delivery_Planning_Alloc.COLUMNNAME_IsActive));
			row.getAsOptionalInt(I_M_Delivery_Planning_Alloc.COLUMNNAME_LineNo)
					.ifPresent(lineNo -> softly.assertThat(allocRecord.getLineNo()).as(I_M_Delivery_Planning_Alloc.COLUMNNAME_LineNo).isEqualTo(lineNo.intValue()));
			softly.assertAll();
		});
	}

	@NonNull
	private List<I_M_Delivery_Planning_Alloc> retrieveActiveAllocations(@NonNull final I_M_ShipperTransportation deliveryInstruction)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstruction.getM_ShipperTransportation_ID())
				.orderBy(I_M_Delivery_Planning_Alloc.COLUMNNAME_LineNo)
				.create()
				.list();
	}

	@NonNull
	private I_M_ShippingPackage loadShippingPackageOf(@NonNull final I_M_Delivery_Planning_Alloc allocRecord)
	{
		assertThat(allocRecord.getM_ShippingPackage_ID())
				.as("%s of M_Delivery_Planning_Alloc %s", I_M_Delivery_Planning_Alloc.COLUMNNAME_M_ShippingPackage_ID, allocRecord.getM_Delivery_Planning_Alloc_ID())
				.isNotZero();

		return load(allocRecord.getM_ShippingPackage_ID(), I_M_ShippingPackage.class);
	}
}
