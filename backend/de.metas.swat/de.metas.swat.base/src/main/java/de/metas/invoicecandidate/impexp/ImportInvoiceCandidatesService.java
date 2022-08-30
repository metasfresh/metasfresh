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
import de.metas.invoicecandidate.model.I_I_Invoice_Candidate;
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
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ImportInvoiceCandidatesService
{
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
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());
		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());

		final Function<BigDecimal, StockQtyAndUOMQty> createQtyInStockAndUOM = (qty) -> StockQtyAndUOMQtys.createConvert(
				Quantitys.create(qty, uomId),
				productId,
				uomId);

		final StockQtyAndUOMQty qtyOrdered = createQtyInStockAndUOM.apply(record.getQtyOrdered());

		final StockQtyAndUOMQty qtyDelivered = Optional.ofNullable(record.getQtyDelivered())
				.map(createQtyInStockAndUOM)
				.orElse(null);

		final TableRecordReference recordReference = TableRecordReference.of(record);

		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());
		final ZoneId orgZoneId = orgDAO.getTimeZone(orgId);

		final LocalDate dateOrdered = Optional.ofNullable(record.getDateOrdered())
				.map(date -> TimeUtil.asLocalDate(date,orgZoneId))
				.orElseGet(() -> LocalDate.now(orgZoneId));

		return NewManualInvoiceCandidate.builder()
				.externalHeaderId(ExternalId.ofOrNull(record.getExternalHeaderId()))
				.externalLineId(ExternalId.ofOrNull(record.getExternalLineId()))
				.poReference(record.getPOReference())
				.lineDescription(record.getDescription())

				.dateOrdered(dateOrdered)
				.presetDateInvoiced(TimeUtil.asLocalDate(record.getPresetDateInvoiced(), orgZoneId))

				.orgId(orgId)
				.billPartnerInfo(getBPartnerInfo(record))
				.invoiceRule(InvoiceRule.ofNullableCode(record.getInvoiceRule()))
				.invoiceDocTypeId(DocTypeId.ofRepoId(record.getC_DocType_ID()))

				.productId(productId)
				.invoicingUomId(uomId)
				.qtyOrdered(qtyOrdered)
				.qtyDelivered(qtyDelivered)

				.recordReference(recordReference)
				.soTrx(SOTrx.ofBoolean(record.isSOTrx()))
				.build();
	}

	@NonNull
	private static BPartnerInfo getBPartnerInfo(@NonNull final I_I_Invoice_Candidate invoiceCandidateToImport)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(invoiceCandidateToImport.getBill_BPartner_ID());

		return BPartnerInfo.builder()
				.bpartnerId(bPartnerId)
				.contactId(BPartnerContactId.ofRepoId(bPartnerId, invoiceCandidateToImport.getBill_User_ID()))
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(bPartnerId, invoiceCandidateToImport.getBill_Location_ID()))
				.build();
	}
}
