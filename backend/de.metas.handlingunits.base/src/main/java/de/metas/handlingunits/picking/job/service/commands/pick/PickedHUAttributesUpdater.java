package de.metas.handlingunits.picking.job.service.commands.pick;

import de.metas.handlingunits.HUContextHolder;
import de.metas.handlingunits.allocation.transfer.LUTUResult;
import de.metas.handlingunits.allocation.transfer.LUTUResult.LU;
import de.metas.handlingunits.allocation.transfer.LUTUResult.TU;
import de.metas.handlingunits.allocation.transfer.LUTUResult.TUPart;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.candidate.commands.PackedHUWeightNetUpdater;
import de.metas.handlingunits.serialno.SerialNoSet;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.function.Supplier;

@Builder
class PickedHUAttributesUpdater
{
	@NonNull private final IUOMConversionBL uomConversionBL;

	public void updateHUs(
			@NonNull final LUTUResult result,
			@NonNull final PickAttributes pickAttributes,
			@NonNull final ProductId productId,
			final boolean serialNoPickingEnabled)
	{
		updateCatchWeight(result, pickAttributes, productId);
		updatePickAttributes(result, pickAttributes, serialNoPickingEnabled);
	}

	private void updateCatchWeight(@NonNull final LUTUResult result, @NonNull final PickAttributes pickAttributes, @NonNull final ProductId productId)
	{
		final Quantity catchWeight = pickAttributes.getCatchWeight();
		if (catchWeight == null) {return;}

		final PackedHUWeightNetUpdater weightUpdater = new PackedHUWeightNetUpdater(uomConversionBL, HUContextHolder.getCurrent(), productId, catchWeight);
		weightUpdater.updatePackToHUs(result);
	}

	private void updatePickAttributes(@NonNull final LUTUResult result, final @NonNull PickAttributes pickAttributes, final boolean serialNoPickingEnabled)
	{
		result.getLus().forEach(lu -> updateLUPickAttributes(lu, pickAttributes, serialNoPickingEnabled));
		result.getTopLevelTUs().forEach(tu -> updateTUPickAttributes(tu, pickAttributes, serialNoPickingEnabled));
	}

	private void updateLUPickAttributes(@NonNull final LU lu, @NonNull PickAttributes pickAttributes, final boolean serialNoPickingEnabled)
	{
		lu.getTus().forEach(tu -> updateTUPickAttributes(tu, pickAttributes, serialNoPickingEnabled));

		updateHUPickAttributes(lu.toHU(), pickAttributes, lu.isPreExistingLU(), serialNoPickingEnabled);
	}

	private void updateTUPickAttributes(@NonNull final TU tu, @NonNull final PickAttributes pickAttributes, final boolean serialNoPickingEnabled)
	{
		if (tu.isFullTU())
		{
			updateHUPickAttributes(tu.toHU(), pickAttributes, false, serialNoPickingEnabled);
		}
		else
		{
			for (final TUPart cu : tu.getCUsNotEmpty())
			{
				updateCUPickAttributes(cu, pickAttributes, serialNoPickingEnabled);
			}

			updateHUPickAttributes(tu.toHU(), pickAttributes, true, serialNoPickingEnabled);
		}
	}

	private void updateCUPickAttributes(@NonNull final TUPart cu, @NonNull final PickAttributes pickAttributes, final boolean serialNoPickingEnabled)
	{
		updateHUPickAttributes(cu.toHU(), pickAttributes, false, serialNoPickingEnabled);
	}

