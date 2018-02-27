package de.metas.order.compensationGroup;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.Collection;

import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.spi.impl.rules.Discount;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_OrderLine;
import org.compiere.util.Util;

import de.metas.order.compensationGroup.GroupRepository.RetrieveOrCreateGroupRequest;
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
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);

	private final GroupRepository groupsRepo;

	private Collection<Integer> lineIdsToGroup;
	private GroupTemplate newGroupTemplate;

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

	public GroupCreator groupTemplate(final GroupTemplate newGroupTemplate)
	{
		this.newGroupTemplate = newGroupTemplate;
		return this;
	}

	public Group createGroup()
	{
		final Group group = groupsRepo.retrieveOrCreateGroup(createRetrieveOrCreateGroupRequest());
		return recreateGroup(group);
	}
	
	public Group recreateGroup(@NonNull final Group group)
	{
		group.removeAllCompensationLines();

		newGroupTemplate.getLines()
				.stream()
				.filter(templateLine -> templateLine.getGroupMatcher().test(group))
				.map(templateLine -> createGroupCompensationLineCreateRequest(templateLine, group))
				.forEach(group::addNewCompensationLine);

		groupsRepo.saveGroup(group);
		return group;
	}

	private RetrieveOrCreateGroupRequest createRetrieveOrCreateGroupRequest()
	{
		return RetrieveOrCreateGroupRequest.builder()
				.orderLineIds(lineIdsToGroup)
				.newGroupTemplate(newGroupTemplate)
				.build();
	}

	private GroupCompensationLineCreateRequest createGroupCompensationLineCreateRequest(@NonNull final GroupTemplateLine templateLine, @NonNull final Group group)
	{
		final I_M_Product product = load(templateLine.getProductId(), I_M_Product.class);
		final I_C_UOM uom = productBL.getStockingUOM(product);

		final GroupCompensationType type = extractGroupCompensationType(product);
		final GroupCompensationAmtType amtType = extractGroupCompensationAmtType(product);

		BigDecimal percentage = BigDecimal.ZERO;
		if (GroupCompensationType.Discount.equals(type) && GroupCompensationAmtType.Percent.equals(amtType))
		{
			percentage = calculateDefaultDiscountPercentage(templateLine, group);
		}

		return GroupCompensationLineCreateRequest.builder()
				.productId(product.getM_Product_ID())
				.uomId(uom.getC_UOM_ID())
				.type(type)
				.amtType(amtType)
				.percentage(percentage)
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

	private BigDecimal calculateDefaultDiscountPercentage(final GroupTemplateLine templateLine, final Group group)
	{
		if (templateLine.getPercentage() != null)
		{
			return templateLine.getPercentage();
		}

		return retrieveDiscountPercentageFromPricing(templateLine, group);
	}

	private final BigDecimal retrieveDiscountPercentageFromPricing(final GroupTemplateLine templateLine, final Group group)
	{
		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		pricingCtx.setM_Product_ID(templateLine.getProductId());
		pricingCtx.setC_BPartner_ID(group.getBpartnerId());
		pricingCtx.setSOTrx(group.isSOTrx());
		pricingCtx.setDisallowDiscount(false);// just to be sure
		pricingCtx.setQty(BigDecimal.ONE);

		final IPricingResult pricingResult = pricingBL.createInitialResult(pricingCtx);
		pricingResult.setCalculated(true); // important, else the Discount rule does not react
		pricingResult.setPriceStd(group.getTotalNetAmt());

		final Discount discountRule = new Discount();
		discountRule.calculate(pricingCtx, pricingResult);

		return pricingResult.getDiscount();
	}

}
