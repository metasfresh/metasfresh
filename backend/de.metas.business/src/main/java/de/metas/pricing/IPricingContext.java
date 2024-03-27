package de.metas.pricing;

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

import de.metas.bpartner.BPartnerId;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.OptionalBoolean;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_PriceList_Version;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;

public interface IPricingContext extends IContextAware
{
	OrgId getOrgId();

	ProductId getProductId();

	Optional<IAttributeSetInstanceAware> getAttributeSetInstanceAware();

	PricingSystemId getPricingSystemId();

	PriceListId getPriceListId();

	PriceListVersionId getPriceListVersionId();

	/** @return price list version or null */
	I_M_PriceList_Version getM_PriceList_Version();

	/**
	 * Gets pricing evaluation date.
	 *
	 * In case no pricing evaluation date was set while the pricing context was build, "now" will be returned.
	 *
	 * @return pricing evaluation date; never returns null.
	 */
	LocalDate getPriceDate();

	UomId getUomId();

	CurrencyId getCurrencyId();

	BPartnerId getBPartnerId();

	BigDecimal getQty();

	SOTrx getSoTrx();

	boolean isDisallowDiscount();

	PricingConditionsBreak getForcePricingConditionsBreak();

	Object getReferencedObject();

	/**
	 * Creates a mutable copy of this pricing context
	 *
	 * @return new mutable copy
	 */
	IEditablePricingContext copy();

	/**
	 * @return context; never return null
	 */
	@Override
	Properties getCtx();

	@Override
	String getTrxName();

	/**
	 * If set, during pricing calculations, prices will be converted from {@link IPricingResult#getPriceUomId()} ()} to {@link IPricingContext#getUomId()} .
	 *
	 * @return true if prices needs to be converted from Price UOM to context UOM
	 */
	boolean isConvertPriceToContextUOM();

	<T> T getProperty(String propertyName, Class<T> clazz);

	boolean isPropertySet(String propertyName);

	/**
	 * Specifies if the pricing engine shall calculate a price or not.
	 *
	 * @return returns the context value or <code>null</code> if unspecified. In this case the pricing engine shall check if the references object&model has a <code>IsManualPrice</code> field to go
	 *         with.
	 */
	OptionalBoolean getManualPriceEnabled();

	CountryId getCountryId();

	boolean isFailIfNotCalculated();

	boolean isSkipCheckingPriceListSOTrxFlag();

	BigDecimal getManualPrice();

	Quantity getQuantity();
}
