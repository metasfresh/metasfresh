package de.metas.picking.rest_api.json;

import com.google.common.collect.ImmutableMap;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobLineQtyAvailable;
import de.metas.handlingunits.picking.job.model.PickingJobQtyAvailable;
import de.metas.handlingunits.picking.job.model.QtyAvailableStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonPickingJobQtyAvailable
{
	@Nullable QtyAvailableStatus status;
	@NonNull Map<PickingJobLineId, Line> lines;

	public static JsonPickingJobQtyAvailable of(PickingJobQtyAvailable from)
	{
		return builder()
				.status(from.getStatus())
				.lines(from.getLines().stream()
						.map(Line::of)
						.collect(ImmutableMap.toImmutableMap(Line::getLineId, line -> line)))
				.build();
	}

	//
	//
	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Line
	{
		@NonNull PickingJobLineId lineId;
		@Nullable QtyAvailableStatus status;
		@NonNull String uom;
		@NonNull BigDecimal qtyRemainingToPick;
		@Nullable BigDecimal qtyAvailableToPick;

		public static Line of(PickingJobLineQtyAvailable from)
		{
			return builder()
					.lineId(from.getLineId())
					.status(from.getStatus())
					.uom(from.getUomSymbol())
					.qtyRemainingToPick(from.getQtyRemainingToPick().toBigDecimal())
					.qtyAvailableToPick(from.getQtyAvailableToPick() != null ? from.getQtyAvailableToPick().toBigDecimal() : null)
					.build();
		}
	}
}
