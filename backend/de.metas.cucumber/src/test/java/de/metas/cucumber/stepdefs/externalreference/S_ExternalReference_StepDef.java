/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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
import de.metas.common.externalreference.v2.JsonExternalReferenceItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupResponse;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.M_Shipper_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.ExternalUserReferenceType;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.IExternalSystem;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.productcategory.ProductCategoryExternalReferenceType;
import de.metas.externalreference.shipper.ShipperExternalReferenceType;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.externalreference.model.I_S_ExternalReference.COLUMNNAME_S_ExternalReference_ID;
import static de.metas.externalreference.model.X_S_ExternalReference.TYPE_Bpartner;
import static de.metas.externalreference.model.X_S_ExternalReference.TYPE_Product;
import static de.metas.externalreference.model.X_S_ExternalReference.TYPE_ProductCategory;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID;
import static org.compiere.model.I_M_Shipper.COLUMNNAME_M_Shipper_ID;

public class S_ExternalReference_StepDef
{
	private final OrgId defaultOrgId = OrgId.ofRepoId(1000000);

	private final AD_User_StepDefData userTable;
	private final S_ExternalReference_StepDefData externalRefTable;
	private final M_Shipper_StepDefData shipperTable;
	private final M_Product_StepDefData productTable;
	private final C_BPartner_StepDefData bpartnerTable;

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
			@NonNull final TestContext testContext)
	{
		this.externalSystems = externalSystems;
		this.userTable = userTable;
		this.externalRefTable = externalRefTable;
		this.shipperTable = shipperTable;
		this.productTable = productTable;
		this.bpartnerTable = bpartnerTable;
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
			final String externalSystem = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "ExternalSystem");
			final String type = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "Type");
			final String externalReference = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "ExternalReference");
			final String externalReferenceURL = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "ExternalReferenceURL");

			final boolean externalRefExists = queryBL.createQueryBuilder(I_S_ExternalReference.class)
					.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalSystem, externalSystem)
					.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_Type, type)
					.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalReference, externalReference)
					.addEqualsFilter(I_S_ExternalReference.COLUMN_ExternalReferenceURL, externalReferenceURL)
					.create()
					.anyMatch();

			assertThat(externalRefExists).isTrue();
		}
	}

	@And("metasfresh contains S_ExternalReference:")
	public void add_S_ExternalReference(@NonNull final DataTable dataTable)
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
	public void process_external_reference_lookup_endpoint_response(@NonNull final DataTable table) throws JsonProcessingException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final JsonExternalReferenceLookupResponse jsonExternalReferenceLookupResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonExternalReferenceLookupResponse.class);
		assertThat(jsonExternalReferenceLookupResponse).isNotNull();
		assertThat(jsonExternalReferenceLookupResponse.getItems()).isNotEmpty();

		final List<JsonExternalReferenceItem> referenceItems = jsonExternalReferenceLookupResponse.getItems();
		final List<Map<String, String>> rows = table.asMaps();

		assertThat(referenceItems.size()).isEqualTo(rows.size());

		for (final Map<String, String> row : rows)
		{
			final String expectedExternalReference = DataTableUtil.extractStringForColumnName(row, I_S_ExternalReference.COLUMNNAME_ExternalReference);

			final JsonExternalReferenceItem item = Check.singleElement(referenceItems
																			   .stream()
																			   .filter(referenceItem -> referenceItem.getLookupItem().getId().equals(expectedExternalReference))
																			   .collect(ImmutableList.toImmutableList()));

			final String externalReferenceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_S_ExternalReference_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_S_ExternalReference externalReference = InterfaceWrapperHelper.load(item.getExternalReferenceId().getValue(), I_S_ExternalReference.class);

			assertThat(externalReference).isNotNull();

			externalRefTable.put(externalReferenceIdentifier, externalReference);
		}
	}

	@And("the following S_ExternalReference is changed:")
	public void change_S_ExternalReference(@NonNull final DataTable dataTable)
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


	@And("metasfresh contains S_ExternalReferences:")
	public void metasfresh_contains_s_external_reference(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createS_ExternalReference(tableRow);
		}
	}

	@And("remove external reference if exists:")
	public void remove_external_reference_if_exists(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			removeExternalReferenceIfExists(tableRow);
		}
	}

	@Given("metasfresh contains S_ExternalReferences")
	public void theUserAddsBpartnerExternalReference(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> dataTableEntries = dataTable.asMaps();

		final IQueryOrderBy orderBy =
				queryBL.createQueryOrderByBuilder(I_C_BPartner.class)
						.addColumn(I_C_BPartner.COLUMN_C_BPartner_ID)
						.createQueryOrderBy();

		final List<JsonMetasfreshId> bPartnerIds = queryBL.createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.setOrderBy(orderBy)
				.list()
				.stream()
				.map(bPartner -> JsonMetasfreshId.of(bPartner.getC_BPartner_ID())).collect(Collectors.toList());

		for (final Map<String, String> dataTableEntry : dataTableEntries)
		{
			final String externalSystemName = DataTableUtil.extractStringForColumnName(dataTableEntry, I_S_ExternalReference.COLUMNNAME_ExternalSystem);
			final String externalId = DataTableUtil.extractStringForColumnName(dataTableEntry, I_S_ExternalReference.COLUMNNAME_ExternalReference);
			final IExternalReferenceType externalReferenceType = getExternalReferenceType(DataTableUtil.extractStringForColumnName(dataTableEntry, I_S_ExternalReference.COLUMNNAME_Type));

			final JsonMetasfreshId metasfreshId;
			if (externalReferenceType.equals(BPartnerExternalReferenceType.BPARTNER))
			{
				metasfreshId = bPartnerIds.get(dataTableEntries.indexOf(dataTableEntry));
			}
			else
			{
				throw new AdempiereException("No implementation for external reference type.");
			}

			final IExternalSystem externalSystem = externalSystems.ofCode(externalSystemName)
					.orElseThrow(() -> new InvalidIdentifierException("systemName", externalSystemName));

			final ExternalReference externalReference = ExternalReference.builder()
					.orgId(ORG_ID)
					.externalSystem(externalSystem)
					.externalReference(externalId)
					.externalReferenceType(externalReferenceType)
					.recordId(metasfreshId.getValue())
					.build();

			externalReferenceRepository.save(externalReference);
		}
	}

	private void createS_ExternalReference(@NonNull final Map<String, String> tableRow)
	{
		final String externalSystemCode = DataTableUtil.extractStringForColumnName(tableRow, "ExternalSystem.Code");
		final String externalId = DataTableUtil.extractStringForColumnName(tableRow, "ExternalReference");
		final String referenceType = DataTableUtil.extractStringForColumnName(tableRow, "ExternalReferenceType.Code");
		final String recordIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "RecordId.Identifier");

		final IExternalSystem externalSystem = externalSystems.ofCode(externalSystemCode)
				.orElseThrow(() -> new InvalidIdentifierException("systemName", externalSystemCode));

		final IExternalReferenceType externalReferenceType = externalReferenceTypes.ofCode(referenceType)
				.orElseThrow(() -> new InvalidIdentifierException("externalReferenceType", externalSystemCode));

		final int recordId;

		if (externalReferenceType.equals(ProductExternalReferenceType.PRODUCT))
		{
			final I_M_Product product = productTable.get(recordIdentifier);
			recordId = product.getM_Product_ID();
		}
		else if (externalReferenceType.equals(BPartnerExternalReferenceType.BPARTNER))
		{
			final I_C_BPartner bPartner = bpartnerTable.get(recordIdentifier);
			recordId = bPartner.getC_BPartner_ID();
		}
		else
		{
			throw new RuntimeException("External reference type not covered by this step def!");
		}

		final ExternalReference externalReference = ExternalReference.builder()
				.orgId(defaultOrgId)
				.externalSystem(externalSystem)
				.externalReference(externalId)
				.externalReferenceType(externalReferenceType)
				.recordId(recordId)
				.build();

		externalReferenceRepository.save(externalReference);
	}

	private void removeExternalReferenceIfExists(@NonNull final Map<String, String> tableRow)
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

	private IExternalReferenceType getExternalReferenceType(final String type)
	{
		switch (type)
		{
			case TYPE_Product:
				return ProductExternalReferenceType.PRODUCT;
			case TYPE_Bpartner:
				return BPartnerExternalReferenceType.BPARTNER;
			case TYPE_ProductCategory:
				return ProductCategoryExternalReferenceType.PRODUCT_CATEGORY;
			default:
				throw new AdempiereException("Bad external reference type: " + type);
		}
	}
}