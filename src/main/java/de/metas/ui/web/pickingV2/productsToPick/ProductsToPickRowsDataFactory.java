package de.metas.ui.web.pickingV2.productsToPick;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.api.impl.LotNumberDateAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.reservation.HUReservation;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.api.Packageable;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.lookup.LookupValueByIdSupplier;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
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
	// services
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IBPartnerBL bpartnersService;
	private final HUReservationService huReservationService;
	private final PickingCandidateService pickingCandidateService;

	private final LookupValueByIdSupplier locatorLookup;
	private final IAttributeStorageFactory attributesFactory;

	private final Map<ReservableStorageKey, ReservableStorage> storages = new HashMap<>();
	private final Map<HuId, ImmutableAttributeSet> huAttributesCache = new HashMap<>();
	private final Map<HuId, I_M_HU> husCache = new HashMap<>();

	private final boolean considerAttributes;

	static final String ATTR_LotNumber = LotNumberDateAttributeDAO.ATTR_LotNumber;
	static final String ATTR_BestBeforeDate = AttributeConstants.ATTR_BestBeforeDate;
	static final String ATTR_RepackNumber = "RepackNumber"; // TODO use it as constant, see RepackNumberUtils
	private static final ImmutableSet<String> ATTRIBUTES = ImmutableSet.of(
			ATTR_LotNumber,
			ATTR_BestBeforeDate,
			ATTR_RepackNumber);

	@Builder
	private ProductsToPickRowsDataFactory(
			@NonNull final IBPartnerBL bpartnersService,
			@NonNull final HUReservationService huReservationService,
			@NonNull final PickingCandidateService pickingCandidateService,
			//
			@NonNull final LookupValueByIdSupplier locatorLookup,
			//
			final boolean considerAttributes)
	{
		this.bpartnersService = bpartnersService;
		this.huReservationService = huReservationService;
		this.pickingCandidateService = pickingCandidateService;

		this.locatorLookup = locatorLookup;

		final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);
		attributesFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();

		this.considerAttributes = considerAttributes;
	}

	public ProductsToPickRowsData create(final PackageableRow packageableRow)
	{
		final ImmutableList<ProductsToPickRow> rows = packageableRow.getPackageables()
				.stream()
				.map(AllocablePackageable::of)
				.flatMap(this::createRowsAndStream)
				.collect(ImmutableList.toImmutableList());

		return ProductsToPickRowsData.builder()
				.pickingCandidateService(pickingCandidateService)
				.rows(rows)
				.orderBy(DocumentQueryOrderBy.byFieldName(ProductsToPickRow.FIELD_Locator))
				.build();
	}

	private Stream<ProductsToPickRow> createRowsAndStream(final AllocablePackageable packageable)
	{
		final ArrayList<ProductsToPickRow> rows = new ArrayList<>();
		rows.addAll(createRowsFromExistingPickingCandidates(packageable));
		rows.addAll(createRowsFromHUs(packageable));

		if (!packageable.isAllocated())
		{
			rows.add(createQtyNotAvailableRowForRemainingQtyToAllocate(packageable));
		}

		return rows.stream();
	}

	private List<ProductsToPickRow> createRowsFromExistingPickingCandidates(final AllocablePackageable packageable)
	{
		final List<PickingCandidate> pickingCandidates = pickingCandidateService.getByShipmentScheduleIdAndStatus(packageable.getShipmentScheduleId(), PickingCandidateStatus.Draft);

		return pickingCandidates
				.stream()
				.map(pickingCandidate -> createRowFromExistingPickingCandidate(packageable, pickingCandidate))
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());
	}

	private ProductsToPickRow createRowFromExistingPickingCandidate(final AllocablePackageable packageable, final PickingCandidate existingPickingCandidate)
	{
		final Quantity qty;

		final HuId pickFromHUId = existingPickingCandidate.getPickFromHuId();
		if (pickFromHUId != null)
		{
			final ProductId productId = packageable.getProductId();
			final ReservableStorage storage = getStorage(pickFromHUId, productId);
			qty = storage.reserve(packageable, existingPickingCandidate.getQtyPicked());
		}
		else
		{
			qty = existingPickingCandidate.getQtyPicked();
		}

		return createRow(packageable, qty, pickFromHUId, existingPickingCandidate);
	}

	private List<ProductsToPickRow> createRowsFromHUs(final AllocablePackageable packageable)
	{
		if (packageable.isAllocated())
		{
			return ImmutableList.of();
		}

		final Set<HuId> reservedHuIds = getHuIdsReservedForSalesOrderLine(packageable);
		final Set<HuId> availableHuIds = getHuIdsAvailableToAllocate(packageable);

		final ImmutableSet<HuId> allHuIds = ImmutableSet.<HuId> builder()
				.addAll(reservedHuIds)
				.addAll(availableHuIds)
				.build();

		final List<ProductsToPickRow> rows = allHuIds.stream()
				.map(huId -> createZeroQtyRowFromHU(packageable, huId))
				.collect(ImmutableList.toImmutableList());

		final ShipmentAllocationBestBeforePolicy bestBeforePolicy = getBestBeforePolicy(packageable);

		return rows.stream()
				.sorted(Comparator
						.<ProductsToPickRow> comparingInt((row -> row.isHuReservedForThisRow() ? 0 : 1)) // consider reserved HU first
						.thenComparing(bestBeforePolicy.comparator(ProductsToPickRow::getExpiringDate))) // then first/last expiring HU
				.map(row -> allocateRowFromHU(row, packageable))
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());
	}

	private ShipmentAllocationBestBeforePolicy getBestBeforePolicy(final AllocablePackageable packageable)
	{
		Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy = packageable.getBestBeforePolicy();
		if (bestBeforePolicy.isPresent())
		{
			return bestBeforePolicy.get();
		}

		final BPartnerId bpartnerId = packageable.getCustomerId();
		return bpartnersService.getBestBeforePolicy(bpartnerId);
	}

	private Set<HuId> getHuIdsReservedForSalesOrderLine(final AllocablePackageable packageable)
	{
		if (packageable.getSalesOrderLineIdOrNull() == null)
		{
			return ImmutableSet.of();
		}

		final HUReservation huReservation = huReservationService.getBySalesOrderLineId(packageable.getSalesOrderLineIdOrNull()).orElse(null);
		if (huReservation == null)
		{
			return ImmutableSet.of();
		}

		return huReservation.getVhuIds();
	}

	private Set<HuId> getHuIdsAvailableToAllocate(final AllocablePackageable packageable)
	{
		final OrderLineId salesOrderLine = packageable.getSalesOrderLineIdOrNull();

		final Set<HuId> huIds = huReservationService.prepareHUQuery()
				.warehouseId(packageable.getWarehouseId())
				.productId(packageable.getProductId())
				.asiId(considerAttributes ? packageable.getAsiId() : null)
				.reservedToSalesOrderLineIdOrNotReservedAtAll(salesOrderLine)
				.build()
				.listIds();

		warmUpCacheForHuIds(huIds);

		return huIds;
	}

	private void warmUpCacheForHuIds(final Collection<HuId> huIds)
	{
		getHUs(huIds); // pre-load all HUs
		huReservationService.warmup(huIds);
	}

	private ProductsToPickRow allocateRowFromHU(final ProductsToPickRow row, final AllocablePackageable packageable)
	{
		if (packageable.isAllocated())
		{
			return null;
		}

		final HuId huId = row.getHuId();
		final ProductId productId = packageable.getProductId();
		final ReservableStorage storage = getStorage(huId, productId);
		final Quantity qty = storage.reserve(packageable);
		if (qty.isZero())
		{
			return null;
		}

		return row.withQty(qty);
	}

	private ProductsToPickRow createZeroQtyRowFromHU(@NonNull final AllocablePackageable packageable, @NonNull final HuId huId)
	{
		final PickingCandidate existingPickingCandidate = null;
		final Quantity qtyZero = packageable.getQtyToAllocate().toZero();
		return createRow(packageable, qtyZero, huId, existingPickingCandidate);
	}

	private ProductsToPickRow createQtyNotAvailableRowForRemainingQtyToAllocate(final AllocablePackageable packageable)
	{
		final ShipmentScheduleId shipmentScheduleId = packageable.getShipmentScheduleId();

		final ProductInfo productInfo = getProductInfo(packageable.getProductId());

		final ProductsToPickRowId rowId = ProductsToPickRowId.builder()
				.productId(productInfo.getProductId())
				.shipmentScheduleId(packageable.getShipmentScheduleId())
				.huId(null)
				.build();

		return ProductsToPickRow.builder()
				.rowId(rowId)
				.productInfo(productInfo)
				.locator(null) // will be updated from picking candidate
				.qty(packageable.getQtyToAllocate())
				.shipmentScheduleId(shipmentScheduleId)
				.build();
	}

	private ProductsToPickRow createRow(
			@NonNull final AllocablePackageable packageable,
			@NonNull final Quantity qty,
			@Nullable final HuId pickFromHUId,
			@Nullable final PickingCandidate existingPickingCandidate)
	{
		final ShipmentScheduleId shipmentScheduleId = packageable.getShipmentScheduleId();

		final OrderLineId salesOrderLineId = packageable.getSalesOrderLineIdOrNull();
		final boolean huReservedForThisRow = pickFromHUId != null
				&& salesOrderLineId != null
				&& huReservationService.isVhuIdReservedToSalesOrderLineId(pickFromHUId, salesOrderLineId);

		final ProductInfo productInfo = getProductInfo(packageable.getProductId());

		final LookupValue locator = pickFromHUId != null ? getLocatorLookupValueByHuId(pickFromHUId) : null;
		final ImmutableAttributeSet attributes = pickFromHUId != null ? getHUAttributes(pickFromHUId) : ImmutableAttributeSet.EMPTY;

		final ShipperId suggestedShipperId = packageable.getSuggestedShipperId();

		final ProductsToPickRowId rowId = ProductsToPickRowId.builder()
				.productId(productInfo.getProductId())
				.shipmentScheduleId(packageable.getShipmentScheduleId())
				.huId(pickFromHUId)
				.build();

		return ProductsToPickRow.builder()
				.rowId(rowId)
				//
				.productInfo(productInfo)
				.huReservedForThisRow(huReservedForThisRow)
				//
				.locator(locator)
				//
				// Attributes:
				.lotNumber(attributes.getValueAsStringIfExists(ATTR_LotNumber).orElseGet(() -> buildLotNumberFromHuId(pickFromHUId)))
				.expiringDate(attributes.getValueAsLocalDateIfExists(ATTR_BestBeforeDate).orElse(null))
				.repackNumber(attributes.getValueAsStringIfExists(ATTR_RepackNumber).orElse(null))
				//
				.qty(qty)
				//
				.shipmentScheduleId(shipmentScheduleId)
				.suggestedShipperId(suggestedShipperId)
				//
				.build()
				.withUpdatesFromPickingCandidateIfNotNull(existingPickingCandidate);
	}

	private String buildLotNumberFromHuId(final HuId huId)
	{
		if (huId == null)
		{
			return null;
		}

		if (!Services.get(IDeveloperModeBL.class).isEnabled())
		{
			return null;
		}

		return "<" + huId.getRepoId() + ">";
	}

	private ProductInfo getProductInfo(@NonNull final ProductId productId)
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

		final I_M_Product productRecord = productsRepo.getById(productId);

		final int packageUOMId = productRecord.getPackage_UOM_ID();
		final String packageSizeUOM;
		if (packageUOMId > 0)
		{
			final I_C_UOM packageUOM = uomsRepo.getById(packageUOMId);
			packageSizeUOM = packageUOM.getUOMSymbol();
		}
		else
		{
			packageSizeUOM = null;
		}

		final ITranslatableString productName = InterfaceWrapperHelper.getModelTranslationMap(productRecord)
				.getColumnTrl(I_M_Product.COLUMNNAME_Name, productRecord.getName());

		return ProductInfo.builder()
				.productId(productId)
				.code(productRecord.getValue())
				.name(productName)
				.packageSize(productRecord.getPackageSize())
				.packageSizeUOM(packageSizeUOM)
				.build();
	}

	private LookupValue getLocatorLookupValueByHuId(final HuId huId)
	{
		final I_M_HU hu = getHU(huId);
		final int locatorId = hu.getM_Locator_ID();
		if (locatorId <= 0)
		{
			return null;
		}
		return locatorLookup.findById(locatorId);
	}

	private I_M_HU getHU(final HuId huId)
	{
		return husCache.computeIfAbsent(huId, handlingUnitsBL::getById);
	}

	private Collection<I_M_HU> getHUs(final Collection<HuId> huIds)
	{
		return CollectionUtils.getAllOrLoad(husCache, huIds, this::retrieveHUs);
	}

	private Map<HuId, I_M_HU> retrieveHUs(final Collection<HuId> huIds)
	{
		return Maps.uniqueIndex(handlingUnitsBL.getByIds(huIds), hu -> HuId.ofRepoId(hu.getM_HU_ID()));
	}

	private ReservableStorage getStorage(final HuId huId, final ProductId productId)
	{
		final ReservableStorageKey key = ReservableStorageKey.of(huId, productId);
		return storages.computeIfAbsent(key, this::retrieveStorage);
	}

	private ReservableStorage retrieveStorage(final ReservableStorageKey key)
	{
		final ProductId productId = key.getProductId();
		final I_M_HU hu = getHU(key.getHuId());

		final IHUProductStorage huProductStorage = handlingUnitsBL
				.getStorageFactory()
				.getStorage(hu)
				.getProductStorageOrNull(productId);

		if (huProductStorage == null)
		{
			final I_C_UOM uom = productBL.getStockUOM(productId);
			return new ReservableStorage(productId, Quantity.zero(uom));
		}
		else
		{
			final Quantity qtyFreeToReserve = huProductStorage.getQty();
			return new ReservableStorage(productId, qtyFreeToReserve);
		}
	}

	private ImmutableAttributeSet getHUAttributes(final HuId huId)
	{
		return huAttributesCache.computeIfAbsent(huId, this::retrieveHUAttributes);
	}

	private ImmutableAttributeSet retrieveHUAttributes(final HuId huId)
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

		@Getter
		private BPartnerId customerId;
		@Getter
		private final ProductId productId;
		@Getter
		private final AttributeSetInstanceId asiId;
		@Getter
		private final ShipmentScheduleId shipmentScheduleId;
		@Getter
		private final Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy;
		@Getter
		private final WarehouseId warehouseId;
		@Getter
		private final OrderLineId salesOrderLineIdOrNull;

		@Getter
		private final ShipperId suggestedShipperId;

		private final Quantity qtyToAllocateTarget;
		@Getter
		private Quantity qtyToAllocate;

		private AllocablePackageable(@NonNull final Packageable packageable)
		{
			this.customerId = packageable.getCustomerId();
			this.productId = packageable.getProductId();
			this.asiId = packageable.getAsiId();
			this.shipmentScheduleId = packageable.getShipmentScheduleId();
			this.bestBeforePolicy = packageable.getBestBeforePolicy();
			this.warehouseId = packageable.getWarehouseId();
			this.salesOrderLineIdOrNull = packageable.getSalesOrderLineIdOrNull();
			this.suggestedShipperId = packageable.getShipperId();

			qtyToAllocateTarget = packageable.getQtyOrdered()
					.subtract(packageable.getQtyPickedOrDelivered())
					.toZeroIfNegative();

			qtyToAllocate = qtyToAllocateTarget;
		}

		public void allocateQty(final Quantity qty)
		{
			qtyToAllocate = qtyToAllocate.subtract(qty);
		}

		public boolean isAllocated()
		{
			return getQtyToAllocate().signum() <= 0;
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
		private final ProductId productId;
		private Quantity qtyFreeToReserve;

		private ReservableStorage(
				@NonNull final ProductId productId,
				@NonNull final Quantity qtyFreeToReserve)
		{
			this.productId = productId;
			this.qtyFreeToReserve = qtyFreeToReserve.toZeroIfNegative();
		}

		public Quantity reserve(@NonNull final AllocablePackageable allocable)
		{
			final Quantity qtyToReserve = computeEffectiveQtyToReserve(allocable.getQtyToAllocate());
			return reserve(allocable, qtyToReserve);
		}

		public Quantity reserve(@NonNull final AllocablePackageable allocable, @NonNull final Quantity qtyToReserve)
		{
			assertSameProductId(allocable);

			final Quantity qtyReserved = reserveQty(qtyToReserve);
			allocable.allocateQty(qtyReserved);
			return qtyReserved;
		}

		private void assertSameProductId(final AllocablePackageable allocable)
		{
			if (!ProductId.equals(productId, allocable.getProductId()))
			{
				throw new AdempiereException("ProductId not matching")
						.appendParametersToMessage()
						.setParameter("allocable", allocable)
						.setParameter("storage", this);
			}
		}

		private Quantity computeEffectiveQtyToReserve(@NonNull final Quantity qtyToReserve)
		{
			if (qtyToReserve.signum() <= 0)
			{
				return qtyToReserve.toZero();
			}
			if (qtyFreeToReserve.signum() <= 0)
			{
				return qtyToReserve.toZero();
			}

			return qtyToReserve.min(qtyFreeToReserve);
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
