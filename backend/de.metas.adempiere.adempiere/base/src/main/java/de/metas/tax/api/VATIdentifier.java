package de.metas.tax.api;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Locale;

@EqualsAndHashCode
public final class VATIdentifier
{
	@NonNull private final String value;

	private VATIdentifier(@NonNull final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		if (valueNorm == null)
		{
			throw new AdempiereException("Invalid VAT ID");
		}

		this.value = valueNorm;
	}

	@NonNull
	public static VATIdentifier of(@NonNull final String value)
	{
		return new VATIdentifier(value);
	}

	@Nullable
	public static VATIdentifier ofNullable(@Nullable final String value)
	{
		final String valueNorm = StringUtils.trimBlankToNull(value);
		return valueNorm != null ? of(valueNorm) : null;
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@NonNull
	public String getAsString() {return value;}

	/**
	 * @return the first two characters, uppercased — the member-state prefix every EU VAT-ID carries. A
	 * value shorter than two characters is returned uppercased but otherwise unchanged, and simply matches
	 * no member state.
	 *
	 * <p>{@code VIESClient} and {@code EUVatIdValidator} each re-derive this same 2-char prefix privately,
	 * so a change to the convention here does NOT propagate to them and the compiler will not catch it.
	 */
	@NonNull
	public String getCountryCodePrefix()
	{
		final String upper = value.toUpperCase(Locale.ROOT);
		return upper.length() >= 2 ? upper.substring(0, 2) : upper;
	}

	@Nullable
	public static String toString(@Nullable final VATIdentifier vatId)
	{
		return vatId != null ? vatId.getAsString() : null;
	}
}