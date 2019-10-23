package de.metas.ui.web.pickingV2.productsToPick.rows.factory;

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
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.api.impl.LotNumberDateAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.reservation.HUReservation;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.order.OrderLineId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.pickingV2.packageable.PackageableRow;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductInfo;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRow;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowId;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowsData;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.lookup.LookupValueByIdSupplier;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
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

public final class ProductsToPickRowsDataFactory
{
	// services
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
	private final IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);
	private final IBPartnerBL bpartnersService;
	private final HUReservationService huReservationService;
	private final PickingCandidateService pickingCandidateService;

	private final LookupValueByIdSupplier locatorLookup;
	private final IAttributeStorageFactory attributesFactory;

	private final ProductsToPickSourceStorage storages = new ProductsToPickSourceStorage();
	private final Map<HuId, ImmutableAttributeSet> huAttributesCache = new HashMap<>();

	private final boolean considerAttributes;

	public static final String ATTR_LotNumber = LotNumberDateAttributeDAO.ATTR_LotNumber;
	public static final String ATTR_BestBeforeDate = AttributeConstants.ATTR_BestBeforeDate;
	public static final String ATTR_RepackNumber = "RepackNumber"; // TODO use it as constant, see RepackNumberUtils
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

		if (packageable.isAllocated())
		{
			return rows.stream();
		}

		rows.add(createQtyNotAvailableRowForRemainingQtyToAllocate(packageable));
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
			final ReservableStorage storage = storages.getStorage(pickFromHUId, productId);
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
		storages.warmUpCacheForHuIds(huIds); // pre-load all HUs
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
		final ReservableStorage storage = storages.getStorage(huId, productId);
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

		if (!developerModeBL.isEnabled())
		{
			return null;
		}

		return "<" + huId.getRepoId() + ">";
	}

	private ProductInfo getProductInfo(@NonNull final ProductId productId)
	{
		final I_M_Product productRecord = productsRepo.getById(productId);

		final UomId packageUOMId = UomId.ofRepoIdOrNull(productRecord.getPackage_UOM_ID());
		final String packageSizeUOM;
		if (packageUOMId != null)
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
		final I_M_HU hu = storages.getHU(huId);
		final int locatorId = hu.getM_Locator_ID();
		if (locatorId <= 0)
		{
			return null;
		}
		return locatorLookup.findById(locatorId);
	}

	private ImmutableAttributeSet getHUAttributes(final HuId huId)
	{
		return huAttributesCache.computeIfAbsent(huId, this::retrieveHUAttributes);
	}

	private ImmutableAttributeSet retrieveHUAttributes(final HuId huId)
	{
		final I_M_HU hu = storages.getHU(huId);
		final IAttributeStorage attributes = attributesFactory.getAttributeStorage(hu);
		return ImmutableAttributeSet.createSubSet(attributes, a -> ATTRIBUTES.contains(a.getValue()));
	}
}
