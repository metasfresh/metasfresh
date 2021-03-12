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

import com.google.common.collect.ImmutableMap;
import de.metas.bpartner_product.BPartnerProduct;
import de.metas.common.product.JsonRequestBPartnerProductUpsert;
import de.metas.common.product.JsonRequestProduct;
import de.metas.common.product.JsonRequestProductUpsert;
import de.metas.common.product.JsonRequestProductUpsertItem;
import de.metas.common.rest_api.JsonMetasfreshId;
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
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class UpsertProduct_StepDef
{
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final JsonRequestProductUpsert.JsonRequestProductUpsertBuilder jsonRequestProductUpsertBuilder = JsonRequestProductUpsert.builder();
	private JsonRequestBPartnerProductUpsert jsonRequestBPartnerProductUpsert;
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

	@And("no product with value {string} exists")
	public void noProductWithCodeCodeExists(final String value)

	{
		final Optional<I_M_Product> product = Services.get(IQueryBL.class).createQueryBuilder(I_M_Product.class)
				.addEqualsFilter(I_M_Product.COLUMNNAME_Value, value)
				.create()
				.firstOnlyOptional(I_M_Product.class);

		if (product.isPresent())
		{
			Services.get(IQueryBL.class).createQueryBuilder(I_C_BPartner_Product.class)
					.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, product.get().getM_Product_ID())
					.create()
					.delete();

			Services.get(IQueryBL.class).createQueryBuilder(I_M_Product.class)
					.addEqualsFilter(I_M_Product.COLUMNNAME_Value, value)
					.create()
					.delete();
		}
	}

	@Then("verify if data is persisted correctly for productValue {string}")
	public void verifyIfDataIsPersistedCorrectlyForProductCodeCode(final String value)
	{
		final I_M_Product record = productDAO.retrieveProductByValue(value);
		final JsonRequestProductUpsertItem requestProductUpsert = jsonRequestProductUpsertBuilder.build().getRequestItems().get(0);
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

		final List<BPartnerProduct> bPartnerProducts = productRepository.getByProductId(ProductId.ofRepoId(record.getM_Product_ID()));

		assertThat(bPartnerProducts).isNotNull();
		assertThat(bPartnerProducts).isNotEmpty();

		final BPartnerProduct bPartnerProduct = bPartnerProducts.get(0);

		assertThat(bPartnerProduct).isNotNull();
		assertThat(bPartnerProduct.getActive()).isTrue();
		assertThat(bPartnerProduct.getProductNo()).isEqualTo(jsonRequestBPartnerProductUpsert.getProductNo());
		assertThat(bPartnerProduct.getSeqNo()).isEqualTo(jsonRequestBPartnerProductUpsert.getSeqNo());
		assertThat(bPartnerProduct.getCuEAN()).isEqualTo(jsonRequestBPartnerProductUpsert.getCuEAN());
		assertThat(bPartnerProduct.getGtin()).isEqualTo(jsonRequestBPartnerProductUpsert.getGtin());
		assertThat(bPartnerProduct.getCustomerLabelName()).isEqualTo(jsonRequestBPartnerProductUpsert.getCustomerLabelName());
		assertThat(bPartnerProduct.getIngredients()).isEqualTo(jsonRequestBPartnerProductUpsert.getIngredients());

	}

	@Given("the user adds product")
	public void theUserAddsProduct(@NonNull final DataTable dataTable)
	{
		final Map<String, String> dataTableEntries = dataTable.asMaps().get(0);
		final JsonRequestProductUpsertItem product = mapProductRequestItem(dataTableEntries);
		jsonRequestProductUpsertBuilder.requestItem(product);

	}

	@Given("the user adds bpartner product")
	public void theUserAddsBpartnerProduct(@NonNull final DataTable dataTable)
	{
		final Map<String, String> dataTableEntries = dataTable.asMaps().get(0);
		jsonRequestBPartnerProductUpsert = mapBpartnerProductRequestItem(dataTableEntries);

	}

	@Given("the user adds external reference")
	public void theUserAddsBpartnerExternalReference(@NonNull final DataTable dataTable)
	{
		final Map<String, String> dataTableEntries = dataTable.asMaps().get(0);

		final String externalSystemName = DataTableUtil.extractStringForColumnName(dataTableEntries, "systemname");
		final String externalId = DataTableUtil.extractStringForColumnName(dataTableEntries, "externalId");
		final IExternalReferenceType externalReferenceType = getExternalReferenceType(DataTableUtil.extractStringForColumnName(dataTableEntries, "externalReferenceType"));

		final JsonMetasfreshId metasfreshId;
		if (externalReferenceType.equals(BPartnerExternalReferenceType.BPARTNER))
		{
			final IQueryOrderBy orderBy =
					queryBL.createQueryOrderByBuilder(I_C_BPartner.class)
							.addColumn(I_C_BPartner.COLUMN_C_BPartner_ID)
							.createQueryOrderBy();

			metasfreshId = JsonMetasfreshId.of(queryBL.createQueryBuilder(I_C_BPartner.class)
													   .addOnlyActiveRecordsFilter()
													   .create()
													   .setOrderBy(orderBy)
													   .first()
													   .getC_BPartner_ID());
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
		final String code = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.Code");
		final String name = DataTableUtil.extractStringForColumnName(dataTableEntries, "Name");
		final String type = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "Type");
		final String UOMCode = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "UOMCode");
		final String ean = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.EAN");
		final String gtin = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.GTIN");
		final String description = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.Description");
		final boolean isActive = DataTableUtil.extractBooleanForColumnName(dataTableEntries, "OPT.isActive");

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
		requestProduct.setBpartnerProductItems(Collections.singletonList(jsonRequestBPartnerProductUpsert));

		return JsonRequestProductUpsertItem.builder()
				.productIdentifier(productIdentifier)
				.requestProduct(requestProduct)
				.build();

	}

	@And("the request is set in context of syncAdvise {string}")
	public void theRequestIsSetInContextOfSyncAdvise(final String syncAdvise) throws JsonProcessingException
	{
		final JsonRequestProductUpsert jsonRequestProductUpsert =
				jsonRequestProductUpsertBuilder
						.syncAdvise(RESTUtil.mapSyncAdvise(syncAdvise))
						.build();

		testContext.setRequestPayload(new ObjectMapper().writeValueAsString(jsonRequestProductUpsert));
	}

	@And("verify if data is persisted correctly for type {string} and external reference {string}")
	public void verifyIfDataIsPersistedCorrectlyForProductExternalReference(final String type, final String identifier)
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

		final ImmutableMap<ExternalReferenceQuery, ExternalReference> externalReferences = externalReferenceRepository.getExternalReferences(querySet);

		assertThat(externalReferences).isNotNull();
		assertThat(externalReferences).isNotEmpty();
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

