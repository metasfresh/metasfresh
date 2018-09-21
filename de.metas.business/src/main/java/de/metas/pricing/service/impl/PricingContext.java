package de.metas.pricing.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.util.Env;

import de.metas.bpartner.BPartnerId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
class PricingContext implements IEditablePricingContext
{
	private PricingSystemId pricingSystemId;
	private PriceListId priceListId;

	private PriceListVersionId priceListVersionId;
	private I_M_PriceList_Version _priceListVersion = null;

	private boolean skipCheckingPriceListSOTrxFlag;

	private ProductId productId;

	/**
	 * PriceDate timestamp.
	 * NOTE: storing as milliseconds only because {@link Timestamp} is not an immutable class.
	 */
	private long priceDateTS = 0;
	/**
	 * "now" timestamp to be used when there was no priceDate set.
	 * NOTE: We are taking a snapshot of the current timestamp because we want to return the same value each time we are asked.
	 */
	private long priceDateNowTS = SystemTime.millis();

	private int C_Country_ID = 0;

	private int C_UOM_ID;
	private CurrencyId currencyId;
	private BPartnerId bpartnerId;
	private BigDecimal qty;
	private SOTrx soTrx = SOTrx.PURCHASE;
	private Object referencedObject;
	private String trxName;
	private boolean convertPriceToContextUOM;
	private Boolean isManualPrice = null; // task 08908: can be set by the calling code. Otherwise the engine shall try the referenced object
	private boolean failIfNotCalculated = false;

	private boolean disallowDiscount;
	@Getter
	private PricingConditionsBreak forcePricingConditionsBreak;

	final private Map<String, Object> properties = new HashMap<>();

	@Override
	public IEditablePricingContext copy()
	{
		final PricingContext pricingCtxNew = new PricingContext();
		pricingCtxNew.productId = productId;
		pricingCtxNew.pricingSystemId = pricingSystemId;
		pricingCtxNew.priceListId = priceListId;
		pricingCtxNew.priceListVersionId = priceListVersionId;
		pricingCtxNew._priceListVersion = _priceListVersion;
		pricingCtxNew.priceDateTS = priceDateTS;
		pricingCtxNew.priceDateNowTS = priceDateNowTS;
		pricingCtxNew.C_UOM_ID = C_UOM_ID;
		pricingCtxNew.currencyId = currencyId;
		pricingCtxNew.C_Country_ID = C_Country_ID;
		pricingCtxNew.bpartnerId = bpartnerId;
		pricingCtxNew.qty = qty;
		pricingCtxNew.soTrx = soTrx;
		pricingCtxNew.referencedObject = referencedObject;
		pricingCtxNew.disallowDiscount = disallowDiscount;
		pricingCtxNew.forcePricingConditionsBreak = forcePricingConditionsBreak;
		pricingCtxNew.trxName = trxName;
		pricingCtxNew.convertPriceToContextUOM = convertPriceToContextUOM;
		pricingCtxNew.isManualPrice = isManualPrice;
		pricingCtxNew.failIfNotCalculated = failIfNotCalculated;
		pricingCtxNew.skipCheckingPriceListSOTrxFlag = skipCheckingPriceListSOTrxFlag;
		pricingCtxNew.properties.putAll(properties);

		return pricingCtxNew;
	}

	@Override
	public PricingSystemId getPricingSystemId()
	{
		return pricingSystemId;
	}

	@Override
	public IEditablePricingContext setPricingSystemId(final PricingSystemId pricingSystemId)
	{
		this.pricingSystemId = pricingSystemId;
		return this;
	}

	@Override
	public ProductId getProductId()
	{
		return productId;
	}

	@Override
	public IEditablePricingContext setProductId(final ProductId productId)
	{
		this.productId = productId;
		return this;
	}

	@Override
	public PriceListId getPriceListId()
	{
		return priceListId;
	}

	@Override
	public IEditablePricingContext setPriceListId(final PriceListId priceListId)
	{
		this.priceListId = priceListId;
		return this;
	}

	@Override
	public PriceListVersionId getPriceListVersionId()
	{
		return priceListVersionId;
	}

	@Override
	public IEditablePricingContext setPriceListVersionId(final PriceListVersionId priceListVersionId)
	{
		if (Objects.equals(this.priceListVersionId, priceListVersionId))
		{
			return this;
		}

		this.priceListVersionId = priceListVersionId;
		_priceListVersion = null; // needs to be reloaded
		return this;
	}

	@Override
	public I_M_PriceList_Version getM_PriceList_Version()
	{
		final PriceListVersionId priceListVersionId = getPriceListVersionId();
		if (priceListVersionId == null)
		{
			return null;
		}

		if (_priceListVersion == null || _priceListVersion.getM_PriceList_Version_ID() != priceListVersionId.getRepoId())
		{
			_priceListVersion = Services.get(IPriceListDAO.class).getPriceListVersionById(priceListVersionId);
		}
		return _priceListVersion;
	}

	@Override
	public Timestamp getPriceDate()
	{
		return new Timestamp(priceDateTS <= 0 ? priceDateNowTS : priceDateTS);
	}

