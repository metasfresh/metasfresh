package de.metas.i18n;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@FieldDefaults(makeFinal = true)
@EqualsAndHashCode
public final class BooleanWithReason
{
	public static BooleanWithReason trueBecause(@NonNull final String reason)
	{
		return trueBecause(toTrl(reason));
	}

	public static BooleanWithReason trueBecause(@NonNull final ITranslatableString reason)
	{
		if (TranslatableStrings.isBlank(reason))
		{
			return TRUE;
		}
		else
		{
			return new BooleanWithReason(true, reason);
		}
	}

	public static BooleanWithReason falseBecause(@NonNull final String reason)
	{
		return falseBecause(toTrl(reason));
	}

	public static BooleanWithReason falseBecause(@NonNull final ITranslatableString reason)
	{
		if (TranslatableStrings.isBlank(reason))
		{
			return FALSE;
		}
		else
		{
			return new BooleanWithReason(false, reason);
		}
	}

	public static BooleanWithReason falseBecause(@NonNull final AdMessageKey adMessage, @Nullable final Object... msgParameters)
	{
		return falseBecause(TranslatableStrings.adMessage(adMessage, msgParameters));
	}

	public static BooleanWithReason falseBecause(@NonNull final Exception exception)
	{
		return falseBecause(AdempiereException.extractMessageTrl(exception));
	}

	private static ITranslatableString toTrl(@Nullable final String reasonStr)
	{
		if (reasonStr == null || Check.isBlank(reasonStr))
		{
			return TranslatableStrings.empty();
		}
		else
		{
			return TranslatableStrings.anyLanguage(reasonStr.trim());
		}
	}

	public static final BooleanWithReason TRUE = new BooleanWithReason(true, TranslatableStrings.empty());
	public static final BooleanWithReason FALSE = new BooleanWithReason(false, TranslatableStrings.empty());

	private final boolean value;
	@NonNull @Getter private final ITranslatableString reason;

	private BooleanWithReason(
			final boolean value,
			@NonNull final ITranslatableString reason)
	{
		this.value = value;
		this.reason = reason;
	}

	@Override
	public String toString()
	{
		final String valueStr = String.valueOf(value);
		final String reasonStr = !TranslatableStrings.isBlank(reason)
				? reason.getDefaultValue()
				: null;

		return reasonStr != null
				? valueStr + " because " + reasonStr
				: valueStr;
	}

	public boolean toBoolean()
	{
		return value;
	}

	public boolean isTrue()
	{
		return value;
	}

	public boolean isFalse()
	{
		return !value;
	}

	public String getReasonAsString()
	{
		return reason.getDefaultValue();
	}

	public void assertTrue()
	{
		if (isFalse())
		{
			throw new AdempiereException(reason);
		}
	}

	public BooleanWithReason and(@NonNull final Supplier<BooleanWithReason> otherSupplier)
	{
		return isFalse()
				? this
				: Check.assumeNotNull(otherSupplier.get(), "otherSupplier shall not return null");
	}

}
