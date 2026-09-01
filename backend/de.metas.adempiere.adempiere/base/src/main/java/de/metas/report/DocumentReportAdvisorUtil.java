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
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BP_PrintFormat;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
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

	@NonNull
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

	/**
	 * Pure computation (no DB access): is the given sales order a drop-shipment, i.e. does the goods
	 * recipient deviate from the order's own sold-to?
	 * <p>
	 * NOTE -- this is a <b>different</b> rule from the sales-order ultimate-consignee (UC) resolution
	 * documented in the {@code de.metas.business} module CLAUDE.md ("IsDropShip is irrelevant for UC
	 * resolution -- the presence of DropShip_* alone decides"). That rule answers "who is the goods
	 * recipient". This method answers a different question -- "should auto-print be suppressed for this
	 * shipment" -- and the approved design for that concern deliberately gates on {@code IsDropShip='Y'}
	 * in addition to the DropShip_* deviation. Do not "fix" this to match the UC rule.
	 */
	public boolean isDropShip(@Nullable final I_C_Order order)
	{
		if (order == null)
		{
			// manual shipment, no order to inspect -> not a drop-ship
			return false;
		}
		if (!order.isDropShip())
		{
			return false;
		}

		final BPartnerId orderBPartnerId = BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());
		final BPartnerId dropShipBPartnerId = BPartnerId.ofRepoIdOrNull(order.getDropShip_BPartner_ID());
		final boolean bPartnerDeviates = dropShipBPartnerId != null && !dropShipBPartnerId.equals(orderBPartnerId);

		// Both location ids are anchored on the order's own C_BPartner_ID. BPartnerLocationId always carries
		// a bpartnerId component, but here we only care whether the location repoId itself differs (matching
		// the semantics this replaces) -- anchoring both sides on the same bpartner id makes .equals() reduce
		// to comparing the location repoId alone.
		final BPartnerLocationId orderLocationId = BPartnerLocationId.ofRepoIdOrNull(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID());
		final BPartnerLocationId dropShipLocationId = BPartnerLocationId.ofRepoIdOrNull(order.getC_BPartner_ID(), order.getDropShip_Location_ID());
		final boolean locationDeviates = dropShipLocationId != null && !dropShipLocationId.equals(orderLocationId);

		return bPartnerDeviates || locationDeviates;
	}

	/**
	 * Resolves whether auto-print shall be suppressed for the given query, based on the matching
	 * {@code C_BP_PrintFormat} row's {@code IsAutoPrint} flag (read null-aware: only an explicit "N"
	 * suppresses; "Y", {@code null}, or no matching row at all does not).
	 * <p>
	 * This is a separate lookup from {@link #getDocumentCopies(I_C_DocType, BPPrintFormatQuery)} --
	 * it does not apply the {@code onlyCopiesGreaterZero} filter, since a row without a copies override
	 * can still carry a meaningful {@code IsAutoPrint} setting.
	 */
	public boolean resolveSuppressAutoPrint(@NonNull final BPPrintFormatQuery bpPrintFormatQuery)
	{
		final I_C_BP_PrintFormat bpPrintFormatRecord = bPartnerPrintFormatRepository.getRecordByQuery(bpPrintFormatQuery);
		if (bpPrintFormatRecord == null)
		{
			return false;
		}

		final String isAutoPrintValue = InterfaceWrapperHelper.getValueOrNull(bpPrintFormatRecord, I_C_BP_PrintFormat.COLUMNNAME_IsAutoPrint);
		final Boolean isAutoPrint = StringUtils.toBoolean(isAutoPrintValue, null);

		return Boolean.FALSE.equals(isAutoPrint);
	}
}
