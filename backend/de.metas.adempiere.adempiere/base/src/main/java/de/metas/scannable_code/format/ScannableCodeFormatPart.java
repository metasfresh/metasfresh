package de.metas.scannable_code.format;

import de.metas.i18n.BooleanWithReason;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ParsedScannedCode.ParsedScannedCodeBuilder;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;

@Value
public class ScannableCodeFormatPart
{
	int startPosition;
	int endPosition;

	@NonNull ScannableCodeFormatPartType type;

	@Nullable DateTimeFormatter dateFormat;
	@NonNull private static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyMMdd");

	@Nullable String description;

	@Builder
	private ScannableCodeFormatPart(
			final int startPosition,
			final int endPosition,
			@NonNull final ScannableCodeFormatPartType type,
			@Nullable final DateTimeFormatter dateFormat,
			@Nullable final String description)
	{
		Check.assume(startPosition >= 1, "startPosition >= 1");
		Check.assume(startPosition <= endPosition, "startPosition <= endPosition");

		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.type = type;
		this.description = description;

		switch (type.getDataType())
		{
			case String:
				this.dateFormat = null;
				break;
			case Date:
				this.dateFormat = coalesceNotNull(dateFormat, DEFAULT_DATE_FORMAT);
				break;
			case Number:
				this.dateFormat = null;
				break;
			default:
				throw new AdempiereException("Unexpected value: " + type.getDataType());
		}
	}

	@Override
	@Deprecated
	public String toString() {return toStringShort();}

	private String toStringShort() {return type + "[" + startPosition + "-" + endPosition + "]";}

	BooleanWithReason parseAndUpdateResult(@NonNull final ScannedCode scannedCode, @NonNull final ParsedScannedCodeBuilder result)
	{
		try
		{
			final String valueStr = extractAsString(scannedCode).orElse(null);
			if (valueStr == null)
			{
				return BooleanWithReason.falseBecause("Cannot extract part " + this);
			}

			switch (type)
			{
				case Ignored:
					break;
				case ProductCode:
					result.productNo(valueStr);
					break;
				case WeightInKg:
					result.weightKg(toBigDecimal(valueStr));
					break;
				case LotNo:
					result.lotNo(valueStr);
					break;
				case BestBeforeDate:
					result.bestBeforeDate(toLocalDate(valueStr));
					break;
			}

			return BooleanWithReason.TRUE;
		}
		catch (final Exception ex)
		{
			return BooleanWithReason.falseBecause("Failed extracting " + this + " because " + ex.getLocalizedMessage());
		}
	}

	private Optional<String> extractAsString(final ScannedCode scannedCode)
	{
		int startIndex = startPosition - 1;
		int endIndex = endPosition - 1 + 1;

		if (endIndex > scannedCode.length())
		{
			return Optional.empty();
		}

		return Optional.of(scannedCode.substring(startIndex, endIndex));
	}

	private BigDecimal toBigDecimal(final String valueStr)
	{
		return new BigDecimal(valueStr);
	}

	private LocalDate toLocalDate(final String valueStr)
	{
		final DateTimeFormatter dateFormat = coalesceNotNull(this.dateFormat, DEFAULT_DATE_FORMAT);
		return LocalDate.parse(valueStr, dateFormat);
	}

}
