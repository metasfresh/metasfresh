package de.metas.scannable_code.format.json;

import de.metas.scannable_code.format.ScannableCodeFormatPart;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.util.time.PatternedDateTimeFormatter;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonScannableCodeFormatPart
{
	int startPosition;
	int endPosition;

	@NonNull ScannableCodeFormatPartType type;
	@Nullable String dateFormat;
	@Nullable String constantValue;

	public static JsonScannableCodeFormatPart of(@NonNull final ScannableCodeFormatPart part)
	{
		return builder()
				.startPosition(part.getStartPosition())
				.endPosition(part.getEndPosition())
				.type(part.getType())
				.dateFormat(PatternedDateTimeFormatter.toPattern(part.getDateFormat()))
				.constantValue(part.getConstantValue())
				.build();
	}
}
