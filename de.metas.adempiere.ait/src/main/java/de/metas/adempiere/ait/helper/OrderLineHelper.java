package de.metas.adempiere.ait.helper;

/*
 * #%L
 * de.metas.adempiere.ait
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_UOM;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.junit.Assert;

import de.metas.adempiere.ait.helper.ProductPriceVO.LineType;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.interfaces.I_C_OrderLine;

/**
 * 
 * @author ts
 * 
 */
public class OrderLineHelper
{
	private final OrderHelper parent;
	private final IHelper helper;

	private BigDecimal priceList;
	private BigDecimal priceActual;
	private I_C_UOM priceUOM;
	private String productValue;
	private BigDecimal qty;
	private boolean useFastInput;
	private BigDecimal manualDiscount;
	private String taxCategoryValue;

	public OrderLineHelper(final OrderHelper parent, final IHelper parentHelper, final LineType lineType)
	{
		this.parent = parent;
		this.helper = parentHelper;
		Check.assume(lineType == LineType.Product, "Param 'lineType'={}; unsupported: {}", LineType.Product, lineType);
	}

	public OrderLineHelper setProductPriceVO(ProductPriceVO productPriceVO)
	{
		setPriceActual(productPriceVO.priceActual);
		setPriceList(productPriceVO.priceList);
		setProductValue(productPriceVO.productValue);
		setQty(productPriceVO.qty);
		setUseFastInput(productPriceVO.useFastInput);

		return this;
	}

	public BigDecimal getPriceList()
	{
		return priceList;
	}

	public OrderLineHelper setPriceList(BigDecimal priceList)
	{
		this.priceList = priceList;
		return this;
	}

	public BigDecimal getPriceActual()
	{
		return priceActual;
	}

	public OrderLineHelper setPriceActual(BigDecimal priceActual)
	{
		this.priceActual = priceActual;
		return this;
	}

	public I_C_UOM getPriceUOM()
	{
		return priceUOM;
	}

	public OrderLineHelper setPriceUOM(I_C_UOM priceUOM)
	{
		this.priceUOM = priceUOM;
		return this;
	}
	
	public String getProductValue()
	{
		return productValue;
	}

	public OrderLineHelper setProductValue(String productValue)
	{
		this.productValue = productValue;
		return this;
	}

	public BigDecimal getQty()
	{
		return qty;
	}

	public OrderLineHelper setQty(BigDecimal qty)
	{
		this.qty = qty;
		return this;
	}

	public boolean isUseFastInput()
	{
		return useFastInput;
	}

	public OrderLineHelper setUseFastInput(boolean useFastInput)
	{
		this.useFastInput = useFastInput;
		return this;
	}

	public BigDecimal getManualDiscout()
	{
		return this.manualDiscount;
	}

	public OrderLineHelper setManualDiscount(BigDecimal discount)
	{
		this.manualDiscount = discount;
		return this;
	}

	public OrderLineHelper setTaxCategoryValue(final String taxCategoryValue)
	{
		this.taxCategoryValue = taxCategoryValue;
		return this;
	}

	public String getTaxCategoryValue()
	{
		return taxCategoryValue;
	}

	public OrderHelper endLine()
	{
		// validate
		if (useFastInput)
		{
			// note that idk if we chould also make this check if !useFastInput, but we'll see
			final String msg = "When creating an order line via fast input, both a qty and a product value need to be set";
			Check.errorIf(qty == null, msg);
			Check.errorIf(productValue == null, msg);
		}
		return parent;
	}

