package de.metas.i18n.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ILanguageBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Msg;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;

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

@EqualsAndHashCode
final class ADMessageTranslatableString implements ITranslatableString
{
	private final AdMessageKey adMessage;
	private final List<Object> msgParameters;

	ADMessageTranslatableString(@NonNull final AdMessageKey adMessage, @Nullable final Object... msgParameters)
	{
		this.adMessage = adMessage;
		if (msgParameters == null || msgParameters.length == 0)
		{
			this.msgParameters = ImmutableList.of();
		}
		else
		{
			// NOTE: avoid using ImmutableList because there might be null parameters
			this.msgParameters = Collections.unmodifiableList(Arrays.asList(msgParameters));
		}
	}

	@Override
	@Deprecated
	public String toString()
	{
		return adMessage.toAD_Message();
	}

	@Override
	public String translate(final String adLanguage)
	{
		return Msg.getMsg(adLanguage, adMessage.toAD_Message(), msgParameters.toArray());
	}

	@Override
	public String getDefaultValue()
	{
		return adMessage.toAD_MessageWithMarkers();
	}

	@Override
	public Set<String> getAD_Languages()
	{
		return Services.get(ILanguageBL.class).getAvailableLanguages().getAD_Languages();
	}
}
