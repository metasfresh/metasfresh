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
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.X_M_Delivery_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

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
 */
class DeliveryInstructionDateDefaultsTest
{
	private static final int PRODUCT_ID = 540010;

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private I_C_UOM uom;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		deliveryPlanningRepository = new DeliveryPlanningRepository(Mockito.mock(DimensionService.class));

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);
	}

	private static Timestamp day(final int dayOfMonth)
	{
		return Timestamp.from(LocalDate.of(2026, 3, dayOfMonth).atStartOfDay(ZoneId.of("UTC")).toInstant());
	}

	private I_M_Delivery_Planning deliveryPlanning(
			@Nullable final Timestamp etd,
			@Nullable final Timestamp eta,
			@Nullable final String loadingTime)
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setTransportDirection(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing);
		record.setM_Product_ID(PRODUCT_ID);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setPlannedLoadedQuantity(BigDecimal.TEN);
		record.setPlannedDischargeQuantity(BigDecimal.ONE);
		record.setETD(etd);
		record.setETA(eta);
		record.setLoadingTime(loadingTime);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private I_M_ShipperTransportation draftDeliveryInstruction(
			@Nullable final Timestamp etd,
			@Nullable final Timestamp eta)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setDocumentNo("DATE-DEFAULTS");
		record.setDocStatus(DocStatus.Drafted.getCode());
		record.setETD(etd);
		record.setETA(eta);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private void allocate(@NonNull final I_M_ShipperTransportation instruction, @NonNull final I_M_Delivery_Planning planning)
	{
		deliveryPlanningRepository.createAllocations(
				ShipperTransportationId.ofRepoId(instruction.getM_ShipperTransportation_ID()),
				ImmutableList.of(DeliveryPlanningAllocCreateRequest.builder()
						.deliveryPlanningId(DeliveryPlanningId.ofRepoId(planning.getM_Delivery_Planning_ID()))
						.productId(ProductId.ofRepoId(PRODUCT_ID))
						.qtyLoaded(Quantity.of(BigDecimal.TEN, uom))
						.qtyDischarged(Quantity.of(BigDecimal.ONE, uom))
						// exactly the mapping DeliveryPlanningService.createAllocCreateRequest performs off the
						// loaded planning record - the dates travel on the request so the defaulting needs no
						// second load of rows the caller is already holding
						.etd(planning.getETD())
						.eta(planning.getETA())
						.loadingTime(planning.getLoadingTime())
						.deliveryTime(planning.getDeliveryTime())
						.build()));
	}

	private I_M_ShipperTransportation reload(@NonNull final I_M_ShipperTransportation instruction)
	{
		return InterfaceWrapperHelper.load(instruction.getM_ShipperTransportation_ID(), I_M_ShipperTransportation.class);
	}

	@Test
	@DisplayName("an add fills every empty date from the added planning, and derives ATD/ATA from the filled fields")
	void addFillsEmptyDatesAndDerivesActuals()
	{
		final I_M_ShipperTransportation instruction = draftDeliveryInstruction(null, null);

		allocate(instruction, deliveryPlanning(day(3), day(7), "08:00"));

		final I_M_ShipperTransportation reloaded = reload(instruction);
		assertThat(reloaded.getETD()).as("ETD seeded from the planning").isEqualTo(day(3));
		assertThat(reloaded.getETA()).as("ETA seeded from the planning").isEqualTo(day(7));
		assertThat(reloaded.getLoadingTime()).as("LoadingTime seeded from the planning").isEqualTo("08:00");
		assertThat(reloaded.getATD())
				.as("ATD derives from the instruction's ETD FIELD after the fill, exactly as the transport-order "
						+ "precedent does, so a planner-set ETD propagates into ATD")
				.isEqualTo(day(3));
		assertThat(reloaded.getATA()).as("ATA derives from the filled ETA").isEqualTo(day(7));
	}

	@Test
	@DisplayName("a date the instruction already carries is never overwritten, but its empty siblings are still filled")
	void existingDateSurvivesWhileEmptySiblingsAreFilled()
	{
		final I_M_ShipperTransportation instruction = draftDeliveryInstruction(day(1), null);

		allocate(instruction, deliveryPlanning(day(3), day(7), null));

		final I_M_ShipperTransportation reloaded = reload(instruction);
		assertThat(reloaded.getETD())
				.as("these are defaults - a value entered before the allocation must be kept")
				.isEqualTo(day(1));
		assertThat(reloaded.getETA())
				.as("per field, not per document: one field being set must not skip the whole seed")
				.isEqualTo(day(7));
		assertThat(reloaded.getATD()).as("ATD follows the planner's ETD, not the planning's").isEqualTo(day(1));
	}

	@Test
	@DisplayName("a planning with no dates leaves the instruction untouched - no null-to-null writes, no derived actuals")
	void planningWithoutDatesLeavesTheInstructionEmpty()
	{
		final I_M_ShipperTransportation instruction = draftDeliveryInstruction(null, null);

		allocate(instruction, deliveryPlanning(null, null, null));

		final I_M_ShipperTransportation reloaded = reload(instruction);
		assertThat(reloaded.getETD()).isNull();
		assertThat(reloaded.getETA()).isNull();
		assertThat(reloaded.getATD())
				.as("an unset ETD must never trigger a pointless null-to-null write on ATD")
				.isNull();
		assertThat(reloaded.getATA()).isNull();
	}
}
