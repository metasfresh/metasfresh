package de.metas.handlingunits.qrcodes.special;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.StringUtils;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public final class PickOnTheFlyQRCode implements IHUQRCode
{
	public static final PickOnTheFlyQRCode instance = new PickOnTheFlyQRCode();

	// keep in sync with misc/services/mobile-webui/mobile-webui-frontend/src/containers/activities/picking/PickConfig.js - PICK_ON_THE_FLY_QRCODE constant
	private static final String STRING_VALUE = "PICK_ON_THE_FLY";

	@Nullable
	public static PickOnTheFlyQRCode fromScannedCodeOrNullIfNotHandled(@NonNull final ScannedCode scannedCode)
	{
		return fromStringOrNullIfNotHandled(scannedCode.getAsString());
	}

	@Nullable
	public static PickOnTheFlyQRCode fromStringOrNullIfNotHandled(@NonNull final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		if (STRING_VALUE.equals(stringNorm))
		{
			return instance;
		}
		else
		{
			return null;
		}
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@JsonValue
	public String getAsString() {return STRING_VALUE;}

	@Override
	public Optional<BigDecimal> getWeightInKg() {return Optional.empty();}

	@Override
	public Optional<LocalDate> getBestBeforeDate() {return Optional.empty();}

	@Override
	public Optional<String> getLotNumber() {return Optional.empty();}
}
