package de.metas.migration.cli;

/*
 * #%L
 * de.metas.migration.cli
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


import org.junit.Ignore;

@Ignore
public class RolloutMigrateManualTest
{
	public static final void main(String[] args)
	{
		Main.main(new String[] {
				//"-h",
				"-s", "settings_local.properties",
				"-d", "D:\\workspaces\\rm\\mf_2\\metasfresh\\de.metas.adempiere.adempiere\\migration\\src\\main\\sql\\postgresql\\system",
				// "-i", // ignore errors
				// "-r", // dry run
				"-v", // do not check version
				"-u", // do not update version
				"-a" // ask
		});
	}
}
