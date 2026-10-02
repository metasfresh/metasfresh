package de.metas.order.compensationGroup;

import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.product.ProductCategoryId;
import de.metas.util.Services;
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

import java.math.BigDecimal;
import java.util.Optional;

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
	private static final int TAKE_OVER_ID = 540123;
	private static final int TAKE_OVER_CATEGORY_ID = 777;

	private I_C_Order order;
	private int productId;
	private int uomId;
	private int orderCompensationGroupId;
	private GroupId groupId;
	private OrderGroupRepository repo;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);
		uomId = uom.getC_UOM_ID();

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(uomId);
		saveRecord(product);
		productId = product.getM_Product_ID();

		order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(1);
		saveRecord(order);

		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		saveRecord(groupHeader);
		orderCompensationGroupId = groupHeader.getC_Order_CompensationGroup_ID();
		groupId = OrderGroupRepository.createGroupId(OrderId.ofRepoId(order.getC_Order_ID()), orderCompensationGroupId);

		final I_M_Product regularProduct = newInstance(I_M_Product.class);
		regularProduct.setC_UOM_ID(uomId);
		final I_M_Product_Category regularCategory = newInstance(I_M_Product_Category.class);
		saveRecord(regularCategory);
		regularProduct.setM_Product_Category_ID(regularCategory.getM_Product_Category_ID());
		saveRecord(regularProduct);
		final I_C_OrderLine regularLine = newInstance(I_C_OrderLine.class);
		regularLine.setC_Order_ID(order.getC_Order_ID());
		regularLine.setM_Product_ID(regularProduct.getM_Product_ID());
		regularLine.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		regularLine.setLineNetAmt(new BigDecimal("100"));
		saveRecord(regularLine);

		Services.registerService(IOrderLineBL.class, new OrderGroupRepositoryTest.StubOrderLineBL(order));

		final TakeOverCategoryProvider provider = takeOverId -> takeOverId == TAKE_OVER_ID
				? Optional.of(ProductCategoryId.ofRepoId(TAKE_OVER_CATEGORY_ID))
				: Optional.empty();
		repo = new OrderGroupRepository(
				Mockito.mock(GroupCompensationLineCreateRequestFactory.class),
				Optional.empty(),
				Optional.of(provider));
	}

	@Test
	void ownLine_withTakeOverId_resolvesCategoryFromTakeOverRecord()
	{
		saveCompensationLine(0, TAKE_OVER_ID);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines()).hasSize(1);
		assertThat(group.getCompensationLines().get(0).getAppliesToProductCategoryId())
				.isEqualTo(ProductCategoryId.ofRepoId(TAKE_OVER_CATEGORY_ID));
	}

	@Test
	void schemaBackedLine_keepsSchemaLineCategory_evenIfTakeOverIdIsSet()
	{
		final de.metas.order.model.I_C_CompensationGroup_SchemaLine schemaLine = newInstance(de.metas.order.model.I_C_CompensationGroup_SchemaLine.class);
		schemaLine.setM_Product_Category_ID(888);
		schemaLine.setM_Product_ID(productId);
		saveRecord(schemaLine);
		saveCompensationLine(schemaLine.getC_CompensationGroup_SchemaLine_ID(), TAKE_OVER_ID);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines().get(0).getAppliesToProductCategoryId())
				.isEqualTo(ProductCategoryId.ofRepoId(888));
	}

	@Test
	void ownLine_withoutTakeOverId_hasNoCategory()
	{
		saveCompensationLine(0, 0);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines().get(0).getAppliesToProductCategoryId()).isNull();
	}

	private void saveCompensationLine(final int schemaLineId, final int takeOverId)
	{
		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		line.setM_Product_ID(productId);
		line.setC_UOM_ID(uomId);
		line.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		line.setIsGroupCompensationLine(true);
		line.setC_CompensationGroup_SchemaLine_ID(schemaLineId);
		line.setC_CompensationGroup_ContractSettings_TakeOver_ID(takeOverId);
		line.setGroupCompensationType(X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount);
		line.setGroupCompensationAmtType(X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent);
		line.setGroupCompensationPercentage(BigDecimal.TEN);
		line.setQtyEntered(BigDecimal.ONE);
		line.setPriceEntered(BigDecimal.ZERO);
		line.setLineNetAmt(BigDecimal.ZERO);
		saveRecord(line);
	}
}
