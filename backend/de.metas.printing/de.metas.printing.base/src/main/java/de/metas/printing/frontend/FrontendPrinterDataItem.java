package de.metas.printing.frontend;

import com.google.common.io.BaseEncoding;
import de.metas.printing.HardwarePrinter;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.MimeType;

import java.net.URI;

@Value
public class FrontendPrinterDataItem
{
	@NonNull String printerName;
	@NonNull URI printerURI;
	@NonNull String filename;
	@NonNull String contentType;
	byte[] data;

	@Builder
	private FrontendPrinterDataItem(
			@NonNull final HardwarePrinter printer,
			@NonNull final String filename,
			final byte[] data)
	{
		this.printerName = printer.getName();
		this.printerURI = printer.getPrinterURI();
		this.filename = filename;
		this.contentType = MimeType.getMimeType(filename);
		this.data = data;
	}

	public String getDataBase64Encoded() {return BaseEncoding.base64().encode(data);}
}
