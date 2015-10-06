package de.metas.commission.service;

/*
 * #%L
 * de.metas.commission.base
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
import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Product;
import org.compiere.model.PO;

public interface IFieldAccessBL extends ISingletonService
{

	/**
	 * Get the commission-relevant qty for the given po. For a C_OrderLien this would be QtyOrdered. For an M_InOutLine this would be MovementQty.
	 * 
	 * @param po
	 * @return
	 */
	BigDecimal getQty(Object po);

	int getCurrencyId(PO po);

	int getTaxId(PO po);

	int getWarehouseId(PO po);

	int getTaxCategoryId(PO po);

	boolean isSOTrx(PO po);

	boolean isCreditMemo(Object poLine);

	BigDecimal getCommissionPoints(Object po, boolean throwEx);

	BigDecimal getCommissionPointsSum(PO po, boolean throwEx);

	BigDecimal getLineNetAmtOrNull(Object po);

	/**
	 * 
	 * @param po
	 * @param throwEx if true and po is neither an MOrder nor an MInvoice, an exception is throw. If false, a list containing the given po is returned.
	 * @return
	 */
	List<Object> retrieveLines(Object po, boolean throwEx);

	Object retrieveHeader(Object po);

	/**
	 * 
	 * @param ilOrOl
	 * @return
	 * @deprecated use <code>InterfaceWrapperHelper.create(ilOrOl, IProductAware.class).getM_Product()</code>
	 */
	@Deprecated
	I_M_Product getProduct(PO ilOrOl);

	BigDecimal getDiscount(Object model, boolean throwEx);

	String getDocStatus(PO po, boolean throwEx);
}
