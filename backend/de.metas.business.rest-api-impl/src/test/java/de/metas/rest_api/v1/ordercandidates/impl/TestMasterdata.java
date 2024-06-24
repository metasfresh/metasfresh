package de.metas.rest_api.v1.ordercandidates.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.document.DocBaseAndSubType;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.location.CountryId;
import de.metas.location.LocationId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.rules.IPricingRule;
import de.metas.pricing.rules.price_list_version.PriceListVersionPricingRule;
import de.metas.shipping.ShipperId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.pricing.model.I_C_PricingRule;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.Ignore;

import javax.annotation.Nullable;
import java.time.LocalDate;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Ignore
final class TestMasterdata
{
	public static final String RESOURCE_PATH = "/de/metas/rest_api/v1/ordercandidates/impl/";

	public void createDataSource(final String internalName)
	{
		final I_AD_InputDataSource dataSourceRecord = newInstance(I_AD_InputDataSource.class);
		dataSourceRecord.setInternalName(internalName);
		saveRecord(dataSourceRecord);
	}

	public void createDocType(final DocBaseAndSubType docBaseAndSubType)
	{
		final I_C_DocType docTypeRecord = newInstance(I_C_DocType.class);
		docTypeRecord.setDocBaseType(docBaseAndSubType.getDocBaseType().getCode());
		docTypeRecord.setDocSubType(docBaseAndSubType.getDocSubType());
		saveRecord(docTypeRecord);
	}

	@Builder(builderMethodName = "prepareBPartnerAndLocation", builderClassName = "_BPartnerAndLocationBuilder")
	private BPartnerLocationId createBPartnerAndLocation(
			@Nullable final OrgId orgId,
			@NonNull final String bpValue,
			@Nullable final String bpExternalId,
			@Nullable final PricingSystemId salesPricingSystemId,
			@NonNull final CountryId countryId,
			@Nullable final GLN gln,
			@Nullable final String bpLocationExternalId,
			@Nullable final String vatId)
	{

		final I_C_BP_Group groupRecord = newInstance(I_C_BP_Group.class);
		groupRecord.setName(bpValue + "-name");
		if (orgId != null)
		{
			groupRecord.setAD_Org_ID(orgId.getRepoId());
		}
		saveRecord(groupRecord);

		final I_C_BPartner bpRecord = newInstance(I_C_BPartner.class);
		POJOWrapper.setInstanceName(bpRecord, bpValue);
		if (orgId != null)
		{
			bpRecord.setAD_Org_ID(orgId.getRepoId());
		}
		bpRecord.setValue(bpValue);
		bpRecord.setName(bpValue + "-name");
		bpRecord.setExternalId(bpExternalId);
		bpRecord.setIsCustomer(true);
		bpRecord.setM_PricingSystem_ID(PricingSystemId.toRepoId(salesPricingSystemId));
		bpRecord.setPaymentRule(PaymentRule.OnCredit.getCode());
		bpRecord.setPaymentRulePO(PaymentRule.OnCredit.getCode());
		bpRecord.setC_BP_Group_ID(groupRecord.getC_BP_Group_ID());
		bpRecord.setVATaxID(vatId);
		saveRecord(bpRecord);

		return prepareBPartnerLocation()
				.orgId(orgId)
				.bpartnerId(BPartnerId.ofRepoId(bpRecord.getC_BPartner_ID()))
				.countryId(countryId)
				.gln(gln)
				.externalId(bpLocationExternalId)
				.build();
	}

	@Builder(builderMethodName = "prepareBPartner", builderClassName = "_BPartnerBuilder")
	private BPartnerId createBPartner(
			@Nullable final OrgId orgId,
			@NonNull final String bpValue,
			@Nullable final String bpExternalId,
			@Nullable final String bpGroupExistingName,
			@Nullable final PricingSystemId salesPricingSystemId)
	{
		final I_C_BP_Group groupRecord;
		if (bpGroupExistingName != null)
		{
			groupRecord = POJOLookupMap.get()
					.getFirstOnly(I_C_BP_Group.class, bp -> bp.getName().equals(bpGroupExistingName));
		}
		else
		{
			groupRecord = newInstance(I_C_BP_Group.class);
			groupRecord.setName(bpValue + "-name");
			if (orgId != null)
			{
				groupRecord.setAD_Org_ID(orgId.getRepoId());
			}
			saveRecord(groupRecord);
		}
		final I_C_BPartner bpRecord = newInstance(I_C_BPartner.class);
		POJOWrapper.setInstanceName(bpRecord, bpValue);
		if (orgId != null)
		{
			bpRecord.setAD_Org_ID(orgId.getRepoId());
		}
		bpRecord.setValue(bpValue);
		bpRecord.setName(bpValue + "-name");
		bpRecord.setExternalId(bpExternalId);
		bpRecord.setIsCustomer(true);
		bpRecord.setM_PricingSystem_ID(PricingSystemId.toRepoId(salesPricingSystemId));
		bpRecord.setPaymentRule(PaymentRule.OnCredit.getCode());
		bpRecord.setPaymentRulePO(PaymentRule.OnCredit.getCode());
		bpRecord.setC_BP_Group_ID(groupRecord.getC_BP_Group_ID());
		saveRecord(bpRecord);

		return BPartnerId.ofRepoId(bpRecord.getC_BPartner_ID());
	}

