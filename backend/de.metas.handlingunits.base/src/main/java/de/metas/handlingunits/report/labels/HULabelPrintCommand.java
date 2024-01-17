package de.metas.handlingunits.report.labels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.process.api.HUProcessDescriptor;
import de.metas.handlingunits.process.api.IMHUProcessDAO;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUToReport;
import de.metas.i18n.ExplainedOptional;
import de.metas.process.AdProcessId;
import de.metas.report.PrintCopies;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

class HULabelPrintCommand
{
	private final HULabelConfigService huLabelConfigService;
	private final IMHUProcessDAO huProcessDAO;
	private final HUQRCodesService huQRCodesService;
	private final HULabelService huLabelService;
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@NonNull final HULabelPrintRequest request;

	@Builder
	private HULabelPrintCommand(
			final @NonNull HULabelConfigService huLabelConfigService,
			final @NonNull IMHUProcessDAO huProcessDAO,
			final @NonNull HUQRCodesService huQRCodesService,
			final @NonNull HULabelService huLabelService,
			final @NonNull HULabelPrintRequest request)
	{
		this.huLabelConfigService = huLabelConfigService;
		this.huProcessDAO = huProcessDAO;
		this.huQRCodesService = huQRCodesService;
		this.huLabelService = huLabelService;

		this.request = request;
	}

	public void execute()
	{
		final BatchToPrintCollector batchToPrintCollector = newBatchesToPrintCollector();
		batchToPrintCollector.explodeAndAddAll(request.getHus());
		if (batchToPrintCollector.isEmpty())
		{
			return;
		}

		huQRCodesService.generateForExistingHUs(batchToPrintCollector.getHuIds());

		trxManager.runAfterCommit(() -> batchToPrintCollector.forEach(this::printBatchNow));
	}

	private BatchToPrintCollector newBatchesToPrintCollector()
	{
		return BatchToPrintCollector.builder()
				.huLabelConfigProvider(new HULabelConfigProvider(huLabelConfigService, request.getSourceDocType()))
				.huProcessDAO(huProcessDAO)
				.failOnMissingLabelConfig(request.isFailOnMissingLabelConfig())
				.onlyIfAutoPrint(request.isOnlyIfAutoPrint())
				.printCopiesOverride(request.getPrintCopiesOverride())
				.build();
	}

	private void printBatchNow(final BatchToPrint batchToPrint)
	{
		final HULabelDirectPrintRequest directPrintRequest = HULabelDirectPrintRequest.builder()
				.hus(batchToPrint.getHus())
				.printFormatProcessId(batchToPrint.getPrintInstructions().getPrintFormatProcessId())
				.onlyOneHUPerPrint(batchToPrint.getPrintInstructions().isOnlyOneHUPerPrint())
				.printCopies(batchToPrint.getPrintInstructions().getCopies())
				.build();

		huLabelService.printNow(directPrintRequest);
	}
	//
	//
	//

	private static class HULabelConfigProvider
	{
		private final HULabelConfigService huLabelConfigService;

		private final HULabelSourceDocType sourceDocType;

		private final HashMap<HULabelConfigQuery, ExplainedOptional<HULabelConfig>> cache = new HashMap<>();

		private HULabelConfigProvider(
				@NonNull final HULabelConfigService huLabelConfigService,
				@NonNull final HULabelSourceDocType sourceDocType)
		{
			this.huLabelConfigService = huLabelConfigService;
			this.sourceDocType = sourceDocType;
		}

		private ExplainedOptional<HULabelConfig> getLabelConfig(final @NonNull HUToReport hu)
		{
			return cache.computeIfAbsent(toHULabelConfigQuery(hu), huLabelConfigService::getFirstMatching);
		}

		private HULabelConfigQuery toHULabelConfigQuery(final @NonNull HUToReport hu)
		{
			return HULabelConfigQuery.builder()
					.sourceDocType(sourceDocType)
					.huUnitType(hu.getHUUnitType())
					.bpartnerId(hu.getBPartnerId())
					.build();
		}
	}

	private static class BatchToPrintCollector
	{
		private final HULabelConfigProvider labelConfigProvider;
		private final IMHUProcessDAO huProcessDAO;

		private final boolean failOnMissingLabelConfig;
		private final boolean onlyIfAutoPrint;
		private final PrintCopies printCopiesOverride;
		private final ArrayList<BatchToPrint> batches = new ArrayList<>();
		private final HashSet<HuId> huIdsCollected = new HashSet<>();

		@Builder
		private BatchToPrintCollector(
				@NonNull final HULabelConfigProvider huLabelConfigProvider,
				@NonNull final IMHUProcessDAO huProcessDAO,
				final boolean failOnMissingLabelConfig,
				final boolean onlyIfAutoPrint,
				@Nullable final PrintCopies printCopiesOverride)
		{
			this.labelConfigProvider = huLabelConfigProvider;
			this.huProcessDAO = huProcessDAO;

			this.failOnMissingLabelConfig = failOnMissingLabelConfig;
			this.onlyIfAutoPrint = onlyIfAutoPrint;
			this.printCopiesOverride = printCopiesOverride;
		}

		public void explodeAndAddAll(@NonNull final List<HUToReport> hus)
		{
			for (final HUToReport hu : hus)
			{
				explodeAndAdd(hu);
			}
		}

