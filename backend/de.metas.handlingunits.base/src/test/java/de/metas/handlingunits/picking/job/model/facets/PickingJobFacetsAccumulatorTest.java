/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.job.model.facets;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RenderedAddressProvider;
import de.metas.externalsystem.ExternalSystem;
import de.metas.externalsystem.ExternalSystemCreateRequest;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.facets.customer.CustomerFacet;
import de.metas.handlingunits.picking.job.model.facets.delivery_day.DeliveryDayFacet;
import de.metas.handlingunits.picking.job.model.facets.external_system.ExternalSystemFacet;
import de.metas.handlingunits.picking.job.model.facets.preparation_day.PreparationDayFacet;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.picking.api.Packageable;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static de.metas.common.util.time.SystemTime.zoneId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(AdempiereTestWatcher.class)
class PickingJobFacetsAccumulatorTest
{
	private static final OrgId ORG_ID = OrgId.ofRepoId(1);

	private static final BPartnerId CUSTOMER_1 = BPartnerId.ofRepoId(1_000_001);
	private static final BPartnerId CUSTOMER_2 = BPartnerId.ofRepoId(1_000_002);
	private static final LocalDate DAY_1 = LocalDate.of(2026, 8, 11);
	private static final LocalDate DAY_2 = LocalDate.of(2026, 8, 12);

	private static final ImmutableList<PickingJobFacetGroup> CUSTOMER_THEN_DELIVERY_DATE =
			ImmutableList.of(PickingJobFacetGroup.CUSTOMER, PickingJobFacetGroup.DELIVERY_DATE);

