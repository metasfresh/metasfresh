package de.metas.inoutcandidate.api.impl;

import java.util.List;

import org.adempiere.util.Check;
import org.junit.Ignore;

import de.metas.inoutcandidate.api.IPackageable;
import de.metas.inoutcandidate.api.PackageableQuery;

@Ignore
public class MockedPackagingDAO extends PackagingDAO
{
	private List<IPackageable> packageables;

	public void setPackableLines(final List<IPackageable> packageables)
	{
		this.packageables = packageables;

	}

	@Override
	public List<IPackageable> retrievePackableLines(final PackageableQuery query)
	{
		Check.assumeNotNull(packageables, "packageables were set before calling this method");
		return packageables;
	}
}
