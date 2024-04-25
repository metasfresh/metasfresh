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
import de.metas.currency.ICurrencyBL;
import de.metas.document.DocTypeId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.NewInvoiceCandidate;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.externallyreferenced.InvoiceCandidateRepository;
import de.metas.invoicecandidate.externallyreferenced.ManualCandidateService;
import de.metas.invoicecandidate.model.I_I_Invoice_Candidate;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.InvoiceRule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.impl.PaymentTermQuery;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;

@Service
public class ImportInvoiceCandidatesService
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
	private final IInvoiceCandidateHandlerDAO invoiceCandidateHandlerDAO = Services.get(IInvoiceCandidateHandlerDAO.class);
	private final ManualCandidateService manualCandidateService;
	private final InvoiceCandidateRepository invoiceCandidateRepository;

	public ImportInvoiceCandidatesService(
			@NonNull final ManualCandidateService manualCandidateService,
			@NonNull final InvoiceCandidateRepository invoiceCandidateRepository)
	{
		this.manualCandidateService = manualCandidateService;
		this.invoiceCandidateRepository = invoiceCandidateRepository;
	}

	@NonNull
	public InvoiceCandidateId createInvoiceCandidate(@NonNull final I_I_Invoice_Candidate record)
	{
		final NewInvoiceCandidate newInvoiceCandidate = createManualInvoiceCand(record);

		return invoiceCandidateRepository.save(manualCandidateService.createInvoiceCandidate(newInvoiceCandidate));
	}

	@NonNull
	private NewInvoiceCandidate createManualInvoiceCand(@NonNull final I_I_Invoice_Candidate record)
	{
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());
		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());

		final Function<BigDecimal, StockQtyAndUOMQty> createQtyInStockAndUOM = (qty) -> StockQtyAndUOMQtys.createConvert(
				Quantitys.of(qty, uomId),
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
				.map(date -> TimeUtil.asLocalDate(date, orgZoneId))
				.orElseGet(() -> computeDateOrderedBasedOnPresetDateInvoiced(orgZoneId, record.getPresetDateInvoiced()));

		final BPartnerInfo billPartnerInfo = getBPartnerInfo(record);
		final SOTrx soTrx = SOTrx.ofBoolean(record.isSOTrx());

		final PaymentTermQuery paymentTermQuery = PaymentTermQuery.forPartner(billPartnerInfo.getBpartnerId(), soTrx);
		final PaymentTermId paymentTermId = paymentTermRepository.retrievePaymentTermIdNotNull(paymentTermQuery);

		return NewInvoiceCandidate.builder()
				.externalHeaderId(ExternalId.ofOrNull(record.getExternalHeaderId()))
				.externalLineId(ExternalId.ofOrNull(record.getExternalLineId()))
				.poReference(record.getPOReference())
				.lineDescription(record.getDescription())

				.dateOrdered(dateOrdered)
				.presetDateInvoiced(TimeUtil.asLocalDate(record.getPresetDateInvoiced(), orgZoneId))

				.orgId(orgId)
				.billPartnerInfo(billPartnerInfo)
				.invoiceRule(InvoiceRule.ofNullableCode(record.getInvoiceRule()))
				.invoiceDocTypeId(DocTypeId.ofRepoId(record.getC_DocType_ID()))

				.productId(productId)
				.invoicingUomId(uomId)
				.qtyOrdered(qtyOrdered)
				.qtyDelivered(qtyDelivered)

				.priceEnteredOverride(getPriceEnteredOverride(record).orElse(null))
				.discountOverride(Percent.ofNullable(record.getDiscount()))

				.descriptionBottom(record.getDescriptionBottom())
				.userInChargeId(UserId.ofRepoIdOrNull(record.getAD_User_InCharge_ID()))
				.recordReference(recordReference)
				.soTrx(soTrx)
				.activityId(ActivityId.ofRepoIdOrNull(record.getC_Activity_ID()))
				.paymentTermId(paymentTermId)
				.isManual(true)
				.handlerId(invoiceCandidateHandlerDAO.retrieveIdForClassOneOnly(ManualCandidateHandler.class))
				.build();
	}

	@NonNull
	private static BPartnerInfo getBPartnerInfo(@NonNull final I_I_Invoice_Candidate invoiceCandidateToImport)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(invoiceCandidateToImport.getBill_BPartner_ID());

		return BPartnerInfo.builder()
				.bpartnerId(bPartnerId)
				.contactId(BPartnerContactId.ofRepoIdOrNull(bPartnerId, invoiceCandidateToImport.getBill_User_ID()))
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(bPartnerId, invoiceCandidateToImport.getBill_Location_ID()))
				.build();
	}

	@NonNull
	private LocalDate computeDateOrderedBasedOnPresetDateInvoiced(@NonNull final ZoneId zoneId, @Nullable final Timestamp presetDateInvoiced)
	{
		final LocalDate today = LocalDate.now(zoneId);

		if (presetDateInvoiced == null)
		{
			return today;
		}

		final LocalDate presetInvoiceDate = TimeUtil.asLocalDate(presetDateInvoiced, zoneId);

		if (presetInvoiceDate.isBefore(today))
		{
			return presetInvoiceDate;
		}

		return today;
	}

	@NonNull
	private Optional<ProductPrice> getPriceEnteredOverride(@NonNull final I_I_Invoice_Candidate record)
	{
		if (record.getPrice() == null || record.getPrice().signum() == 0)
		{
			return Optional.empty();
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(record);
		final CurrencyId baseCurrencyId = currencyBL.getBaseCurrency(ctx).getId(); //we're assuming that uses the same currency as the current one product price
		final ProductPrice priceEnteredOverride = ProductPrice.builder()
				.money(Money.ofOrNull(record.getPrice(), baseCurrencyId))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.uomId(UomId.ofRepoId(record.getC_UOM_ID()))
				.build();

		return Optional.of(priceEnteredOverride);
	}
}
