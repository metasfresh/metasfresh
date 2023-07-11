package de.metas.acct.open_items;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

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

	public static Optional<FAOpenItemKey> optionalOfString(@Nullable final String string) {return Optional.ofNullable(ofNullableString(string));}

	public static FAOpenItemKey ofTableAndRecord(@NonNull final String tableName, final int recordId)
	{
		return ofString(tableName + "#" + Math.max(recordId, 0));
	}

	public static FAOpenItemKey ofTableRecordLineAndSubLineId(
			@NonNull final String tableName,
			final int recordId,
			final int lineId,
			final int subLineId)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(tableName);
		sb.append("#").append(Math.max(recordId, 0));
		if (lineId > 0)
		{
			sb.append("#").append(lineId);
		}
		if (subLineId > 0)
		{
			sb.append("#").append(subLineId);
		}

		return ofString(sb.toString());
	}

	public static FAOpenItemKey ofString(@NonNull final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		if (stringNorm == null)
		{
			throw new AdempiereException("empty/null Open Item Key is not allowed");
		}
		return new FAOpenItemKey(stringNorm);
	}

	@Override
	public String toString() {return asString;}

	public static boolean equals(@Nullable FAOpenItemKey o1, @Nullable FAOpenItemKey o2) {return Objects.equals(o1, o2);}
}
