package de.metas.banking.payment;

/*
 * #%L
 * de.metas.banking.base
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


import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Invoice;

import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.payment.model.I_C_Payment_Request;

/**
 * Service for {@link I_C_Payment_Request} operations
 *
 * @author al
 */
public interface IPaymentRequestBL extends ISingletonService
{
	/**
	 * Use given request to fill a new payment request
	 *
	 * @param request
	 * @return newly created payment request
	 */
	I_C_Payment_Request createNewFromTemplate(I_C_Payment_Request request);

	/**
	 * Checks if methods like {@link #updatePaySelectionLineFromPaymentRequestIfExists(I_C_PaySelectionLine)} were already executed on given pay selection line.
	 * 
	 * @param paySelectionLine
	 * @return
	 */
	boolean isUpdatedFromPaymentRequest(I_C_PaySelectionLine paySelectionLine);

	/**
	 * Updates {@link I_C_PaySelectionLine} from {@link I_C_Payment_Request} linked to pay selection line's invoice.
	 * 
	 * @param paySelectionLine
	 * @return true if payment request was found and pay selection line was updated
	 */
	boolean updatePaySelectionLineFromPaymentRequestIfExists(I_C_PaySelectionLine paySelectionLine);

	/**
	 * Creates a new {@link I_C_Payment_Request} for given <code>invoice</code>, optionally using given payment request template
	 * 
	 * @param invoice
	 * @param paymentRequestTemplate payment request template (optional)
	 * @return newly created payment request (saved); never returns <code>null</code>
	 */
	I_C_Payment_Request createPaymentRequest(I_C_Invoice invoice, I_C_Payment_Request paymentRequestTemplate);

}
