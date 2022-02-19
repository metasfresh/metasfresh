package de.metas.global_qrcodes.service;

import de.metas.global_qrcodes.PrintableQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * The structure of this class it's important for the jasper report, so pls don't change it!
 */
@Value
@Builder
@Jacksonized
public class JsonPrintableQRCodesList
{
	@NonNull
	List<PrintableQRCode> qrCodes;
}
