package de.metas.i18n.impl;

import de.metas.i18n.ILanguageBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Msg;
import de.metas.util.Services;
import lombok.NonNull;

import java.util.Properties;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Wraps a given <code>text</code> and will call {@link Msg#translate(Properties, String, boolean)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@lombok.EqualsAndHashCode
final class ADElementOrADMessageTranslatableString implements ITranslatableString
{
	private final String text;

	ADElementOrADMessageTranslatableString(@NonNull final String text)
	{
		this.text = text;
	}

	@Override
	public String toString()
	{
		return text;
	}

	@Override
	public String translate(final String adLanguage)
	{
		final boolean isSOTrx = true;
		return Msg.translate(adLanguage, isSOTrx, text);
	}

	@Override
	public String getDefaultValue()
	{
		return "@" + text + "@";
	}

	@Override
	public Set<String> getAD_Languages()
	{
		return Services.get(ILanguageBL.class).getAvailableLanguages().getAD_Languages();
	}

}
