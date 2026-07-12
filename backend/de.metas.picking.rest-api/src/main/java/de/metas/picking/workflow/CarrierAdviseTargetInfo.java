package de.metas.picking.workflow;

import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class CarrierAdviseTargetInfo
{
	public static final CarrierAdviseTargetInfo NONE = CarrierAdviseTargetInfo.builder()
			.available(false)
			.readOnly(false)
			.build();

	boolean available;
	boolean readOnly;
	@Nullable String productCaption;
}
