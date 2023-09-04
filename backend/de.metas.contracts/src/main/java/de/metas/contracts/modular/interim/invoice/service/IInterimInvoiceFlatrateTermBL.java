/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.interim.invoice.service;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.interim.invoice.InterimInvoiceFlatrateTerm;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.quantity.Quantity;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_M_InOut;

import java.sql.Timestamp;
import java.util.function.Consumer;

/**
 * Note: this BL is not about flartate-terms, but about {@link org.compiere.model.I_C_InterimInvoice_FlatrateTerm}s.
 */
public interface IInterimInvoiceFlatrateTermBL extends ISingletonService
{
	void create(
			@NonNull I_C_Flatrate_Term modularFlatrateTermRecord,
			@NonNull Timestamp startDate,
			@NonNull Timestamp endDate);

	void create(
			@NonNull I_C_Flatrate_Term modularFlatrateTermRecord,
			@NonNull Timestamp startDate,
			@NonNull Timestamp endDate,
			@NonNull final Consumer<I_C_Flatrate_Term> beforeCompleteInterceptor);

	void updateInterimInvoiceFlatrateTermForInOut(I_M_InOut inOutLine);

	void updateQuantities(@NonNull InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm);

	@NonNull
	Quantity getQtyDelivered(@NonNull InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm);

	void updateInvoicedQtyForPartialPayment(I_C_Invoice_Candidate invoiceCand);
}
