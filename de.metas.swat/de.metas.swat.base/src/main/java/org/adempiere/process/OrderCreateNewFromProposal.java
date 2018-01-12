package org.adempiere.process;

import java.sql.Timestamp;

import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.CopyRecordSupport;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.PO;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;

public final class OrderCreateNewFromProposal extends JavaProcess 
{
	private static final Logger log = LogManager.getLogger(OrderCreateNewFromProposal.class);
	private final transient IOrderBL orderBL = Services.get(IOrderBL.class);

	private MOrder sourceOrder;

	private int newOrderDocTypeId;

	private Timestamp newOrderDateOrdered;

	private String poReference;

	private boolean newOrderClompleteIt = false;



	@Override
	protected String doIt() throws Exception
	{


		final I_C_Order newOrder = InterfaceWrapperHelper.create(getCtx(), I_C_Order.class, get_TrxName());
		final PO to = InterfaceWrapperHelper.getPO(newOrder);
		PO.copyValues(sourceOrder, to, true);
		
		orderBL.setDocTypeTargetIdAndUpdateDescription(newOrder, newOrderDocTypeId);
		newOrder.setC_DocType_ID(newOrderDocTypeId);
		
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
		childCRS.setParentPO(to);
		childCRS.setBase(true);
		childCRS.copyRecord(sourceOrder, get_TrxName());

		
		newOrder.setDocStatus(X_C_Order.DOCSTATUS_Drafted);
		newOrder.setDocAction(X_C_Order.DOCACTION_Complete);
		InterfaceWrapperHelper.save(newOrder);

		String docAction;
		if (newOrderClompleteIt)
		{
			docAction = IDocument.ACTION_Complete;
			Services.get(IDocumentBL.class).processEx(newOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}
		else
		{
			docAction = IDocument.ACTION_Prepare;
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
				newOrderDocTypeId = para[i].getParameterAsInt();
			}
			else if (name.equals("DateOrdered"))
			{
				newOrderDateOrdered = para[i].getParameterAsTimestamp();
			}
			else if (name.equals("DocumentNo"))
			{
				poReference = para[i].getParameterAsString();
			}
			else if (name.equals("CompleteIt"))
			{
				newOrderClompleteIt = para[i].getParameterAsBoolean();
			}
			else
			{
				log.error("Unknown Parameter: {}", name);
			}
		}
	}

}
