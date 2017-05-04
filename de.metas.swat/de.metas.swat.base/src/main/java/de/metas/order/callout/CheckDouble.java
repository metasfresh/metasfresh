package de.metas.order.callout;

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


import static org.compiere.model.I_C_Order.COLUMNNAME_DocumentNo;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_C_BPartner_Location_ID;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_C_OrderLine_ID;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Product_ID;

import java.util.List;
import java.util.Properties;

import org.adempiere.util.MiscUtils;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_Order;
import org.compiere.model.MSysConfig;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderPA;

/**
 *
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Dubletten_bei_Auftragserfassung_vermeiden_%282009_0027_G10%29'>(2009 0027 G10)</a>"
 */
public class CheckDouble extends CalloutEngine {

	private static final String PARAM_CHECK_ACTIVE = "de.metas.CheckForDoubleOrderLine";

	final String MSG_DOUBLE = "OrderLineDouble.DoubleExists";
	final String MSG_DOUBLE_LINE = "OrderLineDouble.DoubleLine";

	final String TRX_NAME = null;

	public String mProductId(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value) {

		if (isCalloutActive() || !isCheck(ctx)) {
			return "";
		}
		return evalForOrderLine(ctx, WindowNo, mTab);
	}

	public String cBpartnerLocationId(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value) {

		if (isCalloutActive() || !isCheck(ctx)) {
			return "";
		}
		return evalForOrderLine(ctx, WindowNo, mTab);
	}

	public String mProduct(final Properties ctx, final int WindowNo,
			final GridTab gridTab, final GridField mField, final Object value) {

		if (isCalloutActive() || !isCheck(ctx)) {
			return "";
		}

		final int productId = MiscUtils.getCalloutId(gridTab, I_C_Order.COLUMNNAME_M_Product_ID);
		final int bPartnerLocationId = MiscUtils.getCalloutId(gridTab, COLUMNNAME_C_BPartner_Location_ID);
		final int orderLineId = 0;

		return check(ctx, productId, bPartnerLocationId, orderLineId);
	}

	String evalForOrderLine(final Properties ctx, final int WindowNo,
			final GridTab gridTab) {

		if (!Env.isSOTrx(ctx, WindowNo)) {
			return "";
		}

		final int productId = MiscUtils.getCalloutId(gridTab,
				COLUMNNAME_M_Product_ID);
		final int bPartnerLocationId = MiscUtils.getCalloutId(gridTab,
				COLUMNNAME_C_BPartner_Location_ID);
		final int orderLineId = MiscUtils.getCalloutId(gridTab,
				COLUMNNAME_C_OrderLine_ID);

		return check(ctx, productId, bPartnerLocationId, orderLineId);
	}

	private String check(final Properties ctx, final int productId, final int bPartnerLocationId,
			final int orderLineId) {

		if (productId == 0 || bPartnerLocationId == 0) {
			return "";
		}

		final IOrderPA orderPA = Services.get(IOrderPA.class);

		final List<I_C_OrderLine> lines = orderPA.retrieveOrderLinesForProdAndLoc(ctx, productId, bPartnerLocationId, I_C_OrderLine.class, TRX_NAME);

		final StringBuffer msg = new StringBuffer();
		boolean firstDouble = true;

		for (final I_C_OrderLine potentialDouble : lines) {

			if (potentialDouble.getC_OrderLine_ID() == orderLineId) {
				continue;
			}
			final I_C_Order order = orderPA.retrieveOrder(potentialDouble
					.getC_Order_ID(), TRX_NAME);

			if (!order.isSOTrx()) {
				continue;
			}

			if (firstDouble) {
				firstDouble = false;
				msg.append(Msg.getMsg(Env.getCtx(), MSG_DOUBLE));
			}

			msg.append("\n");
			msg.append(Msg.getElement(Env.getCtx(),
					COLUMNNAME_DocumentNo));
			msg.append(" ");
			msg.append(order.getDocumentNo());
			msg.append(" ");
			msg.append(Msg.getElement(Env.getCtx(),
					I_C_OrderLine.COLUMNNAME_Line));
			msg.append(" ");
			msg.append(potentialDouble.getLine());
		}
		return msg.toString();
	}

	private boolean isCheck(final Properties ctx) {

		final boolean checkDouble = MSysConfig.getBooleanValue(
				PARAM_CHECK_ACTIVE, true, Env.getAD_Client_ID(ctx),
				Env.getAD_Org_ID(ctx));
		return checkDouble;
	}
}
