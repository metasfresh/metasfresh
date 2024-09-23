package de.metas.pos.rest_api.json;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonPOSTerminalOpenJournalRequest
{
	@NonNull BigDecimal cashBeginningBalance;
	@Nullable String openingNote;
}
