package de.metas.handlingunits.pporder.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.util.HUByIdComparator;

/**
 * POJO used to accumulate quantity (see {@link #addQtyToIssue(I_M_Product, BigDecimal, I_C_UOM)}) and then later generate in Issue Cost Collector from it.
 *
 * The cummulated quantity (i.e. {@link #getQtyToIssue()}) is stored in BOM Line's UOM.
 *
 * @author tsa
 *
 */
/* package */class HUIssueCostCollectorCandidate
{
	// Services
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	//
	private final I_PP_Order_BOMLine ppOrderBOMLine;
	private final I_C_UOM uom;
	private BigDecimal qtyToIssue = BigDecimal.ZERO;
	private final Set<I_M_HU> husToAssign = new TreeSet<>(HUByIdComparator.instance);
	private final Set<I_M_HU> husToAssignRO = Collections.unmodifiableSet(husToAssign);

	public HUIssueCostCollectorCandidate(final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		super();

		Check.assumeNotNull(ppOrderBOMLine, "ppOrderBOMLine not null");
		this.ppOrderBOMLine = ppOrderBOMLine;

		uom = ppOrderBOMLine.getC_UOM();
	}

	/**
	 *
	 * @param product
	 * @param qtyToIssueToAdd
	 * @param huToAssign HU to be assigned to generated cost collector
	 */
	public void addQtyToIssue(final I_M_Product product, final Quantity qtyToIssueToAdd, final I_M_HU huToAssign)
	{
		// Validate
		if (product.getM_Product_ID() != ppOrderBOMLine.getM_Product_ID())
		{
			throw new HUException("Invalid product to issue."
					+ "\nExpected: " + ppOrderBOMLine.getM_Product()
					+ "\nGot: " + product
					+ "\n"
					+ "\n@PP_Order_BOMLine_ID@: " + ppOrderBOMLine
			//
			);
		}

		final IUOMConversionContext uomConversionCtx = uomConversionBL.createConversionContext(product);
		final Quantity qtyToIssueToAddConv = qtyToIssueToAdd.convertTo(uomConversionCtx, uom);

		qtyToIssue = qtyToIssue.add(qtyToIssueToAddConv.getQty());

		if (huToAssign != null)
		{
			husToAssign.add(huToAssign);
		}
	}

	public BigDecimal getQtyToIssue()
	{
		return qtyToIssue;
	}

	public I_PP_Order_BOMLine getPP_Order_BOMLine()
	{
		return ppOrderBOMLine;
	}

	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	/**
	 * @return HUs that needs to be assigned to generated cost collector
	 */
	public Set<I_M_HU> getHUsToAssign()
	{
		return husToAssignRO;
	}
}
