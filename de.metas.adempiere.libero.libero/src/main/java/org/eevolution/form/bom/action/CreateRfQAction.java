/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved. *
 * Contributor(s): Victor Perez www.e-evolution.com *
 *****************************************************************************/

package org.eevolution.form.bom.action;

import javax.swing.JFrame;
import javax.swing.JTree;

import org.eevolution.form.action.ProcessPopupAction;
import org.eevolution.model.reasoner.StorageReasoner;

/**
 * @author Victor Perez, e-Evolution, S.C. * @author Victor Perez, e-Evolution, S.C.
 *         Create Request for Quotation
 * 
 *         Creates an RfQ with its line/s from a bill of material with its line/s by tree selection.
 *         Checks the storage quantities of every line's product, dependent on its setted attribute
 *         set instance.
 * 
 *         AD_Process:
 *         INSERT INTO ad_process VALUES (1000100, 0, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100, '10000005', 'Create RfQ', NULL, NULL, '3', 'U', NULL, 'N', 'N', NULL, 'com.compiere.process.CreateBOM', 0, 0, NULL, NULL, NULL, 'N');
 * 
 *         AD_Process_Para:
 *         INSERT INTO ad_process_para VALUES (1000245, 0, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100, 'Sales Representative', NULL, NULL, 1000100, 20, 18, 190, NULL, 'SalesRep_ID', 'Y', 0, 'Y', 'N', NULL, NULL, NULL, NULL, NULL, 1063, 'U');
 *         INSERT INTO ad_process_para VALUES (1000246, 0, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100, 'RfQ Topic', NULL, NULL, 1000100, 10, 18, 1000041, NULL, 'C_RFQ_Topic_ID', 'Y', 0, 'Y', 'N', NULL, NULL, NULL, NULL, NULL, 2376, 'U');
 * 
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 */
public class CreateRfQAction extends ProcessPopupAction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8109415167393003666L;

	public static final String COMMAND = "createRfQ";

	// Hard coded process id. Expected process is 'Create RfQ'.
	final public static int PROCESS_ID = 1000100;

	protected JTree tree;
	protected StorageReasoner reasoner;

	/**
	 * Constructs a new Instance.
	 */
	public CreateRfQAction(JTree tree, JFrame window)
	{

		super(COMMAND, window);
		setActionCommand(COMMAND);

		this.tree = tree;
		this.reasoner = new StorageReasoner();
	}

	@Override
	protected String getCommand()
	{

		return COMMAND;
	}

	@Override
	protected int getProcessID()
	{

		return PROCESS_ID;
	}

	@Override
	protected String validateAction()
	{

		return null;
	}

	@Override
	protected void doProcess()
	{

		if (tree != null)
		{
			// createRfQ((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent());
			throw new UnsupportedOperationException("not implemented"); // FIXME: solve the RfQ dependency
		}
	}
	
	/*
	private void createRfQ(DefaultMutableTreeNode node)
	{
		BOMWrapper bom = (BOMWrapper)node.getUserObject();
		MPPOrder mo = new MPPOrder(Env.getCtx(), bom.getPP_Order_ID(), null);
		MResource r = MResource.get(Env.getCtx(), mo.getS_Resource_ID());

		// Calendar cal = Calendar.getInstance();

		final I_C_RfQ rfq = InterfaceWrapperHelper.create(Env.getCtx(), I_C_RfQ.class, ITrx.TRXNAME_None);
		rfq.setName(
				Services.get(IMsgBL.class).translate(Env.getCtx(), "C_RFQ_ID")
						+ ": "
						+ mo.getDocumentNo() + "_" + r.getName()
						+ " (" + bom.getName() + ")");

		rfq.setC_Currency_ID(Env.getContextAsInt(Env.getCtx(), "$C_Currency_ID"));
		rfq.setQuoteType(X_C_RfQ.QUOTETYPE_QuoteSelectedLines);
		rfq.setDateWorkStart(mo.getDateStartSchedule());
		rfq.setDateWorkComplete(mo.getDateFinishSchedule());

		// process parameters
		rfq.setC_RfQ_Topic_ID(((BigDecimal)getParameterValue("C_RFQ_Topic_ID")).intValue());
		rfq.setSalesRep_ID(((BigDecimal)getParameterValue("SalesRep_ID")).intValue());

		savePO(rfq);

		if (successful())
		{
			createRfQLines(rfq.getC_RfQ_ID(), node);
		}
	}

	private void createRfQLines(int rfqId, DefaultMutableTreeNode parent)
	{

		DefaultMutableTreeNode node = null;
		for (int i = 0; i < parent.getChildCount(); i++)
		{

			node = (DefaultMutableTreeNode)parent.getChildAt(i);

			if (node.isLeaf())
			{

				createRfQLine(rfqId, node);
			}
			else
			{

				createRfQLines(rfqId, node);
			}

			if (!successful())
			{

				break;
			}
		}
	}

	private void createRfQLine(int rfqId, DefaultMutableTreeNode node)
	{

		BOMLineWrapper sourceLine = (BOMLineWrapper)node.getUserObject();

		BigDecimal qtyReq = reasoner.getSumQtyRequired(sourceLine);

		// No requirement qty
		if (qtyReq.compareTo(BigDecimal.ZERO) <= 0)
		{

			return;
		}

		final I_C_RfQ rfq = InterfaceWrapperHelper.create(Env.getCtx(), rfqId, I_C_RfQ.class, ITrx.TRXNAME_None);
		final I_C_RfQLine targetLine = InterfaceWrapperHelper.create(Env.getCtx(), I_C_RfQLine.class, ITrx.TRXNAME_None);

		targetLine.setC_RfQ_ID(rfqId);
		targetLine.setLine(lineCount(rfqId) + 1);
		targetLine.setM_AttributeSetInstance_ID(sourceLine.getM_AttributeSetInstance_ID());
		targetLine.setM_Product_ID(sourceLine.getM_Product_ID());
		targetLine.setDateWorkStart(rfq.getDateWorkStart());
		targetLine.setDateWorkComplete(rfq.getDateWorkComplete());

		savePO(targetLine);

		if (!successful())
		{

			return;
		}

		final I_C_RfQLineQty lineQty = InterfaceWrapperHelper.create(Env.getCtx(), I_C_RfQLineQty.class, ITrx.TRXNAME_None);

		// lineQty.setQty(reasoner.calculateRequiredProducts(sourceLine));
		lineQty.setQty(qtyReq);

		lineQty.setC_UOM_ID(sourceLine.getC_UOM_ID());
		lineQty.setC_RfQLine(targetLine);
		lineQty.setIsRfQQty(true);
		lineQty.setIsPurchaseQty(true);

		savePO(lineQty);
	}

	private int lineCount(int rfqId)
	{
		final I_C_RfQ rfq = InterfaceWrapperHelper.create(Env.getCtx(), rfqId, I_C_RfQ.class, ITrx.TRXNAME_None);
		return Services.get(IRfqDAO.class).retrieveLines(rfq).size();
	}
	*/
}
