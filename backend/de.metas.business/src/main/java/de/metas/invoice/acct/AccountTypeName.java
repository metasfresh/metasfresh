package de.metas.invoice.acct;

import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode
public final class AccountTypeName implements Comparable<AccountTypeName>
{
	@Getter
	private final String columnName;

	private static final ConcurrentHashMap<String, AccountTypeName> cache = new ConcurrentHashMap<>();

	private AccountTypeName(@NonNull final String columnName)
	{
		this.columnName = StringUtils.trimBlankToNull(columnName);
		if (this.columnName == null)
		{
			throw new AdempiereException("columnName shall not be blank");
		}
	}

	@Nullable
	public static AccountTypeName ofNullableColumnName(@Nullable final String columnName)
	{
		final String columnNameNorm = StringUtils.trimBlankToNull(columnName);
		return columnNameNorm != null
				? cache.computeIfAbsent(columnNameNorm, AccountTypeName::new)
				: null;
	}

	@NonNull
	public static AccountTypeName ofColumnName(@NonNull final String columnName)
	{
		final AccountTypeName accountTypeName = ofNullableColumnName(columnName);
		return Check.assumeNotNull(accountTypeName, "ColumnName shall not be blank");
	}

	public String toString() {return columnName;}

	@Override
	public int compareTo(@NonNull final AccountTypeName other)
	{
		return this.columnName.compareTo(other.columnName);
	}

	public static boolean equals(@Nullable final AccountTypeName o1, @Nullable final AccountTypeName o2) {return Objects.equals(o1, o2);}
}
