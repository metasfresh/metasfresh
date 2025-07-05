package de.metas.handlingunits.picking.job.service.commands.pick;

import de.metas.handlingunits.HUContextHolder;
import de.metas.handlingunits.allocation.transfer.LUTUResult;
import de.metas.handlingunits.allocation.transfer.LUTUResult.TUsList;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.candidate.commands.PackedHUWeightNetUpdater;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.HashSet;

@Builder
class PickedHUAttributesUpdater
{
	@NonNull private final IUOMConversionBL uomConversionBL;

	public void updateWeight(
			@NonNull final TUsList tuList,
			@NonNull final ProductId productId,
			@NonNull final Quantity catchWeight)
	{
		final PackedHUWeightNetUpdater weightUpdater = new PackedHUWeightNetUpdater(uomConversionBL, HUContextHolder.getCurrent(), productId, catchWeight);
		weightUpdater.updatePackToHUs(tuList.toHURecords());
	}

	public void updatePickAttributes(@NonNull final LUTUResult result, @NonNull final PickAttributes pickAttributes)
	{
		if (!pickAttributes.hasAttributesToSetExcludingWeight())
		{
			return;
		}

		result.getLus().forEach(lu -> updatePickAttributes(lu, pickAttributes));
		result.getTopLevelTUs().forEach(tu -> updatePickAttributes(tu, pickAttributes));
	}

	private void updatePickAttributes(@NonNull final LUTUResult.LU lu, @NonNull PickAttributes pickAttributes)
	{
		lu.getTus().forEach(tu -> updatePickAttributes(tu, pickAttributes));

		updatePickAttributes(lu.toHU(), pickAttributes, lu.isPreExistingLU());
	}

	public void updatePickAttributes(@NonNull final LUTUResult.TU tu, @NonNull final PickAttributes pickAttributes)
	{
		if (!pickAttributes.hasAttributesToSetExcludingWeight())
		{
			return;
		}

		updatePickAttributes(tu.toHU(), pickAttributes, false);
	}

	private void updatePickAttributes(@NonNull final I_M_HU hu, @NonNull final PickAttributes pickAttributes, final boolean updateFromChildren)
	{
		if (!pickAttributes.hasAttributesToSetExcludingWeight())
		{
			return;
		}

		final IAttributeStorage huAttributes = HUContextHolder.getCurrent().getHUAttributeStorageFactory().getAttributeStorage(hu);
		huAttributes.setSaveOnChange(true);

		if (pickAttributes.isSetBestBeforeDate())
		{
			if (updateFromChildren)
			{
				huAttributes.setValueNoPropagate(AttributeConstants.ATTR_BestBeforeDate, computeBestBeforeDateFromChildren(huAttributes));
			}
			else
			{
				huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, pickAttributes.getBestBeforeDate());
			}
		}
		if (pickAttributes.isSetLotNo())
		{
			if (updateFromChildren)
			{
				huAttributes.setValueNoPropagate(AttributeConstants.ATTR_LotNumber, computeLotNoFromChildren(huAttributes));
			}
			else
			{
				huAttributes.setValue(AttributeConstants.ATTR_LotNumber, pickAttributes.getLotNo());
			}
		}
	}

	@Nullable
	private static LocalDate computeBestBeforeDateFromChildren(final IAttributeStorage huAttributes)
	{
		final HashSet<LocalDate> childValues = new HashSet<>();
		for (final IAttributeStorage childAttributes : huAttributes.getChildAttributeStorages(true))
		{
			if (childAttributes.hasAttribute(AttributeConstants.ATTR_BestBeforeDate))
			{
				childValues.add(childAttributes.getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate));
			}
		}

		return childValues.size() == 1 ? childValues.iterator().next() : null;
	}

	@Nullable
	private static String computeLotNoFromChildren(final IAttributeStorage huAttributes)
	{
		final HashSet<String> childValues = new HashSet<>();
		for (final IAttributeStorage childAttributes : huAttributes.getChildAttributeStorages(true))
		{
			if (childAttributes.hasAttribute(AttributeConstants.ATTR_LotNumber))
			{
				childValues.add(StringUtils.trimBlankToNull(childAttributes.getValueAsString(AttributeConstants.ATTR_LotNumber)));
			}
		}

		return childValues.size() == 1 ? childValues.iterator().next() : null;
	}
}
