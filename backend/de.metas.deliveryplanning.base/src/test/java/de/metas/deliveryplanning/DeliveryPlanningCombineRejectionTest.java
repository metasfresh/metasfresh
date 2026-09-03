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

import de.metas.bpartner.BPartnerLocationId;
import de.metas.deliveryplanning.DeliveryPlanningList.AggregationKeyField;
import de.metas.document.dimension.DimensionService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.incoterms.IncotermsId;
import de.metas.organization.OrgId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.TransportDirection;
import de.metas.shipping.model.ShipperTransportationId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.metas.deliveryplanning.DeliveryPlanningAllocTestHelper.allocatedTo;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Whether a selection may be combined into ONE delivery instruction, and - when it may not - WHICH single reason
 * the planner is given.
 * <p>
 * The reason matters as much as the verdict: the precondition shows it on the disabled button and
 * {@code combine} throws it, so a wrong or vague reason is a wrong feature. Above all, an already-allocated
 * planning has to be named HERE, before any write - the single-active-allocation unique index would otherwise
 * abort the transaction with a constraint violation that names nothing.
 */
class DeliveryPlanningCombineRejectionTest
{
	private static int nextId = 1;

	private DeliveryPlanningService deliveryPlanningService;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final DeliveryPlanningRepository deliveryPlanningRepository = Mockito.mock(DeliveryPlanningRepository.class);
		final DeliveryPlanningAllocRepository deliveryPlanningAllocRepository = new DeliveryPlanningAllocRepository();
		final DeliveryInstructionRepository deliveryInstructionRepository = new DeliveryInstructionRepository(Mockito.mock(DimensionService.class));
		final DeliveryInstructionService deliveryInstructionService = new DeliveryInstructionService(
				deliveryPlanningRepository, deliveryPlanningAllocRepository, deliveryInstructionRepository, new MPackageRepository());

