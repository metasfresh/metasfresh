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

package de.metas.invoicecandidate.externallyreferenced;

import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.common.util.CoalesceUtil;
import de.metas.invoicecandidate.ContractSpecificPrice;
import de.metas.invoicecandidate.NewInvoiceCandidate;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.money.Money;
import de.metas.order.InvoiceRule;
import de.metas.organization.IOrgDAO;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductPrice;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.VatCodeId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class ManualCandidateService
{
	private final BPartnerCompositeRepository bPartnerCompositeRepository;

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public ManualCandidateService(@NonNull final BPartnerCompositeRepository bPartnerCompositeRepository)
	{
		this.bPartnerCompositeRepository = bPartnerCompositeRepository;
	}

	/**
	 * Invokes different metasfresh services to complement additional fields such as the price.
	 */
	public InvoiceCandidate createInvoiceCandidate(@NonNull final NewInvoiceCandidate newIC)
	{
		final InvoiceCandidate.InvoiceCandidateBuilder candidate = InvoiceCandidate.createBuilder(newIC);

		final ProductPrice priceEnteredOverride = newIC.getPriceEnteredOverride();
		final Percent discountOverride = newIC.getDiscountOverride();

		final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

		final BPartnerComposite bpartnerComp = bPartnerCompositeRepository.getById(newIC.getBillPartnerInfo().getBpartnerId());
		final BPartnerLocation location = bpartnerComp.extractLocation(newIC.getBillPartnerInfo().getBpartnerLocationId()).get();
		final CountryId countryId = countryDAO.getCountryIdByCountryCode(location.getCountryCode());

		final ContractSpecificPrice contractSpecificPrice = newIC.getContractSpecificPrice();

		final TaxCategoryId taxCategoryId;
		if (contractSpecificPrice != null)
		{
			taxCategoryId = contractSpecificPrice.getTaxCategoryId();
			candidate.priceEntered(contractSpecificPrice.getProductPrice());
			candidate.priceActual(contractSpecificPrice.getProductPrice());
			candidate.pricingSystemId((contractSpecificPrice.getPricingSystemId()));
			candidate.discount(Percent.ZERO);


		}
		else
		{
			final IPricingBL pricingBL = Services.get(IPricingBL.class);
			final IEditablePricingContext pricingContext = pricingBL
					.createInitialContext(
							newIC.getOrgId(),
							newIC.getProductId(),
							newIC.getBillPartnerInfo().getBpartnerId(),
							newIC.getQtyOrdered().getStockQty(),
							newIC.getSoTrx())
					.setCountryId(countryId)
					.setPriceDate(newIC.getDateOrdered())
					.setFailIfNotCalculated();

			final IPricingResult pricingResult = pricingBL.calculatePrice(pricingContext);

			candidate.pricingSystemId(pricingResult.getPricingSystemId());
			candidate.priceListVersionId(pricingResult.getPriceListVersionId());

			final ProductPrice priceEntered = ProductPrice.builder()
					.money(Money.of(pricingResult.getPriceStd(), pricingResult.getCurrencyId()))
					.productId(pricingResult.getProductId())
					.uomId(pricingResult.getPriceUomId())
					.build();
			candidate.priceEntered(priceEntered);
			candidate.discount(pricingResult.getDiscount());

			candidate.priceEnteredOverride(priceEnteredOverride);
			candidate.discountOverride(discountOverride);

			final BigDecimal priceActualBD = pricingResult.getDiscount()
					.subtractFromBase(
							pricingResult.getPriceStd(),
							pricingResult.getPrecision().toInt());
			final ProductPrice priceActual = ProductPrice.builder()
					.money(Money.of(priceActualBD, pricingResult.getCurrencyId()))
					.productId(pricingResult.getProductId())
					.uomId(pricingResult.getPriceUomId())
					.build();
			candidate.priceActual(priceActual);

			taxCategoryId = pricingResult.getTaxCategoryId();
		}

		final ZoneId timeZone = orgDAO.getTimeZone(newIC.getOrgId());
		final VatCodeId vatCodeId = null;

		final TaxId taxId = Services.get(ITaxBL.class).getTaxNotNull(
				newIC,
				taxCategoryId,
				newIC.getProductId().getRepoId(),
				TimeUtil.asTimestamp(newIC.getDateOrdered(), timeZone), // shipDate
				newIC.getOrgId(),
				newIC.getSoTrx().isSales() ? orgDAO.getOrgWarehouseId(newIC.getOrgId()) : orgDAO.getOrgPOWarehouseId(newIC.getOrgId()),
				newIC.getBillPartnerInfo().toBPartnerLocationAndCaptureId(), // ship location id
				newIC.getSoTrx(),
				vatCodeId);
		candidate.taxId(taxId);

		final BPartner bpartner = bpartnerComp.getBpartner();

		final InvoiceRule newICInvoiceRule = Optional.ofNullable(newIC.getInvoiceRule())
				.orElseGet(() -> newIC.getSoTrx().isSales() ?
						bpartner.getCustomerInvoiceRule() :
						bpartner.getVendorInvoiceRule());

		candidate.invoiceRule(CoalesceUtil.coalesceNotNull(newICInvoiceRule, InvoiceRule.Immediate));
		candidate.recordReference(newIC.getRecordReference());

		candidate.descriptionBottom(newIC.getDescriptionBottom());
		candidate.userInChargeId(newIC.getUserInChargeId());

		return candidate.build();

	}
}
