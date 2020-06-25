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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Properties;

import de.metas.user.UserId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.api.impl.SingletonPrintingQueueSource;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Check;

/**
 * Process only selected {@link I_C_Printing_Queue} create corresponding {@link I_C_Print_Job}.
 *
 * NOTE: this process is only for testing. After printing selected queue item, it will mark it as not printed and no document outbound will be created.
 *
 * @author tsa
 *
 */
public class C_Print_Job_TestPrintQueueItem extends AbstractPrintJobCreate
{

	private int p_C_Printing_Queue_ID = -1;

	@Override
	protected void prepare()
	{
		if (I_C_Printing_Queue.Table_Name.equals(getTableName()))
		{
			p_C_Printing_Queue_ID = getRecord_ID();
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @TableName@: " + getTableName());
		}

		Check.assume(p_C_Printing_Queue_ID > 0, "C_Printing_Queue_ID specified");
	}

	@Override
	protected List<IPrintingQueueSource> createPrintingQueueSources(final Properties ctxToUse)
	{
		// out of transaction because methods which are polling the printing queue are working out of transaction
		final String trxName = ITrx.TRXNAME_None;

		final I_C_Printing_Queue item = InterfaceWrapperHelper.create(ctxToUse, p_C_Printing_Queue_ID, I_C_Printing_Queue.class, trxName);
		Check.assumeNotNull(item, "C_Printing_Queue for {} exists", p_C_Printing_Queue_ID);

		final UserId adUserToPrintId = Env.getLoggedUserId(ctxToUse); // logged in user

		final SingletonPrintingQueueSource queue = new SingletonPrintingQueueSource(item, adUserToPrintId);

		// 04015 : This is a test process. We shall not mark the item as printed
		queue.setPersistPrintedFlag(false);

		return ImmutableList.<IPrintingQueueSource> of(queue);
	}

	@Override
	protected int createSelection()
	{
		throw new IllegalStateException("Method createSelection() shall never been called because we provided an IPrintingQueueSource");
	}
}
