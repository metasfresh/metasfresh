package de.metas.acct.open_items;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

@EqualsAndHashCode
public final class FAOpenItemKey
{
	@Getter @NonNull private final String asString;

	private FAOpenItemKey(@NonNull final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		if (stringNorm == null)
		{
			throw new AdempiereException("empty/null FA Open Item Key is not allowed");
		}

		this.asString = stringNorm;
	}

	public static FAOpenItemKey ofNullableString(final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		return stringNorm != null ? new FAOpenItemKey(stringNorm) : null;
	}

	public static FAOpenItemKey ofString(final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		return stringNorm != null ? new FAOpenItemKey(stringNorm) : null;
	}

	@Override
	public String toString() {return asString;}
}
