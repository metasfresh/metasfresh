package de.metas.order.compensationGroup.calibration;

import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateRegularLine;
import de.metas.order.compensationGroup.GroupTemplateRegularLineId;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CompensationGroupCalibrationService
{
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final CompensationGroupCalibrationRuleRepository ruleRepository;

	public CompensationGroupCalibrationService(@NonNull final CompensationGroupCalibrationRuleRepository ruleRepository)
	{
		this.ruleRepository = ruleRepository;
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
				continue; // the menu's own line is never calibrated
			}

			final CalibrationMatchKey key = CalibrationMatchKey.builder()
					.orgId(orgId)
					.bpartnerId(bpartnerId)
					.bpGroupId(bpGroupId)
					.productId(line.getProductId())
					.productCategoryId(productsRepo.retrieveProductCategoryByProductId(line.getProductId()))
					.groupTemplateId(template.getId())
					.build();

			final Optional<CalibrationRule> rule = rules.findFirstMatching(key);
			if (rule.isPresent() && rule.get().getFactor().signum() == 0)
			{
				result.put(line.getId(), LineCalibration.SKIP);
				continue;
			}

			final BigDecimal factor = rule.map(CalibrationRule::getFactor).orElse(BigDecimal.ONE);
			final CalibratedQty qty = CalibratedQtyCalculator.compute(
					line.getQty().toBigDecimal(),
					qtyMultiplier,
					factor,
					uomDAO.getStandardPrecision(line.getQty().getUomId()).toInt());

			result.put(line.getId(), LineCalibration.builder()
					.calibratedQty(qty.getCalibrated())
					.uncalibratedQty(qty.getUncalibrated())
					.factor(factor)
					.ruleId(rule.map(CalibrationRule::getId).orElse(null))
					.build());
		}
		return GroupCalibrations.of(result.build());
	}
}
