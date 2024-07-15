package de.metas.invoicecandidate.spi.impl;

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntryService;
import de.metas.invoicecandidate.model.I_C_Invoice;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateListener;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import java.util.List;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class AttachmentInvoiceCandidateListener implements IInvoiceCandidateListener
{
	public static final AttachmentInvoiceCandidateListener INSTANCE = new AttachmentInvoiceCandidateListener();

	private AttachmentInvoiceCandidateListener()
	{
	}

	@Override
	public void onBeforeInvoiceComplete(
			@NonNull final I_C_Invoice invoice,
			@NonNull final List<I_C_Invoice_Candidate> fromCandidates)
	{
		final AttachmentEntryService attachmentryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
		attachmentryService.shareAttachmentLinks(fromCandidates, ImmutableList.of(invoice));
	}
}
