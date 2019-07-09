package de.metas.email.mailboxes;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_MailBox;
import org.compiere.model.I_AD_MailConfig;
import org.compiere.model.I_AD_User;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import de.metas.document.DocBaseAndSubType;
import de.metas.email.EMailAddress;
import de.metas.email.EMailCustomType;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

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

@Repository
public class MailboxRepository
{
	private static final Logger logger = LogManager.getLogger(MailboxRepository.class);

	/**
	 * @throws MailboxNotFoundException
	 */
	public Mailbox findMailBox(
			@NonNull final I_AD_Client client,
			final OrgId orgId,
			final AdProcessId adProcessId,
			final DocBaseAndSubType docBaseAndSubType,
			final EMailCustomType customType,
			@Nullable final I_AD_User user)
	{
		final Mailbox mailbox = findMailBox(
				client,
				orgId,
				adProcessId,
				docBaseAndSubType,
				customType);
		Check.errorIf(mailbox == null, "Unable to find IMailbox for AD_Client={}, AD_Org_ID={}, AD_Process_ID={}, customeType={}",
				client, orgId, adProcessId, customType);

		if (user == null)
		{
			return mailbox;
		}

		// use smtpHost from AD_MailConfig, but user data from AD_User
		return mailbox.toBuilder()
				.email(EMailAddress.ofNullableString(user.getEMail()))
				.username(user.getEMailUser())
				.password(user.getEMailUserPW())
				.build();
	}

	private Mailbox findMailBox(
			@NonNull final I_AD_Client client,
			@NonNull final OrgId orgId,
			@Nullable final AdProcessId adProcessId,
			@Nullable final DocBaseAndSubType docBaseAndSubType,
			@Nullable final EMailCustomType customType)
	{
		logger.debug("Looking for AD_Client_ID={}, AD_Org_ID={}, AD_Process_ID={}, customType={}", client, orgId, adProcessId, customType);

		//
		// Check mail routings
		final ClientId clientId = ClientId.ofRepoId(client.getAD_Client_ID());
		final List<I_AD_MailConfig> mailRoutings = retrieveMailRoutings(clientId, adProcessId, docBaseAndSubType, customType);
		for (final I_AD_MailConfig mailRouting : mailRoutings)
		{
			final OrgId configOrgId = OrgId.ofRepoIdOrAny(mailRouting.getAD_Org_ID());

			if (OrgId.equals(configOrgId, orgId) || configOrgId.isAny())
			{
				final I_AD_MailBox mailboxRecord = mailRouting.getAD_MailBox();
				final boolean sendMailsFromServer = client.isServerEMail();
				final String userToColumnName = mailRouting.getColumnUserTo();
				final Mailbox mailbox = toMailbox(mailboxRecord, sendMailsFromServer, userToColumnName);

				if (logger.isDebugEnabled())
				{
					logger.debug("Found: {} => {}", toString(mailRouting), mailbox);
				}

				return mailbox;
			}
		}

		//
		// Fallback to AD_Client config
		final Mailbox mailbox = createClientMailbox(client);
		logger.debug("Fallback to AD_Client settings: {}", mailbox);
		return mailbox;
	}

	private static Mailbox toMailbox(
			final I_AD_MailBox record,
			final boolean sendMailsFromServer,
			final String userToColumnName)
	{
		return Mailbox.builder()
				.smtpHost(record.getSMTPHost())
				.smtpPort(record.getSMTPPort())
				.startTLS(record.isStartTLS())
				.email(EMailAddress.ofString(record.getEMail()))
				.username(record.getUserName())
				.password(record.getPassword())
				.smtpAuthorization(record.isSmtpAuthorization())
				.sendFromServer(sendMailsFromServer)
				.columnUserTo(userToColumnName)
				.build();
	}

	@NonNull
	private static Mailbox createClientMailbox(final I_AD_Client clientRecord)
	{
		final String smtpHost = clientRecord.getSMTPHost();
		if (Check.isEmpty(smtpHost, true))
		{
			final String messageString = StringUtils.formatMessage(
					"Mail System not configured. Please define some AD_MailConfig or set AD_Client.SMTPHost; "
							+ "AD_MailConfig search parameters: AD_Client_ID={}",
					clientRecord);

			throw new MailboxNotFoundException(TranslatableStrings.constant(messageString));
		}

		return Mailbox.builder()
				.smtpHost(smtpHost)
				.smtpPort(clientRecord.getSMTPPort())
				.startTLS(clientRecord.isStartTLS())
				.email(EMailAddress.ofNullableString(clientRecord.getRequestEMail()))
				.username(clientRecord.getRequestUser())
				.password(clientRecord.getRequestUserPW())
				.smtpAuthorization(clientRecord.isSmtpAuthorization())
				.sendFromServer(clientRecord.isServerEMail())
				.build();
	}

	private static String toString(final I_AD_MailConfig routingRecord)
	{
		return routingRecord.getClass().getSimpleName() + "["
				+ "AD_Client_ID=" + routingRecord.getAD_Client_ID()
				+ ", AD_Org_ID=" + routingRecord.getAD_Org_ID()
				+ ", AD_Process_ID=" + routingRecord.getAD_Process_ID()
				+ ", CustomType=" + routingRecord.getCustomType()
				+ ", IsActive=" + routingRecord.isActive()
				+ ", AD_MailConfig_ID=" + routingRecord.getAD_MailConfig_ID()
				+ "]";
	}

	private List<I_AD_MailConfig> retrieveMailRoutings(
			@NonNull final ClientId clientId,
			@Nullable final AdProcessId processId,
			@Nullable final DocBaseAndSubType docBaseAndSubType,
			@Nullable final EMailCustomType customType)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_AD_MailConfig> queryBuilder = queryBL.createQueryBuilderOutOfTrx(I_AD_MailConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_AD_Client_ID, clientId);

		// Order by Org, Desc, Nulls Last
		queryBuilder.orderBy()
				.addColumn(I_AD_MailConfig.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_AD_MailConfig.COLUMN_DocSubType, Direction.Ascending, Nulls.Last)
				.endOrderBy();

		if (customType != null)
		{
			queryBuilder.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_CustomType, customType.getCode());
		}
		else if (processId != null)
		{
			queryBuilder.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_AD_Process_ID, processId);

		}

		//
		// DocBaseType and DocSubType added in the mail config (task FRESH-203)
		if (docBaseAndSubType != null)
		{
			final String docBaseType = docBaseAndSubType.getDocBaseType();
			if (!Check.isEmpty(docBaseType, true))
			{
				queryBuilder.addEqualsFilter(I_AD_MailConfig.COLUMN_DocBaseType, docBaseType);
			}

			final String docSubType = docBaseAndSubType.getDocSubType();
			if (!Check.isEmpty(docSubType, true))
			{
				queryBuilder.addInArrayFilter(I_AD_MailConfig.COLUMN_DocSubType, docSubType, null);
			}

		}

		return queryBuilder.create().list();
	}
}
