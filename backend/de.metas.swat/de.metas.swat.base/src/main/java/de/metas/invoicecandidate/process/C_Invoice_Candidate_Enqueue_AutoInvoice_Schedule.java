/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.invoicecandidate.process;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.InvoiceCandidateQuery;
import de.metas.invoicecandidate.api.impl.InvoicingParams;
import de.metas.lang.SOTrx;
import de.metas.process.JavaProcess;
import de.metas.util.Services;
import lombok.NonNull;

public class C_Invoice_Candidate_Enqueue_AutoInvoice_Schedule extends JavaProcess
{
	@NonNull private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	@Override
	protected String doIt() throws Exception
	{
		final ImmutableSet<InvoiceCandidateId> candidateIds = invoiceCandBL.getIdsByQuery(InvoiceCandidateQuery.builder()
						.autoInvoice(true)
						.processed(false)
						.soTrx(SOTrx.SALES)
						.dateToInvoice(SystemTime.asLocalDate())
						.orgId(getOrgId())
						.build()
		);

		if (candidateIds.isEmpty()) { return MSG_OK; }

		invoiceCandBL.enqueueForInvoicing()
				.setInvoicingParams(new InvoicingParams(getParameterAsIParams()))
				.setFailIfNothingEnqueued(true)
				.enqueueInvoiceCandidateIds(candidateIds);

		return MSG_OK;
	}
}
