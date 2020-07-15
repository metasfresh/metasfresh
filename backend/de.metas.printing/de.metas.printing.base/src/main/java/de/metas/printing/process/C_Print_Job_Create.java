/**
 *
 */
package de.metas.printing.process;

/*
 * #%L
 * de.metas.printing.base
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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.IQuery;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.security.permissions.Access;
import de.metas.util.Loggables;
import de.metas.util.Services;

/**
 * Process all {@link I_C_Printing_Queue}s from user selection and create corresponding {@link I_C_Print_Job}s
 *
 */
public class C_Print_Job_Create extends AbstractPrintJobCreate
{
	private static final Logger logger = LogManager.getLogger(C_Print_Job_Create.class);

	@Override
	protected int createSelection()
	{
		final IQuery<I_C_Printing_Queue> query = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Printing_Queue.class, ITrx.TRXNAME_None) // right now, PrintingQueueBL.createPrintingQueueSources(Properties, IPrintingQueueQuery) is also selecting out-of-trx
				.addOnlyActiveRecordsFilter()
				.filter(getProcessInfo().getQueryFilterOrElseFalse())
				.addEqualsFilter(I_C_Printing_Queue.COLUMNNAME_Processed, false)
				.create()
				.setRequiredAccess(Access.WRITE)
				.setClient_ID();
		final int count = query.createSelection(getPinstanceId());

		Loggables.withLogger(logger, Level.DEBUG).addLog("Created selection of {} C_Printing_Queue records; query={}", count, query);

		return count;
	}

}
