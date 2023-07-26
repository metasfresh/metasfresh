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

package de.metas.cucumber.stepdefs.product;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.BPartnerProduct;
import de.metas.common.product.v2.request.JsonRequestBPartnerProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProduct;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.RESTUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceQuery;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.IExternalSystem;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.productcategory.ProductCategoryExternalReferenceType;
import de.metas.externalreference.rest.ExternalReferenceRestControllerService;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.product.ProductType;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import de.metas.util.collections.MultiValueMap;
import de.metas.util.web.exception.InvalidIdentifierException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class UpsertProduct_StepDef
{
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final MultiValueMap<String, JsonRequestBPartnerProductUpsert> bPartnerProductsByProductCode = new MultiValueMap<>();
	private final Map<String, JsonRequestProductUpsertItem> productsByProductCode = new HashMap<>();

	private final ProductCategoryId defaultProductCategoryId = ProductCategoryId.ofRepoId(1000000);
	private final OrgId defaultOrgId = OrgId.ofRepoId(1000000);

	private final TestContext testContext;
	private final ExternalReferenceRestControllerService externalReferenceRestControllerService;
	private final ExternalReferenceRepository externalReferenceRepository;
	private final ProductRepository productRepository;
	private final ExternalSystems externalSystems;

	public UpsertProduct_StepDef(final TestContext testContext, final ExternalSystems externalSystems)
	{
		this.testContext = testContext;
		this.externalSystems = externalSystems;
		productRepository = SpringContextHolder.instance.getBean(ProductRepository.class);
		externalReferenceRepository = SpringContextHolder.instance.getBean(ExternalReferenceRepository.class);
		this.externalReferenceRestControllerService = SpringContextHolder.instance.getBean(ExternalReferenceRestControllerService.class);
	}

	@And("no product external reference with value {string} exists")
	public void noProductExternalReferenceWithValueExists(final String externalReferenceValue)
	{
		Services.get(IQueryBL.class).createQueryBuilder(I_S_ExternalReference.class)
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalReference, externalReferenceValue)
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_Type, ProductExternalReferenceType.PRODUCT.getCode())
				.create()
				.delete();
	}

	@And("no bpartner external reference with value {string} exists")
	public void noBpartnerExternalReferenceWithValueExists(final String externalReferenceValue)
	{
		Services.get(IQueryBL.class).createQueryBuilder(I_S_ExternalReference.class)
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalReference, externalReferenceValue)
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_Type, BPartnerExternalReferenceType.BPARTNER.getCode())
				.create()
				.delete();
	}

	@Then("verify if data is persisted correctly for each product")
	public void verifyIfDataIsPersistedCorrectlyForProductCodeCode()
	{
		productsByProductCode.keySet().forEach(productCode -> {
			final I_M_Product record = productDAO.retrieveProductByValue(productCode);
			final JsonRequestProductUpsertItem requestProductUpsert = productsByProductCode.get(productCode);
			final JsonRequestProduct jsonRequestProduct = requestProductUpsert.getRequestProduct();

			assertThat(record).isNotNull();
			assertThat(record.isActive()).isEqualTo(jsonRequestProduct.getActive());
			assertThat(record.getName()).isEqualTo(jsonRequestProduct.getName());
			assertThat(ProductType.ofCode(record.getProductType()).toString().toLowerCase()).isEqualTo(jsonRequestProduct.getType().toString().toLowerCase());

			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(jsonRequestProduct.getUomCode()));

			assertThat(record.getC_UOM_ID()).isEqualTo(uomId.getRepoId());
			assertThat(record.getUPC()).isEqualTo(jsonRequestProduct.getEan());
			assertThat(record.getGTIN()).isEqualTo(jsonRequestProduct.getGtin());
			assertThat(record.getDescription()).isEqualTo(jsonRequestProduct.getDescription());
			assertThat(record.getM_Product_Category_ID()).isEqualTo(defaultProductCategoryId.getRepoId());
			assertThat(record.isDiscontinued()).isEqualTo(jsonRequestProduct.getDiscontinued());
			assertThat(record.isStocked()).isEqualTo(jsonRequestProduct.getStocked());

			final Map<BPartnerId, BPartnerProduct> bPartnerProducts = productRepository
					.getByProductId(ProductId.ofRepoId(record.getM_Product_ID()))
					.stream()
					.collect(Collectors.toMap(BPartnerProduct::getBPartnerId, bPartnerProduct -> bPartnerProduct));

			assertThat(bPartnerProducts).isNotNull();
			assertThat(bPartnerProducts).isNotEmpty();

			final List<JsonRequestBPartnerProductUpsert> bPartnerProductUpsertList = bPartnerProductsByProductCode.get(productCode);

			bPartnerProductUpsertList.forEach(jsonRequestBPartnerProductUpsert -> {

				final ExternalReference externalReference =
						getExternalReference("bpartner", jsonRequestBPartnerProductUpsert.getBpartnerIdentifier());

				final BPartnerProduct bPartnerProduct = bPartnerProducts.get(BPartnerId.ofRepoId(externalReference.getRecordId()));

				assertThat(bPartnerProduct).isNotNull();
				assertThat(bPartnerProduct.getActive()).isTrue();
				assertThat(bPartnerProduct.getProductNo()).isEqualTo(jsonRequestBPartnerProductUpsert.getProductNo());
				assertThat(bPartnerProduct.getSeqNo()).isEqualTo(jsonRequestBPartnerProductUpsert.getSeqNo());
				assertThat(bPartnerProduct.getCuEAN()).isEqualTo(jsonRequestBPartnerProductUpsert.getCuEAN());
				assertThat(bPartnerProduct.getGtin()).isEqualTo(jsonRequestBPartnerProductUpsert.getGtin());
				assertThat(bPartnerProduct.getCustomerLabelName()).isEqualTo(jsonRequestBPartnerProductUpsert.getCustomerLabelName());
				assertThat(bPartnerProduct.getIngredients()).isEqualTo(jsonRequestBPartnerProductUpsert.getIngredients());
			});
		});

	}

	@Given("the user adds JsonRequestProductUpsertItems")
	public void theUserAddsProduct(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> dataTableEntries = dataTable.asMaps();

		dataTableEntries.forEach(dataTableEntry -> {
			final String productCode = DataTableUtil.extractStringForColumnName(dataTableEntry, "Code");
			productsByProductCode.put(productCode, mapProductRequestItem(dataTableEntry));
		});
	}

	@Given("the user adds JsonRequestBpartnerProductUpsertItems")
	public void theUserAddsBpartnerProduct(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> dataTableEntries = dataTable.asMaps();

		dataTableEntries.forEach(dataTableEntry -> {
			final String productCode = DataTableUtil.extractStringForColumnName(dataTableEntry, "ProductCode");
			bPartnerProductsByProductCode.add(productCode, mapBpartnerProductRequestItem(dataTableEntry));
		});
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

		dataTableEntries.forEach(dataTableEntry -> {
			final String externalSystemName = DataTableUtil.extractStringForColumnName(dataTableEntry, "ExternalSystem.Code");
			final String externalId = DataTableUtil.extractStringForColumnName(dataTableEntry, "ExternalReference");
			final IExternalReferenceType externalReferenceType = getExternalReferenceType(DataTableUtil.extractStringForColumnName(dataTableEntry, "ExternalReferenceType.Code"));

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
					.orgId(defaultOrgId)
					.externalSystem(externalSystem)
					.externalReference(externalId)
					.externalReferenceType(externalReferenceType)
					.recordId(metasfreshId.getValue())
					.build();

			externalReferenceRepository.save(externalReference);
		});

	}

	private JsonRequestBPartnerProductUpsert mapBpartnerProductRequestItem(final Map<String, String> dataTableEntries)
	{
		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(dataTableEntries, "bpartnerIdentifier");
		final boolean isActive = DataTableUtil.extractBooleanForColumnName(dataTableEntries, "OPT.isActive");
		final Integer seqNo = DataTableUtil.extractIntForColumnName(dataTableEntries, "OPT.seqNo");
		final String productNo = DataTableUtil.extractStringForColumnName(dataTableEntries, "OPT.ProductNo");
		final String description = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.Description");
		final String ean = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.EAN");
		final String gtin = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.GTIN");

		final String customerLabelName = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.customerLabelName");
		final String ingredients = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.ingredients");

		final JsonRequestBPartnerProductUpsert requestBPartnerProductUpsert = new JsonRequestBPartnerProductUpsert();
		requestBPartnerProductUpsert.setBpartnerIdentifier(bpartnerIdentifier);
		requestBPartnerProductUpsert.setActive(isActive);
		requestBPartnerProductUpsert.setSeqNo(seqNo);
		requestBPartnerProductUpsert.setProductNo(productNo);
		requestBPartnerProductUpsert.setDescription(description);
		requestBPartnerProductUpsert.setCuEAN(ean);
		requestBPartnerProductUpsert.setGtin(gtin);
		requestBPartnerProductUpsert.setDropShip(true);
		requestBPartnerProductUpsert.setCustomerLabelName(customerLabelName);
		requestBPartnerProductUpsert.setIngredients(ingredients);

		return requestBPartnerProductUpsert;

	}

	private JsonRequestProductUpsertItem mapProductRequestItem(final Map<String, String> dataTableEntries)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(dataTableEntries, "productIdentifier");
		final String code = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "Code");
		final String name = DataTableUtil.extractStringForColumnName(dataTableEntries, "Name");
		final String type = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "Type");
		final String UOMCode = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "uomCode");
		final String ean = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.EAN");
		final String gtin = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.GTIN");
		final String description = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.Description");
		final boolean isActive = DataTableUtil.extractBooleanForColumnName(dataTableEntries, "OPT.isActive");
		final String externalReferenceURL = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.ExternalReferenceURL");

		final JsonRequestProduct requestProduct = new JsonRequestProduct();
		requestProduct.setCode(code);
		requestProduct.setName(name);
		requestProduct.setType(JsonRequestProduct.Type.ITEM.name().equals(type) ? JsonRequestProduct.Type.ITEM : JsonRequestProduct.Type.SERVICE);
		requestProduct.setUomCode(UOMCode);
		requestProduct.setEan(ean);
		requestProduct.setGtin(gtin);
		requestProduct.setDescription(description);
		requestProduct.setActive(isActive);
		requestProduct.setDiscontinued(false);
		requestProduct.setStocked(true);
		requestProduct.setBpartnerProductItems(bPartnerProductsByProductCode.get(code));

		return JsonRequestProductUpsertItem.builder()
				.productIdentifier(productIdentifier)
				.externalReferenceUrl(externalReferenceURL)
				.requestProduct(requestProduct)
				.build();

	}

	@And("we create a JsonRequestProductUpsert, set syncAdvise to {string}, add the JsonRequestProductUpsertItems and store it in the test context")
	public void theRequestIsSetInContextOfSyncAdvise(final String syncAdvise) throws JsonProcessingException
	{
		final JsonRequestProductUpsert jsonRequestProductUpsert =
				JsonRequestProductUpsert
						.builder()
						.requestItems(productsByProductCode.values())
						.syncAdvise(RESTUtil.mapSyncAdvise(syncAdvise))
						.build();

		testContext.setRequestPayload(new ObjectMapper().writeValueAsString(jsonRequestProductUpsert));
	}

	@And("verify if data is persisted correctly for type {string} and external reference {string}")
	public void verifyIfDataIsPersistedCorrectlyForProductExternalReference(final String type, final String identifier)
	{

		final ExternalReference externalReference = getExternalReference(type, identifier);

		assertThat(externalReference).isNotNull();
	}

	private ExternalReference getExternalReference(final String type, final String identifier)
	{
		final ExternalIdentifier productIdentifier = ExternalIdentifier.of(identifier);

		final IExternalSystem externalSystem = externalSystems.ofCode(productIdentifier.asExternalValueAndSystem().getExternalSystem())
				.orElseThrow(() -> new InvalidIdentifierException("systemName", identifier));

		final ExternalReferenceQuery externalReferenceQuery = ExternalReferenceQuery.builder()
				.externalReferenceType(getExternalReferenceType(type))
				.externalReference(productIdentifier.asExternalValueAndSystem().getValue())
				.externalSystem(externalSystem)
				.orgId(defaultOrgId)
				.build();

		final Set<ExternalReferenceQuery> querySet = new HashSet<>();
		querySet.add(externalReferenceQuery);

		return externalReferenceRepository.getExternalReferences(querySet).get(externalReferenceQuery);
	}

	private IExternalReferenceType getExternalReferenceType(final String type)
	{
		switch (type)
		{
			case "product":
				return ProductExternalReferenceType.PRODUCT;
			case "bpartner":
				return BPartnerExternalReferenceType.BPARTNER;
			case "product category":
				return ProductCategoryExternalReferenceType.PRODUCT_CATEGORY;
			default:
				throw new AdempiereException("Bad external reference type: " + type);
		}
	}
}

