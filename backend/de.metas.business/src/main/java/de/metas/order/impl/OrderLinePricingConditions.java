package de.metas.order.impl;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLinePricingConditions;
import de.metas.util.Check;
import de.metas.util.ColorId;
import de.metas.util.IColorRepository;
import de.metas.util.Services;
import lombok.NonNull;

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
	private static final AdMessageKey MSG_NoPricingConditionsError = AdMessageKey.of("de.metas.order.NoPricingConditionsError");

	private enum HasPricingConditions
	{
		NO,

		YES,

		/** The respective order line has no {@code M_DiscountSchemaBreak_ID}, but manual discount etc. */
		TEMPORARY
	}

	@Override
	public void updateNoPriceConditionsColor(@NonNull final I_C_OrderLine orderLine)
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

	@Override
	public int getTemporaryPriceConditionsColorId()
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
		final ColorId colorId = Services.get(IColorRepository.class).getColorIdByName(colorName);
		return ColorId.toRepoId(colorId);
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
				.anyMatch(this::isPricingConditionsMissingButRequired);

		if (existsOrderLineWithNoPricingConditions)
		{
			final ITranslatableString translatableMsg = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_NoPricingConditionsError);
			throw new AdempiereException(translatableMsg)
					.setParameter("HowToDisablePricingConditionsCheck", "To disable it, please set " + SYSCONFIG_NoPriceConditionsColorName + " to `-`");
		}
	}

	private boolean isMandatoryPricingConditions()
	{
		final int noPriceConditionsColorId = getNoPriceConditionsColorId();
		return noPriceConditionsColorId > 0;
	}

	private boolean isPricingConditionsMissingButRequired(final I_C_OrderLine orderLine)
	{
		// Pricing conditions are not required for packing material line (task 3925)
		if (orderLine.isPackagingMaterial())
		{
			return false;
		}

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
