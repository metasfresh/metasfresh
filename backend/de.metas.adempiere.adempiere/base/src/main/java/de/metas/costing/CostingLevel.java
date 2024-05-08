package de.metas.costing;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.compiere.model.X_C_AcctSchema;

import com.google.common.collect.ImmutableMap;

import de.metas.organization.OrgId;
import de.metas.util.GuavaCollectors;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

public enum CostingLevel
{
	Client(X_C_AcctSchema.COSTINGLEVEL_Client), //
	Organization(X_C_AcctSchema.COSTINGLEVEL_Organization), //
	BatchLot(X_C_AcctSchema.COSTINGLEVEL_BatchLot);

	@Getter
	private final String code;

	CostingLevel(final String code)
	{
		this.code = code;
	}

	public static CostingLevel forNullableCode(final String code)
	{
		if (code == null)
		{
			return null;
		}

		return forCode(code);
	}

	public static CostingLevel forCode(final String code)
	{
		final CostingLevel type = code2type.get(code);
		if (type == null)
		{
			throw new NoSuchElementException("No " + CostingLevel.class + " found for code=" + code);
		}
		return type;
	}

	private static final ImmutableMap<String, CostingLevel> code2type = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(CostingLevel::getCode));

	public ClientId effectiveValue(@NonNull final ClientId clientId)
	{
		if (clientId.isSystem())
		{
			throw new AdempiereException("Invalid: " + clientId);
		}
		return clientId;
	}

	public OrgId effectiveValue(@NonNull final OrgId orgId)
	{
		return effectiveValueOr(orgId, OrgId.ANY);
	}

	public OrgId effectiveValueOrNull(@NonNull final OrgId orgId)
	{
		return effectiveValueOr(orgId, null);
	}

	private OrgId effectiveValueOr(@NonNull final OrgId orgId, final OrgId nullValue)
	{
		if (this == Client)
		{
			return nullValue;
		}
		else if (this == Organization)
		{
			if (orgId.isAny())
			{
				throw new AdempiereException("Regular organization expected when costing level is Organization");
			}
			return orgId;
		}
		else if (this == BatchLot)
		{
			return nullValue;
		}
		else
		{
			throw new AdempiereException("Unknown costingLevel: " + this);
		}
	}

	public AttributeSetInstanceId effectiveValue(@NonNull final AttributeSetInstanceId asiId)
	{
		return effectiveValueOr(asiId, AttributeSetInstanceId.NONE);
	}

	public AttributeSetInstanceId effectiveValueOrNull(@NonNull final AttributeSetInstanceId asiId)
	{
		return effectiveValueOr(asiId, null);
	}

	private AttributeSetInstanceId effectiveValueOr(@NonNull final AttributeSetInstanceId asiId, final AttributeSetInstanceId nullValue)
	{
		if (this == Client)
		{
			return nullValue;
		}
		else if (this == Organization)
		{
			return nullValue;
		}
		else if (this == BatchLot)
		{
			if (asiId.isNone())
			{
				throw new AdempiereException("Regular ASI expected when costing level is Batch/Lot");
			}
			return asiId;
		}
		else
		{
			throw new AdempiereException("Unknown costingLevel: " + this);
		}
	}
}
