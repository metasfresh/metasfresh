package de.metas.handlingunits.qrcodes.ean13;

import de.metas.gs1.ean13.EAN13;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.i18n.ExplainedOptional;
import de.metas.scannable_code.ScannedCode;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@EqualsAndHashCode
public class EAN13HUQRCode implements IHUQRCode
{
	@NonNull private final EAN13 ean13;

	public static EAN13HUQRCode ofEAN13(final EAN13 ean13)
	{
		return new EAN13HUQRCode(ean13);
	}

	@Nullable
	public static EAN13HUQRCode fromScannedCodeOrNullIfNotHandled(@NonNull final ScannedCode scannedCode)
	{
		return fromStringOrNullIfNotHandled(scannedCode.getAsString());
	}

	@Nullable
	public static EAN13HUQRCode fromStringOrNullIfNotHandled(@NonNull final String barcode)
	{
		return fromString(barcode).orElse(null);
	}

	public static ExplainedOptional<EAN13HUQRCode> fromString(@NonNull final String barcode)
	{
		return EAN13.ofString(barcode).map(EAN13HUQRCode::ofEAN13);
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	public ScannedCode toScannedCode() {return ScannedCode.ofString(getAsString());}

	@Override
	public String getAsString() {return ean13.getAsString();}

	@Override
	public Optional<BigDecimal> getWeightInKg() {return ean13.getWeightInKg();}

	@Override
	public Optional<LocalDate> getBestBeforeDate() {return Optional.empty();}

	@Override
	public Optional<String> getLotNumber() {return Optional.empty();}

	@NonNull
	public EAN13 unbox() {return ean13;}
}
