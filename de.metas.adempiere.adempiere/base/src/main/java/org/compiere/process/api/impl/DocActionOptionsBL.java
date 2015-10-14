package org.compiere.process.api.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_Cash;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Movement;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.process.api.IDocActionOptionsBL;
import org.compiere.process.api.IDocActionOptionsContext;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_HR_Process;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import de.metas.shipping.model.I_M_ShipperTransportation;

public class DocActionOptionsBL implements IDocActionOptionsBL
{
	@Override
	public int getDocActionIndex(final IDocActionOptionsContext optionsCtx)
	{
		final Properties ctx = optionsCtx.getCtx();

		//
		// FIXME replace fucking decorator pattern and index with proper pimping of the options list
		int index = getValidActions(optionsCtx);

		final int AD_Table_ID = optionsCtx.getAD_Table_ID();
		final I_AD_Table table = InterfaceWrapperHelper.create(ctx, AD_Table_ID, I_AD_Table.class, ITrx.TRXNAME_None);
		final PO po = InterfaceWrapperHelper.getPO(table);

		if (po instanceof DocOptions)
		{
			index = ((DocOptions)po).customizeValidActions(optionsCtx);
		}

		final int docTypeId = optionsCtx.getC_DocType_ID();
		if (docTypeId > 0)
		{
			final int adClientId = Env.getAD_Client_ID(ctx);
			final int adRoleId = Env.getAD_Role_ID(ctx);

			index = DocumentEngine.checkActionAccess(optionsCtx,
					adClientId,
					adRoleId);
		}
		return index;
	}

