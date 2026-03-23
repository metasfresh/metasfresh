package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesBL;
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
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_HU;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Builder
public class PickingJobGRAIValidator
{
	private static final AdMessageKey GRAI_COUNT_MISMATCH = AdMessageKey.of("de.metas.handlingunits.picking.GRAICountMismatch");
	private static final String MIGROS_COMPANY_PREFIX = "7613204";
	private static final String MIGROS_ASSET_TYPE = "00307";
	private static final int MAX_DUMMY_COUNTER = 99;

	@NonNull private final PickingJob pickingJob;

	public void validate()
	{
		final BPartnerId customerId = pickingJob.getCustomerId();
		if (customerId == null)
		{
			return;
		}

		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final I_C_BPartner bpartner = bpartnerDAO.getById(customerId);
		final String graiRequired = bpartner.getGRAIRequired();
		if (graiRequired == null || "N".equals(graiRequired))
		{
			return;
		}

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final ImmutableSet<HuId> pickedHuIds = pickingJob.getAllPickedHuIds();
		for (final HuId huId : pickedHuIds)
		{
			final I_M_HU hu = handlingUnitsDAO.getById(huId);
			if (!handlingUnitsBL.isLoadingUnit(hu))
			{
				continue;
			}

			validateLU(hu, graiRequired, handlingUnitsDAO, handlingUnitsBL);
		}
	}

	private void validateLU(
			@NonNull final I_M_HU lu,
			@NonNull final String graiRequired,
			@NonNull final IHandlingUnitsDAO handlingUnitsDAO,
			@NonNull final IHandlingUnitsBL handlingUnitsBL)
	{
		final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

		int totalTUs = 0;
		int countWithGRAI = 0;

		// Collect TUs without GRAI for potential dummy generation
		final List<TUWithoutGRAI> tusWithoutGRAI = new ArrayList<>();

		for (final I_M_HU_Item item : handlingUnitsDAO.retrieveItems(lu))
		{
			final String itemType = item.getItemType();
			if (X_M_HU_Item.ITEMTYPE_HandlingUnit.equals(itemType))
			{
				for (final I_M_HU childTU : handlingUnitsDAO.retrieveIncludedHUs(item))
				{
					totalTUs++;
					final String grai = huAttributesBL.getHUAttributeValue(childTU, org.adempiere.mm.attributes.api.AttributeConstants.ATTR_GRAI);
					if (Check.isNotBlank(grai))
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
					final String graiValue = huAttributesBL.getHUAttributeValue(aggregateVHU, org.adempiere.mm.attributes.api.AttributeConstants.ATTR_GRAI);
					if (Check.isNotBlank(graiValue))
					{
						for (final String singleGrai : graiValue.split(","))
						{
							if (!singleGrai.trim().isEmpty())
							{
								graisInAggregate++;
							}
						}
					}
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

		if (countWithGRAI >= totalTUs)
		{
			// All TUs have GRAIs
			return;
		}

		if ("Y".equals(graiRequired))
		{
			final String luDisplayName = handlingUnitsBL.getDisplayName(lu);
			throw new AdempiereException(GRAI_COUNT_MISMATCH, luDisplayName, countWithGRAI, totalTUs);
		}
		else if ("D".equals(graiRequired))
		{
			generateDummyGRAIs(tusWithoutGRAI, lu, handlingUnitsBL);
		}
	}

	private void generateDummyGRAIs(
			@NonNull final List<TUWithoutGRAI> tusWithoutGRAI,
			@NonNull final I_M_HU lu,
			@NonNull final IHandlingUnitsBL handlingUnitsBL)
	{
		final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

		final String poReference = extractPOReference();
		if (poReference == null)
		{
			final String luDisplayName = handlingUnitsBL.getDisplayName(lu);
			throw new AdempiereException("Cannot generate dummy GRAIs: POReference not found for LU " + luDisplayName);
		}

		final String paddedPORef = StringUtils.lpadZero(poReference, 10, "POReference");
		// paddedPORef is 10 chars; lpadZero throws if > 10 chars

		// Find the max existing dummy counter across all picked HUs for the same order
		final int existingMaxCounter = findMaxExistingDummyCounter(paddedPORef);

		int counter = existingMaxCounter + 1;
		for (final TUWithoutGRAI tuInfo : tusWithoutGRAI)
		{
			if (tuInfo.isAggregate)
			{
				// For aggregate VHU: build comma-separated list of dummy GRAIs
				final List<String> dummyGrais = new ArrayList<>();

				// First read existing GRAIs on the aggregate VHU
				final I_M_HU aggregateVHU = Services.get(IHandlingUnitsDAO.class).getById(tuInfo.huId);
				final String existingGraiValue = huAttributesBL.getHUAttributeValue(aggregateVHU, org.adempiere.mm.attributes.api.AttributeConstants.ATTR_GRAI);
				if (Check.isNotBlank(existingGraiValue))
				{
					for (final String existing : existingGraiValue.split(","))
					{
						final String trimmed = existing.trim();
						if (!trimmed.isEmpty())
						{
							dummyGrais.add(trimmed);
						}
					}
				}

				// Add missing dummies
				for (int i = 0; i < tuInfo.missingCount; i++)
				{
					if (counter > MAX_DUMMY_COUNTER)
					{
						throw new AdempiereException("Cannot generate more than " + MAX_DUMMY_COUNTER + " dummy GRAIs per order");
					}
					dummyGrais.add(buildDummyGRAI(paddedPORef, counter));
					counter++;
				}

				final String commaSeparated = String.join(",", dummyGrais);
				huAttributesBL.updateHUAttribute(tuInfo.huId, org.adempiere.mm.attributes.api.AttributeConstants.ATTR_GRAI, commaSeparated);
			}
			else
			{
				// Regular TU: single GRAI
				if (counter > MAX_DUMMY_COUNTER)
				{
					throw new AdempiereException("Cannot generate more than " + MAX_DUMMY_COUNTER + " dummy GRAIs per order");
				}
				final String dummyGrai = buildDummyGRAI(paddedPORef, counter);
				huAttributesBL.updateHUAttribute(tuInfo.huId, org.adempiere.mm.attributes.api.AttributeConstants.ATTR_GRAI, dummyGrai);
				counter++;
			}
		}
	}

	@Nullable
	private String extractPOReference()
	{
		// Navigate from picking job lines to the sales order POReference
		for (final PickingJobLine line : pickingJob.getLines())
		{
			final OrderId orderId = line.getSalesOrderAndLineId().getOrderId();
			final I_C_Order order = Services.get(IOrderDAO.class).getById(orderId);
			final String poReference = order.getPOReference();
			if (Check.isNotBlank(poReference))
			{
				return poReference;
			}
		}
		return null;
	}

	private int findMaxExistingDummyCounter(@NonNull final String paddedPORef)
	{
		final String dummyPrefix = MIGROS_COMPANY_PREFIX + "." + MIGROS_ASSET_TYPE + "." + paddedPORef;
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

		int maxCounter = 0;

		for (final HuId huId : pickingJob.getAllPickedHuIds())
		{
			final I_M_HU hu = handlingUnitsDAO.getById(huId);
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
						maxCounter = Math.max(maxCounter, extractDummyCounter(
								huAttributesBL.getHUAttributeValue(childTU, org.adempiere.mm.attributes.api.AttributeConstants.ATTR_GRAI),
								dummyPrefix));
					}
				}
				else if (X_M_HU_Item.ITEMTYPE_HUAggregate.equals(itemType))
				{
					for (final I_M_HU aggregateVHU : handlingUnitsDAO.retrieveIncludedHUs(item))
					{
						final String graiValue = huAttributesBL.getHUAttributeValue(aggregateVHU, org.adempiere.mm.attributes.api.AttributeConstants.ATTR_GRAI);
						if (Check.isNotBlank(graiValue))
						{
							for (final String singleGrai : graiValue.split(","))
							{
								maxCounter = Math.max(maxCounter, extractDummyCounter(singleGrai.trim(), dummyPrefix));
							}
						}
					}
				}
			}
		}

		return maxCounter;
	}

