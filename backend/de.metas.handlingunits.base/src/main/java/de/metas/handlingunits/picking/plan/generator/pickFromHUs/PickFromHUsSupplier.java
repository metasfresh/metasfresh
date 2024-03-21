package de.metas.handlingunits.picking.plan.generator.pickFromHUs;

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
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_M_Attribute;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;

public class PickFromHUsSupplier
{
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IAttributesBL attributesBL = Services.get(IAttributesBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final HUReservationService huReservationService;
	@Getter
	private final HUsLoadingCache husCache;

	private static final AttributeCode ATTR_SerialNo;
	private static final AttributeCode ATTR_LotNumber;
	private static final AttributeCode ATTR_BestBeforeDate;
	private static final AttributeCode ATTR_RepackNumber;
	public static final ImmutableSet<AttributeCode> ATTRIBUTES = ImmutableSet.of(
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
			@Nullable final HUsLoadingCache sharedHUsCache,
			final boolean considerAttributes,
			final boolean fallbackLotNumberToHUValue)
	{
		this.huReservationService = huReservationService;
		this.husCache = sharedHUsCache != null ? sharedHUsCache : new HUsLoadingCache(ATTRIBUTES);
		this.considerAttributes = considerAttributes;
		this.fallbackLotNumberToHUValue = fallbackLotNumberToHUValue;
	}

	public ImmutableList<PickFromHU> getEligiblePickFromHUs(@NonNull final PickFromHUsGetRequest request)
	{
		final LinkedHashMap<HuId, PickFromHU> pickFromHUs = new LinkedHashMap<>();

		getVHUIdsAlreadyReserved(request)
				.stream()
				.map(this::createPickFromHUByVHUId)
				.map(PickFromHU::withHuReservedForThisLine)
				.forEach(pickFromHU -> pickFromHUs.computeIfAbsent(pickFromHU.getTopLevelHUId(), k -> pickFromHU));

		getVHUIdsEligibleToAllocateAndNotReservedAtAll(request)
				.stream()
				.map(this::createPickFromHUByVHUId)
				.forEach(pickFromHU -> pickFromHUs.computeIfAbsent(pickFromHU.getTopLevelHUId(), k -> pickFromHU));

		final ProductId productId = request.getProductId();
		final AlternativePickFromKeys alternatives = pickFromHUs.values().stream()
				.map(pickFromHU -> toAlternativePickFromKey(pickFromHU, productId))
				.collect(AlternativePickFromKeys.collect());

		return pickFromHUs.values().stream()
				.map(pickFromHU -> withAlternatives(pickFromHU, productId, alternatives))
				.sorted(getAllocationOrder(request.getBestBeforePolicy()))
				.collect(ImmutableList.toImmutableList());
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
				.topLevelHUId(topLevelHUId)
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

	private ImmutableSet<HuId> getVHUIdsAlreadyReserved(@NonNull final PickFromHUsGetRequest request)
	{
		return request.getReservationRef()
				.flatMap(reservationRef -> huReservationService.getByDocumentRef(reservationRef, request.getOnlyHuIds()))
				.map(HUReservation::getVhuIds)
				.orElseGet(ImmutableSet::of);
	}

	private ImmutableSet<HuId> getVHUIdsEligibleToAllocateAndNotReservedAtAll(
			@NonNull final PickFromHUsGetRequest request)
	{
		final IHUQueryBuilder vhuQuery = handlingUnitsDAO
				.createHUQueryBuilder()
				.setOnlyTopLevelHUs(false)
				.addOnlyInLocatorIds(Check.assumeNotEmpty(request.getPickFromLocatorIds(), "no pick from locators set: {}", request))
				.addOnlyWithProductId(request.getProductId())
				.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
				.setExcludeReserved();

		if(request.isEnforceMandatoryAttributesOnPicking())
		{
			final ImmutableList<I_M_Attribute> attributesMandatoryOnPicking = attributesBL.getAttributesMandatoryOnPicking(request.getProductId());
			for (final I_M_Attribute attribute : attributesMandatoryOnPicking)
			{
				vhuQuery.addOnlyWithAttributeNotNull(AttributeCode.ofString(attribute.getValue()));
			}
		}

		// ASI
		if (considerAttributes)
		{
			final ImmutableAttributeSet attributeSet = attributeDAO.getImmutableAttributeSetById(request.getAsiId());
			// TODO: shall we consider only storage relevant attributes?
			vhuQuery.addOnlyWithAttributeValuesMatchingPartnerAndProduct(request.getPartnerId(), request.getProductId(), attributeSet);
			vhuQuery.allowSqlWhenFilteringAttributes(huReservationService.isAllowSqlWhenFilteringHUAttributes());
		}

		if (request.getOnlyHuIds() != null)
		{
			vhuQuery.addOnlyHUIds(request.getOnlyHuIds());
		}
		else
		{
			vhuQuery.addPIVersionToInclude(HuPackingInstructionsVersionId.VIRTUAL);
		}

		final ImmutableSet<HuId> result = vhuQuery.listIds();
		warmUpCacheForHuIds(result);
		return result;
	}

	private void warmUpCacheForHuIds(@NonNull final Collection<HuId> huIds)
	{
		husCache.warmUpCacheForHuIds(huIds);
		huReservationService.warmup(huIds);
	}
	
	private PickFromHU withAlternatives(
			@NonNull final PickFromHU pickFromHU,
			@NonNull final ProductId productId,
			@NonNull final AlternativePickFromKeys alternatives)
	{
		final AlternativePickFromKey excludeKey = toAlternativePickFromKey(pickFromHU, productId);
		return pickFromHU.withAlternatives(alternatives.filter(alternativePickFromKey -> !alternativePickFromKey.equals(excludeKey)));
	}

	private static AlternativePickFromKey toAlternativePickFromKey(@NonNull final PickFromHU pickFromHU, @NonNull final ProductId productId)
	{
		return AlternativePickFromKey.of(pickFromHU.getLocatorId(), pickFromHU.getTopLevelHUId(), productId);
	}

	public static Comparator<PickFromHU> getAllocationOrder(@NonNull final ShipmentAllocationBestBeforePolicy bestBeforePolicy)
	{
		return Comparator.
				<PickFromHU>comparingInt(pickFromHU -> pickFromHU.isHuReservedForThisLine() ? 0 : 1) // consider reserved HU first
				.thenComparing(bestBeforePolicy.comparator(PickFromHU::getExpiringDate)) // then first/last expiring HU
				.thenComparing(PickFromHU::getTopLevelHUId); // then by HUId
	}
}
