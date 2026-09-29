package de.metas.frontend_testing.masterdata.pos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.costing.ChargeId;
import de.metas.pos.POSTerminalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonPOSTerminalResponse
{
	@NonNull POSTerminalId id;
	@NonNull String name;
	@NonNull BPartnerId walkInBPartnerId;
	@NonNull BankAccountId bankAccountId;
	@JsonProperty("cashJournalOpen") boolean isCashJournalOpen;

	/**
	 * Created cash withdrawal categories, keyed by the requested label.
	 */
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) Map<String, CashWithdrawalCategory> cashWithdrawalCategories;

	@Value
	@Builder
	@Jacksonized
	public static class CashWithdrawalCategory
	{
		@NonNull ChargeId chargeId;

		/**
		 * The charge name as offered at the terminal: the requested label plus a per-run unique suffix.
		 */
		@NonNull String name;
	}
}
