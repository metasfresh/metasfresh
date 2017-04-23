package de.metas.ui.web.window.descriptor.factory.standard;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.IDocumentFieldValueProvider;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public final class HARDCODED_OrderDocumentSummaryValueProvider implements IDocumentFieldValueProvider
{
	public static final transient HARDCODED_OrderDocumentSummaryValueProvider instance = new HARDCODED_OrderDocumentSummaryValueProvider();

	private static final Set<String> PARAMETERS = ImmutableSet.of(
			I_C_Order.COLUMNNAME_DocumentNo,
			I_C_Order.COLUMNNAME_C_BPartner_ID,
			I_C_Order.COLUMNNAME_DateOrdered,
			I_C_Order.COLUMNNAME_GrandTotal);

	private HARDCODED_OrderDocumentSummaryValueProvider()
	{
		super();
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return PARAMETERS;
	}

	@Override
	public Object calculateValue(final Document document)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(document, I_C_Order.class);

		final SimpleDateFormat dateFormat = DisplayType.getDateFormat(DisplayType.Date);
		final DecimalFormat numberFormat = DisplayType.getNumberFormat(DisplayType.Amount);

		final StringBuilder result = new StringBuilder();

		final String documentNo = order.getDocumentNo();
		result.append(documentNo);

		final I_C_BPartner bpartner = order.getC_BPartner();
		if (bpartner != null)
		{
			result.append(" ").append(bpartner.getName());
		}

		final Date dateOrdered = order.getDateOrdered();
		if (dateOrdered != null)
		{
			result.append(" ").append(dateFormat.format(dateOrdered));
		}

		final BigDecimal grandTotal = order.getGrandTotal();
		if (grandTotal != null)
		{
			result.append(" ").append(numberFormat.format(grandTotal));
		}

		return result.toString();
	}
}
