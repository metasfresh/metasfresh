package de.metas.handlingunits.picking.config.mobileui;

import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.util.OptionalBoolean;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
@EqualsAndHashCode(doNotUseGetters = true)
public class PickingJobOptions
{
	@Nullable PickingJobAggregationType aggregationType;
	boolean isAllowPickingAnyHU;
	boolean isAlwaysSplitHUsEnabled;
	boolean isShipOnCloseLU;
	@NonNull AllowedPickToStructures allowedPickToStructures;
	@NonNull PickAttributesConfig pickAttributes;
	boolean isCatchWeightTUPickingEnabled;
	boolean considerSalesOrderCapacity;
	boolean isAllowSkippingRejectedReason;
	boolean isShowConfirmationPromptWhenOverPick;
	boolean isAllowCompletingPartialPickingJob;
	boolean isShowLastPickedBestBeforeDateForLines;
	boolean isAnonymousPickHUsOnTheFly;
	@NonNull OptionalBoolean displayPickingSlotSuggestions;
	@NonNull CreateShipmentPolicy createShipmentPolicy;
	@NonNull OptionalBoolean completeJobAutomatically;
	@Nullable PickingLineGroupBy pickingLineGroupBy;
	@Nullable PickingLineSortBy pickingLineSortBy;
	/** null = not set (customer: use the profile's) */
	@Getter(AccessLevel.NONE) @Nullable CUQtyEntryMode cuQtyEntryMode;
	/** null = not set (customer: use the profile's) */
	@Nullable ScannableCodeFormatId cuLabelFormatId;

	@Builder(toBuilder = true)
	private PickingJobOptions(
			@Nullable final PickingJobAggregationType aggregationType,
			final boolean isAllowPickingAnyHU,
			final boolean isAlwaysSplitHUsEnabled,
			final boolean isShipOnCloseLU,
			@NonNull final AllowedPickToStructures allowedPickToStructures,
			@Nullable final PickAttributesConfig pickAttributes,
			final boolean isCatchWeightTUPickingEnabled,
			final boolean considerSalesOrderCapacity,
			final boolean isAllowSkippingRejectedReason,
			final boolean isShowConfirmationPromptWhenOverPick,
			final boolean isAllowCompletingPartialPickingJob,
			final boolean isShowLastPickedBestBeforeDateForLines,
			final boolean isAnonymousPickHUsOnTheFly,
			@Nullable final OptionalBoolean displayPickingSlotSuggestions,
			@NonNull final CreateShipmentPolicy createShipmentPolicy,
			@Nullable final OptionalBoolean completeJobAutomatically,
			@Nullable final PickingLineGroupBy pickingLineGroupBy,
			@Nullable final PickingLineSortBy pickingLineSortBy,
			@Nullable final CUQtyEntryMode cuQtyEntryMode,
			@Nullable final ScannableCodeFormatId cuLabelFormatId)
	{
		this.aggregationType = aggregationType;
		this.isAllowPickingAnyHU = isAllowPickingAnyHU;
		this.isAlwaysSplitHUsEnabled = isAlwaysSplitHUsEnabled;
		this.isShipOnCloseLU = isShipOnCloseLU;
		this.allowedPickToStructures = allowedPickToStructures;
		this.pickAttributes = pickAttributes != null ? pickAttributes : PickAttributesConfig.UNKNOWN;
		this.isCatchWeightTUPickingEnabled = isCatchWeightTUPickingEnabled;
		this.considerSalesOrderCapacity = considerSalesOrderCapacity;
		this.isAllowSkippingRejectedReason = isAllowSkippingRejectedReason;
		this.isShowConfirmationPromptWhenOverPick = isShowConfirmationPromptWhenOverPick;
		this.isAllowCompletingPartialPickingJob = isAllowCompletingPartialPickingJob;
		this.isShowLastPickedBestBeforeDateForLines = isShowLastPickedBestBeforeDateForLines;
		this.isAnonymousPickHUsOnTheFly = isAnonymousPickHUsOnTheFly;
		this.displayPickingSlotSuggestions = displayPickingSlotSuggestions != null ? displayPickingSlotSuggestions : OptionalBoolean.FALSE;
		this.createShipmentPolicy = createShipmentPolicy;
		this.completeJobAutomatically = completeJobAutomatically != null ? completeJobAutomatically : OptionalBoolean.UNKNOWN;
		this.pickingLineGroupBy = pickingLineGroupBy;
		this.pickingLineSortBy = pickingLineSortBy;
		this.cuQtyEntryMode = cuQtyEntryMode;
		this.cuLabelFormatId = cuLabelFormatId;
	}

	public Optional<PickingLineGroupBy> getPickingLineGroupBy() {return Optional.ofNullable(pickingLineGroupBy);}

	public Optional<PickingLineSortBy> getPickingLineSortBy() {return Optional.ofNullable(pickingLineSortBy);}

	@NonNull
	public CUQtyEntryMode getCUQtyEntryModeOrDefault() {return CoalesceUtil.coalesceNotNull(cuQtyEntryMode, CUQtyEntryMode.DEFAULT);}

	@NonNull
	public CUQtyEntryPolicy getCUQtyEntryPolicy() {return getCUQtyEntryModeOrDefault().getPolicy();}

	public Optional<CUQtyEntryMode> getCUQtyEntryMode() {return Optional.ofNullable(cuQtyEntryMode);}

	public Optional<ScannableCodeFormatId> getCULabelFormatId() {return Optional.ofNullable(cuLabelFormatId);}

	public boolean isStrictlyAllowed(@NonNull final PickToStructure pickToStructure) {return allowedPickToStructures.isStrictlyAllowed(pickToStructure);}

	public PickingJobOptions fallbackTo(@NonNull final PickingJobOptions fallback)
	{
		final PickingJobOptions newValue = toBuilder()
				.allowedPickToStructures(this.allowedPickToStructures.fallbackTo(fallback.allowedPickToStructures))
				.pickAttributes(this.pickAttributes.fallbackTo(fallback.pickAttributes))
				.displayPickingSlotSuggestions(this.displayPickingSlotSuggestions.ifUnknown(fallback.getDisplayPickingSlotSuggestions()))
				.completeJobAutomatically(this.completeJobAutomatically.ifUnknown(fallback.getCompleteJobAutomatically()))
				.cuQtyEntryMode(CoalesceUtil.coalesce(this.cuQtyEntryMode, fallback.cuQtyEntryMode))
				.cuLabelFormatId(CoalesceUtil.coalesce(this.cuLabelFormatId, fallback.cuLabelFormatId))
				.build();

		return Objects.equals(this, newValue) ? this : newValue;
	}
}
