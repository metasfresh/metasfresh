package de.metas.handlingunits.pporder.api.issue_schedule;

import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class PPOrderIssueScheduleProcessRequest
{
	@NonNull PPOrderId ppOrderId;
	@NonNull PPOrderIssueScheduleId issueScheduleId;
	@NonNull BigDecimal qtyIssued;
	@Nullable BigDecimal qtyRejected;
	@Nullable QtyRejectedReasonCode qtyRejectedReasonCode;
}
