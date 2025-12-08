package de.metas.pricing.service.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import static de.metas.common.util.CoalesceUtil.coalesce;

@ToString
class PricingContext implements IEditablePricingContext
{
	private PricingSystemId pricingSystemId;
	private PriceListId priceListId;

	private PriceListVersionId priceListVersionId;
	private I_M_PriceList_Version _priceListVersion = null;

	private boolean skipCheckingPriceListSOTrxFlag;

	private OrgId orgId;

	private ProductId productId;

	private LocalDate priceDate;
	/**
	 * "now" to be used when there was no priceDate set.
	 * NOTE: We are taking a snapshot of the current timestamp because we want to return the same value each time we are asked.
	 */
	private LocalDate priceDateNow = SystemTime.asLocalDate();

	@Nullable
	private CountryId countryId;

	@Nullable
	private UomId uomId;
	@Nullable
	private CurrencyId currencyId;
	private BPartnerId bpartnerId;
	private BigDecimal qty;
	private SOTrx soTrx = SOTrx.PURCHASE;
	private Object referencedObject;
	private String trxName;
	private boolean convertPriceToContextUOM;
	private OptionalBoolean manualPriceEnabled = OptionalBoolean.UNKNOWN; // task 08908: can be set by the calling code. Otherwise the engine shall try the referenced object
	private boolean failIfNotCalculated = false;

	private boolean disallowDiscount;

	@Nullable
	private BigDecimal manualPrice;

	@Getter
	private PricingConditionsBreak forcePricingConditionsBreak;

	final private Map<String, Object> properties = new HashMap<>();

	@Override
	public IEditablePricingContext copy()
	{
		final PricingContext pricingCtxNew = new PricingContext();
		pricingCtxNew.orgId = orgId;
		pricingCtxNew.productId = productId;
		pricingCtxNew.pricingSystemId = pricingSystemId;
		pricingCtxNew.priceListId = priceListId;
		pricingCtxNew.priceListVersionId = priceListVersionId;
		pricingCtxNew._priceListVersion = _priceListVersion;
		pricingCtxNew.priceDate = priceDate;
		pricingCtxNew.priceDateNow = priceDateNow;
		pricingCtxNew.uomId = uomId;
		pricingCtxNew.currencyId = currencyId;
		pricingCtxNew.countryId = countryId;
		pricingCtxNew.bpartnerId = bpartnerId;
		pricingCtxNew.qty = qty;
		pricingCtxNew.soTrx = soTrx;
		pricingCtxNew.referencedObject = referencedObject;
		pricingCtxNew.disallowDiscount = disallowDiscount;
		pricingCtxNew.forcePricingConditionsBreak = forcePricingConditionsBreak;
		pricingCtxNew.trxName = trxName;
		pricingCtxNew.convertPriceToContextUOM = convertPriceToContextUOM;
		pricingCtxNew.manualPriceEnabled = manualPriceEnabled;
		pricingCtxNew.failIfNotCalculated = failIfNotCalculated;
		pricingCtxNew.skipCheckingPriceListSOTrxFlag = skipCheckingPriceListSOTrxFlag;
		pricingCtxNew.properties.putAll(properties);
		pricingCtxNew.manualPrice = manualPrice;

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
	public OrgId getOrgId()
	{
		return coalesce(orgId, OrgId.ANY);
	}

	@Override
	public IEditablePricingContext setOrgId(final OrgId orgId)
	{
		this.orgId = orgId;
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
	public LocalDate getPriceDate()
	{
		return priceDate != null ? priceDate : priceDateNow;
	}

	@Override
	public IEditablePricingContext setPriceDate(final LocalDate priceDate)
	{
		this.priceDate = priceDate;
		return this;
	}

	@Override
	public UomId getUomId()
	{
		return uomId;
	}

	@Override
	public IEditablePricingContext setUomId(@Nullable final UomId uomId)
	{
		this.uomId = uomId;
		return this;
	}

	@Override
	@Nullable
	public CurrencyId getCurrencyId()
	{
		return currencyId;
	}

	@Override
	public IEditablePricingContext setCurrencyId(@Nullable final CurrencyId currencyId)
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
	public <T> T getProperty(@NonNull final String propertyName, @NonNull final Class<T> clazz)
	{
		final Object object = properties.get(propertyName);
		Check.assume(clazz.isInstance(object), "The property {}={} is assumed to be an instance of clazz {}", propertyName, object, clazz);

		return (T)object;
	}

	@Override
	public boolean isPropertySet(@NonNull final String propertyName)
	{
		return properties.get(propertyName) != null;
	}

	@Override
	public IEditablePricingContext setProperty(@NonNull final String propertyName, @Nullable final Object value)
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
	public OptionalBoolean getManualPriceEnabled()
	{
		return manualPriceEnabled;
	}

	@Override
	public IEditablePricingContext setManualPriceEnabled(final boolean manualPriceEnabled)
	{
		this.manualPriceEnabled = OptionalBoolean.ofBoolean(manualPriceEnabled);
		return this;
	}

	@Override
	@Nullable
	public CountryId getCountryId()
	{
		return countryId;
	}

	@Override
	public IEditablePricingContext setCountryId(@Nullable final CountryId countryId)
	{
		this.countryId = countryId;
		return this;
	}

	@Override
	public boolean isFailIfNotCalculated()
	{
		return failIfNotCalculated;
	}

	@Override
	public IEditablePricingContext setFailIfNotCalculated()
	{
		this.failIfNotCalculated = true;
		return this;
	}

	@Override
	public IEditablePricingContext setSkipCheckingPriceListSOTrxFlag(final boolean skipCheckingPriceListSOTrxFlag)
	{
		this.skipCheckingPriceListSOTrxFlag = skipCheckingPriceListSOTrxFlag;
		return this;
	}

	@Nullable
	public BigDecimal getManualPrice()
	{
		return manualPrice;
	}

	@Override
	public IEditablePricingContext setManualPrice(@Nullable final BigDecimal manualPrice)
	{
		this.manualPrice = manualPrice;
		return this;
	}

	@Override
	public boolean isSkipCheckingPriceListSOTrxFlag()
	{
		return skipCheckingPriceListSOTrxFlag;
	}

	/** If set to not-{@code null}, then the {@link de.metas.pricing.rules.Discount} rule with go with this break as opposed to look for the currently matching break. */
	@Override
	public IEditablePricingContext setForcePricingConditionsBreak(@Nullable final PricingConditionsBreak forcePricingConditionsBreak)
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

	@Override
	public Quantity getQuantity()
	{
		final BigDecimal ctxQty = getQty();

		if (ctxQty == null)
		{
			return null;
		}

		final UomId ctxUomId = getUomId();

		if (ctxUomId == null)
		{
			return null;
		}

		return Quantitys.of(ctxQty, ctxUomId);
	}
}
