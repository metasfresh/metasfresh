package de.metas.device.accessor.qrcode;

import de.metas.device.accessor.DeviceId;
import de.metas.global_qrcodes.PrintableQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
@Jacksonized // NOTE: we are making it json friendly mainly for snapshot testing
public class DeviceQRCode
{
	@NonNull DeviceId deviceId;
	@NonNull String caption;

	public static boolean equals(@Nullable final DeviceQRCode o1, @Nullable final DeviceQRCode o2)
	{
		return Objects.equals(o1, o2);
	}

	public String toGlobalQRCodeJsonString() {return DeviceQRCodeJsonConverter.toGlobalQRCodeJsonString(this);}

	public static DeviceQRCode ofGlobalQRCodeJsonString(final String json) {return DeviceQRCodeJsonConverter.fromGlobalQRCodeJsonString(json);}

	public PrintableQRCode toPrintableQRCode()
	{
		return PrintableQRCode.builder()
				.bottomText(caption)
				.qrCode(toGlobalQRCodeJsonString())
				.build();
	}
}
