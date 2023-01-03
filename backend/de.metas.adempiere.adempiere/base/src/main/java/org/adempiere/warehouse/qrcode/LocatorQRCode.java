package org.adempiere.warehouse.qrcode;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.PrintableQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_M_Locator;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
@Jacksonized // NOTE: we are making it json friendly mainly for snapshot testing
public class LocatorQRCode
{
	@NonNull LocatorId locatorId;
	@NonNull String caption;

	public static boolean equals(@Nullable final LocatorQRCode o1, @Nullable final LocatorQRCode o2)
	{
		return Objects.equals(o1, o2);
	}

	public String toGlobalQRCodeJsonString() {return LocatorQRCodeJsonConverter.toGlobalQRCodeJsonString(this);}

	public static LocatorQRCode ofGlobalQRCodeJsonString(final String json) {return LocatorQRCodeJsonConverter.fromGlobalQRCodeJsonString(json);}

	public static LocatorQRCode ofGlobalQRCode(final GlobalQRCode globalQRCode) {return LocatorQRCodeJsonConverter.fromGlobalQRCode(globalQRCode);}

	public static LocatorQRCode ofLocator(@NonNull final I_M_Locator locator)
	{
		return builder()
				.locatorId(LocatorId.ofRepoId(locator.getM_Warehouse_ID(), locator.getM_Locator_ID()))
				.caption(locator.getValue())
				.build();
	}

	public PrintableQRCode toPrintableQRCode()
	{
		return PrintableQRCode.builder()
				.qrCode(toGlobalQRCodeJsonString())
				.bottomText(caption)
				.build();
	}

	public static boolean isTypeMatching(@NonNull final GlobalQRCode globalQRCode)
	{
		return LocatorQRCodeJsonConverter.isTypeMatching(globalQRCode);
	}
}
