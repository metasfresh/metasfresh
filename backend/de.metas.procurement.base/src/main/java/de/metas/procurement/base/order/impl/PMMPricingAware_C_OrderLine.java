package de.metas.procurement.base.order.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductId;
import org.adempiere.model.InterfaceWrapperHelper;

import org.apache.commons.lang3.NotImplementedException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.money.CurrencyId;
import de.metas.pricing.rules.IPricingRule;
import de.metas.procurement.base.IPMMContractsDAO;
import de.metas.procurement.base.IPMMPricingAware;
import de.metas.procurement.base.IPMMProductBL;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.product.IProductDAO;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * This implementation is backed by an order line.
 * <p>
 * <b>IMPORTANT:</b> The setters do not alter the wrapped orderline itself, because we currently only use this implementation in an {@link IPricingRule} implementation.
 * But is should not be hard to add an option to also alter the wrapped olderLine in future, if needed.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PMMPricingAware_C_OrderLine implements IPMMPricingAware
{

	private final I_C_OrderLine orderLine;

	@Getter
	@Setter
	private CurrencyId currencyId;

	private BigDecimal price;

	public static PMMPricingAware_C_OrderLine of(final I_C_OrderLine orderLine)
	{
		return new PMMPricingAware_C_OrderLine(orderLine);
	}

	private PMMPricingAware_C_OrderLine(final I_C_OrderLine orderLine)
	{
		Check.assumeNotNull(orderLine, "candidate not null");
		this.orderLine = orderLine;
	}

	@Override
	public Properties getCtx()
	{
		return InterfaceWrapperHelper.getCtx(orderLine);
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return Services.get(IBPartnerDAO.class).getById(orderLine.getC_BPartner_ID());
	}

	@Override
	public boolean isContractedProduct()
	{
		if (getC_Flatrate_Term() != null)
		{
			return true;
		}

		return false;
	}

	@Override
	public I_M_Product getM_Product()
	{
		return Services.get(IProductDAO.class).getById(getProductId());
	}

	@Override
	public int getProductId()
	{
		return orderLine.getM_Product_ID();
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return Services.get(IUOMDAO.class).getById(orderLine.getC_UOM_ID());
	}

	@Nullable
	@Override
	public I_C_Flatrate_Term getC_Flatrate_Term()
	{
		final I_M_HU_PI_Item_Product hupip = getM_HU_PI_Item_Product();
		if (hupip == null)
		{
			// the procurement product must have an M_HU_PI_Item_Product set
			return null;
		}

		// the product and M_HU_PI_Item_Product must belong to a PMM_ProductEntry that is currently valid
		final I_PMM_Product pmmProduct = Services.get(IPMMProductBL.class).getPMMProductForDateProductAndASI(
				getDate(),
				ProductId.ofRepoId(getM_Product().getM_Product_ID()),
				BPartnerId.ofRepoIdOrNull(getC_BPartner().getC_BPartner_ID()),
				hupip.getM_HU_PI_Item_Product_ID(),
				getM_AttributeSetInstance());

		if (pmmProduct == null)
		{
			return null;
		}

		// retrieve the freshest, currently valid term for the partner and pmm product.
		return Services.get(IPMMContractsDAO.class).retrieveTermForPartnerAndProduct(
				getDate(),
				getC_BPartner().getC_BPartner_ID(),
				pmmProduct.getPMM_Product_ID());

	}

	/**
	 * @return the M_HU_PI_ItemProduct if set in the orderline. Null othersiwe.
	 */
	private I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		final de.metas.handlingunits.model.I_C_OrderLine huOrderLine = InterfaceWrapperHelper.create(orderLine, de.metas.handlingunits.model.I_C_OrderLine.class);

		return huOrderLine.getM_HU_PI_Item_Product();
	}

	@Override
	public I_C_Flatrate_DataEntry getC_Flatrate_DataEntry()
	{
		return Services.get(IPMMContractsDAO.class).retrieveFlatrateDataEntry(
				getC_Flatrate_Term(),
				getDate());
	}

	@Override
	public Object getWrappedModel()
	{
		return orderLine;
	}

	@Override
	public Timestamp getDate()
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> orderLine.getDatePromised(),
				() -> orderLine.getC_Order().getDatePromised());
	}

	@Override
	public BigDecimal getQty()
	{
		return orderLine.getQtyOrdered();
	}

	@Override
	public void setM_PricingSystem_ID(int M_PricingSystem_ID)
	{
		throw new NotImplementedException();

	}

	@Override
	public void setM_PriceList_ID(int M_PriceList_ID)
	{
		throw new NotImplementedException();

	}

	/**
	 * Sets a private member variable to the given {@code price}.
	 */
	@Override
	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

	/**
	 *
	 * @return the value that was set via {@link #setPrice(BigDecimal)}.
	 */
	public BigDecimal getPrice()
	{
		return price;
	}

	public I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return orderLine.getM_AttributeSetInstance();
	}

}
