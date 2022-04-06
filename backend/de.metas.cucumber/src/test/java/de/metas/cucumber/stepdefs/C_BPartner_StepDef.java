/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.discountschema.M_DiscountSchema_StepDefData;
import de.metas.cucumber.stepdefs.pricing.M_PricingSystem_StepDefData;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.rest.v1.ExternalReferenceRestControllerService;
import de.metas.product.IProductDAO;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_AD_Language;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_C_BPartner_SalesRep_ID;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_InvoiceRule;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_IsCustomer;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_IsSalesRep;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_IsVendor;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_PO_DiscountSchema_ID;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_PO_InvoiceRule;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_PO_PricingSystem_ID;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_PaymentRulePO;
import static org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_PaymentRule;
import static org.compiere.model.I_M_Product.COLUMNNAME_M_Product_ID;
import static org.compiere.model.X_C_BPartner.DELIVERYRULE_Force;

public class C_BPartner_StepDef
{
	public static final int BP_GROUP_ID = BPGroupId.ofRepoId(1000000).getRepoId();

	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final M_PricingSystem_StepDefData pricingSystemTable;
	private final M_Product_StepDefData productTable;
	private final M_DiscountSchema_StepDefData discountSchemaTable;

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final ExternalReferenceRestControllerService externalReferenceRestControllerService = SpringContextHolder.instance.getBean(ExternalReferenceRestControllerService.class);

	public C_BPartner_StepDef(
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final M_PricingSystem_StepDefData pricingSystemTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_DiscountSchema_StepDefData discountSchemaTable)
	{
		this.bPartnerTable = bPartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.pricingSystemTable = pricingSystemTable;
		this.productTable = productTable;
		this.discountSchemaTable = discountSchemaTable;
	}

