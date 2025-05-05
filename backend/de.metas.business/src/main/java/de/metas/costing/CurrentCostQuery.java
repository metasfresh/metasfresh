package de.metas.costing;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

@Value
@Builder
public class CurrentCostQuery
{
	@Nullable ClientId clientId;
	@Nullable OrgId orgId;
	@NonNull @Singular ImmutableSet<ProductId> productIds;
	@Nullable AttributeSetInstanceId attributeSetInstanceId;
	@Nullable CostTypeId costTypeId;
	@Nullable AcctSchemaId acctSchemaId;
	@NonNull @Singular ImmutableSet<CostElementId> costElementIds;

	public static CurrentCostQueryBuilder builderFrom(@NonNull final CostSegment costSegment)
	{
		return builder()
				.acctSchemaId(costSegment.getAcctSchemaId())
				.costTypeId(costSegment.getCostTypeId())
				.clientId(costSegment.getClientId())
				.orgId(costSegment.getOrgId())
				.productId(costSegment.getProductId())
				.attributeSetInstanceId(costSegment.getAttributeSetInstanceId());
	}

	public static CurrentCostQueryBuilder builderFrom(@NonNull final CostSegmentAndElement costSegmentAndElement)
	{
		return builderFrom(costSegmentAndElement.toCostSegment())
				.costElementId(costSegmentAndElement.getCostElementId());
	}
}
