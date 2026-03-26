package de.metas.handlingunits.picking.job.service.commands.grai;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAIRequired;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.i18n.AdMessageKey;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Builder
public class PickingJobGRAIValidator
{
	private static final AdMessageKey GRAI_COUNT_MISMATCH = AdMessageKey.of("de.metas.handlingunits.picking.GRAICountMismatch");

	// Services
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	// Parameters
	@NonNull private final PickingJob pickingJob;

	// State
	private GRAIRequired _graiRequired; // lazy

	public void validate()
	{
		if (getGRAIRequired().isNo())
		{
			return;
		}

		final HULoadingCache huCache = new HULoadingCache(handlingUnitsDAO, huAttributesBL);

		handlingUnitsDAO.getByIds(pickingJob.getAllPickedHuIds())
				.stream()
				.filter(handlingUnitsBL::isLoadingUnit)
				.forEach(lu -> validateLU(lu, huCache));
	}

	@NonNull
	private GRAIRequired getGRAIRequired()
	{
		if (_graiRequired == null)
		{
			_graiRequired = computeGRAIRequired();
		}
		return _graiRequired;
	}

	@NonNull
	private GRAIRequired computeGRAIRequired()
	{
		final BPartnerId customerId = pickingJob.getCustomerId();
		if (customerId == null)
		{
			return GRAIRequired.No;
		}

		final I_C_BPartner bpartner = bpartnerDAO.getById(customerId);
		return GRAIRequired.optionalOfNullableCode(bpartner.getGRAIRequired()).orElse(GRAIRequired.No);
	}

	private void validateLU(
			@NonNull final I_M_HU lu,
			@NonNull final HULoadingCache huCache)
	{
		int totalTUs = 0;
		int countWithGRAI = 0;

		final TUWithoutGRAICollection tusWithoutGRAI = new TUWithoutGRAICollection(huCache);

		for (final I_M_HU_Item item : handlingUnitsDAO.retrieveItems(lu))
		{
			final String itemType = item.getItemType();
			if (X_M_HU_Item.ITEMTYPE_HandlingUnit.equals(itemType))
			{
				for (final I_M_HU childTU : handlingUnitsDAO.retrieveIncludedHUs(item))
				{
					totalTUs++;
					if (huCache.getGRAI(childTU) != null)
					{
						countWithGRAI++;
					}
					else
					{
						tusWithoutGRAI.add(TUWithoutGRAI.ofRegularTU(HuId.ofRepoId(childTU.getM_HU_ID())));
					}
				}
			}
			else if (X_M_HU_Item.ITEMTYPE_HUAggregate.equals(itemType))
			{
				final int aggregatedTUCount = item.getQty().intValue();
				totalTUs += aggregatedTUCount;

				final List<I_M_HU> aggregateVHUs = handlingUnitsDAO.retrieveIncludedHUs(item);
				int graisInAggregate = 0;
				for (final I_M_HU aggregateVHU : aggregateVHUs)
				{
					graisInAggregate += huCache.getGRAISet(aggregateVHU).size();
				}
				countWithGRAI += graisInAggregate;

				final int missingInAggregate = aggregatedTUCount - graisInAggregate;
				if (missingInAggregate > 0 && !aggregateVHUs.isEmpty())
				{
					tusWithoutGRAI.add(TUWithoutGRAI.ofAggregate(
							HuId.ofRepoId(aggregateVHUs.get(0).getM_HU_ID()),
							missingInAggregate));
				}
			}
		}

		//
		// Case: less GRAIs found than the number of TUs
		if (countWithGRAI < totalTUs)
		{
			final GRAIRequired graiRequired = getGRAIRequired();
			if (graiRequired == GRAIRequired.Yes)
			{
				throw new AdempiereException(GRAI_COUNT_MISMATCH, handlingUnitsBL.getDisplayName(lu), countWithGRAI, totalTUs);
			}
			else if (graiRequired == GRAIRequired.YesWithDummyGRAIs)
			{
				generateDummyGRAIs(tusWithoutGRAI, lu);
			}
		}
	}

