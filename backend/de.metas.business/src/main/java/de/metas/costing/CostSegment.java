package de.metas.costing;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;

import de.metas.acct.api.AcctSchemaId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
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
	@Getter(AccessLevel.NONE)
	CostingLevel costingLevel; // we have it here only for toString()

	AcctSchemaId acctSchemaId;
	CostTypeId costTypeId;

	ClientId clientId;
	OrgId orgId;
	ProductId productId;
	AttributeSetInstanceId attributeSetInstanceId;

	@Builder(toBuilder = true)
	private CostSegment(
			@NonNull final CostingLevel costingLevel,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostTypeId costTypeId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final ProductId productId,
			@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		this.costingLevel = costingLevel;
		this.acctSchemaId = acctSchemaId;
		this.costTypeId = costTypeId;
		this.productId = productId;

		this.clientId = costingLevel.effectiveValue(clientId);
		this.orgId = costingLevel.effectiveValue(orgId);
		this.attributeSetInstanceId = costingLevel.effectiveValue(attributeSetInstanceId);
	}

	public CostSegment withProductIdAndCostingLevel(@NonNull final ProductId productId, @NonNull final CostingLevel costingLevel)
	{
		return toBuilder()
				.productId(productId)
				.costingLevel(costingLevel)
				.build();
	}

	public CostSegmentAndElement withCostElementId(@NonNull final CostElementId costElementId)
	{
		return CostSegmentAndElement.of(this, costElementId);
	}
}
