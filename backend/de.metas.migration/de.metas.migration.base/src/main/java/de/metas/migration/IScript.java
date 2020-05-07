package de.metas.migration;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.File;

public interface IScript
{
	String getProjectName();

	String getModuleName();

	String getFileName();

	String getType();

	File getLocalFile();

	/**
	 * @return the number of millis seconds it took the last time this script was applied, or {@code -1} if it was never applied yet.
	 */
	long getLastDurationMillis();

	/** Shall be used by the applier. */
	void setLastDurationMillis(long lastDurationMillis);
}
