package de.metas.workflow.rest_api.activity_features.set_scanned_barcode;

import de.metas.workflow.rest_api.model.WFProcess;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetScannedBarcodeSuggestionsRequest
{
	@NonNull WFProcess wfProcess;
}
