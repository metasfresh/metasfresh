package de.metas.scannable_code;

import de.metas.global_qrcodes.PrintableQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class PrintableScannedCode
{
	@NonNull ScannedCode code;
	@NonNull String displayable;

	@NonNull
	public static PrintableScannedCode of(@NonNull ScannedCode scannedCode)
	{
		return builder()
				.code(scannedCode)
				.displayable(scannedCode.getAsString())
				.build();
	}

	@Nullable
	public static PrintableScannedCode ofNullable(@Nullable PrintableQRCode qrCode) {return qrCode != null ? of(qrCode) : null;}

	@NonNull
	public static PrintableScannedCode of(@NonNull PrintableQRCode qrCode)
	{
		return builder()
				.code(ScannedCode.ofString(qrCode.getQrCode()))
				.displayable(qrCode.getDisplayableString())
				.build();
	}

	public JsonPrintableScannedCode toJson()
	{
		return JsonPrintableScannedCode.builder()
				.code(code)
				.displayable(displayable)
				.build();
	}
}
