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

package de.metas.handlingunits.picking.job.model.facets.preparation_day;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RenderedAddressProvider;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.model.facets.CollectingParameters;
import de.metas.handlingunits.picking.job.model.facets.PickingJobFacetGroup;
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
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static de.metas.common.util.time.SystemTime.zoneId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(AdempiereTestWatcher.class)
class PreparationDayFacetHandlerTest
{
	private static final OrgId ORG_ID = OrgId.ofRepoId(1);
	private static final LocalDate DAY = LocalDate.of(2026, 8, 11);

	private final PreparationDayFacetHandler handler = new PreparationDayFacetHandler();

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * The mirror of {@code DeliveryDayFacetHandlerTest#noDeliveryDate_contributesNoOption}, and the
	 * reason it is written before the handler exists: {@code Packageable.preparationDate} is nullable
	 * exactly like {@code deliveryDate} — {@code PackagingDAO} leaves it null whenever the shipment
	 * schedule carries none — so a handler cloned from the delivery-day one without a guard would
	 * reproduce the very defect that was just fixed there.
	 */
	@Test
	void noPreparationDate_contributesNoOption()
	{
		assertThat(handler.extractFacets(packageable(null), collectingParameters())).isEmpty();
	}

	@Test
	void withPreparationDate_contributesThatDay()
	{
		final Instant preparationDate = DAY.atStartOfDay(zoneId()).toInstant();

		assertThat(handler.extractFacets(packageable(InstantAndOrgId.ofInstant(preparationDate, ORG_ID)), collectingParameters()))
				.extracting(PreparationDayFacet::getPreparationDate)
				.containsExactly(DAY);
	}

	private static CollectingParameters collectingParameters()
	{
		return CollectingParameters.builder()
				// never consulted by this handler; the parameters object insists on one
				.addressProvider(RenderedAddressProvider.builder().documentLocationBL(mock(IDocumentLocationBL.class)).build())
				.groupsInOrder(ImmutableList.of(PickingJobFacetGroup.PREPARATION_DATE))
				.activeFacets(PickingJobQuery.Facets.builder().build())
				.build();
	}

	private static Packageable packageable(@Nullable final InstantAndOrgId preparationDate)
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
				.preparationDate(preparationDate)
				.build();
	}
}
