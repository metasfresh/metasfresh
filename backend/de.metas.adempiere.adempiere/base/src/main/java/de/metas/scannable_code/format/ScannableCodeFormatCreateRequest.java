package de.metas.scannable_code.format;

import com.google.common.collect.ImmutableList;
import de.metas.util.time.PatternedDateTimeFormatter;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class ScannableCodeFormatCreateRequest
{
	@NonNull String name;
	@Nullable String description;
	@NonNull ImmutableList<Part> parts;

	//
	//
	//
	//
	//

	@Value
	@Builder
	public static class Part
	{
		int startPosition;
		int endPosition;
		@NonNull ScannableCodeFormatPartType type;
		@Nullable PatternedDateTimeFormatter dateFormat;
		@Nullable String description;
	}
}
