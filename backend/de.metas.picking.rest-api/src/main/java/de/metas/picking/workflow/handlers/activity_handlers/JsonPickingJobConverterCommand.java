package de.metas.picking.workflow.handlers.activity_handlers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.config.mobileui.PickingLineGroupBy;
import de.metas.handlingunits.picking.config.mobileui.PickingLineSortBy;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.rest_api.JsonHUAttributeConverters;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.rest_api.json.JsonPickingJob;
import de.metas.picking.rest_api.json.JsonPickingJobLine;
import de.metas.picking.rest_api.json.JsonRejectReasonsList;
import de.metas.picking.workflow.DisplayValueProvider;
import de.metas.picking.workflow.DisplayValueProviderService;
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
			@NonNull final DisplayValueProviderService displayValueProviderService,
			//
			@NonNull final PickingJob pickingJob,
			@NonNull final JsonOpts jsonOpts)
	{
		this.productService = productService;
		this.huService = huService;
		this.pickingJobRestService = pickingJobRestService;
		this.pickingJob = pickingJob;
		this.jsonOpts = jsonOpts;

		this.profile = pickingJobRestService.getProfile();
		this.pickingJobOptions = pickingJobRestService.getPickingJobOptions(pickingJob.getCustomerId());
		this.displayValueProvider = displayValueProviderService.newDisplayValueProvider(profile);
		this.huCache = HUCache.init(huService::getById);
	}

	public JsonPickingJob execute()
	{
		return JsonPickingJob.builderFrom(pickingJob)
				.lines(toJsonPickingJobLines())
				.qtyRejectedReasons(JsonRejectReasonsList.of(pickingJobRestService.getQtyRejectedReasons(), jsonOpts))
				.allowSkippingRejectedReason(pickingJobOptions.isAllowSkippingRejectedReason())
				.allowedPickToStructures(pickingJobOptions.getAllowedPickToStructures().toAllowedSet())
				.readAttributes(pickingJobOptions.getPickAttributes().getAttributesToReadSet())
				.showPromptWhenOverPicking(pickingJobOptions.isShowConfirmationPromptWhenOverPick())
				.anonymousPickHUsOnTheFly(pickingJob.isAnonymousPickHUsOnTheFly())
				.completeJobAutomatically(pickingJobOptions.getCompleteJobAutomatically().isTrue())
				.build();
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
					.map(line -> JsonPickingJobLine.builderFrom(line, this::getUOMSymbolById, jsonOpts)
							.displayGroupKey(group.getKey())
							.allowPickingAnyHU(pickingJob.isAllowPickingAnyHU())
							.additionalHeaderProperties(JsonWFProcessHeaderProperties.of(
									getAdditionalHeaderProperties(line), jsonOpts))
							.build()
					)
					.forEach(result::add);
		}
		return ImmutableList.copyOf(result);
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
