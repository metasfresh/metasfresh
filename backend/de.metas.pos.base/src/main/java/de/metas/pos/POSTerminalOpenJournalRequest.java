package de.metas.pos;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class POSTerminalOpenJournalRequest
{
	@NonNull POSTerminalId posTerminalId;
	@NonNull UserId cashierId;
	@NonNull Instant dateTrx;
	@NonNull BigDecimal cashBeginningBalance;
	@Nullable String openingNote;
}
