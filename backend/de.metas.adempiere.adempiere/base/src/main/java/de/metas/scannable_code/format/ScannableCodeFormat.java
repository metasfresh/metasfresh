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

@Value
public class ScannableCodeFormat
{
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

}
