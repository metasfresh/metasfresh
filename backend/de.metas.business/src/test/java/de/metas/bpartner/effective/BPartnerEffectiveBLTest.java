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
import de.metas.incoterms.Incoterms;
import de.metas.incoterms.IncotermsId;
import de.metas.lang.SOTrx;
import de.metas.order.InvoiceRule;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.util.StringUtils;
import lombok.Builder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Incoterms;
import org.compiere.model.I_C_PaymentTerm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
		assertThat(bPartnerEffective.getIncoterms(SOTrx.SALES)).isNull();
		assertThat(bPartnerEffective.getIncoterms(SOTrx.PURCHASE)).isNull();

		final I_C_PaymentTerm paymentTerm = InterfaceWrapperHelper.newInstance(I_C_PaymentTerm.class);
		paymentTerm.setName("TestPaymentTerm");
		paymentTerm.setValue("TestPaymentTermValue");
		paymentTerm.setIsDefault(true);
		saveRecord(paymentTerm);
		final PaymentTermId paymentTermId = PaymentTermId.ofRepoId(paymentTerm.getC_PaymentTerm_ID());

		final I_C_Incoterms incotermsRecord = InterfaceWrapperHelper.newInstance(I_C_Incoterms.class);
		incotermsRecord.setName("TestIncoterms");
		incotermsRecord.setValue("TestIncotermsValue");
		incotermsRecord.setDefaultLocation("TestIncotermsDefaultLocation");
		incotermsRecord.setIsDefault(true);
		saveRecord(incotermsRecord);
		final IncotermsId incotermsId = IncotermsId.ofRepoId(incotermsRecord.getC_Incoterms_ID());

		final BPartnerEffective bPartnerEffectiveAfterDefaultSetup = bpartnerEffectiveBL.getById(bPartnerId);
		assertThat(PaymentTermId.equals(bPartnerEffectiveAfterDefaultSetup.getPaymentTermId(SOTrx.SALES), paymentTermId)).isTrue();
		assertThat(PaymentTermId.equals(bPartnerEffectiveAfterDefaultSetup.getPaymentTermId(SOTrx.PURCHASE), paymentTermId)).isTrue();

		final Incoterms incoterms = bPartnerEffectiveAfterDefaultSetup.getIncoterms(SOTrx.SALES);
		assertNotNull(incoterms);
		assertThat(IncotermsId.equals(incoterms.getId(), incotermsId)).isTrue();
		assertEquals("TestIncoterms", incoterms.getName());
		assertEquals("TestIncotermsValue", incoterms.getValue());
		assertEquals("TestIncotermsDefaultLocation", incoterms.getLocationEffective());

		final Incoterms poIncoterms = bPartnerEffectiveAfterDefaultSetup.getIncoterms(SOTrx.PURCHASE);
		assertNotNull(poIncoterms);
		assertThat(IncotermsId.equals(poIncoterms.getId(), incotermsId)).isTrue();
		assertEquals("TestIncoterms", poIncoterms.getName());
		assertEquals("TestIncotermsValue", poIncoterms.getValue());
		assertEquals("TestIncotermsDefaultLocation", poIncoterms.getLocationEffective());
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
				.parentBPGroup_incoterms(Incoterms.builder()
						.id(IncotermsId.ofRepoId(5))
						.name("TestIncoterms")
						.value("TestIncotermsValue")
						.locationEffective("TestIncotermsLocation")
						.orgId(OrgId.ANY)
						.build())
				.parentBPGroup_poIncoterms(Incoterms.builder()
						.id(IncotermsId.ofRepoId(6))
						.name("TestPoIncoterms")
						.value("TestPoIncotermsValue")
						.locationEffective("TestPoIncotermsLocation")
						.orgId(OrgId.ANY)
						.build())
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

		final Incoterms incoterms = bPartnerEffective.getIncoterms(SOTrx.SALES);
		assertNotNull(incoterms);
		assertThat(IncotermsId.equals(incoterms.getId(), IncotermsId.ofRepoId(5))).isTrue();
		assertEquals("TestIncoterms", incoterms.getName());
		assertEquals("TestIncotermsValue", incoterms.getValue());
		assertEquals("TestIncotermsLocation", incoterms.getLocationEffective());

		final Incoterms poIncoterms = bPartnerEffective.getIncoterms(SOTrx.PURCHASE);
		assertNotNull(poIncoterms);
		assertThat(IncotermsId.equals(poIncoterms.getId(), IncotermsId.ofRepoId(6))).isTrue();
		assertEquals("TestPoIncoterms", poIncoterms.getName());
		assertEquals("TestPoIncotermsValue", poIncoterms.getValue());
		assertEquals("TestPoIncotermsLocation", poIncoterms.getLocationEffective());
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
				.parentBPGroup_incoterms(Incoterms.builder()
						.id(IncotermsId.ofRepoId(9))
						.name("TestIncoterms")
						.value("TestIncotermsValue")
						.locationEffective("TestIncotermsLocation")
						.orgId(OrgId.ANY)
						.build())
				.parentBPGroup_poIncoterms(Incoterms.builder()
						.id(IncotermsId.ofRepoId(10))
						.name("TestPoIncoterms")
						.value("TestPoIncotermsValue")
						.locationEffective("TestPoIncotermsLocation")
						.orgId(OrgId.ANY)
						.build())
				.bpGroup_PricingSystemId(PricingSystemId.ofRepoId(5))
				.bpGroup_poPricingSystemId(PricingSystemId.ofRepoId(6))
				.bpGroup_PaymentTermId(PaymentTermId.ofRepoId(7))
				.bpGroup_poPaymentTermId(PaymentTermId.ofRepoId(8))
				.bpGroup_PaymentRule(PaymentRule.PayPal)
				.bpGroup_poPaymentRule(PaymentRule.PayPal)
				.bpGroup_InvoiceRule(InvoiceRule.OrderCompletelyDelivered)
				.bpGroup_isAutoInvoice(true)
				.bpGroup_incoterms(Incoterms.builder()
						.id(IncotermsId.ofRepoId(11))
						.name("TestIncoterms2")
						.value("TestIncotermsValue2")
						.locationEffective("TestIncotermsLocation2")
						.orgId(OrgId.ANY)
						.build())
				.bpGroup_poIncoterms(Incoterms.builder()
						.id(IncotermsId.ofRepoId(12))
						.name("TestPoIncoterms2")
						.value("TestPoIncotermsValue2")
						.locationEffective("TestPoIncotermsLocation2")
						.orgId(OrgId.ANY)
						.build())
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

		final Incoterms incoterms = bPartnerEffective.getIncoterms(SOTrx.SALES);
		assertNotNull(incoterms);
		assertThat(IncotermsId.equals(incoterms.getId(), IncotermsId.ofRepoId(11))).isTrue();
		assertEquals("TestIncoterms2", incoterms.getName());
		assertEquals("TestIncotermsValue2", incoterms.getValue());
		assertEquals("TestIncotermsLocation2", incoterms.getLocationEffective());

		final Incoterms poIncoterms = bPartnerEffective.getIncoterms(SOTrx.PURCHASE);
		assertNotNull(poIncoterms);
		assertThat(IncotermsId.equals(poIncoterms.getId(), IncotermsId.ofRepoId(12))).isTrue();
		assertEquals("TestPoIncoterms2", poIncoterms.getName());
		assertEquals("TestPoIncotermsValue2", poIncoterms.getValue());
		assertEquals("TestPoIncotermsLocation2", poIncoterms.getLocationEffective());
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
				.parentBPGroup_incoterms(Incoterms.builder()
						.id(IncotermsId.ofRepoId(13))
						.name("TestIncoterms")
						.value("TestIncotermsValue")
						.locationEffective("TestIncotermsLocation")
						.orgId(OrgId.ANY)
						.build())
				.parentBPGroup_poIncoterms(Incoterms.builder()
						.id(IncotermsId.ofRepoId(14))
						.name("TestPoIncoterms")
						.value("TestPoIncotermsValue")
						.locationEffective("TestPoIncotermsLocation")
						.orgId(OrgId.ANY)
						.build())
				.bpGroup_PricingSystemId(PricingSystemId.ofRepoId(5))
				.bpGroup_poPricingSystemId(PricingSystemId.ofRepoId(6))
				.bpGroup_PaymentTermId(PaymentTermId.ofRepoId(7))
				.bpGroup_poPaymentTermId(PaymentTermId.ofRepoId(8))
				.bpGroup_PaymentRule(PaymentRule.PayPal)
				.bpGroup_poPaymentRule(PaymentRule.PayPal)
				.bpGroup_InvoiceRule(InvoiceRule.OrderCompletelyDelivered)
				.bpGroup_isAutoInvoice(true)
				.bpGroup_incoterms(Incoterms.builder()
						.id(IncotermsId.ofRepoId(15))
						.name("TestIncoterms2")
						.value("TestIncotermsValue2")
						.locationEffective("TestIncotermsLocation2")
						.orgId(OrgId.ANY)
						.build())
				.bpGroup_poIncoterms(Incoterms.builder()
						.id(IncotermsId.ofRepoId(16))
						.name("TestPoIncoterms2")
						.value("TestPoIncotermsValue2")
						.locationEffective("TestPoIncotermsLocation2")
						.orgId(OrgId.ANY)
						.build())
				.bpartner_PricingSystemId(PricingSystemId.ofRepoId(9))
				.bpartner_poPricingSystemId(PricingSystemId.ofRepoId(10))
				.bpartner_PaymentTermId(PaymentTermId.ofRepoId(11))
				.bpartner_poPaymentTermId(PaymentTermId.ofRepoId(12))
				.bpartner_PaymentRule(PaymentRule.Settlement)
				.bpartner_poPaymentRule(PaymentRule.Settlement)
				.bpartner_InvoiceRule(InvoiceRule.AfterPick)
				.bpartner_poInvoiceRule(InvoiceRule.AfterPick)
				.bpartner_isAutoInvoice(false)
				.bpartner_incoterms(Incoterms.builder()
						.id(IncotermsId.ofRepoId(17))
						.name("TestIncoterms3")
						.value("TestIncotermsValue3")
						.locationEffective("TestIncotermsLocation3")
						.orgId(OrgId.ANY)
						.build())
				.bpartner_poIncoterms(Incoterms.builder()
						.id(IncotermsId.ofRepoId(18))
						.name("TestPoIncoterms3")
						.value("TestPoIncotermsValue3")
						.locationEffective("TestPoIncotermsLocation3")
						.orgId(OrgId.ANY)
						.build())
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

		final Incoterms incoterms = bPartnerEffective.getIncoterms(SOTrx.SALES);
		assertNotNull(incoterms);
		assertThat(IncotermsId.equals(incoterms.getId(), IncotermsId.ofRepoId(17))).isTrue();
		assertEquals("TestIncoterms3", incoterms.getName());
		assertEquals("TestIncotermsValue3", incoterms.getValue());
		assertEquals("TestIncotermsLocation3", incoterms.getLocationEffective());

		final Incoterms poIncoterms = bPartnerEffective.getIncoterms(SOTrx.PURCHASE);
		assertNotNull(poIncoterms);
		assertThat(IncotermsId.equals(poIncoterms.getId(), IncotermsId.ofRepoId(18))).isTrue();
		assertEquals("TestPoIncoterms3", poIncoterms.getName());
		assertEquals("TestPoIncotermsValue3", poIncoterms.getValue());
		assertEquals("TestPoIncotermsLocation3", poIncoterms.getLocationEffective());
	}

	@Test
	public void getPurchaseTransportDays_noValueOnBPartner_returns0()
	{
		final I_C_BP_Group bpGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		saveRecord(bpGroup);

		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		partner.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
		saveRecord(partner);

		assertThat(bpartnerEffectiveBL.getPurchaseTransportDays(BPartnerId.ofRepoId(partner.getC_BPartner_ID()))).isEqualTo(0);
	}

	@Test
	public void getPurchaseTransportDays_valueSetOnBPartner_returnsValue()
	{
		final I_C_BP_Group bpGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		saveRecord(bpGroup);

		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		partner.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
		partner.setPO_TransportDays(5);
		saveRecord(partner);

		assertThat(bpartnerEffectiveBL.getPurchaseTransportDays(BPartnerId.ofRepoId(partner.getC_BPartner_ID()))).isEqualTo(5);
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
			@Nullable final Incoterms bpartner_incoterms,
			@Nullable final Incoterms bpartner_poIncoterms,
			@Nullable final PricingSystemId bpGroup_PricingSystemId,
			@Nullable final PricingSystemId bpGroup_poPricingSystemId,
			@Nullable final PaymentTermId bpGroup_PaymentTermId,
			@Nullable final PaymentTermId bpGroup_poPaymentTermId,
			@Nullable final PaymentRule bpGroup_PaymentRule,
			@Nullable final PaymentRule bpGroup_poPaymentRule,
			@Nullable final InvoiceRule bpGroup_InvoiceRule,
			@Nullable final Boolean bpGroup_isAutoInvoice,
			@Nullable final Incoterms bpGroup_incoterms,
			@Nullable final Incoterms bpGroup_poIncoterms,
			@Nullable final PricingSystemId parentBPGroup_PricingSystemId,
			@Nullable final PricingSystemId parentBPGroup_poPricingSystemId,
			@Nullable final PaymentTermId parentBPGroup_PaymentTermId,
			@Nullable final PaymentTermId parentBPGroup_poPaymentTermId,
			@Nullable final PaymentRule parentBPGroup_PaymentRule,
			@Nullable final PaymentRule parentBPGroup_poPaymentRule,
			@Nullable final InvoiceRule parentBPGroup_InvoiceRule,
			@Nullable final Boolean parentBPGroup_isAutoInvoice,
			@Nullable final Incoterms parentBPGroup_incoterms,
			@Nullable final Incoterms parentBPGroup_poIncoterms
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
		bpGroupParent.setC_Incoterms_ID(createIncoterms(parentBPGroup_incoterms));
		bpGroupParent.setIncotermLocation(extractIncotermsLocation(parentBPGroup_incoterms));
        bpGroupParent.setPO_Incoterms_ID(createIncoterms(parentBPGroup_poIncoterms));
		bpGroupParent.setPO_IncotermLocation(extractIncotermsLocation(parentBPGroup_poIncoterms));
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
		bpGroup.setC_Incoterms_ID(createIncoterms(bpGroup_incoterms));
		bpGroup.setIncotermLocation(extractIncotermsLocation(bpGroup_incoterms));
		bpGroup.setPO_Incoterms_ID(createIncoterms(bpGroup_poIncoterms));
		bpGroup.setPO_IncotermLocation(extractIncotermsLocation(bpGroup_poIncoterms));
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
		partner.setC_Incoterms_Customer_ID(createIncoterms(bpartner_incoterms));
		partner.setIncotermLocation(extractIncotermsLocation(bpartner_incoterms));
		partner.setC_Incoterms_Vendor_ID(createIncoterms(bpartner_poIncoterms));
		partner.setPO_IncotermLocation(extractIncotermsLocation(bpartner_poIncoterms));
		saveRecord(partner);

		return BPartnerId.ofRepoId(partner.getC_BPartner_ID());
	}

	private int createIncoterms(@Nullable final Incoterms incoterms)
	{
		if (incoterms == null) {return IncotermsId.toRepoId(null);}

		final I_C_Incoterms incotermsRecord = InterfaceWrapperHelper.newInstance(I_C_Incoterms.class);
		incotermsRecord.setC_Incoterms_ID(incoterms.getId().getRepoId());
		incotermsRecord.setName(incoterms.getName());
		incotermsRecord.setValue(incoterms.getValue());
		incotermsRecord.setDefaultLocation(incoterms.getDefaultLocation());
		incotermsRecord.setIsDefault(incoterms.isDefault());
		saveRecord(incotermsRecord);

		return incotermsRecord.getC_Incoterms_ID();
	}

	@Nullable
	private String extractIncotermsLocation(@Nullable final Incoterms incoterms)
	{
		return incoterms != null ? incoterms.getLocationEffective() : null;
	}
}
