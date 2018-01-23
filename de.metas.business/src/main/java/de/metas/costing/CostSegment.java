package de.metas.costing;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class CostSegment
{
	CostingLevel costingLevel;

	int acctSchemaId;
	int costTypeId;
	int productId;
	int clientId;
	int orgId;
	int attributeSetInstanceId;

	@Builder(toBuilder = true)
	private CostSegment(
			@NonNull final CostingLevel costingLevel,
			final int acctSchemaId,
			final int costTypeId,
			final int clientId,
			final int orgId,
			final int productId,
			final int attributeSetInstanceId)
	{
		Check.assume(acctSchemaId > 0, "acctSchemaId > 0");
		Check.assume(costTypeId > 0, "costTypeId > 0");
		Check.assume(clientId > 0, "clientId > 0");
		Check.assume(orgId >= 0, "orgId >= 0");
		Check.assume(productId > 0, "productId > 0");

		this.costingLevel = costingLevel;
		this.acctSchemaId = acctSchemaId;
		this.costTypeId = costTypeId;
		this.productId = productId;
		this.clientId = clientId;

		if (costingLevel == CostingLevel.Client)
		{
			this.orgId = 0;
			this.attributeSetInstanceId = 0;
		}
		else if (costingLevel == CostingLevel.Organization)
		{
			this.orgId = orgId;
			this.attributeSetInstanceId = 0;
		}
		else if (costingLevel == CostingLevel.BatchLot)
		{
			this.orgId = 0;
			this.attributeSetInstanceId = attributeSetInstanceId;
		}
		else
		{
			throw new AdempiereException("Unknown costingLevel: " + costingLevel);
		}
	}
}
