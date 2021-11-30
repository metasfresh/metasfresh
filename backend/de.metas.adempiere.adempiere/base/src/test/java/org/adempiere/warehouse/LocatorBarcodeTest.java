package org.adempiere.warehouse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LocatorBarcodeTest
{

	@Test
	void ofBarcodeString_getAsString()
	{
		LocatorId locatorId = LocatorId.ofRepoId(1000001, 1000002);
		final LocatorBarcode barcode = LocatorBarcode.ofLocatorId(locatorId);

		Assertions.assertThat(LocatorBarcode.ofBarcodeString(barcode.getAsString())).isEqualTo(barcode);
	}
}