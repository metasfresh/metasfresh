/*
 * #%L
 * de.metas.deliveryplanning.base
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

package de.metas.deliveryplanning;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.DocStatus;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.model.I_M_ShipperTransportation;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The delivery instruction's dates are DEFAULTS seeded from the plannings allocated to it: each field is
 * filled only while it is still empty, on creation and on every later add.
 * <p>
 * This mirrors {@code PurchaseOrderToShipperTransportationService.applyDefaultDatesFromFirstOrder}, which
 * solves the same problem for a transport order on this very table and guards every field with a null check
 * for the reason stated in its own comment: these are defaults, so a value the planner entered before the
 * first allocation must survive.
 * <p>
 * Running on every add rather than only at creation is what makes the feature usable on real data: a
 * planning whose upstream date chain produced nothing leaves the instruction empty, and only a later add can
 * supply the dates. The null guard is what makes running every time safe - it can only fill blanks.
 * <p>
 * Exercises {@link DeliveryPlanningService#resolveInstructionDatesForAllocation} directly, as a pure function:
 * the DECISION lives there (architecture.md §8 - a repository may never make it), so this is where it is
 * tested. {@link DeliveryPlanningRepository#createAllocations} only ever writes what this resolves, verbatim.
 */
class DeliveryInstructionDateDefaultsTest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	private static Timestamp day(final int dayOfMonth)
	{
		return Timestamp.from(LocalDate.of(2026, 3, dayOfMonth).atStartOfDay(ZoneId.of("UTC")).toInstant());
	}

	private static I_M_ShipperTransportation draftDeliveryInstruction(
			@Nullable final Timestamp etd,
			@Nullable final Timestamp eta)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setDocumentNo("DATE-DEFAULTS");
		record.setDocStatus(DocStatus.Drafted.getCode());
		record.setETD(etd);
		record.setETA(eta);
		return record;
	}

	/** Exactly the mapping {@code DeliveryPlanningService#createAllocCreateRequest} performs off a loaded planning. */
	private static DeliveryPlanningAllocCreateRequest allocRequest(
			@Nullable final Timestamp etd,
			@Nullable final Timestamp eta,
			@Nullable final String loadingTime)
	{
		return DeliveryPlanningAllocCreateRequest.builder()
				.deliveryPlanningId(DeliveryPlanningId.ofRepoId(1))
				.productId(ProductId.ofRepoId(540010))
				.qtyLoaded(Quantity.of(BigDecimal.TEN, uom()))
				.qtyDischarged(Quantity.of(BigDecimal.ONE, uom()))
				.etd(etd)
				.eta(eta)
				.loadingTime(loadingTime)
				.build();
	}

	private static I_C_UOM uomInstance;

	private static I_C_UOM uom()
	{
		if (uomInstance == null)
		{
			uomInstance = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
			InterfaceWrapperHelper.save(uomInstance);
		}
		return uomInstance;
	}

	private static DeliveryInstructionDates resolve(
			@NonNull final I_M_ShipperTransportation instruction,
			@NonNull final DeliveryPlanningAllocCreateRequest... requests)
	{
		final List<DeliveryPlanningAllocCreateRequest> requestList = ImmutableList.copyOf(requests);
		return DeliveryPlanningService.resolveInstructionDatesForAllocation(instruction, requestList);
	}

	@Test
	@DisplayName("an add fills every empty date from the added planning, and derives ATD/ATA from the filled fields")
	void addFillsEmptyDatesAndDerivesActuals()
	{
		final I_M_ShipperTransportation instruction = draftDeliveryInstruction(null, null);

		final DeliveryInstructionDates resolved = resolve(instruction, allocRequest(day(3), day(7), "08:00"));

		assertThat(resolved.getEtd()).as("ETD seeded from the planning").isEqualTo(day(3));
		assertThat(resolved.getEta()).as("ETA seeded from the planning").isEqualTo(day(7));
		assertThat(resolved.getLoadingTime()).as("LoadingTime seeded from the planning").isEqualTo("08:00");
		assertThat(resolved.getAtd())
				.as("ATD derives from the instruction's ETD FIELD after the fill, exactly as the transport-order "
						+ "precedent does, so a planner-set ETD propagates into ATD")
				.isEqualTo(day(3));
		assertThat(resolved.getAta()).as("ATA derives from the filled ETA").isEqualTo(day(7));
	}

	@Test
	@DisplayName("a date the instruction already carries is never overwritten, but its empty siblings are still filled")
	void existingDateSurvivesWhileEmptySiblingsAreFilled()
	{
		final I_M_ShipperTransportation instruction = draftDeliveryInstruction(day(1), null);

		final DeliveryInstructionDates resolved = resolve(instruction, allocRequest(day(3), day(7), null));

		assertThat(resolved.getEtd())
				.as("these are defaults - a value entered before the allocation must be kept")
				.isEqualTo(day(1));
		assertThat(resolved.getEta())
				.as("per field, not per document: one field being set must not skip the whole seed")
				.isEqualTo(day(7));
		assertThat(resolved.getAtd()).as("ATD follows the planner's ETD, not the planning's").isEqualTo(day(1));
	}

	@Test
	@DisplayName("a planning with no dates leaves the instruction's resolution empty - no derived actuals from nothing")
	void planningWithoutDatesLeavesTheInstructionEmpty()
	{
		final I_M_ShipperTransportation instruction = draftDeliveryInstruction(null, null);

		final DeliveryInstructionDates resolved = resolve(instruction, allocRequest(null, null, null));

		assertThat(resolved.getEtd()).isNull();
		assertThat(resolved.getEta()).isNull();
		assertThat(resolved.getAtd())
				.as("an unset ETD must never derive a phantom ATD")
				.isNull();
		assertThat(resolved.getAta()).isNull();
	}
}
