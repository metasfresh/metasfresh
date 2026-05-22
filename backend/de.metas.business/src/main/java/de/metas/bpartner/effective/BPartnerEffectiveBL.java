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
import de.metas.incoterms.Incoterms;
import de.metas.incoterms.IncotermsId;
import de.metas.incoterms.IncotermsRepository;
import de.metas.order.InvoiceRule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.repository.IPaymentTermRepository;
import de.metas.pricing.PricingSystemId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.service.ISysConfigBL;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.X_C_Order;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.compiere.model.CalloutOrder.SYSCONFIG_DEFAULT_INVOICE_RULE;

@Service
@RequiredArgsConstructor
public class BPartnerEffectiveBL
{
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IBPGroupDAO bpGroupDAO = Services.get(IBPGroupDAO.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@NonNull private final IncotermsRepository incotermsRepository;

	public static BPartnerEffectiveBL newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new BPartnerEffectiveBL(IncotermsRepository.newInstanceForUnitTesting());
	}

	public BPartnerEffective getById(@NonNull final BPartnerId bPartnerId)
	{
		return getByRecord(bpartnerDAO.getById(bPartnerId));
	}

	public int getPurchaseTransportDays(@NonNull final BPartnerId bPartnerId)
	{
		return getById(bPartnerId).getPurchaseTransportDays();
	}

