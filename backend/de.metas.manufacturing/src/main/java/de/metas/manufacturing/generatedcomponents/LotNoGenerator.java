/*
 * #%L
 * de.metas.manufacturing
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

package de.metas.manufacturing.generatedcomponents;

import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

public class LotNoGenerator implements IComponentGenerator
{
	public static final String FIXED_PREFIX = "1";
	public static final String MATERIAL_RECEIPT_INDICATOR = "R";
	public static final String PP_ORDER_INDICATOR = "M";
	public static final String YEAR_AND_MONTH_PATTERN = "YYMM";

	@Override
	public ImmutableAttributeSet generate(@NonNull final ComponentGeneratorContext context)
	{
		final ImmutableAttributeSet existingAttributes = context.getExistingAttributes();
		if (!existingAttributes.hasAttribute(AttributeConstants.ATTR_LotNumber))
		{
			return ImmutableAttributeSet.EMPTY;
		}

		if (!context.isOverrideExistingValues()
				&& Check.isNotBlank(existingAttributes.getValueAsString(AttributeConstants.ATTR_LotNumber)))
		{
			// don't override existing serial no
			return ImmutableAttributeSet.EMPTY;
		}

		final String serialNo = generateAndIncrementSerialNo(false,context.getClientId());

		return ImmutableAttributeSet.builder()
				.attributeValue(AttributeConstants.ATTR_SerialNo, serialNo)
				.build();
	}

	@NonNull
	private String generateAndIncrementSerialNo(final boolean isMaterialReceipt, @NonNull final ClientId clientId)
	{
		final String materialReceiptIndicator = isMaterialReceipt ? MATERIAL_RECEIPT_INDICATOR : PP_ORDER_INDICATOR;
		final String last2DigitsOfYear =TimeUtil.formatDate(Env.getDate(), YEAR_AND_MONTH_PATTERN);

		final String prefixToUse = FIXED_PREFIX + materialReceiptIndicator + last2DigitsOfYear;

		return prefixToUse;
	}

	@Override
	public ComponentGeneratorParams getDefaultParameters()
	{
		return ComponentGeneratorParams.EMPTY;
	}
}
