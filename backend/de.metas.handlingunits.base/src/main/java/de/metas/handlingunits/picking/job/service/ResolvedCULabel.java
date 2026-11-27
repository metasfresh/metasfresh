package de.metas.handlingunits.picking.job.service;

import de.metas.handlingunits.picking.job.model.CULabelPackId;
import de.metas.handlingunits.picking.job.service.external.hu.CUPackIdentity;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder
public class ResolvedCULabel
{
	@NonNull ProductId productId;
	@Nullable String lotNo;
	@Nullable LocalDate bestBeforeDate;
	/** only set when the policy requires a pack ID */
	@Nullable CULabelPackId packId;

	/** @return the identity of the CU this label stands for; only valid when the policy requires a pack ID, where the pack ID is always set */
	public CUPackIdentity toCUPackIdentity()
	{
		return CUPackIdentity.of(productId, lotNo, Check.assumeNotNull(packId, "packId is set when the policy requires a pack ID"));
	}
}
