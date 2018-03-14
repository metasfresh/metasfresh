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


import java.util.List;

import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.OLCand;

/**
 * Interface can be implemented by modules to add specific objects to the grouping key and thus prevent e.g. candidates
 * with different subscription conditions to be aggregated into the same order line
 * 
 * @see IOLCandBL#registerCustomerGroupingValuesProvider(IOLCandGroupingProvider)
 */
@FunctionalInterface
public interface IOLCandGroupingProvider
{

	/**
	 * 
	 * @param cand
	 * @return a list of values that will be transformed into a grouping key. Two candidates with different keys won't
	 *         be aggregated into the same order line.
	 */
	public List<Object> provideLineGroupingValues(OLCand cand);
}
