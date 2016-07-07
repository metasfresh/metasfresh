package de.metas.printing.spi.impl;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.util.Env;

import de.metas.notification.spi.INotificationCtxProvider;
import de.metas.printing.model.I_C_Print_Job_Instructions;

/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * 
 * task 09833
 * Default ctx provider for printing info of C_PrintJobInstructions. (Fallback to the original print job instruction's error message, as it used to be before the ctx providers were added)
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DefaultPrintingNotificationCtxProvider implements INotificationCtxProvider
{
	public static final String MSG_CLIENT_REPORTS_PRINT_ERROR = "de.metas.printing.C_Print_Job_Instructions.ClientReportsPrintError";

	public DefaultPrintingNotificationCtxProvider()
	{
		super();
	}

	@Override
	public boolean appliesFor(final ITableRecordReference referencedRecord)
	{
		// the default printing ingo ctx provider only applies to I_C_Print_Job_Instructions entries
		if (referencedRecord.getAD_Table_ID() == InterfaceWrapperHelper.getTableId(I_C_Print_Job_Instructions.class))
		{
			return true;
		}

		return false;
	}

	@Override
	public String getTextMessageOrNull(final ITableRecordReference referencedRecord)
	{
		final IContextAware context = new PlainContextAware(Env.getCtx());

		final I_C_Print_Job_Instructions printJobInstructions = referencedRecord.getModel(context, I_C_Print_Job_Instructions.class);

		if (printJobInstructions == null)
		{
			// shall never happen
			return null;
		}

		// fallback to the original error message of the print job instructions. This will be the message of the note in case no specific
		// ctx providers for the referenced record were registered
		return printJobInstructions.getErrorMsg();
	}

	@Override
	public boolean isDefault()
	{
		// this will be used if no other specific ctx provider is registered for a certain reference record
		return true;
	}
}
