package de.metas.order.compensationGroup.calibration;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class CalibrationRule
{
	@NonNull CalibrationRuleId id;
	/** {@link OrgId#ANY} applies to every organization */
	@NonNull OrgId orgId;
	int seqNo;
	@Nullable BPartnerId bpartnerId;
	@Nullable BPGroupId bpGroupId;
	@Nullable ProductId productId;
	@Nullable ProductCategoryId productCategoryId;
	@Nullable GroupTemplateId schemaId;
	@NonNull BigDecimal factor;

	public boolean appliesTo(@NonNull final CalibrationMatchKey key)
	{
		return (OrgId.ANY.equals(orgId) || orgId.equals(key.getOrgId()))
				&& isNullOrEqual(bpartnerId, key.getBpartnerId())
				&& isNullOrEqual(bpGroupId, key.getBpGroupId())
				&& isNullOrEqual(productId, key.getProductId())
				&& isNullOrEqual(productCategoryId, key.getProductCategoryId())
				&& isNullOrEqual(schemaId, key.getGroupTemplateId());
	}

	private static <T> boolean isNullOrEqual(@Nullable final T ruleValue, @Nullable final T keyValue)
	{
		return ruleValue == null || ruleValue.equals(keyValue);
	}
}
