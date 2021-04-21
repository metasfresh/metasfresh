package de.metas.ui.web.login.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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
public class JSONLoginRole
{
	@JsonCreator
	public static JSONLoginRole of(
			@JsonProperty("caption") final String caption,
			@JsonProperty("roleId") final int roleId,
			@JsonProperty("tenantId") final int tenantId,
			@JsonProperty("orgId") final int orgId
	)
	{
		return builder()
				.caption(caption)
				.roleId(roleId)
				.tenantId(tenantId)
				.orgId(orgId)
				.build();
	}

	@JsonProperty("key") String key;
	@JsonProperty("caption") String caption;
	@JsonProperty("roleId") int roleId;
	@JsonProperty("tenantId") int tenantId;
	@JsonProperty("orgId") int orgId;

	@Builder
	private JSONLoginRole(
			final String caption,
			final int roleId,
			final int tenantId,
			final int orgId)
	{
		this.caption = caption;
		this.roleId = roleId;
		this.tenantId = tenantId;
		this.orgId = orgId;
		this.key = roleId + "_" + tenantId + "_" + orgId;
	}
}
