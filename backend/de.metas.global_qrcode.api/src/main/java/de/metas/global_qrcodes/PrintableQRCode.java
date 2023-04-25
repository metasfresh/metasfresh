package de.metas.global_qrcodes;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class PrintableQRCode
{
	@Nullable String topText;
	@Nullable String bottomText;
	@NonNull String qrCode;
}
