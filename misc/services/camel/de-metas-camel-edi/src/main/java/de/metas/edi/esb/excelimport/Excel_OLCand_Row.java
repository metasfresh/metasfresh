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

package de.metas.edi.esb.excelimport;

import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Internal row representation of a customer's Excel file which shall be imported to C_OLCands.
 *
 * @author tsa
 * task 08839
 */
public class Excel_OLCand_Row
{
	public static Excel_OLCand_Row_Builder builder()
	{
		return new Excel_OLCand_Row_Builder();
	}

	public static Map<Integer, Excel_OLCand_Row> indexByLineNo(final Collection<Excel_OLCand_Row> rows)
	{
		final Map<Integer, Excel_OLCand_Row> lineNo2row = new HashMap<>(rows.size());
		for (final Excel_OLCand_Row row : rows)
		{
			final Integer lineNo = row.getLineNo();
			lineNo2row.put(lineNo, row);
		}

		return lineNo2row;
	}

	public static Excel_OLCand_Row ofMap(@NonNull final Map<String, Object> map)
	{
		return builder()
				.setFromMap(map)
				.build();

	}

	// public static List<Excel_OLCand_Row> ofMaps(final List<Map<String, Object>> mapsList)
	// {
	// 	final List<Excel_OLCand_Row> rows = new ArrayList<>(mapsList.size());
	// 	for (final Map<String, Object> rowData : mapsList)
	// 	{
	// 		final Excel_OLCand_Row row = ofMap(rowData);
	// 		rows.add(row);
	// 	}
	//
	// 	return rows;
	// }

	private final Integer lineNo;

	private final int M_Product_ID;
	private final String productDescription;
	private final String productAttributes;

	private final BigDecimal qtyCUsPerTU;
	private final BigDecimal qtyTUs;

	private final BigDecimal qtyInUOM;
	private final String UOM_x12de355;

	private final BigDecimal price;
	private final String priceUOM_x12de355;

	private final String currencyISOCode;

	private final String POReference;

	private final Date dateCandidate;
	private final Date datePromised;

	private final int M_ProductPrice_ID;
	private final int M_ProductPrice_Attribute_ID;
	private final int M_HU_PI_Item_Product_ID;

	private final int C_BPartner_ID;
	private final int C_BPartner_Location_ID;

	@Getter private final int Bill_BPartner_ID;
	@Getter private final int Bill_Location_ID;
	@Getter private final int HandOver_Partner_ID;
	@Getter private final int HandOver_Location_ID;
	@Getter private final int DropShip_BPartner_ID;
	@Getter private final int DropShip_Location_ID;



	/* package */Excel_OLCand_Row(final Excel_OLCand_Row_Builder builder)
	{
		this.lineNo = builder.lineNo;

		this.M_Product_ID = builder.M_Product_ID;
		this.productDescription = builder.productDescription;
		this.productAttributes = builder.productAttributes;
		this.UOM_x12de355 = builder.UOM_x12de355;

		this.price = builder.price;
		this.priceUOM_x12de355 = builder.priceUOM_x12de355;
		this.currencyISOCode = null; // TODO

		this.POReference = builder.POReference;

		this.dateCandidate = builder.dateCandidate;
		this.datePromised = builder.datePromised;

		this.qtyInUOM = BigDecimal.valueOf(builder.qtyUOM);
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
		this.qtyTUs = qtyInUOM
				.setScale(0, RoundingMode.CEILING) // this is probably not needed because the got this BD from an int
				.divide(qtyCUsPerTU, RoundingMode.CEILING);

		this.M_ProductPrice_ID = builder.M_ProductPrice_ID;
		this.M_ProductPrice_Attribute_ID = builder.M_ProductPrice_Attribute_ID;
		this.M_HU_PI_Item_Product_ID = builder.M_HU_PI_Item_Product_ID;

		this.C_BPartner_ID = builder.C_BPartner_ID;
		this.C_BPartner_Location_ID = builder.C_BPartner_Location_ID;
		this.Bill_BPartner_ID = builder.Bill_BPartner_ID;
		this.Bill_Location_ID = builder.Bill_Location_ID;
		this.HandOver_Partner_ID = builder.HandOver_Partner_ID;
		this.HandOver_Location_ID = builder.HandOver_Location_ID;
		this.DropShip_BPartner_ID = builder.DropShip_BPartner_ID;
		this.DropShip_Location_ID = builder.DropShip_Location_ID;
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
				+ ", qtyInUOM=" + qtyInUOM
				+ ", qtyCUsPerTU=" + qtyCUsPerTU
				+ ", qtyTUs=" + qtyTUs
				+ ", UOM_x12de355=" + UOM_x12de355
				//
				+ ", price=" + price
				+ ", currencyISOCode=" + currencyISOCode
				+ ", priceUOM_x12de355=" + priceUOM_x12de355
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

	public BigDecimal getQtyInUOM()
	{
		return qtyInUOM;
	}

	public String getUOM_x12de355()
	{
		return UOM_x12de355;
	}

	public String getPriceUOM_x12de355()
	{
		return priceUOM_x12de355;
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