	private void updateHUPickAttributes(@NonNull final I_M_HU hu, @NonNull final PickAttributes pickAttributes, final boolean recomputeFromChildren, final boolean serialNoPickingEnabled)
	{
		// With recomputeFromChildren, lot and best-before date are always recomputed from the children (even when the pick
		// did not set them), so that the HU never keeps a value that does not match its content.
		if (!recomputeFromChildren
				&& !pickAttributes.isSetBestBeforeDate()
				&& !pickAttributes.isSetProductionDate()
				&& !pickAttributes.isSetLotNo()
				&& !serialNoPickingEnabled)
		{
			return;
		}

		final IAttributeStorage huAttributes = HUContextHolder.getCurrent().getHUAttributeStorageFactory().getAttributeStorage(hu);
		huAttributes.setSaveOnChange(true);

		applyPickOrRecompute(huAttributes, AttributeConstants.ATTR_BestBeforeDate, recomputeFromChildren, pickAttributes.isSetBestBeforeDate(),
				pickAttributes::getBestBeforeDate, () -> computeDateValueFromChildren(huAttributes, AttributeConstants.ATTR_BestBeforeDate));
		// ProductionDate is deliberately not part of applyPickOrRecompute: only lot and best-before are kept on a pick without a sent value
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
		applyPickOrRecompute(huAttributes, AttributeConstants.ATTR_LotNumber, recomputeFromChildren, pickAttributes.isSetLotNo(),
				pickAttributes::getLotNo, () -> computeLotNoFromChildren(huAttributes));
		// SerialNo: only when the product opts in (serialNoPickingEnabled) AND the HU storage actually supports the attribute.
		// A product flagged IsSerialNoPicked=Y whose picked HU lacks the SerialNo attribute is a config gap → no write, no error.
		if (serialNoPickingEnabled && huAttributes.hasAttribute(AttributeConstants.ATTR_SerialNo))
		{
			if (recomputeFromChildren)
			{
				huAttributes.setValueNoPropagate(AttributeConstants.ATTR_SerialNo, computeSerialNoFromChildren(huAttributes));
			}
			else
			{
				// Backend safety net: the serials are validated once, authoritatively, in PickingJobPickCommand
				// (one distinct serial per picked unit). Here we only guard against an empty set before writing.
				final SerialNoSet serialNos = pickAttributes.getSerialNos();
				if (serialNos.isEmpty())
				{
					throw new AdempiereException(PickAttributes.ERR_SerialNoRequired);
				}
				huAttributes.setValue(AttributeConstants.ATTR_SerialNo, SerialNoSet.toCommaSeparatedStringOrNull(serialNos));
			}
		}
	}

	/**
	 * With {@code recomputeFromChildren} the attribute is taken from the HU's children (only if the HU has the attribute);
	 * otherwise the value sent with the pick is written, only when the pick sent one.
	 */
	private static void applyPickOrRecompute(
			@NonNull final IAttributeStorage huAttributes,
			@NonNull final AttributeCode attributeCode,
			final boolean recomputeFromChildren,
			final boolean pickSetsValue,
			@NonNull final Supplier<Object> pickValue,
			@NonNull final Supplier<Object> valueFromChildren)
	{
		if (recomputeFromChildren)
		{
			if (huAttributes.hasAttribute(attributeCode))
			{
				huAttributes.setValueNoPropagate(attributeCode, valueFromChildren.get());
			}
		}
		else if (pickSetsValue)
		{
			huAttributes.setValue(attributeCode, pickValue.get());
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

	@Nullable
	private static String computeSerialNoFromChildren(final IAttributeStorage huAttributes)
	{
		// SerialNo is a MULTI-value attribute (one serial per unit, comma-separated): UNION all
		// children's serials — unlike LotNo/dates, the children do NOT agree on a single value.
		final LinkedHashSet<String> allSerials = new LinkedHashSet<>();
		for (final IAttributeStorage childAttributes : huAttributes.getChildAttributeStorages(true))
		{
			if (childAttributes.hasAttribute(AttributeConstants.ATTR_SerialNo))
			{
				SerialNoSet.ofNullableCommaSeparated(childAttributes.getValueAsString(AttributeConstants.ATTR_SerialNo))
						.forEach(serialNo -> allSerials.add(serialNo.getValueAsString()));
			}
		}

		return allSerials.isEmpty() ? null : String.join(",", allSerials);
	}
}
