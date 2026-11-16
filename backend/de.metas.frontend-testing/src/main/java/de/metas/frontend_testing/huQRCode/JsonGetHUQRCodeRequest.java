package de.metas.frontend_testing.huQRCode;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.JsonCreateMasterdataResponse;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonGetHUQRCodeRequest
{
	@NonNull Identifier identifier;
	@Nullable JsonCreateMasterdataResponse masterdata;
	@Nullable Map<String, Object> context;
}
