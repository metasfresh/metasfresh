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

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.interim.invoice.InterimInvoiceFlatrateTerm;
import de.metas.contracts.modular.interim.invoice.InterimInvoiceFlatrateTermId;
import de.metas.contracts.modular.interim.invoice.InterimInvoiceFlatrateTermQuery;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.order.OrderLineId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_M_InOutLine;

import java.util.stream.Stream;

/**
  * Note: this DAO is not about flartate-terms, but about {@link org.compiere.model.I_C_InterimInvoice_FlatrateTerm}s.
  */
public interface IInterimInvoiceFlatrateTermDAO extends ISingletonService
{
	InterimInvoiceFlatrateTerm getById(@NonNull final InterimInvoiceFlatrateTermId id);

	InterimInvoiceFlatrateTerm save(@NonNull final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm);

	Stream<InterimInvoiceFlatrateTerm> retrieveBy(InterimInvoiceFlatrateTermQuery query);

	InterimInvoiceFlatrateTerm getInterimInvoiceOverviewForInOutLine(@NonNull final I_M_InOutLine inOutLine);

	boolean isInterimInvoiceStillUsable(@NonNull final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm);

	InterimInvoiceFlatrateTerm getInterimInvoiceForFlatrateTermAndOrderLineId(@NonNull final FlatrateTermId flatrateTerm, final @NonNull OrderLineId orderLineId);

	InterimInvoiceFlatrateTerm getInterimInvoiceFlatrateTermForWithwoldingOrInterimICId(@NonNull final InvoiceCandidateId icId);
}
