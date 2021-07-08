/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.adempiere.archive.api;

import de.metas.email.EMailSentStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import lombok.NonNull;

public class ArchiveEmailSentStatus
{
	public static final ArchiveEmailSentStatus MESSAGE_SENT = new ArchiveEmailSentStatus(AdMessageKey.of("MessageSent"));
	public static final ArchiveEmailSentStatus MESSAGE_NOT_SENT = new ArchiveEmailSentStatus(AdMessageKey.of("MessageNotSent"));

	private final AdMessageKey adMessage;
	private final String plainMessage;

	ArchiveEmailSentStatus(
			@NonNull final AdMessageKey adMessage)
	{
		this.adMessage = adMessage;
		this.plainMessage = null;
	}

	ArchiveEmailSentStatus(@NonNull final String plainMessage)
	{
		this.adMessage = null;
		this.plainMessage = plainMessage;
	}

	public static ArchiveEmailSentStatus ofEMailSentStatus(final EMailSentStatus emailSentStatus)
	{
		if (emailSentStatus.isSentOK())
		{
			return MESSAGE_SENT;
		}
		else
		{
			return new ArchiveEmailSentStatus(emailSentStatus.getSentMsg());
		}
	}

	public String toDisplayText(@NonNull final IMsgBL msgBL, @NonNull final String adLanguage)
	{
		if (adMessage != null)
		{
			return msgBL.getTranslatableMsgText(adMessage).translate(adLanguage);
		}
		else
		{
			return plainMessage;
		}
	}
}
