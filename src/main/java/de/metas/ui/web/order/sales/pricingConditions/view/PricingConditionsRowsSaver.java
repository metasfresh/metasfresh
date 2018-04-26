package de.metas.ui.web.order.sales.pricingConditions.view;

import static org.adempiere.model.InterfaceWrapperHelper.copyValues;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.X_M_DiscountSchemaBreak;

import lombok.Builder;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PricingConditionsRowsSaver
{
	private final PricingConditionsRow row;

	@Builder
	private PricingConditionsRowsSaver(final PricingConditionsRow row)
	{
		this.row = row;
	}

	public int save()
	{
		if (!row.isEditable())
		{
			throw new AdempiereException("Saving not editable rows is not allowed")
					.setParameter("row", row);
		}

		final int discountSchemaId = row.getDiscountSchemaId();
		if (discountSchemaId <= 0)
		{
			throw new AdempiereException("Cannot save row because no discount schema was defined"); // TODO trl
		}

		final I_M_DiscountSchemaBreak schemaBreak;
		if (row.getDiscountSchemaBreakId() > 0)
		{
			schemaBreak = load(row.getDiscountSchemaBreakId(), I_M_DiscountSchemaBreak.class);
			throw new AdempiereException("Saving an existing discount schema break is not supported");
		}
		else
		{
			schemaBreak = newInstance(I_M_DiscountSchemaBreak.class);
		}

		if (row.getCopiedFromDiscountSchemaBreakId() > 0)
		{
			final I_M_DiscountSchemaBreak fromSchemaBreak = load(row.getCopiedFromDiscountSchemaBreakId(), I_M_DiscountSchemaBreak.class);
			copyValues(fromSchemaBreak, schemaBreak);
		}

		schemaBreak.setM_DiscountSchema_ID(discountSchemaId);
		schemaBreak.setC_PaymentTerm_ID(row.getPaymentTermId());
		schemaBreak.setBreakDiscount(row.getDiscount());
		updatePrice(schemaBreak, row.getPrice());
		InterfaceWrapperHelper.save(schemaBreak);

		return schemaBreak.getM_DiscountSchemaBreak_ID();
	}

	private void updatePrice(final I_M_DiscountSchemaBreak schemaBreak, final Price price)
	{
		final PriceType priceType = price.getPriceType();
		if (priceType == PriceType.NONE)
		{
			schemaBreak.setIsPriceOverride(false);
			schemaBreak.setPriceBase(null);
			schemaBreak.setBase_PricingSystem_ID(-1);
			schemaBreak.setStd_AddAmt(BigDecimal.ZERO);
			schemaBreak.setPriceStd(BigDecimal.ZERO);
		}
		else if (priceType == PriceType.BASE_PRICING_SYSTEM)
		{
			schemaBreak.setIsPriceOverride(true);
			schemaBreak.setPriceBase(X_M_DiscountSchemaBreak.PRICEBASE_PricingSystem);
			schemaBreak.setBase_PricingSystem_ID(price.getPricingSystemId());
			schemaBreak.setStd_AddAmt(price.getBasePriceAddAmt());
			schemaBreak.setPriceStd(BigDecimal.ZERO);
		}
		else if (priceType == PriceType.FIXED_PRICED)
		{
			schemaBreak.setIsPriceOverride(true);
			schemaBreak.setPriceBase(X_M_DiscountSchemaBreak.PRICEBASE_Fixed);
			schemaBreak.setBase_PricingSystem_ID(-1);
			schemaBreak.setStd_AddAmt(BigDecimal.ZERO);
			schemaBreak.setPriceStd(price.getPriceValue());
		}
		else
		{
			throw new AdempiereException("Unknown priceType: " + priceType);
		}
	}
}
