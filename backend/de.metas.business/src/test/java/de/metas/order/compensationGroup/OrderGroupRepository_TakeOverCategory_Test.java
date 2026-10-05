package de.metas.order.compensationGroup;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.contracts.compensationGroup.contract.ContractSettingsTakeOverId;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.model.I_C_CompensationGroup_SchemaLine;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

/** A compensation line without a schema line but with a take-over id resolves its applies-to category from the take-over record. */
public class OrderGroupRepository_TakeOverCategory_Test
{
	private static final ContractSettingsTakeOverId TAKE_OVER_ID = ContractSettingsTakeOverId.ofRepoId(540123);
	private static final ContractSettingsTakeOverId OTHER_TAKE_OVER_ID = ContractSettingsTakeOverId.ofRepoId(540124);
	private static final ProductCategoryId TAKE_OVER_CATEGORY_ID = ProductCategoryId.ofRepoId(777);
	private static final ProductCategoryId OTHER_TAKE_OVER_CATEGORY_ID = ProductCategoryId.ofRepoId(778);
	private static final ProductCategoryId SCHEMA_LINE_CATEGORY_ID = ProductCategoryId.ofRepoId(888);
	private static final ProductCategoryId OTHER_SCHEMA_LINE_CATEGORY_ID = ProductCategoryId.ofRepoId(889);

	private I_C_Order order;
	private ProductId productId;
	private UomId uomId;
	private GroupId groupId;
	private OrderGroupRepository repo;
	private GroupTemplateRepository groupTemplateRepository;
	private final List<Set<ContractSettingsTakeOverId>> takeOverCategoryProviderCalls = new ArrayList<>();
	private ContractSettingsTakeOverCategoryProvider provider;
	private IQueryBL queryBLSpy;
	private ProductId regularProductId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		queryBLSpy = Mockito.spy(Services.get(IQueryBL.class));
		Services.registerService(IQueryBL.class, queryBLSpy); // before any repository or DAO picks up IQueryBL

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(uomId.getRepoId());
		saveRecord(product);
		productId = ProductId.ofRepoId(product.getM_Product_ID());

		order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(1);
		saveRecord(order);

		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		saveRecord(groupHeader);
		groupId = OrderGroupRepository.createGroupId(OrderId.ofRepoId(order.getC_Order_ID()), groupHeader.getC_Order_CompensationGroup_ID());

		final I_M_Product regularProduct = newInstance(I_M_Product.class);
		regularProduct.setC_UOM_ID(uomId.getRepoId());
		final I_M_Product_Category regularCategory = newInstance(I_M_Product_Category.class);
		saveRecord(regularCategory);
		regularProduct.setM_Product_Category_ID(regularCategory.getM_Product_Category_ID());
		saveRecord(regularProduct);
		regularProductId = ProductId.ofRepoId(regularProduct.getM_Product_ID());
		final I_C_OrderLine regularLine = newInstance(I_C_OrderLine.class);
		regularLine.setC_Order_ID(order.getC_Order_ID());
		regularLine.setM_Product_ID(regularProduct.getM_Product_ID());
		regularLine.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
		regularLine.setLineNetAmt(new BigDecimal("100"));
		saveRecord(regularLine);

		Services.registerService(IOrderLineBL.class, new OrderGroupRepositoryTest.StubOrderLineBL(order));

