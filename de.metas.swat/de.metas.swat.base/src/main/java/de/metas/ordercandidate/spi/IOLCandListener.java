package de.metas.ordercandidate.spi;

/*
 * #%L
 * de.metas.swat.base
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


import org.compiere.model.I_C_OrderLine;

import de.metas.ordercandidate.api.OLCand;

/**
 * Interface to be implemented by other modules if they want to add their<br>
 * specific customizations to an order line that is just created from a candidate.
 * 
 */
public interface IOLCandListener
{
	/**
	 * This method is called before the new order lines was saved.
	 * 
	 * @param olCand
	 * @param newOrderLine
	 */
	public void onOrderLineCreated(OLCand olCand, I_C_OrderLine newOrderLine);
}