	private ExternalSystemRepository externalSystemRepository;
	private ExternalSystem shopware;
	private ExternalSystem woo;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		externalSystemRepository = ExternalSystemRepository.newInstanceForUnitTesting();
		shopware = externalSystemRepository.create(ExternalSystemCreateRequest.builder().name("Shopware 6").type(ExternalSystemType.Shopware6).build());
		woo = externalSystemRepository.create(ExternalSystemCreateRequest.builder().name("WOO Commerce").type(ExternalSystemType.WOO).build());
	}

	@Test
	void byDefault_onlyTheFirstGroupIsOffered()
	{
		final PickingJobFacets facets = collect(parameters(PickingJobQuery.Facets.EMPTY));

		assertThat(customerIdsOf(facets)).containsExactlyInAnyOrder(CUSTOMER_1, CUSTOMER_2);
		assertThat(deliveryDaysOf(facets)).isEmpty();
	}

	/**
	 * The preparation-date group placed FIRST is offered straight away, with nothing selected — which is
	 * what lets a profile put a date filter in front of the operator without them picking a customer
	 * first. Progressive disclosure is unchanged; only the configured order decides what comes first.
	 */
	@Test
	void preparationDateFirst_isOfferedWithNothingSelected()
	{
		final PickingJobFacets facets = collect(parameters(
				PickingJobQuery.Facets.EMPTY,
				ImmutableList.of(PickingJobFacetGroup.PREPARATION_DATE, PickingJobFacetGroup.CUSTOMER)));

		assertThat(preparationDaysOf(facets)).containsExactlyInAnyOrder(DAY_1, DAY_2);
		assertThat(customerIdsOf(facets)).isEmpty();
	}

	/**
	 * Selecting a preparation date narrows the group that follows it — the composition half of the
	 * feature. Without {@code FacetAwareItem.isMatching} consulting preparationDays this passes the
	 * visibility check above yet silently offers both customers.
	 */
	@Test
	void selectingAPreparationDate_narrowsTheFollowingGroup()
	{
		final PickingJobFacets facets = collect(parameters(
				PickingJobQuery.Facets.builder().preparationDay(DAY_1).build(),
				ImmutableList.of(PickingJobFacetGroup.PREPARATION_DATE, PickingJobFacetGroup.CUSTOMER)));

		assertThat(preparationDaysOf(facets)).containsExactlyInAnyOrder(DAY_1, DAY_2);
		assertThat(customerIdsOf(facets)).containsExactly(CUSTOMER_1);
	}

	/** AC4 — the group placed first is offered straight away, one option per distinct external system. */
	@Test
	void externalSystemFirst_isOfferedWithNothingSelected()
	{
		final PickingJobFacets facets = collectWithExternalSystems(parameters(
				PickingJobQuery.Facets.EMPTY,
				ImmutableList.of(PickingJobFacetGroup.EXTERNAL_SYSTEM, PickingJobFacetGroup.CUSTOMER)));

		assertThat(externalSystemIdsOf(facets)).containsExactlyInAnyOrder(shopware.getId(), woo.getId());
		assertThat(customerIdsOf(facets)).isEmpty();
	}

	/**
	 * The composition half: without {@code FacetAwareItem.isMatching} consulting externalSystemIds this
	 * still passes the visibility check above yet silently offers both customers.
	 */
	@Test
	void selectingAnExternalSystem_narrowsTheFollowingGroup()
	{
		final PickingJobFacets facets = collectWithExternalSystems(parameters(
				PickingJobQuery.Facets.builder().externalSystemId(shopware.getId()).build(),
				ImmutableList.of(PickingJobFacetGroup.EXTERNAL_SYSTEM, PickingJobFacetGroup.CUSTOMER)));

		assertThat(externalSystemIdsOf(facets)).containsExactlyInAnyOrder(shopware.getId(), woo.getId());
		assertThat(customerIdsOf(facets)).containsExactly(CUSTOMER_1);
	}

	private PickingJobFacets collect(final CollectingParameters parameters)
	{
		return Stream.of(
						packageable(CUSTOMER_1, DAY_1),
						packageable(CUSTOMER_2, DAY_2))
				.collect(PickingJobFacetsAccumulator.collect(parameters));
	}

	/** CUSTOMER_1 came in through Shopware, CUSTOMER_2 through WOO. */
	private PickingJobFacets collectWithExternalSystems(final CollectingParameters parameters)
	{
		return Stream.of(
						packageable(CUSTOMER_1, DAY_1, shopware.getId()),
						packageable(CUSTOMER_2, DAY_2, woo.getId()))
				.collect(PickingJobFacetsAccumulator.collect(parameters));
	}

	private CollectingParameters parameters(final PickingJobQuery.Facets activeFacets)
	{
		return parameters(activeFacets, CUSTOMER_THEN_DELIVERY_DATE);
	}

	private CollectingParameters parameters(
			final PickingJobQuery.Facets activeFacets,
			final ImmutableList<PickingJobFacetGroup> groupsInOrder)
	{
		return CollectingParameters.builder()
				.addressProvider(RenderedAddressProvider.builder().documentLocationBL(mock(IDocumentLocationBL.class)).build())
				.externalSystemRepository(externalSystemRepository)
				.groupsInOrder(groupsInOrder)
				.activeFacets(activeFacets)
				.build();
	}

	private static Iterable<BPartnerId> customerIdsOf(final PickingJobFacets facets)
	{
		return facets.toList(CustomerFacet.class, CustomerFacet::getBpartnerId);
	}

	private static Iterable<LocalDate> deliveryDaysOf(final PickingJobFacets facets)
	{
		return facets.toList(DeliveryDayFacet.class, DeliveryDayFacet::getDeliveryDate);
	}

	private static Iterable<ExternalSystemId> externalSystemIdsOf(final PickingJobFacets facets)
	{
		return facets.toList(ExternalSystemFacet.class, ExternalSystemFacet::getExternalSystemId);
	}

	private static Iterable<LocalDate> preparationDaysOf(final PickingJobFacets facets)
	{
		return facets.toList(PreparationDayFacet.class, PreparationDayFacet::getPreparationDate);
	}

	private static Packageable packageable(final BPartnerId customerId, final LocalDate deliveryDay)
	{
		return packageable(customerId, deliveryDay, null);
	}

	private static Packageable packageable(final BPartnerId customerId, final LocalDate deliveryDay, @Nullable final ExternalSystemId externalSystemId)
	{
		final I_C_UOM uomRecord = newInstanceOutOfTrx(I_C_UOM.class);
		uomRecord.setUOMSymbol("PCE");
		final Quantity zero = Quantity.zero(uomRecord);

		return Packageable.builder()
				.orgId(ORG_ID)
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(customerId.getRepoId()))
				.qtyOrdered(zero)
				.qtyToDeliver(zero)
				.qtyDelivered(zero)
				.qtyPickedAndDelivered(zero)
				.qtyPickedNotDelivered(zero)
				.qtyPickedPlanned(zero)
				.customerId(customerId)
				.customerBPValue("BP" + customerId.getRepoId())
				.customerName("Customer " + customerId.getRepoId())
				.customerLocationId(BPartnerLocationId.ofRepoId(customerId.getRepoId(), 1))
				.handoverLocationId(BPartnerLocationId.ofRepoId(customerId.getRepoId(), 1))
				.warehouseId(WarehouseId.ofRepoId(1))
				.bestBeforePolicy(Optional.of(ShipmentAllocationBestBeforePolicy.Expiring_First))
				.productId(ProductId.ofRepoId(1))
				.productValueAndName(ProductValueAndName.of("P1", TranslatableStrings.constant("Product 1")))
				.asiId(AttributeSetInstanceId.NONE)
				.deliveryDate(InstantAndOrgId.ofInstant(
						Objects.requireNonNull(deliveryDay).atStartOfDay(zoneId()).toInstant(), ORG_ID))
				// same day as the delivery date: this fixture feeds BOTH date groups, so a
				// preparation-date assertion is not silently comparing against an absent value
				.preparationDate(InstantAndOrgId.ofInstant(
						deliveryDay.atStartOfDay(zoneId()).toInstant(), ORG_ID))
				.externalSystemId(externalSystemId)
				.build();
	}
}
