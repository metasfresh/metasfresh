package de.metas.datev;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class DATEVExportCreateLinesRequest
{
	@NonNull DATEVExportId datevExportId;
	@NonNull Instant now;
	@NonNull UserId userId;
	boolean isOneLinePerInvoiceTax;
}
