/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.ordercandidate.process;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.util.Services;

public class OLCandDeleteSelectedRecords extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private static final AdMessageKey PROCESSED_RECORDS_CANNOT_BE_DELETED = AdMessageKey.of("de.metas.ui.web.ordercandidate.process.PROCESSED_RECORDS_CANNOT_BE_DELETED");

	private final IOLCandDAO candDAO = Services.get(IOLCandDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (getSelectedRowIds().isAll())
		{
			return ProcessPreconditionsResolution.accept();
		}

		final boolean isProcessedRecordSelected = candDAO.isAnyRecordProcessed(getSelectedRowIds().toIds(OLCandId::ofRepoId));
		if (isProcessedRecordSelected)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(PROCESSED_RECORDS_CANNOT_BE_DELETED));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final int count = getSelectedRowIds().isAll()
				? candDAO.deleteUnprocessedRecords(getProcessInfo().getQueryFilterOrElseFalse())
				: candDAO.deleteRecords(getSelectedRowIds().toIds(OLCandId::ofRepoId));

		return "@Deleted@ #" + count;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		invalidateView();
	}
}
