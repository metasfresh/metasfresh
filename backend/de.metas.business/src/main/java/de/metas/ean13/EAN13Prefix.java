package de.metas.ean13;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode(doNotUseGetters = true)
public class EAN13Prefix
{
	public static final EAN13Prefix VariableWeight = new EAN13Prefix("28");
	public static final EAN13Prefix InternalUseOrVariableMeasure = new EAN13Prefix("29");

	private static final ConcurrentHashMap<String, EAN13Prefix> interner = new ConcurrentHashMap<>();

	static
	{
		interner.put(VariableWeight.code, VariableWeight);
		interner.put(InternalUseOrVariableMeasure.code, InternalUseOrVariableMeasure);
	}

	@NonNull private final String code;

	private EAN13Prefix(@NonNull final String code)
	{
		this.code = code;
	}

	@NonNull
	static EAN13Prefix extractFromBarcode(@NonNull final String barcode)
	{
		// GS1 Prefix logic: Check if it's 2 or 3 digits based on known rules
		final String firstTwoDigits = barcode.substring(0, 2);
		if (VariableWeight.code.equals(firstTwoDigits))
		{
			return VariableWeight; // Variable-length barcodes or special GS1 allocations
		}
		else if (InternalUseOrVariableMeasure.code.equals(firstTwoDigits))
		{
			return InternalUseOrVariableMeasure; // Variable-length barcodes or special GS1 allocations
		}
		else
		{
			// Otherwise, use the first 3 digits for standard GS1 prefixes
			return ofString(barcode.substring(0, 3));
		}

	}

	@NonNull
	private static EAN13Prefix ofString(@NonNull final String stringCode)
	{
		final String stringCodeNorm = StringUtils.trimBlankToNull(stringCode);
		if (stringCodeNorm == null
				|| !(stringCodeNorm.length() == 2 || stringCodeNorm.length() == 3))
		{
			throw new AdempiereException("Invalid EAN13 prefix: " + stringCode);
		}

		return interner.computeIfAbsent(stringCode, EAN13Prefix::new);
	}

	@Override
	@Deprecated
	public String toString() {return code;}

	public String getAsString() {return code;}

	public boolean isVariableWeight() {return this.equals(VariableWeight);}

	public boolean isInternalUseOrVariableMeasure() {return this.equals(InternalUseOrVariableMeasure);}
}
