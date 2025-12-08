package de.metas.pos;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Set;

@Value
@Builder
public class POSOrderQuery
{
	@NonNull POSTerminalId posTerminalId;
	@NonNull UserId cashierId;
	boolean isOpen;
	@Nullable Set<POSOrderExternalId> onlyOrderExternalIds;
}
