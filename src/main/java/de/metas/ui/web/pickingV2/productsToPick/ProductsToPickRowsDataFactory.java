package de.metas.ui.web.pickingV2.productsToPick;

import java.util.List;
import java.util.stream.Stream;

import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.api.impl.LotNumberDateAttributeDAO;
import org.adempiere.util.Services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
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
import lombok.Builder;
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

class ProductsToPickRowsDataFactory
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final HUReservationDocumentFilterService huReservationService;

	private final LookupDataSource productLookup;
	private final LookupDataSource locatorLookup;
	private final IAttributeStorageFactory attributesFactory;

	private static final String ATTR_LotNumber = LotNumberDateAttributeDAO.ATTR_LotNumber;
	private static final String ATTR_BestBeforeDate = AttributeConstants.ATTR_BestBeforeDate;
	private static final String ATTR_RepackNumber = "RepackNumber"; // TODO use it as constant, see RepackNumberUtils
	private static final String ATTR_Damaged = "HU_Damaged"; // TODO use it as constant
	private static final ImmutableSet<String> ATTRIBUTES = ImmutableSet.of(
			ATTR_LotNumber,
			ATTR_BestBeforeDate,
			ATTR_RepackNumber,
			ATTR_Damaged);

	@Builder
	private ProductsToPickRowsDataFactory(
			@NonNull final HUReservationDocumentFilterService huReservationService)
	{
		this.huReservationService = huReservationService;

		productLookup = LookupDataSourceFactory.instance.searchInTableLookup(org.compiere.model.I_M_Product.Table_Name);
		locatorLookup = LookupDataSourceFactory.instance.searchInTableLookup(org.compiere.model.I_M_Locator.Table_Name);

		final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);
		attributesFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();
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

	private ProductsToPickRow createRow(
			final Packageable packageable,
			final IHUProductStorage huProductStorage)
	{
		final ProductId productId = huProductStorage.getProductId();
		final I_M_HU hu = huProductStorage.getM_HU();
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

		final LookupValue product = productLookup.findById(productId);
		final LookupValue locator = locatorLookup.findById(hu.getM_Locator_ID());

		final ProductsToPickRowId rowId = ProductsToPickRowId.builder()
				.huId(huId)
				.productId(productId)
				.build();

		final ImmutableAttributeSet attributes = extractAttributes(hu);

		return ProductsToPickRow.builder()
				.rowId(rowId)
				.product(product)
				.locator(locator)
				//
				// Attributes:
				.lotNumberAttr(attributes.getValueAsStringIfExists(ATTR_LotNumber).orElse(null))
				.expiringDateAttr(attributes.getValueAsLocalDateIfExists(ATTR_BestBeforeDate).orElse(null))
				.repackNumberAttr(attributes.getValueAsStringIfExists(ATTR_RepackNumber).orElse(null))
				.bruchAttr(attributes.getValueAsBooleanIfExists(ATTR_Damaged).orElse(null))
				//
				.qty(Quantity.of(huProductStorage.getQty(), huProductStorage.getC_UOM()))
				.processed(false)
				.build();
	}

	private ImmutableAttributeSet extractAttributes(final I_M_HU hu)
	{
		final IAttributeStorage attributes = attributesFactory.getAttributeStorage(hu);
		return ImmutableAttributeSet.createSubSet(attributes, a -> ATTRIBUTES.contains(a.getValue()));
	}
}
