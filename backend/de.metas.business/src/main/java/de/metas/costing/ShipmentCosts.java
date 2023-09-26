package de.metas.costing;

import de.metas.acct.api.AcctSchema;
import de.metas.common.util.CoalesceUtil;
import de.metas.costing.methods.CostAmountType;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.ArrayList;

@Value
public class ShipmentCosts
{
	//
	// P_ExternallyOwnedStock -> P_COGS
	@NonNull CostAmountAndQty shippedAndNotified;

	//
	// P_Asset -> P_COGS
	@NonNull CostAmountAndQty shippedButNotNotified;

	//
	// P_ExternallyOwnedStock -> P_Asset
	@NonNull CostAmountAndQty notifiedButNotShipped;

	@Builder
	private ShipmentCosts(
			@Nullable CostAmountAndQty shippedAndNotified,
			@Nullable CostAmountAndQty shippedButNotNotified,
			@Nullable CostAmountAndQty notifiedButNotShipped)
	{
		if (CoalesceUtil.isAllNotNulls(shippedAndNotified, shippedButNotNotified, notifiedButNotShipped))
		{
			this.shippedAndNotified = Check.assumeNotNull(shippedAndNotified, "shippedAndNotified");
			this.shippedButNotNotified = Check.assumeNotNull(shippedButNotNotified, "shippedButNotNotified");
			this.notifiedButNotShipped = Check.assumeNotNull(notifiedButNotShipped, "notifiedButNotShipped");
		}
		else
		{
			final CostAmountAndQty ZERO = CoalesceUtil.coalesceNotNull(shippedAndNotified, shippedButNotNotified, notifiedButNotShipped).toZero();
			this.shippedAndNotified = CoalesceUtil.coalesceNotNull(shippedAndNotified, ZERO);
			this.shippedButNotNotified = CoalesceUtil.coalesceNotNull(shippedButNotNotified, ZERO);
			this.notifiedButNotShipped = CoalesceUtil.coalesceNotNull(notifiedButNotShipped, ZERO);
		}
	}

	public static ShipmentCosts extractAccountableFrom(@NonNull final CostDetailCreateResultsList results, @NonNull final AcctSchema acctSchema)
	{
		return builder()
				.shippedAndNotified(results.getAmtAndQtyToPost(CostAmountType.ALREADY_SHIPPED, acctSchema).map(CostAmountAndQty::negate).orElse(null))
				.shippedButNotNotified(results.getAmtAndQtyToPost(CostAmountType.MAIN, acctSchema).map(CostAmountAndQty::negate).orElse(null))
				.notifiedButNotShipped(results.getAmtAndQtyToPost(CostAmountType.ADJUSTMENT, acctSchema).orElse(null))
				.build();
	}

	public interface CostAmountAndQtyAndTypeMapper
	{
		CostDetailCreateResult shippedAndNotified(CostAmountAndQty amtAndQty, CostAmountType type);

		CostDetailCreateResult shippedButNotNotified(CostAmountAndQty amtAndQty, CostAmountType type);

		CostDetailCreateResult notifiedButNotShipped(CostAmountAndQty amtAndQty, CostAmountType type);
	}

	public CostDetailCreateResultsList toCostDetailCreateResultsList(@NonNull final CostAmountAndQtyAndTypeMapper mapper)
	{
		final ArrayList<CostDetailCreateResult> resultsList = new ArrayList<>();

		{
			final CostDetailCreateResult result = mapper.shippedButNotNotified(shippedButNotNotified, CostAmountType.MAIN);
			resultsList.add(Check.assumeNotNull(result, "result shall not be null"));
		}

		if (!shippedAndNotified.isZero())
		{
			final CostDetailCreateResult result = mapper.shippedAndNotified(shippedAndNotified, CostAmountType.ALREADY_SHIPPED);
			resultsList.add(Check.assumeNotNull(result, "result shall not be null"));
		}

		if (!notifiedButNotShipped.isZero())
		{
			final CostDetailCreateResult result = mapper.notifiedButNotShipped(notifiedButNotShipped, CostAmountType.ADJUSTMENT);
			resultsList.add(Check.assumeNotNull(result, "result shall not be null"));
		}

		return CostDetailCreateResultsList.ofList(resultsList);
	}
}
