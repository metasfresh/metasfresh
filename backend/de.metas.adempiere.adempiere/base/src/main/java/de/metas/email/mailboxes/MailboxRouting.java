package de.metas.email.mailboxes;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.email.EMailCustomType;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

@Value
@Builder
class MailboxRouting
{
	@NonNull MailboxId mailboxId;
	@Nullable String userToColumnName;
	
	@NonNull ClientId clientId;
	@NonNull OrgId orgId;
	@Nullable DocBaseType docBaseType;
	@NonNull @Builder.Default DocSubType docSubType = DocSubType.ANY;
	@Nullable AdProcessId adProcessId;
	@Nullable EMailCustomType emailCustomType;
}
