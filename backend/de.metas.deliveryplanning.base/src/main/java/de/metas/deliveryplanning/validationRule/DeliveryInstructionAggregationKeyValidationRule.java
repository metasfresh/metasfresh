/*
 * #%L
 * de.metas.deliveryplanning.base
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

package de.metas.deliveryplanning.validationRule;

import com.google.common.collect.ImmutableSet;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import org.adempiere.ad.validationRule.AbstractJavaValidationRule;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.model.InterfaceWrapperHelper;
import lombok.NonNull;
import org.compiere.util.NamePair;

import javax.annotation.Nullable;

import java.util.Set;

/**
 * Offers only the delivery instructions whose header agrees with the selected plannings on every field the
 * header can hold one of. The direction, the draft state and the document type are filtered in SQL by the
 * sibling rule; this one covers the remaining seven fields, whose values arrive as the hidden process
 * parameters the selection is condensed into.
 * <p>
 * In Java rather than in the SQL rule for one reason: an EMPTY value has to count as a value, so "both empty"
 * matches and "one empty" does not. That is what {@code DeliveryPlanningList.aggregationKeyViolations()} does
 * when it judges the selection, and the picker has to agree with it — offering a target the subsequent
 * admissibility check would refuse is the defect this rule exists to prevent.
 */
public class DeliveryInstructionAggregationKeyValidationRule extends AbstractJavaValidationRule
{
	private static final ImmutableSet<String> PARAMETERS = ImmutableSet.of(
			I_M_ShipperTransportation.COLUMNNAME_AD_Org_ID,
			I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID,
			I_M_ShipperTransportation.COLUMNNAME_C_Incoterms_ID,
			I_M_ShipperTransportation.COLUMNNAME_IncotermLocation,
			I_M_ShipperTransportation.COLUMNNAME_M_MeansOfTransportation_ID,
			I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Loading_ID,
			I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Delivery_ID);

	@Override
	public Set<String> getParameters(@Nullable final String contextTableName)
	{
		return PARAMETERS;
	}

	@Override
	public boolean accept(final IValidationContext evalCtx, final NamePair item)
	{
		// no context to judge against: leave the list as the SQL rule produced it rather than emptying it
		if (evalCtx == IValidationContext.NULL || evalCtx == IValidationContext.DISABLED)
		{
			return true;
		}
		if (item == null)
		{
			return false;
		}

		final int deliveryInstructionId = StringUtils.toIntegerOrZero(item.getID());
		if (deliveryInstructionId <= 0)
		{
			return false;
		}

		final I_M_ShipperTransportation deliveryInstruction = InterfaceWrapperHelper
				.load(deliveryInstructionId, I_M_ShipperTransportation.class);
		if (deliveryInstruction == null)
		{
			return false;
		}

		return idMatches(evalCtx, I_M_ShipperTransportation.COLUMNNAME_AD_Org_ID, deliveryInstruction.getAD_Org_ID())
				&& idMatches(evalCtx, I_M_ShipperTransportation.COLUMNNAME_M_Shipper_ID, deliveryInstruction.getM_Shipper_ID())
				&& idMatches(evalCtx, I_M_ShipperTransportation.COLUMNNAME_C_Incoterms_ID, deliveryInstruction.getC_Incoterms_ID())
				&& idMatches(evalCtx, I_M_ShipperTransportation.COLUMNNAME_M_MeansOfTransportation_ID, deliveryInstruction.getM_MeansOfTransportation_ID())
				&& idMatches(evalCtx, I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Loading_ID, deliveryInstruction.getC_BPartner_Location_Loading_ID())
				&& idMatches(evalCtx, I_M_ShipperTransportation.COLUMNNAME_C_BPartner_Location_Delivery_ID, deliveryInstruction.getC_BPartner_Location_Delivery_ID())
				&& stringMatches(evalCtx, I_M_ShipperTransportation.COLUMNNAME_IncotermLocation, deliveryInstruction.getIncotermLocation());
	}

	/**
	 * An id field. Absent, zero and negative all mean "not set" and are one value, so an instruction that has no
	 * forwarder matches a selection that has none either, and never matches one that has.
	 */
	private static boolean idMatches(
			@NonNull final IValidationContext evalCtx,
			final String columnName,
			final int deliveryInstructionValue)
	{
		final int selectionValue = StringUtils.toIntegerOrZero(evalCtx.get_ValueAsString(columnName));
		return normalizeId(selectionValue) == normalizeId(deliveryInstructionValue);
	}

	private static int normalizeId(final int id)
	{
		return id > 0 ? id : 0;
	}

	/** A text field. Null and blank are one value, for the same reason. */
	private static boolean stringMatches(
			@NonNull final IValidationContext evalCtx,
			final String columnName,
			@Nullable final String deliveryInstructionValue)
	{
		return normalizeString(evalCtx.get_ValueAsString(columnName))
				.equals(normalizeString(deliveryInstructionValue));
	}

	private static String normalizeString(@Nullable final String value)
	{
		return Check.isBlank(value) ? "" : value.trim();
	}
}
