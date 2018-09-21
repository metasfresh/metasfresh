package de.metas.document.archive.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getValueOrNull;

import java.util.Optional;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Component;

import de.metas.document.archive.DocOutBoundRecipient;
import de.metas.document.archive.DocOutBoundRecipientId;
import de.metas.document.archive.DocOutBoundRecipientRepository;
import de.metas.document.archive.DocOutboundLogMailRecipientProvider;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.email.IMailBL;
import de.metas.email.IMailBL.MailboxNotFoundException;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.email.Mailbox;
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

	public DefaultDocOutboundLogMailRecipientProvider(@NonNull final DocOutBoundRecipientRepository userRepository)
	{
		this.docOutBoundRecipientRepository = userRepository;
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
			return Optional.empty();
		}

		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final Mailbox mailbox = findMailboxOrNull(docOutboundLogRecord);
		if (mailbox == null)
		{
			return Optional.empty();
		}

		// check if the column for the user is specified
		if (!Check.isEmpty(mailbox.getColumnUserTo(), true))
		{
			final String tableName = adTableDAO.retrieveTableName(docOutboundLogRecord.getAD_Table_ID());
			final boolean existsColumn = tableName != null && adTableDAO.hasColumnName(tableName, mailbox.getColumnUserTo());

			if (existsColumn)
			{
				final IContextAware context = PlainContextAware.newWithThreadInheritedTrx();
				final Object referencedModel = TableRecordReference.ofReferenced(docOutboundLogRecord).getModel(context);

				// load the column content
				final Integer userRepoId = getValueOrNull(referencedModel, mailbox.getColumnUserTo());
				if (userRepoId == null)
				{
					return Optional.empty();
				}
				final DocOutBoundRecipientId docOutBoundRecipientId = DocOutBoundRecipientId.ofRepoId(userRepoId);
				final DocOutBoundRecipient user  = docOutBoundRecipientRepository.getById(docOutBoundRecipientId);
				if (Check.isEmpty(user.getEmailAddress(), true))
				{
					return Optional.empty();
				}
				return Optional.of(user);
			}
		}
		return Optional.empty();
	}

	private Mailbox findMailboxOrNull(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		try
		{
			final IMailBL mailBL = Services.get(IMailBL.class);

			final Mailbox mailbox = mailBL.findMailBox(
					docOutboundLogRecord.getAD_Client(),
					docOutboundLogRecord.getAD_Org_ID(),
					0, // don't filter by processID
					docOutboundLogRecord.getC_DocType(),
					null, // mailCustomType
					null // userFrom
			);
			return mailbox;
		}
		catch (final MailboxNotFoundException e)
		{
			Loggables.get().addLog("DefaultDocOutboundLogMailRecipientProvider - Unable to find a mailbox; exception message: {}", e.getMessage());
			return null;
		}
	}

}
