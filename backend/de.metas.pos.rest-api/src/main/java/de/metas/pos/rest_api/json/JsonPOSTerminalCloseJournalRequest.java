package de.metas.pos.rest_api.json;

import de.metas.pos.POSTerminalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonPOSTerminalCloseJournalRequest
{
	@NonNull POSTerminalId posTerminalId;
	@NonNull BigDecimal cashClosingBalance;
	@Nullable String closingNote;
}
