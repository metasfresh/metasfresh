package de.metas.scannable_code.format;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ExplainedOptional;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ParsedScannedCode.ParsedScannedCodeBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Optional;

@Value
public class ScannableCodeFormat
{
	/** The constant marker in front of the pack ID; the zone falls back to it when the format has no {@link ScannableCodeFormatPartType#PackId} part. */
	private static final String PACK_ID_MARKER = "92";

	@NonNull ScannableCodeFormatId id;
	@NonNull String name;
	@Nullable String description;
	@NonNull ImmutableList<ScannableCodeFormatPart> parts;

	@Builder
	private ScannableCodeFormat(
			@NonNull final ScannableCodeFormatId id,
			@NonNull final String name,
			@Nullable final String description,
			@NonNull final ImmutableList<ScannableCodeFormatPart> parts)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.parts = parts;
	}

	public ExplainedOptional<ParsedScannedCode> parse(@NonNull final ScannedCode scannedCode)
	{
		if (parts.isEmpty())
		{
			return ExplainedOptional.emptyBecause("No parts configured");
		}

		try
		{
			final ParsedScannedCodeBuilder result = ParsedScannedCode.builder()
					.scannedCode(scannedCode);
			
			for (final ScannableCodeFormatPart part : parts)
			{
				final BooleanWithReason ok = part.parseAndUpdateResult(scannedCode, result);
				if (ok.isFalse())
				{
					return ExplainedOptional.emptyBecause(ok.getReason());
				}
			}
			return ExplainedOptional.of(result.build());
		}
		catch (final Exception ex)
		{
			return ExplainedOptional.emptyBecause(ex.getLocalizedMessage());
		}
	}

	/**
	 * The pack ID zone: the characters in front of (and including) the pack ID value, i.e. the constant marker plus the pack ID part itself.
	 * It starts at the constant marker directly in front of the {@link ScannableCodeFormatPartType#PackId} part (or at that part itself).
	 * A format without a pack ID part falls back to the constant "92" marker: the zone is the marker, cut short by a part starting inside it.
	 * A format with neither has no pack ID zone.
	 * <p>
	 * Positions are 1-based like {@link ScannableCodeFormatPart#getStartPosition()}; {@link PackIdZone#getEnd()} is the start position of the part after the zone.
	 */
	public Optional<PackIdZone> getPackIdZone()
	{
		final ScannableCodeFormatPart packIdPart = parts.stream()
				.filter(part -> part.getType() == ScannableCodeFormatPartType.PackId)
				.min(Comparator.comparingInt(ScannableCodeFormatPart::getStartPosition))
				.orElse(null);
		return packIdPart != null
				? Optional.of(zoneBeforePackIdPart(packIdPart))
				: zoneOfFallbackMarker();
	}

	/**
	 * @return true if the code is too short to hold the pack ID zone while every part in front of the zone reads: the code was scanned right, it just
	 * has no pack ID. A format with no pack ID zone: false.
	 */
	public boolean isEndingBeforePackId(@NonNull final ScannedCode scannedCode)
	{
		return getPackIdZone()
				.filter(zone -> scannedCode.length() < zone.getEnd())
				.filter(zone -> isPartsBeforeReadable(scannedCode, zone.getStart()))
				.isPresent();
	}

	private PackIdZone zoneBeforePackIdPart(@NonNull final ScannableCodeFormatPart packIdPart)
	{
		final int packIdStart = packIdPart.getStartPosition();
		final int zoneStart = parts.stream()
				.filter(part -> part.getStartPosition() < packIdStart)
				.max(Comparator.comparingInt(ScannableCodeFormatPart::getStartPosition))
				.filter(part -> part.getType() == ScannableCodeFormatPartType.Constant)
				.map(ScannableCodeFormatPart::getStartPosition)
				.orElse(packIdStart);
		return new PackIdZone(zoneStart, packIdStart);
	}

	private Optional<PackIdZone> zoneOfFallbackMarker()
	{
		return parts.stream()
				.filter(part -> part.getType() == ScannableCodeFormatPartType.Constant && PACK_ID_MARKER.equals(part.getConstantValue()))
				.min(Comparator.comparingInt(ScannableCodeFormatPart::getStartPosition))
				.map(marker -> {
					final int zoneStart = marker.getStartPosition();
					final int markerEnd = zoneStart + PACK_ID_MARKER.length();
					final int zoneEnd = Math.min(markerEnd, parts.stream()
							.mapToInt(ScannableCodeFormatPart::getStartPosition)
							.filter(start -> start > zoneStart)
							.min()
							.orElse(markerEnd));
					return new PackIdZone(zoneStart, zoneEnd);
				});
	}

	private boolean isPartsBeforeReadable(@NonNull final ScannedCode scannedCode, final int zoneStart)
	{
		final ImmutableList<ScannableCodeFormatPart> partsBefore = parts.stream()
				.filter(part -> part.getStartPosition() < zoneStart)
				.filter(part -> part.getType() != ScannableCodeFormatPartType.Ignored) // separators: the scanned code is trimmed, a trailing one is lost
				.collect(ImmutableList.toImmutableList());
		if (partsBefore.isEmpty())
		{
			return false;
		}

		return ScannableCodeFormat.builder()
				.id(id)
				.name(name)
				.parts(partsBefore)
				.build()
				.parse(scannedCode)
				.isPresent();
	}

	@Value
	public static class PackIdZone
	{
		/** 1-based start position of the zone */
		int start;
		/** 1-based start position of the part after the zone: a code shorter than this ends before the pack ID */
		int end;
	}
}
