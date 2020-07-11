/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.ordersimport.compudata;

import java.util.ArrayList;
import java.util.List;

import de.metas.edi.esb.ordersimport.AbstractEDIOrdersBean;
import de.metas.edi.esb.ordersimport.OrderEDI;
import de.metas.edi.esb.ordersimport.OrderHeader;
import de.metas.edi.esb.ordersimport.OrderLine;
import lombok.NonNull;

public class CompudataEDIOrdersBean extends AbstractEDIOrdersBean
{

	@Override
	protected List<OrderEDI> convertToOrderEDIs(@NonNull final List<Object> ediLines)
	{
		final List<OrderEDI> ediDocuments = new ArrayList<OrderEDI>();

		OrderEDI orderEDITmp = null;
		OrderHeader orderHeaderTmp = null;
		OrderLine orderLineTmp = null;
		for (final Object ediLine : ediLines)
		{
			// treat the EDI header
			if (ediLine instanceof H000)
			{
				orderEDITmp = new OrderEDI((H000)ediLine);
				ediDocuments.add(orderEDITmp);
			}

			// each H100 line is an OrderHeader
			if (ediLine instanceof H100)
			{
				orderHeaderTmp = new OrderHeader((H100)ediLine);
				orderEDITmp.addOrderHeader(orderHeaderTmp);
			}

			if (ediLine instanceof H110)
			{
				orderHeaderTmp.addH110((H110)ediLine);
			}

			if (ediLine instanceof H120)
			{
				orderHeaderTmp.addH120((H120)ediLine);
			}

			if (ediLine instanceof H130)
			{
				orderHeaderTmp.addH130((H130)ediLine);
			}

			if (ediLine instanceof P100)
			{
				orderLineTmp = new OrderLine((P100)ediLine);
				orderHeaderTmp.addOrderLine(orderLineTmp);
			}

			if (ediLine instanceof P110)
			{
				orderLineTmp.addP110((P110)ediLine);
			}

			if (ediLine instanceof T100)
			{
				orderHeaderTmp.addT100((T100)ediLine);
			}
		}

		return ediDocuments;
	}

}
