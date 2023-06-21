package de.metas.gplr.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class IncotermsInfo
{
	@NonNull String code;
	@Nullable String location;
}
