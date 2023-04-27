/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.notification;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.i18n.impl.PlainMsgBL;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.text.MessageFormat;
import java.util.HashMap;

public class MockedMsgBL extends PlainMsgBL
{
	private final HashMap<AdMessageKey, String> msgTexts = new HashMap<>();

	public void putMsgText(@NonNull final AdMessageKey adMessage, @NonNull final String msgText)
	{
		msgTexts.put(adMessage, msgText);
	}

	private String getMsgText(final AdMessageKey adMessage)
	{
		final String msgText = msgTexts.get(adMessage);
		if (msgText == null)
		{
			throw new AdempiereException("No MsgText registered for " + adMessage + " in " + this);
		}
		return msgText;
	}

	@Override
	public ITranslatableString getTranslatableMsgText(@NonNull final AdMessageKey adMessage, final Object... msgParameters)
	{
		final String msgText = getMsgText(adMessage);
		return TranslatableStrings.constant(MessageFormat.format(msgText, msgParameters));
	}
}
