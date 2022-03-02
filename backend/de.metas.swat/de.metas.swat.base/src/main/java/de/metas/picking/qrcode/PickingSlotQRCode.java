package de.metas.picking.qrcode;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotIdAndCaption;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
@Jacksonized // NOTE: we are making it json friendly mainly for snapshot testing
public class PickingSlotQRCode
{
	@NonNull PickingSlotId pickingSlotId;
	@NonNull String caption;

	public static boolean equals(@Nullable final PickingSlotQRCode o1, @Nullable final PickingSlotQRCode o2)
	{
		return Objects.equals(o1, o2);
	}

	public String toGlobalQRCodeJsonString() {return PickingSlotQRCodeJsonConverter.toGlobalQRCodeJsonString(this);}

	public static PickingSlotQRCode ofGlobalQRCodeJsonString(@NonNull final String json) {return PickingSlotQRCodeJsonConverter.fromGlobalQRCodeJsonString(json);}

	public static PickingSlotQRCode ofGlobalQRCode(@NonNull final GlobalQRCode globalQRCode) {return PickingSlotQRCodeJsonConverter.fromGlobalQRCode(globalQRCode);}

	public static PickingSlotQRCode ofPickingSlotIdAndCaption(@NonNull final PickingSlotIdAndCaption pickingSlotIdAndCaption)
	{
		return builder()
				.pickingSlotId(pickingSlotIdAndCaption.getPickingSlotId())
				.caption(pickingSlotIdAndCaption.getCaption())
				.build();
	}

	public PrintableQRCode toPrintableQRCode()
	{
		return PrintableQRCode.builder()
				.qrCode(toGlobalQRCodeJsonString())
				.bottomText(caption)
				.build();
	}

}
