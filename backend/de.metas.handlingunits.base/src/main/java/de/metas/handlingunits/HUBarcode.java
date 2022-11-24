package de.metas.handlingunits;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode
public final class HUBarcode
{
	private final String stringRepresentation;

	private HUBarcode(@NonNull final String stringRepresentation)
	{
		Check.assumeNotEmpty(stringRepresentation, "empty string barcode is not valid");
		this.stringRepresentation = stringRepresentation;
	}

	@JsonCreator
	public static HUBarcode ofBarcodeString(@NonNull final String barcode)
	{
		return new HUBarcode(barcode);
	}

	public static HUBarcode ofHuId(@NonNull final HuId huId)
	{
		return new HUBarcode(String.valueOf(huId.getRepoId()));
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@JsonValue
	public String getAsString() {return stringRepresentation;}
}