		private void explodeAndAdd(final HUToReport hu)
		{
			final HULabelConfig huLabelConfig = getLabelConfigOrNull(hu);
			final HUProcessDescriptor huProcessDescriptor = huLabelConfig != null
					? huProcessDAO.getByProcessIdOrNull(huLabelConfig.getPrintFormatProcessId())
					: null;

			if (huProcessDescriptor != null)
			{
				explodeAndAdd_using_HUProcessDescriptor(hu, huProcessDescriptor, huLabelConfig.getAutoPrintCopies());
			}
			else
			{
				explodeAndAdd_using_standardWay(hu, huLabelConfig);
			}
		}

		private void explodeAndAdd_using_HUProcessDescriptor(
				@NonNull final HUToReport hu,
				@NonNull final HUProcessDescriptor huProcessDescriptor,
				@NonNull final PrintCopies autoPrintCopies)
		{
			//
			// Explode:
			final List<HUToReport> explodedHUs = hu.streamRecursively()
					.filter(huProcessDescriptor::isMatching)
					.collect(ImmutableList.toImmutableList());
			if (explodedHUs.isEmpty())
			{
				return;
			}

			//
			// Add:
			final PrintInstructions printInstructions = PrintInstructions.builder()
					.printFormatProcessId(huProcessDescriptor.getProcessId())
					.copies(autoPrintCopies)
					.onlyOneHUPerPrint(false)
					.build();

			explodedHUs.forEach(explodedHU -> add(explodedHU, printInstructions));
		}

		private void explodeAndAdd_using_standardWay(
				@NonNull final HUToReport hu,
				@Nullable final HULabelConfig huLabelConfig)
		{
			if (huLabelConfig != null)
			{
				add(hu, PrintInstructions.of(huLabelConfig));
			}

			final HuUnitType huUnitType = hu.getHUUnitType();
			if (huUnitType == HuUnitType.LU)
			{
				for (final HUToReport includedHU : hu.getIncludedHUs())
				{
					final HULabelConfig includeHULabelConfig = getLabelConfigOrNull(includedHU);
					if (includeHULabelConfig != null)
					{
						add(includedHU, PrintInstructions.of(includeHULabelConfig));
					}
				}
			}
		}

		@Nullable
		private HULabelConfig getLabelConfigOrNull(final HUToReport hu)
		{
			final ExplainedOptional<HULabelConfig> optionalLabelConfig = labelConfigProvider.getLabelConfig(hu);
			if (optionalLabelConfig.isPresent())
			{
				HULabelConfig huLabelConfig = optionalLabelConfig.get();

				if (onlyIfAutoPrint && !huLabelConfig.isAutoPrint())
				{
					return null;
				}

				if (printCopiesOverride != null)
				{
					huLabelConfig = huLabelConfig.withAutoPrintCopies(printCopiesOverride);
				}

				return huLabelConfig;
			}
			else
			{
				if (failOnMissingLabelConfig)
				{
					throw new AdempiereException(optionalLabelConfig.getExplanation());
				}
				else
				{
					return null;
				}
			}
		}

		private void add(@NonNull final HUToReport hu, @NonNull final PrintInstructions printInstructions)
		{
			// Don't add it if we already considered it
			if (!huIdsCollected.add(hu.getHUId()))
			{
				return;
			}

			final BatchToPrint lastBatch = !batches.isEmpty() ? batches.get(batches.size() - 1) : null;

			final BatchToPrint batch;
			if (lastBatch == null || !lastBatch.isMatching(printInstructions))
			{
				batch = new BatchToPrint(printInstructions);
				batches.add(batch);
			}
			else
			{
				batch = lastBatch;
			}

			batch.addHU(hu);
		}

		public boolean isEmpty() {return huIdsCollected.isEmpty();}

		public ImmutableSet<HuId> getHuIds() {return ImmutableSet.copyOf(huIdsCollected);}

		public void forEach(@NonNull final Consumer<BatchToPrint> action)
		{
			batches.forEach(action);
		}
	}

	@Getter
	private static class BatchToPrint
	{
		@NonNull private final PrintInstructions printInstructions;
		@NonNull private final ArrayList<HUToReport> hus = new ArrayList<>();

		private BatchToPrint(final @NonNull PrintInstructions printInstructions) {this.printInstructions = printInstructions;}

		public boolean isMatching(@NonNull final PrintInstructions printInstructions)
		{
			return Objects.equals(this.printInstructions, printInstructions);
		}

		public void addHU(@NonNull final HUToReport hu) {this.hus.add(hu);}
	}

	@Value
	@Builder
	private static class PrintInstructions
	{
		@NonNull AdProcessId printFormatProcessId;
		@Builder.Default PrintCopies copies = PrintCopies.ONE;
		@Builder.Default boolean onlyOneHUPerPrint = true;

		public static PrintInstructions of(HULabelConfig huLabelConfig)
		{
			return builder()
					.printFormatProcessId(huLabelConfig.getPrintFormatProcessId())
					.copies(huLabelConfig.getAutoPrintCopies())
					// IMPORTANT: call the report with one HU only because label reports are working only if we provide M_HU_ID parameter.
					// We changed HUReportExecutor to provide the M_HU_ID parameter and as AD_Table_ID/Record_ID in case only one HU is provided.
					.onlyOneHUPerPrint(true)
					.build();
		}
	}

}
