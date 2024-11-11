package de.metas.email.mailboxes;

import de.metas.cache.CCache;
import de.metas.document.DocBaseAndSubType;
import de.metas.email.EMailAddress;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.compiere.model.I_AD_MailBox;
import org.compiere.model.I_AD_MailConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, Mailbox> mailboxesById = CCache.<Integer, Mailbox> builder()
			.tableName(I_AD_MailBox.Table_Name)
			.build();

	@NonNull
	public Optional<Mailbox> findMailBox(@NonNull final MailboxQuery query)
	{
		for (final I_AD_MailConfig mailRouting : retrieveMailRoutings(query))
		{
			final OrgId configOrgId = OrgId.ofRepoIdOrAny(mailRouting.getAD_Org_ID());

			if (OrgId.equals(configOrgId, query.getOrgId()) || configOrgId.isAny())
			{
				final Mailbox mailbox = getMailbox(mailRouting);
				return Optional.of(mailbox);
			}
		}

		return Optional.empty();
	}

	private Mailbox getMailbox(final I_AD_MailConfig mailRouting)
	{
		return getById(mailRouting.getAD_MailBox_ID())
				.withUserToColumnName(mailRouting.getColumnUserTo());
	}

	private Mailbox getById(final int mailboxRepoId)
	{
		return mailboxesById.getOrLoad(mailboxRepoId, this::retrieveById);
	}

	private Mailbox retrieveById(final int mailboxRepoId)
	{
		Check.assumeGreaterThanZero(mailboxRepoId, "mailboxRepoId");

		final I_AD_MailBox mailboxRecord = loadOutOfTrx(mailboxRepoId, I_AD_MailBox.class);
		return toMailbox(mailboxRecord);
	}

	private static Mailbox toMailbox(final I_AD_MailBox record)
	{
		return Mailbox.builder()
				.smtpHost(record.getSMTPHost())
				.smtpPort(record.getSMTPPort())
				.startTLS(record.isStartTLS())
				.email(EMailAddress.ofString(record.getEMail()))
				.username(record.getUserName())
				.password(record.getPassword())
				.smtpAuthorization(record.isSmtpAuthorization())
				.sendEmailsFromServer(true)
				.userToColumnName(null)
				.build();
	}

	private List<I_AD_MailConfig> retrieveMailRoutings(@NonNull final MailboxQuery query)
	{
		final IQueryBuilder<I_AD_MailConfig> queryBuilder = queryBL.createQueryBuilderOutOfTrx(I_AD_MailConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_AD_Client_ID, query.getClientId());

		// Order by Org, Desc, Nulls Last
		queryBuilder.orderBy()
				.addColumn(I_AD_MailConfig.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_AD_MailConfig.COLUMN_DocSubType, Direction.Ascending, Nulls.Last)
				.endOrderBy();

		if (query.getCustomType() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_CustomType, query.getCustomType().getCode());
		}
		else if (query.getAdProcessId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_AD_Process_ID, query.getAdProcessId());
		}

		//
		// DocBaseType and DocSubType added in the mail config (task FRESH-203)
		final DocBaseAndSubType docBaseAndSubType = query.getDocBaseAndSubType();
		if (docBaseAndSubType != null)
		{
			queryBuilder.addEqualsFilter(I_AD_MailConfig.COLUMN_DocBaseType, docBaseAndSubType.getDocBaseType());

			final String docSubType = docBaseAndSubType.getDocSubType();
			if (!Check.isEmpty(docSubType, true))
			{
				queryBuilder.addInArrayFilter(I_AD_MailConfig.COLUMN_DocSubType, docSubType, null);
			}
		}

		return queryBuilder.create().list();
	}
}
