package de.metas.device.accessor.qrcode;

import de.metas.device.accessor.DeviceId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DeviceQRCodeTest
{
	@Test
	void ofGlobalQRCodeJsonString_toGlobalQRCodeJsonString()
	{
		final DeviceQRCode qrCode = DeviceQRCode.builder()
				.deviceId(DeviceId.ofString("device1"))
				.caption("caption1")
				.build();

		System.out.println("QR Code: " + qrCode.toGlobalQRCodeJsonString());
		Assertions.assertThat(DeviceQRCode.ofGlobalQRCodeJsonString(qrCode.toGlobalQRCodeJsonString()))
				.usingRecursiveComparison()
				.isEqualTo(qrCode);
	}

	/**
	 * IMPORTANT: Makes sure that already printed QR codes are still valid.
	 */
	@Test
	void fromGlobalQRCode_v1()
	{
		Assertions.assertThat(DeviceQRCode.ofGlobalQRCodeJsonString("SCALE#1#{\"deviceId\":\"device1\",\"caption\":\"caption1\"}"))
				.usingRecursiveComparison()
				.isEqualTo(DeviceQRCode.builder()
						.deviceId(DeviceId.ofString("device1"))
						.caption("caption1")
						.build());
	}

}