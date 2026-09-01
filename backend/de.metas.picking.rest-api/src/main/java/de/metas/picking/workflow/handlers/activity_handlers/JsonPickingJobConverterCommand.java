package de.metas.picking.workflow.handlers.activity_handlers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.PickAttribute;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.config.mobileui.PickingLineGroupBy;
import de.metas.handlingunits.picking.config.mobileui.PickingLineSortBy;
import de.metas.handlingunits.picking.job.model.CurrentPickingTarget;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.rest_api.JsonHUAttributeConverters;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.rest_api.json.JsonLUPickingTarget;
import de.metas.picking.rest_api.json.JsonPickingJob;
import de.metas.picking.rest_api.json.JsonPickingJobLine;
import de.metas.picking.rest_api.json.JsonTUPickingTarget;
import de.metas.picking.rest_api.json.JsonRejectReasonsList;
import de.metas.picking.workflow.CarrierAdviseTargetInfo;
import de.metas.picking.workflow.DisplayValueProvider;
import de.metas.picking.workflow.DisplayValueProviderService;
import de.metas.picking.workflow.PackedHUCarrierAdviseService;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.uom.UomId;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static org.adempiere.mm.attributes.api.AttributeConstants.ATTR_BestBeforeDate;

public class JsonPickingJobConverterCommand
{
	private static final AdMessageKey LAST_PICKED_HU_BEST_BEFORE_DATE = AdMessageKey.of("de.metas.picking.workflow.handlers.activity_handlers.LAST_PICKED_HU_BEST_BEFORE_DATE");

	// services
	@NonNull private final PickingJobProductService productService;
	@NonNull private final PickingJobHUService huService;
	@NonNull private final PickingJobRestService pickingJobRestService;
	@NonNull private final PackedHUCarrierAdviseService packedHUCarrierAdviseService;

	@NonNull private final PickingJob pickingJob;
	@NonNull private final JsonOpts jsonOpts;

	@NonNull private final MobileUIPickingUserProfile profile;
	@NonNull private final PickingJobOptions pickingJobOptions;
	@NonNull private final DisplayValueProvider displayValueProvider;
	@NonNull private final HUCache huCache;

	@Builder
	private JsonPickingJobConverterCommand(
			@NonNull final PickingJobProductService productService,
			@NonNull final PickingJobHUService huService,
			@NonNull final PickingJobRestService pickingJobRestService,
			@NonNull final PackedHUCarrierAdviseService packedHUCarrierAdviseService,
			@NonNull final DisplayValueProviderService displayValueProviderService,
			//
			@NonNull final PickingJob pickingJob,
			@NonNull final JsonOpts jsonOpts)
	{
		this.productService = productService;
		this.huService = huService;
		this.pickingJobRestService = pickingJobRestService;
		this.packedHUCarrierAdviseService = packedHUCarrierAdviseService;
		this.pickingJob = pickingJob;
		this.jsonOpts = jsonOpts;

		this.profile = pickingJobRestService.getProfile();
		this.pickingJobOptions = pickingJobRestService.getPickingJobOptions(pickingJob.getCustomerId());
		this.displayValueProvider = displayValueProviderService.newDisplayValueProvider(profile);
		this.huCache = HUCache.init(huService::getById);
	}

	public JsonPickingJob execute()
	{
		final JsonPickingJob.JsonPickingJobBuilder builder = JsonPickingJob.builderFrom(pickingJob)
				.lines(toJsonPickingJobLines())
				.qtyRejectedReasons(JsonRejectReasonsList.of(pickingJobRestService.getQtyRejectedReasons(), jsonOpts))
				.allowSkippingRejectedReason(pickingJobOptions.isAllowSkippingRejectedReason())
				.allowedPickToStructures(pickingJobOptions.getAllowedPickToStructures().toAllowedSet())
				.readAttributes(pickingJobOptions.getPickAttributes().getAttributesToReadSet())
				.showPromptWhenOverPicking(pickingJobOptions.isShowConfirmationPromptWhenOverPick())
				.anonymousPickHUsOnTheFly(pickingJob.isAnonymousPickHUsOnTheFly())
				.completeJobAutomatically(pickingJobOptions.getCompleteJobAutomatically().isTrue());

		// The LU/TU pick targets are already set by JsonPickingJob.builderFrom(pickingJob).

		final CarrierAdviseTargetInfo jobCarrierAdvise = packedHUCarrierAdviseService.resolveInfo(pickingJob, null, jsonOpts.getAdLanguage());

		// Job-level carrier-advise flags — the mobile UI reads these for the job view's advise button.
		builder.carrierAdviseAvailable(jobCarrierAdvise.isAvailable())
				.carrierAdviseReadOnly(jobCarrierAdvise.isReadOnly())
				.carrierProductCaption(jobCarrierAdvise.getProductCaption())
				.carrierAdviseDisabledReason(jobCarrierAdvise.getDisabledReason());

		return builder.build();
	}

