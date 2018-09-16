package de.metas.ui.web.pickingV2.packageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.warehouse.WarehouseTypeId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Shipper;

import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

final class PackageableRowsRepository
{
	private final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
	private final IPackagingDAO packageablesRepo = Services.get(IPackagingDAO.class);

	private final Supplier<LookupDataSource> bpartnerLookup;
	private final Supplier<LookupDataSource> shipperLookup;

	public PackageableRowsRepository()
	{
		// creating those LookupDataSources requires DB access. So, to allow this component to be initialized early during startup
		// and also to allow it to be unit-tested (when the lookups are not part of the test), I use those suppliers.

		bpartnerLookup = Suppliers.memoize(() -> LookupDataSourceFactory.instance.searchInTableLookup(I_C_BPartner.Table_Name));
		shipperLookup = Suppliers.memoize(() -> LookupDataSourceFactory.instance.searchInTableLookup(I_M_Shipper.Table_Name));
	}

	public PackageableRowsData getPackageableRowsData()
	{
		return PackageableRowsData.newInstance(this);
	}

	List<PackageableRow> retrieveRows()
	{
		return packageablesRepo.streamAll()
				.collect(GuavaCollectors.toImmutableListMultimap(packageable -> extractPackageableRowId(packageable)))
				.asMap()
				.values()
				.stream()
				.map(this::createPackageableRow)
				.collect(ImmutableList.toImmutableList());
	}

	private static PackageableRowId extractPackageableRowId(final Packageable packageable)
	{
		return PackageableRowId.of(packageable.getSalesOrderId(), packageable.getWarehouseTypeId());
	}

	private PackageableRow createPackageableRow(final Collection<Packageable> packageables)
	{
		final Packageable firstPackageable = packageables.iterator().next();

		final LookupValue customer = bpartnerLookup.get().findById(firstPackageable.getCustomerId());

		final WarehouseTypeId warehouseTypeId = firstPackageable.getWarehouseTypeId();
		final ITranslatableString warehouseTypeName;
		if (warehouseTypeId != null)
		{
			warehouseTypeName = warehousesRepo.getWarehouseTypeById(warehouseTypeId).getName();
		}
		else
		{
			warehouseTypeName = null;
		}

		final LookupValue shipper = shipperLookup.get().findById(firstPackageable.getShipperId());

		return PackageableRow.builder()
				.orderId(firstPackageable.getSalesOrderId())
				.orderDocumentNo(firstPackageable.getSalesOrderDocumentNo())
				.customer(customer)
				.warehouseTypeId(warehouseTypeId)
				.warehouseTypeName(warehouseTypeName)
				.lines(packageables.size())
				.shipper(shipper)
				.deliveryDate(calculateEarliestDeliveryDate(packageables))
				.preparationDate(calculateEarliestPreparationTime(packageables))
				.lineNetAmt(calculateNetAmt(packageables))
				.shipmentScheduleIds(extractShipmentScheduleIds(packageables))
				.build();
	}

	private static LocalDate calculateEarliestDeliveryDate(final Collection<Packageable> packageables)
	{
		return packageables.stream()
				.map(Packageable::getDeliveryDate)
				.filter(Predicates.notNull())
				.map(LocalDateTime::toLocalDate)
				.min(LocalDate::compareTo)
				.orElse(null);
	}

	private static LocalDateTime calculateEarliestPreparationTime(final Collection<Packageable> packageables)
	{
		return packageables.stream()
				.map(Packageable::getPreparationDate)
				.filter(Predicates.notNull())
				.min(LocalDateTime::compareTo)
				.orElse(null);
	}

	private static BigDecimal calculateNetAmt(final Collection<Packageable> packageables)
	{
		return packageables.stream()
				.map(Packageable::getSalesOrderLineNetAmt)
				.filter(Predicates.notNull())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static Set<ShipmentScheduleId> extractShipmentScheduleIds(final Collection<Packageable> packageables)
	{
		return packageables.stream()
				.map(Packageable::getShipmentScheduleId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
