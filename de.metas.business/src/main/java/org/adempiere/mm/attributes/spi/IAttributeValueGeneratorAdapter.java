package org.adempiere.mm.attributes.spi;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General default License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General default License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IAttributeValueGeneratorAdapter extends IAttributeValueGenerator
{
	@Override
	default String getAttributeValueType()
	{
		return null;
	}

	@Override
	default boolean canGenerateValue(final Properties ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		return false;
	}

	@Override
	default String generateStringValue(final Properties ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	default BigDecimal generateNumericValue(final Properties ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	default Date generateDateValue(final Properties ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	default I_M_AttributeValue generateAttributeValue(final Properties ctx, final int tableId, final int recordId, final boolean isSOTrx, final String trxName)
	{
		throw new UnsupportedOperationException("Not supported");
	}
}
