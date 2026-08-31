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
import de.metas.deliveryplanning.DeliveryPlanningList.AggregationKeyField;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.notification.INotificationBL;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Delivery_Planning;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * That "Add to Delivery Instruction" cannot build, one selection at a time, an instruction that
 * "Combine into one Delivery Instruction" would have refused outright.
 * <p>
 * The two actions write the SAME document, so they answer to the same rule: the header holds ONE forwarder,
 * ONE incoterm, ONE incoterm location, ONE means of transportation and ONE loading and delivery address, so
 * add-to must judge the selection TOGETHER WITH what the target already holds.
 */
class DeliveryPlanningAddToAdmissibilityTest
{
	private static final int PRODUCT_ID = 540010;

	/** Two forwarders, so a selection can differ from the target in the forwarder and in nothing else. */
	private static final int FORWARDER_A = 540001;
	private static final int FORWARDER_B = 540002;

	private static final int BPARTNER_ID = 540020;
	private static final int BPARTNER_LOCATION_ID = 540021;

	private DeliveryPlanningRepository deliveryPlanningRepository;
	private DeliveryPlanningService deliveryPlanningService;
	private I_C_UOM uom;

	// created on first use and shared by every planning, so the loading and delivery addresses the plannings are
	// read from agree and the forwarder is the ONLY field a test makes differ
	private I_M_Warehouse loadingWarehouse;
	private I_M_ShipmentSchedule deliveryShipmentSchedule;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);
		Services.registerService(INotificationBL.class, Mockito.mock(INotificationBL.class));

		deliveryPlanningRepository = Mockito.spy(new DeliveryPlanningRepository(Mockito.mock(DimensionService.class)));
		deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);

		createDeliveryInstructionDocType();
	}

	// ------------------------------------------------------------------ helpers

	/** A planning a delivery instruction can be built from; every planning here shares all its fields except the forwarder, which is the parameter. */
	private I_M_Delivery_Planning deliveryPlanning(final int shipperId)
	{
		final I_M_Delivery_Planning record = InterfaceWrapperHelper.newInstance(I_M_Delivery_Planning.class);
		record.setTransportDirection(X_M_Delivery_Planning.TRANSPORTDIRECTION_Outgoing);
		record.setM_Product_ID(PRODUCT_ID);
		record.setC_UOM_ID(uom.getC_UOM_ID());
		record.setPlannedLoadedQuantity(BigDecimal.TEN);
		record.setPlannedDischargeQuantity(BigDecimal.ONE);
		record.setM_Shipper_ID(shipperId);
		record.setC_BPartner_ID(BPARTNER_ID);
		record.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
		record.setM_Warehouse_ID(loadingWarehouseId());
		record.setM_ShipmentSchedule_ID(deliveryShipmentScheduleId());
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private int loadingWarehouseId()
	{
		if (loadingWarehouse == null)
		{
			loadingWarehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
			loadingWarehouse.setValue("WH");
			loadingWarehouse.setName("WH");
			loadingWarehouse.setC_BPartner_ID(BPARTNER_ID);
			loadingWarehouse.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
			InterfaceWrapperHelper.save(loadingWarehouse);
		}
		return loadingWarehouse.getM_Warehouse_ID();
	}

	private int deliveryShipmentScheduleId()
	{
		if (deliveryShipmentSchedule == null)
		{
			deliveryShipmentSchedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
			deliveryShipmentSchedule.setC_BPartner_ID(BPARTNER_ID);
			deliveryShipmentSchedule.setC_BPartner_Location_ID(BPARTNER_LOCATION_ID);
			InterfaceWrapperHelper.save(deliveryShipmentSchedule);
		}
		return deliveryShipmentSchedule.getM_ShipmentSchedule_ID();
	}

	private void createDeliveryInstructionDocType()
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setName("Delivery Instruction");
		docType.setDocBaseType(DocBaseType.ShipperTransportation.getCode());
		docType.setDocSubType(DocSubType.DeliveryInstruction.getCode());
		InterfaceWrapperHelper.save(docType);
	}

	private IQueryFilter<I_M_Delivery_Planning> selectionOf(@NonNull final List<I_M_Delivery_Planning> records)
	{
		@SuppressWarnings("unchecked") final IQueryFilter<I_M_Delivery_Planning> filter = Mockito.mock(IQueryFilter.class);
		Mockito.doAnswer(invocation -> records.iterator())
				.when(deliveryPlanningRepository).extractDeliveryPlannings(filter);
		return filter;
	}

	/**
	 * Combines the given plannings into ONE draft delivery instruction, and returns it.
	 * <p>
	 * The {@code DocStatus} is stamped explicitly: the in-memory store applies no column defaults, and a
	 * non-draft target would be refused for a reason these tests are not about.
	 */
	private ShipperTransportationId combineIntoDraftInstruction(@NonNull final List<I_M_Delivery_Planning> records)
	{
		final ShipperTransportationId deliveryInstructionId = deliveryPlanningService.combine(selectionOf(records), false);

		final I_M_ShipperTransportation deliveryInstruction = InterfaceWrapperHelper.load(deliveryInstructionId, I_M_ShipperTransportation.class);
		deliveryInstruction.setDocStatus(DocStatus.Drafted.getCode());
		InterfaceWrapperHelper.save(deliveryInstruction);

		return deliveryInstructionId;
	}

	private static DeliveryPlanningId idOf(@NonNull final I_M_Delivery_Planning record)
	{
		return DeliveryPlanningId.ofRepoId(record.getM_Delivery_Planning_ID());
	}

	private static I_M_Delivery_Planning reload(@NonNull final I_M_Delivery_Planning record)
	{
		return InterfaceWrapperHelper.load(idOf(record), I_M_Delivery_Planning.class);
	}

	private String addToRejectionTextOf(
			@NonNull final IQueryFilter<I_M_Delivery_Planning> selection,
			@NonNull final ShipperTransportationId target)
	{
		return deliveryPlanningService
				.getAddToRejectionReason(deliveryPlanningService.getBySelection(selection), target)
				.map(reason -> reason.translate("en_US"))
				.orElse(null);
	}

	/**
	 * The same for move-to: the two actions share this rule, so both are asserted against it.
	 */
	private String moveToRejectionTextOf(
			@NonNull final IQueryFilter<I_M_Delivery_Planning> selection,
			@NonNull final ShipperTransportationId target)
	{
		return deliveryPlanningService
				.getMoveToRejectionReason(deliveryPlanningService.getBySelection(selection), target)
				.map(reason -> reason.translate("en_US"))
				.orElse(null);
	}

	private static String keyOf(final AdMessageKey adMessageKey)
	{
		return adMessageKey.toAD_Message();
	}

	// ------------------------------------------------------------------ tests

	@Test
	@DisplayName("add to is refused when the selection differs from what the target already holds, naming the field")
	void addTo_differingFromTheTargetsOwnPlanningsIsRefused()
	{
		// the target: two plannings of forwarder A, combined into ONE draft instruction whose header therefore
		// carries A's forwarder, incoterms and addresses
		final I_M_Delivery_Planning p1 = deliveryPlanning(FORWARDER_A);
		final I_M_Delivery_Planning p3 = deliveryPlanning(FORWARDER_A);
		final ShipperTransportationId target = combineIntoDraftInstruction(ImmutableList.of(p1, p3));

		// a third planning that differs from them in the FORWARDER and in nothing else - combining it with either
		// of them would be refused, so putting it on the document they already sit on has to be refused too
		final I_M_Delivery_Planning p2 = deliveryPlanning(FORWARDER_B);
		final IQueryFilter<I_M_Delivery_Planning> selection = selectionOf(ImmutableList.of(p2));

		assertThat(addToRejectionTextOf(selection, target))
				.contains(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_IncompatibleSelection))
				.contains(keyOf(AggregationKeyField.Forwarder.getLabel()));

		assertThatThrownBy(() -> deliveryPlanningService.addTo(selection, target))
				.isInstanceOf(AdempiereException.class);

		assertThat(deliveryPlanningRepository.getAllocatedPlanningIds(target))
				.as("the target still holds only its own two plannings")
				.containsExactlyInAnyOrder(idOf(p1), idOf(p3));
		assertThat(reload(p2).getM_ShipperTransportation_ID())
				.as("the refused planning was not stamped onto the target either")
				.isLessThanOrEqualTo(0);
	}

	@Test
	@DisplayName("add to still accepts a selection that agrees with what the target holds")
	void addTo_agreeingWithTheTargetIsAccepted()
	{
		final I_M_Delivery_Planning p1 = deliveryPlanning(FORWARDER_A);
		final ShipperTransportationId target = combineIntoDraftInstruction(ImmutableList.of(p1));

		final I_M_Delivery_Planning p2 = deliveryPlanning(FORWARDER_A);
		final IQueryFilter<I_M_Delivery_Planning> selection = selectionOf(ImmutableList.of(p2));

		assertThat(addToRejectionTextOf(selection, target)).isNull();

		deliveryPlanningService.addTo(selection, target);

		assertThat(deliveryPlanningRepository.getAllocatedPlanningIds(target))
				.containsExactlyInAnyOrder(idOf(p1), idOf(p2));
		assertThat(reload(p2).getM_ShipperTransportation_ID())
				.isEqualTo(target.getRepoId());
	}

	@Test
	@DisplayName("move to of a planning the target already holds is a no-op, not a mismatch with itself")
	void moveTo_alreadyOnTheTargetIsIdempotent()
	{
		final I_M_Delivery_Planning p1 = deliveryPlanning(FORWARDER_A);
		final I_M_Delivery_Planning p3 = deliveryPlanning(FORWARDER_A);
		final ShipperTransportationId target = combineIntoDraftInstruction(ImmutableList.of(p1, p3));
		final String releaseNoBefore = reload(p1).getReleaseNo();

		// the same planning is in the selection AND on the target: counted once, so it is never compared against
		// itself and reported as differing from itself
		final IQueryFilter<I_M_Delivery_Planning> selection = selectionOf(ImmutableList.of(p1));
		assertThat(moveToRejectionTextOf(selection, target)).isNull();

		deliveryPlanningService.moveTo(selection, target);

		assertThat(deliveryPlanningRepository.getAllocatedPlanningIds(target))
				.as("nothing was added and nothing was taken away")
				.containsExactlyInAnyOrder(idOf(p1), idOf(p3));
		assertThat(reload(p1).getReleaseNo())
				.as("the release number already names this instruction, so it is not re-stamped")
				.isEqualTo(releaseNoBefore);
	}

	@Test
	@DisplayName("move to is refused when the moved selection differs from what the target already holds, naming the field")
	void moveTo_differingFromTheTargetsOwnPlanningsIsRefused()
	{
		final I_M_Delivery_Planning p1 = deliveryPlanning(FORWARDER_A);
		final ShipperTransportationId target = combineIntoDraftInstruction(ImmutableList.of(p1, deliveryPlanning(FORWARDER_A)));

		// on a draft instruction of its own, so move-to's allocation guard passes and the admissibility rule -
		// the same one add-to applies - is what has to refuse it
		final I_M_Delivery_Planning moving = deliveryPlanning(FORWARDER_B);
		final ShipperTransportationId source = combineIntoDraftInstruction(ImmutableList.of(moving, deliveryPlanning(FORWARDER_B)));
		final IQueryFilter<I_M_Delivery_Planning> selection = selectionOf(ImmutableList.of(moving));

		assertThat(moveToRejectionTextOf(selection, target))
				.contains(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_IncompatibleSelection))
				.contains(keyOf(AggregationKeyField.Forwarder.getLabel()));

		assertThatThrownBy(() -> deliveryPlanningService.moveTo(selection, target))
				.isInstanceOf(AdempiereException.class);

		assertThat(deliveryPlanningRepository.getAllocatedPlanningIds(target))
				.as("the target holds only its own two plannings")
				.hasSize(2)
				.doesNotContain(idOf(moving));
		assertThat(reload(moving).getM_ShipperTransportation_ID())
				.as("and the refused planning stayed on the source it was on")
				.isEqualTo(source.getRepoId());
	}

	@Test
	@DisplayName("add to onto an EMPTY draft instruction still judges the selection's own admissibility")
	void addTo_selectionThatDisagreesWithItselfIsRefusedEvenOnAnEmptyTarget()
	{
		final I_M_ShipperTransportation empty = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		empty.setDocStatus(DocStatus.Drafted.getCode());
		InterfaceWrapperHelper.save(empty);
		final ShipperTransportationId target = ShipperTransportationId.ofRepoId(empty.getM_ShipperTransportation_ID());

		final IQueryFilter<I_M_Delivery_Planning> selection = selectionOf(ImmutableList.of(
				deliveryPlanning(FORWARDER_A), deliveryPlanning(FORWARDER_B)));

		assertThat(addToRejectionTextOf(selection, target))
				.contains(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_IncompatibleSelection))
				.contains(keyOf(AggregationKeyField.Forwarder.getLabel()));
	}
}
