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
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPPrintFormatQuery;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import de.metas.report.DocumentPrintOptions;
import de.metas.report.DocumentReportAdvisor;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.DocumentReportInfo;
import de.metas.report.PrintFormatId;
import de.metas.report.StandardDocumentReportType;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.X_C_DocType;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class OrderDocumentReportAdvisor implements DocumentReportAdvisor
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final DocumentReportAdvisorUtil util;

	public OrderDocumentReportAdvisor(@NonNull final DocumentReportAdvisorUtil util)
	{
		this.util = util;
	}

	@Override
	@NonNull
	public String getHandledTableName()
	{
		return I_C_Order.Table_Name;
	}

	@Override
	public StandardDocumentReportType getStandardDocumentReportType()
	{
		return StandardDocumentReportType.ORDER;
	}

	@Override
	@NonNull
	public DocumentReportInfo getDocumentReportInfo(
			@NonNull final TableRecordReference recordRef,
			@Nullable final PrintFormatId adPrintFormatToUseId, final AdProcessId reportProcessIdToUse)
	{
		final OrderId orderId = recordRef.getIdAssumingTableName(I_C_Order.Table_Name, OrderId::ofRepoId);
		final I_C_Order order = orderBL.getById(orderId);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final I_C_BPartner bpartner = util.getBPartnerById(bpartnerId);

		final DocTypeId docTypeId = extractDocTypeId(order);
		final I_C_DocType docType = util.getDocTypeById(docTypeId);

		final ClientId clientId = ClientId.ofRepoId(order.getAD_Client_ID());

		final PrintFormatId printFormatId = CoalesceUtil.coalesceSuppliers(
				() -> adPrintFormatToUseId,
				() -> util.getBPartnerPrintFormats(bpartnerId).getPrintFormatIdByDocTypeId(docTypeId).orElse(null),
				() -> PrintFormatId.ofRepoIdOrNull(docType.getAD_PrintFormat_ID()),
				() -> util.getDefaultPrintFormats(clientId).getOrderPrintFormatId());
		if (printFormatId == null)
		{
			throw new AdempiereException("@NotFound@ @AD_PrintFormat_ID@");
		}

		final Language language = util.getBPartnerLanguage(bpartner).orElse(null);

		final BPPrintFormatQuery bpPrintFormatQuery = BPPrintFormatQuery.builder()
				.adTableId(recordRef.getAdTableId())
				.bpartnerId(bpartnerId)
				.bPartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, order.getC_BPartner_Location_ID()))
				.docTypeId(docTypeId)
				.onlyCopiesGreaterZero(true)
				.build();

		return DocumentReportInfo.builder()
				.recordRef(TableRecordReference.of(I_C_Order.Table_Name, orderId))
				.reportProcessId(util.getReportProcessIdByPrintFormatId(printFormatId))
				.copies(util.getDocumentCopies(docType, bpPrintFormatQuery))
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
		final OptionalBoolean printTotals = getPrintTotalsOption(order);
		if (printTotals.isPresent())
		{
			return DocumentPrintOptions.builder()
					.sourceName("Order document: C_Order_ID=" + order.getC_Order_ID())
					.option(DocumentPrintOptions.OPTION_IsPrintTotals, printTotals.isTrue())
					.build();
		}
		else
		{
			return DocumentPrintOptions.NONE;
		}
	}

	private OptionalBoolean getPrintTotalsOption(@NonNull final I_C_Order order)
	{
		final String docSubType = order.getOrderType();
		if (X_C_DocType.DOCSUBTYPE_Proposal.equals(docSubType)
				|| X_C_DocType.DOCSUBTYPE_Quotation.equals(docSubType))
		{
			return StringUtils.toOptionalBoolean(order.getPRINTER_OPTS_IsPrintTotals());
		}
		else
		{
			return OptionalBoolean.UNKNOWN;
		}
	}

}
