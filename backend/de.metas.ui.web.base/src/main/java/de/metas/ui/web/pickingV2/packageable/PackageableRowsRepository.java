package de.metas.ui.web.pickingV2.packageable;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.inoutcandidate.api.IPackagingDAO;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.inoutcandidate.api.PackageableQuery;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.OrderId;
import de.metas.shipping.ShipperId;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.pickingV2.packageable.PackageableRowsData.PackageableRowsDataBuilder;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseTypeId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
	private static final Logger logger = LogManager.getLogger(PackageableRowsRepository.class);

	private final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
	private final IPackagingDAO packageablesRepo = Services.get(IPackagingDAO.class);
	private final MoneyService moneyService;

	private final Supplier<LookupDataSource> bpartnerLookup;
	private final Supplier<LookupDataSource> shipperLookup;
	private final Supplier<LookupDataSource> userLookup;

	public PackageableRowsRepository(@NonNull final MoneyService moneyService)
	{
		this.moneyService = moneyService;

		// creating those LookupDataSources requires DB access. So, to allow this component to be initialized early during startup
		// and also to allow it to be unit-tested (when the lookups are not part of the test), I use those suppliers.
		bpartnerLookup = Suppliers.memoize(() -> LookupDataSourceFactory.instance.searchInTableLookup(I_C_BPartner.Table_Name));
		shipperLookup = Suppliers.memoize(() -> LookupDataSourceFactory.instance.searchInTableLookup(I_M_Shipper.Table_Name));
		userLookup = Suppliers.memoize(() -> LookupDataSourceFactory.instance.searchInTableLookup(I_AD_User.Table_Name));
	}

	public PackageableRowsDataBuilder newPackageableRowsData()
	{
		return PackageableRowsData.builder().repo(this);
	}

	List<PackageableRow> retrieveRows(final DocumentFilterList filters)
	{
		final PackageableQuery query = createPackageableQuery(filters);

		return packageablesRepo.stream(query)
				.collect(GuavaCollectors.toImmutableListMultimap(packageable -> extractGroupingKey(packageable)))
				.asMap()
				.values()
				.stream()
				.map(this::createPackageableRowNoFail)
				.filter(Objects::nonNull)
				.sorted(Comparator.comparing(PackageableRow::getPreparationDate).thenComparing(PackageableRow::getOrderDocumentNo))
				.collect(ImmutableList.toImmutableList());
	}

	private PackageableQuery createPackageableQuery(final DocumentFilterList filters)
	{
		final PackageableViewFilterVO filterVO = PackageableViewFilters.extractPackageableViewFilterVO(filters);

		return PackageableQuery.builder()
				.onlyFromSalesOrder(true)
				.salesOrderId(filterVO.getSalesOrderId())
				.customerId(filterVO.getCustomerId())
				.warehouseId(filterVO.getWarehouseId())
				.warehouseTypeId(filterVO.getWarehouseTypeId())
				.deliveryDate(filterVO.getDeliveryDate())
				.preparationDate(filterVO.getPreparationDate())
				.shipperId(filterVO.getShipperId())
				.build();
	}

	private static ArrayKey extractGroupingKey(final Packageable packageable)
	{
		return ArrayKey.of(
				PackageableRowId.of(packageable.getSalesOrderId(), packageable.getWarehouseTypeId()),
				packageable.getLockedBy());
	}

	private PackageableRow createPackageableRowNoFail(final Collection<Packageable> packageables)
	{
		try
		{
			return createPackageableRow(packageables);
		}
		catch (Exception ex)
		{
			logger.warn("Failed creating row from {}. Skip.", packageables, ex);
			return null;
		}
	}

	private PackageableRow createPackageableRow(final Collection<Packageable> packageables)
	{
		Check.assumeNotEmpty(packageables, "packageables is not empty");

		final BPartnerId customerId = Packageable.extractSingleValue(packageables, Packageable::getCustomerId).get();
		final LookupValue customer = bpartnerLookup.get().findById(customerId);

		final WarehouseTypeId warehouseTypeId = Packageable.extractSingleValue(packageables, Packageable::getWarehouseTypeId).orElse(null);
		final ITranslatableString warehouseTypeName;
		if (warehouseTypeId != null)
		{
			warehouseTypeName = warehousesRepo.getWarehouseTypeById(warehouseTypeId).getName();
		}
		else
		{
			warehouseTypeName = null;
		}

		final ShipperId shipperId = Packageable.extractSingleValue(packageables, Packageable::getShipperId).orElse(null);
		final LookupValue shipper = shipperLookup.get().findById(shipperId);

		final OrderId salesOrderId = Packageable.extractSingleValue(packageables, Packageable::getSalesOrderId).get();
		final String salesOrderDocumentNo = Packageable.extractSingleValue(packageables, Packageable::getSalesOrderDocumentNo).get();
		final String poReference = Packageable.extractSingleValue(packageables, Packageable::getPoReference).orElse(null);

		final UserId lockedByUserId = Packageable.extractSingleValue(packageables, Packageable::getLockedBy).orElse(null);
		final LookupValue lockedByUser = userLookup.get().findById(lockedByUserId);

		return PackageableRow.builder()
				.orderId(salesOrderId)
				.poReference(poReference)
				.orderDocumentNo(salesOrderDocumentNo)
				.customer(customer)
				.warehouseTypeId(warehouseTypeId)
				.warehouseTypeName(warehouseTypeName)
				.lockedByUser(lockedByUser)
				.lines(packageables.size())
				.shipper(shipper)
				.lineNetAmt(buildNetAmtTranslatableString(packageables))
				.packageables(packageables)
				.build();
	}

	private ITranslatableString buildNetAmtTranslatableString(final Collection<Packageable> packageables)
	{
		return packageables.stream()
				.map(Packageable::getSalesOrderLineNetAmt)
				.filter(Objects::nonNull)
				.collect(Money.sumByCurrencyAndStream())
				.map(moneyService::toTranslatableString)
				.collect(TranslatableStrings.joining(", "));
	}

}
