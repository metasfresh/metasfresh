package de.metas.order.compensationGroup;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.Collection;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_OrderLine;
import org.compiere.util.Util;

import de.metas.product.IProductBL;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

public final class GroupCreator
{
	private final transient IProductBL productBL = Services.get(IProductBL.class);
	private final GroupRepository groupsRepo;

	private Collection<Integer> lineIdsToGroup;
	private int compensationProductId = -1;

	/* package */ GroupCreator(@NonNull final GroupRepository groupsRepo)
	{
		this.groupsRepo = groupsRepo;
	}

	public GroupCreator linesToGroup(@NonNull final Collection<Integer> lineIdsToGroup)
	{
		Check.assumeNotEmpty(lineIdsToGroup, "lineIdsToGroup is not empty");
		this.lineIdsToGroup = lineIdsToGroup;
		return this;
	}

	public GroupCreator compensationProductId(final int compensationProductId)
	{
		this.compensationProductId = compensationProductId;
		return this;
	}

	private int getCompensationProductId()
	{
		return compensationProductId;
	}

	public void createGroup()
	{
		Check.assume(compensationProductId > 0, "compensationProductId > 0");

		final Group group = groupsRepo.retrieveOrCreateGroupFromLineIds(lineIdsToGroup);
		group.addNewCompensationLine(createGroupCompensationLineCreateRequest());
		groupsRepo.saveGroup(group);
	}

	private GroupCompensationLineCreateRequest createGroupCompensationLineCreateRequest()
	{
		final I_M_Product product = load(getCompensationProductId(), I_M_Product.class);
		final I_C_UOM uom = productBL.getStockingUOM(product);

		return GroupCompensationLineCreateRequest.builder()
				.productId(product.getM_Product_ID())
				.uomId(uom.getC_UOM_ID())
				.type(extractGroupCompensationType(product))
				.amtType(extractGroupCompensationAmtType(product))
				.percentage(BigDecimal.ZERO)
				.qty(BigDecimal.ZERO)
				.price(BigDecimal.ZERO)
				.build();
	}

	private static final GroupCompensationType extractGroupCompensationType(final I_M_Product product)
	{
		return GroupCompensationType.ofAD_Ref_List_Value(Util.coalesce(product.getGroupCompensationType(), X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount));
	}

	private static final GroupCompensationAmtType extractGroupCompensationAmtType(final I_M_Product product)
	{
		return GroupCompensationAmtType.ofAD_Ref_List_Value(Util.coalesce(product.getGroupCompensationAmtType(), X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent));
	}

}
