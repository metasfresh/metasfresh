package de.metas.order.compensationGroup.calibration;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateRegularLine;
import de.metas.order.compensationGroup.GroupTemplateRegularLineId;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.quantity.Quantity;
import de.metas.uom.UOMPrecision;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CompensationGroupCalibrationService
{
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	@NonNull private final CompensationGroupCalibrationRuleRepository ruleRepository;

	@VisibleForTesting
	public static CompensationGroupCalibrationService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				CompensationGroupCalibrationService.class,
				() -> new CompensationGroupCalibrationService(CompensationGroupCalibrationRuleRepository.newInstanceForUnitTesting()));
	}

	public GroupCalibrations computeCalibrations(
			@NonNull final I_C_Order order,
			@NonNull final GroupTemplate template,
			@NonNull final BigDecimal qtyMultiplier)
	{
		if (!order.isSOTrx())
		{
			return GroupCalibrations.NONE;
		}

		final CalibrationRules rules = ruleRepository.getActiveRules();
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final BPGroupId bpGroupId = bpartnersRepo.getBPGroupIdByBPartnerId(bpartnerId);
		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());

		final ImmutableMap.Builder<GroupTemplateRegularLineId, LineCalibration> result = ImmutableMap.builder();
		for (final GroupTemplateRegularLine line : template.getRegularLinesToAdd())
		{
			if (productsRepo.getGroupTemplateIdByProductId(line.getProductId()).isPresent())
			{
				continue; // a line whose product has its own compensation group schema is never calibrated
			}

			final CalibrationMatchKey key = CalibrationMatchKey.builder()
					.orgId(orgId)
					.bpartnerId(bpartnerId)
					.bpGroupId(bpGroupId)
					.productId(line.getProductId())
					.productCategoryId(productsRepo.retrieveProductCategoryByProductId(line.getProductId()))
					.groupTemplateId(template.getId())
					.build();

			final CalibrationRule rule = rules.findFirstMatching(key).orElse(null);
			result.put(line.getId(), calibrate(
					line.getQty().multiply(qtyMultiplier),
					rule != null ? rule.getFactor() : Percent.ONE_HUNDRED,
					rule != null ? rule.getId() : null));
		}
		return GroupCalibrations.of(result.build());
	}

	/** Scales the base quantity by the factor, rounding HALF_UP to the UOM precision. */
	@VisibleForTesting
	static LineCalibration calibrate(
			@NonNull final Quantity baseQty,
			@NonNull final Percent factor,
			@Nullable final CalibrationRuleId ruleId)
	{
		final UOMPrecision precision = baseQty.getUOMPrecision();
		return LineCalibration.builder()
				.calibratedQty(baseQty.multiply(factor.toBigDecimal().movePointLeft(2)).setScale(precision, RoundingMode.HALF_UP))
				.uncalibratedQty(baseQty.setScale(precision, RoundingMode.HALF_UP))
				.factor(factor)
				.ruleId(ruleId)
				.build();
	}
}
