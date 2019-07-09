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

		final ClientId clientId = ClientId.ofRepoId(client.getAD_Client_ID());
		final List<I_AD_MailConfig> configs = retrieveMailConfigs(clientId, adProcessId, docBaseAndSubType, customType);

		for (final I_AD_MailConfig config : configs)
		{
			final OrgId configOrgId = OrgId.ofRepoIdOrAny(config.getAD_Org_ID());

			if (OrgId.equals(configOrgId, orgId) || configOrgId.isAny())
			{
				final I_AD_MailBox adMailbox = config.getAD_MailBox();
				final Mailbox mailbox = Mailbox.builder()
						.smtpHost(adMailbox.getSMTPHost())
						.smtpPort(adMailbox.getSMTPPort())
						.startTLS(adMailbox.isStartTLS())
						.email(EMailAddress.ofString(adMailbox.getEMail()))
						.username(adMailbox.getUserName())
						.password(adMailbox.getPassword())
						.smtpAuthorization(adMailbox.isSmtpAuthorization())
						.sendFromServer(client.isServerEMail())
						.columnUserTo(config.getColumnUserTo())
						.build();

				if (logger.isDebugEnabled())
				{
					logger.debug("Found: {} => {}", toString(config), mailbox);
				}

				return mailbox;
			}
		}

		final String smtpHost = client.getSMTPHost();
		if (Check.isEmpty(smtpHost, true))
		{
			final String messageString = StringUtils.formatMessage(
					"Mail System not configured. Please define some AD_MailConfig or set AD_Client.SMTPHost; "
							+ "AD_MailConfig search parameters: AD_Client_ID={}; AD_Org_ID={}; AD_Process_ID={}; C_DocType={}; CustomType={}",
					clientId, orgId, adProcessId, docBaseAndSubType, customType);

			throw new MailboxNotFoundException(TranslatableStrings.constant(messageString));
		}

		final Mailbox mailbox = Mailbox.builder()
				.smtpHost(smtpHost)
				.smtpPort(client.getSMTPPort())
				.startTLS(client.isStartTLS())
				.email(EMailAddress.ofNullableString(client.getRequestEMail()))
				.username(client.getRequestUser())
				.password(client.getRequestUserPW())
				.smtpAuthorization(client.isSmtpAuthorization())
				.sendFromServer(client.isServerEMail())
				.build();
		logger.debug("Fallback to AD_Client settings: {}", mailbox);
		return mailbox;
	}

	private static String toString(final I_AD_MailConfig config)
	{
		return config.getClass().getSimpleName() + "["
				+ "AD_Client_ID=" + config.getAD_Client_ID()
				+ ", AD_Org_ID=" + config.getAD_Org_ID()
				+ ", AD_Process_ID=" + config.getAD_Process_ID()
				+ ", CustomType=" + config.getCustomType()
				+ ", IsActive=" + config.isActive()
				+ ", AD_MailConfig_ID=" + config.getAD_MailConfig_ID()
				+ "]";
	}

	private List<I_AD_MailConfig> retrieveMailConfigs(
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
