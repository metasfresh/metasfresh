/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.model.interceptor;

import de.metas.contacts.invoice.interim.service.IInterimInvoiceFlatrateTermBL;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_InOutLine.class)
@Component
public class M_InOutLine
{
	private final IInterimInvoiceFlatrateTermBL interimInvoiceBL = Services.get(IInterimInvoiceFlatrateTermBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_M_InOutLine.COLUMNNAME_MovementQty })
	public void verifyIfPartOfInterimContract(final I_M_InOutLine inOutLine)
	{
		if (inOutLine.getC_OrderLine_ID() <= 0)
		{
			return;
		}
		interimInvoiceBL.updateInterimInvoiceFlatrateTermForInOutLine(inOutLine);
	}

}
