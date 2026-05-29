package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class PickingJobQtyAvailable
{
	@NonNull ImmutableList<PickingJobLineQtyAvailable> lines;
	@Nullable QtyAvailableStatus status;

	@Builder
	private PickingJobQtyAvailable(
			@Nullable @Singular final List<PickingJobLineQtyAvailable> lines)
	{
		this.lines = lines != null && !lines.isEmpty() ? ImmutableList.copyOf(lines) : ImmutableList.of();
		this.status = QtyAvailableStatus.computeOfLines(this.lines, PickingJobLineQtyAvailable::getStatus).orElse(null);
	}
}

