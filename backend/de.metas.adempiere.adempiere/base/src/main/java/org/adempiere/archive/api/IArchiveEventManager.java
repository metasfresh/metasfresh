package org.adempiere.archive.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.adempiere.archive.spi.IArchiveEventListener;
import org.compiere.model.I_AD_Archive;
import de.metas.email.EMailAddress;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;

/**
 * Can be called from different places to "inform" registered {@link IArchiveEventListener}s about events.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IArchiveEventManager extends ISingletonService
{
	String STATUS_MESSAGE_SENT = "MessageSent";
	String STATUS_MESSAGE_NOT_SENT = "MessageNotSent";

	String STATUS_Success = "Success";
	String STATUS_Failure = "Failure";

	/**
	 * To be used where you need to pass the "1 copy" parameter.
	 *
	 * NOTE: please use this constant instead of hardcoding the number "1" because in most of the cases, you are passing this because the copies is not available or is not implemented.
	 */
	int COPIES_ONE = 1;

	void registerArchiveEventListener(IArchiveEventListener listener);

	/**
	 * @param action the value given as action is stored in the respective <code>C_Doc_Outbound_Log_Line</code> column.
	 */
	void firePdfUpdate(I_AD_Archive archive, UserId userId, String action);

	void fireEmailSent(I_AD_Archive archive, String action, UserEMailConfig user, EMailAddress from, EMailAddress to, EMailAddress cc, EMailAddress bcc, String status);

	void firePrintOut(I_AD_Archive archive, UserId userId, String printerName, int copies, String status);

	/**
	 * To be invoked if the archive document is voided, reversed etc.
	 */
	void fireVoidDocument(I_AD_Archive archive);
}
