package de.metas.contracts.interceptor;

import de.metas.adempiere.model.I_C_Order;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateBL.ContractExtendingRequest;
import de.metas.contracts.impl.CustomerRetentionRepository;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.i18n.AdMessageKey;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	private static final Logger logger = LogManager.getLogger(C_Order.class);

	private static final AdMessageKey MSG_ORDER_DATE_ORDERED_CHANGE_FORBIDDEN_1P = AdMessageKey.of("Order_DateOrdered_Change_Forbidden");
	
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@ModelChange( //
			timings = ModelValidator.TYPE_BEFORE_CHANGE, //
			ifColumnsChanged = I_C_Order.COLUMNNAME_DateOrdered)
	public void updateDataEntry(@NonNull final I_C_Order order)
	{
		final IOrderDAO orderDAO = this.orderDAO;
		final IInvoiceCandDAO invoiceCandDB = Services.get(IInvoiceCandDAO.class);

		for (final I_C_OrderLine ol : orderDAO.retrieveOrderLines(order, I_C_OrderLine.class))
		{
			for (final I_C_Invoice_Candidate icOfOl : invoiceCandDB.retrieveReferencing(TableRecordReference.of(ol)))
			{
				if (icOfOl.isToClear())
				{
					throw new AdempiereException(MSG_ORDER_DATE_ORDERED_CHANGE_FORBIDDEN_1P, ol.getLine());
				}
			}
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void handleComplete(@NonNull final I_C_Order order)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoId(order.getC_DocType_ID());
		if (docTypeBL.isSalesProposalOrQuotation(docTypeId))
		{
			return;
		}

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order, I_C_OrderLine.class);
		for (final I_C_OrderLine ol : orderLines)
		{
			if (!subscriptionBL.isSubscription(ol))
			{
				logger.debug("Order line " + ol + " has no subscription");
				continue;
			}
			handleSubscriptionOrderLine(ol);
		}
	}

	private void handleSubscriptionOrderLine(@NonNull final I_C_OrderLine ol)
	{
		if (flatrateBL.existsTermForOrderLine(ol))
		{
			logger.debug("{} is already already referenced by a C_Flatrate_Term record; -> nothing to do", ol);
			return;
		}

		logger.info("Creating new {} entry", I_C_Flatrate_Term.Table_Name);

		final boolean completeIt = true;
		final I_C_Flatrate_Term newSc = subscriptionBL.createSubscriptionTerm(ol, completeIt);

		Check.assume(
				X_C_Flatrate_Term.DOCSTATUS_Completed.equals(newSc.getDocStatus()),
				"{} has DocStatus={}", newSc, newSc.getDocStatus());
		logger.info("Created and completed {}", newSc);

		extendFlatrateTermIfAutoExtension(newSc);
	}

	private void extendFlatrateTermIfAutoExtension(final I_C_Flatrate_Term term)
	{
		final I_C_Flatrate_Conditions conditions = term.getC_Flatrate_Conditions();
		final I_C_Flatrate_Transition transition = conditions.getC_Flatrate_Transition();

		if (transition != null 
				&& X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendAll.equals(transition.getExtensionType())
				&& transition.getC_Flatrate_Conditions_Next_ID() > 0)
		{
			final ContractExtendingRequest request = ContractExtendingRequest.builder()
					.contract(term)
					.forceExtend(true)
					.forceComplete(true)
					.nextTermStartDate(null)
					.build();

			// running in its own trx for backwards compatibility
			trxManager.run(ITrx.TRXNAME_ThreadInherited, localTrxName_IGNORED -> flatrateBL.extendContractAndNotifyUser(request));
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE, ModelValidator.TIMING_AFTER_REACTIVATE })
	public void handleReactivate(final I_C_Order order)
	{
		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order, I_C_OrderLine.class);
		for (final I_C_OrderLine ol : orderLines)
		{
			if (ol.getC_Flatrate_Conditions_ID() <= 0)
			{
				logger.debug("Order line " + ol + " has no contract term assigned");
				continue;
			}
			handleOrderLineReactivate(ol);
		}
	}

	/**
	 * Make sure the orderLine still has processed='Y', even if the order is reactivated. <br>
	 * This was apparently added in task 03152.<br>
	 * I can guess that if an order line already has a C_Flatrate_Term, then we don't want that order line to be editable, because it could create inconsistencies with the term.
	 */
	private void handleOrderLineReactivate(final I_C_OrderLine ol)
	{
		logger.info("Setting order line's processed status " + ol + " back to Processed='Y'" + " as it references a contract term");

		ol.setProcessed(true);
		InterfaceWrapperHelper.save(ol);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void updateCustomerRetention(final I_C_Order order)
	{

		final CustomerRetentionRepository customerRetentionRepo = SpringContextHolder.instance.getBean(CustomerRetentionRepository.class);

		if (!order.isSOTrx())
		{
			// nothing to do
			return;
		}

		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		customerRetentionRepo.updateCustomerRetentionOnOrderComplete(orderId);

	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = I_C_Order.COLUMNNAME_DatePromised)
	public void updateOrderLineFromContract(final I_C_Order order)
	{
		orderDAO.retrieveOrderLines(order)
				.stream()
				.map(ol -> InterfaceWrapperHelper.create(ol, de.metas.contracts.order.model.I_C_OrderLine.class))
				.filter(subscriptionBL::isSubscription)
				.forEach(orderLineBL::updatePrices);
	}
}
