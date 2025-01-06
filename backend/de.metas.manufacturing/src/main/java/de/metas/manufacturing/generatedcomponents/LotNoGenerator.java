/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2024 metas GmbH
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
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

public class LotNoGenerator implements IComponentGenerator
{
	private static final String FIXED_PREFIX = "1";
	private static final String PP_ORDER_INDICATOR = "M";
	private static final String SYSCONFIG_DATE_FORMAT = "de.metas.document.seqNo.DateSequenceProvider.dateFormat";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

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

		final String serialNo = generateAndIncrementSerialNo();

		return ImmutableAttributeSet.builder()
				.attributeValue(AttributeConstants.ATTR_SerialNo, serialNo)
				.build();
	}

	@NonNull
	private String generateAndIncrementSerialNo()
	{
		final String dateFormatToUse = sysConfigBL.getValue(SYSCONFIG_DATE_FORMAT);
		Check.assumeNotEmpty(dateFormatToUse, "{} sysconfig has not been defined", SYSCONFIG_DATE_FORMAT);

		final String lastDigits = TimeUtil.formatDate(Env.getDate(), dateFormatToUse);

		return FIXED_PREFIX + PP_ORDER_INDICATOR + lastDigits;
	}

	@Override
	public ComponentGeneratorParams getDefaultParameters()
	{
		return ComponentGeneratorParams.EMPTY;
	}
}
