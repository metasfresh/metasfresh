package de.metas.ui.web.pickingV2.productsToPick;

import java.util.List;
import java.util.stream.Stream;

import org.adempiere.util.Services;
import org.springframework.stereotype.Repository;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.NonNull;

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

@Repository
public class ProductsToPickRowsRepository
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final HUReservationDocumentFilterService huReservationService;

	private final Supplier<LookupDataSource> productLookup;
	private final Supplier<LookupDataSource> locatorLookup;

	public ProductsToPickRowsRepository(
			@NonNull final HUReservationDocumentFilterService huReservationService)
	{
		this.huReservationService = huReservationService;

		productLookup = Suppliers.memoize(() -> LookupDataSourceFactory.instance.searchInTableLookup(org.compiere.model.I_M_Product.Table_Name));
		locatorLookup = Suppliers.memoize(() -> LookupDataSourceFactory.instance.searchInTableLookup(org.compiere.model.I_M_Locator.Table_Name));
	}

	public ProductsToPickRowsData createProductsToPickRowsData(final PackageableRow packageableRow)
	{
		final ImmutableList<ProductsToPickRow> rows = packageableRow.getPackageables()
				.stream()
				.flatMap(this::createRowsAndStream)
				.collect(ImmutableList.toImmutableList());

		return ProductsToPickRowsData.ofRows(rows);
	}

	private Stream<ProductsToPickRow> createRowsAndStream(final Packageable packageable)
	{
		final ProductId productId = packageable.getProductId();

		final List<I_M_HU> hus = huReservationService.prepareHUQuery()
				.warehouseId(packageable.getWarehouseId())
				.productId(productId)
				.asiId(null)
				.reservedToSalesOrderLineId(packageable.getSalesOrderLineIdOrNull())
				.build()
				.list();

		return handlingUnitsBL
				.getStorageFactory()
				.getHUProductStorages(hus, productId)
				.stream()
				.map(huProductStorage -> createRow(packageable, huProductStorage));
	}

	private ProductsToPickRow createRow(final Packageable packageable, final IHUProductStorage huProductStorage)
	{
		final ProductId productId = huProductStorage.getProductId();
		final I_M_HU hu = huProductStorage.getM_HU();
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

		LookupValue product = productLookup.get().findById(productId);
		LookupValue locator = locatorLookup.get().findById(hu.getM_Locator_ID());

		return ProductsToPickRow.builder()
				.rowId(ProductsToPickRowId.builder()
						.huId(huId)
						.productId(productId)
						.build())
				.product(product)
				.locator(locator)
				// TODO
				// .lotNumberAttr(lotNumberAttr)
				// .expiringDateAttr(expiringDateAttr)
				// .repackNumberAttr(repackNumberAttr)
				// .bruchAttr(bruchAttr)
				.qty(Quantity.of(huProductStorage.getQty(), huProductStorage.getC_UOM()))
				.processed(false)
				.build();
	}

}
