package org.adempiere.process;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.references.RecordZoomWindowFinder;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.CopyRecordSupport;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.PO;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.util.Optional;

public final class OrderCreateNewFromProposal extends JavaProcess implements IProcessPrecondition
{
	private static final Logger log = LogManager.getLogger(OrderCreateNewFromProposal.class);
	private final transient IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final Optional<AdWindowId> orderWindowId = RecordZoomWindowFinder.findAdWindowId(I_C_Order.Table_Name);

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

		newOrder.setDatePromised(sourceOrder.getDatePromised());
		newOrder.setPreparationDate(sourceOrder.getPreparationDate());
		newOrder.setDocStatus(DocStatus.Drafted.getCode());
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

		getResult().setRecordToOpen(
				TableRecordReference.of(newOrder),
				orderWindowId.get().getRepoId(), // adWindowId
				ProcessExecutionResult.RecordsToOpen.OpenTarget.SingleDocument,
				ProcessExecutionResult.RecordsToOpen.TargetTab.SAME_TAB
		);

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

		for (ProcessInfoParameter element : para)
		{
			String name = element.getParameterName();
			if (element.getParameter() == null)
			{
				// do nothing
			}
			else if (name.equals("C_DocType_ID"))
			{
				newOrderDocTypeId = element.getParameterAsInt();
			}
			else if (name.equals("DateOrdered"))
			{
				newOrderDateOrdered = element.getParameterAsTimestamp();
			}
			else if (name.equals("DocumentNo"))
			{
				poReference = element.getParameterAsString();
			}
			else if (name.equals("CompleteIt"))
			{
				newOrderClompleteIt = element.getParameterAsBoolean();
			}
			else
			{
				log.error("Unknown Parameter: {}", name);
			}
		}
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{

		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final int orderId = context.getSingleSelectedRecordId();
		final I_C_Order order = orderDAO.getById(OrderId.ofRepoId(orderId));

		final DocStatus quotationDocStatus = DocStatus.ofNullableCodeOrUnknown(order.getDocStatus());
		if (!quotationDocStatus.isCompleted())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a completed quotation");
		}

		final Optional<DocTypeId> docTypeId = DocTypeId.optionalOfRepoId(order.getC_DocTypeTarget_ID());
		if (docTypeId.isPresent())
		{
			final boolean isSalesProposalOrQuotation = docTypeBL.isSalesProposalOrQuotation(docTypeId.get());
			if (!isSalesProposalOrQuotation)
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason("is not sales proposal or quotation");
			}
		}
		else
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no C_DocTypeTarget_ID");

		}

		return ProcessPreconditionsResolution.accept();
	}
}
