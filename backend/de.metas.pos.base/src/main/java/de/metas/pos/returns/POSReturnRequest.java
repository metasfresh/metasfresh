package de.metas.pos.returns;

import de.metas.pos.POSTerminalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.UUID;

/**
 * A customer handing goods back at the till, to be received into stock and credited at the till's price.
 */
@Value
@Builder
public class POSReturnRequest
{
	@NonNull POSTerminalId posTerminalId;

	/** Identifies this return so a retried request does not create a second one. */
	@NonNull UUID externalId;

	@NonNull List<POSReturnLine> lines;
}
