/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.invoicecandidate.location;

import de.metas.document.location.IDocumentLocationBL;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.Builder;
import lombok.NonNull;

public class InvoiceCandidateLocationsUpdater
{
	private final IDocumentLocationBL documentLocationBL;
	private final I_C_Invoice_Candidate record;

	@Builder
	private InvoiceCandidateLocationsUpdater(
			@NonNull final IDocumentLocationBL documentLocationBL,
			@NonNull final I_C_Invoice_Candidate record)
	{
		this.documentLocationBL = documentLocationBL;
		this.record = record;
	}

	public void updateAllIfNeeded()
	{
		InvoiceCandidateLocationAdapterFactory.billLocationAdapter(record).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);
		InvoiceCandidateLocationAdapterFactory.billLocationOverrideAdapter(record).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);
	}
}
