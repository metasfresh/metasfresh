package de.metas.scannable_code.format.json;

import com.google.common.collect.ImmutableList;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.scannable_code.format.ScannableCodeFormatsCollection;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

/**
 * The format as the mobile app gets it, including its pack ID zone ({@link ScannableCodeFormat#getPackIdZone()}).
 */
@Value
@Builder
@Jacksonized
public class JsonScannableCodeFormat
{
	@NonNull ScannableCodeFormatId id;
	@NonNull String name;
	@NonNull ImmutableList<JsonScannableCodeFormatPart> parts;
	/** Start position of the pack ID zone (see {@link ScannableCodeFormat#getPackIdZone()}); null = the format has none. */
	@Nullable Integer packIdZoneStart;
	/** End position of the pack ID zone: a code shorter than this ends before the pack ID. Null = the format has no zone. */
	@Nullable Integer packIdZoneEnd;

	public static JsonScannableCodeFormat of(@NonNull final ScannableCodeFormat format)
	{
		final ScannableCodeFormat.PackIdZone packIdZone = format.getPackIdZone().orElse(null);
		return builder()
				.id(format.getId())
				.name(format.getName())
				.parts(format.getParts()
						.stream()
						.map(JsonScannableCodeFormatPart::of)
						.collect(ImmutableList.toImmutableList()))
				.packIdZoneStart(packIdZone != null ? packIdZone.getStart() : null)
				.packIdZoneEnd(packIdZone != null ? packIdZone.getEnd() : null)
				.build();
	}

	public static List<JsonScannableCodeFormat> ofCollection(@NonNull final ScannableCodeFormatsCollection collection)
	{
		return collection.isEmpty()
				? ImmutableList.of()
				: collection.toList().stream().map(JsonScannableCodeFormat::of).collect(ImmutableList.toImmutableList());
	}
}