	@NonNull
	private List<JsonPickingJobLine> toJsonPickingJobLines()
	{
		final PickingLineGroupBy groupBy = pickingJobOptions.getPickingLineGroupBy().orElse(PickingLineGroupBy.NONE);
		final PickingLineSortBy sortBy = pickingJobOptions.getPickingLineSortBy().orElse(PickingLineSortBy.ORDER_LINE_SEQ_NO);
		final Map<String, List<PickingJobLine>> sortedGroupedLines = groupBy.groupLines(pickingJob.getLines(), sortBy);
		cacheLastPickedHUsForEachLineIfNeeded();

		final ArrayList<JsonPickingJobLine> result = new ArrayList<>();
		for (final Map.Entry<String, List<PickingJobLine>> group : sortedGroupedLines.entrySet())
		{
			group.getValue().stream()
					.map(line -> enrichLineCarrierAdvise(
							JsonPickingJobLine.builderFrom(line, this::getUOMSymbolById, jsonOpts)
									.displayGroupKey(group.getKey())
									.allowPickingAnyHU(pickingJob.isAllowPickingAnyHU())
									.readAttributes(computeLineReadAttributes(line))
									.additionalHeaderProperties(JsonWFProcessHeaderProperties.of(
											getAdditionalHeaderProperties(line), jsonOpts)),
							line)
							.build()
					)
					.forEach(result::add);
		}
		return ImmutableList.copyOf(result);
	}

	/**
	 * Exposes the carrier-advise flags on the line (the mobile UI reads them for the line view's advise button)
	 * and sets the current LU/TU pick target, if any. The carrier-advise flags live only on the line/job, never
	 * on the pick target: the UI shows one package at a time, so the current target's advise is always the
	 * line's (line view) / job's (job view) value — a per-target copy carried no extra information.
	 */
	@NonNull
	private JsonPickingJobLine.JsonPickingJobLineBuilder enrichLineCarrierAdvise(
			@NonNull final JsonPickingJobLine.JsonPickingJobLineBuilder lineBuilder,
			@NonNull final PickingJobLine line)
	{
		final CurrentPickingTarget currentPickingTarget = line.getCurrentPickingTarget();

		// The carrier product is the line's own job-scoped persisted value (or the job's shared value for
		// header-level aggregation).
		final CarrierAdviseTargetInfo lineInfo = packedHUCarrierAdviseService.resolveInfo(pickingJob, line, jsonOpts.getAdLanguage());
		lineBuilder.carrierAdviseAvailable(lineInfo.isAvailable())
				.carrierAdviseReadOnly(lineInfo.isReadOnly())
				.carrierProductCaption(lineInfo.getProductCaption())
				.carrierAdviseDisabledReason(lineInfo.getDisabledReason());

		final LUPickingTarget existingLuTarget = currentPickingTarget.getLuPickingTarget()
				.filter(LUPickingTarget::isExistingLU)
				.orElse(null);
		if (existingLuTarget != null)
		{
			return lineBuilder.luPickingTarget(JsonLUPickingTarget.of(existingLuTarget));
		}

		final TUPickingTarget existingTuTarget = currentPickingTarget.getTuPickingTarget()
				.filter(TUPickingTarget::isExistingTU)
				.orElse(null);
		if (existingTuTarget != null)
		{
			return lineBuilder.tuPickingTarget(JsonTUPickingTarget.of(existingTuTarget));
		}

		return lineBuilder;
	}

