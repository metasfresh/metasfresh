package de.metas.handlingunits.qrcodes.gs1;

import de.metas.gs1.GS1Elements;
import de.metas.gs1.GS1Parser;
import de.metas.gs1.GTIN;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public class GS1HUQRCode implements IHUQRCode
{
	@NonNull private final GS1Elements elements;

	private GS1HUQRCode(@NonNull final GS1Elements elements)
	{
		this.elements = elements;
	}

	public static GS1HUQRCode fromStringOrNullIfNotHandled(@NonNull final String string)
	{
		final GS1Elements elements = GS1Parser.parseElementsOrNull(string);
		return elements != null ? new GS1HUQRCode(elements) : null;
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

	public Optional<GTIN> getGTIN() {return elements.getGTIN();}

	@Override
	public Optional<BigDecimal> getWeightInKg() {return elements.getWeightInKg();}

	@Override
	public Optional<LocalDate> getBestBeforeDate() {return elements.getBestBeforeDate();}

	@Override
	public Optional<String> getLotNumber() {return elements.getLotNumber();}
}
