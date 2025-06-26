package de.metas.scannable_code.format;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class ScannableCodeFormatsCollection
{
	public static ScannableCodeFormatsCollection EMPTY = new ScannableCodeFormatsCollection(ImmutableList.of());

	@NonNull ImmutableList<ScannableCodeFormat> formats;

	private ScannableCodeFormatsCollection(@NonNull final ImmutableList<ScannableCodeFormat> formats)
	{
		this.formats = formats;
	}

	public static Collector<ScannableCodeFormat, ?, ScannableCodeFormatsCollection> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(ScannableCodeFormatsCollection::ofList);
	}

	public static ScannableCodeFormatsCollection ofList(@Nullable final List<ScannableCodeFormat> formats)
	{
		return formats != null && !formats.isEmpty()
				? new ScannableCodeFormatsCollection(ImmutableList.copyOf(formats))
				: EMPTY;
	}

	public ExplainedOptional<ParsedScannedCode> parse(@NonNull final ScannedCode scannedCode)
	{
		if (formats.isEmpty())
		{
			return ExplainedOptional.emptyBecause("No formats configured");
		}

		final TranslatableStringBuilder notMatchingExplanation = TranslatableStrings.builder();
		for (final ScannableCodeFormat format : formats)
		{
			final ExplainedOptional<ParsedScannedCode> result = format.parse(scannedCode);
			if (result.isPresent())
			{
				return result;
			}

			if (!notMatchingExplanation.isEmpty())
			{
				notMatchingExplanation.append(" | ");
			}
			notMatchingExplanation.append(format.getName()).append(" ").append(result.getExplanation());
		}

		return ExplainedOptional.emptyBecause(notMatchingExplanation.build());
	}

	public boolean isEmpty() {return formats.isEmpty();}

	public ImmutableList<ScannableCodeFormat> toList() {return formats;}
}
