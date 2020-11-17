/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.api;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import de.metas.report.DefaultPrintFormatsRepository;
import de.metas.report.DocumentReportAdvisor;
import de.metas.report.PrintFormatId;
import de.metas.report.StandardDocumentReportInfo;
import de.metas.report.StandardDocumentReportType;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.print.MPrintFormat;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class DistributionOrderDocumentReportAdvisor implements DocumentReportAdvisor
{
	private final IDDOrderBL ddOrderBL = Services.get(IDDOrderBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IBPartnerBL bpartnerBL;
	private final DefaultPrintFormatsRepository defaultPrintFormatsRepository;

	public DistributionOrderDocumentReportAdvisor(
			@NonNull final IBPartnerBL bpartnerBL,
			@NonNull final DefaultPrintFormatsRepository defaultPrintFormatsRepository)
	{
		this.bpartnerBL = bpartnerBL;
		this.defaultPrintFormatsRepository = defaultPrintFormatsRepository;
	}

	@Override
	public @NonNull String getHandledTableName()
	{
		return I_DD_Order.Table_Name;
	}

	@Override
	public StandardDocumentReportType getStandardDocumentReportType()
	{
		return StandardDocumentReportType.DISTRIBUTION_ORDER;
	}

	@Override
	public @NonNull StandardDocumentReportInfo getDocumentReportInfo(
			final @NonNull StandardDocumentReportType type,
			final int recordId,
			@Nullable final PrintFormatId adPrintFormatToUseId)
	{
		Check.assumeEquals(type, StandardDocumentReportType.DISTRIBUTION_ORDER, "type");

		final int ddOrderId = recordId;
		final I_DD_Order ddOrder = ddOrderBL.getById(ddOrderId);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(ddOrder.getC_BPartner_ID());
		final I_C_BPartner bpartner = bpartnerBL.getById(bpartnerId);

		final DocTypeId docTypeId = extractDocTypeId(ddOrder);
		final I_C_DocType docType = docTypeDAO.getById(docTypeId);

		final ClientId clientId = ClientId.ofRepoId(ddOrder.getAD_Client_ID());

		final PrintFormatId printFormatId = CoalesceUtil.coalesceSuppliers(
				() -> adPrintFormatToUseId,
				() -> bpartnerBL.getPrintFormats(bpartnerId).getPrintFormatIdByDocTypeId(docTypeId).orElse(null),
				() -> PrintFormatId.ofRepoIdOrNull(docType.getAD_PrintFormat_ID()),
				() -> defaultPrintFormatsRepository.getByClientId(clientId).getDistributionOrderPrintFormatId());
		if (printFormatId == null)
		{
			throw new AdempiereException("@NotFound@ @AD_PrintFormat_ID@");
		}

		final int documentCopies = getDocumentCopies(docType, 0) + getDocumentCopies(bpartner, 1); // for now, preserving the legacy logic

		final String adLanguage = bpartner.getAD_Language();
		final Language language = Check.isNotBlank(adLanguage) ? Language.getLanguage(adLanguage) : null;

		return StandardDocumentReportInfo.builder()
				.recordRef(TableRecordReference.of(I_DD_Order.Table_Name, ddOrderId))
				.printFormatId(printFormatId)
				.reportProcessId(getReportProcessIdByPrintFormatId(printFormatId))
				.copies(documentCopies)
				.documentNo(ddOrder.getDocumentNo())
				.bpartnerId(bpartnerId)
				.docTypeId(docTypeId)
				.language(language)
				.build();
	}

	private DocTypeId extractDocTypeId(@NonNull final I_DD_Order ddOrder)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(ddOrder.getC_DocType_ID());
		if (docTypeId != null)
		{
			return docTypeId;
		}
		throw new AdempiereException("No document type set");
	}

	private Integer getDocumentCopies(@NonNull final I_C_BPartner bpartner, final Integer defaultValue)
	{
		return !InterfaceWrapperHelper.isNull(bpartner, I_C_BPartner.COLUMNNAME_DocumentCopies)
				? bpartner.getDocumentCopies()
				: defaultValue;
	}

	private Integer getDocumentCopies(@NonNull final I_C_DocType docType, final Integer defaultValue)
	{
		return !InterfaceWrapperHelper.isNull(docType, I_C_BPartner.COLUMNNAME_DocumentCopies)
				? docType.getDocumentCopies()
				: defaultValue;
	}

	private AdProcessId getReportProcessIdByPrintFormatId(@NonNull final PrintFormatId printFormatId)
	{
		final MPrintFormat adPrintFormat = MPrintFormat.get(Env.getCtx(), printFormatId.getRepoId(), false);
		if (adPrintFormat == null)
		{
			throw new AdempiereException("No print format found for AD_PrintFormat_ID=" + printFormatId);
		}

		final AdProcessId reportProcessId = AdProcessId.ofRepoIdOrNull(adPrintFormat.getJasperProcess_ID());
		if (reportProcessId == null)
		{
			throw new AdempiereException("No report process found");
		}

		return reportProcessId;
	}
}