	public I_C_OrderLine createLine(final I_C_Order order)
	{
		final BigDecimal priceListBD = this.getPriceList();
		BigDecimal priceActualBD = this.getPriceActual();
		final String productValue = this.getProductValue();
		final BigDecimal qty = this.getQty();

		// if a tax category has been set, then we use it when creating the pricing data, and we explicitly set it to the new order line.
		// if non has been set, then we use the configured "normal" tax category for the pricing data, and we don't explicitly set it to the new order line.
		final int taxCategory_ID = Check.isEmpty(getTaxCategoryValue())
				? helper.getConfig().getC_TaxCategory_Normal_ID()
				: helper.getC_TaxCategory(getTaxCategoryValue()).getC_TaxCategory_ID();

		if (this.getPriceList() != null)
		{
			helper.setProductPrice(
					parent.getPricingSystemValue(),
					parent.getCurrencyCode(),
					parent.getCountryCode(),
					productValue,
					priceListBD,
					taxCategory_ID,
					parent.isSOTrx());
		}

		final I_M_Product product = helper.getM_Product(productValue);

		final I_C_OrderLine orderLine;

		if (parent.isGridTabLevel())
		{
			if (this.isUseFastInput())
			{
				final int previousLastOrderLineId = parent.retrieveLastOrderLineId(order, false);
				order.setM_Product_ID(product.getM_Product_ID());
				order.setQty_FastInput(qty);
				try
				{
					Thread.sleep(500);
				}
				catch (InterruptedException e)
				{
				}
				final int lastOrderLineId = parent.retrieveLastOrderLineId(order, true);
				Assert.assertTrue("Previous and last order line ids are the same: prev=" + previousLastOrderLineId + ", last=" + lastOrderLineId,
						previousLastOrderLineId != lastOrderLineId);

				orderLine = parent.getGridWindowHelper()
						.selectTab(I_C_OrderLine.Table_Name)
						.refreshAllRows()
						.selectRecordById(lastOrderLineId)
						.getGridTabInterface(I_C_OrderLine.class);
			}
			else
			{
				orderLine = parent.getGridWindowHelper()
						.selectTab(I_C_OrderLine.Table_Name)
						.newRecord()
						.getGridTabInterface(I_C_OrderLine.class);

				orderLine.setM_Product_ID(product.getM_Product_ID());
				orderLine.setQtyOrdered(this.getQty());
			}
			//
			if (priceActualBD != null && priceActualBD.signum() > 0 && priceActualBD.compareTo(priceListBD) != 0)
			{
				orderLine.setPriceActual(priceActualBD);
				orderLine.setPrice_UOM_ID(priceUOM == null ? 0 : priceUOM.getC_UOM_ID()); // 07090: when setting a priceActual, we also need to specify a PriceUOM
			}
			else
			{
				priceActualBD = priceListBD;
			}

			if (manualDiscount != null)
			{
				orderLine.setIsManualDiscount(true);
				orderLine.setDiscount(getManualDiscout());
			}

			if (!Check.isEmpty(getTaxCategoryValue()))
			{
				orderLine.setC_TaxCategory_ID(taxCategory_ID);
			}
			parent.getGridWindowHelper().validateLookups().save();
		}
		else
		{
			final MOrder orderPO = (MOrder)InterfaceWrapperHelper.getPO(order);
			final MOrderLine orderLinePO = new MOrderLine(orderPO);

			orderLinePO.setM_Product_ID(product.getM_Product_ID());
			orderLinePO.setQty(qty);

			orderLine = InterfaceWrapperHelper.create(orderLinePO, de.metas.interfaces.I_C_OrderLine.class);
			if (manualDiscount != null)
			{
				orderLine.setIsManualDiscount(true);
				orderLine.setDiscount(getManualDiscout());
			}

			if (priceActualBD != null && priceActualBD.signum() > 0 && priceActualBD.compareTo(priceListBD) != 0)
			{
				orderLine.setPriceActual(priceActualBD);
				orderLine.setPrice_UOM_ID(priceUOM == null ? 0 : priceUOM.getC_UOM_ID()); // 07090: when setting a priceActual, we also need to specify a PriceUOM
			}
			else
			{
				priceActualBD = priceListBD;
			}

			if (!Check.isEmpty(getTaxCategoryValue()))
			{
				orderLine.setC_TaxCategory_ID(taxCategory_ID);
			}
			InterfaceWrapperHelper.save(orderLine);
		}

		if (priceActualBD != null)
		{
			assertThat("order line price actual not match", orderLine.getPriceActual(), comparesEqualTo(priceActualBD));
		}
		if (priceListBD != null)
		{
			assertThat("order line price list not match", orderLine.getPriceList(), comparesEqualTo(priceListBD));
			assertThat("order line price limit not match", orderLine.getPriceLimit(), comparesEqualTo(priceListBD));
		}

		return orderLine;
	}
}
