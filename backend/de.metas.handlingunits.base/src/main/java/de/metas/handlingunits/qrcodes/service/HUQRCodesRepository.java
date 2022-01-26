package de.metas.handlingunits.qrcodes.service;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HUQRCodesRepository
{
	public Optional<HuId> getHUIdByQRCode(@NonNull final HUQRCode huQRCode)
	{
		// TODO: implement
		return Optional.empty();
	}
}
