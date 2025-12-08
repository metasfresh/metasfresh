package de.metas.printing.frontend;

import com.google.common.collect.ImmutableSet;
import de.metas.printing.HardwarePrinter;
import de.metas.printing.HardwarePrinterId;
import de.metas.printing.printingdata.PrintingData;
import de.metas.printing.printingdata.PrintingDataToPDFWriter;
import de.metas.printing.printingdata.PrintingSegment;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FrontendPrinter implements IAutoCloseable
{
	private static final ThreadLocal<FrontendPrinter> threadLocal = new ThreadLocal<>();
	private final PrinterQueuesMap printerQueuesMap = new PrinterQueuesMap();
	private boolean closed = false;

	public static FrontendPrinter start()
	{
		if (threadLocal.get() != null)
		{
			throw new AdempiereException("Frontend printer session already started");
		}

		final FrontendPrinter frontendPrinter = new FrontendPrinter();
		threadLocal.set(frontendPrinter);
		return frontendPrinter;
	}

	public static FrontendPrinter getOrNull()
	{
		return threadLocal.get();
	}

	@Override
	public void close()
	{
		if (closed)
		{
			return;
		}

		closed = true;
		threadLocal.remove();
	}

	public void add(@NonNull final PrintingData printingData)
	{
		printerQueuesMap.add(printingData);
	}

	public FrontendPrinterData getDataAndClear()
	{
		final FrontendPrinterData data = printerQueuesMap.toFrontendPrinterData();
		printerQueuesMap.clear();
		return data;
	}

	//
	//
	//
	//
	//
	@RequiredArgsConstructor
	private static class PrinterQueuesMap
	{
		private final LinkedHashMap<HardwarePrinterId, PrinterQueue> queuesMap = new LinkedHashMap<>();

		public void add(@NonNull PrintingData printingData)
		{
			printingData.getSegments().forEach(segment -> add(printingData, segment));
		}

		private void add(
				@NonNull PrintingData printingData,
				@NonNull PrintingSegment segment)
		{
			final HardwarePrinter printer = segment.getPrinter();
			getOrCreateQueue(printer).add(printingData, segment);
		}

		private PrinterQueue getOrCreateQueue(final HardwarePrinter printer)
		{
			return queuesMap.computeIfAbsent(printer.getId(), k -> new PrinterQueue(printer));
		}

		public void clear()
		{
			queuesMap.clear();
		}

		public FrontendPrinterData toFrontendPrinterData()
		{
			return queuesMap.values()
					.stream()
					.map(PrinterQueue::toFrontendPrinterData)
					.collect(GuavaCollectors.collectUsingListAccumulator(FrontendPrinterData::of));
		}
	}

	@RequiredArgsConstructor
	private static class PrinterQueue
	{
		@NonNull private final HardwarePrinter printer;
		@NonNull private final ArrayList<PrintingDataAndSegment> segments = new ArrayList<>();

		public void add(
				@NonNull PrintingData printingData,
				@NonNull PrintingSegment segment)
		{
			Check.assumeEquals(this.printer, segment.getPrinter(), "Expected segment printer to match: {}, expected={}", segment, printer);
			segments.add(PrintingDataAndSegment.of(printingData, segment));
		}

		public FrontendPrinterDataItem toFrontendPrinterData()
		{
			return FrontendPrinterDataItem.builder()
					.printer(printer)
					.filename(suggestFilename())
					.data(toByteArray())
					.build();
		}

		private byte[] toByteArray()
		{
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try (final PrintingDataToPDFWriter pdfWriter = new PrintingDataToPDFWriter(baos))
			{
				for (PrintingDataAndSegment printingDataAndSegment : segments)
				{
					pdfWriter.addArchivePartToPDF(printingDataAndSegment.getPrintingData(), printingDataAndSegment.getSegment());
				}
			}
			return baos.toByteArray();
		}

		private String suggestFilename()
		{
			final ImmutableSet<String> filenames = segments.stream()
					.map(PrintingDataAndSegment::getDocumentFileName)
					.collect(ImmutableSet.toImmutableSet());

			return filenames.size() == 1 ? filenames.iterator().next() : "report.pdf";
		}
	}

	@Value(staticConstructor = "of")
	private static class PrintingDataAndSegment
	{
		@NonNull PrintingData printingData;
		@NonNull PrintingSegment segment;

		public String getDocumentFileName()
		{
			return printingData.getDocumentFileName();
		}
	}

}
