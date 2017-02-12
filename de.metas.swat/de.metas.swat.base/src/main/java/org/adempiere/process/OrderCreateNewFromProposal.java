package org.adempiere.process;

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

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.CopyRecordSupport;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;
import de.metas.document.engine.IDocActionBL;

public final class OrderCreateNewFromProposal extends JavaProcess 
{
	private static final Logger log = LogManager.getLogger(OrderCreateNewFromProposal.class);

	private MOrder sourceOrder;

	private int newOrderDocType;

	private Timestamp newOrderDateOrdered;

	private String poReference;

	private boolean newOrderClompleteIt = false;



	@Override
	protected String doIt() throws Exception
	{


		final I_C_Order newOrder = InterfaceWrapperHelper.create(getCtx(), I_C_Order.class, get_TrxName());
		final PO to = InterfaceWrapperHelper.getPO(newOrder);
		PO.copyValues(sourceOrder, to, true);
		
		final MDocType docType = MDocType.get(Env.getCtx(), newOrderDocType);
		newOrder.setC_DocTypeTarget(docType);
		newOrder.setC_DocType(docType);
		
		if (newOrderDateOrdered != null)
		{
			newOrder.setDateOrdered(newOrderDateOrdered);
		}
		if (poReference != null)
		{
			newOrder.setPOReference(poReference);
		}
		
		newOrder.setRef_Proposal(sourceOrder);
		
		InterfaceWrapperHelper.save(newOrder);
		
		final CopyRecordSupport childCRS = CopyRecordFactory.getCopyRecordSupport(I_C_Order.Table_Name);
		childCRS.setGridTab(null);
		childCRS.setParentPO(to);
		childCRS.setParentID(to.get_ID());
		childCRS.setBase(true);
		childCRS.copyRecord(sourceOrder, get_TrxName());

		
		
		InterfaceWrapperHelper.save(newOrder);

		String docAction;
		if (newOrderClompleteIt)
		{
			docAction = DocAction.ACTION_Complete;
			Services.get(IDocActionBL.class).processEx(newOrder, DocAction.ACTION_Complete, DocAction.STATUS_Completed);
		}
		else
		{
			docAction = DocAction.ACTION_Prepare;
		}

		newOrder.setDocAction(docAction);

		
		InterfaceWrapperHelper.save(newOrder);
		
		
		sourceOrder.setRef_Order_ID(newOrder.getC_Order_ID());
		InterfaceWrapperHelper.save(sourceOrder, get_TrxName());

		return newOrder.getDocumentNo();
	}

	@Override
	protected void prepare()
	{

		sourceOrder = new MOrder(Env.getCtx(), getRecord_ID(), null);

		//
		// check if we are invoked with a legal doc type
		final MDocType sourceOrderDocType = MDocType.get(Env.getCtx(),
				sourceOrder.getC_DocTypeTarget_ID());

		if (!(MDocType.DOCBASETYPE_SalesOrder.equals(sourceOrderDocType
				.getDocBaseType()) //
		&& MDocType.DOCSUBTYPE_Proposal.equals(sourceOrderDocType
				.getDocSubType())//
		))
		{
			throw new IllegalStateException(
					"This process may be started for proposals only");
		}

		final ProcessInfoParameter[] para = getParametersAsArray();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
				// do nothing
			}
			else if (name.equals("C_DocType_ID"))
			{
				newOrderDocType = ((BigDecimal)para[i].getParameter())
						.intValue();
			}
			else if (name.equals("DateOrdered"))
			{
				newOrderDateOrdered = (Timestamp)para[i].getParameter();
			}
			else if (name.equals("DocumentNo"))
			{
				poReference = (String)para[i].getParameter();
			}
			else if (name.equals("CompleteIt"))
			{

				final String strCompleteIt = (String)para[i].getParameter();
				newOrderClompleteIt = ("Y".equals(strCompleteIt));
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}

}
