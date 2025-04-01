/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.detail;

import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.HashMap;

@Builder
public class InvoiceDetailCloneMapper
{
	@NonNull @Getter private final InvoiceId originalInvoiceId;
	@NonNull @Getter private final InvoiceId targetInvoiceId;
	@NonNull private final ClonedInvoiceLinesInfo clonedInvoiceLinesInfo;

	@NonNull
	public InvoiceLineId getTargetInvoiceLineId(@NonNull final InvoiceLineId originalInvoiceLineId)
	{
		return clonedInvoiceLinesInfo.getTargetInvoiceLineId(originalInvoiceLineId);
	}

	public static class ClonedInvoiceLinesInfo
	{
		private final HashMap<InvoiceLineId, InvoiceLineId> original2targetInvoiceLineIds = new HashMap<>();

		public void addOriginalToClonedInvoiceLineMapping(@NonNull final InvoiceLineId originalInvoiceLineId, @NonNull final InvoiceLineId targetInvoiceLineId)
		{
			original2targetInvoiceLineIds.put(originalInvoiceLineId, targetInvoiceLineId);
		}

		@NonNull
		public InvoiceLineId getTargetInvoiceLineId(@NonNull final InvoiceLineId originalInvoiceLineId)
		{
			final InvoiceLineId targetInvoiceLineId = original2targetInvoiceLineIds.get(originalInvoiceLineId);
			if (targetInvoiceLineId == null)
			{
				throw new AdempiereException("No target invoice line found for " + originalInvoiceLineId);
			}
			return targetInvoiceLineId;
		}
	}
}
