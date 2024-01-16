package de.metas.security.permissions;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

/**
 * Identifies a particular organization.
 *
 * @author tsa
 */
@EqualsAndHashCode(of = "clientAndOrgId")
@ToString(of = "clientAndOrgId")
public final class OrgResource implements Resource
{
	/**
	 * Any Org
	 */
	public static final OrgResource ANY = new OrgResource();

	public static OrgResource of(
			@NonNull final ClientId adClientId,
			@NonNull final OrgId adOrgId,
			final boolean isGroupingOrg)
	{
		return new OrgResource(ClientAndOrgId.ofClientAndOrg(adClientId, adOrgId), isGroupingOrg);
	}

	public static OrgResource anyOrg(@NonNull final ClientId adClientId)
	{
		return new OrgResource(ClientAndOrgId.ofClientAndOrg(adClientId, OrgId.ANY), false);
	}

	@Nullable private final ClientAndOrgId clientAndOrgId;

	@Getter
	private final boolean isGroupingOrg;

	private OrgResource(
			@NonNull final ClientAndOrgId clientAndOrgId,
			final boolean isGroupingOrg)
	{
		this.clientAndOrgId = clientAndOrgId;
		this.isGroupingOrg = isGroupingOrg;
	}

	/**
	 * Any Org constructor
	 */
	private OrgResource()
	{
		clientAndOrgId = null;
		isGroupingOrg = false;
	}

	public boolean isRegularOrg() {return clientAndOrgId != null && clientAndOrgId.getOrgId().isRegular();}

	@Nullable
	public ClientId getClientId() {return clientAndOrgId != null ? clientAndOrgId.getClientId() : null;}

	@Nullable
	public OrgId getOrgId() {return clientAndOrgId != null ? clientAndOrgId.getOrgId() : null;}

	@Nullable
	public OrgId getOrgIdOrAny() {return clientAndOrgId != null ? clientAndOrgId.getOrgId() : OrgId.ANY;}
}
