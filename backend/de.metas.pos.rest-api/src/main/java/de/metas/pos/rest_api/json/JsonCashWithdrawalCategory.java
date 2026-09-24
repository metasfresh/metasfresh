package de.metas.pos.rest_api.json;

import de.metas.costing.ChargeId;
import de.metas.pos.withdrawal.POSCashWithdrawalCategory;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonCashWithdrawalCategory
{
	@NonNull ChargeId chargeId;
	@NonNull String name;

	public static JsonCashWithdrawalCategory of(@NonNull final POSCashWithdrawalCategory category)
	{
		return JsonCashWithdrawalCategory.builder()
				.chargeId(category.getChargeId())
				.name(category.getName())
				.build();
	}
}
