package de.metas.printing.frontend;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class FrontendPrinterData
{
	public static final FrontendPrinterData EMPTY = new FrontendPrinterData(ImmutableList.of());

	@NonNull ImmutableList<FrontendPrinterDataItem> items;

	private FrontendPrinterData(@NonNull final List<FrontendPrinterDataItem> items)
	{
		this.items = ImmutableList.copyOf(items);
	}

	public static FrontendPrinterData of(@NonNull final List<FrontendPrinterDataItem> items)
	{
		return items.isEmpty() ? EMPTY : new FrontendPrinterData(items);
	}
}