	@Builder(builderMethodName = "prepareBPartnerLocation", builderClassName = "_BPartnerLocationBuilder")
	private BPartnerLocationId createBPartnerLocation(
			@Nullable final OrgId orgId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final CountryId countryId,
			@Nullable final GLN gln,
			@Nullable final String externalId,
			final boolean billTo,
			final boolean billToDefault,
			final boolean shipTo,
			final boolean shipToDefault)
	{
		final LocationId locationId = createLocation(countryId);

		final I_C_BPartner_Location bplRecord = newInstance(I_C_BPartner_Location.class);
		if (orgId != null)
		{
			bplRecord.setAD_Org_ID(orgId.getRepoId());
		}
		bplRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bplRecord.setC_Location_ID(locationId.getRepoId());
		bplRecord.setGLN(gln != null ? gln.getCode() : null);
		bplRecord.setExternalId(externalId);
		bplRecord.setIsBillTo(billTo);
		bplRecord.setIsBillToDefault(billToDefault);
		bplRecord.setIsShipTo(shipTo);
		bplRecord.setIsShipToDefault(shipToDefault);

		POJOWrapper.setInstanceName(bplRecord, coalesce(bplRecord.getGLN(), bplRecord.getExternalId()));
		saveRecord(bplRecord);

		return BPartnerLocationId.ofRepoId(bplRecord.getC_BPartner_ID(), bplRecord.getC_BPartner_Location_ID());
	}

	private LocationId createLocation(final CountryId countryId)
	{
		final I_C_Location record = newInstance(I_C_Location.class);
		record.setC_Country_ID(countryId.getRepoId());
		saveRecord(record);
		return LocationId.ofRepoId(record.getC_Location_ID());
	}

	public void createProduct(final String value, final UomId uomId)
	{
		final I_M_Product record = newInstance(I_M_Product.class);
		record.setValue(value);
		record.setC_UOM_ID(uomId.getRepoId());
		saveRecord(record);
	}

	public PricingSystemId createPricingSystem(@NonNull final String pricinSystemCode)
	{
		final I_M_PricingSystem record = newInstance(I_M_PricingSystem.class);
		record.setValue(pricinSystemCode);
		saveRecord(record);
		return PricingSystemId.ofRepoId(record.getM_PricingSystem_ID());
	}

	public PriceListId createSalesPriceList(
			@NonNull final PricingSystemId pricingSystemId,
			@NonNull final CountryId countryId,
			@NonNull final CurrencyId currencyId,
			@Nullable final TaxCategoryId defaultTaxCategoryId)
	{
		final I_M_PriceList record = newInstance(I_M_PriceList.class);
		record.setM_PricingSystem_ID(pricingSystemId.getRepoId());
		record.setC_Country_ID(countryId.getRepoId());
		record.setC_Currency_ID(currencyId.getRepoId());
		record.setIsSOPriceList(true);
		record.setPricePrecision(2);
		record.setDefault_TaxCategory_ID(TaxCategoryId.toRepoId(defaultTaxCategoryId));
		saveRecord(record);
		return PriceListId.ofRepoId(record.getM_PriceList_ID());
	}

	public PriceListVersionId createPriceListVersion(
			@NonNull final PriceListId priceListId,
			@NonNull final LocalDate validFrom)
	{
		I_M_PriceList_Version record = newInstance(I_M_PriceList_Version.class);
		record.setM_PriceList_ID(priceListId.getRepoId());
		record.setValidFrom(TimeUtil.asTimestamp(validFrom));
		saveRecord(record);
		return PriceListVersionId.ofRepoId(record.getM_PriceList_Version_ID());
	}

	public TaxCategoryId createTaxCategory()
	{
		final I_C_TaxCategory record = newInstance(I_C_TaxCategory.class);
		saveRecord(record);
		return TaxCategoryId.ofRepoId(record.getC_TaxCategory_ID());
	}

	public void createPricingRules()
	{
		createPricingRule(PriceListVersionPricingRule.class, 10);
	}

	private void createPricingRule(
			@NonNull final Class<? extends IPricingRule> clazz,
			int seqNo)
	{
		final String classname = clazz.getName();

		final I_C_PricingRule pricingRule = newInstance(I_C_PricingRule.class);
		pricingRule.setName(classname);
		pricingRule.setClassname(classname);
		pricingRule.setIsActive(true);
		pricingRule.setSeqNo(seqNo);
		saveRecord(pricingRule);
	}

	public WarehouseId createWarehouse(final String value)
	{
		final I_M_Warehouse record = newInstance(I_M_Warehouse.class);
		record.setValue(value);
		saveRecord(record);
		return WarehouseId.ofRepoId(record.getM_Warehouse_ID());
	}

	public ShipperId createShipper(final String shipperName)
	{
		final I_M_Shipper shipper = newInstance(I_M_Shipper.class);
		shipper.setName(shipperName);
		shipper.setValue(shipperName);
		saveRecord(shipper);

		return ShipperId.ofRepoId(shipper.getM_Shipper_ID());

	}

	public PaymentTermId createPaymentTerm(final String value, final String externalId)
	{
		final I_C_PaymentTerm paymentTerm = newInstance(I_C_PaymentTerm.class);
		paymentTerm.setValue(value);
		paymentTerm.setExternalId(externalId);

		saveRecord(paymentTerm);

		return PaymentTermId.ofRepoId(paymentTerm.getC_PaymentTerm_ID());
	}

	public BPartnerId createSalesRep(final String salesRepCode)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setSalesPartnerCode(salesRepCode);
		partner.setIsSalesRep(true);
		saveRecord(partner);

		return BPartnerId.ofRepoId(partner.getC_BPartner_ID());
	}
}