	private void generateDummyGRAIs(@NonNull final TUWithoutGRAICollection tusWithoutGRAI, @NonNull final I_M_HU lu)
	{
		final String poReference = extractPOReference();
		if (poReference == null)
		{
			throw new AdempiereException("Cannot generate dummy GRAIs: POReference not found for LU " + handlingUnitsBL.getDisplayName(lu));
		}

		final String paddedPORef = DummyGRAIGenerator.padPOReference(poReference);
		final int startCounter = findMaxExistingDummyCounter(paddedPORef) + 1;

		tusWithoutGRAI.assignDummyGRAIs(paddedPORef, startCounter);
	}

	@Nullable
	private String extractPOReference()
	{
		for (final PickingJobLine line : pickingJob.getLines())
		{
			final OrderId orderId = line.getSalesOrderAndLineId().getOrderId();
			final I_C_Order order = orderDAO.getById(orderId);
			final String poReference = StringUtils.trimBlankToNull(order.getPOReference());
			if (poReference != null)
			{
				return poReference;
			}
		}
		return null;
	}

	private int findMaxExistingDummyCounter(@NonNull final String paddedPORef)
	{
		final String dummyPrefix = DummyGRAIGenerator.buildDummyPrefix(paddedPORef);
		int maxCounter = 0;

		for (final I_M_HU hu : handlingUnitsDAO.getByIds(pickingJob.getAllPickedHuIds()))
		{
			if (!handlingUnitsBL.isLoadingUnit(hu))
			{
				continue;
			}

			for (final I_M_HU_Item item : handlingUnitsDAO.retrieveItems(hu))
			{
				final String itemType = item.getItemType();
				if (X_M_HU_Item.ITEMTYPE_HandlingUnit.equals(itemType))
				{
					for (final I_M_HU childTU : handlingUnitsDAO.retrieveIncludedHUs(item))
					{
						maxCounter = Math.max(maxCounter, DummyGRAIGenerator.extractDummyCounter(
								GRAI.ofNullableCanonicalString(huAttributesBL.getHUAttributeValue(childTU, AttributeConstants.ATTR_GRAI)),
								dummyPrefix));
					}
				}
				else if (X_M_HU_Item.ITEMTYPE_HUAggregate.equals(itemType))
				{
					for (final I_M_HU aggregateVHU : handlingUnitsDAO.retrieveIncludedHUs(item))
					{
						final int aggregateMax = GRAISet.ofNullableCommaSeparated(
										huAttributesBL.getHUAttributeValue(aggregateVHU, AttributeConstants.ATTR_GRAI))
								.stream()
								.mapToInt(grai -> DummyGRAIGenerator.extractDummyCounter(grai, dummyPrefix))
								.max()
								.orElse(0);
						maxCounter = Math.max(maxCounter, aggregateMax);
					}
				}
			}
		}

		return maxCounter;
	}

	//
	//
	//
	// ----------------------------------------------------------------------------
	//
	//
	//

	private static class TUWithoutGRAI
	{
		@NonNull private final HuId huId;
		private final boolean isAggregate;
		private final int missingCount;

		private TUWithoutGRAI(@NonNull final HuId huId, final boolean isAggregate, final int missingCount)
		{
			this.huId = huId;
			this.isAggregate = isAggregate;
			this.missingCount = missingCount;
		}

		static TUWithoutGRAI ofRegularTU(@NonNull final HuId huId)
		{
			return new TUWithoutGRAI(huId, false, 1);
		}

		static TUWithoutGRAI ofAggregate(@NonNull final HuId huId, final int missingCount)
		{
			return new TUWithoutGRAI(huId, true, missingCount);
		}
	}

	private static class TUWithoutGRAICollection
	{
		@NonNull private final HULoadingCache huCache;
		private final List<TUWithoutGRAI> items = new ArrayList<>();

		TUWithoutGRAICollection(@NonNull final HULoadingCache huCache)
		{
			this.huCache = huCache;
		}

		void add(@NonNull final TUWithoutGRAI item)
		{
			items.add(item);
		}

		boolean isEmpty()
		{
			return items.isEmpty();
		}

