package de.metas.frontend_testing.masterdata.custom_qrcode_format;

import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonCustomQRCodeFormatRequest
{
	@NonNull String name;
	@NonNull List<Part> parts;

	//
	//
	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Part
	{
		int startPosition;
		int endPosition;
		@NonNull ScannableCodeFormatPartType type;
		@Nullable String dateFormat;
	}
}
