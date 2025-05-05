package de.metas.handlingunits.pporder.api;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ReceiveTUsToLUResult
{
	@NonNull ImmutableList<I_M_HU> tusOrVhus;
	@NonNull HuId luId;
}
