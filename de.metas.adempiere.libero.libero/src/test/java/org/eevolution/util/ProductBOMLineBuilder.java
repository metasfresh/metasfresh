package org.eevolution.util;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Product_BOMLine;

import lombok.NonNull;

public class ProductBOMLineBuilder
{
	private ProductBOMBuilder _parent;
	//
	private String componentType = X_PP_Product_BOMLine.COMPONENTTYPE_Component;
	private I_M_Product _product;
	private I_C_UOM _uom;
	private boolean _isQtyPercentage;
	private BigDecimal _qtyBOM;

	// we need some issue method to avoid NPE when creating the lightweight PPOrdeRLine pojo;
	private String _issueMethod = X_PP_Order_BOMLine.ISSUEMETHOD_Issue;

	private BigDecimal _scrap;
	private BigDecimal _qtyBatch;

	/* package */ ProductBOMLineBuilder(final ProductBOMBuilder parent)
	{
		super();
		Check.assumeNotNull(parent, "parent not null");
		this._parent = parent;
	}

	public ProductBOMBuilder endLine()
	{
		return _parent;
	}

	private IContextAware getContext()
	{
		return _parent.getContext();
	}

	private I_PP_Product_BOM getPP_Product_BOM()
	{
		return _parent.getCreateProductBOM();
	}

	public I_PP_Product_BOMLine build()
	{
		final I_PP_Product_BOMLine bomLine = InterfaceWrapperHelper.newInstance(I_PP_Product_BOMLine.class, getContext());
		bomLine.setPP_Product_BOM(getPP_Product_BOM());
		bomLine.setM_Product(getM_Product());
		bomLine.setC_UOM(getC_UOM());

		bomLine.setIsCritical(false);
		bomLine.setComponentType(componentType);
		bomLine.setIssueMethod(_issueMethod);

		bomLine.setIsQtyPercentage(_isQtyPercentage);
		bomLine.setQtyBOM(_qtyBOM); // used when QtyPercentage=false
		bomLine.setQtyBatch(_qtyBatch); // used when QtyPercentage=true
		bomLine.setScrap(_scrap);

		bomLine.setValidFrom(Timestamp.valueOf("2010-01-01 00:00:00")); // needed to avoid an NPE when creating the lightweight PPOrderLine pojos.

		InterfaceWrapperHelper.save(bomLine);
		return bomLine;
	}

	public ProductBOMLineBuilder product(final I_M_Product product)
	{
		this._product = product;
		return this;
	}

	private I_M_Product getM_Product()
	{
		Check.assumeNotNull(_product, "_product not null");
		return _product;
	}

	public ProductBOMLineBuilder uom(final I_C_UOM uom)
	{
		this._uom = uom;
		return this;
	}

	private I_C_UOM getC_UOM()
	{
		if (this._uom != null)
		{
			return this._uom;
		}

		I_C_UOM uom = null;

		final I_M_Product product = getM_Product();
		if (product != null)
		{
			uom = product.getC_UOM();
		}

		Check.assumeNotNull(uom, "uom not null");
		return uom;
	}

	public ProductBOMLineBuilder setIsQtyPercentage(final boolean isQtyPercentage)
	{
		this._isQtyPercentage = isQtyPercentage;
		return this;
	}

	/**
	 * Sets Qty to be used with {@link #setIsQtyPercentage(boolean)} is set to <code>false</code>.
	 * 
	 * @param qtyBOM
	 * @return this
	 */
	public ProductBOMLineBuilder setQtyBOM(final BigDecimal qtyBOM)
	{
		this._qtyBOM = qtyBOM;
		return this;
	}

	/**
	 * Sets Qty to be used with {@link #setIsQtyPercentage(boolean)} is set to <code>false</code>.
	 * 
	 * @param qtyBOM
	 * @return this
	 */
	public ProductBOMLineBuilder setQtyBOM(final String qtyBOM)
	{
		return setQtyBOM(new BigDecimal(qtyBOM));
	}

	/**
	 * Sets Qty to be used with {@link #setIsQtyPercentage(boolean)} is set to <code>false</code>.
	 * 
	 * @param qtyBOM
	 * @return this
	 */
	public ProductBOMLineBuilder setQtyBOM(final int qtyBOM)
	{
		return setQtyBOM(new BigDecimal(qtyBOM));
	}

	public ProductBOMLineBuilder setScrap(final BigDecimal scrap)
	{
		this._scrap = scrap;
		return this;
	}

	/**
	 * Sets Qty to be used with {@link #setIsQtyPercentage(boolean)} is set to true.
	 * 
	 * @param qtyBatch
	 * @return this
	 */
	public ProductBOMLineBuilder setQtyBatch(final BigDecimal qtyBatch)
	{
		this._qtyBatch = qtyBatch;
		return this;
	}

	public ProductBOMLineBuilder setQtyBatch(final int qtyBatch)
	{
		return setQtyBatch(new BigDecimal(qtyBatch));
	}

	public ProductBOMLineBuilder setScrap(final String scrap)
	{
		return setScrap(new BigDecimal(scrap));
	}

	public ProductBOMLineBuilder setScrap(final int scrap)
	{
		return setScrap(new BigDecimal(scrap));
	}

	/**
	 * The default is {@link X_PP_Product_BOMLine#ISSUEMETHOD_Issue}.
	 * 
	 * @param issueMethod
	 * @return
	 */
	public ProductBOMLineBuilder setIssueMethod(final String issueMethod)
	{
		this._issueMethod = issueMethod;
		return this;
	}
	
	public ProductBOMLineBuilder componentType(@NonNull final String componentType)
	{
		this.componentType = componentType;
		return this;
	}
}
