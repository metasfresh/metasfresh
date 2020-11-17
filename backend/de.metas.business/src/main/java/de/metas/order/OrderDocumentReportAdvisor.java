/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.order;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.Language;
import de.metas.report.DefaultPrintFormatsRepository;
import de.metas.report.DocumentPrintOptions;
import de.metas.report.DocumentReportAdvisor;
import de.metas.report.PrintFormatId;
import de.metas.report.StandardDocumentReportInfo;
import de.metas.report.StandardDocumentReportType;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

import static de.metas.report.DocumentReportAdvisorUtil.getDocumentCopies;
import static de.metas.report.DocumentReportAdvisorUtil.getReportProcessIdByPrintFormatId;

@Component
public class OrderDocumentReportAdvisor implements DocumentReportAdvisor
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IBPartnerBL bpartnerBL;
	private final DefaultPrintFormatsRepository defaultPrintFormatsRepository;

	public OrderDocumentReportAdvisor(
			@NonNull final IBPartnerBL bpartnerBL,
			@NonNull final DefaultPrintFormatsRepository defaultPrintFormatsRepository)
	{
		this.bpartnerBL = bpartnerBL;
		this.defaultPrintFormatsRepository = defaultPrintFormatsRepository;
	}

	@Override
	public String getHandledTableName()
	{
		return I_C_Order.Table_Name;
	}

	@Override
	public StandardDocumentReportType getStandardDocumentReportType()
	{
		return StandardDocumentReportType.ORDER;
	}

	@NonNull
	public StandardDocumentReportInfo getDocumentReportInfo(
			@NonNull final StandardDocumentReportType type,
			final int recordId,
			@Nullable final PrintFormatId adPrintFormatToUseId)
	{
		Check.assumeEquals(type, StandardDocumentReportType.ORDER, "type");

		final OrderId orderId = OrderId.ofRepoId(recordId);
		final I_C_Order order = orderBL.getById(orderId);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final I_C_BPartner bpartner = bpartnerBL.getById(bpartnerId);

		final DocTypeId docTypeId = extractDocTypeId(order);
		final I_C_DocType docType = docTypeDAO.getById(docTypeId);

		final ClientId clientId = ClientId.ofRepoId(order.getAD_Client_ID());

		final PrintFormatId printFormatId = CoalesceUtil.coalesceSuppliers(
				() -> adPrintFormatToUseId,
				() -> bpartnerBL.getPrintFormats(bpartnerId).getPrintFormatIdByDocTypeId(docTypeId).orElse(null),
				() -> PrintFormatId.ofRepoIdOrNull(docType.getAD_PrintFormat_ID()),
				() -> defaultPrintFormatsRepository.getByClientId(clientId).getOrderPrintFormatId());
		if (printFormatId == null)
		{
			throw new AdempiereException("@NotFound@ @AD_PrintFormat_ID@");
		}

		final int documentCopies = getDocumentCopies(bpartner, docType);

		final String adLanguage = bpartner.getAD_Language();
		final Language language = Check.isNotBlank(adLanguage) ? Language.getLanguage(adLanguage) : null;

		return StandardDocumentReportInfo.builder()
				.recordRef(TableRecordReference.of(I_C_Order.Table_Name, orderId))
				.printFormatId(printFormatId)
				.reportProcessId(getReportProcessIdByPrintFormatId(printFormatId))
				.copies(documentCopies)
				.documentNo(order.getDocumentNo())
				.bpartnerId(bpartnerId)
				.docTypeId(docTypeId)
				.language(language)
				.printOptions(getDocumentPrintOptions(order))
				.build();
	}

	private DocTypeId extractDocTypeId(@NonNull final I_C_Order order)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocType_ID());
		if (docTypeId != null)
		{
			return docTypeId;
		}

		final DocTypeId docTypeTargetId = DocTypeId.ofRepoIdOrNull(order.getC_DocTypeTarget_ID());
		if (docTypeTargetId != null)
		{
			return docTypeTargetId;
		}

		throw new AdempiereException("No document type set");
	}

	private DocumentPrintOptions getDocumentPrintOptions(@NonNull final I_C_Order order)
	{
		final boolean isPrintTotals = true; // TODO introduce C_Order.IsPrintTotals

		return DocumentPrintOptions.builder()
				.option(DocumentPrintOptions.OPTION_IsPrintTotals, isPrintTotals)
				.build();
	}


}
