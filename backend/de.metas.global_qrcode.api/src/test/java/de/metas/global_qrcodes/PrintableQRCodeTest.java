package de.metas.global_qrcodes;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SnapshotExtension.class)
class PrintableQRCodeTest
{
	private Expect expect;

	private PrintableQRCode newStandardPrintableQRCode()
	{
		return PrintableQRCode.builder()
				.topText("top text")
				.bottomText("bottom text")
				.qrCode("qr code line 1\nline 2\nline 3")
				.build();
	}

	/**
	 * Makes sure that the JSON format is not changing.
	 * This is important because those JSON we will send to JasperReports.
	 */
	@Test
	public void checkJsonFormatIsNotChanging()
	{
		expect.serializer("orderedJson").toMatchSnapshot(newStandardPrintableQRCode());
	}
}