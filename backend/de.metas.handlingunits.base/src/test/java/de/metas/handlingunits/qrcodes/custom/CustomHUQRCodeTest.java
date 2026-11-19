package de.metas.handlingunits.qrcodes.custom;

import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ParsedScannedCode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomHUQRCodeTest
{
	@Nested
	class getPackId
	{
		@Test
		void returnsParsedPackId()
		{
			final CustomHUQRCode qrCode = CustomHUQRCode.ofParsedScannedCode(ParsedScannedCode.builder()
					.scannedCode(ScannedCode.ofString("any"))
					.packId("2")
					.build());

			assertThat(qrCode.getPackId()).hasValue("2");
		}

		@Test
		void emptyWhenFormatHasNoPackId()
		{
			final CustomHUQRCode qrCode = CustomHUQRCode.ofParsedScannedCode(ParsedScannedCode.builder()
					.scannedCode(ScannedCode.ofString("any"))
					.build());

			assertThat(qrCode.getPackId()).isEmpty();
		}
	}
}
