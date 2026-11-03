package de.metas.picking.rest_api.json;

import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonGetHUInfoByScannedCodeRequest
{
	@NonNull String scannedCode;
	@Nullable String productNo;

	/**
	 * Picking job + line the code was scanned for. When both are set, the scanned code is resolved to its HU the
	 * same way the pick resolves it, so non-{@code HUQRCode} labels (custom weight label, LMQ, GS1) are supported.
	 * When either is missing, only a plain HU QR code can be resolved (legacy behaviour).
	 */
	@Nullable WFProcessId wfProcessId;
	@Nullable PickingJobLineId lineId;
}