		private ImmutableSet<HuId> getAggregateVHUIds()
		{
			return items.stream()
					.filter(t -> t.isAggregate)
					.map(t -> t.huId)
					.collect(ImmutableSet.toImmutableSet());
		}

		void assignDummyGRAIs(@NonNull final String paddedPORef, final int startCounter)
		{
			huCache.loadHUs(getAggregateVHUIds());

			int counter = startCounter;
			for (final TUWithoutGRAI tuInfo : items)
			{
				if (tuInfo.isAggregate)
				{
					final List<GRAI> dummyGrais = new ArrayList<>();

					// Preserve existing GRAIs on the aggregate VHU
					huCache.getGRAISet(tuInfo.huId).forEach(dummyGrais::add);

					// Add missing dummies
					for (int i = 0; i < tuInfo.missingCount; i++)
					{
						if (counter > DummyGRAIGenerator.MAX_DUMMY_COUNTER)
						{
							throw new AdempiereException("Cannot generate more than " + DummyGRAIGenerator.MAX_DUMMY_COUNTER + " dummy GRAIs per order");
						}
						dummyGrais.add(DummyGRAIGenerator.buildDummyGRAI(paddedPORef, counter));
						counter++;
					}

					huCache.setGRAISet(tuInfo.huId, GRAISet.ofCollection(dummyGrais));
				}
				else
				{
					if (counter > DummyGRAIGenerator.MAX_DUMMY_COUNTER)
					{
						throw new AdempiereException("Cannot generate more than " + DummyGRAIGenerator.MAX_DUMMY_COUNTER + " dummy GRAIs per order");
					}
					huCache.setGRAI(tuInfo.huId, DummyGRAIGenerator.buildDummyGRAI(paddedPORef, counter));
					counter++;
				}
			}
		}
	}

	//
	//
	//
	// --------------------------------------------------------------------------
	//
	//
	//

	private static class HULoadingCache
	{
		@NonNull private final IHandlingUnitsDAO handlingUnitsDAO;
		@NonNull private final IHUAttributesBL huAttributesBL;
		private final HashMap<HuId, I_M_HU> husById = new HashMap<>();

		HULoadingCache(
				@NonNull final IHandlingUnitsDAO handlingUnitsDAO,
				@NonNull final IHUAttributesBL huAttributesBL)
		{
			this.handlingUnitsDAO = handlingUnitsDAO;
			this.huAttributesBL = huAttributesBL;
		}

		void loadHUs(@NonNull final Collection<HuId> huIds)
		{
			if (huIds.isEmpty())
			{
				return;
			}
			handlingUnitsDAO.getByIds(huIds)
					.forEach(hu -> husById.put(HuId.ofRepoId(hu.getM_HU_ID()), hu));
		}

		@Nullable
		GRAI getGRAI(@NonNull final I_M_HU hu)
		{
			return GRAI.ofNullableCanonicalString(huAttributesBL.getHUAttributeValue(hu, AttributeConstants.ATTR_GRAI));
		}

		@NonNull
		GRAISet getGRAISet(@NonNull final I_M_HU hu)
		{
			return GRAISet.ofNullableCommaSeparated(huAttributesBL.getHUAttributeValue(hu, AttributeConstants.ATTR_GRAI));
		}

		@NonNull
		GRAISet getGRAISet(@NonNull final HuId huId)
		{
			return getGRAISet(getHU(huId));
		}

		private I_M_HU getHU(@NonNull final HuId huId)
		{
			return Check.assumeNotNull(husById.get(huId), "HU not loaded into cache: {}", huId);
		}

		void setGRAI(@NonNull final HuId huId, @NonNull final GRAI grai)
		{
			huAttributesBL.updateHUAttribute(huId, AttributeConstants.ATTR_GRAI, grai.toCanonicalString());
		}

		void setGRAISet(@NonNull final HuId huId, @NonNull final GRAISet graiSet)
		{
			huAttributesBL.updateHUAttribute(huId, AttributeConstants.ATTR_GRAI, graiSet.toCommaSeparatedString());
		}
	}
}
