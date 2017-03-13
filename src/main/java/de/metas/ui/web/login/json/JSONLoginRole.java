package de.metas.ui.web.login.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
public class JSONLoginRole implements Serializable
{
	@JsonCreator
	public static final JSONLoginRole of(
			@JsonProperty("caption") final String caption //
			, @JsonProperty("roleId") final int roleId //
			, @JsonProperty("tenantId") final int tenantId //
			, @JsonProperty("orgId") int orgId //
	)
	{
		return new JSONLoginRole(caption, roleId, tenantId, orgId);
	}

	@JsonProperty("caption")
	private final String caption;

	@JsonProperty("roleId")
	private final int roleId;

	@JsonProperty("tenantId")
	private final int tenantId;

	@JsonProperty("orgId")
	private final int orgId;

	private JSONLoginRole(final String caption, final int roleId, final int tenantId, int orgId)
	{
		super();
		this.caption = caption;
		this.roleId = roleId;
		this.tenantId = tenantId;
		this.orgId = orgId;
	}
	
	public String getCaption()
	{
		return caption;
	}
	
	public int getRoleId()
	{
		return roleId;
	}
	
	public int getTenantId()
	{
		return tenantId;
	}
	
	public int getOrgId()
	{
		return orgId;
	}
}
