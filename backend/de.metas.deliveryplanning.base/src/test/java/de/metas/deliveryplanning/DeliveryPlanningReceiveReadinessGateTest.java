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
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.TransportDirection;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static de.metas.deliveryplanning.DeliveryPlanningService.SYSCONFIG_PREVENT_RECEIPT_IF_MISSING_DELIVERY_INSTRUCTIONS;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Whether a selection may be RECEIVED when its plannings are not yet on a completed delivery instruction.
 * <p>
 * The condition itself is not in question - a receipt before the instruction is completed is what
 * {@code IsReadyForReceipt} exists to describe. What these tests pin is WHO decides it is enforced: core ships
 * the {@code PreventReceiptIfMissingDeliveryInstructions} SysConfig OFF (gh14843, 2023), so receiving an
 * unallocated planning is the default-permitted behaviour and this method must NOT reject it on its own
 * authority. It is shared with the plain HU receipt window ({@code WEBUI_M_HU_CreateReceipt_Base}), so an
 * unconditional gate here would silently change behaviour for every customer that left the switch alone.
 * <p>
 * The single-record precondition on the Delivery Planning window reads the same switch
 * ({@code DeliveryPlanningGenerateProcessesHelper}), which is why the switch name lives on the service.
 */
class DeliveryPlanningReceiveReadinessGateTest
{
	private static int nextId = 1;

	private static final OrgId ORG_OPTED_IN = OrgId.ofRepoId(1000000);
	private static final OrgId ORG_DEFAULT = OrgId.ofRepoId(1000001);

	private DeliveryPlanningService deliveryPlanningService;
	private ISysConfigBL sysConfigBL;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		sysConfigBL = Services.get(ISysConfigBL.class);

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

	private void optIn(final OrgId orgId)
	{
		sysConfigBL.setValue(SYSCONFIG_PREVENT_RECEIPT_IF_MISSING_DELIVERY_INSTRUCTIONS, true, Env.getClientId(), orgId);
	}

	private static DeliveryPlanning planning(final OrgId orgId, final boolean readyForReceipt)
	{
		return DeliveryPlanning.builder()
				.id(DeliveryPlanningId.ofRepoId(nextId++))
				.orgId(orgId)
				.transportDirection(TransportDirection.Incoming)
				.readyForReceipt(readyForReceipt)
				.build();
	}

	@Test
	@DisplayName("switch left at the core default: a not-ready planning is NOT rejected")
	void notReady_switchOff_notRejected()
	{
		final Optional<ITranslatableString> rejection = deliveryPlanningService.getReceiveRejectionReason(
				DeliveryPlanningList.of(planning(ORG_DEFAULT, false)));

		assertThat(rejection).isEmpty();
	}

	@Test
	@DisplayName("org opted in: a not-ready planning IS rejected")
	void notReady_switchOn_rejected()
	{
		optIn(ORG_OPTED_IN);

		final Optional<ITranslatableString> rejection = deliveryPlanningService.getReceiveRejectionReason(
				DeliveryPlanningList.of(planning(ORG_OPTED_IN, false)));

		assertThat(rejection).isPresent();
	}

	@Test
	@DisplayName("org opted in, but the planning IS ready: not rejected")
	void ready_switchOn_notRejected()
	{
		optIn(ORG_OPTED_IN);

		final Optional<ITranslatableString> rejection = deliveryPlanningService.getReceiveRejectionReason(
				DeliveryPlanningList.of(planning(ORG_OPTED_IN, true)));

		assertThat(rejection).isEmpty();
	}

	@Test
	@DisplayName("the switch is evaluated per ROW's org: a row in a non-opted-in org does not trigger the rejection")
	void notReady_onlyOtherOrgOptedIn_notRejected()
	{
		optIn(ORG_OPTED_IN);

		final Optional<ITranslatableString> rejection = deliveryPlanningService.getReceiveRejectionReason(
				DeliveryPlanningList.of(planning(ORG_DEFAULT, false)));

		assertThat(rejection).isEmpty();
	}

	@Test
	@DisplayName("processed still wins: it is reported before readiness is even consulted")
	void processed_reportedBeforeReadiness()
	{
		optIn(ORG_OPTED_IN);

		final DeliveryPlanning processed = DeliveryPlanning.builder()
				.id(DeliveryPlanningId.ofRepoId(nextId++))
				.orgId(ORG_OPTED_IN)
				.transportDirection(TransportDirection.Incoming)
				.processed(true)
				.readyForReceipt(false)
				.build();

		final Optional<ITranslatableString> rejection =
				deliveryPlanningService.getReceiveRejectionReason(DeliveryPlanningList.of(processed));

		assertThat(rejection).isPresent();
	}
}