		deliveryPlanningService = new DeliveryPlanningService(
				Mockito.mock(ShipperRepository.class),
				deliveryPlanningRepository,
				deliveryPlanningAllocRepository,
				deliveryInstructionRepository,
				deliveryInstructionService,
				Mockito.mock(DeliveryStatusColorPaletteService.class),
				Mockito.mock(DimensionService.class),
				Mockito.mock(MeansOfTransportationService.class),
				new ShipperTransportationDocSubTypeGuard());
	}

	private static DeliveryPlanning.DeliveryPlanningBuilder combinable()
	{
		return DeliveryPlanning.builder()
				.id(DeliveryPlanningId.ofRepoId(nextId++))
				.orgId(OrgId.ofRepoId(1000000))
				.transportDirection(TransportDirection.Outgoing)
				.shipperId(ShipperId.ofRepoId(540001));
	}

	/**
	 * The rejection rendered as text. In unit-test mode {@code IMsgBL} renders an AD_Message as its key followed
	 * by its parameters, so the text shows WHICH message was chosen and which parameters it names.
	 */
	private String rejectionTextOf(final DeliveryPlanning... deliveryPlannings)
	{
		final Optional<ITranslatableString> reason = deliveryPlanningService.getCombineRejectionReason(DeliveryPlanningList.of(deliveryPlannings));
		assertThat(reason).as("a rejection reason").isPresent();
		return reason.get().translate("en_US");
	}

	private static String keyOf(final AdMessageKey adMessageKey)
	{
		return adMessageKey.toAD_Message();
	}

	@Test
	@DisplayName("a selection agreeing on every admissibility field is accepted")
	void admissibleSelectionIsAccepted()
	{
		assertThat(deliveryPlanningService.getCombineRejectionReason(
				DeliveryPlanningList.of(combinable().build(), combinable().build())))
				.isEmpty();
	}

	@Test
	@DisplayName("a planning already on a delivery instruction is rejected here, not by the unique index")
	void alreadyAllocatedIsRejected()
	{
		final DeliveryPlanning allocated = combinable().allocations(allocatedTo(ShipperTransportationId.ofRepoId(540021))).build();

		// and it names the planning that is in the way, which a constraint violation would not
		assertThat(rejectionTextOf(combinable().build(), allocated))
				.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_AlreadyOnDeliveryInstruction)
						+ " - " + allocated.getId().getRepoId());
	}

	@Test
	@DisplayName("a closed planning is rejected for the whole selection, not silently skipped")
	void closedIsRejected()
	{
		final DeliveryPlanning closed = combinable().closed(true).build();

		assertThat(rejectionTextOf(combinable().build(), closed))
				.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_ClosedPlannings)
						+ " - " + closed.getId().getRepoId());
	}

	@Test
	@DisplayName("a planning without a forwarder is rejected with the existing forwarder message, not a new one")
	void withoutForwarderIsRejected()
	{
		assertThat(rejectionTextOf(combinable().build(), combinable().shipperId(null).build()))
				.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_NoForwarder));
	}

	@Test
	@DisplayName("a selection differing in several fields is rejected ONCE, with the aggregate message")
	void severalDifferingFieldsGiveOneMessage()
	{
		final String rejectionText = rejectionTextOf(
				combinable().incotermsId(IncotermsId.ofRepoId(540002)).build(),
				combinable().shipperId(ShipperId.ofRepoId(540099)).incotermsId(IncotermsId.ofRepoId(540003)).build());

		// the full sentence, not three contains(): the separator and the order are the point of naming them all
		assertThat(rejectionText)
				.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_IncompatibleSelection)
						+ " - " + keyOf(AggregationKeyField.Forwarder.getLabel())
						+ ", " + keyOf(AggregationKeyField.Incoterms.getLabel()));
	}

	@Test
	@DisplayName("row eligibility outranks cross-row compatibility: a closed row is named before the fields that differ")
	void eligibilityIsReportedBeforeCompatibility()
	{
		final DeliveryPlanning closedAndDiffering = combinable().closed(true).shipperId(ShipperId.ofRepoId(540099)).build();

		assertThat(rejectionTextOf(combinable().build(), closedAndDiffering))
				.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_ClosedPlannings)
						+ " - " + closedAndDiffering.getId().getRepoId());
	}

	@Test
	@DisplayName("a selection differing in EVERY admissibility field names every one of them, in one message")
	void everyDifferingFieldIsNamedInTheOneMessage()
	{
		final DeliveryPlanning row1 = DeliveryPlanning.builder()
				.id(DeliveryPlanningId.ofRepoId(nextId++))
				.orgId(OrgId.ofRepoId(1000000))
				.transportDirection(TransportDirection.Outgoing)
				.shipperId(ShipperId.ofRepoId(540001))
				.incotermsId(IncotermsId.ofRepoId(540002))
				.incotermLocation("Hamburg")
				.meansOfTransportationId(MeansOfTransportationId.ofRepoId(540003))
				.loadingLocationId(BPartnerLocationId.ofRepoId(540004, 540005))
				.deliveryLocationId(BPartnerLocationId.ofRepoId(540006, 540007))
				.build();

		final DeliveryPlanning row2 = DeliveryPlanning.builder()
				.id(DeliveryPlanningId.ofRepoId(nextId++))
				.orgId(OrgId.ofRepoId(1000001))
				.transportDirection(TransportDirection.Incoming)
				.shipperId(ShipperId.ofRepoId(540011))
				.incotermsId(IncotermsId.ofRepoId(540012))
				.incotermLocation("Rotterdam")
				.meansOfTransportationId(MeansOfTransportationId.ofRepoId(540013))
				.loadingLocationId(BPartnerLocationId.ofRepoId(540014, 540015))
				.deliveryLocationId(BPartnerLocationId.ofRepoId(540016, 540017))
				.build();

		final String rejectionText = rejectionTextOf(row1, row2);

		// the full sentence, derived from the SAME enum the loop used to walk - so it still auto-tracks a new
		// AggregationKeyField, but now also pins that EVERY field is named, exactly once, in declaration order
		assertThat(rejectionText)
				.isEqualTo(keyOf(DeliveryPlanningService.MSG_M_Delivery_Planning_IncompatibleSelection)
						+ " - " + Arrays.stream(AggregationKeyField.values())
						.map(field -> keyOf(field.getLabel()))
						.collect(Collectors.joining(", ")));
	}
}