	private static int extractDummyCounter(@Nullable final String grai, @NonNull final String dummyPrefix)
	{
		if (grai == null || !grai.startsWith(dummyPrefix))
		{
			return 0;
		}
		final String counterStr = grai.substring(dummyPrefix.length());
		if (counterStr.length() != 2)
		{
			return 0;
		}
		try
		{
			return Integer.parseInt(counterStr);
		}
		catch (final NumberFormatException e)
		{
			return 0;
		}
	}

	@NonNull
	private static String buildDummyGRAI(@NonNull final String paddedPORef, final int counter)
	{
		return MIGROS_COMPANY_PREFIX + "." + MIGROS_ASSET_TYPE + "." + paddedPORef + String.format("%02d", counter);
	}

	// --- Helper types ---

	private static class TUWithoutGRAI
	{
		final HuId huId;
		final boolean isAggregate;
		final int missingCount;

		private TUWithoutGRAI(final HuId huId, final boolean isAggregate, final int missingCount)
		{
			this.huId = huId;
			this.isAggregate = isAggregate;
			this.missingCount = missingCount;
		}

		static TUWithoutGRAI ofRegularTU(final HuId huId)
		{
			return new TUWithoutGRAI(huId, false, 1);
		}

		static TUWithoutGRAI ofAggregate(final HuId huId, final int missingCount)
		{
			return new TUWithoutGRAI(huId, true, missingCount);
		}
	}
}
