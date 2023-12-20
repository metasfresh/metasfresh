/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.rest_api.v2.product;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.product.ProductCategoryId;
import de.metas.rest_api.utils.JsonCreatedUpdatedInfo;
import de.metas.rest_api.v2.product.response.JsonGetProductCategoriesResponse;
import de.metas.rest_api.v2.product.response.JsonProductCategory;
import de.metas.sectionCode.SectionCodeRepository;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product_Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SnapshotExtension.class)
public class ProductCategoriesRestControllerTest
{
	private ProductCategoriesRestController restController;

	private JsonCreatedUpdatedInfo createdUpdatedInfo;
	private Expect expect;

	@BeforeAll
	static void initStatic()
	{
		LogManager.setLoggerLevel(ProductCategoriesRestController.class, Level.ALL);
	}

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		createMasterData();

		final SectionCodeRepository sectionCodeRepository = new SectionCodeRepository();

		final ProductsServicesFacade productsServicesFacade = new ProductsServicesFacade(sectionCodeRepository)
		{
			@Override
			public JsonCreatedUpdatedInfo extractCreatedUpdatedInfo(final I_M_Product_Category record)
			{
				return createdUpdatedInfo;
			}
		};

		restController = new ProductCategoriesRestController(productsServicesFacade);
	}

	private void createMasterData()
	{
		createdUpdatedInfo = JsonCreatedUpdatedInfo.builder()
				.created(LocalDate.of(2019, Month.SEPTEMBER, 5).atTime(3, 20, 14, 500).atZone(ZoneId.of("Europe/Berlin")))
				.createdBy(UserId.METASFRESH)
				.updated(LocalDate.of(2019, Month.SEPTEMBER, 5).atTime(3, 21, 22, 501).atZone(ZoneId.of("Europe/Berlin")))
				.updatedBy(UserId.METASFRESH)
				.build();
	}

	@Test
	public void getProductCategories_standardCase()
	{
		//
		// Masterdata
		final ProductCategoryId categoryId1 = prepareProductCategory()
				.value("value1")
				.name("name1")
				.description("description1")
				.defaultCategory(true)
				.build();
		final ProductCategoryId categoryId2 = prepareProductCategory()
				.value("value2")
				.name("name2")
				.description("description2")
				.parentId(categoryId1)
				.build();

		//
		// Call endpoint
		final ResponseEntity<JsonGetProductCategoriesResponse> response = restController.getProductCategories();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		//
		// Expectations
		final JsonGetProductCategoriesResponse responseBody = response.getBody();
		assertThat(responseBody)
				.isEqualTo(JsonGetProductCategoriesResponse.builder()
						.productCategory(JsonProductCategory.builder()
								.id(categoryId1)
								.value("value1")
								.name("name1")
								.description("description1")
								.createdUpdatedInfo(createdUpdatedInfo)
								.defaultCategory(true)
								.build())
						.productCategory(JsonProductCategory.builder()
								.id(categoryId2)
								.value("value2")
								.name("name2")
								.description("description2")
								.createdUpdatedInfo(createdUpdatedInfo)
								.parentProductCategoryId(categoryId1)
								.build())
						.build());

		//
		expect.serializer("orderedJson").toMatchSnapshot(responseBody);
	}

	@Builder(builderMethodName = "prepareProductCategory", builderClassName = "prepareProductCategoryBuilder")
	private ProductCategoryId createProductCategory(
			@NonNull final String value,
			@NonNull final String name,
			final String description,
			final ProductCategoryId parentId,
			final boolean defaultCategory)
	{
		final I_M_Product_Category record = newInstance(I_M_Product_Category.class);
		record.setValue(value);
		record.setName(name);
		record.setDescription(description);
		record.setM_Product_Category_Parent_ID(ProductCategoryId.toRepoId(parentId));
		record.setIsDefault(defaultCategory);

		saveRecord(record);
		return ProductCategoryId.ofRepoId(record.getM_Product_Category_ID());
	}
}
