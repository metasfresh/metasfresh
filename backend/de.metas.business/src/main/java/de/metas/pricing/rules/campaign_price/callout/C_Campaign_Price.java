package de.metas.pricing.rules.campaign_price.callout;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.pricing.service.impl.PlainPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.Callout.RecursionAvoidanceLevel;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Campaign_Price;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

import static java.math.BigDecimal.ONE;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Interceptor(I_C_Campaign_Price.class)
@Callout(value = I_C_Campaign_Price.class, recursionAvoidanceLevel = RecursionAvoidanceLevel.CalloutClass)
@Component
public class C_Campaign_Price
{
	private final IBPartnerDAO bpartnerRepo = Services.get(IBPartnerDAO.class);
	private final ICountryDAO countriesRepo = Services.get(ICountryDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private static final AdMessageKey MSG_ProductNotOnPriceList = AdMessageKey.of("ProductNotOnPriceList");
	private static final AdMessageKey ERR_MandatoryFields = AdMessageKey.of("C_Campaign_Price_MandatoryFields");

	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
		CopyRecordFactory.enableForTableName(I_C_Campaign_Price.Table_Name);
	}

	@CalloutMethod(columnNames = I_C_Campaign_Price.COLUMNNAME_C_BPartner_ID, skipIfCopying = true)
	public void onBPartnerChanged(final I_C_Campaign_Price record)
	{
		if (record == null)
		{
			return;
		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return;
		}

		updatePricingInfo(record);
		record.setC_BP_Group_ID(-1);
	}

	private void updatePricingInfo(final I_C_Campaign_Price record)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return;
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(record.getM_Product_ID());
		if (productId == null)
		{
			return;
		}

		final CountryId countryId = bpartnerRepo.getDefaultShipToLocationCountryIdOrNull(bpartnerId);
		if (countryId != null)
		{
			record.setC_Country_ID(countryId.getRepoId());
			updateCurrency(record, countryId);
		}

		final PricingSystemId pricingSystemId = bpartnerRepo.retrievePricingSystemIdOrNull(bpartnerId, SOTrx.SALES);
		if (pricingSystemId != null)
		{
			record.setM_PricingSystem_ID(pricingSystemId.getRepoId());

			final PriceListId priceListId = priceListDAO.retrievePriceListIdByPricingSyst(pricingSystemId, countryId, SOTrx.SALES);
			if (priceListId != null)
			{
				final OrgId orgId = OrgId.ofRepoIdOrAny(record.getAD_Org_ID());
				final ZoneId timeZone = orgDAO.getTimeZone(orgId);
				final IEditablePricingContext pricingContext = pricingBL
						.createInitialContext(
								orgId,
								productId,
								bpartnerId,
								Quantitys.of(ONE, productId),
								SOTrx.SALES)
						.setCountryId(countryId)
						.setPriceDate(TimeUtil.asLocalDate(record.getValidFrom(), timeZone));
				final IPricingResult pricingResult = pricingBL.calculatePrice(pricingContext);
				if (!pricingResult.isCalculated())
				{
					throw new AdempiereException(MSG_ProductNotOnPriceList)
							.setParameter("pricingContext", pricingContext)
							.setParameter("pricingResult", pricingResult);
				}

				record.setC_TaxCategory_ID(pricingResult.getTaxCategoryId().getRepoId());
				record.setInvoicableQtyBasedOn(pricingResult.getInvoicableQtyBasedOn().getCode());
			}
		}
		else
		{
			record.setM_PricingSystem_ID(-1);
		}
	}

	@CalloutMethod(columnNames = I_C_Campaign_Price.COLUMNNAME_C_BP_Group_ID, skipIfCopying = true)
	public void onBPGroupChanged(final I_C_Campaign_Price record)
	{
		if (record == null)
		{
			return;
		}
		final BPGroupId bpGroupId = BPGroupId.ofRepoIdOrNull(record.getC_BP_Group_ID());
		if (bpGroupId == null)
		{
			return;
		}

		record.setC_BPartner_ID(-1);
	}

	@CalloutMethod(columnNames = I_C_Campaign_Price.COLUMNNAME_C_Country_ID, skipIfCopying = true)
	public void onCountryChanged(final I_C_Campaign_Price record)
	{
		final CountryId countryId = CountryId.ofRepoIdOrNull(record.getC_Country_ID());
		if (countryId == null)
		{
			return;
		}

		updateCurrency(record, countryId);
	}

	private void updateCurrency(final I_C_Campaign_Price record, final CountryId countryId)
	{
		countriesRepo.getCountryCurrencyId(countryId).ifPresent(currencyId -> record.setC_Currency_ID(currencyId.getRepoId()));
	}

	@CalloutMethod(columnNames = I_C_Campaign_Price.COLUMNNAME_M_Product_ID, skipIfCopying = true)
	public void onProductChanged(final I_C_Campaign_Price record)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(record.getM_Product_ID());
		if (productId == null)
		{
			return;
		}

		final UomId stockUomId = productBL.getStockUOMId(productId);
		record.setC_UOM_ID(stockUomId.getRepoId());

		updatePricingInfo(record);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_Campaign_Price.COLUMNNAME_C_BPartner_ID, I_C_Campaign_Price.COLUMNNAME_C_BP_Group_ID, I_C_Campaign_Price.COLUMNNAME_M_PricingSystem_ID })
	public void beforeSave(final I_C_Campaign_Price record)
	{
		if (record.getC_BPartner_ID() <= 0 && record.getC_BP_Group_ID() <= 0 && record.getM_PricingSystem_ID() <= 0)
		{
			throw new AdempiereException(ERR_MandatoryFields);
		}
	}
}
