package de.metas.handlingunits.qrcodes.custom;

import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ParsedScannedCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomHUQRCodeTest
{
	@Test
	void getPackId_returnsParsedPackId()
	{
		final CustomHUQRCode qrCode = CustomHUQRCode.ofParsedScannedCode(ParsedScannedCode.builder()
				.scannedCode(ScannedCode.ofString("any"))
				.packId("2")
				.build());

		assertThat(qrCode.getPackId()).contains("2");
	}

	@Test
	void getPackId_emptyWhenFormatHasNoPackId()
	{
		final CustomHUQRCode qrCode = CustomHUQRCode.ofParsedScannedCode(ParsedScannedCode.builder()
				.scannedCode(ScannedCode.ofString("any"))
				.build());

		assertThat(qrCode.getPackId()).isEmpty();
	}
}
