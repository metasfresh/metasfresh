package de.metas.marketing.gateway.cleverreach;

/*
 * #%L
 * de.metas.marketing
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ManualTest
{

	public static void main(String args[])
	{
		final CleverReachConfig cleverReachConfig = CleverReachConfig.builder()
				.client_id("")
				.login("")
				.password("").build();

		final CleverReachClient cleverReachClient = CleverReachClient.createNewClientAndLogin(cleverReachConfig);
		cleverReachClient.retrieveGroups();
	}
}
