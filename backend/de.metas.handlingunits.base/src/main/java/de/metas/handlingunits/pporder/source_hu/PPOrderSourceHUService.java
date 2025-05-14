package de.metas.handlingunits.pporder.source_hu;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PPOrderSourceHUService
{
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final PPOrderSourceHURepository ppOrderSourceHURepository;
	private final PPOrderIssueScheduleService ppOrderIssueScheduleService;

	private static final AdMessageKey MSG_HUProductsNotMatchingIssuingProducts = AdMessageKey.of("de.metas.handlingunits.HUProductsNotMatchingIssuingProducts");
	private static final AdMessageKey MSG_HUIsEmpty = AdMessageKey.of("de.metas.handlingunits.HUIsEmpty");

	public PPOrderSourceHUService(
			@NonNull final PPOrderSourceHURepository ppOrderSourceHURepository,
			@NonNull final PPOrderIssueScheduleService ppOrderIssueScheduleService)
	{
		this.ppOrderSourceHURepository = ppOrderSourceHURepository;
		this.ppOrderIssueScheduleService = ppOrderIssueScheduleService;
	}

	public void addSourceHU(@NonNull final PPOrderId ppOrderId, @NonNull final HuId huId)
	{
		addSourceHUs(ppOrderId, ImmutableSet.of(huId));
	}

	public void addSourceHUs(@NonNull final PPOrderId ppOrderId, @NonNull final Set<HuId> huIds)
	{
		checkEligibleToAddToManufacturingOrder(ppOrderId).assertTrue();
		checkEligibleToAddAsSourceHUs(huIds).assertTrue();

		final Map<HuId, Set<ProductId>> huProductIds = getHUProductIds(huIds);
		final Set<ProductId> productIdsToIssue = ppOrderBL.getProductIdsToIssue(ppOrderId);
		final TranslatableStringBuilder errorMessageCollector = TranslatableStrings.builder();
		huIds.forEach(huId -> {
			final Set<ProductId> productIds = huProductIds.get(huId);
			if (Check.isEmpty(productIds))
			{
				errorMessageCollector.
						append("\n [" + huId.getRepoId())
						.append(":")
						.append(TranslatableStrings.adMessage(MSG_HUIsEmpty))
						.append("]");
			}
			else if (Sets.intersection(productIdsToIssue, productIds).isEmpty())
			{
				errorMessageCollector.
						append("\n [" + huId.getRepoId())
						.append(":")
						.append(TranslatableStrings.adMessage(MSG_HUIsEmpty))
						.append("]");
			}
		});

		if (!errorMessageCollector.isEmpty())
		{
			throw new AdempiereException(errorMessageCollector.build());
		}

		ppOrderSourceHURepository.addSourceHUs(ppOrderId, huIds);
	}

	@NonNull
	private Map<HuId, Set<ProductId>> getHUProductIds(final @NonNull Set<HuId> huIds)
	{
		final List<I_M_HU> hus = handlingUnitsBL.getByIds(huIds);
		return handlingUnitsBL.getStorageFactory().getHUProductIds(hus);
	}

	@NonNull
	public BooleanWithReason checkEligibleToAddAsSourceHU(@NonNull final HuId huId)
	{
		return checkEligibleToAddAsSourceHUs(ImmutableSet.of(huId));
	}

	@NonNull
	public BooleanWithReason checkEligibleToAddAsSourceHUs(@NonNull final Set<HuId> huIds)
	{
		final String notEligibleReason = handlingUnitsBL.getByIds(huIds)
				.stream()
				.map(this::checkEligibleToAddAsSourceHU)
				.filter(BooleanWithReason::isFalse)
				.map(BooleanWithReason::getReasonAsString)
				.collect(Collectors.joining(" | "));

		return Check.isBlank(notEligibleReason)
				? BooleanWithReason.TRUE
				: BooleanWithReason.falseBecause(notEligibleReason);
	}

	@NonNull
	private BooleanWithReason checkEligibleToAddAsSourceHU(@NonNull final I_M_HU hu)
	{
		if (!X_M_HU.HUSTATUS_Active.equals(hu.getHUStatus()))
		{
			return BooleanWithReason.falseBecause("HU is not active");
		}

		if (!handlingUnitsBL.isTopLevel(hu))
		{
			return BooleanWithReason.falseBecause("HU is not top level");
		}

		return BooleanWithReason.TRUE;
	}

	public BooleanWithReason checkEligibleToAddToManufacturingOrder(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);
		final DocStatus ppOrderDocStatus = DocStatus.ofNullableCodeOrUnknown(ppOrder.getDocStatus());
		if (!ppOrderDocStatus.isCompleted())
		{
			return BooleanWithReason.falseBecause("Order is not completed");
		}

		if (ppOrderIssueScheduleService.matchesByOrderId(ppOrderId))
		{
			return BooleanWithReason.falseBecause("Manufacturing Job already started");
		}

		return BooleanWithReason.TRUE;
	}

	@NonNull
	public ImmutableSet<HuId> getSourceHUIds(@NonNull final PPOrderId ppOrderId)
	{
		return ppOrderSourceHURepository.getSourceHUIds(ppOrderId);
	}
}
