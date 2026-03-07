package de.metas.handlingunits.rest_api;

import com.google.common.collect.ImmutableList;
import de.metas.printing.frontend.FrontendPrinterData;
import de.metas.printing.frontend.FrontendPrinterDataItem;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonPrintHULabelResponse
{
	List<JsonPrintDataItem> printData;

	//
	//
	//

	@Value
	@Builder
	public static class JsonPrintDataItem
	{
		@NonNull String printerName;
		@NonNull String printerURI;
		@NonNull String filename;
		@Nullable String dataBase64Encoded;

		public static List<JsonPrintDataItem> of(final FrontendPrinterData data)
		{
			return data.getItems().stream()
					.map(JsonPrintDataItem::of)
					.collect(ImmutableList.toImmutableList());
		}

		public static JsonPrintDataItem of(final FrontendPrinterDataItem item)
		{
			return builder()
					.printerName(item.getPrinterName())
					.printerURI(item.getPrinterURI().toString())
					.filename(item.getFilename())
					.dataBase64Encoded(item.getDataBase64Encoded())
					.build();
		}
	}
}
