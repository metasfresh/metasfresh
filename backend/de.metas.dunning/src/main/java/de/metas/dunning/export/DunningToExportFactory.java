package de.metas.dunning.export;

import com.google.common.collect.ImmutableList;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentEntryService.AttachmentEntryQuery;
import de.metas.attachments.AttachmentTags;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.dunning.DunningDocId;
import de.metas.dunning.invoice.DunningService;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning_gateway.spi.model.BPartnerId;
import de.metas.dunning_gateway.spi.model.DunningAttachment;
import de.metas.dunning_gateway.spi.model.DunningId;
import de.metas.dunning_gateway.spi.model.DunningToExport;
import de.metas.dunning_gateway.spi.model.MetasfreshVersion;
import de.metas.dunning_gateway.spi.model.Money;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.util.Services;
import de.metas.util.lang.SoftwareVersion;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.load;

/*
 * #%L
 * de.metas.business
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

@Service
public class DunningToExportFactory
{
	private final DunningService dunningService;
	private final AttachmentEntryService attachmentEntryService;
	private final CurrencyRepository currenciesRepo;

	public DunningToExportFactory(
			@NonNull final DunningService dunningService,
			@NonNull final AttachmentEntryService attachmentEntryService,
			@NonNull final CurrencyRepository currenciesRepo)
	{
		this.attachmentEntryService = attachmentEntryService;
		this.dunningService = dunningService;
		this.currenciesRepo = currenciesRepo;
	}

	public List<DunningToExport> getCreateForId(@NonNull final DunningDocId dunningDocId)
	{
		final ImmutableList.Builder<DunningToExport> result = ImmutableList.builder();

		final I_C_DunningDoc dunningDocRecord = load(dunningDocId, I_C_DunningDoc.class);

		final List<I_C_Invoice> dunnedInvoiceRecords = dunningService.retrieveDunnedInvoices(dunningDocId);
		for (final I_C_Invoice dunnedInvoiceRecord : dunnedInvoiceRecords)
		{
			final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);

			final CurrencyCode currencyCode = extractCurrencyCode(dunnedInvoiceRecord);
			final Money grandTotal = Money.of(dunnedInvoiceRecord.getGrandTotal(), currencyCode.toThreeLetterCode());

			final BigDecimal allocatedAmt = CoalesceUtil.coalesce(allocationDAO.retrieveAllocatedAmt(dunnedInvoiceRecord), ZERO);
			final Money allocatedMoney = Money.of(allocatedAmt, currencyCode.toThreeLetterCode());

			final DunningToExport dunningToExport = DunningToExport
					.builder()
					.id(DunningId.ofRepoId(dunningDocId.getRepoId()))
					.invoiceId(InvoiceId.ofRepoId(dunnedInvoiceRecord.getC_Invoice_ID()))
					.alreadyPaidAmount(allocatedMoney)
					.amount(grandTotal)
					.dunningAttachments(createDunningAttachments(dunningDocId))
					.metasfreshVersion(createMetasfreshVersion())
					.dunningDate(TimeUtil.asCalendar(dunningDocRecord.getDunningDate()))
					.documentNumber(dunningDocRecord.getDocumentNo())
					.dunningTimestamp(dunningDocRecord.getCreated().toInstant())
					.recipientId(BPartnerId.ofRepoId(dunnedInvoiceRecord.getC_BPartner_ID()))
					.dunningText(dunningDocRecord.getC_DunningLevel().getNote())
					.build();
			result.add(dunningToExport);
		}

		return result.build();
	}

	private CurrencyCode extractCurrencyCode(final I_C_Invoice invoiceRecord)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(invoiceRecord.getC_Currency_ID());
		return currenciesRepo.getCurrencyCodeById(currencyId);
	}

	private ImmutableList<DunningAttachment> createDunningAttachments(@NonNull final DunningDocId dunningDocId)
	{
		final ImmutableList.Builder<DunningAttachment> dunningAttachments = ImmutableList.builder();

		final List<I_C_Invoice> dunnedInvoiceRecords = dunningService.retrieveDunnedInvoices(dunningDocId);
		for (final I_C_Invoice dunnedInvoiceRecord : dunnedInvoiceRecords)
		{

			final AttachmentEntryQuery query = AttachmentEntryQuery
					.builder()
					.referencedRecord(TableRecordReference.of(dunnedInvoiceRecord))
					.tagSetToTrue(AttachmentTags.TAGNAME_IS_DOCUMENT)
					.build();
			final List<AttachmentEntry> attachments = attachmentEntryService.getByQuery(query);
			for (final AttachmentEntry attachment : attachments)
			{
				final byte[] attachmentData = attachmentEntryService.retrieveData(attachment.getId());

				final DunningAttachment invoiceAttachment = DunningAttachment.builder()
						.fileName(attachment.getFilename())
						.mimeType(attachment.getMimeType())
						.data(attachmentData)
						.tags(attachment.getTags().toMap())
						.build();
				dunningAttachments.add(invoiceAttachment);
			}
		}

		return dunningAttachments.build();

	}

	private MetasfreshVersion createMetasfreshVersion()
	{
		final SoftwareVersion metasfreshBuildVersion = Adempiere.getBuildVersion();
		final MetasfreshVersion metasfreshVersion = MetasfreshVersion.builder()
				.major(metasfreshBuildVersion.getMajor())
				.minor(metasfreshBuildVersion.getMinor())
				.fullVersion(Adempiere.getBuildAndDateVersion())
				.build();
		return metasfreshVersion;
	}

}
