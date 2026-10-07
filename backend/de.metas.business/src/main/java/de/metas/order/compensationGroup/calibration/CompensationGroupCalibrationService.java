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
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

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
		return SpringContextHolder.getBeanOrSupply(
				CompensationGroupCalibrationService.class,
				() -> new CompensationGroupCalibrationService(CompensationGroupCalibrationRuleRepository.newInstanceForUnitTesting()));
	}

	public GroupCalibrations computeCalibrations(@NonNull final I_C_Order order, @NonNull final GroupTemplate template)
	{
		if (!order.isSOTrx())
		{
			return GroupCalibrations.NONE;
		}

		final CalibrationRules rules = ruleRepository.getActiveRules();
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final BPGroupId bpGroupId = bpartnersRepo.getBPGroupIdByBPartnerId(bpartnerId);
		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());

		final ImmutableMap.Builder<GroupTemplateRegularLineId, CalibrationRule> result = ImmutableMap.builder();
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

			rules.findFirstMatching(key)
					.ifPresent(rule -> result.put(line.getId(), rule));
		}
		return GroupCalibrations.of(result.build());
	}
}
