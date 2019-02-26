package de.metas.order.impl;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.service.OrgId;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLinePricingConditions;
import de.metas.util.Check;
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
	private static final String MSG_NoPricingConditionsError = "de.metas.order.NoPricingConditionsError";

	private static enum HasPricingConditions
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

		final int colorId = getColorId(
				hasPricingConditions,
				ClientId.ofRepoId(orderLine.getAD_Client_ID()),
				OrgId.ofRepoIdOrAny(orderLine.getAD_Org_ID()));

		orderLine.setNoPriceConditionsColor_ID(colorId);
	}

	private int getColorId(
			@NonNull final HasPricingConditions hasPricingConditions,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		if (hasPricingConditions == HasPricingConditions.YES)
		{
			return -1;
		}
		else if (hasPricingConditions == HasPricingConditions.TEMPORARY)
		{
			return getTemporaryPriceConditionsColorId(clientId, orgId);
		}
		else if (hasPricingConditions == HasPricingConditions.NO)
		{
			return getNoPriceConditionsColorId(clientId, orgId);
		}
		else
		{
			throw new AdempiereException("Unknown " + HasPricingConditions.class + ": " + hasPricingConditions);
		}
	}

	@Override
	public int getTemporaryPriceConditionsColorId(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		return getColorIdBySysConfig(SYSCONFIG_TemporaryPriceConditionsColorName, clientId, orgId);
	}

	private int getNoPriceConditionsColorId(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		return getColorIdBySysConfig(SYSCONFIG_NoPriceConditionsColorName, clientId, orgId);
	}

	private int getColorIdBySysConfig(
			@NonNull final String sysConfigName,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final String colorName = sysConfigBL.getValue(
				sysConfigName,
				"-",
				ClientId.toRepoId(clientId),
				OrgId.toRepoId(orgId));

		if (Check.isEmpty(colorName) || "-".equals(colorName))
		{
			return -1;
		}
		return Services.get(IColorRepository.class).getColorIdByName(colorName);
	}

	@Override
	public void failForMissingPricingConditions(final de.metas.adempiere.model.I_C_Order order)
	{
		final boolean mandatoryPricingConditions = isMandatoryPricingConditions(
				ClientId.ofRepoId(order.getAD_Client_ID()),
				OrgId.ofRepoIdOrAny(order.getAD_Org_ID()));

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

	private boolean isMandatoryPricingConditions(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		final int noPriceConditionsColorId = getNoPriceConditionsColorId(clientId, orgId);
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
