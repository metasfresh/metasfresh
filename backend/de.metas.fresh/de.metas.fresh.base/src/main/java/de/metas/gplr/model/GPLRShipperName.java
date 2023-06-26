package de.metas.gplr.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GPLRShipperName
{
	@NonNull String name;
}
