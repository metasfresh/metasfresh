package de.metas.document.archive.mailrecipient.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getValueOrNull;

import java.util.Optional;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_DocType;
import org.springframework.stereotype.Component;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientId;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientRepository;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientProvider;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.email.EMailCustomType;
import de.metas.email.MailService;
import de.metas.email.mailboxes.ClientEMailConfig;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxNotFoundException;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class DefaultDocOutboundLogMailRecipientProvider implements DocOutboundLogMailRecipientProvider
{
	private final DocOutBoundRecipientRepository docOutBoundRecipientRepository;
	private final MailService mailService;

	private final IClientDAO clientsRepo = Services.get(IClientDAO.class);
	private final IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);

	public DefaultDocOutboundLogMailRecipientProvider(
			@NonNull final DocOutBoundRecipientRepository userRepository,
			@NonNull final MailService mailService)
	{
		this.docOutBoundRecipientRepository = userRepository;
		this.mailService = mailService;
	}

	/** returns {@code true}. */
	@Override
	public boolean isDefault()
	{
		return true;
	}

	/** returns {@code null}. */
	@Override
	public String getTableName()
	{
		return null;
	}

	@Override
	public Optional<DocOutBoundRecipient> provideMailRecipient(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		if (docOutboundLogRecord.getRecord_ID() <= 0 || docOutboundLogRecord.getAD_Table_ID() <= 0)
		{
			Loggables.addLog("provideMailRecipient - docOutboundLogRecord has no AD_Table_ID/Record_ID => return 'no recipient'; docOutboundLogRecord={}", docOutboundLogRecord);
			return Optional.empty();
		}

		final Mailbox mailbox = findMailboxOrNull(docOutboundLogRecord);
		if (mailbox == null)
		{
			Loggables.addLog("provideMailRecipient - return 'no recipient'; mailbox={}", mailbox);
			return Optional.empty();
		}

		// check if the column for the user is specified
		final String userToColumnName = mailbox.getUserToColumnName();
		if (!Check.isEmpty(userToColumnName, true))
		{

			final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
			final String tableName = adTableDAO.retrieveTableName(docOutboundLogRecord.getAD_Table_ID());
			final boolean existsColumn = tableName != null && adTableDAO.hasColumnName(tableName, userToColumnName);

			Loggables.addLog("provideMailRecipient - Mail config has userToColumnName={}; column exists: {}; mailbox={}", userToColumnName, existsColumn, mailbox);
			if (existsColumn)
			{
				final IContextAware context = PlainContextAware.newWithThreadInheritedTrx();
				final Object referencedModel = TableRecordReference.ofReferenced(docOutboundLogRecord).getModel(context);

				// load the column content
				final Integer userRepoId = getValueOrNull(referencedModel, userToColumnName);
				if (userRepoId == null || userRepoId < 0)
				{
					Loggables.addLog("provideMailRecipient - Record model has {}={} => return 'no recipient'", userToColumnName, userRepoId);
					return Optional.empty();
				}
				if (userRepoId == 0)
				{
					Loggables.addLog("provideMailRecipient - Record model has {}={} (system-user) => return 'no recipient'", userToColumnName, userRepoId);
					return Optional.empty();
				}

				final DocOutBoundRecipientId docOutBoundRecipientId = DocOutBoundRecipientId.ofRepoId(userRepoId);
				final DocOutBoundRecipient user = docOutBoundRecipientRepository.getById(docOutBoundRecipientId);
				if (Check.isEmpty(user.getEmailAddress(), true))
				{
					Loggables.addLog("provideMailRecipient - user-id {} has no/empty emailAddress => return 'no recipient'; user={}", user.getId(), user);
					return Optional.empty();
				}
				return Optional.of(user);
			}
		}
		Loggables.addLog("provideMailRecipient - return 'no recipient'; mailbox={}", mailbox);
		return Optional.empty();
	}

	private Mailbox findMailboxOrNull(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		try
		{
			final ClientEMailConfig tenantEmailConfig = extractTenantEmailConfig(docOutboundLogRecord);
			final DocBaseAndSubType docBaseAndSubType = extractDocBaseAndSubType(docOutboundLogRecord);

			return mailService.findMailBox(
					tenantEmailConfig,
					OrgId.ofRepoId(docOutboundLogRecord.getAD_Org_ID()),
					(AdProcessId)null, // don't filter by processID
					docBaseAndSubType,
					(EMailCustomType)null); // mailCustomType
		}
		catch (final MailboxNotFoundException e)
		{
			Loggables.addLog("findMailboxOrNull. DefaultDocOutboundLogMailRecipientProvider - Unable to find a mailbox for record (system-user) => return null; exception message: {}; docOutboundLogRecord={}", e.getMessage(), docOutboundLogRecord);
			return null;
		}
	}

	private ClientEMailConfig extractTenantEmailConfig(final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final ClientId adClientId = ClientId.ofRepoId(docOutboundLogRecord.getAD_Client_ID());
		final ClientEMailConfig tenantEmailConfig = clientsRepo.getEMailConfigById(adClientId);
		return tenantEmailConfig;
	}

	private DocBaseAndSubType extractDocBaseAndSubType(final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(docOutboundLogRecord.getC_DocType_ID());
		if (docTypeId == null)
		{
			return null;
		}

		final I_C_DocType docType = docTypesRepo.getById(docTypeId);
		return DocBaseAndSubType.of(docType.getDocBaseType(), docType.getDocSubType());
	}
}
