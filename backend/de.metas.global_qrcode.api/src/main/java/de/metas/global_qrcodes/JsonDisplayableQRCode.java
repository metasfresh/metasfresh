package de.metas.global_qrcodes;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonDisplayableQRCode
{
	@NonNull String code;
	@NonNull String displayable;
}
