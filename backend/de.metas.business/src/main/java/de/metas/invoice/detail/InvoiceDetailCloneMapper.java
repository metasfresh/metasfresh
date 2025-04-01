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
import de.metas.invoice.service.MInvoiceLinePOCopyRecordSupport;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Builder
public class InvoiceDetailCloneMapper
{
	@NonNull @Getter private final InvoiceId originalInvoiceId;
	@NonNull @Getter private final InvoiceId targetInvoiceId;
	@Nullable private final MInvoiceLinePOCopyRecordSupport.ClonedInvoiceLinesInfo clonedInvoiceLinesInfo;

	@NonNull
	public InvoiceLineId getTargetInvoiceLineId(@NonNull final InvoiceLineId originalInvoiceLineId)
	{
		// shall not happen because this method shall not be called in case there are no invoice lines
		if (clonedInvoiceLinesInfo == null)
		{
			throw new AdempiereException("No cloned invoice lines info available");
		}

		return clonedInvoiceLinesInfo.getTargetInvoiceLineId(originalInvoiceLineId);
	}
}
