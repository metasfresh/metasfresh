package de.metas.gplr.source.model;

import de.metas.util.Check;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class SourceTaxInfo
{
	@Nullable String vatCode;
	@NonNull BigDecimal rate;

	@Override
	public String toString() {return toRenderedString();}

	public String toRenderedString()
	{
		StringBuilder result = new StringBuilder("VAT");
		if (!Check.isBlank(vatCode))
		{
			result.append(" (").append(vatCode).append(")");
		}

		result.append(" ").append(NumberUtils.stripTrailingDecimalZeros(rate)).append("%");

		return result.toString();
	}
}
