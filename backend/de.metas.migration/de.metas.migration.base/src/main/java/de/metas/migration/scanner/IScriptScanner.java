package de.metas.migration.scanner;

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

import java.util.Iterator;

import de.metas.migration.IScript;

public interface IScriptScanner extends Iterator<IScript>
{
	IScriptScanner NULL = null;

	void setScriptScannerFactory(IScriptScannerFactory scriptScannerFactory);

	IScriptScannerFactory getScriptScannerFactory();

	IScriptScannerFactory getScriptScannerFactoryToUse();

	IScriptFactory getScriptFactory();

	void setScriptFactory(IScriptFactory scriptFactory);

	IScriptFactory getScriptFactoryToUse();

	/**
	 * @return true if there more scripts to be retrieved by {@link #next()}
	 */
	@Override
	boolean hasNext();

	/**
	 * @return next script
	 */
	@Override
	IScript next();
}
