/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.handlingunits.picking.job.service.external.shipmentschedule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.organization.OrgId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith({ AdempiereTestWatcher.class })
public class PickingJobShipmentScheduleServiceTest
{
	private IShipmentScheduleAllocDAO shipmentScheduleAllocDAO;
	private PickingJobShipmentScheduleService service;

	private I_C_UOM uom;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		// filterOutDraftShipmentBound only consults IShipmentScheduleAllocDAO; the other collaborators
		// pulled in by the constructor are mocked so the service can be instantiated without a DB.
		shipmentScheduleAllocDAO = Mockito.mock(IShipmentScheduleAllocDAO.class);
		Services.registerService(IShipmentScheduleAllocDAO.class, shipmentScheduleAllocDAO);
		Services.registerService(IHUShipmentScheduleBL.class, Mockito.mock(IHUShipmentScheduleBL.class));
		Services.registerService(IShipmentScheduleBL.class, Mockito.mock(IShipmentScheduleBL.class));
		Services.registerService(IPackagingDAO.class, Mockito.mock(IPackagingDAO.class));

		service = new PickingJobShipmentScheduleService(ShipmentScheduleRepository.newInstanceForUnitTesting());

		uom = BusinessTestHelper.createUOM("PCE");
	}

	private Packageable packageable(final int shipmentScheduleId)
	{
		final ShipmentScheduleId id = ShipmentScheduleId.ofRepoId(shipmentScheduleId);
		return Packageable.builder()
				.orgId(OrgId.ofRepoId(1))
				.shipmentScheduleId(id)
				//
				.qtyOrdered(Quantity.of(10, uom))
				.qtyToDeliver(Quantity.of(10, uom))
				.qtyDelivered(Quantity.of(0, uom))
				.qtyPickedAndDelivered(Quantity.of(0, uom))
				.qtyPickedNotDelivered(Quantity.of(0, uom))
				.qtyPickedPlanned(Quantity.of(0, uom))
				//
				.customerId(BPartnerId.ofRepoId(1))
				.customerLocationId(BPartnerLocationId.ofRepoId(1, 1))
				.handoverLocationId(BPartnerLocationId.ofRepoId(1, 1))
				//
				.warehouseId(WarehouseId.ofRepoId(1))
				//
				.productId(ProductId.ofRepoId(1))
				.productValueAndName(ProductValueAndName.of("P1", TranslatableStrings.anyLanguage("Product 1")))
				.asiId(AttributeSetInstanceId.NONE)
				//
				.build();
	}

	private static List<Integer> scheduleIdsOf(final Stream<Packageable> stream)
	{
		return stream.map(Packageable::getShipmentScheduleId)
				.map(ShipmentScheduleId::getRepoId)
				.collect(Collectors.toList());
	}

	@Test
	public void emptyStream_returnsEmpty()
	{
		final Stream<Packageable> result = service.filterOutDraftShipmentBound(Stream.of());

		assertThat(result).isEmpty();
		// The DAO must not even be consulted for an empty input.
		Mockito.verify(shipmentScheduleAllocDAO, Mockito.never()).getScheduleIdsWithDraftShipmentAllocations(any());
	}

	@Test
	public void noDraftBoundIds_allPassThrough()
	{
		Mockito.when(shipmentScheduleAllocDAO.getScheduleIdsWithDraftShipmentAllocations(any()))
				.thenReturn(ImmutableSet.of());

		final Stream<Packageable> result = service.filterOutDraftShipmentBound(
				Stream.of(packageable(1), packageable(2), packageable(3)));

		assertThat(scheduleIdsOf(result)).containsExactly(1, 2, 3);
	}

	@Test
	public void someDraftBoundIds_onlyNonDraftPassThrough()
	{
		final Set<ShipmentScheduleId> draftBound = ImmutableSet.of(
				ShipmentScheduleId.ofRepoId(1),
				ShipmentScheduleId.ofRepoId(3));
		Mockito.when(shipmentScheduleAllocDAO.getScheduleIdsWithDraftShipmentAllocations(any()))
				.thenReturn(ImmutableSet.copyOf(draftBound));

		final Stream<Packageable> result = service.filterOutDraftShipmentBound(
				Stream.of(packageable(1), packageable(2), packageable(3)));

		assertThat(scheduleIdsOf(result)).containsExactly(2);
	}

	@Test
	public void allDraftBoundIds_nonePassThrough()
	{
		Mockito.when(shipmentScheduleAllocDAO.getScheduleIdsWithDraftShipmentAllocations(any()))
				.thenReturn(ImmutableSet.of(
						ShipmentScheduleId.ofRepoId(1),
						ShipmentScheduleId.ofRepoId(2)));

		final Stream<Packageable> result = service.filterOutDraftShipmentBound(
				Stream.of(packageable(1), packageable(2)));

		assertThat(ImmutableList.copyOf(scheduleIdsOf(result))).isEmpty();
	}
}
