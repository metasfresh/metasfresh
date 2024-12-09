package de.metas.email.mailboxes;

<<<<<<< HEAD
import javax.annotation.Nullable;

import org.adempiere.service.ClientId;

import de.metas.document.DocBaseAndSubType;
import de.metas.email.EMailCustomType;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
=======
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.email.EMailCustomType;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessExecutor;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
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
public class MailboxQuery
{
<<<<<<< HEAD
	@NonNull
	ClientId clientId;

	@NonNull
	OrgId orgId;

	@Nullable
	AdProcessId adProcessId;

	@Nullable
	DocBaseAndSubType docBaseAndSubType;

	@Nullable
	EMailCustomType customType;
=======
	@NonNull ClientId clientId;
	@NonNull @Builder.Default OrgId orgId = ProcessExecutor.getCurrentOrgId();
	@Nullable @Builder.Default AdProcessId adProcessId = ProcessExecutor.getCurrentProcessIdOrNull();
	@Nullable DocBaseAndSubType docBaseAndSubType;
	@Nullable EMailCustomType customType;

	@Nullable UserId fromUserId;

	public static MailboxQuery ofClientId(@NonNull final ClientId clientId) {return MailboxQuery.builder().clientId(clientId).build();}

	public DocBaseType getDocBaseType() { return docBaseAndSubType != null ? docBaseAndSubType.getDocBaseType() : null; }
	public DocSubType getDocSubType() { return docBaseAndSubType != null ? docBaseAndSubType.getDocSubType() : DocSubType.ANY; }
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
