/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.order.model.interceptor;

import com.google.common.annotations.VisibleForTesting;
import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.DeliveryViaRule;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.IOrderLinePricingConditions;
import de.metas.order.OrderId;
import de.metas.order.impl.OrderLineDetailRepository;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.reservation.PaymentReservationService;
import de.metas.pricing.PriceListId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Interceptor(I_C_Order.class)
@Callout(I_C_Order.class)
public class C_Order
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IOrderLinePricingConditions orderLinePricingConditions = Services.get(IOrderLinePricingConditions.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final OrderLineDetailRepository orderLineDetailRepository;

	@VisibleForTesting
	public static final String AUTO_ASSIGN_TO_SALES_ORDER_BY_EXTERNAL_ORDER_ID_SYSCONFIG = "de.metas.payment.autoAssignToSalesOrderByExternalOrderId.enabled";
	private static final AdMessageKey MSG_SELECT_CONTACT_WITH_VALID_EMAIL = AdMessageKey.of("de.metas.order.model.interceptor.C_Order.PleaseSelectAContactWithValidEmailAddress");

	public C_Order(final OrderLineDetailRepository orderLineDetailRepository)
	{
		this.orderLineDetailRepository = orderLineDetailRepository;
		final IProgramaticCalloutProvider programmaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programmaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Order.COLUMNNAME_M_PriceList_ID })
	public void onPriceListChangeInterceptor(final I_C_Order order)
	{
		onPriceListChange(order);
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_M_PriceList_ID)
	public void onPriceListChangeCallout(final I_C_Order order)
	{
		onPriceListChange(order);
	}

	private void onPriceListChange(final I_C_Order order)
	{
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(order.getM_PriceList_ID());
		if (priceListId == null)
		{
			return;
		}

		final I_M_PriceList pl = priceListDAO.getById(priceListId);

		order.setM_PricingSystem_ID(pl.getM_PricingSystem_ID());
		order.setC_Currency_ID(pl.getC_Currency_ID());
		order.setIsTaxIncluded(pl.isTaxIncluded());
	}

	// 03409: Context menu fixes (2012101810000086)
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = {
					// I checked the code of OrderBL.updateAddresses() and MOrderLine.setHeaderInfo() to get this list
					I_C_Order.COLUMNNAME_C_BPartner_ID,
					I_C_Order.COLUMNNAME_C_BPartner_Location_ID,
					I_C_Order.COLUMNNAME_AD_User_ID,
					I_C_Order.COLUMNNAME_DropShip_BPartner_ID,
					I_C_Order.COLUMNNAME_DropShip_Location_ID,
					I_C_Order.COLUMNNAME_DropShip_User_ID,
					I_C_Order.COLUMNNAME_M_PriceList_ID,
					I_C_Order.COLUMNNAME_IsSOTrx,
					I_C_Order.COLUMNNAME_C_Currency_ID })
	public void updateOrderLineAddresses(final I_C_Order order)
	{
		orderBL.updateAddresses(order);
	}

	//	// 04579 Cannot change order's warehouse (2013071510000103)
	//	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	//	public void unreserveStock(final I_C_Order order) throws Exception
	//	{
	//		for (final I_C_OrderLine orderLine : orderDAO.retrieveOrderLines(order, I_C_OrderLine.class))
	//		{
	//			if (orderLine.getQtyReserved().signum() <= 0)
	//			{
	//				continue; // nothing to do
	//			}
	//
	//			final BigDecimal qtyOrdered = orderLine.getQtyOrdered();
	//			final BigDecimal qtyEntered = orderLine.getQtyEntered();
	//			final BigDecimal lineNetAmt = orderLine.getLineNetAmt();
	//
	//			// just setting this one to zero would result in negative reservations in case of (partial) deliveries.
	//			orderLine.setQtyOrdered(orderLine.getQtyDelivered());
	//			orderLine.setQtyEntered(BigDecimal.ZERO);
	//			orderLine.setLineNetAmt(BigDecimal.ZERO);
	//
	//			// task 08002
	//			InterfaceWrapperHelper.setDynAttribute(orderLine, IOrderLineBL.DYNATTR_DoNotRecalculatePrices, Boolean.TRUE);
	//
	//			orderDAO.save(orderLine);
	//
	//			orderDAO.reserveStock(orderLine.getC_Order(), orderLine);
	//
	//			orderLine.setQtyOrdered(qtyOrdered);
	//			orderLine.setQtyEntered(qtyEntered);
	//			orderLine.setLineNetAmt(lineNetAmt);
	//			orderDAO.save(orderLine);
	//
	//			// task 08002
	//			InterfaceWrapperHelper.setDynAttribute(orderLine, IOrderLineBL.DYNATTR_DoNotRecalculatePrices, Boolean.FALSE);
	//		}
	//	}

	/**
	 * Updates <code>C_OrderLine.QtyReserved</code> of the given order's lines when the Doctype or DocStatus changes.
	 * <p>
	 * Task http://dewiki908/mediawiki/index.php/09358_OrderLine-QtyReserved_sometimes_not_updated_%28108061810375%29
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_C_Order.COLUMNNAME_C_DocType_ID,
					I_C_Order.COLUMNNAME_DocStatus })
	public void updateReserved(final I_C_Order order)
	{
		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order, I_C_OrderLine.class);
		for (final I_C_OrderLine orderLine : orderLines)
		{
			orderLineBL.updateQtyReserved(orderLine);
			orderDAO.save(orderLine);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_C_Order.COLUMNNAME_C_BPartner_ID })
	public void setDeliveryViaRule(final I_C_Order order)
	{
		final DeliveryViaRule deliveryViaRule = orderBL.evaluateOrderDeliveryViaRule(order);

		if (deliveryViaRule != null)
		{
			order.setDeliveryViaRule(deliveryViaRule.getCode());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(@NonNull final I_C_Order order)
	{
		unlinkSalesOrders(order);
		unlinkThisProposalFromGeneratedSalesOrders(order);
		unlinkThisSalesOrderFromProposal(order);
		orderLineDetailRepository.deleteByOrderId(OrderId.ofRepoId(order.getC_Order_ID()));
	}

	/**
	 * If a purchase order is deleted, then an< sales orders need to un-reference it to avoid an FK-constraint-error
	 * <p>
	 * Task http://dewiki908/mediawiki/index.php/09557_Wrong_aggregation_on_OrderPOCreate_%28109614894753%29
	 */
	private void unlinkSalesOrders(final I_C_Order order)
	{
		final List<I_C_Order> referencingOrders = queryBL.createQueryBuilder(I_C_Order.class, order)
				.addEqualsFilter(org.compiere.model.I_C_Order.COLUMNNAME_Link_Order_ID, order.getC_Order_ID())
				.create()
				.list(I_C_Order.class);
		for (final I_C_Order referencingOrder : referencingOrders)
		{
			referencingOrder.setLink_Order_ID(-1);
			orderDAO.save(referencingOrder);
		}
	}

	/**
	 * When deleting a proposal/quotation, unlink all generated sales orders from it
	 */
	private void unlinkThisProposalFromGeneratedSalesOrders(final I_C_Order proposal)
	{
		if (!orderBL.isSalesProposalOrQuotation(proposal))
		{
			return;
		}

		final List<I_C_Order> linkedSalesOrders = queryBL.createQueryBuilder(I_C_Order.class, proposal)
				.addEqualsFilter(org.compiere.model.I_C_Order.COLUMNNAME_Ref_Proposal_ID, proposal.getC_Order_ID())
				.create()
				.list(I_C_Order.class);
		if (linkedSalesOrders.isEmpty())
		{
			return;
		}

		for (final I_C_Order salesOrder : linkedSalesOrders)
		{
			salesOrder.setRef_Proposal_ID(-1);
			orderDAO.save(salesOrder);
		}
	}

	private void unlinkThisSalesOrderFromProposal(final I_C_Order salesOrder)
	{
		final List<I_C_Order> referencingOrders = queryBL.createQueryBuilder(I_C_Order.class)
				.addEqualsFilter(org.compiere.model.I_C_Order.COLUMNNAME_Ref_Order_ID, salesOrder.getC_Order_ID())
				.create()
				.list(I_C_Order.class);
		for (final I_C_Order referencingOrder : referencingOrders)
		{
			referencingOrder.setRef_Order_ID(-1);
			orderDAO.save(referencingOrder);
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void checkPricingConditionsInOrderLines(final I_C_Order order)
	{
		checkPaymentRuleWithReservation(order);
		orderLinePricingConditions.failForMissingPricingConditions(order);
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_PaymentRule)
	public void checkPaymentRuleWithReservation(@NonNull final I_C_Order salesOrder, @NonNull final ICalloutField calloutField)
	{
		final AdMessageKey errorMessage = checkNeedsAndHasContactWithValidEmail(salesOrder);

		if (errorMessage != null)
		{
			final ITranslatableString msgText = msgBL.getTranslatableMsgText(errorMessage);
			calloutField.fireDataStatusEEvent(
					msgText.translate(Env.getAD_Language()),
					msgText.translate(Env.getAD_Language()), // this appears onHover
					true);
		}
		//else
		//{
		// TODO teo: there's no way to remove the callout error right now, unless the user reloads the page.
		//  	It will always appear when changing the PaymentRule after it was set once.
		//}
	}

	private void checkPaymentRuleWithReservation(@NonNull final I_C_Order salesOrder)
	{
		final AdMessageKey errorMessage = checkNeedsAndHasContactWithValidEmail(salesOrder);

		if (errorMessage != null)
		{
			throw new AdempiereException(errorMessage);
		}
	}

	@Nullable
	private AdMessageKey checkNeedsAndHasContactWithValidEmail(@NonNull final I_C_Order salesOrder)
	{
		if (!salesOrder.isSOTrx())
		{
			return null;
		}

		final PaymentRule paymentRule = PaymentRule.ofNullableCode(salesOrder.getPaymentRule());
		if(paymentRule == null)
		{
			return null;
		}

		final PaymentReservationService paymentReservationService = SpringContextHolder.instance.getBean(PaymentReservationService.class);
		if (!paymentReservationService.isPaymentReservationRequired(paymentRule))
		{
			return null;
		}

		final boolean hasBillToContactId = orderBL.hasBillToContactId(salesOrder);
		if (!hasBillToContactId)
		{
			return MSG_SELECT_CONTACT_WITH_VALID_EMAIL;
		}

		final BPartnerContactId billToContactId = orderBL.getBillToContactId(salesOrder);

		if (!bpartnerDAO.hasEmailAddress(billToContactId))
		{
			return MSG_SELECT_CONTACT_WITH_VALID_EMAIL;
		}

		return null;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void linkWithPaymentByExternalOrderId(@NonNull final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return;
		}

		if (order.getExternalId() == null || Check.isBlank(order.getExternalId()))
		{
			return;
		}

		if (order.getC_Payment_ID() > 0)
		{
			return;
		}

		final boolean autoAssignEnabled = sysConfigBL.getBooleanValue(AUTO_ASSIGN_TO_SALES_ORDER_BY_EXTERNAL_ORDER_ID_SYSCONFIG, false, order.getAD_Client_ID(), order.getAD_Org_ID());
		if (!autoAssignEnabled)
		{
			return;
		}

		final Optional<I_C_Payment> paymentOptional = paymentDAO.getByExternalOrderId(ExternalId.of(order.getExternalId()), OrgId.ofRepoId(order.getAD_Org_ID()));

		if (!paymentOptional.isPresent())
		{
			return;
		}

		// ! [workaround]
		// This save should not be needed but it is.
		// Without it MPayment.setC_Order_ID fails because it tries to read the C_DocType_ID from the order
		// 		and since the order is not yet saved => only C_DocType_Target_ID has value and C_DocType_ID doesn't (is 0).
		// Therefore we use this save to flush the order.
		orderDAO.save(order);

		final I_C_Payment payment = paymentOptional.get();
		order.setC_Payment_ID(payment.getC_Payment_ID());
		payment.setC_Order_ID(order.getC_Order_ID());

		paymentDAO.save(payment);
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_Order.COLUMNNAME_C_DocTypeTarget_ID,
			I_C_Order.COLUMNNAME_C_BPartner_ID
	})
	public void updateDescriptionFromDocType(final I_C_Order order)
	{
		if (!InterfaceWrapperHelper.isCopying(order))
		{
			orderBL.updateDescriptionFromDocTypeTargetId(order);
		}
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE,
	}, ifColumnsChanged = {
			I_C_Order.COLUMNNAME_DropShip_Location_ID
	})
	public void onDropShipLocation(final I_C_Order order)
	{
		if (order.getDropShip_Location_ID() <= 0)
		{
			// nothing to do
			return;
		}

		orderBL.setPriceList(order);
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_Order.COLUMNNAME_C_BPartner_ID,
			I_C_Order.COLUMNNAME_DatePromised })
	public void validateHaddexOnChange(final I_C_Order order)
	{
		validateHaddex(order);
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_COMPLETE })
	public void validateHaddexOnComplete(final I_C_Order order)
	{
		// validate on order completion to prevent cases when a partner or product became haddex relevant after the order was created but before it was completed
		validateHaddex(order);
	}

	private void validateHaddex(final I_C_Order order)
	{
		final IOrderBL orderBL = Services.get(IOrderBL.class);

		orderBL.validateHaddexOrder(order);
	}
}
