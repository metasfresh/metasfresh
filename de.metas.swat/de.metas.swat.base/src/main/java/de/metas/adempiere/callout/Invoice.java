package de.metas.adempiere.callout;

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


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Msg;

import de.metas.adempiere.model.I_C_Invoice;

public class Invoice extends CalloutEngine {

	public static final String MSG_CREDIT_MEMO_EXITS_1P = "CreditMemoAlreadyExists_1P";

	public String creditMemo(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue) {

		if (isCalloutActive()) {// prevent recursive
			return "";
		}

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(mTab, I_C_Invoice.class);

		if (invoice.getRef_CreditMemo_ID() > 0) {

			final Object[] param = { invoice.getRef_CreditMemo()
					.getDocumentNo() };
			return Msg.getMsg(ctx, MSG_CREDIT_MEMO_EXITS_1P, param);
		}
		return "";
	}

}
