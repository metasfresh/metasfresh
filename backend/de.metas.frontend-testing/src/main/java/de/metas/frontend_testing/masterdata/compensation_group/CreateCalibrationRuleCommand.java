package de.metas.frontend_testing.masterdata.compensation_group;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.compensationGroup.calibration.CalibrationRuleId;
import de.metas.order.model.I_C_CompensationGroup_CalibrationRule;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;

/**
 * Creates one {@link I_C_CompensationGroup_CalibrationRule}, resolving the selector identifiers through the context.
 * Persistence goes through {@link CalibrationRuleMasterdataRepository} (persistence primitives belong in a repository);
 * the table's own model interceptor validates BP-or-group and the non-negative factor.
 */
@Builder
public class CreateCalibrationRuleCommand
{
	@NonNull private final CalibrationRuleMasterdataRepository repository = new CalibrationRuleMasterdataRepository();

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonCalibrationRuleRequest request;
	@NonNull private final Identifier identifier;

	public JsonCalibrationRuleResponse execute()
	{
		final I_C_CompensationGroup_CalibrationRule rule = InterfaceWrapperHelper.newInstance(I_C_CompensationGroup_CalibrationRule.class);
		rule.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		rule.setIsActive(true);
		rule.setSeqNo(request.getSeqNo() != null ? request.getSeqNo() : repository.nextSeqNo());
		// Only the named selectors are set; the others stay unset (null), i.e. "any".
		final BPartnerId bpartnerId = resolve(request.getBpartner(), BPartnerId.class);
		if (bpartnerId != null)
		{
			rule.setC_BPartner_ID(bpartnerId.getRepoId());
		}
		final BPGroupId bpGroupId = resolve(request.getBpGroup(), BPGroupId.class);
		if (bpGroupId != null)
		{
			rule.setC_BP_Group_ID(bpGroupId.getRepoId());
		}
		final ProductId productId = resolve(request.getProduct(), ProductId.class);
		if (productId != null)
		{
			rule.setM_Product_ID(productId.getRepoId());
		}
		final ProductCategoryId productCategoryId = resolve(request.getProductCategory(), ProductCategoryId.class);
		if (productCategoryId != null)
		{
			rule.setM_Product_Category_ID(productCategoryId.getRepoId());
		}
		final GroupTemplateId schemaId = resolve(request.getSchema(), GroupTemplateId.class);
		if (schemaId != null)
		{
			rule.setC_CompensationGroup_Schema_ID(schemaId.getRepoId());
		}
		rule.setGroupCompensationCalibrationFactor(request.getFactor());
		repository.save(rule);

		final CalibrationRuleId ruleId = CalibrationRuleId.ofRepoId(rule.getC_CompensationGroup_CalibrationRule_ID());
		context.putIdentifier(identifier, ruleId);
		return JsonCalibrationRuleResponse.builder().id(ruleId).build();
	}

	@Nullable
	private <T extends RepoIdAware> T resolve(@Nullable final Identifier selector, @NonNull final Class<T> idClass)
	{
		return selector == null ? null : context.getId(selector, idClass);
	}
}
