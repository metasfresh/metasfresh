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


import java.util.UUID;

import org.adempiere.ad.trx.api.ITrxNameGenerator;

/**
 * Default transaction name generator implementation.
 * 
 * Generated transaction name will have following format: [Prefix or {@value #TRXNAME_PREFIX_DEFAULT}]_[generated UUID]
 * 
 * @author tsa
 * 
 */
public class DefaultTrxNameGenerator implements ITrxNameGenerator
{
	public static final transient DefaultTrxNameGenerator instance = new DefaultTrxNameGenerator();

	public static final String TRXNAME_PREFIX_DEFAULT = "Trx";

	@Override
	public String createTrxName(final String prefix)
	{
		final StringBuilder trxName = new StringBuilder();

		if (prefix == null || prefix.isEmpty())
		{
			trxName.append(TRXNAME_PREFIX_DEFAULT);
		}
		else
		{
			trxName.append(prefix);
		}

		trxName.append("_").append(UUID.randomUUID());

		return trxName.toString();
	}
}
