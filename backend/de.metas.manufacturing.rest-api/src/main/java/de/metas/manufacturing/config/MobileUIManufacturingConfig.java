package de.metas.manufacturing.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.config.mobileui.PickAttribute;
import de.metas.util.OptionalBoolean;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

@Value
@Builder(toBuilder = true)
public class MobileUIManufacturingConfig
{
	@NonNull OptionalBoolean isScanResourceRequired;
	@NonNull OptionalBoolean isAllowIssuingAnyHU;
	@Nullable ReceiveUnitType receiveUnitType;

	/**
	 * The editable-attribute list (global-only, v1 - {@code MobileUI_MFG_Config_Attribute} active rows in
	 * {@code SeqNo} order), replacing the retired per-attribute boolean flags. Empty when no config row exists
	 * or none of its child rows are active.
	 */
	@NonNull ImmutableList<AttributeCode> editableAttributeCodesInOrder;

	@NonNull OptionalBoolean isAllowFinishedGoodsReceiveToLU;
	@NonNull OptionalBoolean isAllowFinishedGoodsReceiveToTU;
	@NonNull OptionalBoolean isSkipFinishedGoodsReceiveTargetStep;
	@NonNull OptionalBoolean isCaptureCatchWeightAtReceipt;

	@NonNull
	public ReceiveUnitType getReceiveUnitTypeEffective()
	{
		return receiveUnitType != null ? receiveUnitType : ReceiveUnitType.CU;
	}

	public boolean getIsAllowFinishedGoodsReceiveToLUEffective()
	{
		return isAllowFinishedGoodsReceiveToLU.orElseTrue();
	}

	public boolean getIsAllowFinishedGoodsReceiveToTUEffective()
	{
		return isAllowFinishedGoodsReceiveToTU.orElseTrue();
	}

	public boolean getIsSkipFinishedGoodsReceiveTargetStepEffective()
	{
		return isSkipFinishedGoodsReceiveTargetStep.orElseFalse();
	}

	public boolean getIsCaptureCatchWeightAtReceiptEffective()
	{
		return isCaptureCatchWeightAtReceipt.orElseTrue();
	}

	/**
	 * @param isMainFinishedGood {@code false} for a co-/by-product line. THREE of the four flags exempt such a line
	 *                           from the configured simplification; catch weight does NOT — it applies to every line.
	 */
	@NonNull
	public FinishedGoodsReceiveLineConfig effectiveForReceiveLine(final boolean isMainFinishedGood)
	{
		// Three of the four flags exempt co-/by-products, and the polarity differs because "exempt" means the
		// opposite thing per flag: for the two allow-flags the co-/by-product must stay PERMITTED (it is
		// legitimately received into a TU, including an infinite-capacity one), whereas for the skip-flag it must
		// keep its target chooser, i.e. NOT skip. That exemption is a deliberately conservative default - those
		// co-/by-product paths keep exactly today's behaviour.
		//
		// Catch weight is deliberately NOT exempt: it applies to every line. Nothing is lost by not weighing at
		// receipt, because the weight of a catch-weight product is captured later at picking
		// (PickingJobPickCommand takes it from the operator), which is the whole point of switching it off here.
		// A product-data fallback would be meaningless: a nominal weight is exactly what a catch-weight product
		// declares untrustworthy - for anything else the kg/piece UOM conversion already answers it.
		return FinishedGoodsReceiveLineConfig.builder()
				.allowReceiveToLU(!isMainFinishedGood || getIsAllowFinishedGoodsReceiveToLUEffective())
				.allowReceiveToTU(!isMainFinishedGood || getIsAllowFinishedGoodsReceiveToTUEffective())
				.captureCatchWeight(getIsCaptureCatchWeightAtReceiptEffective())
				.skipReceiveTargetStep(isMainFinishedGood && getIsSkipFinishedGoodsReceiveTargetStepEffective())
				.build();
	}

	/**
	 * @return the legacy {@link PickAttribute} view of {@link #editableAttributeCodesInOrder}, derived from the
	 * two well-known special attributes (Lot number, Best-before date) only.
	 * @deprecated temporary shim for {@code MaterialReceiptActivityHandler}'s "readAttributes" JSON emit, kept
	 * ONLY until that call site is migrated to the generic {@link #editableAttributeCodesInOrder} contract
	 * (issue #31771 Task 6). Any other configured attribute is NOT represented here.
	 */
	@Deprecated
	@NonNull
	public Set<PickAttribute> getEditableAttributes()
	{
		final ImmutableSet.Builder<PickAttribute> result = ImmutableSet.builder();
		for (final AttributeCode code : editableAttributeCodesInOrder)
		{
			if (AttributeConstants.ATTR_LotNumber.equals(code))
			{
				result.add(PickAttribute.LotNo);
			}
			else if (AttributeConstants.ATTR_BestBeforeDate.equals(code))
			{
				result.add(PickAttribute.BestBeforeDate);
			}
		}
		return result.build();
	}

	public MobileUIManufacturingConfig fallbackTo(@NonNull final MobileUIManufacturingConfig other)
	{
		final MobileUIManufacturingConfig result = MobileUIManufacturingConfig.builder()
				.isScanResourceRequired(this.isScanResourceRequired.ifUnknown(other.isScanResourceRequired))
				.isAllowIssuingAnyHU(this.isAllowIssuingAnyHU.ifUnknown(other.isAllowIssuingAnyHU))
				.receiveUnitType(this.receiveUnitType != null ? this.receiveUnitType : other.receiveUnitType)
				.editableAttributeCodesInOrder(!this.editableAttributeCodesInOrder.isEmpty() ? this.editableAttributeCodesInOrder : other.editableAttributeCodesInOrder)
				.isAllowFinishedGoodsReceiveToLU(this.isAllowFinishedGoodsReceiveToLU.ifUnknown(other.isAllowFinishedGoodsReceiveToLU))
				.isAllowFinishedGoodsReceiveToTU(this.isAllowFinishedGoodsReceiveToTU.ifUnknown(other.isAllowFinishedGoodsReceiveToTU))
				.isSkipFinishedGoodsReceiveTargetStep(this.isSkipFinishedGoodsReceiveTargetStep.ifUnknown(other.isSkipFinishedGoodsReceiveTargetStep))
				.isCaptureCatchWeightAtReceipt(this.isCaptureCatchWeightAtReceipt.ifUnknown(other.isCaptureCatchWeightAtReceipt))
				.build();
		if (result.equals(this))
		{
			return this;
		}
		else if (result.equals(other))
		{
			return other;
		}
		else
		{
			return result;
		}
	}

	public static Optional<MobileUIManufacturingConfig> merge(@Nullable final MobileUIManufacturingConfig... configs)
	{
		if (configs == null || configs.length <= 0)
		{
			return Optional.empty();
		}

		MobileUIManufacturingConfig result = null;
		for (final MobileUIManufacturingConfig config : configs)
		{
			if (config == null)
			{
				continue;
			}

			result = result != null ? result.fallbackTo(config) : config;
		}

		return Optional.ofNullable(result);
	}

}