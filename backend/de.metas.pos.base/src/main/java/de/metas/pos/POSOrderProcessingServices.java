package de.metas.pos;

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.service.AsyncBatchService;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.ordercandidate.api.OLCandValidatorService;
import de.metas.payment.api.IPaymentBL;
import de.metas.pos.async.C_POSOrder_CreateInvoiceAndShipment;
import de.metas.salesorder.candidate.ProcessOLCandsWorkpackageEnqueuer;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import static de.metas.pos.async.C_POSOrder_CreateInvoiceAndShipment.WP_PARAM_POSOrderExternalId;

@Service
@RequiredArgsConstructor
public class POSOrderProcessingServices
{
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	@NonNull private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);
	@NonNull private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	@NonNull private final OLCandRepository olCandRepo;
	@NonNull private final OLCandValidatorService olCandValidatorService;
	@NonNull private final ProcessOLCandsWorkpackageEnqueuer processOLCandsWorkpackageEnqueuer;
	@NonNull private final AsyncBatchService asyncBatchService;
	@NonNull private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

	// public I_C_Order createSalesOrder(@NonNull final POSOrder posOrder)
	// {
	// 	final OrderFactory salesOrderFactory = OrderFactory.newSalesOrder()
	// 			.docType(posOrder.getSalesOrderDocTypeId())
	// 			.shipBPartner(posOrder.getShipToCustomerAndLocationId())
	// 			.datePromised(posOrder.getDate())
	// 			.pricingSystemId(posOrder.getPricingSystemAndListId());
	//
	// 	posOrder.getLines().forEach(line -> createOrderLine(salesOrderFactory, line));
	//
	// 	return salesOrderFactory.createAndComplete();
	// }
	//
	// private void createOrderLine(@NonNull final OrderFactory salesOrderFactory, @NonNull final POSOrderLine posLine)
	// {
	// 	salesOrderFactory.newOrderLine()
	// 			.productId(posLine.getProductId())
	// 			.addQty(posLine.getQty())
	// 			.manualPrice(posLine.getPrice());
	// }

	public void createPayments(@NonNull POSOrder posOrder)
	{
		posOrder.getPayments().forEach(posPayment -> createPayment(posPayment, posOrder));
	}

	public void createPayment(@NonNull final POSPayment posPayment, @NonNull POSOrder posOrder)
	{
		paymentBL.newInboundReceiptBuilder()
				.adOrgId(posOrder.getOrgId())
				.orgBankAccountId(posOrder.getCashbookId())
				.bpartnerId(posOrder.getShipToCustomerId())
				.payAmt(posPayment.getAmount())
				.currencyId(posOrder.getCurrencyId())
				.tenderType(posPayment.getPaymentMethod().getTenderType())
				.dateTrx(posOrder.getDate())
				// TODO: isAutoAllocateAvailableAmt(true) somehow set posOrderId so we know to allocate only to invoices for that POSOrder
				.createAndProcess();

		// TODO: add the payment to bank statement

		//return PaymentId.ofRepoId(payment.getC_Payment_ID());
	}

	public void scheduleCreateSalesOrderInvoiceAndShipment(@NonNull final POSOrder posOrder)
	{
		workPackageQueueFactory.getQueueForEnqueuing(Env.getCtx(), C_POSOrder_CreateInvoiceAndShipment.class)
				.newWorkPackage()
				.bindToThreadInheritedTrx()
				.parameter(WP_PARAM_POSOrderExternalId, posOrder.getExternalId().getAsString())
				.buildAndEnqueue();
	}

}
