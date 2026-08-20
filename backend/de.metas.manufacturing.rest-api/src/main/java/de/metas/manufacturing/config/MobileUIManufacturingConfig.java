package de.metas.manufacturing.config;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.config.mobileui.PickAttribute;
import de.metas.util.OptionalBoolean;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
	@NonNull OptionalBoolean isBestBeforeDateEditable;
	@NonNull OptionalBoolean isLotNumberEditable;
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
	 * @param isMainFinishedGood {@code false} for a co-/by-product line, which every flag below exempts from the configured simplification.
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
	 * @return the set of attributes that shall be editable on the manufacturing finished-goods receipt.
	 * An attribute is editable when its config flag resolves to {@code TRUE}.
	 * Reuses picking's {@link PickAttribute} so the mobile JSON contract is identical to picking's {@code readAttributes}.
	 */
	@NonNull
	public Set<PickAttribute> getEditableAttributes()
	{
		final ImmutableSet.Builder<PickAttribute> result = ImmutableSet.builder();
		if (isBestBeforeDateEditable.isTrue())
		{
			result.add(PickAttribute.BestBeforeDate);
		}
		if (isLotNumberEditable.isTrue())
		{
			result.add(PickAttribute.LotNo);
		}
		return result.build();
	}

	public MobileUIManufacturingConfig fallbackTo(@NonNull final MobileUIManufacturingConfig other)
	{
		final MobileUIManufacturingConfig result = MobileUIManufacturingConfig.builder()
				.isScanResourceRequired(this.isScanResourceRequired.ifUnknown(other.isScanResourceRequired))
				.isAllowIssuingAnyHU(this.isAllowIssuingAnyHU.ifUnknown(other.isAllowIssuingAnyHU))
				.receiveUnitType(this.receiveUnitType != null ? this.receiveUnitType : other.receiveUnitType)
				.isBestBeforeDateEditable(this.isBestBeforeDateEditable.ifUnknown(other.isBestBeforeDateEditable))
				.isLotNumberEditable(this.isLotNumberEditable.ifUnknown(other.isLotNumberEditable))
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