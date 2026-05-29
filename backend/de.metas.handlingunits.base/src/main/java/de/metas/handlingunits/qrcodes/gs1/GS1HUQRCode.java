package de.metas.handlingunits.qrcodes.gs1;

import de.metas.gs1.GS1Elements;
import de.metas.gs1.GS1Parser;
import de.metas.gs1.GTIN;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.scannable_code.ScannedCode;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@EqualsAndHashCode
public class GS1HUQRCode implements IHUQRCode
{
	@NonNull private final ScannedCode code;
	@NonNull private final GS1Elements elements;

	private GS1HUQRCode(@NonNull final ScannedCode code, @NonNull final GS1Elements elements)
	{
		this.code = code;
		this.elements = elements;
	}

	@Nullable
	public static GS1HUQRCode fromStringOrNullIfNotHandled(@NonNull final String string)
	{
		return fromScannedCodeOrNullIfNotHandled(ScannedCode.ofString(string));
	}

	public static GS1HUQRCode fromScannedCodeOrNullIfNotHandled(@NonNull final ScannedCode scannedCode)
	{
		final GS1Elements elements = GS1Parser.parseElementsOrNull(scannedCode.getAsString());
		return elements != null ? new GS1HUQRCode(scannedCode, elements) : null;
	}

	public static GS1HUQRCode fromString(@NonNull final String string)
	{
		final GS1HUQRCode code = fromStringOrNullIfNotHandled(string);
		if (code == null)
		{
			throw new AdempiereException("Invalid GS1 code: " + string);
		}
		return code;
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@Override
	public String getAsString() {return code.getAsString();}

	public Optional<GTIN> getGTIN() {return elements.getGTIN();}

	@Override
	public Optional<BigDecimal> getWeightInKg() {return elements.getWeightInKg();}

	@Override
	public Optional<LocalDate> getBestBeforeDate() {return elements.getBestBeforeDate();}

	@Override
	public Optional<String> getLotNumber() {return elements.getLotNumber();}
}
