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

@Value
@Builder
public class CalibrationMatchKey
{
	@NonNull OrgId orgId;
	@NonNull BPartnerId bpartnerId;
	@NonNull BPGroupId bpGroupId;
	@NonNull ProductId productId;
	@NonNull ProductCategoryId productCategoryId;
	@Nullable GroupTemplateId groupTemplateId;
}
