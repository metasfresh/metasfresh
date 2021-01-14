package org.adempiere.process;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.references.RecordZoomWindowFinder;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.PO;
import org.compiere.model.X_C_Order;

import java.sql.Timestamp;
import java.util.Optional;

public final class OrderCreateNewFromProposal extends JavaProcess implements IProcessPrecondition
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	@Param(parameterName = "C_DocType_ID", mandatory = true)
	private DocTypeId newOrderDocTypeId;

	@Param(parameterName = "DateOrdered")
	private Timestamp newOrderDateOrdered;

	@Param(parameterName = "DocumentNo")
	private String poReference;

	@Param(parameterName = "CompleteIt")
	private boolean completeIt;

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

		final OrderId orderId = OrderId.ofRepoId(context.getSingleSelectedRecordId());
		final I_C_Order order = orderDAO.getById(orderId);
		return checkPreconditionsApplicable(order);
	}

	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull I_C_Order order)
	{
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

	@Override
	protected String doIt()
	{
		final OrderId fromProposalId = OrderId.ofRepoId(getRecord_ID());
		final I_C_Order fromProposal = orderBL.getById(fromProposalId);
		checkPreconditionsApplicable(fromProposal).throwExceptionIfRejected();

		final I_C_Order newSalesOrder = copyProposalHeader(fromProposal);
		copyProposalLines(fromProposal, newSalesOrder);
		completeSalesOrderIfNeeded(newSalesOrder);

		openOrder(newSalesOrder);

		return newSalesOrder.getDocumentNo();
	}

	private I_C_Order copyProposalHeader(@NonNull final I_C_Order fromProposal)
	{
		final I_C_Order newSalesOrder = InterfaceWrapperHelper.copy()
				.setFrom(fromProposal)
				.setSkipCalculatedColumns(true)
				.copyToNew(I_C_Order.class);

		orderBL.setDocTypeTargetIdAndUpdateDescription(newSalesOrder, newOrderDocTypeId);
		newSalesOrder.setC_DocType_ID(newOrderDocTypeId.getRepoId());

		if (newOrderDateOrdered != null)
		{
			newSalesOrder.setDateOrdered(newOrderDateOrdered);
		}
		if (!Check.isBlank(poReference))
		{
			newSalesOrder.setPOReference(poReference);
		}

		newSalesOrder.setDatePromised(fromProposal.getDatePromised());
		newSalesOrder.setPreparationDate(fromProposal.getPreparationDate());
		newSalesOrder.setDocStatus(DocStatus.Drafted.getCode());
		newSalesOrder.setDocAction(X_C_Order.DOCACTION_Complete);
		newSalesOrder.setRef_Proposal_ID(fromProposal.getC_Order_ID());
		orderDAO.save(newSalesOrder);

		fromProposal.setRef_Order_ID(newSalesOrder.getC_Order_ID());
		orderDAO.save(fromProposal);

		return newSalesOrder;
	}

	private void copyProposalLines(
			@NonNull final I_C_Order fromProposal,
			@NonNull final I_C_Order newSalesOrder)
	{
		CopyRecordFactory.getCopyRecordSupport(I_C_Order.Table_Name)
				.setParentPO(InterfaceWrapperHelper.getPO(newSalesOrder))
				.addChildRecordCopiedListener(this::onRecordCopied)
				.copyRecord(InterfaceWrapperHelper.getPO(fromProposal), ITrx.TRXNAME_ThreadInherited);
	}

	private void onRecordCopied(@NonNull final PO to, @NonNull final PO from)
	{
		if (InterfaceWrapperHelper.isInstanceOf(to, I_C_OrderLine.class)
				&& InterfaceWrapperHelper.isInstanceOf(from, I_C_OrderLine.class))
		{
			final I_C_OrderLine newSalesOrderLine = InterfaceWrapperHelper.create(to, I_C_OrderLine.class);
			final I_C_OrderLine fromProposalLine = InterfaceWrapperHelper.create(from, I_C_OrderLine.class);

			newSalesOrderLine.setRef_ProposalLine_ID(fromProposalLine.getC_OrderLine_ID());
			orderDAO.save(newSalesOrderLine);
		}
	}

	// private void copyProposalLine(
	// 		@NonNull final I_C_OrderLine fromProposalLine,
	// 		@NonNull final OrderId newSalesOrderId)
	// {
	// 	if (!MOrderLinePOCopyRecordSupport.isCopyRecord(fromProposalLine))
	// 	{
	// 		return;
	// 	}
	//
	// 	final I_C_OrderLine newSalesOrderLine = InterfaceWrapperHelper.copy()
	// 			.setFrom(fromProposalLine)
	// 			.setSkipCalculatedColumns(true)
	// 			.copyToNew(I_C_OrderLine.class);
	// 	newSalesOrderLine.setC_Order_ID(newSalesOrderId.getRepoId());
	// 	newSalesOrderLine.setRef_ProposalLine_ID(fromProposalLine.getC_OrderLine_ID());
	// 	orderDAO.save(newSalesOrderLine);
	//
	// 	fromProposalLine.setRef_OrderLine_ID(newSalesOrderLine.getC_OrderLine_ID());
	// 	orderDAO.save(fromProposalLine);
	// }

	private void completeSalesOrderIfNeeded(final I_C_Order newSalesOrder)
	{
		if (completeIt)
		{
			documentBL.processEx(newSalesOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}
		else
		{
			newSalesOrder.setDocAction(IDocument.ACTION_Prepare);
			orderDAO.save(newSalesOrder);
		}
	}

	private void openOrder(@NonNull final I_C_Order order)
	{
		final AdWindowId orderWindowId = RecordZoomWindowFinder
				.findAdWindowId(TableRecordReference.of(order))
				.orElse(null);

		if (orderWindowId == null)
		{
			log.warn("Skip opening {} because no window found for it", order);
			return;
		}

		getResult().setRecordToOpen(
				TableRecordReference.of(order),
				orderWindowId.getRepoId(),
				ProcessExecutionResult.RecordsToOpen.OpenTarget.SingleDocument,
				ProcessExecutionResult.RecordsToOpen.TargetTab.SAME_TAB);
	}
}
