/*
 * #%L
 * de.metas.salescandidate.base
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

package de.metas.ordercandidate.api;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.document.location.DocumentLocation;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.externalsystem.model.I_ExternalSystem;
import de.metas.greeting.GreetingRepository;
import de.metas.location.CountryId;
import de.metas.location.LocationId;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.ordercandidate.api.impl.OLCandBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.NullOLCandListener;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.uom.X12DE355;
import lombok.NonNull;
import org.adempiere.ad.persistence.custom_columns.CustomColumnService;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_PromotionCode;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Tests that {@link OLCandOrderFactory} copies first-class fields
 * (C_PromotionCode_ID, C_PromotionCode2_ID, IsWithoutCharge, Reason)
 * from C_OLCand to the created C_Order and C_OrderLine explicitly via typed setters.
 */
class OLCandOrderFactoryFirstClassFieldsTest
{
	private CountryId countryDE;
	private I_C_UOM uomKg;
	private ProductId productId;
	private ExternalSystemId externalSystemId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new GreetingRepository());
		SpringContextHolder.registerJUnitBean(new OrderGroupRepository(
				new GroupCompensationLineCreateRequestFactory(),
				Optional.empty()
		));
		SpringContextHolder.registerJUnitBean(new GroupTemplateRepository(Optional.empty()));
		SpringContextHolder.registerJUnitBean(new OLCandValidatorService(
				new OLCandSPIRegistry(Optional.empty(), Optional.empty(), Optional.empty())));

		// registers IOLCandBL under the interface key consumers resolve via Services.get(IOLCandBL.class)
		OLCandBL.newInstanceForUnitTesting();

		// No custom columns needed for this test (we test first-class typed propagation, not generic)
		CustomColumnService.newInstanceForUnitTesting();

		countryDE = createCountry("DE", "@A1@ @CO@");
		uomKg = createUomKg();
		productId = createProduct("product");
		externalSystemId = ExternalSystemId.ofRepoId(createExternalSystem().getExternalSystem_ID());
	}

	@Test
	void promotionCodesPropagatedToOrder()
	{
		// Given: two promotion code records
		final I_C_PromotionCode promoCode1 = newInstanceOutOfTrx(I_C_PromotionCode.class);
		promoCode1.setName("PROMO1");
		promoCode1.setValue("PROMO1");
		saveRecord(promoCode1);

		final I_C_PromotionCode promoCode2 = newInstanceOutOfTrx(I_C_PromotionCode.class);
		promoCode2.setName("PROMO2");
		promoCode2.setValue("PROMO2");
		saveRecord(promoCode2);

		// Stage an OLCand with both promo codes
		final DocumentLocation docLocation = createDocumentLocation(countryDE, "addr1");
		final I_C_OLCand olCandRecord = newOLCandRecord(docLocation);
		olCandRecord.setC_PromotionCode_ID(promoCode1.getC_PromotionCode_ID());
		olCandRecord.setC_PromotionCode2_ID(promoCode2.getC_PromotionCode_ID());
		saveRecord(olCandRecord);

		final OLCand olCand = new OLCandFactory().toOLCand(olCandRecord);

		// When
		final OLCandOrderFactory factory = createFactory();
		factory.addOLCand(olCand);

		// Then: promo codes land on the created C_Order
		final I_C_Order order = factory.getOrder();
		Assertions.assertThat(order).isNotNull();
		Assertions.assertThat(order.getC_PromotionCode_ID())
				.as("C_PromotionCode_ID should be propagated to C_Order")
				.isEqualTo(promoCode1.getC_PromotionCode_ID());
		Assertions.assertThat(order.getC_PromotionCode2_ID())
				.as("C_PromotionCode2_ID should be propagated to C_Order")
				.isEqualTo(promoCode2.getC_PromotionCode_ID());
	}

	@Test
	void isWithoutChargeAndReasonPropagatedToOrderLine()
	{
		// Stage an OLCand with IsWithoutCharge=true and Reason="someReason"
		final DocumentLocation docLocation = createDocumentLocation(countryDE, "addr2");
		final I_C_OLCand olCandRecord = newOLCandRecord(docLocation);
		olCandRecord.setIsWithoutCharge(true);
		olCandRecord.setReason("someReason");
		saveRecord(olCandRecord);

		final OLCand olCand = new OLCandFactory().toOLCand(olCandRecord);

		// When
		final OLCandOrderFactory factory = createFactory();
		factory.addOLCand(olCand);
		factory.closeCurrentOrderLine();

		// Then: IsWithoutCharge and Reason land on the created C_OrderLine
		final I_C_Order order = factory.getOrder();
		Assertions.assertThat(order).isNotNull();

		final I_C_OrderLine savedLine = org.adempiere.ad.wrapper.POJOLookupMap.get()
				.getFirstOnly(I_C_OrderLine.class, ol -> true);

		Assertions.assertThat(savedLine).isNotNull();
		Assertions.assertThat(savedLine.isWithoutCharge())
				.as("IsWithoutCharge should be propagated to C_OrderLine")
				.isTrue();
		Assertions.assertThat(savedLine.getReason())
				.as("Reason should be propagated to C_OrderLine")
				.isEqualTo("someReason");
	}

	// ---- helpers ----

	private I_C_OLCand newOLCandRecord(final DocumentLocation location)
	{
		final I_C_OLCand record = InterfaceWrapperHelper.newInstance(I_C_OLCand.class);
		de.metas.ordercandidate.location.adapter.OLCandDocumentLocationAdapterFactory
				.bpartnerLocationAdapter(record).setFrom(location);
		record.setExternalSystem_ID(externalSystemId.getRepoId());
		record.setM_Product_ID(productId.getRepoId());
		record.setC_UOM_ID(uomKg.getC_UOM_ID());
		record.setApplySalesRepFrom(AssignSalesRepRule.CandidateFirst.getCode());
		record.setDateCandidate(SystemTime.asTimestamp());
		return record;
	}

	private OLCandOrderFactory createFactory()
	{
		return OLCandOrderFactory.builder()
				.orderDefaults(OLCandOrderDefaults.builder().build())
				.olCandProcessorId(111)
				.olCandListeners(NullOLCandListener.instance)
				.build();
	}

	private I_C_UOM createUomKg()
	{
		final I_C_UOM uom = newInstanceOutOfTrx(I_C_UOM.class);
		uom.setName(X12DE355.KILOGRAM.getCode());
		uom.setUOMSymbol(X12DE355.KILOGRAM.getCode());
		uom.setX12DE355(X12DE355.KILOGRAM.getCode());
		saveRecord(uom);
		return uom;
	}

	private I_ExternalSystem createExternalSystem()
	{
		final I_ExternalSystem externalSystem = newInstanceOutOfTrx(I_ExternalSystem.class);
		externalSystem.setValue("test");
		externalSystem.setName("test");
		saveRecord(externalSystem);
		return externalSystem;
	}

	private ProductId createProduct(@NonNull final String name)
	{
		final I_M_Product product = newInstanceOutOfTrx(I_M_Product.class);
		POJOWrapper.setInstanceName(product, name);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(uomKg.getC_UOM_ID());
		product.setProductType(ProductType.Item.getCode());
		product.setIsStocked(true);
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private CountryId createCountry(@NonNull final String countryCode, final String addressFormat)
	{
		final I_C_Country record = newInstance(I_C_Country.class);
		record.setCountryCode(countryCode);
		record.setName(countryCode);
		record.setDisplaySequence(addressFormat);
		record.setDisplaySequenceLocal(addressFormat);
		POJOWrapper.setInstanceName(record, countryCode);
		saveRecord(record);
		return CountryId.ofRepoId(record.getC_Country_ID());
	}

	private LocationId createLocation(@NonNull final CountryId countryId, @Nullable final String address1)
	{
		final I_C_Location record = newInstance(I_C_Location.class);
		record.setC_Country_ID(countryId.getRepoId());
		record.setAddress1(address1);
		saveRecord(record);
		return LocationId.ofRepoId(record.getC_Location_ID());
	}

	private DocumentLocation createDocumentLocation(@NonNull final CountryId countryId, @Nullable final String address1)
	{
		final I_C_BP_Group bpGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		bpGroup.setName("bpGroup");
		bpGroup.setValue("bpGroupValue");
		InterfaceWrapperHelper.saveRecord(bpGroup);

		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bpartner.setInvoiceRule(X_C_BPartner.INVOICERULE_AfterDelivery);
		bpartner.setPO_InvoiceRule(X_C_BPartner.INVOICERULE_AfterDelivery);
		bpartner.setPaymentRule(X_C_BPartner.PAYMENTRULE_Cash);
		bpartner.setPaymentRulePO(X_C_BPartner.PAYMENTRULE_Cash);
		bpartner.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
		InterfaceWrapperHelper.saveRecord(bpartner);

		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpLocation.setC_Location_ID(createLocation(countryId, address1).getRepoId());
		InterfaceWrapperHelper.saveRecord(bpLocation);

		final I_AD_User user = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		user.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		InterfaceWrapperHelper.saveRecord(user);

		return DocumentLocation.builder()
				.bpartnerId(BPartnerId.ofRepoId(bpLocation.getC_BPartner_ID()))
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID()))
				.locationId(LocationId.ofRepoId(bpLocation.getC_Location_ID()))
				.contactId(BPartnerContactId.ofRepoId(user.getC_BPartner_ID(), user.getAD_User_ID()))
				.build();
	}
}
