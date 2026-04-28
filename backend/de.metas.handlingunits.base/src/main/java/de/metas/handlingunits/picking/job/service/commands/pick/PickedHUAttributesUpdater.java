package de.metas.handlingunits.picking.job.service.commands.pick;

import de.metas.handlingunits.HUContextHolder;
import de.metas.handlingunits.allocation.transfer.LUTUResult;
import de.metas.handlingunits.allocation.transfer.LUTUResult.LU;
import de.metas.handlingunits.allocation.transfer.LUTUResult.TU;
import de.metas.handlingunits.allocation.transfer.LUTUResult.TUPart;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.candidate.commands.PackedHUWeightNetUpdater;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.HashSet;

@Builder
class PickedHUAttributesUpdater
{
	@NonNull private final IUOMConversionBL uomConversionBL;

	public void updateHUs(
			@NonNull final LUTUResult result,
			@NonNull final PickAttributes pickAttributes,
			@NonNull final ProductId productId)
	{
		updateCatchWeight(result, pickAttributes, productId);
		updatePickAttributes(result, pickAttributes);
	}

	private void updateCatchWeight(@NonNull final LUTUResult result, @NonNull final PickAttributes pickAttributes, @NonNull final ProductId productId)
	{
		final Quantity catchWeight = pickAttributes.getCatchWeight();
		if (catchWeight == null) {return;}

		final PackedHUWeightNetUpdater weightUpdater = new PackedHUWeightNetUpdater(uomConversionBL, HUContextHolder.getCurrent(), productId, catchWeight);
		weightUpdater.updatePackToHUs(result);
	}

	private void updatePickAttributes(@NonNull final LUTUResult result, final @NonNull PickAttributes pickAttributes)
	{
		result.getLus().forEach(lu -> updateLUPickAttributes(lu, pickAttributes));
		result.getTopLevelTUs().forEach(tu -> updateTUPickAttributes(tu, pickAttributes));
	}

	private void updateLUPickAttributes(@NonNull final LU lu, @NonNull PickAttributes pickAttributes)
	{
		lu.getTus().forEach(tu -> updateTUPickAttributes(tu, pickAttributes));

		updateHUPickAttributes(lu.toHU(), pickAttributes, lu.isPreExistingLU());
	}

	private void updateTUPickAttributes(@NonNull final TU tu, @NonNull final PickAttributes pickAttributes)
	{
		if (tu.isFullTU())
		{
			updateHUPickAttributes(tu.toHU(), pickAttributes, false);
		}
		else
		{
			for (final TUPart cu : tu.getCUsNotEmpty())
			{
				updateCUPickAttributes(cu, pickAttributes);
			}
			
			updateHUPickAttributes(tu.toHU(), pickAttributes, true);
		}
	}

	private void updateCUPickAttributes(@NonNull final TUPart cu, @NonNull final PickAttributes pickAttributes)
	{
		updateHUPickAttributes(cu.toHU(), pickAttributes, false);
	}

	private void updateHUPickAttributes(@NonNull final I_M_HU hu, @NonNull final PickAttributes pickAttributes, final boolean recomputeFromChildren)
	{
		if (!pickAttributes.isSetBestBeforeDate()
				&& !pickAttributes.isSetProductionDate()
				&& !pickAttributes.isSetLotNo())
		{
			return;
		}

		final IAttributeStorage huAttributes = HUContextHolder.getCurrent().getHUAttributeStorageFactory().getAttributeStorage(hu);
		huAttributes.setSaveOnChange(true);

		if (pickAttributes.isSetBestBeforeDate())
		{
			if (recomputeFromChildren)
			{
				huAttributes.setValueNoPropagate(AttributeConstants.ATTR_BestBeforeDate, computeDateValueFromChildren(huAttributes, AttributeConstants.ATTR_BestBeforeDate));
			}
			else
			{
				huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, pickAttributes.getBestBeforeDate());
			}
		}
		if (pickAttributes.isSetProductionDate())
		{
			if (recomputeFromChildren)
			{
				huAttributes.setValueNoPropagate(AttributeConstants.ProductionDate, computeDateValueFromChildren(huAttributes, AttributeConstants.ProductionDate));
			}
			else
			{
				huAttributes.setValue(AttributeConstants.ProductionDate, pickAttributes.getProductionDate());
			}
		}
		if (pickAttributes.isSetLotNo())
		{
			if (recomputeFromChildren)
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
	private static LocalDate computeDateValueFromChildren(@NonNull final IAttributeStorage huAttributes, @NonNull final AttributeCode attributeCode)
	{
		final HashSet<LocalDate> childValues = new HashSet<>();
		for (final IAttributeStorage childAttributes : huAttributes.getChildAttributeStorages(true))
		{
			if (childAttributes.hasAttribute(attributeCode))
			{
				childValues.add(childAttributes.getValueAsLocalDate(attributeCode));
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
