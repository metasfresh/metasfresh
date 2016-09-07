package de.metas.adempiere.process;

/*
 * #%L
 * de.metas.swat.base
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


import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Order;
import org.compiere.model.MPackage;
import org.compiere.model.MSysConfig;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.service.IPackageInfoService;
import de.metas.adempiere.service.IPackagingBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public class PerformPackaging extends SvrProcess
{

	@SuppressWarnings("unchecked")
	@Override
	protected String doIt() throws Exception
	{
		final Timestamp startTime = SystemTime.asTimestamp();

		final ProcessInfoParameter[] params = getProcessInfo().getParameter();
		if (params == null || params.length != 3)
		{
			throw new AdempiereException("Invalid process info params");
		}

		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		
		try
		{
			// update the
			shipmentSchedulePA.updateInOutGentColumnPInstanceId(getAD_PInstance_ID(), getAD_User_ID(), null);

			final DefaultMutableTreeNode root = (DefaultMutableTreeNode)params[0].getParameter();

			final int shipperId = (Integer)params[1].getParameter();

			final List<I_M_ShipmentSchedule> nonItems = (List<I_M_ShipmentSchedule>)params[2].getParameter();

			final Map<I_M_InOut, Collection<MPackage>> result;
			try
			{
				final IPackagingBL packagingBL = Services.get(IPackagingBL.class);
				result = packagingBL.createInOutAndPackages(getCtx(), root, shipperId, nonItems, get_TrxName());
			}
			catch (final AdempiereException e)
			{
				addLog(e.getMessage());
				return "@Error@";
			}

			Trx.get(get_TrxName(), false).commit(true);

			int packageCounter = 0;

			
			final String allowInvoiceAtPacking = MSysConfig.getValue("ALLOW_GENERATE_INVOICE_AT_PACKING", "YES"); //01358
			IClientUI factory = Services.get(IClientUI.class);// 01358
			for (final I_M_InOut inOut : result.keySet())
			{
				// TODO -> AD_Message
				addLog(inOut.getM_InOut_ID(), SystemTime.asTimestamp(), null, "@Created@: Lieferung " + inOut.getDocumentNo());

				// metas-ts: printing the packages as as soon as possible
				final Collection<MPackage> packages = result.get(inOut);
				for (final MPackage pack : packages)
				{
					addLog(pack.getM_Package_ID(), SystemTime.asTimestamp(), null, "@Created@: @M_Package_ID@ " + pack.getDocumentNo());
					packageCounter++;
					// TODO: print the packages, if there is more than one
				}
				
				final I_C_Order order = InterfaceWrapperHelper.create(inOut.getC_Order(), I_C_Order.class);
				
				final boolean generateInvoice;
				if (order.getBill_Location_ID() != inOut.getC_BPartner_Location_ID())
				{
					generateInvoice = false;
				}
				else
				{
					if ("NO".equalsIgnoreCase(allowInvoiceAtPacking))
					{
						generateInvoice = false;
					}
					else if ("YES".equalsIgnoreCase(allowInvoiceAtPacking))
					{
						generateInvoice = true;
					}
					else
					{
						Check.assume("ASK".equalsIgnoreCase(allowInvoiceAtPacking),
								"AD_SysConfig value for " + "ALLOW_GENERATE_INVOICE_AT_PACKING" + " should be one of YES, NO or ASK, but is: " + allowInvoiceAtPacking);
						generateInvoice = factory.ask(0, "gen.invoice"); // 01358
					}
				}
				if (generateInvoice)
				{
					final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
					final org.compiere.model.I_C_Invoice invoice = invoiceBL.createAndCompleteForInOut(inOut, inOut.getMovementDate(), get_TrxName());

					// TODO -> AD_Message
					addLog(invoice.getC_Invoice_ID(), SystemTime.asTimestamp(), null, "@Created@: Rechnung " + invoice.getDocumentNo());

					// need to commit our trx now, otherwise the print engine
					// won't find the new invoice
					Trx.get(get_TrxName(), false).commit(true);

					printInvoice(InterfaceWrapperHelper.create(invoice, I_C_Invoice.class));
				}
				else
				{
					// need to commit our trx now, otherwise the print engine
					// won't find our new inOut
					Trx.get(get_TrxName(), false).commit(true);
					printInOut(inOut);
				}
			
				if (!packages.isEmpty())
				{
					final IPackageInfoService packageInfoService = Services.get(IPackageInfoService.class);
					packageInfoService.createLabel(getCtx(), inOut, null, null, get_TrxName());
					printLabel(inOut, packageCounter);
				}
			
			}
		}
		finally
		{
			shipmentSchedulePA.markLocksForShipmentRunProcessed(getAD_PInstance_ID(), getAD_User_ID(), null);
		}
		return "@Success@ in " + TimeUtil.formatElapsed(startTime);
	}

	@Override
	protected void prepare()
	{
		// nothing to do
	}

	private void printInOut(final I_M_InOut inout)
	{
		boolean printSuccess = false;

		try
		{
			printSuccess = ReportCtl.startDocumentPrint(ReportEngine.SHIPMENT, inout.getM_InOut_ID(), null, 0, true);
		}
		catch (final Exception e)
		{
			final String msg = "Error printing the report - " + e.getMessage();
			throw new AdempiereException(msg, e);
		}
		
		if (printSuccess)
		{
			addLog(inout.getM_InOut_ID(), inout.getMovementDate(), null, "@Printed@: Lieferschein " + inout.getDocumentNo());
		}
	}

	private void printInvoice(final I_C_Invoice invoice)
	{
		
		boolean printSuccess = false;

		try
		{
			printSuccess = ReportCtl.startDocumentPrint(ReportEngine.INVOICE, invoice.getC_Invoice_ID(), null, 0, true);
		}
		catch (final Exception e)
		{
			final String msg = "Error printing the report - " + e.getMessage();
			throw new AdempiereException(msg, e);
		}
		
		if (printSuccess)
		{
			addLog(invoice.getC_Invoice_ID(), invoice.getDateInvoiced(), null, "@Printed@: Rechnung " + invoice.getDocumentNo());
		}
	}
	
	
	private void printLabel(final I_M_InOut inOut, final int packageCount)
	{
		// print package label (currently only DPD)
		final IPackageInfoService packageInfoService = Services.get(IPackageInfoService.class);
		
		boolean printSuccess = false;
		
		try
		{
			printSuccess = packageInfoService.printLabel(getCtx(), inOut, null, null, get_TrxName());
		}
		catch (final Exception e)
		{
			final String msg = "Error printing the report - " + e.getMessage();
			throw new AdempiereException(msg, e);
		}

		if (printSuccess)
		{
			addLog(inOut.getM_InOut_ID(), inOut.getDateAcct(),  null, "@Printed@: " + packageCount + " @M_Package_ID@ for @M_InOut_ID@ " + inOut.getDocumentNo());
		}
	}
}
