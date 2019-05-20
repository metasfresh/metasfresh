package de.metas.migration.scanner.impl;

import de.metas.migration.IScript;
import de.metas.migration.scanner.IScriptFactory;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;

/*
 * #%L
 * de.metas.migration.base
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public abstract class ForwardingScriptScanner implements IScriptScanner
{
	protected abstract IScriptScanner getDelegate();

	@Override
	public void setScriptScannerFactory(final IScriptScannerFactory scriptScannerFactory)
	{
		getDelegate().setScriptScannerFactory(scriptScannerFactory);
	}

	@Override
	public IScriptScannerFactory getScriptScannerFactory()
	{
		return getDelegate().getScriptScannerFactory();
	}

	@Override
	public IScriptScannerFactory getScriptScannerFactoryToUse()
	{
		return getDelegate().getScriptScannerFactoryToUse();
	}

	@Override
	public IScriptFactory getScriptFactory()
	{
		return getDelegate().getScriptFactory();
	}

	@Override
	public void setScriptFactory(final IScriptFactory scriptFactory)
	{
		getDelegate().setScriptFactory(scriptFactory);
	}

	@Override
	public IScriptFactory getScriptFactoryToUse()
	{
		return getDelegate().getScriptFactoryToUse();
	}

	@Override
	public boolean hasNext()
	{
		return getDelegate().hasNext();
	}

	@Override
	public IScript next()
	{
		return getDelegate().next();
	}

}
