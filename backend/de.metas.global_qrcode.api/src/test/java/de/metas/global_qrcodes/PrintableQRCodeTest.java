package de.metas.global_qrcodes;

<<<<<<< HEAD
import io.github.jsonSnapshot.SnapshotConfig;
import io.github.jsonSnapshot.SnapshotMatchingStrategy;
import io.github.jsonSnapshot.matchingstrategy.JSONAssertMatchingStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;

class PrintableQRCodeTest
{
	@BeforeAll
	static void beforeAll()
	{
		start(new SnapshotConfig()
		{
			@Override
			public String getFilePath() {return "src/test/resources/";}

			@Override
			public SnapshotMatchingStrategy getSnapshotMatchingStrategy() {return JSONAssertMatchingStrategy.INSTANCE_STRICT;}
		});
	}
=======
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SnapshotExtension.class)
class PrintableQRCodeTest
{
	private Expect expect;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
		expect(newStandardPrintableQRCode()).toMatchSnapshot();
=======
		expect.serializer("orderedJson").toMatchSnapshot(newStandardPrintableQRCode());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}