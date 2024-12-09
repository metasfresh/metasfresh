package de.metas.email.mailboxes;

<<<<<<< HEAD
import java.util.Optional;

import org.adempiere.service.ClientId;

import de.metas.email.EMailAddress;
import de.metas.email.templates.MailTemplateId;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
=======
import de.metas.i18n.ExplainedOptional;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@Builder
<<<<<<< HEAD
@ToString(exclude = "password")
public class ClientEMailConfig
{
	@NonNull
	ClientId clientId;

	boolean sendEmailsFromServer;

	String smtpHost;
	int smtpPort;

	boolean startTLS;

	EMailAddress email;
	boolean smtpAuthorization;
	String username;
	String password;

	Optional<MailTemplateId> passwordResetMailTemplateId;
=======
public class ClientEMailConfig
{
	@NonNull ClientId clientId;
	@NonNull ExplainedOptional<Mailbox> mailbox;

	public Mailbox getMailboxNotNull()
	{
		return mailbox.orElseThrow(MailboxNotFoundException::new);
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
