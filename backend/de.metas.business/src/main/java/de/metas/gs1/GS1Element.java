package de.metas.gs1;

import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Value
@Builder
public class GS1Element
{
	private static final DateTimeFormatter LOCAL_DATE_PATTERN = DateTimeFormatter.ofPattern("yyMMdd");

	@Nullable GS1ApplicationIdentifier identifier;
	@NonNull String key;
	@NonNull Object value;

	public String getValueAsString() {return value.toString();}

	public BigDecimal getValueAsBigDecimal() {return NumberUtils.asBigDecimal(value);}

	public LocalDate getValueAsLocalDate()
	{
		if (value instanceof LocalDateTime)
		{
			return ((LocalDateTime)value).toLocalDate();
		}
		else if (value instanceof LocalDate)
		{
			return (LocalDate)value;
		}
		else
		{
			try
			{
				return LocalDate.parse(value.toString(), LOCAL_DATE_PATTERN);
			}
			catch (Exception ex)
			{
				throw new AdempiereException("Invalid LocalDate GS1 element: " + value + " (" + value.getClass() + ")", ex);
			}
		}
	}
}
