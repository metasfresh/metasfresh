package de.metas.workflow.rest_api.activity_features.set_scanned_barcode;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonScannedBarcodeSuggestion
{
	@NonNull String caption;
	@NonNull String qrCode;
	@Nullable String detail;

	@Nullable String property1;
	@Nullable String value1;

	@Nullable String property2;
	@Nullable String value2;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@NonNull @Singular Map<String, Object> additionalProperties;
}
