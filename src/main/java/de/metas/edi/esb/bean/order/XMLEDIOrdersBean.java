/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.edi.esb.bean.order;

import de.metas.edi.esb.pojo.order.*;
import de.metas.edi.esb.pojo.order.compudata.H000;
import de.metas.edi.esb.pojo.order.compudata.H100;
import de.metas.edi.esb.pojo.order.compudata.P100;
import de.metas.edi.esb.pojo.order.qualifier.*;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class XMLEDIOrdersBean extends AbstractEDIOrdersBean
{

	@Override protected List<OrderEDI> getEDIDocumentObjects(List<Object> orders)
	{
		final List<OrderEDI> ediDocuments = new ArrayList<>();

		OrderEDI orderEDI = new OrderEDI(new H000());
		ediDocuments.add(orderEDI);
		Document ordersDocument = (Document)orders.get(0);
		for (final Xbest4H order : ordersDocument.getXbest4H())
		{
			HEADERXbest header = order.getHEADER();
			H100 h100 = mapToH100(header);

			OrderHeader orderHeader = new OrderHeader(h100);
			orderEDI.addOrderHeader(orderHeader);
			for (DETAILXbest detail : header.getDETAIL())
			{

				P100 p100 = mapToP100(header, detail);

				OrderLine orderLine = new OrderLine(p100);
				orderHeader.addOrderLine(orderLine);

			}
		}

		return ediDocuments;
	}

	private P100 mapToP100(HEADERXbest header, DETAILXbest detail)
	{
		P100 p100 = new P100();
		p100.setPositionNo(detail.getLINENUMBER());

		BigDecimal cutuQty = BigDecimal.ZERO;
		BigDecimal orderQty = BigDecimal.ZERO;
		String orderUnit = StringUtils.EMPTY;
		// TODO check later again
		for (DQUAN1 dquan1 : detail.getDQUAN1())
		{
			QuantityQual quantityQual = QuantityQual.valueOf(dquan1.getQUANTITYQUAL());
			switch (quantityQual)
			{
				//TODO what about the measurement unit if both types of quantity are there?
				case CUTU:
				{
					final String quantityStr = dquan1.getQUANTITY();
					cutuQty = cutuQty.add(new BigDecimal(quantityStr));
					orderUnit = dquan1.getMEASUREMENTUNIT();
					break;
				}
				case ORDR:
				{
					final String quantityStr = dquan1.getQUANTITY();
					orderQty = orderQty.add(new BigDecimal(quantityStr));
					orderUnit = dquan1.getMEASUREMENTUNIT();
					break;
				}
			}
		}
		if (cutuQty == BigDecimal.ZERO)
		{
			cutuQty = BigDecimal.ONE;
		}
		p100.setCuperTU(cutuQty.toString());
		p100.setOrderQty(orderQty.toString());
		p100.setOrderUnit(orderUnit);

		//TODO check mapping - very unsure here
		BigDecimal price = BigDecimal.ZERO;
		for (DPRIC1 dpric1 : detail.getDPRIC1())
		{
			price = price.add(new BigDecimal(dpric1.getPRICE()));
		}
		p100.setBuyerPrice(price.toString());

		// ProductDescription
		for (DPRDE1 dprde1 : detail.getDPRDE1())
		{
			//TODO check mapping, is another condition necessary?
			if (ProductDescQual.valueOf(dprde1.getPRODUCTDESCQUAL()) == ProductDescQual.PROD)
			{
				final String productDescription = dprde1.getPRODUCTDESCTEXT();
				p100.setArtDescription(productDescription);
				break;
			}
		}

		// Product information
		//TODO check mapping
		for (DPRIN1 dprin1 : detail.getDPRIN1())
		{
			// upc, ean or ucc
			if (ProductQual.valueOf(dprin1.getPRODUCTQUAL()) == ProductQual.GTIN)
			{
				p100.setEanArtNo(dprin1.getPRODUCTID());
				break;
			}
		}

		// TODO check mapping
		if (header.getHCURR1() != null)
		{
			final String currency = header.getHCURR1().getCURRENCYCODE();
			p100.setCurrency(currency);
		}
		return p100;
	}

	private H100 mapToH100(HEADERXbest header)
	{
		H100 h100 = new H100();

		for (HADRE1 hadre1 : header.getHADRE1())
		{
			AddressQual addressQual = AddressQual.valueOf(hadre1.getADDRESSQUAL());
			switch (addressQual)
			{
				case SUPL:
				{
					h100.setSupplierID(hadre1.getPARTYIDGLN());
					break;
				}
				case BUYR:
				{
					h100.setBuyerID(hadre1.getPARTYIDGLN());
					break;
				}
				case DELV:
				{
					h100.setDeliveryID(hadre1.getPARTYIDGLN());
					break;
				}
				case IVCE:
				{
					h100.setInvoiceID(hadre1.getPARTYIDGLN());
					break;
				}
				case ULCO:
				{
					h100.setStoreNumber(hadre1.getPARTYIDGLN());
					break;
				}
			}
		}

		for (HDATE1 hdate1 : header.getHDATE1())
		{
			DateQual dateQual = DateQual.valueOf(hdate1.getDATEQUAL());
			switch (dateQual)
			{
				case CREA:
				{
					h100.setMessageDate(hdate1.getDATEFROM());
					break;
				}
				case DELV:
				{
					if (hdate1.getDATEFROM() == null)
					{
						h100.setDeliveryDate(hdate1.getDATETO());
					}
					else
					{
						h100.setDeliveryDate(hdate1.getDATEFROM());
					}
					break;
				}
			}
		}
		h100.setDocumentNo(header.getDOCUMENTID());
		return h100;
	}

}
