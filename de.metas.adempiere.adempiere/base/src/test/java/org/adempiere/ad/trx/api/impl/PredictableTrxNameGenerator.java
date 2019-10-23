package org.adempiere.ad.trx.api.impl;

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


import org.adempiere.ad.trx.api.ITrxNameGenerator;

import de.metas.util.Check;

/**
 * An {@link ITrxNameGenerator} where generated names are predictable.
 * 
 * On constructions it takes the <code>trxNamePrefix</code> and an starting index.
 * 
 * Each time it needs to generate a transaction name, it will use the given <code>trxNamePrefix</code> as a prefix and concatenates current index.
 * 
 * @author tsa
 * 
 */
public class PredictableTrxNameGenerator implements ITrxNameGenerator
{
	private final String trxNamePrefix;
	private final int startIndex;
	private int index;

	public PredictableTrxNameGenerator(final String trxNamePrefix, final int startIndex)
	{
		Check.assumeNotEmpty(trxNamePrefix, "trxNamePrefix not empty");
		this.trxNamePrefix = trxNamePrefix;

		Check.assume(startIndex >= 0, "Invalid startIndex value: {}", startIndex);
		this.startIndex = startIndex;
		this.index = startIndex;
	}

	@Override
	public String createTrxName(String prefix)
	{
		final String trxName = trxNamePrefix + index;
		index++;

		return trxName;
	}
	
	public void resetIndex()
	{
		this.index = startIndex;
	}

}
