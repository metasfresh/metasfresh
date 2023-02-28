/*
 * #%L
 * marketing-activecampaign
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.marketing.gateway.activecampaign;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface ActiveCampaignConstants
{
	@Getter
	@AllArgsConstructor
	enum ResourcePath
	{
		LISTS("lists"),
		CONTACTS("contacts"),
		CONTACT("contact"),
		CONTACT_LISTS("contactLists"),
		SYNC("sync"),
		;

		private final String value;
	}

	@Getter
	@AllArgsConstructor
	enum QueryParam
	{
		LIST_ID("listid"),
		LIMIT("limit"),
		OFFSET("offset"),
		;

		private final String value;
	}

	String ACTIVE_CAMPAIGN_API = "api";
	String ACTIVE_CAMPAIGN_API_VERSION = "3";
	String ACTIVE_CAMPAIGN_API_TOKEN_HEADER = "Api-Token";

	String ACTIVE_CAMPAIGN_API_RATE_LIMIT_RETRY_HEADER = "Retry-After";

	String MAX_SECONDS_TO_WAIT_FOR_ACTIVE_CAMPAIGN_LIMIT_RESET = "de.metas.marketing.gateway.activecampaign.rest.maxSecondsToWaitForLimitReset";
	int ACTIVE_CAMPAIGN_API_PAGINATION_LIMIT = 100;
}
