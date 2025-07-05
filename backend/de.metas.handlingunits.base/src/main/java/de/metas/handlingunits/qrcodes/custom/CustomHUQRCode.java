package de.metas.handlingunits.qrcodes.custom;

import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.scannable_code.format.ParsedScannedCode;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@EqualsAndHashCode
public class CustomHUQRCode implements IHUQRCode
{
	@NonNull private final ParsedScannedCode parsedScannedCode;

	private CustomHUQRCode(@NonNull final ParsedScannedCode parsedScannedCode)
	{
		this.parsedScannedCode = parsedScannedCode;
	}

	public static CustomHUQRCode ofParsedScannedCode(@NonNull final ParsedScannedCode parsedScannedCode)
	{
		return new CustomHUQRCode(parsedScannedCode);
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@Override
	public String getAsString() {return parsedScannedCode.getAsString();}

	@Override
	public Optional<BigDecimal> getWeightInKg()
	{
		return Optional.ofNullable(parsedScannedCode.getWeightKg());
	}

	@Override
	public Optional<LocalDate> getBestBeforeDate()
	{
		return Optional.ofNullable(parsedScannedCode.getBestBeforeDate());
	}

	@Override
	public Optional<String> getLotNumber()
	{
		return Optional.ofNullable(parsedScannedCode.getLotNo());
	}
}
