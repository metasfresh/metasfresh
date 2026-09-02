package de.metas.common.handlingunits;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * @implNote This is a copy-paste of de.metas.global_qrcodes.JsonDisplayableQRCode, because we don't have access to that class from here.
 */
@Value
@Builder
@Jacksonized
public class JsonHUQRCode
{
	@NonNull String code;
	@NonNull String displayable;
}
