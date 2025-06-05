package de.metas.hu_consolidation.mobile.rest_api.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.util.Check;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class JsonConsolidateRequest
{
	@Nullable WFProcessId wfProcessId;
	@NonNull String fromPickingSlotQRCode;

	public WFProcessId getWfProcessIdNotNull()
	{
		return Check.assumeNotNull(wfProcessId, "wfProcessId not null");
	}

	public JsonConsolidateRequest withWFProcessId(@NonNull final String wfProcessIdStr)
	{
		final WFProcessId wfProcessIdNew = WFProcessId.ofString(wfProcessIdStr);
		if (wfProcessId != null && !WFProcessId.equals(wfProcessId, wfProcessIdNew))
		{
			throw new AdempiereException("Cannot change WFProcessId from " + wfProcessId + " to " + wfProcessIdNew + " when it was already set");
		}

		return toBuilder().wfProcessId(wfProcessIdNew).build();
	}

	@JsonIgnore
	public PickingSlotId getFromPickingSlotId()
	{
		return PickingSlotQRCode.ofGlobalQRCodeJsonString(fromPickingSlotQRCode).getPickingSlotId();
	}

}
