package de.metas.picking.workflow;

import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class CarrierAdviseTargetInfo
{
	public static final CarrierAdviseTargetInfo NONE = CarrierAdviseTargetInfo.builder().build();

	boolean available;
	boolean readOnly;
	@Nullable String productCaption;
}
