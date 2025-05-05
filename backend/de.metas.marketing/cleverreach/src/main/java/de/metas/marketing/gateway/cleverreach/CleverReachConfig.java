package de.metas.marketing.gateway.cleverreach;

import de.metas.marketing.base.model.CampaignConfig;
import de.metas.marketing.base.model.PlatformId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

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

@Value
@Builder
@Jacksonized
public class CleverReachConfig implements CampaignConfig
{
	@NonNull
	String client_id;

	@NonNull
	String login;

	@NonNull
	String password;

	@NonNull
	PlatformId platformId;

	@NonNull
	OrgId orgId;
}
