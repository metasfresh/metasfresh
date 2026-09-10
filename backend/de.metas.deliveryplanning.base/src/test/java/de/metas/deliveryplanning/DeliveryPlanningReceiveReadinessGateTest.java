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

import de.metas.document.dimension.DimensionService;
import de.metas.i18n.ITranslatableString;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.TransportDirection;
import org.adempiere.model.InterfaceWrapperHelper;
import de.metas.bpartner.BPartnerId;
import de.metas.uom.UomId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Whether a selection may be RECEIVED when its plannings are not yet on a COMPLETED delivery instruction.
 * <p>
 * It may not, unconditionally: receiving before the instruction is completed makes no business sense, and the
 * window shows {@code IsReadyForReceipt} as a column - so offering the action on a row it just labelled "not
 * ready" is the defect these pin. There is deliberately no SysConfig in the way. The
 * {@code PreventReceiptIfMissingDeliveryInstructions} switch (gh14843, 2023) used to gate the Delivery
 * Planning window's equivalent check with a default of OFF; delivery planning is a fresh feature with no
 * active use, so there was no permissive behaviour to preserve and the switch only kept the defect alive.
 * <p>
 * Every caller of this method deals in delivery plannings only, so the reach is that same fresh feature:
 * {@code WEBUI_M_HU_CreateReceipt_Base} consults it solely for the plannings a receipt launch references and
 * returns early when there are none, leaving a plain receipt-schedule confirm untouched.
 */
class DeliveryPlanningReceiveReadinessGateTest
{
	private static I_C_UOM uom;

	private static int nextId = 1;

	private static final OrgId ORG = OrgId.ofRepoId(1000000);

	private DeliveryPlanningService deliveryPlanningService;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		// The five quantity columns are AD_IsMandatory='Y', so a planning always has them - and a
		// quantity needs a UOM. Stated here rather than relying on a mapper to omit them.
		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);

		final DeliveryPlanningRepository deliveryPlanningRepository = Mockito.mock(DeliveryPlanningRepository.class);
		final DeliveryPlanningAllocRepository deliveryPlanningAllocRepository = new DeliveryPlanningAllocRepository();
		final DeliveryInstructionRepository deliveryInstructionRepository = new DeliveryInstructionRepository(Mockito.mock(DimensionService.class));
		final DeliveryInstructionService deliveryInstructionService = new DeliveryInstructionService(
				deliveryPlanningRepository, deliveryPlanningAllocRepository, deliveryInstructionRepository, new MPackageRepository());

		deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				deliveryPlanningAllocRepository,
				deliveryInstructionService,
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());
	}

	private static DeliveryPlanning planning(final OrgId orgId, final boolean readyForReceipt)
	{
		return DeliveryPlanning.builder()
				.id(DeliveryPlanningId.ofRepoId(nextId++))
				.orgId(orgId)
				.transportDirection(TransportDirection.Incoming)
				.qtyOrdered(zeroQty())
				.plannedLoadedQty(zeroQty())
				.actualLoadedQty(zeroQty())
				.plannedDischargeQty(zeroQty())
				.actualDischargeQty(zeroQty())
				.qtyTotalOpen(zeroQty())
				.readyForReceipt(readyForReceipt)
				.bpartnerId(BPartnerId.ofRepoId(2000000))
				.uomId(UomId.ofRepoId(uom.getC_UOM_ID()))
				.build();
	}

	private static Quantity zeroQty() {return Quantity.of(BigDecimal.ZERO, uom);}

	@Test
	@DisplayName("a not-ready planning is rejected - no switch required")
	void notReady_rejected()
	{
		final Optional<ITranslatableString> rejection = deliveryPlanningService.getReceiveRejectionReason(
				DeliveryPlanningList.of(planning(ORG, false)));

		assertThat(rejection).isPresent();
	}

	@Test
	@DisplayName("a ready planning is not rejected")
	void ready_notRejected()
	{
		final Optional<ITranslatableString> rejection = deliveryPlanningService.getReceiveRejectionReason(
				DeliveryPlanningList.of(planning(ORG, true)));

		assertThat(rejection).isEmpty();
	}

	@Test
	@DisplayName("all-or-nothing: one not-ready row refuses the whole selection")
	void oneNotReadyAmongReady_wholeSelectionRejected()
	{
		final Optional<ITranslatableString> rejection = deliveryPlanningService.getReceiveRejectionReason(
				DeliveryPlanningList.of(planning(ORG, true), planning(ORG, false), planning(ORG, true)));

		assertThat(rejection).isPresent();
	}

	@Test
	@DisplayName("an empty selection has nothing to object to")
	void emptySelection_notRejected()
	{
		assertThat(deliveryPlanningService.getReceiveRejectionReason(DeliveryPlanningList.EMPTY)).isEmpty();
	}

	@Test
	@DisplayName("processed still wins: it is reported before readiness is even consulted")
	void processed_reportedBeforeReadiness()
	{
		final DeliveryPlanning processed = DeliveryPlanning.builder()
				.id(DeliveryPlanningId.ofRepoId(nextId++))
				.orgId(ORG)
				.transportDirection(TransportDirection.Incoming)
				.qtyOrdered(zeroQty())
				.plannedLoadedQty(zeroQty())
				.actualLoadedQty(zeroQty())
				.plannedDischargeQty(zeroQty())
				.actualDischargeQty(zeroQty())
				.qtyTotalOpen(zeroQty())
				.processed(true)
				.readyForReceipt(false)
				.bpartnerId(BPartnerId.ofRepoId(2000000))
				.uomId(UomId.ofRepoId(uom.getC_UOM_ID()))
				.build();

		final Optional<ITranslatableString> rejection =
				deliveryPlanningService.getReceiveRejectionReason(DeliveryPlanningList.of(processed));

		assertThat(rejection).isPresent();
	}
}
