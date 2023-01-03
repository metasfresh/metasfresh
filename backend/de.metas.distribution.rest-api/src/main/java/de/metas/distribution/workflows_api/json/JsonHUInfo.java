package de.metas.distribution.workflows_api.json;

import de.metas.global_qrcodes.JsonDisplayableQRCode;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value(staticConstructor = "of")
@Jacksonized
public class JsonHUInfo
{
	@NonNull JsonDisplayableQRCode qrCode;
}
