package de.metas.picking.qrcode;

import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PickingSlotQRCodeTest
{
	@Test
	void ofGlobalQRCodeJsonString_toGlobalQRCodeJsonString()
	{
		final PickingSlotIdAndCaption pickingSlot = PickingSlotIdAndCaption.of(PickingSlotId.ofRepoId(123), "pickingSlot1");
		final PickingSlotQRCode qrCode = PickingSlotQRCode.ofPickingSlotIdAndCaption(pickingSlot);

		final String globalQRCode = qrCode.toGlobalQRCodeJsonString();
		System.out.println("globalQRCode=" + globalQRCode);

		Assertions.assertThat(PickingSlotQRCode.ofGlobalQRCodeJsonString(globalQRCode))
				.usingRecursiveComparison()
				.isEqualTo(qrCode);
	}

	/**
	 * IMPORTANT: Makes sure that already printed QR codes are still valid.
	 */
	@Test
	void fromGlobalQRCode_v1()
	{
		Assertions.assertThat(PickingSlotQRCode.ofGlobalQRCodeJsonString("PICKING_SLOT#1#{\"pickingSlotId\":123,\"caption\":\"pickingSlot1\"}"))
				.usingRecursiveComparison()
				.isEqualTo(PickingSlotQRCode.builder()
						.pickingSlotId(PickingSlotId.ofRepoId(123))
						.caption("pickingSlot1")
						.build());
	}

}