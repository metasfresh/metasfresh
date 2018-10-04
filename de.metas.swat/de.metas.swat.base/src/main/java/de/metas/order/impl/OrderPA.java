package de.metas.order.impl;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.document.ICopyHandlerBL;
import de.metas.logging.LogManager;
import de.metas.order.IOrderPA;
import de.metas.util.Services;

public class OrderPA implements IOrderPA
{
	private static final Logger logger = LogManager.getLogger(OrderPA.class);

	@Override
	public MOrder retrieveOrder(final int orderId, final String trxName)
	{

		final MOrder order = new MOrder(Env.getCtx(), orderId, trxName);

		if (order.get_ID() == 0)
		{

			logger.debug("There is no order with id '" + orderId + "'. Returning null.");
			return null;
		}
		return order;

	}

	/**
	 * Invokes {@link MOrder#getLines()}.
	 *
	 */
	@Override
	public <T extends I_C_OrderLine> List<T> retrieveOrderLines(final I_C_Order order, final Class<T> clazz)
	{
		final MOrderLine[] lines = ((MOrder)InterfaceWrapperHelper.getPO(order)).getLines(true, "");

		final List<T> result = new ArrayList<>(lines.length);

		for (final MOrderLine currentLine : lines)
		{
			result.add(InterfaceWrapperHelper.create(currentLine, clazz));
		}

		return result;
	}

	@Override
	public void reserveStock(final I_C_Order order, final I_C_OrderLine... ols)
	{

		final MOrder mOrder = InterfaceWrapperHelper.getPO(order);
		final MOrderLine[] mOls = new MOrderLine[ols.length];

		for (int i = 0; i < ols.length; i++)
		{
			mOls[i] = InterfaceWrapperHelper.getPO(ols[i]);
		}
		mOrder.reserveStock(null, mOls); // docType=null (i.e. fetch it from order)
	}

	@Override
	public I_C_Order copyOrder(
			final I_C_Order originalOrder,
			final boolean copyLines,
			final String trxName)
	{

		final MOrder newOrder = new MOrder(Env.getCtx(), 0, trxName);

		Services.get(ICopyHandlerBL.class).copyPreliminaryValues(originalOrder, newOrder);

		newOrder.setAD_Org_ID(originalOrder.getAD_Org_ID());

		newOrder.setDatePromised(Env.getContextAsDate(Env.getCtx(), "#Date"));
		newOrder.setDateOrdered(Env.getContextAsDate(Env.getCtx(), "#Date"));

		newOrder.setAD_OrgTrx_ID(originalOrder.getAD_OrgTrx_ID());
		newOrder.setAD_User_ID(originalOrder.getAD_User_ID());
		newOrder.setC_Activity_ID(originalOrder.getC_Activity_ID());
		newOrder.setC_BPartner_ID(originalOrder.getC_BPartner_ID());
		newOrder.setC_BPartner_Location_ID(originalOrder.getC_BPartner_Location_ID());
		newOrder.setBill_BPartner_ID(originalOrder.getBill_BPartner_ID());
		newOrder.setBill_Location_ID(originalOrder.getBill_Location_ID());
		newOrder.setBill_User_ID(originalOrder.getBill_User_ID());
		newOrder.setC_Campaign_ID(originalOrder.getC_Campaign_ID());
		newOrder.setC_CashLine_ID(originalOrder.getC_CashLine_ID());
		newOrder.setC_Charge_ID(originalOrder.getC_Charge_ID());
		newOrder.setC_ConversionType_ID(originalOrder.getC_ConversionType_ID());
		newOrder.setC_Currency_ID(originalOrder.getC_Currency_ID());
		newOrder.setC_DocType_ID(originalOrder.getC_DocType_ID());
		newOrder.setC_DocTypeTarget_ID(originalOrder.getC_DocTypeTarget_ID());
		newOrder.setC_Payment_ID(originalOrder.getC_Payment_ID());
		newOrder.setC_PaymentTerm_ID(originalOrder.getC_PaymentTerm_ID());
		newOrder.setC_Project_ID(originalOrder.getC_Project_ID());
		newOrder.setChargeAmt(originalOrder.getChargeAmt());
		newOrder.setDescription(originalOrder.getDescription());
		newOrder.setFreightAmt(originalOrder.getFreightAmt());
		newOrder.setFreightCostRule(originalOrder.getFreightCostRule());
		newOrder.setInvoiceRule(originalOrder.getInvoiceRule());
		newOrder.setIsSOTrx(originalOrder.isSOTrx());
		newOrder.setIsTaxIncluded(originalOrder.isTaxIncluded());
		newOrder.setM_PriceList_ID(originalOrder.getM_PriceList_ID());
		newOrder.setM_Shipper_ID(originalOrder.getM_Shipper_ID());
		newOrder.setM_Warehouse_ID(originalOrder.getM_Warehouse_ID());
		newOrder.setPay_BPartner_ID(originalOrder.getPay_BPartner_ID());
		newOrder.setPay_Location_ID(originalOrder.getPay_Location_ID());
		newOrder.setPaymentRule(originalOrder.getPaymentRule());
		newOrder.setPriorityRule(originalOrder.getPriorityRule());
		newOrder.setSalesRep_ID(originalOrder.getSalesRep_ID());
		newOrder.setSendEMail(originalOrder.isSendEMail());
		newOrder.setUser1_ID(originalOrder.getUser1_ID());
		newOrder.setUser2_ID(originalOrder.getUser2_ID());
		newOrder.setVolume(originalOrder.getVolume());
		newOrder.setWeight(originalOrder.getWeight());
		newOrder.setPOReference(originalOrder.getPOReference());

		newOrder.setCompleteOrderDiscount(originalOrder.getCompleteOrderDiscount());
		newOrder.setDescriptionBottom(originalOrder.getDescriptionBottom());

		save(newOrder);

		if (copyLines)
		{
			final int linesCopied = newOrder.copyLinesFrom(LegacyAdapters.convertToPO(originalOrder), false, false);
			logger.debug("Copied " + linesCopied + " form original order");
		}

		Services.get(ICopyHandlerBL.class).copyValues(originalOrder, newOrder);

		return newOrder;
	}
}
