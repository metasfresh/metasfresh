package de.metas.inventory.mobileui.job.qrcode;

import de.metas.inventory.InventoryLineId;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;

@Value
@Builder
public class ResolveHURequest
{
	@NonNull ScannedCode scannedCode;

	//
	// Context:
	@NonNull UserId callerId;
	@NonNull WFProcessId wfProcessId;
	@Nullable InventoryLineId lineId;
	@NonNull LocatorId locatorId;
}
