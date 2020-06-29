package de.metas.edi.esb.bean.imports.excel;

/*
 * #%L
 * de.metas.edi.esb
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.NonNull;

/**
 * Internal row representation of a customer's Excel file which shall be imported to C_OLCands.
 *
 * @author tsa
 * @task 08839
 */
public class Excel_OLCand_Row
{
	public static final Excel_OLCand_Row_Builder builder()
	{
		return new Excel_OLCand_Row_Builder();
	}

	public static final Map<Integer, Excel_OLCand_Row> indexByLineNo(final Collection<Excel_OLCand_Row> rows)
	{
		final Map<Integer, Excel_OLCand_Row> lineNo2row = new HashMap<Integer, Excel_OLCand_Row>(rows.size());
		for (Excel_OLCand_Row row : rows)
		{
			final Integer lineNo = row.getLineNo();
			lineNo2row.put(lineNo, row);
		}

		return lineNo2row;
	}

	public static final Excel_OLCand_Row ofMap(@NonNull final Map<String, Object> map)
	{
		return builder()
				.setFromMap(map)
				.build();

	}

	public static final List<Excel_OLCand_Row> ofMaps(final List<Map<String, Object>> mapsList)
	{
		final List<Excel_OLCand_Row> rows = new ArrayList<Excel_OLCand_Row>(mapsList.size());
		for (final Map<String, Object> rowData : mapsList)
		{
			final Excel_OLCand_Row row = ofMap(rowData);
			rows.add(row);
		}

		return rows;
	}

	private final Integer lineNo;

	private final int M_Product_ID;
	private final String productDescription;
	private final String productAttributes;

	private final String UOM_x12de355;
	private final BigDecimal qtyCUsPerTU;
	private final BigDecimal qtyTUs;
	private final BigDecimal qtyUOM;

	private final BigDecimal price;
	private final String currencyISOCode;

	private final String POReference;

	private final Date dateCandidate;
	private final Date datePromised;

	private final int M_ProductPrice_ID;
	private final int M_ProductPrice_Attribute_ID;
	private final int M_HU_PI_Item_Product_ID;
	private final int C_BPartner_ID;
	private final int C_BPartner_Location_ID;

	/* package */Excel_OLCand_Row(final Excel_OLCand_Row_Builder builder)
	{
		this.lineNo = builder.lineNo;

		this.M_Product_ID = builder.M_Product_ID;
		this.productDescription = builder.productDescription;
		this.productAttributes = builder.productAttributes;
		this.UOM_x12de355 = builder.UOM_x12de355;

		this.price = builder.price;
		this.currencyISOCode = null; // TODO

		this.POReference = builder.POReference;

		this.dateCandidate = builder.dateCandidate;
		this.datePromised = builder.datePromised;

		this.qtyUOM = BigDecimal.valueOf(builder.qtyUOM);
		if ("TU".equals(UOM_x12de355))
		{
			this.qtyCUsPerTU = BigDecimal.ONE; // the customer's UOM is TU, so QtyCU is the number of TUs
		}
		else
		{
			this.qtyCUsPerTU = (builder.qtyCUsPerTU == null || builder.qtyCUsPerTU.signum() <= 0)
					// there is no PIIP or the PIIP has unlimited capacity. As we can't deduct the QtyCU from QtyTU, we assume that QtyTU actually means QtyCU.
					// metasfresh shall sort it out by supplying a default PIIP etc
					? BigDecimal.ONE
					// default: use the supplied qtyCUsPerTU value
					: builder.qtyCUsPerTU;
		}
		this.qtyTUs = qtyUOM
				.setScale(0, RoundingMode.CEILING) // this is probably not needed because the got this BD from an int
				.divide(qtyCUsPerTU, RoundingMode.CEILING);

		this.M_ProductPrice_ID = builder.M_ProductPrice_ID;
		this.M_ProductPrice_Attribute_ID = builder.M_ProductPrice_Attribute_ID;
		this.M_HU_PI_Item_Product_ID = builder.M_HU_PI_Item_Product_ID;
		this.C_BPartner_ID = builder.C_BPartner_ID;
		this.C_BPartner_Location_ID = builder.C_BPartner_Location_ID;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " ["
				+ "lineNo=" + lineNo
				+ ", M_Product_ID=" + M_Product_ID
				+ ", productDescription=" + productDescription
				+ ", productAttributes=" + productAttributes
				//
				+ ", qtyUOM=" + qtyUOM
				+ ", qtyCUsPerTU=" + qtyCUsPerTU
				+ ", qtyTUs=" + qtyTUs
				+ ", UOM_x12de355=" + UOM_x12de355
				//
				+ ", price=" + price
				+ ", currencyISOCode=" + currencyISOCode
				//
				+ ", POReference=" + POReference
				//
				+ ", datePromised=" + datePromised
				//
				+ ", M_ProductPrice_ID=" + M_ProductPrice_ID
				+ ", M_ProductPrice_Attribute_ID=" + M_ProductPrice_Attribute_ID
				+ ", M_HU_PI_Item_Product_ID=" + M_HU_PI_Item_Product_ID
				+ ", C_BPartner_ID=" + C_BPartner_ID
				+ ", C_BPartner_Location_ID=" + C_BPartner_Location_ID
				+ "]";
	}

	public Integer getLineNo()
	{
		return lineNo;
	}

	public BigDecimal getQtyCUsPerTU()
	{
		return qtyCUsPerTU;
	}

	public BigDecimal getQtyTUs()
	{
		return qtyTUs;
	}

	public BigDecimal getQtyUOM()
	{
		return qtyUOM;
	}

	public String getUOM_x12de355()
	{
		return UOM_x12de355;
	}

	public BigDecimal getPrice()
	{
		return price;
	}

	public String getCurrencyISOCode()
	{
		return currencyISOCode;
	}

	public String getPOReference()
	{
		return POReference;
	}

	public Date getDateCandidate()
	{
		return dateCandidate;
	}

	public Date getDatePromised()
	{
		return datePromised;
	}

	public int getM_Product_ID()
	{
		return M_Product_ID;
	}

	public String getProductDescription()
	{
		return productDescription;
	}

	public String getProductAttributes()
	{
		return productAttributes;
	}

	public int getM_ProductPrice_ID()
	{
		return M_ProductPrice_ID;
	}

	public int getM_ProductPrice_Attribute_ID()
	{
		return M_ProductPrice_Attribute_ID;
	}

	public int getM_HU_PI_Item_Product_ID()
	{
		return M_HU_PI_Item_Product_ID;
	}

	public int getC_BPartner_ID()
	{
		return C_BPartner_ID;
	}

	public int getC_BPartner_Location_ID()
	{
		return C_BPartner_Location_ID;
	}
}
