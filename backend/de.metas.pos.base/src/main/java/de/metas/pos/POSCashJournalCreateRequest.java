package de.metas.pos;

import de.metas.money.Money;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class POSCashJournalCreateRequest
{
	@NonNull OrgId orgId;
	@NonNull POSTerminalId terminalId;
	@NonNull Instant dateTrx;
	@NonNull Money cashBeginningBalance;
	@Nullable String openingNote;
}
