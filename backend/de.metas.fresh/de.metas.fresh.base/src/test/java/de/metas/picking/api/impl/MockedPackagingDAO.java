package de.metas.picking.api.impl;

import de.metas.picking.api.Packageable;
import de.metas.picking.api.PackageableQuery;
import de.metas.util.Check;
import org.junit.Ignore;

import java.util.List;
import java.util.stream.Stream;

@Ignore
public class MockedPackagingDAO extends PackagingDAO
{
	private List<Packageable> packageables;

	public void setPackableLines(final List<Packageable> packageables)
	{
		this.packageables = packageables;

	}

	@Override
	public Stream<Packageable> stream(final PackageableQuery query)
	{
		Check.assumeNotNull(packageables, "packageables were set before calling this method");
		return packageables.stream();
	}
}