	@Given("metasfresh contains C_BPartners:")
	public void metasfresh_contains_c_bpartners(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createC_BPartner(tableRow);
		}
	}

	@And("preexisting test data is put into tableData")
	public void store_test_data_in_table_data(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final int bpartnerId = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_C_BPartner_ID);
			final I_C_BPartner bPartner = bpartnerDAO.getById(bpartnerId);
			assertThat(bPartner).isNotNull();

			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			bPartnerTable.put(bpartnerIdentifier, bPartner);

			final int bpartnerLocationId = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_C_BPartner_Location_ID);
			final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, bpartnerLocationId);

			final I_C_BPartner_Location bPartnerLocation = bpartnerDAO.getBPartnerLocationByIdInTrx(bPartnerLocationId);
			assertThat(bPartnerLocation).isNotNull();

			final String bpartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			bPartnerLocationTable.put(bpartnerLocationIdentifier, bPartnerLocation);

			final int productId = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_M_Product_ID);

			final I_M_Product product = productDAO.getById(productId);
			assertThat(product).isNotNull();

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			productTable.put(productIdentifier, product);
		}
	}

	@And("the following c_bpartner is changed")
	public void change_bpartner(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> dataRows = dataTable.asMaps();

		for (final Map<String, String> row : dataRows)
		{
			changeBPartner(row);
		}
	}

	@And("locate bpartner by external identifier")
	public void locate_bpartner_by_external_identifier(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			locate_bpartner_by_external_identifier(tableRow);
		}
	}

	private void createC_BPartner(@NonNull final Map<String, String> tableRow)
	{
		final String bPartnerName = tableRow.get("Name");
		final String bPartnerValue = CoalesceUtil.coalesce(tableRow.get("Value"), bPartnerName);

		final I_C_BPartner bPartnerRecord =
				CoalesceUtil.coalesceSuppliers(
						() -> bpartnerDAO.retrieveBPartnerByValue(Env.getCtx(), bPartnerValue),
						() -> InterfaceWrapperHelper.newInstance(I_C_BPartner.class));

		bPartnerRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		bPartnerRecord.setName(bPartnerName);
		bPartnerRecord.setValue(bPartnerValue);
		bPartnerRecord.setC_BP_Group_ID(BP_GROUP_ID);
		bPartnerRecord.setIsVendor(StringUtils.toBoolean(tableRow.get("OPT." + COLUMNNAME_IsVendor), false));
		bPartnerRecord.setIsCustomer(StringUtils.toBoolean(tableRow.get("OPT." + COLUMNNAME_IsCustomer), false));
		bPartnerRecord.setIsSalesRep(StringUtils.toBoolean(tableRow.get("OPT." + COLUMNNAME_IsSalesRep), false));

		final String discountSchemaIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_PO_DiscountSchema_ID + "." + TABLECOLUMN_IDENTIFIER);

		if (EmptyUtil.isNotBlank(discountSchemaIdentifier))
		{
			final I_M_DiscountSchema discountSchemaRecord = discountSchemaTable.get(discountSchemaIdentifier);
			bPartnerRecord.setPO_DiscountSchema_ID(discountSchemaRecord.getM_DiscountSchema_ID());
		}

		final String invoiceRule = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_InvoiceRule);

		if (EmptyUtil.isNotBlank(invoiceRule))
		{
			bPartnerRecord.setInvoiceRule(invoiceRule);
		}

		bPartnerRecord.setDeliveryRule(DELIVERYRULE_Force);

		final String pricingSystemIdentifier = tableRow.get(I_M_PricingSystem.COLUMNNAME_M_PricingSystem_ID + ".Identifier");
		if (EmptyUtil.isNotBlank(pricingSystemIdentifier))
		{
			final int pricingSystemId = pricingSystemTable.get(pricingSystemIdentifier).getM_PricingSystem_ID();
			bPartnerRecord.setM_PricingSystem_ID(pricingSystemId);
			bPartnerRecord.setPO_PricingSystem_ID(pricingSystemId);
		}

		final String poPricingSystemIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_PO_PricingSystem_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (EmptyUtil.isNotBlank(poPricingSystemIdentifier))
		{
			final int poPricingSystemId = pricingSystemTable.get(poPricingSystemIdentifier).getM_PricingSystem_ID();
			bPartnerRecord.setPO_PricingSystem_ID(poPricingSystemId);
		}

		final int paymentTermId = DataTableUtil.extractIntOrMinusOneForColumnName(tableRow, "OPT.C_PaymentTerm_ID");
		if (paymentTermId > 0)
		{
			bPartnerRecord.setC_PaymentTerm_ID(paymentTermId);
			bPartnerRecord.setPO_PaymentTerm_ID(paymentTermId);
		}

		bPartnerRecord.setAD_Language(tableRow.get("OPT." + COLUMNNAME_AD_Language));

		final String salesRepIdentifier = tableRow.get("OPT." + COLUMNNAME_C_BPartner_SalesRep_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (EmptyUtil.isNotBlank(salesRepIdentifier))
		{
			final I_C_BPartner salesRep = bPartnerTable.get(salesRepIdentifier);
			assertThat(salesRep).as("Missing salesrep C_BPartner record for identifier=" + salesRepIdentifier).isNotNull();

			bPartnerRecord.setC_BPartner_SalesRep_ID(salesRep.getC_BPartner_ID());
		}

		final String companyName = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner.COLUMNNAME_CompanyName);
		if (EmptyUtil.isNotBlank(companyName))
		{
			bPartnerRecord.setCompanyName(companyName);
		}

		final String adLanguage = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_AD_Language);
		if (EmptyUtil.isNotBlank(adLanguage))
		{
			bPartnerRecord.setAD_Language(adLanguage);
		}

		final String paymentTermValue = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner.COLUMNNAME_C_PaymentTerm_ID + ".Value");
		if (Check.isNotBlank(paymentTermValue))
		{
			final I_C_PaymentTerm paymentTerm = queryBL.createQueryBuilder(I_C_PaymentTerm.class)
					.addEqualsFilter(I_C_PaymentTerm.COLUMNNAME_Value, paymentTermValue)
					.create()
					.firstOnlyNotNull(I_C_PaymentTerm.class);

			bPartnerRecord.setC_PaymentTerm_ID(paymentTerm.getC_PaymentTerm_ID());
			bPartnerRecord.setPO_PaymentTerm_ID(paymentTerm.getC_PaymentTerm_ID());
		}

		final String paymentRule = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_PaymentRule);
		if(Check.isNotBlank(paymentRule))
		{
			bPartnerRecord.setPaymentRule(paymentRule);
		}

		final String paymentRulePO = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_PaymentRulePO);
		if(Check.isNotBlank(paymentRulePO))
		{
			bPartnerRecord.setPaymentRulePO(paymentRulePO);
		}

		final String poInvoiceRule = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_PO_InvoiceRule);
		if (EmptyUtil.isNotBlank(poInvoiceRule))
		{
			bPartnerRecord.setPO_InvoiceRule(poInvoiceRule);
		}

		final boolean alsoCreateLocation = InterfaceWrapperHelper.isNew(bPartnerRecord);
		InterfaceWrapperHelper.saveRecord(bPartnerRecord);

		if (alsoCreateLocation)
		{
			final I_C_Location locationRecord = InterfaceWrapperHelper.newInstance(I_C_Location.class);
			locationRecord.setC_Country_ID(StepDefConstants.COUNTRY_ID.getRepoId());
			InterfaceWrapperHelper.saveRecord(locationRecord);

			final I_C_BPartner_Location bPartnerLocationRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
			bPartnerLocationRecord.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
			bPartnerLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
			bPartnerLocationRecord.setIsBillToDefault(true);
			bPartnerLocationRecord.setIsShipTo(true);

			final String gln = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_GLN);
			if (EmptyUtil.isNotBlank(gln))
			{
				bPartnerLocationRecord.setGLN(gln);
			}

			InterfaceWrapperHelper.saveRecord(bPartnerLocationRecord);

			final String locationIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (EmptyUtil.isNotBlank(locationIdentifier))
			{
				bPartnerLocationTable.put(locationIdentifier, bPartnerLocationRecord);
			}
		}

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "C_BPartner");
		bPartnerTable.putOrReplace(recordIdentifier, bPartnerRecord);
	}

	private void changeBPartner(@NonNull final Map<String, String> row)
	{
		final String bpartner = DataTableUtil.extractStringForColumnName(row, I_C_BPartner.COLUMNNAME_C_BPartner_ID + ".Identifier");
		final String name2 = DataTableUtil.extractStringOrNullForColumnName(row, "Name2");

		final I_C_BPartner bPartner = bPartnerTable.get(bpartner);

		bPartner.setName2(name2);

		InterfaceWrapperHelper.save(bPartner);
	}

	private void locate_bpartner_by_external_identifier(@NonNull final Map<String, String> row)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(DataTableUtil.extractStringForColumnName(row, "externalIdentifier"));

		final Optional<JsonMetasfreshId> bpartnerIdOptional = externalReferenceRestControllerService.getJsonMetasfreshIdFromExternalReference(ORG_ID, externalIdentifier, BPartnerExternalReferenceType.BPARTNER);
		assertThat(bpartnerIdOptional).isPresent();

		final I_C_BPartner bPartnerRecord = bpartnerDAO.getById(bpartnerIdOptional.get().getValue());
		assertThat(bPartnerRecord).isNotNull();

		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		bPartnerTable.putOrReplace(bpartnerIdentifier, bPartnerRecord);
	}
}
