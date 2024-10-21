package de.metas.handlingunits.qrcodes.gs1;

import de.metas.gs1.GS1Elements;
import de.metas.gs1.GS1Parser;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

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

	@Override
	public Optional<BigDecimal> getWeightInKg() {return elements.getWeightInKg();}

	@Override
	public Optional<LocalDate> getBestBeforeDate() {return elements.getBestBeforeDate();}

	@Override
	public Optional<String> getLotNumber() {return elements.getLotNumber();}
}
