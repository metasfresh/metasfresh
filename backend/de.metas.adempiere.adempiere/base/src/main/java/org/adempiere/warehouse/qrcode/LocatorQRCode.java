package org.adempiere.warehouse.qrcode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_M_Locator;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
public class LocatorQRCode
{
	@NonNull LocatorId locatorId;
	@NonNull String caption;

	public static boolean equals(@Nullable final LocatorQRCode o1, @Nullable final LocatorQRCode o2)
	{
		return Objects.equals(o1, o2);
	}

	@Deprecated
	public String toString() {return toGlobalQRCodeJsonString();}

	@JsonValue
	public String toGlobalQRCodeJsonString() {return LocatorQRCodeJsonConverter.toGlobalQRCodeJsonString(this);}

	@JsonCreator
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

	public GlobalQRCode toGlobalQRCode()
	{
		return LocatorQRCodeJsonConverter.toGlobalQRCode(this);
	}

	public ScannedCode toScannedCode()
	{
		return ScannedCode.ofString(toGlobalQRCodeJsonString());
	}

	public static boolean isTypeMatching(@NonNull final GlobalQRCode globalQRCode)
	{
		return LocatorQRCodeJsonConverter.isTypeMatching(globalQRCode);
	}
}
