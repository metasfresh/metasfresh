package de.metas.inoutcandidate.spi;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import org.adempiere.model.InterfaceWrapperHelper;

/**
 * Implementors of this interface can be registered using
 * {@link IInvoiceCandBL#registerVetoer(ModelWithoutInvoiceCandidateVetoer, String)} . They are notified if a given
 * {@link AbstractInvoiceCandidateHandler} wants to create a {@link I_C_Invoice_Candidate} for a given data record and they can veto
 * that creation.
 * <p>
 *
 * @author adi
 */
@FunctionalInterface
public interface ModelWithoutInvoiceCandidateVetoer
{
	enum OnMissingCandidate
	{
		I_VETO,

		I_DONT_CARE
	}

	/**
	 * This method is called when a handler found a data record (e.g. a C_OrderLine) for which would like to create a
	 * {@link I_C_Invoice_Candidate}.
	 *
	 * @param model the record that the given
	 *              <code>handler</code> identified as "lacking an invoice candidate". The implementor can assume that this model can be accessed using {@link InterfaceWrapperHelper}.
	 * @return {@link OnMissingCandidate#I_VETO} if there are reasons that no {@link I_C_Invoice_Candidate}
	 * should be created. Each implementation can assume that no {@link I_C_Invoice_Candidate} is created if
	 * at least one vetoer returns {@link OnMissingCandidate#I_VETO}.
	 */
	OnMissingCandidate foundModelWithoutInvoiceCandidate(Object model);
}
