package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.fresh.base
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


import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Check;
import org.junit.Ignore;

import de.metas.inoutcandidate.api.IPackageable;
import de.metas.inoutcandidate.api.IPackageableQuery;

@Ignore
public class MockedPackagingDAO extends PackagingDAO
{
	private List<IPackageable> packageables;

	/**
	 * Set {@link IPackageable}s to be returned by {@link #retrievePackableLines(Properties, IPackageableQuery)}.
	 * 
	 * @param packageables
	 */
	public void setPackableLines(final List<IPackageable> packageables)
	{
		this.packageables = packageables;

	}

	@Override
	public Iterator<IPackageable> retrievePackableLines(final Properties ctx, final IPackageableQuery query)
	{
		Check.assumeNotNull(packageables, "packageables were set before calling this method");
		return packageables.iterator();
	}

	public Packageable createPackageable()
	{
		return new Packageable();
	}
}
