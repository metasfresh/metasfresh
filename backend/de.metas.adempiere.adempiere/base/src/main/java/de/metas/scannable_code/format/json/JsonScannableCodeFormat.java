package de.metas.scannable_code.format.json;

import com.google.common.collect.ImmutableList;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatsCollection;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonScannableCodeFormat
{
	@NonNull String name;
	@NonNull ImmutableList<JsonScannableCodeFormatPart> parts;

	public static JsonScannableCodeFormat of(@NonNull final ScannableCodeFormat format)
	{
		return builder()
				.name(format.getName())
				.parts(format.getParts()
						.stream()
						.map(JsonScannableCodeFormatPart::of)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public static List<JsonScannableCodeFormat> ofCollection(@NonNull final ScannableCodeFormatsCollection collection)
	{
		return collection.isEmpty()
				? ImmutableList.of()
				: collection.toList().stream().map(JsonScannableCodeFormat::of).collect(ImmutableList.toImmutableList());
	}
}
