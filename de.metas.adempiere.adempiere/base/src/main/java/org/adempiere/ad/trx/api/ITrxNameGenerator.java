package org.adempiere.ad.trx.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


/**
 * Interface responsible for generating new transaction names
 * 
 * @author tsa
 * 
 */
public interface ITrxNameGenerator
{

	/**
	 * Generate an unique transaction name which starts with given prefix.
	 * 
	 * NOTE: if the <code>prefix</code> is really used depends on implementation.
	 * 
	 * @param prefix
	 * @return generated transaction name
	 */
	String createTrxName(String prefix);
}
