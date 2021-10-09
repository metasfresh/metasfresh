package de.metas.document.engine.impl;

import java.util.LinkedHashSet;
import java.util.Set;

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
import org.compiere.model.X_C_DocType;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_HR_Process;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import com.google.common.collect.ImmutableSet;

import de.metas.document.engine.DocActionOptionsContext;
import de.metas.document.engine.IDocActionOptionsCustomizer;
import de.metas.document.engine.IDocument;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Check;

/*
 * #%L
 * de.metas.business
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

// @Component // shall not be discovered by spring context
/* package */final class DefaultDocActionOptionsCustomizer implements IDocActionOptionsCustomizer
{
	public static final transient DefaultDocActionOptionsCustomizer instance = new DefaultDocActionOptionsCustomizer();

	private DefaultDocActionOptionsCustomizer()
	{
	}

	@Override
	public String getAppliesToTableName()
	{
		return null; // ANY
	}

	@Override
	public void customizeValidActions(final DocActionOptionsContext optionsCtx)
	{
		final Set<String> docActions = new LinkedHashSet<>(optionsCtx.getDocActions());
		Check.assume(docActions.isEmpty(), "options not initialized");

		// Locked
		final boolean locked = optionsCtx.isProcessing();
		if (locked)
		{
			docActions.add(IDocument.ACTION_Unlock);
		}

		final String tableName = optionsCtx.getTableName();

		final String docStatus = optionsCtx.getDocStatus();

		// Approval required .. NA
		if (IDocument.STATUS_NotApproved.equals(docStatus))
		{
			docActions.add(IDocument.ACTION_Prepare);
			docActions.add(IDocument.ACTION_Void);
		}
		// Draft/Invalid .. DR/IN
		else if (IDocument.STATUS_Drafted.equals(docStatus)
				|| IDocument.STATUS_Invalid.equals(docStatus))
		{
			docActions.add(IDocument.ACTION_Complete);
			// options.add(DocumentEngine.ACTION_Prepare;
			docActions.add(IDocument.ACTION_Void);
		}
		// In Process .. IP
		else if (IDocument.STATUS_InProgress.equals(docStatus)
				|| IDocument.STATUS_Approved.equals(docStatus))
		{
			docActions.add(IDocument.ACTION_Complete);
			docActions.add(IDocument.ACTION_Void);
		}
		// Complete .. CO
		else if (IDocument.STATUS_Completed.equals(docStatus))
		{
			docActions.add(IDocument.ACTION_ReActivate);
			docActions.add(IDocument.ACTION_Close);
		}
		// Waiting Payment
		else if (IDocument.STATUS_WaitingPayment.equals(docStatus)
				|| IDocument.STATUS_WaitingConfirmation.equals(docStatus))
		{
			docActions.add(IDocument.ACTION_Void);
			docActions.add(IDocument.ACTION_Prepare);
		}
		// Closed, Voided, REversed .. CL/VO/RE
		else if (IDocument.STATUS_Closed.equals(docStatus)
				|| IDocument.STATUS_Voided.equals(docStatus)
				|| IDocument.STATUS_Reversed.equals(docStatus))
		{
			optionsCtx.setDocActions(ImmutableSet.of());
			return;
		}

		/********************
		 * Order
		 */
		if (I_C_Order.Table_Name.equals(tableName))
		{
			// Draft .. DR/IP/IN
			if (docStatus.equals(IDocument.STATUS_Drafted)
					|| docStatus.equals(IDocument.STATUS_InProgress)
					|| docStatus.equals(IDocument.STATUS_Invalid))
			{
				docActions.add(IDocument.ACTION_Prepare);
				docActions.add(IDocument.ACTION_Close);
				// Draft Sales Order Quote/Proposal - Process

				final String orderType = optionsCtx.getOrderType();
				if (optionsCtx.getSoTrx().isSales()
						&& (X_C_DocType.DOCSUBTYPE_Quotation.equals(orderType)
						|| X_C_DocType.DOCSUBTYPE_Proposal.equals(orderType)
						|| (X_C_DocType.DOCSUBTYPE_FrameAgrement.equals(orderType))))
				{
					optionsCtx.setDocActionToUse(IDocument.ACTION_Prepare);
				}
			}
			// Complete .. CO
			else if (docStatus.equals(IDocument.STATUS_Completed))
			{
				docActions.add(IDocument.ACTION_Void);
				docActions.add(IDocument.ACTION_ReActivate);
			}
			else if (docStatus.equals(IDocument.STATUS_WaitingPayment))
			{
				docActions.add(IDocument.ACTION_ReActivate);
				docActions.add(IDocument.ACTION_Close);
			}
		}
		/********************
		 * Material Shipment/Receipt
		 */
		else if (I_M_InOut.Table_Name.equals(tableName))
		{
			// Complete .. CO
			if (docStatus.equals(IDocument.STATUS_Completed))
			{
				docActions.add(IDocument.ACTION_Reverse_Correct);
				docActions.add(IDocument.ACTION_Void);
				docActions.add(IDocument.ACTION_Close);
			}
		}
		/********************
		 * Invoice
		 */
		else if (I_C_Invoice.Table_Name.equals(tableName))
		{
			// Complete .. CO
			if (docStatus.equals(IDocument.STATUS_Completed))
			{
				docActions.add(IDocument.ACTION_Void);
				docActions.add(IDocument.ACTION_Reverse_Correct);
			}
		}
		/********************
		 * Payment
		 */
		else if (I_C_Payment.Table_Name.equals(tableName))
		{
			// Complete .. CO
			if (docStatus.equals(IDocument.STATUS_Completed))
			{
				docActions.add(IDocument.ACTION_Void);
				docActions.add(IDocument.ACTION_Reverse_Correct);
			}
		}
		/********************
		 * GL Journal
		 */
		else if (I_GL_Journal.Table_Name.equals(tableName) || I_GL_JournalBatch.Table_Name.equals(tableName))
		{
			// Complete .. CO
			if (docStatus.equals(IDocument.STATUS_Completed))
			{
				docActions.add(IDocument.ACTION_Reverse_Correct);
				docActions.add(IDocument.ACTION_Reverse_Accrual);
				docActions.add(IDocument.ACTION_ReActivate);
			}
		}
		/********************
		 * Allocation
		 */
		else if (I_C_AllocationHdr.Table_Name.equals(tableName))
		{
			// Complete .. CO
			if (docStatus.equals(IDocument.STATUS_Completed))
			{
				docActions.add(IDocument.ACTION_Void);
				docActions.add(IDocument.ACTION_Reverse_Correct);
			}
		}
		// [ 1782412 ]
		/********************
		 * Cash
		 */
		else if (I_C_Cash.Table_Name.equals(tableName))
		{
			// Complete .. CO
			if (docStatus.equals(IDocument.STATUS_Completed))
			{
				docActions.add(IDocument.ACTION_Void);
			}
		}
		/********************
		 * Bank Statement
		 */
		else if (I_C_BankStatement.Table_Name.equals(tableName))
		{
			// Complete .. CO
			if (docStatus.equals(IDocument.STATUS_Completed))
			{
				docActions.add(IDocument.ACTION_Void);
			}
		}
		/********************
		 * Inventory Movement, Physical Inventory
		 */
		else if (I_M_Movement.Table_Name.equals(tableName)
				|| I_M_Inventory.Table_Name.equals(tableName))
		{
			// Complete .. CO
			if (docStatus.equals(IDocument.STATUS_Completed))
			{
				docActions.add(IDocument.ACTION_Void);
				docActions.add(IDocument.ACTION_Reverse_Correct);
			}
		}
		/********************
		 * Manufacturing Order
		 */
		else if (I_PP_Order.Table_Name.equals(tableName))
		{
			if (docStatus.equals(IDocument.STATUS_Drafted)
					|| docStatus.equals(IDocument.STATUS_InProgress)
					|| docStatus.equals(IDocument.STATUS_Invalid))
			{
				docActions.add(IDocument.ACTION_Prepare);
				docActions.add(IDocument.ACTION_Close);
			}
			// Complete .. CO
			else if (docStatus.equals(IDocument.STATUS_Completed))
			{
				docActions.add(IDocument.ACTION_Void);
				docActions.add(IDocument.ACTION_ReActivate);
			}
		}
		/********************
		 * Manufacturing Cost Collector
		 */
		else if (I_PP_Cost_Collector.Table_Name.equals(tableName))
		{
			if (docStatus.equals(IDocument.STATUS_Drafted)
					|| docStatus.equals(IDocument.STATUS_InProgress)
					|| docStatus.equals(IDocument.STATUS_Invalid))
			{
				docActions.add(IDocument.ACTION_Prepare);
				docActions.add(IDocument.ACTION_Close);
			}
			// Complete .. CO
			else if (docStatus.equals(IDocument.STATUS_Completed))
			{
				docActions.add(IDocument.ACTION_Void);
				docActions.add(IDocument.ACTION_Reverse_Correct);
			}
		}
		/********************
		 * Distribution Order
		 */
		else if (I_DD_Order.Table_Name.equals(tableName))
		{
			if (docStatus.equals(IDocument.STATUS_Drafted)
					|| docStatus.equals(IDocument.STATUS_InProgress)
					|| docStatus.equals(IDocument.STATUS_Invalid))
			{
				docActions.add(IDocument.ACTION_Prepare);
				docActions.add(IDocument.ACTION_Close);
			}
			// Complete .. CO
			else if (docStatus.equals(IDocument.STATUS_Completed))
			{
				docActions.add(IDocument.ACTION_Void);
				docActions.add(IDocument.ACTION_ReActivate);
			}
		}
		/********************
		 * Payroll Process
		 */
		else if (I_HR_Process.Table_Name.equals(tableName))
		{
			if (docStatus.equals(IDocument.STATUS_Drafted)
					|| docStatus.equals(IDocument.STATUS_InProgress)
					|| docStatus.equals(IDocument.STATUS_Invalid))
			{
				docActions.add(IDocument.ACTION_Prepare);
				docActions.add(IDocument.ACTION_Close);
			}
			// Complete .. CO
			else if (docStatus.equals(IDocument.STATUS_Completed))
			{
				docActions.add(IDocument.ACTION_Void);
				docActions.add(IDocument.ACTION_ReActivate);
			}
		}
		// metas: (Task: us240) Shipping order needs to be reactivatable.
		/********************
		 * Shipping Order
		 */
		else if (I_M_ShipperTransportation.Table_Name.equals(tableName))
		{
			if (docStatus.equals(IDocument.STATUS_Completed))
			{
				docActions.add(IDocument.ACTION_ReActivate);
			}
		}
		
		//
		// Set configured options in the context
		optionsCtx.setDocActions(ImmutableSet.copyOf(docActions));
	}
}