	/**
	 * Per-line read attributes = the job-level set plus {@link PickAttribute#SerialNo} when this line's product
	 * opts into serial-no picking and its attribute set supports SerialNo.
	 */
	@NonNull
	private Set<PickAttribute> computeLineReadAttributes(@NonNull final PickingJobLine line)
	{
		final Set<PickAttribute> base = pickingJobOptions.getPickAttributes().getAttributesToReadSet();
		if (!productService.isSerialNoPickingEnabled(line.getProductId()))
		{
			return base;
		}
		return ImmutableSet.<PickAttribute>builder().addAll(base).add(PickAttribute.SerialNo).build();
	}

	@Nullable
	private ITranslatableString getUOMSymbolById(@Nullable final UomId uomId)
	{
		if (uomId == null)
		{
			return TranslatableStrings.empty();
		}
		return productService.getUOMSymbolById(uomId);
	}

	@NonNull
	private WFProcessHeaderProperties getAdditionalHeaderProperties(@NonNull final PickingJobLine line)
	{
		final WFProcessHeaderProperties.WFProcessHeaderPropertiesBuilder builder = WFProcessHeaderProperties.builder();
		getLastPickedBestBeforeDate(line).ifPresent(builder::entry);

		profile.getDetailFieldsInOrder()
				.stream()
				.map(field -> WFProcessHeaderProperty.builder()
						.caption(field.getCaption())
						.value(displayValueProvider.getDisplayValue(field, line))
						.build())
				.filter(WFProcessHeaderProperty::isValueNotBlank)
				.forEach(builder::entry);

		return builder.build();
	}

	private Optional<WFProcessHeaderProperty> getLastPickedBestBeforeDate(@NonNull final PickingJobLine line)
	{
		if (!pickingJobOptions.isShowLastPickedBestBeforeDateForLines())
		{
			return Optional.empty();
		}

		final String lastPickedHUBestBeforeDate = line.getLastPickedHUId()
				.flatMap(this::getBestBeforeDate)
				.orElse("");

		return Optional.of(WFProcessHeaderProperty.builder()
				.caption(TranslatableStrings.adMessage(LAST_PICKED_HU_BEST_BEFORE_DATE))
				.value(lastPickedHUBestBeforeDate)
				.build());
	}

	private Optional<String> getBestBeforeDate(final HuId huId)
	{
		final I_M_HU hu = huCache.getOrLoad(huId);
		final IAttributeValue attributeValue = huService.getAttributeValueIfExists(hu, ATTR_BestBeforeDate).orElse(null);
		if (attributeValue == null)
		{
			return Optional.empty();
		}

		final Date bestBeforeDate = attributeValue.getValueAsDate();
		final Object bestBeforeDateDisplay = JsonHUAttributeConverters.toDisplayValue(bestBeforeDate, jsonOpts.getAdLanguage());
		return Optional.of(String.valueOf(bestBeforeDateDisplay));
	}

	private void cacheLastPickedHUsForEachLineIfNeeded()
	{
		if (!pickingJobOptions.isShowLastPickedBestBeforeDateForLines())
		{
			return;
		}

		final Set<HuId> huIds = pickingJob.streamLines()
				.map(PickingJobLine::getLastPickedHUId)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(ImmutableSet.toImmutableSet());

		huCache.cacheHUs(huService.getByIds(huIds));
	}

	@Value
	@Builder
	private static class HUCache
	{
		public static HUCache init(@NonNull Function<HuId, I_M_HU> loadHU)
		{
			return HUCache.builder()
					.loadHU(loadHU)
					.build();
		}

		@NonNull Function<HuId, I_M_HU> loadHU;
		Map<HuId, I_M_HU> huById = new ConcurrentHashMap<>();

		public void cacheHUs(@NonNull final List<I_M_HU> hus)
		{
			hus.forEach(hu -> huById.put(HuId.ofRepoId(hu.getM_HU_ID()), hu));
		}

		@NonNull
		public I_M_HU getOrLoad(@NonNull final HuId huId)
		{
			return huById.computeIfAbsent(huId, loadHU);
		}
	}

}
