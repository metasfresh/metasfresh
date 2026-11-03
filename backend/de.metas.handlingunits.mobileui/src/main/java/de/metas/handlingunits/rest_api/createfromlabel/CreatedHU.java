package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.NonNull;
import lombok.Value;

/** The HU that "create HU from a scanned label" made, with its HU QR code. */
@Value
public class CreatedHU
{
	@NonNull HuId huId;
	@NonNull HUQRCode qrCode;
}