		final ImmutableMap<ContractSettingsTakeOverId, ProductCategoryId> categoryIdsByTakeOverId = ImmutableMap.of(
				TAKE_OVER_ID, TAKE_OVER_CATEGORY_ID,
				OTHER_TAKE_OVER_ID, OTHER_TAKE_OVER_CATEGORY_ID);
		provider = takeOverIds -> {
			takeOverCategoryProviderCalls.add(ImmutableSet.copyOf(takeOverIds));
			return ImmutableMap.copyOf(Maps.filterKeys(categoryIdsByTakeOverId, takeOverIds::contains));
		};
		groupTemplateRepository = Mockito.spy(GroupTemplateRepository.newInstanceForUnitTesting());
		repo = new OrderGroupRepository(
				Mockito.mock(GroupCompensationLineCreateRequestFactory.class),
				Optional.empty(),
				groupTemplateRepository,
				Optional.of(provider));
	}

	@Test
	void ownLine_withTakeOverId_resolvesCategoryFromTakeOverRecord()
	{
		saveCompensationLine(null, TAKE_OVER_ID);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines()).hasSize(1);
		assertThat(group.getCompensationLines().get(0).getAppliesToProductCategoryId())
				.isEqualTo(TAKE_OVER_CATEGORY_ID);
	}

	@Test
	void schemaBackedLine_keepsSchemaLineCategory_evenIfTakeOverIdIsSet()
	{
		final GroupTemplateLineId schemaLineId = createSchemaLine(SCHEMA_LINE_CATEGORY_ID);
		saveCompensationLine(schemaLineId, TAKE_OVER_ID);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines().get(0).getAppliesToProductCategoryId())
				.isEqualTo(SCHEMA_LINE_CATEGORY_ID);
	}

	@Test
	void severalLines_resolveCategoriesWithOneBulkLoadPerSource()
	{
		final GroupTemplateLineId schemaLineId1 = createSchemaLine(SCHEMA_LINE_CATEGORY_ID);
		final GroupTemplateLineId schemaLineId2 = createSchemaLine(OTHER_SCHEMA_LINE_CATEGORY_ID);
		saveCompensationLine(schemaLineId1, null);
		saveCompensationLine(schemaLineId2, null);
		saveCompensationLine(null, TAKE_OVER_ID);
		saveCompensationLine(null, OTHER_TAKE_OVER_ID);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines())
				.extracting(GroupCompensationLine::getAppliesToProductCategoryId)
				.containsExactlyInAnyOrder(
						SCHEMA_LINE_CATEGORY_ID,
						OTHER_SCHEMA_LINE_CATEGORY_ID,
						TAKE_OVER_CATEGORY_ID,
						OTHER_TAKE_OVER_CATEGORY_ID);
		Mockito.verify(groupTemplateRepository, Mockito.times(1)).getAppliesToProductCategoryIds(ImmutableSet.of(schemaLineId1, schemaLineId2));
		assertThat(takeOverCategoryProviderCalls).containsExactly(ImmutableSet.of(TAKE_OVER_ID, OTHER_TAKE_OVER_ID));
	}

	@Test
	void ownLine_withoutTakeOverId_hasNoCategory()
	{
		saveCompensationLine(null, null);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines().get(0).getAppliesToProductCategoryId()).isNull();
	}

	@Test
	void retrieveContractCreatedGroupsByOrderId_loadsAnyNumberOfGroupsWithTheSameQueries()
	{
		final OrderId orderWithOneGroupId = createOrderWithContractCreatedGroups(1);
		final OrderId orderWithThreeGroupsId = createOrderWithContractCreatedGroups(3);

		Mockito.clearInvocations(queryBLSpy, groupTemplateRepository);
		takeOverCategoryProviderCalls.clear();

		assertThat(repo.retrieveContractCreatedGroupsByOrderId(orderWithOneGroupId)).hasSize(1);
		final long queriesForOneGroup = countCreatedQueryBuilders();

		Mockito.clearInvocations(queryBLSpy, groupTemplateRepository);
		takeOverCategoryProviderCalls.clear();

		final List<Group> groups = repo.retrieveContractCreatedGroupsByOrderId(orderWithThreeGroupsId);

		assertThat(groups).hasSize(3);
		assertThat(groups).allSatisfy(group -> assertThat(group.getCompensationLines())
				.extracting(GroupCompensationLine::getAppliesToProductCategoryId)
				.containsExactly(SCHEMA_LINE_CATEGORY_ID, TAKE_OVER_CATEGORY_ID));
		assertThat(countCreatedQueryBuilders()).isEqualTo(queriesForOneGroup);
		Mockito.verify(groupTemplateRepository, Mockito.times(1)).getAppliesToProductCategoryIds(Mockito.any());
		assertThat(takeOverCategoryProviderCalls).hasSize(1);
	}

	private long countCreatedQueryBuilders()
	{
		return Mockito.mockingDetails(queryBLSpy).getInvocations().stream()
				.filter(invocation -> invocation.getMethod().getName().startsWith("createQueryBuilder"))
				.count();
	}

	private OrderId createOrderWithContractCreatedGroups(final int groupsCount)
	{
		final I_C_Order contractOrder = newInstance(I_C_Order.class);
		contractOrder.setC_BPartner_ID(1);
		saveRecord(contractOrder);
		final OrderId contractOrderId = OrderId.ofRepoId(contractOrder.getC_Order_ID());

		final GroupTemplateLineId schemaLineId = createSchemaLine(SCHEMA_LINE_CATEGORY_ID);
		for (int i = 0; i < groupsCount; i++)
		{
			final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
			groupHeader.setC_Order_ID(contractOrderId.getRepoId());
			groupHeader.setC_Flatrate_Term_ID(1);
			saveRecord(groupHeader);
			final GroupId contractGroupId = OrderGroupRepository.createGroupId(contractOrderId, groupHeader.getC_Order_CompensationGroup_ID());

			final I_C_OrderLine regularLine = newInstance(I_C_OrderLine.class);
			regularLine.setC_Order_ID(contractOrderId.getRepoId());
			regularLine.setM_Product_ID(regularProductId.getRepoId());
			regularLine.setC_Order_CompensationGroup_ID(contractGroupId.getOrderCompensationGroupId());
			regularLine.setLine(10);
			regularLine.setLineNetAmt(new BigDecimal("100"));
			saveRecord(regularLine);

			saveCompensationLine(contractGroupId, schemaLineId, null, 20);
			saveCompensationLine(contractGroupId, null, TAKE_OVER_ID, 30);
		}
		return contractOrderId;
	}

	private GroupTemplateLineId createSchemaLine(final ProductCategoryId productCategoryId)
	{
		final I_C_CompensationGroup_SchemaLine schemaLine = newInstance(I_C_CompensationGroup_SchemaLine.class);
		schemaLine.setM_Product_Category_ID(productCategoryId.getRepoId());
		schemaLine.setM_Product_ID(productId.getRepoId());
		saveRecord(schemaLine);
		return GroupTemplateLineId.ofRepoId(schemaLine.getC_CompensationGroup_SchemaLine_ID());
	}

	private void saveCompensationLine(@Nullable final GroupTemplateLineId schemaLineId, @Nullable final ContractSettingsTakeOverId takeOverId)
	{
		saveCompensationLine(groupId, schemaLineId, takeOverId, 0);
	}

	private void saveCompensationLine(
			final GroupId groupId,
			@Nullable final GroupTemplateLineId schemaLineId,
			@Nullable final ContractSettingsTakeOverId takeOverId,
			final int lineNo)
	{
		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(OrderGroupRepository.extractOrderIdFromGroupId(groupId).getRepoId());
		line.setLine(lineNo);
		line.setM_Product_ID(productId.getRepoId());
		line.setC_UOM_ID(uomId.getRepoId());
		line.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
		line.setIsGroupCompensationLine(true);
		line.setC_CompensationGroup_SchemaLine_ID(GroupTemplateLineId.toRepoId(schemaLineId));
		line.setC_CompensationGroup_ContractSettings_TakeOver_ID(ContractSettingsTakeOverId.toRepoId(takeOverId));
		line.setGroupCompensationType(X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount);
		line.setGroupCompensationAmtType(X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent);
		line.setGroupCompensationPercentage(BigDecimal.TEN);
		line.setQtyEntered(BigDecimal.ONE);
		line.setPriceEntered(BigDecimal.ZERO);
		line.setLineNetAmt(BigDecimal.ZERO);
		saveRecord(line);
	}
}
