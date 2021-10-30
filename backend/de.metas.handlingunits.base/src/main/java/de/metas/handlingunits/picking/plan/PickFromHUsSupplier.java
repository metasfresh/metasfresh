package de.metas.handlingunits.picking.plan;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.reservation.HUReservation;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

class PickFromHUsSupplier
{
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final HUReservationService huReservationService;
	private final HUsLoadingCache husCache;

	private static final AttributeCode ATTR_SerialNo;
	private static final AttributeCode ATTR_LotNumber;
	private static final AttributeCode ATTR_BestBeforeDate;
	private static final AttributeCode ATTR_RepackNumber;
	static final ImmutableSet<AttributeCode> ATTRIBUTES = ImmutableSet.of(
			ATTR_SerialNo = AttributeConstants.ATTR_SerialNo,
			ATTR_LotNumber = AttributeConstants.ATTR_LotNumber,
			ATTR_BestBeforeDate = AttributeConstants.ATTR_BestBeforeDate,
			ATTR_RepackNumber = AttributeConstants.ATTR_RepackNumber);

	// Params
	private final boolean considerAttributes;
	private final boolean fallbackLotNumberToHUValue;

	@Builder
	private PickFromHUsSupplier(
			@NonNull final HUReservationService huReservationService,
			@NonNull final HUsLoadingCache husCache,
			final boolean considerAttributes,
			final boolean fallbackLotNumberToHUValue)
	{
		this.huReservationService = huReservationService;
		this.husCache = husCache;
		this.considerAttributes = considerAttributes;
		this.fallbackLotNumberToHUValue = fallbackLotNumberToHUValue;
	}

	public List<PickFromHU> getEligiblePickFromHUs(
			@NonNull final AllocablePackageable packageable,
			@NonNull final ShipmentAllocationBestBeforePolicy bestBeforePolicy)
	{
		final ArrayList<PickFromHU> result = new ArrayList<>();

		getVHUIdsAlreadyReserved(packageable)
				.stream()
				.map(vhuId -> createPickFromHUByVHUId(vhuId).withHuReservedForThisLine(true))
				.forEach(result::add);

		getVHUIdsEligibleToAllocateAndNotReservedAtAll(packageable)
				.stream()
				.map(this::createPickFromHUByVHUId)
				.forEach(result::add);

		result.sort(getAllocationOrder(bestBeforePolicy));

		return ImmutableList.copyOf(result);
	}

	private PickFromHU createPickFromHUByVHUId(final @NonNull HuId vhuId)
	{
		final HuId topLevelHUId = husCache.getTopLevelHUId(vhuId);
		return createPickFromHUByTopLevelHUId(topLevelHUId);
	}

	public PickFromHU createPickFromHUByTopLevelHUId(@NonNull final HuId topLevelHUId)
	{
		final LocatorId locatorId = husCache.getLocatorIdByHuId(topLevelHUId);
		final ImmutableAttributeSet attributes = husCache.getHUAttributes(topLevelHUId);

		return PickFromHU.builder()
				.huId(topLevelHUId)
				.huReservedForThisLine(false)
				.locatorId(locatorId)
				//
				.huCode(topLevelHUId.toHUValue())
				.serialNo(attributes.getValueAsStringIfExists(ATTR_SerialNo).orElse(null))
				.lotNumber(attributes.getValueAsStringIfExists(ATTR_LotNumber).orElseGet(() -> buildLotNumberFromHuId(topLevelHUId)))
				.expiringDate(attributes.getValueAsLocalDateIfExists(ATTR_BestBeforeDate).orElse(null))
				.repackNumber(attributes.getValueAsStringIfExists(ATTR_RepackNumber).orElse(null))
				//
				.build();
	}

	@Nullable
	private String buildLotNumberFromHuId(@Nullable final HuId huId)
	{
		return huId != null && fallbackLotNumberToHUValue
				? "<" + huId.toHUValue() + ">"
				: null;
	}

	private ImmutableSet<HuId> getVHUIdsAlreadyReserved(final AllocablePackageable packageable)
	{
		return packageable.getReservationRef()
				.flatMap(huReservationService::getByDocumentRef)
				.map(HUReservation::getVhuIds)
				.orElseGet(ImmutableSet::of);
	}

	private ImmutableSet<HuId> getVHUIdsEligibleToAllocateAndNotReservedAtAll(final AllocablePackageable packageable)
	{
		final Set<WarehouseId> pickingWarehouseIds = warehouseDAO.getWarehouseIdsOfSamePickingGroup(packageable.getWarehouseId());

		final IHUQueryBuilder vhuQuery = handlingUnitsDAO
				.createHUQueryBuilder()
				.addPIVersionToInclude(HuPackingInstructionsVersionId.VIRTUAL)
				.addOnlyInWarehouseIds(pickingWarehouseIds)
				.addOnlyWithProductId(packageable.getProductId())
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.setExcludeReserved();

		// ASI
		if (considerAttributes)
		{
			final ImmutableAttributeSet attributeSet = attributeDAO.getImmutableAttributeSetById(packageable.getAsiId());
			// TODO: shall we consider only storage relevant attributes?
			vhuQuery.addOnlyWithAttributes(attributeSet);
			vhuQuery.allowSqlWhenFilteringAttributes(huReservationService.isAllowSqlWhenFilteringHUAttributes());
		}

		return vhuQuery.listIds();
	}

	public Comparator<PickFromHU> getAllocationOrder(@NonNull final ShipmentAllocationBestBeforePolicy bestBeforePolicy)
	{
		return Comparator.
				<PickFromHU>comparingInt(pickFromHU -> pickFromHU.isHuReservedForThisLine() ? 0 : 1) // consider reserved HU first
				.thenComparing(bestBeforePolicy.comparator(PickFromHU::getExpiringDate)) // then first/last expiring HU
				.thenComparing(PickFromHU::getHuId); // then by HUId
	}
}
