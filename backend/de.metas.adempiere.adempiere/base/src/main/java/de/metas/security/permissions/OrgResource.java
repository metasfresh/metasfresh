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

import javax.annotation.concurrent.Immutable;

import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Org;
import org.compiere.util.KeyNamePair;

import com.google.common.base.Function;

import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Identifies a particular organization.
 *
 * @author tsa
 *
 */
@Immutable
public final class OrgResource implements Resource
{
	/** Any Org */
	public static final OrgResource ANY = new OrgResource();

	public static final OrgResource of(final ClientId adClientId, final OrgId adOrgId)
	{
		return new OrgResource(adClientId, adOrgId);
	}

	public static final OrgResource anyOrg(final ClientId adClientId)
	{
		return new OrgResource(adClientId, OrgId.ANY);
	}

	public static final Function<OrgResource, KeyNamePair> TO_ClientKeyNamePair_Function = new Function<OrgResource, KeyNamePair>()
	{
		@Override
		public KeyNamePair apply(OrgResource orgResource)
		{
			return orgResource.asClientKeyNamePair();
		}
	};

	public static final Function<OrgResource, KeyNamePair> TO_OrgKeyNamePair_Function = new Function<OrgResource, KeyNamePair>()
	{
		@Override
		public KeyNamePair apply(OrgResource orgResource)
		{
			return orgResource.asOrgKeyNamePair();
		}
	};

	/** Client */
	private final ClientId _adClientId;
	/** Organization */
	private final OrgId _adOrgId;

	// cached values
	private int hashcode = 0;
	private String clientName;
	private String orgName;
	private Boolean summaryOrg;

	private OrgResource(@NonNull final ClientId adClientId, @NonNull final OrgId adOrgId)
	{
		_adClientId = adClientId;
		_adOrgId = adOrgId;
	}

	/** Any Org constructor */
	private OrgResource()
	{
		_adClientId = null;
		_adOrgId = null;
		clientName = "-";
		orgName = "-";
		summaryOrg = false;
	}

	@Override
	public int hashCode()
	{
		if (hashcode == 0)
		{
			hashcode = new HashcodeBuilder()
					.append(31) // seed
					.append(_adClientId)
					.append(_adOrgId)
					.toHashcode();
		}
		return hashcode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final OrgResource other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(_adClientId, other._adClientId)
				.append(_adOrgId, other._adOrgId)
				.isEqual();
	}

	@Override
	public String toString()
	{
		return toDisplayName();
	}

	private String toDisplayName()
	{
		final String clientName = getClientName();
		final String orgName = getOrgName();
		final StringBuilder sb = new StringBuilder();
		sb.append("@AD_Client_ID@").append("=").append(clientName)
				.append(" - ")
				.append("@AD_Org_ID@").append("=").append(orgName);
		return sb.toString();
	}

	public final String getClientName()
	{
		if (clientName == null)
		{
			clientName = computeClientName();
		}
		return clientName;

	}

	private String computeClientName()
	{
		final ClientId adClientId = getClientId();
		if (adClientId != null)
		{
			final I_AD_Client client = Services.get(IClientDAO.class).getById(adClientId);
			return client == null ? String.valueOf(adClientId.getRepoId()) : client.getName();
		}
		else
		{
			return "System";
		}
	}

	public final String getOrgName()
	{
		if (orgName == null)
		{
			orgName = computeOrgName();
		}
		return orgName;
	}

	private String computeOrgName()
	{
		final OrgId adOrgId = getOrgId();
		if (adOrgId != null)
		{
			return Services.get(IOrgDAO.class).retrieveOrgName(adOrgId);
		}
		else
		{
			return "*";
		}
	}

	public final boolean isSummaryOrganization()
	{
		if (summaryOrg == null)
		{
			summaryOrg = computeIsSummaryOrganization();
		}
		return summaryOrg;
	}

	private boolean computeIsSummaryOrganization()
	{
		final OrgId adOrgId = getOrgId();
		if (adOrgId == null)
		{
			return false;
		}

		final I_AD_Org org = Services.get(IOrgDAO.class).getById(adOrgId);
		return org == null ? false : org.isSummary();
	}

	public boolean isRegularOrg()
	{
		return _adOrgId != null && _adOrgId.isRegular();
	}

	public ClientId getClientId()
	{
		return _adClientId;
	}

	public OrgId getOrgId()
	{
		return _adOrgId;
	}

	public KeyNamePair asClientKeyNamePair()
	{
		if (this == ANY)
		{
			return KeyNamePair.EMPTY;
		}
		return KeyNamePair.of(getClientId(), getClientName());
	}

	public KeyNamePair asOrgKeyNamePair()
	{
		if (this == ANY)
		{
			return KeyNamePair.EMPTY;
		}
		return KeyNamePair.of(getOrgId(), getOrgName());
	}

}
