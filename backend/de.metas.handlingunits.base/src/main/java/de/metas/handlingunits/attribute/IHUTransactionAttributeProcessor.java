package de.metas.handlingunits.attribute;

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


import java.util.List;

import org.adempiere.util.lang.IReference;

import de.metas.handlingunits.hutransaction.IHUTransactionAttribute;
import de.metas.handlingunits.model.I_M_HU_Trx_Attribute;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;

public interface IHUTransactionAttributeProcessor
{
	void setM_HU_Trx_Hdr(IReference<I_M_HU_Trx_Hdr> trxHdrRef);

	/**
	 * Creates actual HU Attribute transactions (see {@link #create(List)}) and then process them.
	 *
	 * @param attributeTrxs
	 * @return created HU attribute transactions.
	 */
	List<I_M_HU_Trx_Attribute> createAndProcess(final List<IHUTransactionAttribute> attributeTrxs);

	/**
	 *
	 * @param reversalTrxLine
	 * @param trxLine original line
	 */
	void reverseTrxAttributes(I_M_HU_Trx_Line reversalTrxLine, I_M_HU_Trx_Line trxLine);
}
