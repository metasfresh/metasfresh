package de.metas.order.impl;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLinePricingConditions;
import de.metas.util.IColorRepository;

/*
 * #%L
 * de.metas.business
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

public class OrderLinePricingConditions implements IOrderLinePricingConditions
{
	private static final String SYSCONFIG_NoPriceConditionsColorName = "de.metas.order.NoPriceConditionsColorName";
	private static final String SYSCONFIG_TemporaryPriceConditionsColorName = "de.metas.order.TemporaryPriceConditionsColorName";
	private static final String MSG_NoPricingConditionsError = "de.metas.order.NoPricingConditionsError";

	private static enum HasPricingConditions
	{
		NO, YES, TEMPORARY
	}

	@Override
	public void updateNoPriceConditionsColor(final I_C_OrderLine orderLine)
	{
		final HasPricingConditions hasPricingConditions = hasPricingConditions(orderLine);
		final int colorId = getColorId(hasPricingConditions);
		orderLine.setNoPriceConditionsColor_ID(colorId);
	}

	private int getColorId(final HasPricingConditions hasPricingConditions)
	{
		if (hasPricingConditions == HasPricingConditions.YES)
		{
			return -1;
		}
		else if (hasPricingConditions == HasPricingConditions.TEMPORARY)
		{
			return getTemporaryPriceConditionsColorId();
		}
		else if (hasPricingConditions == HasPricingConditions.NO)
		{
			return getNoPriceConditionsColorId();
		}
		else
		{
			throw new AdempiereException("Unknown " + HasPricingConditions.class + ": " + hasPricingConditions);
		}
	}

	private int getTemporaryPriceConditionsColorId()
	{
		return getColorIdBySysConfig(SYSCONFIG_TemporaryPriceConditionsColorName);
	}

	private int getNoPriceConditionsColorId()
	{
		return getColorIdBySysConfig(SYSCONFIG_NoPriceConditionsColorName);
	}

	private int getColorIdBySysConfig(final String sysConfigName)
	{
		final String colorName = Services.get(ISysConfigBL.class).getValue(sysConfigName, "-");
		if (Check.isEmpty(colorName) || "-".equals(colorName))
		{
			return -1;
		}
		return Services.get(IColorRepository.class).getColorIdByName(colorName);
	}

	@Override
	public void failForMissingPricingConditions(final de.metas.adempiere.model.I_C_Order order)
	{
		final boolean mandatoryPricingConditions = isMandatoryPricingConditions();
		if (!mandatoryPricingConditions)
		{
			return;
		}

		final List<I_C_OrderLine> orderLines = Services.get(IOrderDAO.class).retrieveOrderLines(order);
		final boolean existsOrderLineWithNoPricingConditions = orderLines
				.stream()
				.anyMatch(this::isPricingConditionsMissing);

		if (existsOrderLineWithNoPricingConditions)
		{
			final ITranslatableString translatableMsg = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_NoPricingConditionsError);
			throw new AdempiereException(translatableMsg.translate(Env.getAD_Language()));
		}
	}

	private boolean isMandatoryPricingConditions()
	{
		final int noPriceConditionsColorId = getNoPriceConditionsColorId();
		return noPriceConditionsColorId > 0;
	}

	private boolean isPricingConditionsMissing(final I_C_OrderLine orderLine)
	{
		return hasPricingConditions(orderLine) == HasPricingConditions.NO;
	}

	private HasPricingConditions hasPricingConditions(final I_C_OrderLine orderLine)
	{
		if (orderLine.isTempPricingConditions())
		{
			return HasPricingConditions.TEMPORARY;
		}
		else if (orderLine.getM_DiscountSchemaBreak_ID() > 0)
		{
			return HasPricingConditions.YES;
		}
		else
		{
			return HasPricingConditions.NO;
		}
	}
}
