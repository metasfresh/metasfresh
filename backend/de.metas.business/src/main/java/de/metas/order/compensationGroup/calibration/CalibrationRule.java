package de.metas.order.compensationGroup.calibration;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.order.compensationGroup.GroupTemplateId;
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
	int seqNo;
	@Nullable BPartnerId bpartnerId;
	@Nullable BPGroupId bpGroupId;
	@Nullable ProductId productId;
	@Nullable ProductCategoryId productCategoryId;
	@Nullable GroupTemplateId schemaId;
	@NonNull BigDecimal factor;
}
