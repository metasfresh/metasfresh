package de.metas.migration.scanner.impl;

/*
 * #%L
 * de.metas.migration.base
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

import de.metas.migration.exception.ScriptException;
import de.metas.migration.scanner.IScriptFactory;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;

public abstract class AbstractScriptScanner implements IScriptScanner
{
	private final IScriptScanner parentScanner;

	private IScriptScannerFactory scriptScannerFactory;
	private IScriptFactory scriptFactory;

	public AbstractScriptScanner(final IScriptScanner parentScanner)
	{
		super();
		this.parentScanner = parentScanner;
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setScriptScannerFactory(final IScriptScannerFactory scriptScannerFactory)
	{
		// NOTE: null is allowed
		this.scriptScannerFactory = scriptScannerFactory;
	}

	@Override
	public IScriptScannerFactory getScriptScannerFactory()
	{
		return scriptScannerFactory;
	}

	@Override
	public IScriptScannerFactory getScriptScannerFactoryToUse()
	{
		if (scriptScannerFactory != null)
		{
			return scriptScannerFactory;
		}
		if (parentScanner != null)
		{
			return parentScanner.getScriptScannerFactoryToUse();
		}

		throw new ScriptException(" No " + IScriptScannerFactory.class + " defined for " + this + ".");
	}

	@Override
	public IScriptFactory getScriptFactory()
	{
		return scriptFactory;
	}

	@Override
	public void setScriptFactory(final IScriptFactory scriptFactory)
	{
		this.scriptFactory = scriptFactory;
	}

	@Override
	public IScriptFactory getScriptFactoryToUse()
	{
		//
		// If there is an script factory defined on this level, return it
		{
			final IScriptFactory scriptFactory = getScriptFactory();
			if (scriptFactory != null)
			{
				return scriptFactory;
			}
		}

		//
		// Ask parent script scanner
		if (parentScanner != null)
		{
			final IScriptFactory scriptFactory = parentScanner.getScriptFactoryToUse();
			if (scriptFactory != null)
			{
				return scriptFactory;
			}
		}

		//
		// Ask Script Scanner Factory for IScriptFactory
		{
			final IScriptScannerFactory scriptScannerFactory = getScriptScannerFactoryToUse();
			final IScriptFactory scriptFactory = scriptScannerFactory.getScriptFactoryToUse();
			return scriptFactory;
		}
	}

}
