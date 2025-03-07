package de.metas.handlingunits.picking.config.mobileui;

import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@EqualsAndHashCode(doNotUseGetters = true)
public class PickingJobOptions
{
	@Nullable PickingJobAggregationType aggregationType;
	boolean isAllowPickingAnyHU;
	boolean isAlwaysSplitHUsEnabled;
	boolean isPickWithNewLU;
	boolean isAllowNewTU;
	boolean isCatchWeightTUPickingEnabled;
	boolean considerSalesOrderCapacity;
	boolean isAllowSkippingRejectedReason;
	boolean isShowConfirmationPromptWhenOverPick;
	@NonNull CreateShipmentPolicy createShipmentPolicy;
	@Nullable PickingLineGroupBy pickingLineGroupBy;
	@Nullable PickingLineSortBy pickingLineSortBy;

	@Builder(toBuilder = true)
	private PickingJobOptions(
			@Nullable final PickingJobAggregationType aggregationType,
			final boolean isAllowPickingAnyHU,
			final boolean isAlwaysSplitHUsEnabled,
			final boolean isPickWithNewLU,
			final boolean isAllowNewTU,
			final boolean isCatchWeightTUPickingEnabled,
			final boolean considerSalesOrderCapacity,
			final boolean isAllowSkippingRejectedReason,
			final boolean isShowConfirmationPromptWhenOverPick,
			@NonNull final CreateShipmentPolicy createShipmentPolicy,
			@Nullable final PickingLineGroupBy pickingLineGroupBy,
			@Nullable final PickingLineSortBy pickingLineSortBy)
	{
		this.aggregationType = aggregationType;
		this.isAllowPickingAnyHU = isAllowPickingAnyHU;
		this.isAlwaysSplitHUsEnabled = isAlwaysSplitHUsEnabled;
		this.isPickWithNewLU = isPickWithNewLU;
		this.isAllowNewTU = isAllowNewTU;
		this.isCatchWeightTUPickingEnabled = isCatchWeightTUPickingEnabled;
		this.considerSalesOrderCapacity = considerSalesOrderCapacity;
		this.isAllowSkippingRejectedReason = isAllowSkippingRejectedReason;
		this.isShowConfirmationPromptWhenOverPick = isShowConfirmationPromptWhenOverPick;
		this.createShipmentPolicy = createShipmentPolicy;
		this.pickingLineGroupBy = pickingLineGroupBy;
		this.pickingLineSortBy = pickingLineSortBy;
	}

	public Optional<PickingLineGroupBy> getPickingLineGroupBy() {return Optional.ofNullable(pickingLineGroupBy);}

	public Optional<PickingLineSortBy> getPickingLineSortBy() {return Optional.ofNullable(pickingLineSortBy);}
}
