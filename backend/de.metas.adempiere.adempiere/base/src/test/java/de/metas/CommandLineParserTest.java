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

package de.metas;

import de.metas.CommandLineParser.CommandLineOptions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CommandLineParserTest
{

	@Test
	void parse()
	{
		// given
		final String[] args = {//
				"-dbPort", "12345678",
				"-dbHost", "databaseHost",
				"-dbName", "dataBaseName",
				"-dbUser", "dataBaseUser",
				"-dbPassword", "dataBasePassword"
		};

		// when
		final CommandLineOptions result = CommandLineParser.parse(args);

		// then
		assertThat(result.getDbHost()).isEqualTo("databaseHost");
		assertThat(result.getDbPort()).isEqualTo(12345678);
		assertThat(result.getDbName()).isEqualTo("dataBaseName");
		assertThat(result.getDbUser()).isEqualTo("dataBaseUser");
		assertThat(result.getDbPassword()).isEqualTo("dataBasePassword");
	}
}