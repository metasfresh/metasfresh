package de.metas.fresh.printing.spi.impl;

import java.util.Properties;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_Order;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.google.common.base.Optional;

import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.printing.model.I_C_Print_Job_Instructions_v;
import de.metas.notification.spi.INotificationCtxProvider;
import de.metas.printing.api.IPrintJobDAO;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.spi.impl.DefaultPrintingNotificationCtxProvider;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 * 
 *         task 09833
 *         Implementation of the notification ctx provider for C_Order_MFGWarehouse_Report.
 *         In this case, the referencedRecord will be an instance of <code>AD_Archive</code> that points to a <code>I_C_Order_MFGWarehouse_Report</code> entry
 *
 */
public final class C_Order_MFGWarehouse_Report_NotificationCtxProvider implements INotificationCtxProvider
{
	public static final transient C_Order_MFGWarehouse_Report_NotificationCtxProvider instance = new C_Order_MFGWarehouse_Report_NotificationCtxProvider();
	
	private static final String MSG_PrintingInfo_MFGWarehouse_Report = "de.metas.printing.C_Print_Job_Instructions.C_Order_MFGWarehouse_Report_Error";
	
	private C_Order_MFGWarehouse_Report_NotificationCtxProvider()
	{
		super();
	}

	@Override
	public Optional<String> getTextMessageIfApplies(ITableRecordReference referencedRecord)
	{
		if (referencedRecord.getAD_Table_ID() != InterfaceWrapperHelper.getTableId(I_C_Print_Job_Instructions.class))
		{
			return Optional.absent();
		}
		final IContextAware context = new PlainContextAware(Env.getCtx());

		// the reference record must be a I_C_PrintJobInstructions entry
		final I_C_Print_Job_Instructions printJobInstructions = referencedRecord.getModel(context, I_C_Print_Job_Instructions.class);

		final I_C_Print_Job_Instructions_v printingInfo = InterfaceWrapperHelper.create(
				Services.get(IPrintJobDAO.class).retrieveC_Print_Job_Instructions_Info(printJobInstructions),
				I_C_Print_Job_Instructions_v.class);
		if (printingInfo == null)
		{
			return Optional.absent();
		}

		// an archive must exist in the C_Print_Job_Instructions_v view linked with this C_Print_Job_Instructions entry
		final I_AD_Archive archive = printingInfo.getAD_Archive();
		if (archive == null)
		{
			return Optional.absent();
		}

		// the archive must point to the C_Order_MFGWarehouse_Report table
		if (archive.getAD_Table_ID() != InterfaceWrapperHelper.getTableId(I_C_Order_MFGWarehouse_Report.class))
		{
			return Optional.absent();
		}

		final I_C_Order_MFGWarehouse_Report mfgWarehouseReport = printingInfo.getC_Order_MFGWarehouse_Report();

		// in case there is no I_C_Order_MFGWarehouse_Report with the ID given by AD_Archive's Record_ID ( shall never happen),
		// just display the original error message from the print job instructions
		if (mfgWarehouseReport == null)
		{
			return DefaultPrintingNotificationCtxProvider.instance.getTextMessage(printJobInstructions);
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(printJobInstructions);

		final I_C_Order order = printingInfo.getC_Order();
		final String orderDocNo = order == null ? "" : order.getDocumentNo();

		final StringBuilder mfgWarehouseReportID = new StringBuilder();
		mfgWarehouseReportID.append(mfgWarehouseReport.getC_Order_MFGWarehouse_Report_ID());

		final String textMessge = Services.get(IMsgBL.class).getMsg(ctx, MSG_PrintingInfo_MFGWarehouse_Report, new Object[] { orderDocNo, mfgWarehouseReportID.toString() });

		return Optional.of(Util.coalesce(textMessge, ""));
	}
}
