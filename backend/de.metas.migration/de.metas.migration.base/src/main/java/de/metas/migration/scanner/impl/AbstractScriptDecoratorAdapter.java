package de.metas.migration.scanner.impl;

import de.metas.migration.IScript;
import de.metas.migration.scanner.IScriptFactory;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;

/*
 * #%L
 * de.metas.migration.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Delegator that delegates everything to an internal scanner.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class AbstractScriptDecoratorAdapter implements IScriptScanner
{
	private final IScriptScanner internalScanner;

	public AbstractScriptDecoratorAdapter(final IScriptScanner scanner)
	{
		internalScanner = scanner;
	}

	protected IScriptScanner getInternalScanner()
	{
		return internalScanner;
	}

	@Override
	public void remove()
	{
		internalScanner.remove();
	}

	@Override
	public void setScriptScannerFactory(IScriptScannerFactory scriptScannerFactory)
	{
		internalScanner.setScriptScannerFactory(scriptScannerFactory);
	}

	@Override
	public IScriptScannerFactory getScriptScannerFactory()
	{
		return internalScanner.getScriptScannerFactory();
	}

	@Override
	public IScriptScannerFactory getScriptScannerFactoryToUse()
	{
		return internalScanner.getScriptScannerFactoryToUse();
	}

	@Override
	public IScriptFactory getScriptFactory()
	{
		return internalScanner.getScriptFactory();
	}

	@Override
	public void setScriptFactory(IScriptFactory scriptFactory)
	{
		internalScanner.setScriptFactory(scriptFactory);
	}

	@Override
	public IScriptFactory getScriptFactoryToUse()
	{
		return internalScanner.getScriptFactoryToUse();
	}

	@Override
	public boolean hasNext()
	{
		return internalScanner.hasNext();
	}

	@Override
	public IScript next()
	{
		return internalScanner.next();
	}
}
