/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.bpartner.effective;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.repository.IPaymentTermRepository;
import de.metas.pricing.PricingSystemId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class BPartnerEffectiveBL
{
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IBPGroupDAO bpGroupDAO = Services.get(IBPGroupDAO.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);

	public static BPartnerEffectiveBL newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new BPartnerEffectiveBL();
	}

	public BPartnerEffective getById(@NonNull final BPartnerId bPartnerId)
	{
		return getByRecord(bpartnerDAO.getById(bPartnerId));
	}

	public BPartnerEffective getByRecord(@NonNull final I_C_BPartner bPartnerRecord)
	{
		final I_C_BP_Group bpGroup = bpGroupDAO.getById(BPGroupId.ofRepoId(bPartnerRecord.getC_BP_Group_ID()));
		final I_C_BP_Group bpParentGroup = getParentGroup(bpGroup);

		final BPartnerEffective.BPartnerEffectiveBuilder bPartnerBuilder = BPartnerEffective.builder()
				.id(BPartnerId.ofRepoId(bPartnerRecord.getC_BPartner_ID()));

		bPartnerBuilder.paymentTermId(getEffectiveId(
				bPartnerRecord, bpGroup, bpParentGroup,
				I_C_BPartner::getC_PaymentTerm_ID,
				I_C_BP_Group::getC_PaymentTerm_ID,
				PaymentTermId::ofRepoIdOrNull,
				() -> paymentTermRepository.getDefaultPaymentTermId().orElse(null))
		);

		bPartnerBuilder.poPaymentTermId(getEffectiveId(
				bPartnerRecord, bpGroup, bpParentGroup,
				I_C_BPartner::getPO_PaymentTerm_ID,
				I_C_BP_Group::getPO_PaymentTerm_ID,
				PaymentTermId::ofRepoIdOrNull,
				() -> paymentTermRepository.getDefaultPaymentTermId().orElse(null))
		);

		bPartnerBuilder.pricingSystemId(getEffectiveId(
				bPartnerRecord, bpGroup, bpParentGroup,
				I_C_BPartner::getM_PricingSystem_ID,
				I_C_BP_Group::getM_PricingSystem_ID,
				PricingSystemId::ofRepoIdOrNull,
				() -> getDefaultSalesPricingSystemId(OrgId.ofRepoId(bPartnerRecord.getAD_Org_ID())))
		);

		bPartnerBuilder.poPricingSystemId(getEffectiveId(
				bPartnerRecord, bpGroup, bpParentGroup,
				I_C_BPartner::getPO_PricingSystem_ID,
				I_C_BP_Group::getPO_PricingSystem_ID,
				PricingSystemId::ofRepoIdOrNull,
				() -> null)
		);

		bPartnerBuilder.isAutoInvoice(getEffectiveBoolean(
				bPartnerRecord, bpGroup, bpParentGroup,
				I_C_BPartner::getIsAutoInvoice,
				I_C_BP_Group::getIsAutoInvoice,
				() -> false)
		);

		return bPartnerBuilder.build();
	}

	@Nullable
	private I_C_BP_Group getParentGroup(@Nullable final I_C_BP_Group bpGroup)
	{
		if (bpGroup == null)
		{
			return null;
		}
		final BPGroupId parentGroupId = BPGroupId.ofRepoIdOrNull(bpGroup.getParent_BP_Group_ID());
		return parentGroupId != null ? bpGroupDAO.getById(parentGroupId) : null;
	}

	@Nullable
	private <T> T getEffectiveId(
			@NonNull final I_C_BPartner bPartner,
			@NonNull final I_C_BP_Group bpGroup,
			@Nullable final I_C_BP_Group bpParentGroup,
			@NonNull final Function<I_C_BPartner, Integer> bPartnerIdExtractor,
			@NonNull final Function<I_C_BP_Group, Integer> bpGroupIdExtractor,
			@NonNull final Function<Integer, T> idMapper,
			@NonNull final Supplier<T> defaultValueSupplier)
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> idMapper.apply(bPartnerIdExtractor.apply(bPartner)),
				() -> idMapper.apply(bpGroupIdExtractor.apply(bpGroup)),
				() -> bpParentGroup != null ? idMapper.apply(bpGroupIdExtractor.apply(bpParentGroup)) : null,
				defaultValueSupplier
		);
	}

	private boolean getEffectiveBoolean(
			@NonNull final I_C_BPartner bPartner,
			@Nullable final I_C_BP_Group bpGroup,
			@Nullable final I_C_BP_Group bpParentGroup,
			@NonNull final Function<I_C_BPartner, String> bPartnerBooleanExtractor,
			@NonNull final Function<I_C_BP_Group, String> bpGroupBooleanExtractor,
			@NonNull final Supplier<Boolean> defaultValueSupplier)
	{
		final Boolean effectiveValue = CoalesceUtil.coalesceSuppliers(
				() -> StringUtils.toBoolean(bPartnerBooleanExtractor.apply(bPartner), null),
				() -> StringUtils.toBoolean(bpGroupBooleanExtractor.apply(bpGroup), null),
				() -> bpParentGroup != null ?  StringUtils.toBoolean(bpGroupBooleanExtractor.apply(bpParentGroup),null) : null,
				defaultValueSupplier
		);

		return Boolean.TRUE.equals(effectiveValue);
	}

	@Nullable
	private PricingSystemId getDefaultSalesPricingSystemId(@NonNull final OrgId orgId)
	{
		if (orgId.isRegular())
		{
			final OrgInfo orgInfo = orgDAO.getOrgInfoById(orgId);
			return orgInfo.getPricingSystemId();
		}
		return null;
	}
}
