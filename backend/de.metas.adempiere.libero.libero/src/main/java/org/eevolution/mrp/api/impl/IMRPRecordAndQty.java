package org.eevolution.mrp.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.eevolution.model.I_PP_MRP;

/**
 * Pair of MRP record ({@link I_PP_MRP}) and Quantity.
 * 
 * @author tsa
 *
 */
public interface IMRPRecordAndQty
{
	/** @return MRP record; never returns <code>null</code> */
	I_PP_MRP getPP_MRP();

	BigDecimal getQtyRequired();

	BigDecimal getQtyInitial();

	/** @return Qty; never returns <code>null</code> */
	BigDecimal getQty();

	/** @return true if {@link #getQty()} is zero */
	boolean isZeroQty();
}
