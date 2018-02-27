package de.metas.handlingunits.inout;

import org.adempiere.util.ISingletonService;
import org.eevolution.model.I_DD_Order;

import de.metas.handlingunits.model.I_M_InOutLine;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public interface IInOutDDOrderBL extends ISingletonService
{

	/**
	 * Create a DD_Order header with a DD_OrderLine based on the given inoutLine.
	 * If there are no configuration problems, the new dd_order will be filled with the right data and it will be completed.
	 * 
	 * @param inOutLine
	 * @return the created DD_Order
	 */
	I_DD_Order createDDOrderForInOutLine(I_M_InOutLine inOutLine);

}
