/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.report;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPPrintFormat;
import de.metas.bpartner.service.BPPrintFormatQuery;
import de.metas.bpartner.service.BPartnerPrintFormatMap;
import de.metas.bpartner.service.BPartnerPrintFormatRepository;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID;

@Component
public class DocumentReportAdvisorUtil
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IBPartnerBL bpartnerBL;
	private final PrintFormatRepository printFormatRepository;
	private final DefaultPrintFormatsRepository defaultPrintFormatsRepository;
	private final BPartnerPrintFormatRepository bPartnerPrintFormatRepository;

	public DocumentReportAdvisorUtil(
			@NonNull final IBPartnerBL bpartnerBL,
			@NonNull final PrintFormatRepository printFormatRepository,
			@NonNull final DefaultPrintFormatsRepository defaultPrintFormatsRepository,
			@NonNull final BPartnerPrintFormatRepository bPartnerPrintFormatRepository)
	{
		this.bpartnerBL = bpartnerBL;
		this.printFormatRepository = printFormatRepository;
		this.defaultPrintFormatsRepository = defaultPrintFormatsRepository;
		this.bPartnerPrintFormatRepository = bPartnerPrintFormatRepository;
	}

	public Optional<BPartnerId> getBPartnerIdForModel(@NonNull final Object model)
	{
		return bpartnerBL.getBPartnerIdForModel(model);
	}

	public I_C_BPartner getBPartnerById(@NonNull final BPartnerId bpartnerId)
	{
		return bpartnerBL.getById(bpartnerId);
	}

	@Nullable
	public BPartnerLocationId getBPartnerLocationId(@Nullable final BPartnerId bPartnerId, @NonNull final Object model)
	{
		final Integer locationId = InterfaceWrapperHelper.getValueOrNull(model, COLUMNNAME_C_BPartner_Location_ID);
		return BPartnerLocationId.ofRepoIdOrNull(bPartnerId, locationId);
	}

	public Optional<Language> getBPartnerLanguage(@NonNull final I_C_BPartner bpartner)
	{
		return bpartnerBL.getLanguage(bpartner);
	}

	public BPartnerPrintFormatMap getBPartnerPrintFormats(final BPartnerId bpartnerId)
	{
		return bpartnerBL.getPrintFormats(bpartnerId);
	}

	@NonNull
	public DefaultPrintFormats getDefaultPrintFormats(@NonNull final ClientId clientId)
	{
		return defaultPrintFormatsRepository.getByClientId(clientId);
	}

	@NonNull
	public AdProcessId getReportProcessIdByPrintFormatId(@NonNull final PrintFormatId printFormatId)
	{
		return getReportProcessIdByPrintFormatIdIfExists(printFormatId).get();
	}

	@NonNull
	public ExplainedOptional<AdProcessId> getReportProcessIdByPrintFormatIdIfExists(@NonNull final PrintFormatId printFormatId)
	{
		final PrintFormat printFormat = printFormatRepository.getById(printFormatId);
		final AdProcessId reportProcessId = printFormat.getReportProcessId();
		return reportProcessId != null
				? ExplainedOptional.of(reportProcessId)
				: ExplainedOptional.emptyBecause("No report process defined by " + printFormat);
	}

	public I_C_DocType getDocTypeById(@NonNull final DocTypeId docTypeId)
	{
		return docTypeDAO.getById(docTypeId);
	}

	public PrintCopies getDocumentCopies(
			@Nullable final I_C_DocType docType,
			@Nullable final BPPrintFormatQuery bpPrintFormatQuery)
	{

		final BPPrintFormat bpPrintFormat = bpPrintFormatQuery == null ? null : bPartnerPrintFormatRepository.getByQuery(bpPrintFormatQuery);
		if(bpPrintFormat == null)
		{
			return getDocumentCopies(docType);
		}
		return bpPrintFormat.getPrintCopies();
	}

	private static PrintCopies getDocumentCopies(@Nullable final I_C_DocType docType)
	{
		return docType != null && !InterfaceWrapperHelper.isNull(docType, I_C_DocType.COLUMNNAME_DocumentCopies)
				? PrintCopies.ofInt(docType.getDocumentCopies())
				: PrintCopies.ONE;
	}
}
