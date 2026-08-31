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
	/** Translated human-readable reason why the "Advise Carrier" button is disabled (shown but non-clickable).
	 * {@code null} when the button is enabled OR when carrier advise is not available ({@code available=false}). */
	@Nullable String disabledReason;
}
