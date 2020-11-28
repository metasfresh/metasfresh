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


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxSavepoint;

/**
 * Plain (i.e. nothing) implementation of {@link ITrxSavepoint}.
 * 
 * @author tsa
 * 
 */
public class PlainTrxSavepoint implements ITrxSavepoint
{
	private final ITrx trx;
	private final String name;

	public PlainTrxSavepoint(final ITrx trx, final String name)
	{
		this.trx = trx;
		this.name = name;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ "name=" + name
				+ ", trx=" + trx.getTrxName() // prevent stackoverflow
				+ "]";
	}

	@Override
	public Object getNativeSavepoint()
	{
		// dummy
		return name;
	}

	@Override
	public ITrx getTrx()
	{
		return trx;
	}
}
