package de.metas.ui.web.pickingV2.productsToPick;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.api.impl.LotNumberDateAttributeDAO;
import org.adempiere.util.Services;
import org.adempiere.util.collections.CollectionUtils;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
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
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

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
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final HUReservationDocumentFilterService huReservationService;

	private final LookupDataSource productLookup;
	private final LookupDataSource locatorLookup;
	private final IAttributeStorageFactory attributesFactory;

	private final Map<ReservableStorageKey, ReservableStorage> storages = new HashMap<>();
	private final Map<HuId, ImmutableAttributeSet> huAttributes = new HashMap<>();
	private final Map<HuId, I_M_HU> husCache = new HashMap<>();

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
		final Set<HuId> huIds = huReservationService.prepareHUQuery()
				.warehouseId(packageable.getWarehouseId())
				.productId(packageable.getProductId())
				.asiId(null)
				.reservedToSalesOrderLineId(packageable.getSalesOrderLineIdOrNull())
				.build()
				.listIds();

		getHUs(huIds); // pre-load all HUs

		final AllocablePackageable allocablePackageable = AllocablePackageable.of(packageable);

		return huIds.stream()
				.map(huId -> createRow(allocablePackageable, huId))
				.filter(Predicates.notNull());
	}

	private ProductsToPickRow createRow(final AllocablePackageable packageable, final HuId huId)
	{
		final ProductId productId = packageable.getProductId();
		final ReservableStorage storage = getStorage(huId, productId);
		final Quantity qty = storage.reserve(packageable);
		if (qty.isZero())
		{
			return null;
		}

		final ImmutableAttributeSet attributes = getAttributes(huId);

		final LookupValue product = productLookup.findById(productId);
		final LookupValue locator = getLocatorByHuId(huId);

		final ProductsToPickRowId rowId = ProductsToPickRowId.builder()
				.huId(huId)
				.productId(productId)
				.build();

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
				.qty(qty)
				.processed(false)
				.build();
	}

	private LookupValue getLocatorByHuId(final HuId huId)
	{
		final I_M_HU hu = getHU(huId);
		return locatorLookup.findById(hu.getM_Locator_ID());
	}

	private I_M_HU getHU(final HuId huId)
	{
		return husCache.computeIfAbsent(huId, handlingUnitsDAO::getById);
	}

	private Collection<I_M_HU> getHUs(final Collection<HuId> huIds)
	{
		return CollectionUtils.getAllOrLoad(husCache, huIds, this::retrieveHUs);
	}

	private Map<HuId, I_M_HU> retrieveHUs(final Collection<HuId> huIds)
	{
		return Maps.uniqueIndex(handlingUnitsDAO.getByIds(huIds), hu -> HuId.ofRepoId(hu.getM_HU_ID()));
	}

	private ReservableStorage getStorage(final HuId huId, final ProductId productId)
	{
		final ReservableStorageKey key = ReservableStorageKey.of(huId, productId);
		return storages.computeIfAbsent(key, this::createStorage);
	}

	private ReservableStorage createStorage(final ReservableStorageKey key)
	{
		final I_M_HU hu = getHU(key.getHuId());
		final IHUProductStorage huProductStorage = handlingUnitsBL
				.getStorageFactory()
				.getStorage(hu)
				.getProductStorageOrNull(key.getProductId());

		final Quantity qtyFreeToReserve = Quantity.of(huProductStorage.getQty(), huProductStorage.getC_UOM());

		return new ReservableStorage(key, qtyFreeToReserve);
	}

	private ImmutableAttributeSet getAttributes(final HuId huId)
	{
		return huAttributes.computeIfAbsent(huId, this::retrieveAttributes);
	}

	private ImmutableAttributeSet retrieveAttributes(final HuId huId)
	{
		final I_M_HU hu = getHU(huId);
		final IAttributeStorage attributes = attributesFactory.getAttributeStorage(hu);
		return ImmutableAttributeSet.createSubSet(attributes, a -> ATTRIBUTES.contains(a.getValue()));
	}

	@ToString
	private static class AllocablePackageable
	{
		public static AllocablePackageable of(final Packageable packageable)
		{
			return new AllocablePackageable(packageable);
		}

		private final Packageable packageable;
		private final Quantity qtyToAllocateTarget;

		@Getter
		private Quantity qtyToAllocate;

		private AllocablePackageable(@NonNull final Packageable packageable)
		{
			this.packageable = packageable;
			qtyToAllocateTarget = packageable.getQtyOrdered()
					.subtract(packageable.getQtyDelivered())
					.subtract(packageable.getQtyPicked())
					.subtract(packageable.getQtyPickedPlanned())
					.toZeroIfNegative();

			qtyToAllocate = qtyToAllocateTarget;
		}

		public ProductId getProductId()
		{
			return packageable.getProductId();
		}

		public void allocateQty(final Quantity qty)
		{
			qtyToAllocate = qtyToAllocate.subtract(qty);
		}
	}

	@Value(staticConstructor = "of")
	private static class ReservableStorageKey
	{
		@NonNull
		HuId huId;
		@NonNull
		ProductId productId;
	}

	@ToString
	private static class ReservableStorage
	{
		private final ReservableStorageKey key;
		private Quantity qtyFreeToReserve;

		private ReservableStorage(
				@NonNull final ReservableStorageKey key,
				@NonNull final Quantity qtyFreeToReserve)
		{
			this.key = key;
			this.qtyFreeToReserve = qtyFreeToReserve.toZeroIfNegative();
		}

		public Quantity reserve(@NonNull final AllocablePackageable allocable)
		{
			if (!Objects.equals(key.getProductId(), allocable.getProductId()))
			{
				throw new AdempiereException("ProductId not matching")
						.appendParametersToMessage()
						.setParameter("allocable", allocable)
						.setParameter("storage", this);
			}

			final Quantity qtyReserved = reserveQty(allocable.getQtyToAllocate());
			allocable.allocateQty(qtyReserved);
			return qtyReserved;
		}

		private Quantity reserveQty(@NonNull final Quantity qtyToReserve)
		{
			if (qtyToReserve.signum() <= 0)
			{
				return qtyToReserve.toZero();
			}
			if (qtyFreeToReserve.signum() <= 0)
			{
				return qtyToReserve.toZero();
			}

			final Quantity qtyToReserveEffective = qtyToReserve.min(qtyFreeToReserve);
			qtyFreeToReserve = qtyFreeToReserve.subtract(qtyToReserveEffective);

			return qtyToReserveEffective;
		}
	}
}
