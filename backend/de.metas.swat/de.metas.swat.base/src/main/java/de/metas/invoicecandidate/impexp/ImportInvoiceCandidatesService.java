/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.invoicecandidate.impexp;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.document.DocTypeId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.externallyreferenced.ExternallyReferencedCandidateRepository;
import de.metas.invoicecandidate.externallyreferenced.ManualCandidateService;
import de.metas.invoicecandidate.externallyreferenced.NewManualInvoiceCandidate;
import de.metas.lang.SOTrx;
import de.metas.order.InvoiceRule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_I_Invoice_Candidate;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class ImportInvoiceCandidatesService
{
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final ManualCandidateService manualCandidateService;
	private final ExternallyReferencedCandidateRepository externallyReferencedCandidateRepository;

	public ImportInvoiceCandidatesService(
			@NonNull final ManualCandidateService manualCandidateService,
			@NonNull final ExternallyReferencedCandidateRepository externallyReferencedCandidateRepository)
	{
		this.manualCandidateService = manualCandidateService;
		this.externallyReferencedCandidateRepository = externallyReferencedCandidateRepository;
	}

	@NonNull
	public InvoiceCandidateId createInvoiceCandidate(@NonNull final I_I_Invoice_Candidate record)
	{
		final NewManualInvoiceCandidate newManualInvoiceCandidate = createManualInvoiceCand(record);

		return externallyReferencedCandidateRepository.save(manualCandidateService.createInvoiceCandidate(newManualInvoiceCandidate));
	}

	@NonNull
	private NewManualInvoiceCandidate createManualInvoiceCand(@NonNull final I_I_Invoice_Candidate record)
	{
		final BPartnerInfo bPartnerInfo = BPartnerInfo.builder()
				.bpartnerId(BPartnerId.ofRepoId(record.getBill_BPartner_ID()))
				.contactId(BPartnerContactId.ofRepoId(record.getBill_BPartner_ID(), record.getBill_User_ID()))
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(record.getBill_BPartner_ID(), record.getBill_Location_ID()))
				.build();

		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
		final StockQtyAndUOMQty qtyOrdered = StockQtyAndUOMQtys.createConvert(
				Quantitys.create(record.getQtyOrdered(), uomId),
				productId,
				uomId);

		final StockQtyAndUOMQty qtyDelivered = record.getQtyDelivered() != null
				? StockQtyAndUOMQtys.createConvert(
				Quantitys.create(record.getQtyDelivered(), uomId),
				productId,
				uomId)
				: null;

		final NewManualInvoiceCandidate.NewManualInvoiceCandidateBuilder candidate = NewManualInvoiceCandidate.builder();

		final int adTableId = tableDAO.retrieveAdTableId(I_I_Invoice_Candidate.Table_Name).getRepoId();
		final int recordId = record.getI_Invoice_Candidate_ID();

		final TableRecordReference recordReference = TableRecordReference.of(adTableId, recordId);

		final ZoneId orgZoneId = orgDAO.getTimeZone(OrgId.ofRepoId(record.getAD_Org_ID()));
		final LocalDate dateOrdered = record.getDateOrdered() == null
				? LocalDate.now()
				: TimeUtil.asLocalDate(record.getDateOrdered(),orgZoneId);

		return candidate
				.dateOrdered(dateOrdered)
				.billPartnerInfo(bPartnerInfo)
				.externalHeaderId(ExternalId.ofOrNull(record.getExternalHeaderId()))
				.externalLineId(ExternalId.ofOrNull(record.getExternalLineId()))
				.invoicingUomId(uomId)
				.qtyOrdered(qtyOrdered)
				.qtyDelivered(qtyDelivered)
				.productId(productId)
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.invoiceRule(InvoiceRule.ofNullableCode(record.getInvoiceRule()))
				.presetDateInvoiced(TimeUtil.asLocalDate(record.getPresetDateInvoiced(), orgZoneId))
				.soTrx(SOTrx.ofBoolean(record.isSOTrx()))
				.poReference(record.getPOReference())
				.lineDescription(record.getDescription())
				.recordReference(recordReference)
				.invoiceDocTypeId(DocTypeId.ofRepoId(record.getC_DocType_ID()))
				.build();
	}
}
