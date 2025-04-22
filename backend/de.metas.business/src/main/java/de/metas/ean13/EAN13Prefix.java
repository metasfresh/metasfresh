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
	public static EAN13Prefix ofString(@NonNull final String stringCode)
	{
		final String stringCodeNorm = StringUtils.trimBlankToNull(stringCode);
		if (stringCodeNorm == null || stringCodeNorm.length() != 2)
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
