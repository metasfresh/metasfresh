package de.metas.frontend_testing.masterdata.pos;

import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.pos.POSTerminalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPOSTerminalResponse
{
	@NonNull POSTerminalId id;
	@NonNull BPartnerId walkInBPartnerId;
	@NonNull BankAccountId bankAccountId;
	boolean cashJournalOpen;
}
