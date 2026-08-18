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
	@NonNull OptionalBoolean isCaptureFinishedGoodsCatchWeightAtReceipt;

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

	public boolean getIsCaptureFinishedGoodsCatchWeightAtReceiptEffective()
	{
		return isCaptureFinishedGoodsCatchWeightAtReceipt.orElseTrue();
	}

	/**
	 * @param isMainFinishedGood {@code false} for a co-/by-product line, which every flag below exempts from the configured simplification.
	 */
	@NonNull
	public FinishedGoodsReceiveLineConfig effectiveForReceiveLine(final boolean isMainFinishedGood)
	{
		// The polarity differs because "exempt" means the opposite thing per flag: for the three allow-flags the
		// co-/by-product must stay PERMITTED (it is legitimately received into a TU - including an infinite-capacity
		// one, where its catch weight IS the quantity), whereas for the skip-flag it must keep its target chooser,
		// i.e. NOT skip.
		return FinishedGoodsReceiveLineConfig.builder()
				.allowReceiveToLU(!isMainFinishedGood || getIsAllowFinishedGoodsReceiveToLUEffective())
				.allowReceiveToTU(!isMainFinishedGood || getIsAllowFinishedGoodsReceiveToTUEffective())
				.captureCatchWeight(!isMainFinishedGood || getIsCaptureFinishedGoodsCatchWeightAtReceiptEffective())
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
				.isCaptureFinishedGoodsCatchWeightAtReceipt(this.isCaptureFinishedGoodsCatchWeightAtReceipt.ifUnknown(other.isCaptureFinishedGoodsCatchWeightAtReceipt))
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