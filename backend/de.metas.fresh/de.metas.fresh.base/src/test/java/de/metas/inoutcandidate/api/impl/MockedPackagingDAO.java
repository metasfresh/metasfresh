package de.metas.inoutcandidate.api.impl;

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
	public List<IPackageable> retrievePackableLines(final IPackageableQuery query)
	{
		Check.assumeNotNull(packageables, "packageables were set before calling this method");
		return packageables;
	}
}