	@Override
	public IEditablePricingContext setPriceDate(final Timestamp priceDate)
	{
		priceDateTS = priceDate == null ? 0 : priceDate.getTime();
		return this;
	}

	@Override
	public int getC_UOM_ID()
	{
		return C_UOM_ID;
	}

	@Override
	public IEditablePricingContext setC_UOM_ID(final int c_UOM_ID)
	{
		C_UOM_ID = c_UOM_ID;
		return this;
	}

	@Override
	public CurrencyId getCurrencyId()
	{
		return currencyId;
	}

	@Override
	public IEditablePricingContext setCurrencyId(final CurrencyId currencyId)
	{
		this.currencyId = currencyId;
		return this;
	}

	@Override
	public BPartnerId getBPartnerId()
	{
		return bpartnerId;
	}

	@Override
	public IEditablePricingContext setBPartnerId(final BPartnerId bpartnerId)
	{
		this.bpartnerId = bpartnerId;
		return this;
	}

	@Override
	public BigDecimal getQty()
	{
		return qty;
	}

	@Override
	public IEditablePricingContext setQty(final BigDecimal qty)
	{
		this.qty = qty;
		return this;
	}

	@Override
	public SOTrx getSoTrx()
	{
		return soTrx;
	}

	@Override
	public IEditablePricingContext setSOTrx(@NonNull final SOTrx soTrx)
	{
		this.soTrx = soTrx;
		return this;
	}

	@Override
	public Object getReferencedObject()
	{
		return referencedObject;
	}

	@Override
	public IEditablePricingContext setReferencedObject(final Object referencedObject)
	{
		this.referencedObject = referencedObject;
		if (null != referencedObject)
		{
			trxName = InterfaceWrapperHelper.getTrxName(referencedObject);
		}
		return this;
	}

	@Override
	public boolean isDisallowDiscount()
	{
		return disallowDiscount;
	}

	@Override
	public IEditablePricingContext setDisallowDiscount(final boolean disallowDiscount)
	{
		this.disallowDiscount = disallowDiscount;
		return this;
	}

	@Override
	public String getTrxName()
	{
		return trxName;
	}

	@Override
	public IEditablePricingContext setTrxName(final String trxName)
	{
		this.trxName = trxName;
		return this;
	}

	@Override
	public boolean isConvertPriceToContextUOM()
	{
		return convertPriceToContextUOM;
	}

	@Override
	public IEditablePricingContext setConvertPriceToContextUOM(final boolean convertPriceToContextUOM)
	{
		this.convertPriceToContextUOM = convertPriceToContextUOM;
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(final String propertyName, final Class<T> clazz)
	{
		final Object object = properties.get(propertyName);
		Check.assume(clazz.isInstance(object), "The property {}={} is assumed to be an instance of clazz {}", propertyName, object, clazz);

		return (T)object;
	}

	@Override
	public boolean isPropertySet(final String propertyName)
	{
		return properties.get(propertyName) != null;
	}

	@Override
	public IEditablePricingContext setProperty(final String propertyName, final Object value)
	{
		properties.put(propertyName, value);
		return this;
	}

	@Override
	public Properties getCtx()
	{
		final Object referencedObject = getReferencedObject();
		if (referencedObject == null)
		{
			return Env.getCtx();
		}

		return InterfaceWrapperHelper.getCtx(referencedObject);
	}

	@Override
	public Boolean isManualPrice()
	{
		return isManualPrice;
	}

	@Override
	public IEditablePricingContext setManualPrice(final boolean isManualPrice)
	{
		this.isManualPrice = isManualPrice;
		return this;
	}

	@Override
	public int getC_Country_ID()
	{
		return C_Country_ID;
	}

	@Override
	public IEditablePricingContext setC_Country_ID(final int countryId)
	{
		C_Country_ID = countryId;
		return this;
	}

	@Override
	public boolean isFailIfNotCalculated()
	{
		return failIfNotCalculated;
	}

	@Override
	public IEditablePricingContext setFailIfNotCalculated(final boolean failIfNotCalculated)
	{
		this.failIfNotCalculated = failIfNotCalculated;
		return this;
	}

	@Override
	public IEditablePricingContext setSkipCheckingPriceListSOTrxFlag(final boolean skipCheckingPriceListSOTrxFlag)
	{
		this.skipCheckingPriceListSOTrxFlag = skipCheckingPriceListSOTrxFlag;
		return this;
	}

	@Override
	public boolean isSkipCheckingPriceListSOTrxFlag()
	{
		return skipCheckingPriceListSOTrxFlag;
	}

	@Override
	public IEditablePricingContext setForcePricingConditionsBreak(final PricingConditionsBreak forcePricingConditionsBreak)
	{
		this.forcePricingConditionsBreak = forcePricingConditionsBreak;
		return this;
	}

	@Override
	public Optional<IAttributeSetInstanceAware> getAttributeSetInstanceAware()
	{
		final Object referencedObj = getReferencedObject();
		if (referencedObj == null)
		{
			return Optional.empty();
		}

		final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
		final IAttributeSetInstanceAware asiAware = attributeSetInstanceAwareFactoryService.createOrNull(referencedObj);
		return Optional.ofNullable(asiAware);
	}
}
