package de.metas.banking.payment.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.compiere.model.I_C_PaySelectionLine;

import de.metas.banking.payment.IPaySelectionBL;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Callout(I_C_PaySelectionLine.class)
public class C_PaySelectionLine
{
	public static final C_PaySelectionLine instance = new C_PaySelectionLine();

	private C_PaySelectionLine()
	{
	}

	/**
	 * Invokes {@link IPaySelectionBL#updateFromInvoice(I_C_PaySelectionLine)}.
	 *
	 * @param psl
	 */
	// TODO: merge it with org.compiere.model.CalloutPaySelection
	@CalloutMethod(columnNames = I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID)
	public void invoice(final I_C_PaySelectionLine psl)
	{
		Services.get(IPaySelectionBL.class).updateFromInvoice(psl);
	}
}
