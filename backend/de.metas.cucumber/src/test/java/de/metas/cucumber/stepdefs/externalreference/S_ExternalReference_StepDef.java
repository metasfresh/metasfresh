/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.cucumber.stepdefs.externalreference;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupResponse;
import de.metas.common.externalreference.v2.JsonExternalReferenceResponseItem;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.externalsystem.ExternalSystem_Config_StepDefData;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDefData;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.ExternalUserReferenceType;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.IExternalSystem;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.bpartnerlocation.BPLocationExternalReferenceType;
import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.shipper.ShipperExternalReferenceType;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.externalreference.model.I_S_ExternalReference.COLUMNNAME_ExternalSystem_Config_ID;
import static de.metas.externalreference.model.I_S_ExternalReference.COLUMNNAME_S_ExternalReference_ID;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID;
import static org.compiere.model.I_AD_User.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_AD_User.COLUMNNAME_C_BPartner_Location_ID;
import static org.compiere.model.I_M_Product.COLUMNNAME_M_Product_ID;
import static org.compiere.model.I_M_Shipper.COLUMNNAME_M_Shipper_ID;
import static org.glassfish.gmbal.impl.TypeConverterImpl.NULL_STRING;

public class S_ExternalReference_StepDef
{
	private final OrgId defaultOrgId = OrgId.ofRepoId(1000000);

	private final AD_User_StepDefData userTable;
	private final S_ExternalReference_StepDefData externalRefTable;
	private final M_Shipper_StepDefData shipperTable;
	private final M_Product_StepDefData productTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bpLocationTable;
	private final ExternalSystem_Config_StepDefData externalSystemConfigTable;
	private final AD_Org_StepDefData orgTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final ExternalReferenceTypes externalReferenceTypes;
	private final ExternalSystems externalSystems;
	private final ExternalReferenceRepository externalReferenceRepository;
	private final TestContext testContext;

	public S_ExternalReference_StepDef(
			@NonNull final ExternalSystems externalSystems,
			@NonNull final AD_User_StepDefData userTable,
			@NonNull final S_ExternalReference_StepDefData externalRefTable,
			@NonNull final M_Shipper_StepDefData shipperTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bpLocationTable,
			@NonNull final ExternalSystem_Config_StepDefData externalSystemConfigTable,
			@NonNull final AD_Org_StepDefData orgTable,
			@NonNull final TestContext testContext)
	{
		this.externalSystems = externalSystems;
		this.userTable = userTable;
		this.externalRefTable = externalRefTable;
		this.shipperTable = shipperTable;
		this.productTable = productTable;
		this.bpartnerTable = bpartnerTable;
		this.bpLocationTable = bpLocationTable;
		this.externalSystemConfigTable = externalSystemConfigTable;
		this.orgTable = orgTable;
		this.testContext = testContext;
		this.externalReferenceTypes = SpringContextHolder.instance.getBean(ExternalReferenceTypes.class);
		this.externalReferenceRepository = SpringContextHolder.instance.getBean(ExternalReferenceRepository.class);
	}

