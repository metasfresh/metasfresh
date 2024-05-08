package de.metas.distribution.workflows_api.json;

import de.metas.handlingunits.qrcodes.model.json.JsonRenderedHUQRCode;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value(staticConstructor = "of")
@Jacksonized
public class JsonHUInfo
{
	@NonNull JsonRenderedHUQRCode qrCode;
}
