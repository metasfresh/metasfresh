package de.metas.printing.spi.impl;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.google.common.base.Optional;

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
public final class DefaultPrintingNotificationCtxProvider implements INotificationCtxProvider
{
	public static final transient DefaultPrintingNotificationCtxProvider instance = new DefaultPrintingNotificationCtxProvider();
	
	public static final String MSG_CLIENT_REPORTS_PRINT_ERROR = "de.metas.printing.C_Print_Job_Instructions.ClientReportsPrintError";

	private DefaultPrintingNotificationCtxProvider()
	{
		super();
	}
	
	@Override
	public Optional<String> getTextMessageIfApplies(ITableRecordReference referencedRecord)
	{
		// the default printing ctx provider only applies to I_C_Print_Job_Instructions entries
		if (referencedRecord.getAD_Table_ID() != InterfaceWrapperHelper.getTableId(I_C_Print_Job_Instructions.class))
		{
			return Optional.absent();
		}
		
		final IContextAware context = new PlainContextAware(Env.getCtx());
		final I_C_Print_Job_Instructions printJobInstructions = referencedRecord.getModel(context, I_C_Print_Job_Instructions.class);
		return getTextMessage(printJobInstructions);
	}
	
	public Optional<String> getTextMessage(final I_C_Print_Job_Instructions printJobInstructions)
	{
		if (printJobInstructions == null)
		{
			// shall never happen
			return Optional.absent();
		}

		final String errorMsg = printJobInstructions.getErrorMsg();
		return Optional.of(Util.coalesce(errorMsg, ""));
	}
}
