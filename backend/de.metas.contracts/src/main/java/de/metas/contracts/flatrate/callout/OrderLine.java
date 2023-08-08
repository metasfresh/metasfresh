package de.metas.contracts.flatrate.callout;

import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLinePriceUpdateRequest.ResultUOM;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.model.CalloutEngine;
import org.compiere.model.CalloutOrder;
import org.compiere.model.I_C_Order;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderLine extends CalloutEngine
{

	private final ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);
	/**
	 * This callout "interposes" the callout {@link CalloutOrder#amt(ICalloutField)}
	 *
	 * and decides whether that callout should be executed. If no subscription is selected or this callout is invoked
	 * for another field than 'QtyEntered' that callout is executed, otherwise it is not.
	 */
	public String amt(@NonNull final ICalloutField calloutField)
	{

		if (isCalloutActive())
		{// prevent recursive
			return NO_ERROR;
		}

		if (isDoInvocation(calloutField))
		{
			return new CalloutOrder().amt(calloutField);
		}
		return NO_ERROR;
	}

	/**
	 * This callout "interposes" {@link CalloutOrder#qty(ICalloutField)}
	 *
	 * and decides whether that callout should be executed. If no subscription is selected or this is invoked for
	 * another field than 'QtyEntered' that callout is executed, otherwise not.
	 */
	public String qty(final ICalloutField calloutField)
	{

		if (isCalloutActive())
		{// prevent recursive
			return NO_ERROR;
		}

		if (isDoInvocation(calloutField))
		{
			return new CalloutOrder().qty(calloutField);
		}
		return NO_ERROR;
	}

	private boolean isDoInvocation(final ICalloutField calloutField)
	{

		if (!I_C_OrderLine.COLUMNNAME_QtyEntered.equals(calloutField.getColumnName()))
		{
			// execute the callout
			return true;
		}

		final I_C_OrderLine ol = calloutField.getModel(I_C_OrderLine.class);

		// don't execute the callout
		if(subscriptionBL.isSubscription(ol))
		{
			return false;
		}

		//execute the callout
		return true;
	}



	// metas
	public String subscriptionLocation(final ICalloutField calloutField)
	{

		final I_C_OrderLine ol = calloutField.getModel(I_C_OrderLine.class);
		final I_C_Order order = ol.getC_Order();
		final boolean IsSOTrx = order.isSOTrx();
		final boolean isSubscription = ol.isSubscription();

		if (IsSOTrx && !isSubscription)
		{
			final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

			final Quantity qty = orderLineBL.getQtyEntered(ol);
			final BigDecimal qtyOrdered = orderLineBL.convertQtyEnteredToStockUOM(ol).toBigDecimal();
			ol.setQtyOrdered(qtyOrdered);

			orderLineBL.updatePrices(OrderLinePriceUpdateRequest.builder()
					.orderLine(ol)
					.qtyOverride(qty)
					.resultUOM(ResultUOM.CONTEXT_UOM)
					.updatePriceEnteredAndDiscountOnlyIfNotAlreadySet(true)
					.updateLineNetAmt(true)
					.build());
		}
		if (IsSOTrx && isSubscription)
		{
			final int C_BPartner_ID = order.getC_BPartner_ID();

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			final String sql = "SELECT C_BPartner_Location_ID FROM C_BPartner_Location "
					+ " WHERE C_BPartner_ID = ? "
					+ " ORDER BY IsSubscriptionToDefault DESC";
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, C_BPartner_ID);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					final int bpartnerLocationId = rs.getInt(1);
					order.setC_BPartner_Location_ID(bpartnerLocationId);
					log.debug("C_BPartner_Location_ID for Subscription changed -> " + bpartnerLocationId);
				}
			}
			catch (final SQLException e)
			{
				log.error(sql, e);
				return e.getLocalizedMessage();
			}
			finally
			{
				DB.close(rs, pstmt);
			}
		}
		return NO_ERROR;
	}

}
