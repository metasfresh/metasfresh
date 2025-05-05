package de.metas.inout;

import de.metas.document.engine.DocStatus;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class InOutQuery
{
	@Nullable Instant movementDateFrom;
	@Nullable Instant movementDateTo;
	@Nullable DocStatus docStatus;
}