	public BPartnerEffective getByRecord(@NonNull final I_C_BPartner bPartnerRecord)
	{
		final I_C_BP_Group bpGroup = bpGroupDAO.getById(BPGroupId.ofRepoId(bPartnerRecord.getC_BP_Group_ID()));
		final I_C_BP_Group bpParentGroup = getParentGroup(bpGroup);

		final BPartnerEffective.BPartnerEffectiveBuilder bPartnerBuilder = BPartnerEffective.builder()
				.id(BPartnerId.ofRepoId(bPartnerRecord.getC_BPartner_ID()));

		bPartnerBuilder.paymentTermId(getEffectiveValue(
				bPartnerRecord, bpGroup, bpParentGroup,
				I_C_BPartner::getC_PaymentTerm_ID,
				I_C_BP_Group::getC_PaymentTerm_ID,
				PaymentTermId::ofRepoIdOrNull,
				() -> paymentTermRepository.getDefaultPaymentTermId().orElse(null))
		);

		bPartnerBuilder.poPaymentTermId(getEffectiveValue(
				bPartnerRecord, bpGroup, bpParentGroup,
				I_C_BPartner::getPO_PaymentTerm_ID,
				I_C_BP_Group::getPO_PaymentTerm_ID,
				PaymentTermId::ofRepoIdOrNull,
				() -> paymentTermRepository.getDefaultPaymentTermId().orElse(null))
		);

		bPartnerBuilder.pricingSystemId(getEffectiveValue(
				bPartnerRecord, bpGroup, bpParentGroup,
				I_C_BPartner::getM_PricingSystem_ID,
				I_C_BP_Group::getM_PricingSystem_ID,
				PricingSystemId::ofRepoIdOrNull,
				() -> getDefaultSalesPricingSystemId(OrgId.ofRepoId(bPartnerRecord.getAD_Org_ID())))
		);

		bPartnerBuilder.poPricingSystemId(getEffectiveValue(
				bPartnerRecord, bpGroup, bpParentGroup,
				I_C_BPartner::getPO_PricingSystem_ID,
				I_C_BP_Group::getPO_PricingSystem_ID,
				PricingSystemId::ofRepoIdOrNull,
				() -> null)
		);

		bPartnerBuilder.isAutoInvoice(Boolean.TRUE.equals(getEffectiveValue(
				bPartnerRecord, bpGroup, bpParentGroup,
				I_C_BPartner::getIsAutoInvoice,
				I_C_BP_Group::getIsAutoInvoice,
				(v) -> StringUtils.toBoolean(v, null),
				() -> false))
		);

		//noinspection DataFlowIssue
		bPartnerBuilder.invoiceRule(getEffectiveValue(
				bPartnerRecord, bpGroup, bpParentGroup,
				I_C_BPartner::getInvoiceRule,
				I_C_BP_Group::getInvoiceRule,
				InvoiceRule::ofNullableCode,
				this::getDefaultInvoiceRule)
		);

		//noinspection DataFlowIssue
		bPartnerBuilder.poInvoiceRule(getEffectiveValue(
				bPartnerRecord,
				I_C_BPartner::getPO_InvoiceRule,
				InvoiceRule::ofNullableCode,
				this::getDefaultInvoiceRule)
		);

		//noinspection DataFlowIssue
		bPartnerBuilder.paymentRule(getEffectiveValue(
				bPartnerRecord, bpGroup, bpParentGroup,
				I_C_BPartner::getPaymentRule,
				I_C_BP_Group::getPaymentRule,
				PaymentRule::ofNullableCode,
				() -> PaymentRule.OnCredit) // same as bPartnerRecord mandatory column default
		);

		//noinspection DataFlowIssue
		bPartnerBuilder.poPaymentRule(getEffectiveValue(
				bPartnerRecord, bpGroup, bpParentGroup,
				I_C_BPartner::getPaymentRulePO,
				I_C_BP_Group::getPaymentRulePO,
				PaymentRule::ofNullableCode,
				() -> PaymentRule.OnCredit) // same as bPartnerRecord mandatory column default
		);

		final OrgId orgId = OrgId.ofRepoId(bPartnerRecord.getAD_Org_ID());
		setIncoterms(bPartnerRecord, bpGroup, bpParentGroup, orgId,
				I_C_BPartner::getC_Incoterms_Customer_ID,
				I_C_BP_Group::getC_Incoterms_ID,
				I_C_BPartner::getIncotermLocation,
				I_C_BP_Group::getIncotermLocation,
				bPartnerBuilder::incoterms);

		setIncoterms(bPartnerRecord, bpGroup, bpParentGroup, orgId,
				I_C_BPartner::getC_Incoterms_Vendor_ID,
				I_C_BP_Group::getPO_Incoterms_ID,
				I_C_BPartner::getPO_IncotermLocation,
				I_C_BP_Group::getPO_IncotermLocation,
				bPartnerBuilder::poIncoterms);

		bPartnerBuilder.purchaseTransportDays(
				bpartnerDAO.getPurchaseTransportDays(bPartnerRecord).orElse(0));

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
	private <T, V> T getEffectiveValue(
			@NonNull final I_C_BPartner bPartner,
			@NonNull final I_C_BP_Group bpGroup,
			@Nullable final I_C_BP_Group bpParentGroup,
			@NonNull final Function<I_C_BPartner, V> bPartnerValueExtractor,
			@NonNull final Function<I_C_BP_Group, V> bpGroupValueExtractor,
			@NonNull final Function<V, T> valueMapper,
			@NonNull final Supplier<T> defaultValueSupplier)
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> {
					final V value = bPartnerValueExtractor.apply(bPartner);
					return value != null ? valueMapper.apply(value) : null;
				},
				() -> {
					final V value = bpGroupValueExtractor.apply(bpGroup);
					return value != null ? valueMapper.apply(value) : null;
				},
				() -> {
					if (bpParentGroup == null)
					{
						return null;
					}
					final V value = bpGroupValueExtractor.apply(bpParentGroup);
					return value != null ? valueMapper.apply(value) : null;
				},
				defaultValueSupplier
		);
	}

	@Nullable
	private <T, V> T getEffectiveValue(
			@NonNull final I_C_BPartner bPartner,
			@NonNull final Function<I_C_BPartner, V> bPartnerValueExtractor,
			@NonNull final Function<V, T> valueMapper,
			@NonNull final Supplier<T> defaultValueSupplier)
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> {
					final V value = bPartnerValueExtractor.apply(bPartner);
					return value != null ? valueMapper.apply(value) : null;
				},
				defaultValueSupplier
		);
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

	@NonNull
	private InvoiceRule getDefaultInvoiceRule()
	{
		final String invoiceRule = sysConfigBL.getValue(SYSCONFIG_DEFAULT_INVOICE_RULE, X_C_Order.INVOICERULE_AfterDelivery);
		return StringUtils.trimBlankToOptional(invoiceRule)
				.map(InvoiceRule::ofCode)
				.orElse(InvoiceRule.AfterDelivery);
	}

	private void setIncoterms(
			@NonNull final I_C_BPartner bPartnerRecord,
			@NonNull final I_C_BP_Group bpGroup,
			@Nullable final I_C_BP_Group bpParentGroup,
			@NonNull final OrgId orgId,
			final Function<I_C_BPartner, Integer> bpartnerIncotermsIdGetter,
			final Function<I_C_BP_Group, Integer> bpGroupIncotermsIdGetter,
			final Function<I_C_BPartner, String> bpartnerLocationGetter,
			final Function<I_C_BP_Group, String> bpGroupLocationGetter,
			final Consumer<Incoterms> incotermsConsumer)
	{
		final Incoterms incoterms = getEffectiveValue(
				bPartnerRecord, bpGroup, bpParentGroup,
				bpartnerIncotermsIdGetter,
				bpGroupIncotermsIdGetter,
				this::getIncoterms,
				() -> incotermsRepository.getDefaultIncoterms(orgId));

		if (incoterms == null)
		{
			return;
		}

		final String location = getEffectiveValue(
				bPartnerRecord, bpGroup, bpParentGroup,
				bpartnerLocationGetter,
				bpGroupLocationGetter,
				StringUtils::trimBlankToNull,
				() -> getDefaultIncotermsLocation(orgId));

		incotermsConsumer.accept(incoterms.withLocationEffective(location));
	}


	@Nullable
	private Incoterms getIncoterms(final int incotermsId)
	{
		final IncotermsId id = IncotermsId.ofRepoIdOrNull(incotermsId);
		if (id == null)
		{
			return null;
		}

		return incotermsRepository.getById(id);
	}

	@Nullable
	private String getDefaultIncotermsLocation(@NonNull final OrgId orgId)
	{
		final Incoterms incoterms = incotermsRepository.getDefaultIncoterms(orgId);
		if (incoterms == null)
		{
			return null;
		}

		return StringUtils.trimBlankToNull(incoterms.getDefaultLocation());
	}
}
