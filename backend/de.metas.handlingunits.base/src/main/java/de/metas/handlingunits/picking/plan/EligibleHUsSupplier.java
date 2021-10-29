package de.metas.handlingunits.picking.plan;

import com.google.common.collect.ImmutableSet;
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
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class EligibleHUsSupplier
{
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final HUReservationService huReservationService;
	private final HUsLoadingCache husCache;

	// Params
	final boolean considerAttributes;

	@Builder
	private EligibleHUsSupplier(
			@NonNull final HUReservationService huReservationService,
			@NonNull final HUsLoadingCache husCache,
			final boolean considerAttributes)
	{
		this.huReservationService = huReservationService;
		this.husCache = husCache;
		this.considerAttributes = considerAttributes;
	}

	public List<TopLevelHUInfo> getTopLevelHUsPossibleToAllocate(final AllocablePackageable packageable)
	{
		final ArrayList<TopLevelHUInfo> result = new ArrayList<>();

		getVHUIdsAlreadyReserved(packageable)
				.stream()
				.map(vhuId -> TopLevelHUInfo.builder()
						.topLevelHUId(husCache.getTopLevelHUId(vhuId))
						.hasHUReservationsForRequestedDocument(true)
						.build())
				.forEach(result::add);

		getVHUIdsEligibleToAllocateAndNotReservedAtAll(packageable)
				.stream()
				.map(vhuId -> TopLevelHUInfo.builder()
						.topLevelHUId(husCache.getTopLevelHUId(vhuId))
						.hasHUReservationsForRequestedDocument(false)
						.build())
				.forEach(result::add);

		return result;
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
}
