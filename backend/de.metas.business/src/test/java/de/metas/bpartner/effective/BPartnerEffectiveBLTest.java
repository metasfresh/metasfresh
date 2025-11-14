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

import de.metas.bpartner.BPartnerId;
import de.metas.lang.SOTrx;
import de.metas.order.InvoiceRule;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.util.StringUtils;
import lombok.Builder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class BPartnerEffectiveBLTest
{
	private BPartnerEffectiveBL bpartnerEffectiveBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
        bpartnerEffectiveBL = BPartnerEffectiveBL.newInstanceForUnitTesting();
	}

	@Test
	public void getEffectiveValue_defaultIsUsedTest()
	{
		final BPartnerId bPartnerId = setup().build();
		final BPartnerEffective bPartnerEffective = bpartnerEffectiveBL.getById(bPartnerId);
		assertThat(bPartnerEffective.getPricingSystemId(SOTrx.SALES)).isNull();
		assertThat(bPartnerEffective.getPricingSystemId(SOTrx.PURCHASE)).isNull();
		assertThat(bPartnerEffective.getPaymentTermId(SOTrx.SALES)).isNull();
		assertThat(bPartnerEffective.getPaymentTermId(SOTrx.PURCHASE)).isNull();
		assertThat(bPartnerEffective.getPaymentRule(SOTrx.SALES).isOnCredit()).isTrue();
		assertThat(bPartnerEffective.getPaymentRule(SOTrx.PURCHASE).isOnCredit()).isTrue();
		assertThat(bPartnerEffective.getInvoiceRule(SOTrx.SALES).isAfterDelivery()).isTrue();
		assertThat(bPartnerEffective.getInvoiceRule(SOTrx.PURCHASE).isAfterDelivery()).isTrue();
		assertThat(bPartnerEffective.isAutoInvoice(SOTrx.SALES)).isFalse();
		assertThat(bPartnerEffective.isAutoInvoice(SOTrx.PURCHASE)).isFalse();
	}

	@Test
	public void getEffectiveValue_parentGroupIsUsedTest()
	{
		final BPartnerId bPartnerId = setup()
				.parentBPGroup_PricingSystemId(PricingSystemId.ofRepoId(1))
				.parentBPGroup_poPricingSystemId(PricingSystemId.ofRepoId(2))
				.parentBPGroup_PaymentTermId(PaymentTermId.ofRepoId(3))
				.parentBPGroup_poPaymentTermId(PaymentTermId.ofRepoId(4))
				.parentBPGroup_PaymentRule(PaymentRule.PayPal)
				.parentBPGroup_poPaymentRule(PaymentRule.PayPal)
				.parentBPGroup_InvoiceRule(InvoiceRule.OrderCompletelyDelivered)
				.parentBPGroup_isAutoInvoice(true)
				.build();
		final BPartnerEffective bPartnerEffective = bpartnerEffectiveBL.getById(bPartnerId);
		assertThat(PricingSystemId.equals(bPartnerEffective.getPricingSystemId(SOTrx.SALES), PricingSystemId.ofRepoId(1))).isTrue();
		assertThat(PricingSystemId.equals(bPartnerEffective.getPricingSystemId(SOTrx.PURCHASE), PricingSystemId.ofRepoId(2))).isTrue();
		assertThat(PaymentTermId.equals(bPartnerEffective.getPaymentTermId(SOTrx.SALES), PaymentTermId.ofRepoId(3))).isTrue();
		assertThat(PaymentTermId.equals(bPartnerEffective.getPaymentTermId(SOTrx.PURCHASE), PaymentTermId.ofRepoId(4))).isTrue();
		assertThat(bPartnerEffective.getPaymentRule(SOTrx.SALES).isPayPal()).isTrue();
		assertThat(bPartnerEffective.getPaymentRule(SOTrx.PURCHASE).isPayPal()).isTrue();
		assertThat(bPartnerEffective.getInvoiceRule(SOTrx.SALES).isOrderCompletelyDelivered()).isTrue();
		assertThat(bPartnerEffective.getInvoiceRule(SOTrx.PURCHASE).isAfterDelivery()).isTrue();
		assertThat(bPartnerEffective.isAutoInvoice(SOTrx.SALES)).isTrue();
		assertThat(bPartnerEffective.isAutoInvoice(SOTrx.PURCHASE)).isFalse();
	}

	@Test
	public void getEffectiveValue_childGroupIsUsedTest()
	{
		final BPartnerId bPartnerId = setup()
				.parentBPGroup_PricingSystemId(PricingSystemId.ofRepoId(1))
				.parentBPGroup_poPricingSystemId(PricingSystemId.ofRepoId(2))
				.parentBPGroup_PaymentTermId(PaymentTermId.ofRepoId(3))
				.parentBPGroup_poPaymentTermId(PaymentTermId.ofRepoId(4))
				.parentBPGroup_PaymentRule(PaymentRule.CreditCard)
				.parentBPGroup_poPaymentRule(PaymentRule.CreditCard)
				.parentBPGroup_InvoiceRule(InvoiceRule.AfterDelivery)
				.parentBPGroup_isAutoInvoice(false)
				.bpGroup_PricingSystemId(PricingSystemId.ofRepoId(5))
				.bpGroup_poPricingSystemId(PricingSystemId.ofRepoId(6))
				.bpGroup_PaymentTermId(PaymentTermId.ofRepoId(7))
				.bpGroup_poPaymentTermId(PaymentTermId.ofRepoId(8))
				.bpGroup_PaymentRule(PaymentRule.PayPal)
				.bpGroup_poPaymentRule(PaymentRule.PayPal)
				.bpGroup_InvoiceRule(InvoiceRule.OrderCompletelyDelivered)
				.bpGroup_isAutoInvoice(true)
				.build();
		final BPartnerEffective bPartnerEffective = bpartnerEffectiveBL.getById(bPartnerId);
		assertThat(PricingSystemId.equals(bPartnerEffective.getPricingSystemId(SOTrx.SALES), PricingSystemId.ofRepoId(5))).isTrue();
		assertThat(PricingSystemId.equals(bPartnerEffective.getPricingSystemId(SOTrx.PURCHASE), PricingSystemId.ofRepoId(6))).isTrue();
		assertThat(PaymentTermId.equals(bPartnerEffective.getPaymentTermId(SOTrx.SALES), PaymentTermId.ofRepoId(7))).isTrue();
		assertThat(PaymentTermId.equals(bPartnerEffective.getPaymentTermId(SOTrx.PURCHASE), PaymentTermId.ofRepoId(8))).isTrue();
		assertThat(bPartnerEffective.getPaymentRule(SOTrx.SALES).isPayPal()).isTrue();
		assertThat(bPartnerEffective.getPaymentRule(SOTrx.PURCHASE).isPayPal()).isTrue();
		assertThat(bPartnerEffective.getInvoiceRule(SOTrx.SALES).isOrderCompletelyDelivered()).isTrue();
		assertThat(bPartnerEffective.getInvoiceRule(SOTrx.PURCHASE).isAfterDelivery()).isTrue();
		assertThat(bPartnerEffective.isAutoInvoice(SOTrx.SALES)).isTrue();
		assertThat(bPartnerEffective.isAutoInvoice(SOTrx.PURCHASE)).isFalse();
	}

	@Test
	public void getEffectiveValue_partnerIsUsedTest()
	{
		final BPartnerId bPartnerId = setup()
				.parentBPGroup_PricingSystemId(PricingSystemId.ofRepoId(1))
				.parentBPGroup_poPricingSystemId(PricingSystemId.ofRepoId(2))
				.parentBPGroup_PaymentTermId(PaymentTermId.ofRepoId(3))
				.parentBPGroup_poPaymentTermId(PaymentTermId.ofRepoId(4))
				.parentBPGroup_PaymentRule(PaymentRule.CreditCard)
				.parentBPGroup_poPaymentRule(PaymentRule.CreditCard)
				.parentBPGroup_InvoiceRule(InvoiceRule.AfterDelivery)
				.parentBPGroup_isAutoInvoice(false)
				.bpGroup_PricingSystemId(PricingSystemId.ofRepoId(5))
				.bpGroup_poPricingSystemId(PricingSystemId.ofRepoId(6))
				.bpGroup_PaymentTermId(PaymentTermId.ofRepoId(7))
				.bpGroup_poPaymentTermId(PaymentTermId.ofRepoId(8))
				.bpGroup_PaymentRule(PaymentRule.PayPal)
				.bpGroup_poPaymentRule(PaymentRule.PayPal)
				.bpGroup_InvoiceRule(InvoiceRule.OrderCompletelyDelivered)
				.bpGroup_isAutoInvoice(true)
				.bpartner_PricingSystemId(PricingSystemId.ofRepoId(9))
				.bpartner_poPricingSystemId(PricingSystemId.ofRepoId(10))
				.bpartner_PaymentTermId(PaymentTermId.ofRepoId(11))
				.bpartner_poPaymentTermId(PaymentTermId.ofRepoId(12))
				.bpartner_PaymentRule(PaymentRule.Settlement)
				.bpartner_poPaymentRule(PaymentRule.Settlement)
				.bpartner_InvoiceRule(InvoiceRule.AfterPick)
				.bpartner_poInvoiceRule(InvoiceRule.AfterPick)
				.bpartner_isAutoInvoice(false)
				.build();
		final BPartnerEffective bPartnerEffective = bpartnerEffectiveBL.getById(bPartnerId);
		assertThat(PricingSystemId.equals(bPartnerEffective.getPricingSystemId(SOTrx.SALES), PricingSystemId.ofRepoId(9))).isTrue();
		assertThat(PricingSystemId.equals(bPartnerEffective.getPricingSystemId(SOTrx.PURCHASE), PricingSystemId.ofRepoId(10))).isTrue();
		assertThat(PaymentTermId.equals(bPartnerEffective.getPaymentTermId(SOTrx.SALES), PaymentTermId.ofRepoId(11))).isTrue();
		assertThat(PaymentTermId.equals(bPartnerEffective.getPaymentTermId(SOTrx.PURCHASE), PaymentTermId.ofRepoId(12))).isTrue();
		assertThat(bPartnerEffective.getPaymentRule(SOTrx.SALES).isSettlement()).isTrue();
		assertThat(bPartnerEffective.getPaymentRule(SOTrx.PURCHASE).isSettlement()).isTrue();
		assertThat(bPartnerEffective.getInvoiceRule(SOTrx.SALES).isAfterPick()).isTrue();
		assertThat(bPartnerEffective.getInvoiceRule(SOTrx.PURCHASE).isAfterPick()).isTrue();
		assertThat(bPartnerEffective.isAutoInvoice(SOTrx.SALES)).isFalse();
		assertThat(bPartnerEffective.isAutoInvoice(SOTrx.PURCHASE)).isFalse();
	}

	@Builder(builderMethodName = "setup", builderClassName = "$SetupBuilder")
	private BPartnerId setupTest(
			@Nullable final PricingSystemId bpartner_PricingSystemId,
			@Nullable final PricingSystemId bpartner_poPricingSystemId,
			@Nullable final PaymentTermId bpartner_PaymentTermId,
			@Nullable final PaymentTermId bpartner_poPaymentTermId,
			@Nullable final PaymentRule bpartner_PaymentRule,
			@Nullable final PaymentRule bpartner_poPaymentRule,
			@Nullable final InvoiceRule bpartner_InvoiceRule,
			@Nullable final InvoiceRule bpartner_poInvoiceRule,
			@Nullable final Boolean bpartner_isAutoInvoice,
			@Nullable final PricingSystemId bpGroup_PricingSystemId,
			@Nullable final PricingSystemId bpGroup_poPricingSystemId,
			@Nullable final PaymentTermId bpGroup_PaymentTermId,
			@Nullable final PaymentTermId bpGroup_poPaymentTermId,
			@Nullable final PaymentRule bpGroup_PaymentRule,
			@Nullable final PaymentRule bpGroup_poPaymentRule,
			@Nullable final InvoiceRule bpGroup_InvoiceRule,
			@Nullable final Boolean bpGroup_isAutoInvoice,
			@Nullable final PricingSystemId parentBPGroup_PricingSystemId,
			@Nullable final PricingSystemId parentBPGroup_poPricingSystemId,
			@Nullable final PaymentTermId parentBPGroup_PaymentTermId,
			@Nullable final PaymentTermId parentBPGroup_poPaymentTermId,
			@Nullable final PaymentRule parentBPGroup_PaymentRule,
			@Nullable final PaymentRule parentBPGroup_poPaymentRule,
			@Nullable final InvoiceRule parentBPGroup_InvoiceRule,
			@Nullable final Boolean parentBPGroup_isAutoInvoice
	)
	{
		final I_C_BP_Group bpGroupParent = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		bpGroupParent.setM_PricingSystem_ID(PricingSystemId.toRepoId(parentBPGroup_PricingSystemId));
		bpGroupParent.setPO_PricingSystem_ID(PricingSystemId.toRepoId(parentBPGroup_poPricingSystemId));
		bpGroupParent.setC_PaymentTerm_ID(PaymentTermId.toRepoId(parentBPGroup_PaymentTermId));
		bpGroupParent.setPO_PaymentTerm_ID(PaymentTermId.toRepoId(parentBPGroup_poPaymentTermId));
		bpGroupParent.setPaymentRule(PaymentRule.toCodeOrNull(parentBPGroup_PaymentRule));
		bpGroupParent.setPaymentRulePO(PaymentRule.toCodeOrNull(parentBPGroup_poPaymentRule));
		bpGroupParent.setInvoiceRule(InvoiceRule.toCodeOrNull(parentBPGroup_InvoiceRule));
		bpGroupParent.setIsAutoInvoice(StringUtils.ofBoolean(parentBPGroup_isAutoInvoice));
		saveRecord(bpGroupParent);

		final I_C_BP_Group bpGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		bpGroup.setC_BP_Group_ID(3);
		bpGroup.setParent_BP_Group_ID(bpGroupParent.getC_BP_Group_ID());
		bpGroup.setM_PricingSystem_ID(PricingSystemId.toRepoId(bpGroup_PricingSystemId));
		bpGroup.setPO_PricingSystem_ID(PricingSystemId.toRepoId(bpGroup_poPricingSystemId));
		bpGroup.setC_PaymentTerm_ID(PaymentTermId.toRepoId(bpGroup_PaymentTermId));
		bpGroup.setPO_PaymentTerm_ID(PaymentTermId.toRepoId(bpGroup_poPaymentTermId));
		bpGroup.setPaymentRule(PaymentRule.toCodeOrNull(bpGroup_PaymentRule));
		bpGroup.setPaymentRulePO(PaymentRule.toCodeOrNull(bpGroup_poPaymentRule));
		bpGroup.setInvoiceRule(InvoiceRule.toCodeOrNull(bpGroup_InvoiceRule));
		bpGroup.setIsAutoInvoice(StringUtils.ofBoolean(bpGroup_isAutoInvoice));
		saveRecord(bpGroup);

		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		partner.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
		partner.setM_PricingSystem_ID(PricingSystemId.toRepoId(bpartner_PricingSystemId));
		partner.setPO_PricingSystem_ID(PricingSystemId.toRepoId(bpartner_poPricingSystemId));
		partner.setC_PaymentTerm_ID(PaymentTermId.toRepoId(bpartner_PaymentTermId));
		partner.setPO_PaymentTerm_ID(PaymentTermId.toRepoId(bpartner_poPaymentTermId));
		partner.setPaymentRule(PaymentRule.toCodeOrNull(bpartner_PaymentRule));
		partner.setPaymentRulePO(PaymentRule.toCodeOrNull(bpartner_poPaymentRule));
		partner.setInvoiceRule(InvoiceRule.toCodeOrNull(bpartner_InvoiceRule));
		partner.setPO_InvoiceRule(InvoiceRule.toCodeOrNull(bpartner_poInvoiceRule));
		partner.setIsAutoInvoice(StringUtils.ofBoolean(bpartner_isAutoInvoice));
		saveRecord(partner);

		return BPartnerId.ofRepoId(partner.getC_BPartner_ID());
	}

}
