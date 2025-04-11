/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.picking.workflow.handlers.activity_handlers;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.config.mobileui.PickingLineGroupBy;
import de.metas.handlingunits.picking.config.mobileui.PickingLineSortBy;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobProgress;
import de.metas.handlingunits.rest_api.JsonHUAttributeConverters;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.rest_api.json.JsonPickingJob;
import de.metas.picking.rest_api.json.JsonPickingJobLine;
import de.metas.picking.rest_api.json.JsonRejectReasonsList;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperties;
import de.metas.workflow.rest_api.model.WFProcessHeaderProperty;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.Params;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static de.metas.picking.workflow.handlers.activity_handlers.PickingWFActivityHelper.getPickingJob;
import static org.adempiere.mm.attributes.api.AttributeConstants.ATTR_BestBeforeDate;

@Component
@RequiredArgsConstructor
public class ActualPickingWFActivityHandler implements WFActivityHandler
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("picking.actualPicking");
	public static final UIComponentType COMPONENTTYPE_PICK_PRODUCTS = UIComponentType.ofString("picking/pickProducts");

	private static final AdMessageKey LAST_PICKED_HU_BEST_BEFORE_DATE = AdMessageKey
			.of("de.metas.picking.workflow.handlers.activity_handlers.LAST_PICKED_HU_BEST_BEFORE_DATE");

	@VisibleForTesting
	public static final String PROP_pickingJob = "pickingJob";

	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final PickingJobRestService pickingJobRestService;

	@Override
	public WFActivityType getHandledActivityType()
	{
		return HANDLED_ACTIVITY_TYPE;
	}

	@Override
	public UIComponent getUIComponent(
			final @NonNull WFProcess wfProcess,
			final @NonNull WFActivity wfActivity,
			final @NonNull JsonOpts jsonOpts)
	{
		final PickingJob pickingJob = getPickingJob(wfProcess);

		return UIComponent.builderFrom(COMPONENTTYPE_PICK_PRODUCTS, wfActivity)
				.properties(Params.builder()
						.valueObj(PROP_pickingJob, toJsonPickingJob(pickingJob, jsonOpts))
						.build())
				.build();
	}

	@Nullable
	private ITranslatableString getUOMSymbolById(@Nullable final UomId uomId)
	{
		if (uomId == null)
		{
			return TranslatableStrings.empty();
		}
		return uomDAO.getUOMSymbolById(uomId);
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		final PickingJob pickingJob = getPickingJob(wfProcess);
		return computeActivityState(pickingJob);
	}

	public static WFActivityStatus computeActivityState(final PickingJob pickingJob)
	{
		final PickingJobProgress progress = pickingJob.getProgress();
		switch (progress)
		{
			case NOT_STARTED:
				return WFActivityStatus.NOT_STARTED;
			case IN_PROGRESS:
				return WFActivityStatus.IN_PROGRESS;
			case DONE:
				return WFActivityStatus.COMPLETED;
			default:
				throw new AdempiereException("Unknown process status: " + progress);
		}
	}

	private JsonPickingJob toJsonPickingJob(@NonNull final PickingJob pickingJob, final @NotNull JsonOpts jsonOpts)
	{
		final PickingJobOptions pickingJobOptions = pickingJobRestService.getPickingJobOptions(pickingJob.getCustomerId());
		return JsonPickingJob.builderFrom(pickingJob)
				.lines(toJsonPickingJobLines(pickingJob, pickingJobOptions, jsonOpts))
				.qtyRejectedReasons(JsonRejectReasonsList.of(pickingJobRestService.getQtyRejectedReasons(), jsonOpts))
				.allowSkippingRejectedReason(pickingJobOptions.isAllowSkippingRejectedReason())
				.pickWithNewLU(pickingJobOptions.isPickWithNewLU())
				.allowNewTU(pickingJobOptions.isAllowNewTU())
				.showPromptWhenOverPicking(pickingJobOptions.isShowConfirmationPromptWhenOverPick())
				.build();
	}

	@NonNull
	private List<JsonPickingJobLine> toJsonPickingJobLines(
			@NonNull final PickingJob pickingJob,
			@NonNull final PickingJobOptions pickingJobOptions,
			@NonNull final JsonOpts jsonOpts)
	{
		final PickingLineGroupBy groupBy = pickingJobOptions.getPickingLineGroupBy().orElse(PickingLineGroupBy.NONE);
		final PickingLineSortBy sortBy = pickingJobOptions.getPickingLineSortBy().orElse(PickingLineSortBy.ORDER_LINE_SEQ_NO);
		final Map<String, List<PickingJobLine>> sortedGroupedLines = groupBy.groupLines(pickingJob.getLines(), sortBy);
		final HUCache huCache = cacheLastPickedHUsForEachLine(pickingJob);

		final ArrayList<JsonPickingJobLine> result = new ArrayList<>();
		for (final Map.Entry<String, List<PickingJobLine>> group : sortedGroupedLines.entrySet())
		{
			group.getValue().stream()
					.map(line -> JsonPickingJobLine.builderFrom(line, this::getUOMSymbolById, jsonOpts)
							.displayGroupKey(group.getKey())
							.allowPickingAnyHU(pickingJob.isAllowPickingAnyHU())
							.additionalHeaderProperties(JsonWFProcessHeaderProperties.of(
									getAdditionalHeaderProperties(line, pickingJobOptions, huCache, jsonOpts), jsonOpts))
							.build()
					)
					.forEach(result::add);
		}
		return ImmutableList.copyOf(result);
	}

	@NonNull
	private WFProcessHeaderProperties getAdditionalHeaderProperties(
			@NonNull final PickingJobLine line,
			@NonNull final PickingJobOptions options,
			@NonNull final HUCache huCache,
			@NonNull final JsonOpts opts)
	{
		final WFProcessHeaderProperties.WFProcessHeaderPropertiesBuilder builder = WFProcessHeaderProperties.builder();
		getLastPickedBestBeforeDate(line, options, huCache, opts).ifPresent(builder::entry);
		return builder.build();
	}

	private Optional<WFProcessHeaderProperty> getLastPickedBestBeforeDate(
			@NonNull final PickingJobLine line,
			@NonNull final PickingJobOptions pickingJobOptions,
			@NonNull final HUCache huCache,
			@NonNull final JsonOpts opts)
	{
		if (!pickingJobOptions.isShowLastPickedBestBeforeDateForLines())
		{
			return Optional.empty();
		}

		final String lastPickedHUBestBeforeDate = line.getLastPickedHUId()
				.map(huId -> huAttributesBL.getAttributeValue(huCache.getOrLoad(huId), ATTR_BestBeforeDate))
				.map(IAttributeValue::getValueAsDate)
				.map(date -> JsonHUAttributeConverters.toDisplayValue(date, opts.getAdLanguage()))
				.map(String::valueOf)
				.orElse("");

		return Optional.of(WFProcessHeaderProperty.builder()
								   .caption(msgBL.getTranslatableMsgText(LAST_PICKED_HU_BEST_BEFORE_DATE))
								   .value(lastPickedHUBestBeforeDate)
								   .build());
	}

	@NonNull
	private HUCache cacheLastPickedHUsForEachLine(@NonNull final PickingJob job)
	{
		final Set<HuId> huIds = job.streamLines()
				.map(PickingJobLine::getLastPickedHUId)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(ImmutableSet.toImmutableSet());

		final HUCache cache = HUCache.builder()
				.loadHU(handlingUnitsBL::getById)
				.build();

		cache.cacheHUs(handlingUnitsBL.getByIds(huIds));

		return cache;
	}

	@Value
	@Builder
	private static class HUCache
	{
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
