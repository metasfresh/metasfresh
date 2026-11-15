package de.metas.handlingunits;

import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.NonNull;
import lombok.Value;

/** An HU that was just created, with its HU QR code. */
@Value
public class CreatedHU
{
	@NonNull HuId huId;
	@NonNull HUQRCode qrCode;
}
