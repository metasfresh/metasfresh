package de.metas.costing;

import de.metas.acct.api.AcctSchema;
import de.metas.costing.methods.CostAmountType;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.Objects;

@Value
public class ShipmentCosts
{
	//
	// P_Asset -> P_COGS
	@NonNull CostAmountAndQty shippedButNotNotified;

	@Builder
	private ShipmentCosts(
			@NonNull final CostAmountAndQty shippedButNotNotified)
	{
		this.shippedButNotNotified = Check.assumeNotNull(shippedButNotNotified, "shippedButNotNotified");
	}

	public static ShipmentCosts extractAccountableFrom(@NonNull final CostDetailCreateResultsList results, @NonNull final AcctSchema acctSchema)
	{
		return builder()
				.shippedButNotNotified(Objects.requireNonNull(results.getAmtAndQtyToPost(CostAmountType.MAIN, acctSchema).map(CostAmountAndQty::negate).orElse(null)))
				.build();
	}

	public static ShipmentCosts extractFrom(@NonNull final CostDetailCreateRequest request)
	{
		final CostAmountAndQty amtAndQty = CostAmountAndQty.of(request.getAmt(), request.getQty());
		final ShipmentCosts shipmentCosts;
		if (request.getAmtType() == CostAmountType.MAIN)
		{
			shipmentCosts = builder().shippedButNotNotified(amtAndQty.negate()).build();
		}
		else
		{
			throw new IllegalArgumentException();
		}
		return shipmentCosts;
	}

	public interface CostAmountAndQtyAndTypeMapper
	{
		CostDetailCreateResult shippedButNotNotified(CostAmountAndQty amtAndQty, CostAmountType type);
	}

	public CostDetailCreateResultsList toCostDetailCreateResultsList(@NonNull final CostAmountAndQtyAndTypeMapper mapper)
	{
		final ArrayList<CostDetailCreateResult> resultsList = new ArrayList<>();

		{
			final CostDetailCreateResult result = mapper.shippedButNotNotified(shippedButNotNotified, CostAmountType.MAIN);
			resultsList.add(Check.assumeNotNull(result, "result shall not be null"));
		}

		return CostDetailCreateResultsList.ofList(resultsList);
	}
}
