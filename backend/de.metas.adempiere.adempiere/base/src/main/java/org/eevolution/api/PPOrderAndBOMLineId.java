package org.eevolution.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class PPOrderAndBOMLineId
{
	@NonNull PPOrderId ppOrderId;
	@NonNull PPOrderBOMLineId lineId;

	public static PPOrderAndBOMLineId of(@NonNull final PPOrderId ppOrderId, @NonNull final PPOrderBOMLineId lineId)
	{
		return new PPOrderAndBOMLineId(ppOrderId, lineId);
	}

	@Nullable
	public static PPOrderAndBOMLineId ofNullable(@Nullable final PPOrderId ppOrderId, @Nullable final PPOrderBOMLineId lineId)
	{
		return ppOrderId != null && lineId != null ? new PPOrderAndBOMLineId(ppOrderId, lineId) : null;
	}

	public static PPOrderAndBOMLineId ofRepoIds(final int ppOrderId, final int lineId)
	{
		return new PPOrderAndBOMLineId(PPOrderId.ofRepoId(ppOrderId), PPOrderBOMLineId.ofRepoId(lineId));
	}

	public boolean isSameOrderAs(@Nullable final PPOrderAndBOMLineId other) {return other != null && PPOrderId.equals(this.ppOrderId, other.ppOrderId);}

	public int getLineRepoId() {return lineId.getRepoId();}
}
