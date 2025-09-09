package de.metas.handlingunits.picking.config.mobileui;

import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.util.OptionalBoolean;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
	boolean isCatchWeightTUPickingEnabled;
	boolean considerSalesOrderCapacity;
	boolean isAllowSkippingRejectedReason;
	boolean isShowConfirmationPromptWhenOverPick;
	boolean isAllowCompletingPartialPickingJob;
	boolean isShowLastPickedBestBeforeDateForLines;
	boolean isAnonymousPickHUsOnTheFly;
	@NonNull OptionalBoolean displayPickingSlotSuggestions;
	@NonNull CreateShipmentPolicy createShipmentPolicy;
	@Nullable PickingLineGroupBy pickingLineGroupBy;
	@Nullable PickingLineSortBy pickingLineSortBy;

	@Builder(toBuilder = true)
	private PickingJobOptions(
			@Nullable final PickingJobAggregationType aggregationType,
			final boolean isAllowPickingAnyHU,
			final boolean isAlwaysSplitHUsEnabled,
			final boolean isShipOnCloseLU,
			@NonNull AllowedPickToStructures allowedPickToStructures,
			final boolean isCatchWeightTUPickingEnabled,
			final boolean considerSalesOrderCapacity,
			final boolean isAllowSkippingRejectedReason,
			final boolean isShowConfirmationPromptWhenOverPick,
			final boolean isAllowCompletingPartialPickingJob,
			final boolean isShowLastPickedBestBeforeDateForLines,
			final boolean isAnonymousPickHUsOnTheFly,
			@Nullable final OptionalBoolean displayPickingSlotSuggestions,
			@NonNull final CreateShipmentPolicy createShipmentPolicy,
			@Nullable final PickingLineGroupBy pickingLineGroupBy,
			@Nullable final PickingLineSortBy pickingLineSortBy)
	{
		this.aggregationType = aggregationType;
		this.isAllowPickingAnyHU = isAllowPickingAnyHU;
		this.isAlwaysSplitHUsEnabled = isAlwaysSplitHUsEnabled;
		this.isShipOnCloseLU = isShipOnCloseLU;
		this.allowedPickToStructures = allowedPickToStructures;
		this.isCatchWeightTUPickingEnabled = isCatchWeightTUPickingEnabled;
		this.considerSalesOrderCapacity = considerSalesOrderCapacity;
		this.isAllowSkippingRejectedReason = isAllowSkippingRejectedReason;
		this.isShowConfirmationPromptWhenOverPick = isShowConfirmationPromptWhenOverPick;
		this.isAllowCompletingPartialPickingJob = isAllowCompletingPartialPickingJob;
		this.isShowLastPickedBestBeforeDateForLines = isShowLastPickedBestBeforeDateForLines;
		this.isAnonymousPickHUsOnTheFly = isAnonymousPickHUsOnTheFly;
		this.displayPickingSlotSuggestions = displayPickingSlotSuggestions != null ? displayPickingSlotSuggestions : OptionalBoolean.FALSE;
		this.createShipmentPolicy = createShipmentPolicy;
		this.pickingLineGroupBy = pickingLineGroupBy;
		this.pickingLineSortBy = pickingLineSortBy;
	}

	public Optional<PickingLineGroupBy> getPickingLineGroupBy() {return Optional.ofNullable(pickingLineGroupBy);}

	public Optional<PickingLineSortBy> getPickingLineSortBy() {return Optional.ofNullable(pickingLineSortBy);}

	public PickingJobOptions fallbackTo(@NonNull final PickingJobOptions fallback)
	{
		final PickingJobOptions newValue = toBuilder()
				.allowedPickToStructures(this.allowedPickToStructures.fallbackTo(fallback.allowedPickToStructures))
				.displayPickingSlotSuggestions(this.displayPickingSlotSuggestions.ifUnknown(fallback.getDisplayPickingSlotSuggestions()))
				.build();

		return Objects.equals(this, newValue) ? this : newValue;
	}
}
