package org.adempiere.archive.spi;

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

import org.adempiere.archive.api.IArchiveEventManager;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_User;

/**
 * Implementors can be registered to {@link IArchiveEventManager#registerArchiveEventListener(IArchiveEventListener)} and can then be fired using that manager.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IArchiveEventListener
{
	void onPdfUpdate(I_AD_Archive archive, I_AD_User user, String action);

	void onEmailSent(I_AD_Archive archive, String action, I_AD_User user, String from, String to, String cc, String bcc, String status);

	void onPrintOut(I_AD_Archive archive, I_AD_User user, String printerName, int copies, String status);

	void onVoidDocument(I_AD_Archive archive);
}
