package de.metas.workflow.rest_api.controller.v2.json;

import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonGetCurrentTrolleyResponse
{
	public static final JsonGetCurrentTrolleyResponse EMPTY = builder().build();

	@Nullable Trolley trolley;

	public static JsonGetCurrentTrolleyResponse ofQRCode(@NonNull final LocatorQRCode qrCode)
	{
		return builder()
				.trolley(Trolley.builder()
						.qrCode(qrCode.toScannedCode())
						.caption(qrCode.getCaption())
						.build())
				.build();
	}

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Trolley
	{
		@NonNull String caption;
		@NonNull ScannedCode qrCode;
	}
}
