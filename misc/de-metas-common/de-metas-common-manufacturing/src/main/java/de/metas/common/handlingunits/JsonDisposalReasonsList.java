package de.metas.common.handlingunits;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonDisposalReasonsList
{
	@NonNull List<JsonDisposalReason> reasons;
}
