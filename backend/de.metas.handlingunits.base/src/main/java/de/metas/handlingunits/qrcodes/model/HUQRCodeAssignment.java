package de.metas.handlingunits.qrcodes.model;

import de.metas.handlingunits.HuId;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class HUQRCodeAssignment
{
	@NonNull HUQRCodeUniqueId id;
	@NonNull HuId huId;
}
