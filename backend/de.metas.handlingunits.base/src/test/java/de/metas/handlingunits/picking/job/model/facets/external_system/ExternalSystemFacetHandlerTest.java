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

package de.metas.handlingunits.picking.job.model.facets.external_system;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ADReferenceService;
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
import de.metas.handlingunits.picking.job.model.facets.CollectingParameters;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacets;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetGroup;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetId;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.organization.OrgId;
import de.metas.picking.api.Packageable;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(AdempiereTestWatcher.class)
class ExternalSystemFacetHandlerTest
{
	private static final OrgId ORG_ID = OrgId.ofRepoId(1);

	private final ExternalSystemFacetHandler handler = new ExternalSystemFacetHandler();

	private ExternalSystemRepository externalSystemRepository;
	private ExternalSystem shopware;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		// the group caption is an AD_Ref_List lookup, resolved eagerly when the group is built
		SpringContextHolder.registerJUnitBean(ADReferenceService.newMocked());

		externalSystemRepository = ExternalSystemRepository.newInstanceForUnitTesting();
		shopware = externalSystemRepository.create(ExternalSystemCreateRequest.builder()
				.name("Shopware 6")
				.type(ExternalSystemType.Shopware6)
				.build());
	}

	/**
	 * The mirror of {@code PreparationDayFacetHandlerTest#noPreparationDate_contributesNoOption}, and
	 * written first for the same reason: {@code Packageable.externalSystemId} is nullable — an order
	 * that came in through no external system leaves it unset — so a handler cloned from the customer
	 * one (whose {@code customerId} is @NonNull) without a guard would NPE on exactly the rows that
	 * are the common case.
	 */
	@Test
	void noExternalSystem_contributesNoOption()
	{
		assertThat(handler.extractFacets(packageable(null), collectingParameters())).isEmpty();
	}

	/**
	 * The caption is the external system's {@code Name}, never its {@code Value} code: the operator
	 * reads "Shopware 6", not "Shopware6".
	 */
	@Test
	void withExternalSystem_contributesItByName()
	{
		assertThat(handler.extractFacets(packageable(shopware.getId()), collectingParameters()))
				.extracting(ExternalSystemFacet::getExternalSystemId, ExternalSystemFacet::getName)
				.containsExactly(org.assertj.core.groups.Tuple.tuple(shopware.getId(), "Shopware 6"));
	}

	/**
	 * The facet id is what travels to the device and back, and it is built by reflection over
	 * {@code ExternalSystemId.ofRepoId} ({@code RepoIdAwares}). A missing/renamed factory method would not
	 * fail compilation — it would fail at runtime the moment an operator ticks the filter — so the
	 * round-trip is pinned here rather than left to the E2E.
	 */
	@Test
	void facetId_roundTripsBackToTheExternalSystemId()
	{
		final PickingJobFacets facets = PickingJobFacets.builder()
				.facets(ImmutableSet.copyOf(handler.extractFacets(packageable(shopware.getId()), collectingParameters())))
				.build();

		final WorkflowLaunchersFacetGroup group = handler.toWorkflowLaunchersFacetGroup(facets);
		final WorkflowLaunchersFacetId facetId = group.getFacets().get(0).getFacetId();

		final PickingJobQuery.Facets.FacetsBuilder collector = PickingJobQuery.Facets.builder();
		handler.collectFromFacetId(collector, facetId);

		assertThat(collector.build().getExternalSystemIds()).containsExactly(shopware.getId());
	}

	private CollectingParameters collectingParameters()
	{
		return CollectingParameters.builder()
				// never consulted by this handler; the parameters object insists on one
				.addressProvider(RenderedAddressProvider.builder().documentLocationBL(mock(IDocumentLocationBL.class)).build())
				.externalSystemRepository(externalSystemRepository)
				.groupsInOrder(ImmutableList.of(PickingJobFacetGroup.EXTERNAL_SYSTEM))
				.activeFacets(PickingJobQuery.Facets.builder().build())
				.build();
	}

	private static Packageable packageable(@Nullable final ExternalSystemId externalSystemId)
	{
		final I_C_UOM uomRecord = newInstanceOutOfTrx(I_C_UOM.class);
		uomRecord.setUOMSymbol("PCE");
		final Quantity zero = Quantity.zero(uomRecord);

		return Packageable.builder()
				.orgId(ORG_ID)
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(1))
				.qtyOrdered(zero)
				.qtyToDeliver(zero)
				.qtyDelivered(zero)
				.qtyPickedAndDelivered(zero)
				.qtyPickedNotDelivered(zero)
				.qtyPickedPlanned(zero)
				.customerId(BPartnerId.ofRepoId(1))
				.customerLocationId(BPartnerLocationId.ofRepoId(1, 1))
				.handoverLocationId(BPartnerLocationId.ofRepoId(1, 1))
				.warehouseId(WarehouseId.ofRepoId(1))
				.bestBeforePolicy(Optional.of(ShipmentAllocationBestBeforePolicy.Expiring_First))
				.productId(ProductId.ofRepoId(1))
				.productValueAndName(ProductValueAndName.of("P1", TranslatableStrings.constant("Product 1")))
				.asiId(AttributeSetInstanceId.NONE)
				.externalSystemId(externalSystemId)
				.build();
	}
}
