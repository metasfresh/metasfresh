package de.metas.i18n;

import java.util.Set;
import java.util.function.Supplier;

import org.adempiere.util.Check;

import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2017 metas GmbH
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

@ToString
public class ForwardingTranslatableString implements ITranslatableString
{
	public static final ForwardingTranslatableString of(final Supplier<ITranslatableString> delegateSupplier)
	{
		return new ForwardingTranslatableString(delegateSupplier);
	}

	private final Supplier<ITranslatableString> delegateSupplier;

	private ForwardingTranslatableString(@NonNull final Supplier<ITranslatableString> delegateSupplier)
	{
		this.delegateSupplier = delegateSupplier;
	}

	private ITranslatableString getDelegate()
	{
		final ITranslatableString delegate = delegateSupplier.get();
		Check.assumeNotNull(delegate, "delegate supplier shall not return null instances: {}", delegateSupplier);
		return delegate;
	}

	@Override
	public String translate(final String adLanguage)
	{
		return getDelegate().translate(adLanguage);
	}

	@Override
	public String getDefaultValue()
	{
		return getDelegate().getDefaultValue();
	}

	@Override
	public Set<String> getAD_Languages()
	{
		return getDelegate().getAD_Languages();
	}
}
