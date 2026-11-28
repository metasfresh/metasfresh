package de.metas.handlingunits.picking.job.service.external.hu;

import de.metas.handlingunits.picking.job.model.CULabelPackId;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/** What identifies one CU of a scan-each-CU label: its product, lot and pack ID */
@Value(staticConstructor = "of")
public class CUPackIdentity
{
	@NonNull ProductId productId;
	@Nullable String lotNo;
	@NonNull CULabelPackId packId;
}