	@Then("verify that S_ExternalReference was created")
	public void verifyExists(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> externalReferencesTableList = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : externalReferencesTableList)
		{
			final String externalSystem = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, I_S_ExternalReference.COLUMNNAME_ExternalSystem);
			final String type = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, I_S_ExternalReference.COLUMNNAME_Type);
			final String externalReference = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, I_S_ExternalReference.COLUMNNAME_ExternalReference);

			final IQueryBuilder<I_S_ExternalReference> externalReferenceQueryBuilder = queryBL.createQueryBuilder(I_S_ExternalReference.class)
					.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalSystem, externalSystem)
					.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_Type, type)
					.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalReference, externalReference);

			final I_S_ExternalReference externalReferenceRecord = externalReferenceQueryBuilder
					.create()
					.firstOnlyOrNull(I_S_ExternalReference.class);

			assertThat(externalReferenceRecord).as("S_ExternalReference with [ExternalSystem=%s, Type=%s, ExternalReference=%s]", externalSystem, type, externalReference).isNotNull();

			final SoftAssertions softly = new SoftAssertions();
			
			final String orgCodeIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + I_S_ExternalReference.COLUMNNAME_AD_Org_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orgCodeIdentifier))
			{
				final int orgId = orgTable.get(orgCodeIdentifier).getAD_Org_ID();

				softly.assertThat(externalReferenceRecord.getAD_Org_ID()).as("AD_Org_ID for Identifier=%s", orgCodeIdentifier).isEqualTo(orgId);
			}

			final Integer recordId = DataTableUtil.extractIntegerOrNullForColumnName(dataTableRow, "OPT." + I_S_ExternalReference.COLUMNNAME_Referenced_Record_ID);
			if (recordId != null)
			{
				softly.assertThat(externalReferenceRecord.getRecord_ID()).as("Record_ID").isEqualTo(recordId);
				softly.assertThat(externalReferenceRecord.getReferenced_Record_ID()).as("Referenced_Record_ID").isEqualTo(recordId);
			}

			final Boolean isReadOnlyInMetasfresh = DataTableUtil.extractBooleanForColumnNameOrNull(dataTableRow, "OPT." + I_S_ExternalReference.COLUMNNAME_IsReadOnlyInMetasfresh);
			if (isReadOnlyInMetasfresh != null)
			{
				softly.assertThat(externalReferenceRecord.isReadOnlyInMetasfresh()).as("IsReadOnlyInMetasfresh").isEqualTo(isReadOnlyInMetasfresh);
			}

			final String externalReferenceURL = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + I_S_ExternalReference.COLUMNNAME_ExternalReferenceURL);
			if (externalReferenceURL != null)
			{
				if (NULL_STRING.equals(externalReferenceURL))
				{
					softly.assertThat(externalReferenceRecord.getExternalReferenceURL()).as("ExternalReferenceURL").isNull();
				}
				else
				{
					softly.assertThat(externalReferenceRecord.getExternalReferenceURL()).as("ExternalReferenceURL").isEqualTo(externalReferenceURL);
				}
			}
			final String externalSystemParentConfigId = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT." + I_S_ExternalReference.COLUMNNAME_ExternalSystem_Config_ID);
			if (externalSystemParentConfigId != null)
			{
				if (NULL_STRING.equals(externalSystemParentConfigId))
				{
					softly.assertThat(externalReferenceRecord.getExternalSystem_Config_ID()).as("ExternalSystem_Config_ID").isLessThanOrEqualTo(0);
				}
				else
				{
					softly.assertThat(externalReferenceRecord.getExternalSystem_Config_ID()).as("ExternalSystem_Config_ID").isEqualTo(Integer.parseInt(externalSystemParentConfigId));
				}
			}
			
			softly.assertAll();
		}
	}

	@And("metasfresh contains S_ExternalReference:")
	public void add_S_ExternalReference(
			@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String externalSystemCode = DataTableUtil.extractStringForColumnName(row, I_S_ExternalReference.COLUMNNAME_ExternalSystem);
			final IExternalSystem externalSystemType = externalSystems.ofCode(externalSystemCode)
					.orElseThrow(() -> new AdempiereException("Unknown externalSystemCode" + externalSystemCode));

			final String typeCode = DataTableUtil.extractStringForColumnName(row, I_S_ExternalReference.COLUMNNAME_Type);
			final IExternalReferenceType type = externalReferenceTypes.ofCode(typeCode)
					.orElseThrow(() -> new InvalidIdentifierException("type", typeCode));

			final String externalReference = DataTableUtil.extractStringForColumnName(row, I_S_ExternalReference.COLUMNNAME_ExternalReference);

			final I_S_ExternalReference externalReferenceRecord = CoalesceUtil.coalesceSuppliers(
					() -> queryBL.createQueryBuilder(I_S_ExternalReference.class)
							.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalReference, externalReference)
							.addEqualsFilter(I_S_ExternalReference.COLUMN_Type, type.getCode())
							.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalSystem, externalSystemType.getCode())
							.create()
							.firstOnlyOrNull(I_S_ExternalReference.class),
					() -> newInstanceOutOfTrx(I_S_ExternalReference.class));

			assertThat(externalReferenceRecord).isNotNull();

			externalReferenceRecord.setExternalSystem(externalSystemType.getCode());
			externalReferenceRecord.setType(type.getCode());
			externalReferenceRecord.setExternalReference(externalReference);

			if (type.getCode().equals(ExternalUserReferenceType.USER_ID.getCode()))
			{
				final String userIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
				assertThat(userIdentifier).isNotNull();

				final I_AD_User user = userTable.get(userIdentifier);
				assertThat(user).isNotNull();

				externalReferenceRecord.setRecord_ID(user.getAD_User_ID());
			}
			else if (type.getCode().equals(ShipperExternalReferenceType.SHIPPER.getCode()))
			{
				final String shipperIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_Shipper_ID + "." + TABLECOLUMN_IDENTIFIER);
				assertThat(shipperIdentifier).isNotNull();

				final I_M_Shipper shipper = shipperTable.get(shipperIdentifier);
				assertThat(shipper).isNotNull();

				externalReferenceRecord.setRecord_ID(shipper.getM_Shipper_ID());
			}
			else if (type.getCode().equals(BPartnerExternalReferenceType.BPARTNER.getCode()))
			{
				final String bPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
				assertThat(bPartnerIdentifier).isNotNull();

				final I_C_BPartner bPartnerRecord = bpartnerTable.get(bPartnerIdentifier);
				assertThat(bPartnerIdentifier).isNotNull();

				externalReferenceRecord.setRecord_ID(bPartnerRecord.getC_BPartner_ID());
				externalReferenceRecord.setReferenced_Record_ID(bPartnerRecord.getC_BPartner_ID());
			}
			else if (type.getCode().equals(BPLocationExternalReferenceType.BPARTNER_LOCATION.getCode()))
			{
				final String bPartnerLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
				assertThat(bPartnerLocationIdentifier).isNotNull();

				final I_C_BPartner_Location bpartnerLocationRecord = bpLocationTable.get(bPartnerLocationIdentifier);
				assertThat(bpartnerLocationRecord).isNotNull();

				externalReferenceRecord.setRecord_ID(bpartnerLocationRecord.getC_BPartner_Location_ID());
				externalReferenceRecord.setReferenced_Record_ID(bpartnerLocationRecord.getC_BPartner_Location_ID());
			}
			else if (ProductExternalReferenceType.PRODUCT.getCode().equals(type.getCode()))
			{
				final String externalSystemConfigIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_ExternalSystem_Config_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(externalSystemConfigIdentifier))
				{
					final int externalSystemConfigId = externalSystemConfigTable.getOptional(externalSystemConfigIdentifier)
							.map(I_ExternalSystem_Config::getExternalSystem_Config_ID)
							.orElseGet((() -> Integer.parseInt(externalSystemConfigIdentifier)));

					externalReferenceRecord.setExternalSystem_Config_ID(externalSystemConfigId);
				}

				final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
				assertThat(productIdentifier).isNotNull();

				final int productId = productTable.getOptional(productIdentifier)
						.map(I_M_Product::getM_Product_ID)
						.orElseGet(() -> Integer.parseInt(productIdentifier));

				externalReferenceRecord.setRecord_ID(productId);
			}
			else
			{
				throw new AdempiereException("Unknown X_S_ExternalReference.Type! type:" + typeCode);
			}

			InterfaceWrapperHelper.saveRecord(externalReferenceRecord);

			final String externalReferenceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_S_ExternalReference_ID + "." + TABLECOLUMN_IDENTIFIER);
			externalRefTable.put(externalReferenceIdentifier, externalReferenceRecord);
		}
	}

	@And("process external reference lookup endpoint response")
	public void process_external_reference_lookup_endpoint_response(
			@NonNull final DataTable table) throws JsonProcessingException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final JsonExternalReferenceLookupResponse jsonExternalReferenceLookupResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonExternalReferenceLookupResponse.class);
		assertThat(jsonExternalReferenceLookupResponse).isNotNull();
		assertThat(jsonExternalReferenceLookupResponse.getItems()).isNotEmpty();

		final List<JsonExternalReferenceResponseItem> referenceItems = jsonExternalReferenceLookupResponse.getItems();
		final List<Map<String, String>> rows = table.asMaps();

		assertThat(referenceItems.size()).isEqualTo(rows.size());

		for (final Map<String, String> row : rows)
		{
			final String expectedExternalReference = DataTableUtil.extractStringForColumnName(row, I_S_ExternalReference.COLUMNNAME_ExternalReference);

			final JsonExternalReferenceResponseItem item = Check.singleElement(referenceItems
																					   .stream()
																					   .filter(referenceItem -> referenceItem.getLookupItem().getExternalReference().equals(expectedExternalReference))
																					   .collect(ImmutableList.toImmutableList()));

			final String externalReferenceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_S_ExternalReference_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_S_ExternalReference externalReference = InterfaceWrapperHelper.load(item.getExternalReferenceId().getValue(), I_S_ExternalReference.class);

			assertThat(externalReference).isNotNull();

			externalRefTable.put(externalReferenceIdentifier, externalReference);
		}
	}

	@And("the following S_ExternalReference is changed:")
	public void change_S_ExternalReference(
			@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String externalReferenceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_S_ExternalReference_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_S_ExternalReference externalReferenceRecord = externalRefTable.get(externalReferenceIdentifier);

			final String version = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_S_ExternalReference.COLUMNNAME_Version);
			if (Check.isNotBlank(version))
			{
				externalReferenceRecord.setVersion(version);
			}

			InterfaceWrapperHelper.saveRecord(externalReferenceRecord);
			externalRefTable.putOrReplace(externalReferenceIdentifier, externalReferenceRecord);
		}
	}

	@And("remove external reference if exists:")
	public void remove_external_reference_if_exists(
			@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			removeExternalReferenceIfExists(tableRow);
		}
	}

	private void removeExternalReferenceIfExists(
			@NonNull final Map<String, String> tableRow)
	{
		final String externalSystem = DataTableUtil.extractStringForColumnName(tableRow, I_S_ExternalReference.COLUMNNAME_ExternalSystem);
		final String externalReference = DataTableUtil.extractStringForColumnName(tableRow, I_S_ExternalReference.COLUMNNAME_ExternalReference);
		final String referenceType = DataTableUtil.extractStringForColumnName(tableRow, I_S_ExternalReference.COLUMNNAME_Type);

		Services.get(IQueryBL.class)
				.createQueryBuilder(I_S_ExternalReference.class)
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalReference, externalReference)
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalSystem, externalSystem)
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_Type, referenceType)
				.create()
				.delete();
	}
}