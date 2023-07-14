package de.metas.acct;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode(doNotUseGetters = true)
public final class AccountConceptualName implements Comparable<AccountConceptualName>
{
	private final String name;

	private static final ConcurrentHashMap<String, AccountConceptualName> cache = new ConcurrentHashMap<>();

	private AccountConceptualName(@NonNull final String name) {this.name = name;}

	@Nullable
	public static AccountConceptualName ofNullableString(@Nullable final String name)
	{
		final String nameNorm = StringUtils.trimBlankToNull(name);
		return nameNorm != null ? ofString(nameNorm) : null;
	}

	public static AccountConceptualName ofString(@NonNull final String name)
	{
		final String nameNorm = StringUtils.trimBlankToNull(name);
		if (nameNorm == null)
		{
			throw new AdempiereException("empty/null account conceptual name is not allowed");
		}

		return cache.computeIfAbsent(nameNorm, AccountConceptualName::new);
	}

	@Override
	public String toString() {return name;}

	public String getAsString() {return name;}

	@Override
	public int compareTo(@NonNull final AccountConceptualName other)
	{
		return this.name.compareTo(other.name);
	}

	public static boolean equals(@Nullable final AccountConceptualName o1, @Nullable final AccountConceptualName o2) {return Objects.equals(o1, o2);}

	public boolean isAnyOf(@Nullable final AccountConceptualName... names)
	{
		if (names == null)
		{
			return false;
		}

		for (final AccountConceptualName name : names)
		{
			if (name != null && equals(this, name))
			{
				return true;
			}
		}

		return false;
	}
}
