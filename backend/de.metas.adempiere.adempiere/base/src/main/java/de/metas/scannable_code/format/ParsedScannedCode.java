package de.metas.scannable_code.format;

import de.metas.gs1.GTIN;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class ParsedScannedCode
{
	@NonNull ScannedCode scannedCode;

	@Nullable String productNo;
	@Nullable BigDecimal weightKg;
	@Nullable String lotNo;
	@Nullable LocalDate bestBeforeDate;
	@Nullable LocalDate productionDate;
	@Nullable GTIN gtin;

	public String getAsString()
	{
		return scannedCode.getAsString();
	}
}
