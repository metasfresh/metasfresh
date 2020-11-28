package de.metas.ui.web.picking.packageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Component;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.order.OrderLineId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Class to retrieve the rows shown in {@link PackageableView}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
public class PackageableRowsRepository
{
	private final Supplier<LookupDataSource> orderLookup;
	private final Supplier<LookupDataSource> productLookup;
	private final Supplier<LookupDataSource> bpartnerLookup;

	public PackageableRowsRepository()
	{
		// creating those LookupDataSources requires DB access. So, to allow this component to be initialized early during startup
		// and also to allow it to be unit-tested (when the lookups are not part of the test), I use those suppliers.

		orderLookup = Suppliers.memoize(() -> LookupDataSourceFactory.instance.searchInTableLookup(I_C_Order.Table_Name));
		productLookup = Suppliers.memoize(() -> LookupDataSourceFactory.instance.searchInTableLookup(I_M_Product.Table_Name));
		bpartnerLookup = Suppliers.memoize(() -> LookupDataSourceFactory.instance.searchInTableLookup(I_C_BPartner.Table_Name));
	}

	private List<PackageableRow> retrieveRowsByShipmentScheduleIds(final ViewId viewId, final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return Services.get(IPackagingDAO.class).getByShipmentScheduleIds(shipmentScheduleIds)
				.stream()
				.map(packageable -> createPackageableRow(viewId, packageable))
				.collect(ImmutableList.toImmutableList());
	}

	private PackageableRow createPackageableRow(final ViewId viewId, final Packageable packageable)
	{
		final Quantity qtyPickedOrDelivered = packageable.getQtyPickedOrDelivered();
		final Optional<OrderLineId> orderLineId = Optional.ofNullable(packageable.getSalesOrderLineIdOrNull());

		return PackageableRow.builder()
				.shipmentScheduleId(packageable.getShipmentScheduleId())
				.salesOrderLineId(orderLineId)
				.viewId(viewId)
				//
				.order(orderLookup.get().findById(packageable.getSalesOrderId()))
				.product(productLookup.get().findById(packageable.getProductId()))
				.bpartner(bpartnerLookup.get().findById(packageable.getCustomerId()))
				.preparationDate(packageable.getPreparationDate())
				//
				.qtyOrdered(packageable.getQtyOrdered())
				.qtyPicked(qtyPickedOrDelivered)
				//
				.build();
	}

	public PackageableRowsData createRowsData(
			@NonNull final ViewId viewId,
			@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return PackageableRowsData.EMPTY;
		}

		final Set<ShipmentScheduleId> shipmentScheduleIdsCopy = ImmutableSet.copyOf(shipmentScheduleIds);
		return PackageableRowsData.ofSupplier(() -> retrieveRowsByShipmentScheduleIds(viewId, shipmentScheduleIdsCopy));
	}
}
