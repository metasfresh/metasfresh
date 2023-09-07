/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoicecandidate.externallyreferenced;

import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;
import de.metas.util.Services;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

@Repository
public class ExternallyReferencedCandidateRepository extends AbstractInvoiceCandidateRepository
{
	private final IInvoiceCandidateHandlerDAO invoiceCandidateHandlerDAO = Services.get(IInvoiceCandidateHandlerDAO.class);

	protected void setICHandler(final I_C_Invoice_Candidate icRecord)
	{
		final I_C_ILCandHandler handlerRecord = invoiceCandidateHandlerDAO.retrieveForClassOneOnly(Env.getCtx(), ManualCandidateHandler.class);
		icRecord.setC_ILCandHandler_ID(handlerRecord.getC_ILCandHandler_ID());
		icRecord.setIsManual(true);
	}

}
