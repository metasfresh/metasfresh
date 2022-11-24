package org.adempiere.warehouse;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Optional;

@EqualsAndHashCode
public class LocatorBarcode
{
	@JsonCreator
	public static LocatorBarcode ofBarcodeString(@NonNull final String barcode)
	{
		if (!barcode.startsWith(PREFIX))
		{
			throw new AdempiereException("Invalid locator barcode: " + barcode);
		}

		try
		{
			final String[] locatorIdStrParts = barcode.substring(PREFIX.length()).split("-");
			final WarehouseId warehouseId = WarehouseId.ofRepoId(Integer.parseInt(locatorIdStrParts[0]));
			final int locatorRepoId = Integer.parseInt(locatorIdStrParts[1]);
			final LocatorId locatorId = LocatorId.ofRepoId(warehouseId, locatorRepoId);
			return new LocatorBarcode(locatorId);
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid locator barcode: " + barcode, ex);

		}
	}

	public static Optional<LocatorBarcode> optionalOfBarcodeString(@Nullable final String barcode)
	{
		return barcode != null && !barcode.isEmpty()
				? Optional.of(ofBarcodeString(barcode))
				: Optional.empty();
	}

	public static LocatorBarcode ofLocatorId(@NonNull final LocatorId locatorId)
	{
		return new LocatorBarcode(locatorId);
	}

	private static final String PREFIX = "locator-";

	@Getter
	private final LocatorId locatorId;

	private transient String stringRepresentation = null;

	private LocatorBarcode(@NonNull final LocatorId locatorId) {this.locatorId = locatorId;}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		String stringRepresentation = this.stringRepresentation;
		if (stringRepresentation == null)
		{
			stringRepresentation = this.stringRepresentation = PREFIX + locatorId.getWarehouseId().getRepoId() + "-" + locatorId.getRepoId();
		}
		return stringRepresentation;
	}
}
