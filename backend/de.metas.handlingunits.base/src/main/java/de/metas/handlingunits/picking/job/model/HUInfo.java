package de.metas.handlingunits.picking.job.model;

import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.movement.HUIdAndQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Objects;

@Value
@Builder
public class HUInfo
{
	@NonNull HuId id;
	@NonNull HUQRCode qrCode;

	public static boolean equals(final HUInfo obj1, final HUInfo obj2) {return Objects.equals(obj1, obj2);}

	public HUIdAndQRCode toHUIdAndQRCode()
	{
		return HUIdAndQRCode.builder().huId(id).huQRCode(qrCode).build();
	}

	public JsonDisplayableQRCode toQRCodeRenderedJson() {return getQrCode().toRenderedJson();}
}
