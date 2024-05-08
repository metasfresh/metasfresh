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
public class CostSegmentAndElement
{
	public static final CostSegmentAndElement of(
			@NonNull final CostSegment costSegment,
			@NonNull final CostElementId costElementId)
	{
		return new CostSegmentAndElement(costSegment, costElementId);
	}

	@Getter(AccessLevel.PRIVATE)
	CostSegment costSegment;
	CostElementId costElementId;

	private CostSegmentAndElement(
			@NonNull final CostSegment costSegment,
			@NonNull final CostElementId costElementId)
	{
		this.costSegment = costSegment;
		this.costElementId = costElementId;
	}

	@Builder
	private CostSegmentAndElement(
			@NonNull final CostingLevel costingLevel,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostTypeId costTypeId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final ProductId productId,
			@NonNull final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final CostElementId costElementId)
	{
		costSegment = CostSegment.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(acctSchemaId)
				.costTypeId(costTypeId)
				.productId(productId)
				.clientId(clientId)
				.orgId(orgId)
				.attributeSetInstanceId(attributeSetInstanceId)
				.build();

		this.costElementId = costElementId;
	}

	public CostSegmentAndElement withProductIdAndCostingLevel(final ProductId productId, final CostingLevel costingLevel)
	{
		return new CostSegmentAndElement(
				getCostSegment().withProductIdAndCostingLevel(productId, costingLevel),
				getCostElementId());
	}

	public CostSegment toCostSegment()
	{
		return getCostSegment();
	}

	public AcctSchemaId getAcctSchemaId()
	{
		return getCostSegment().getAcctSchemaId();
	}

	public CostTypeId getCostTypeId()
	{
		return getCostSegment().getCostTypeId();
	}

	public OrgId getOrgId()
	{
		return getCostSegment().getOrgId();
	}

	public ProductId getProductId()
	{
		return getCostSegment().getProductId();
	}

	public AttributeSetInstanceId getAttributeSetInstanceId()
	{
		return getCostSegment().getAttributeSetInstanceId();
	}

}
