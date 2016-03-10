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

import java.io.File;
import java.io.InputStream;

/**
 * A file, virtual or physical
 *
 * @author tsa
 *
 */
public interface IFileRef
{
	/**
	 *
	 * @return parent file or null
	 */
	IFileRef getParent();

	/**
	 * Gets filename (without the path)
	 *
	 * @return filename
	 */
	String getFileName();

	/**
	 *
	 * @return local file or null if this is a virtual file
	 */
	File getFile();

	/**
	 *
	 * @return input stream; never return null
	 */
	InputStream getInputStream();

	/**
	 *
	 * @return true if file is virtual (i.e. not actually a physical file)
	 */
	boolean isVirtual();

	/**
	 *
	 * @return script scanner used to retrieve this file
	 */
	IScriptScanner getScriptScanner();
}