	/**
	 * Get list of valid document action into the options collection. Set default document action into the docAction array parameter.
	 *
	 * @param optionsCtx
	 * @return Number of valid options
	 */
	private int getValidActions(final IDocActionOptionsContext optionsCtx)
	{
		final Set<String> options = new LinkedHashSet<>(optionsCtx.getOptions());
		Check.assume(optionsCtx.getOptions().isEmpty(), "options not initialized");

		// Locked
		final boolean locked = optionsCtx.isProcessing();
		if (locked)
		{
			options.add(DocAction.ACTION_Unlock);
		}

		final int AD_Table_ID = optionsCtx.getAD_Table_ID();

		final String docStatus = optionsCtx.getDocStatus();

		// Approval required .. NA
		if (docStatus.equals(DocAction.STATUS_NotApproved))
		{
			options.add(DocAction.ACTION_Prepare);
			options.add(DocAction.ACTION_Void);
		}
		// Draft/Invalid .. DR/IN
		else if (docStatus.equals(DocAction.STATUS_Drafted)
				|| docStatus.equals(DocAction.STATUS_Invalid))
		{
			options.add(DocAction.ACTION_Complete);
			// options.add(DocumentEngine.ACTION_Prepare;
			options.add(DocAction.ACTION_Void);
		}
		// In Process .. IP
		else if (docStatus.equals(DocAction.STATUS_InProgress)
				|| docStatus.equals(DocAction.STATUS_Approved))
		{
			options.add(DocAction.ACTION_Complete);
			options.add(DocAction.ACTION_Void);
		}
		// Complete .. CO
		else if (docStatus.equals(DocAction.STATUS_Completed))
		{
			if (AD_Table_ID == I_M_InOut.Table_ID) // fresh 08656: Default action Re-Activate
			{
				options.add(DocAction.ACTION_ReActivate);
			}
			else
			{
				options.add(DocAction.ACTION_Close);
			}
		}
		// Waiting Payment
		else if (docStatus.equals(DocAction.STATUS_WaitingPayment)
				|| docStatus.equals(DocAction.STATUS_WaitingConfirmation))
		{
			options.add(DocAction.ACTION_Void);
			options.add(DocAction.ACTION_Prepare);
		}
		// Closed, Voided, REversed .. CL/VO/RE
		else if (docStatus.equals(DocAction.STATUS_Closed)
				|| docStatus.equals(DocAction.STATUS_Voided)
				|| docStatus.equals(DocAction.STATUS_Reversed))
		{
			return 0;
		}

		/********************
		 * Order
		 */
		if (AD_Table_ID == I_C_Order.Table_ID)
		{
			// Draft .. DR/IP/IN
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocAction.STATUS_InProgress)
					|| docStatus.equals(DocAction.STATUS_Invalid))
			{
				options.add(DocAction.ACTION_Prepare);
				options.add(DocAction.ACTION_Close);
				// Draft Sales Order Quote/Proposal - Process

				final String orderType = optionsCtx.getOrderType();
				if (optionsCtx.isSOTrx()
						&& ("OB".equals(orderType) || "ON".equals(orderType)))
				{
					optionsCtx.setDocActionToUse(DocAction.ACTION_Prepare);
				}
			}
			// Complete .. CO
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options.add(DocAction.ACTION_Void);
				options.add(DocAction.ACTION_ReActivate);
			}
			else if (docStatus.equals(DocAction.STATUS_WaitingPayment))
			{
				options.add(DocAction.ACTION_ReActivate);
				options.add(DocAction.ACTION_Close);
			}
		}
		/********************
		 * Material Shipment/Receipt
		 */
		else if (AD_Table_ID == I_M_InOut.Table_ID)
		{
			// Complete .. CO
			if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options.add(DocAction.ACTION_Reverse_Correct);
				options.add(DocAction.ACTION_Void);
				options.add(DocAction.ACTION_Close);
			}
		}
		/********************
		 * Invoice
		 */
		else if (AD_Table_ID == I_C_Invoice.Table_ID)
		{
			// Complete .. CO
			if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options.add(DocAction.ACTION_Void);
				options.add(DocAction.ACTION_Reverse_Correct);
			}
		}
		/********************
		 * Payment
		 */
		else if (AD_Table_ID == I_C_Payment.Table_ID)
		{
			// Complete .. CO
			if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options.add(DocAction.ACTION_Void);
				options.add(DocAction.ACTION_Reverse_Correct);
			}
		}
		/********************
		 * GL Journal
		 */
		else if (AD_Table_ID == I_GL_Journal.Table_ID || AD_Table_ID == I_GL_JournalBatch.Table_ID)
		{
			// Complete .. CO
			if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options.add(DocAction.ACTION_Reverse_Correct);
				options.add(DocAction.ACTION_Reverse_Accrual);
				options.add(DocAction.ACTION_ReActivate);
			}
		}
		/********************
		 * Allocation
		 */
		else if (AD_Table_ID == I_C_AllocationHdr.Table_ID)
		{
			// Complete .. CO
			if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options.add(DocAction.ACTION_Void);
				options.add(DocAction.ACTION_Reverse_Correct);
			}
		}
		// [ 1782412 ]
		/********************
		 * Cash
		 */
		else if (AD_Table_ID == I_C_Cash.Table_ID)
		{
			// Complete .. CO
			if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options.add(DocAction.ACTION_Void);
			}
		}
		/********************
		 * Bank Statement
		 */
		else if (AD_Table_ID == I_C_BankStatement.Table_ID)
		{
			// Complete .. CO
			if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options.add(DocAction.ACTION_Void);
			}
		}
		/********************
		 * Inventory Movement, Physical Inventory
		 */
		else if (AD_Table_ID == I_M_Movement.Table_ID
				|| AD_Table_ID == I_M_Inventory.Table_ID)
		{
			// Complete .. CO
			if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options.add(DocAction.ACTION_Void);
				options.add(DocAction.ACTION_Reverse_Correct);
			}
		}
		/********************
		 * Manufacturing Order
		 */
		else if (AD_Table_ID == Services.get(IADTableDAO.class).retrieveTableId(I_PP_Order.Table_Name))
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocAction.STATUS_InProgress)
					|| docStatus.equals(DocAction.STATUS_Invalid))
			{
				options.add(DocAction.ACTION_Prepare);
				options.add(DocAction.ACTION_Close);
			}
			// Complete .. CO
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options.add(DocAction.ACTION_Void);
				options.add(DocAction.ACTION_ReActivate);
			}
		}
		/********************
		 * Manufacturing Cost Collector
		 */
		else if (AD_Table_ID == Services.get(IADTableDAO.class).retrieveTableId(I_PP_Cost_Collector.Table_Name))
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocAction.STATUS_InProgress)
					|| docStatus.equals(DocAction.STATUS_Invalid))
			{
				options.add(DocAction.ACTION_Prepare);
				options.add(DocAction.ACTION_Close);
			}
			// Complete .. CO
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options.add(DocAction.ACTION_Void);
				options.add(DocAction.ACTION_Reverse_Correct);
			}
		}
		/********************
		 * Distribution Order
		 */
		else if (AD_Table_ID == Services.get(IADTableDAO.class).retrieveTableId(I_DD_Order.Table_Name))
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocAction.STATUS_InProgress)
					|| docStatus.equals(DocAction.STATUS_Invalid))
			{
				options.add(DocAction.ACTION_Prepare);
				options.add(DocAction.ACTION_Close);
			}
			// Complete .. CO
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options.add(DocAction.ACTION_Void);
				options.add(DocAction.ACTION_ReActivate);
			}
		}
		/********************
		 * Payroll Process
		 */
		else if (AD_Table_ID == I_HR_Process.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Drafted)
					|| docStatus.equals(DocAction.STATUS_InProgress)
					|| docStatus.equals(DocAction.STATUS_Invalid))
			{
				options.add(DocAction.ACTION_Prepare);
				options.add(DocAction.ACTION_Close);
			}
			// Complete .. CO
			else if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options.add(DocAction.ACTION_Void);
				options.add(DocAction.ACTION_ReActivate);
			}
		}
		// metas: (Task: us240) Shipping order needs to be reactivatable.
		/********************
		 * Shipping Order
		 */
		else if (AD_Table_ID == I_M_ShipperTransportation.Table_ID)
		{
			if (docStatus.equals(DocAction.STATUS_Completed))
			{
				options.add(DocAction.ACTION_ReActivate);
			}
		}
		// metas: end
		// metas us050: Allow to reactivate document by default (required for C_AdvcomDoc)
		else
		{
			options.add(DocAction.ACTION_ReActivate);
		}
		// metas: end

		//
		// Set configured options in the context
		optionsCtx.setOptions(options);

		final int index = options.size();
		return index;
	}
}
