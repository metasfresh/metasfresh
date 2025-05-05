package de.metas.impexp.parser.csv;

import de.metas.impexp.format.ImpFormat;
import de.metas.impexp.format.ImpFormatType;
import de.metas.impexp.parser.ImpDataParser;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

@UtilityClass
public class CsvImpDataParserFactory
{
	public static ImpDataParser createParser(@NonNull final ImpFormat impFormat)
	{
		return CsvImpDataParser.builder()
				.multiline(impFormat.isMultiLine())
				.lineParser(createLineParser(impFormat))
				.charset(impFormat.getCharset())
				.skipFirstNRows(impFormat.getSkipFirstNRows())
				.build();
	}

	private static CsvImpDataLineParser createLineParser(@NonNull final ImpFormat impFormat)
	{
		final ImpFormatType formatType = impFormat.getFormatType();
		if (ImpFormatType.FIXED_POSITION.equals(formatType))
		{
			return new FixedPositionImpDataLineParser(impFormat);
		}
		else if (ImpFormatType.COMMA_SEPARATED.equals(formatType)
				|| ImpFormatType.SEMICOLON_SEPARATED.equals(formatType)
				|| ImpFormatType.TAB_SEPARATED.equals(formatType))
		{
			return new FlexImpDataLineParser(impFormat);
		}
		else
		{
			throw new AdempiereException("Unsupported format type: " + formatType);
		}
	}
}
