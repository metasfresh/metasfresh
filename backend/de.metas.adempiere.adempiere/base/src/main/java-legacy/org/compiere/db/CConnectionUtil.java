/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.compiere.db;

import de.metas.CommandLineParser;
import de.metas.CommandLineParser.CommandLineOptions;
import de.metas.common.util.CoalesceUtil;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.experimental.UtilityClass;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.stream.Stream;

import static de.metas.common.util.CoalesceUtil.coalesce;

@UtilityClass
public class CConnectionUtil
{
	private final static transient Logger logger = LogManager.getLogger(CConnectionUtil.class);

	/**
	 * Can set up the database connection at an early state.
	 * It's assumed that if this method does not set up the connection, it is done later (currently via {@link CConnection#createInstance(CConnectionAttributes)}).
	 */
	public void createInstanceIfArgsProvided(@Nullable final CommandLineOptions commandLineOptions)
	{
		if (commandLineOptions == null)
		{
			return;
		}

		if (Check.isNotBlank(commandLineOptions.getDbHost()) || commandLineOptions.getDbPort() != null)
		{
			final CConnectionAttributes connectionAttributes = CConnectionAttributes.builder()
					.dbHost(commandLineOptions.getDbHost())
					.dbPort(commandLineOptions.getDbPort())
					.dbName(coalesce(commandLineOptions.getDbName(), "metasfresh"))
					.dbUid(coalesce(commandLineOptions.getDbUser(), "metasfresh"))
					.dbPwd(coalesce(commandLineOptions.getDbPassword(), "metasfresh"))
					.build();

			logger.info("!!!!!!!!!!!!!!!!\n"
					+ "!! dbHost and/or dbPort were set from cmdline; -> will ignore DB-Settings from metasfresh.properties and connect to DB with {}\n"
					+ "!!!!!!!!!!!!!!!!", connectionAttributes);
			CConnection.createInstance(connectionAttributes);
		}
	}

}
