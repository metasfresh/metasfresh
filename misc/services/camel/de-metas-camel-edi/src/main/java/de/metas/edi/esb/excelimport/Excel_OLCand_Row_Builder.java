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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.edi.esb.commons.SystemTime;
import de.metas.edi.esb.commons.Util;
import lombok.NonNull;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * Builds {@link Excel_OLCand_Row}.
 *
 * @author tsa
 * task 08839
 */
public class Excel_OLCand_Row_Builder
{
	public static final String MAPKEY_LineNo = "LineNo";
	Integer lineNo;
	//
	private static final String MAPKEY_M_Product_ID = "M_Product_ID";
	int M_Product_ID = -1;
	private static final String MAPKEY_ProductDescription = "Artikel";
	String productDescription;
	private static final String MAPKEY_ProductAttributes = "Merkmale";
	String productAttributes;

	//
	private static final String MAPKEY_UOM_x12de355 = "uom_x12de355";
	String UOM_x12de355;
	private static final String MAPKEY_QtyUOM = "Menge";
	int qtyUOM;
	private static final String MAPKEY_QtyCUsPerTU = "QtyCUsPerTU";
	BigDecimal qtyCUsPerTU;
	private static final String MAPKEY_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	int M_HU_PI_Item_Product_ID;

	//
	private static final String MAPKEY_Price = "Preis";
	BigDecimal price;
	private static final String MAPKEY_PriceUOM_x12de355 = "price_uom_x12de355";
	String priceUOM_x12de355;

	//
	private static final String MAPKEY_POReference = "Bestellung Nr";
	String POReference;
	//
	Date dateCandidate = SystemTime.asDate();
	private static final String MAPKEY_DatePromised = "Liefertag";
	Date datePromised = null;
	//
	private static final String MAPKEY_M_ProductPrice_ID = "M_ProductPrice_ID";
	int M_ProductPrice_ID = -1;
	private static final String MAPKEY_M_ProductPrice_Attribute_ID = "M_ProductPrice_Attribute_ID";
	int M_ProductPrice_Attribute_ID = -1;
	private static final String MAPKEY_C_BPartner_ID = "C_BPartner_ID";
	int C_BPartner_ID = -1;
	private static final String MAPKEY_C_BPartner_Location_ID = "C_BPartner_Location_ID";
	int C_BPartner_Location_ID = -1;

	private static final String MAPKEY_Bill_BPartner_ID = "Bill_BPartner_ID";
	int Bill_BPartner_ID = -1;

	private static final String MAPKEY_Bill_Location_ID = "Bill_Location_ID";
	int Bill_Location_ID = -1;

	private static final String MAPKEY_HandOver_Partner_ID = "HandOver_Partner_ID";
	int HandOver_Partner_ID = -1;

	private static final String MAPKEY_HandOver_Location_ID = "HandOver_Location_ID";
	int HandOver_Location_ID = -1;

	private static final String MAPKEY_DropShip_BPartner_ID = "DropShip_BPartner_ID";
	int DropShip_BPartner_ID = -1;

	private static final String MAPKEY_DropShip_Location_ID = "DropShip_Location_ID";
	int DropShip_Location_ID = -1;

	private static final List<DateFormat> dateFormats = ImmutableList.<DateFormat>builder()
			.add(new SimpleDateFormat("dd.MM.yyyy"))
			.add(new SimpleDateFormat("dd.MM.yy"))
			.add(new SimpleDateFormat("MM/dd/yyyy")) // US format
			.add(new SimpleDateFormat("MM/dd/yy")) // US format
			.build();

	private static final List<NumberFormat> numberFormats = ImmutableList.<NumberFormat>builder()
			.add(NumberFormat.getInstance(Locale.GERMAN))
			.add(NumberFormat.getInstance(Locale.ENGLISH))
			.build();

	Excel_OLCand_Row_Builder()
	{
	}

	public Excel_OLCand_Row build()
	{
		return new Excel_OLCand_Row(this);
	}

	public Excel_OLCand_Row_Builder setFromMap(@NonNull final Map<String, Object> map)
	{
		// NOTE: we need to create a new map, because we want to use case insensitive keys.
		final TreeMap<String, Object> caseInsensitiveKeysMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		caseInsensitiveKeysMap.putAll(map);

		try
		{
			this.lineNo = extractInteger(caseInsensitiveKeysMap, MAPKEY_LineNo);

			this.M_Product_ID = coalesce(extractInteger(caseInsensitiveKeysMap, MAPKEY_M_Product_ID), -1);
			this.productDescription = extractString(caseInsensitiveKeysMap, MAPKEY_ProductDescription);
			this.productAttributes = extractString(caseInsensitiveKeysMap, MAPKEY_ProductAttributes);
			//
			this.UOM_x12de355 = extractString(caseInsensitiveKeysMap, MAPKEY_UOM_x12de355);
			this.qtyUOM = coalesce(extractInteger(caseInsensitiveKeysMap, MAPKEY_QtyUOM), 0);
			this.qtyCUsPerTU = extractBigDecimal(caseInsensitiveKeysMap, MAPKEY_QtyCUsPerTU);
			this.M_HU_PI_Item_Product_ID = coalesce(extractInteger(caseInsensitiveKeysMap, MAPKEY_M_HU_PI_Item_Product_ID), -1);
			//
			this.priceUOM_x12de355 = extractString(caseInsensitiveKeysMap, MAPKEY_PriceUOM_x12de355);
			this.price = extractBigDecimal(caseInsensitiveKeysMap, MAPKEY_Price);
			//
			this.POReference = extractString(caseInsensitiveKeysMap, MAPKEY_POReference);
			//
			this.datePromised = extractDate(caseInsensitiveKeysMap, MAPKEY_DatePromised);
			//
			this.M_ProductPrice_ID = coalesce(extractInteger(caseInsensitiveKeysMap, MAPKEY_M_ProductPrice_ID), -1);
			this.M_ProductPrice_Attribute_ID = coalesce(extractInteger(caseInsensitiveKeysMap, MAPKEY_M_ProductPrice_Attribute_ID), -1);

			this.C_BPartner_ID = coalesce(extractInteger(caseInsensitiveKeysMap, MAPKEY_C_BPartner_ID), -1);
			this.C_BPartner_Location_ID = coalesce(extractInteger(caseInsensitiveKeysMap, MAPKEY_C_BPartner_Location_ID), -1);

			this.Bill_BPartner_ID = coalesce(extractInteger(caseInsensitiveKeysMap, MAPKEY_Bill_BPartner_ID), -1);
			this.Bill_Location_ID = coalesce(extractInteger(caseInsensitiveKeysMap, MAPKEY_Bill_Location_ID), -1);

			this.HandOver_Partner_ID = coalesce(extractInteger(caseInsensitiveKeysMap, MAPKEY_HandOver_Partner_ID), -1);
			this.HandOver_Location_ID = coalesce(extractInteger(caseInsensitiveKeysMap, MAPKEY_HandOver_Location_ID), -1);

			this.DropShip_BPartner_ID = coalesce(extractInteger(caseInsensitiveKeysMap, MAPKEY_DropShip_BPartner_ID), -1);
			this.DropShip_Location_ID = coalesce(extractInteger(caseInsensitiveKeysMap, MAPKEY_DropShip_Location_ID), -1);

			return this;
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Failed building " + Excel_OLCand_Row.class + " from " + caseInsensitiveKeysMap, e);
		}
	}

	private static Object getValue(final Map<String, Object> map, final String key)
	{
		return map.get(key);
	}

	private static String extractString(final Map<String, Object> map, final String key)
	{
		final Object value = getValue(map, key);

		if (value == null)
		{
			return null;
		}
		else if (value instanceof BigDecimal)
		{
			final BigDecimal valueBD = Util.stripTrailingDecimalZeros((BigDecimal)value);
			return valueBD.toString();
		}
		else
		{
			return value.toString();
		}
	}

	private static Integer extractInteger(final Map<String, Object> map, final String key)
	{
		final Object value = getValue(map, key);
		if (value == null)
		{
			return null;
		}
		if (value instanceof Number)
		{
			return ((Number)value).intValue();
		}

		final String valueStr = value.toString().trim();
		if (valueStr.isEmpty())
		{
			return null;
		}

		try
		{
			final int valueInt = Integer.parseInt(valueStr);
			return valueInt;
		}
		catch (final NumberFormatException e)
		{
			throw new RuntimeException("Failed parsing attribute: " + key, e);
		}
	}

	private static BigDecimal extractBigDecimal(final Map<String, Object> map, final String key)
	{
		final Object value = getValue(map, key);

		return extractBigDecimal(value);

	}

	@VisibleForTesting
	static BigDecimal extractBigDecimal(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		if (value instanceof BigDecimal)
		{
			return (BigDecimal)value;
		}

		final String valueStr = value.toString().trim();
		if (valueStr.isEmpty())
		{
			return null;
		}

		return extractBigDecimalFromStringWithUnknownLocale(valueStr);
	}

	/**
	 * @deprecated this code is required for old excel files (>2018) where some number are encoded as strings. It can soon be removed.
	 */
	@Deprecated
	private static BigDecimal extractBigDecimalFromStringWithUnknownLocale(@NonNull final String valueStr)
	{
		BigDecimal actualNumber = null;
		try
		{
			for (final NumberFormat numberFormat : numberFormats)
			{
				final Number parsed = numberFormat.parse(valueStr);
				final BigDecimal numberCandidate = new BigDecimal(parsed.toString());

				if (actualNumber == null)
				{
					actualNumber = numberCandidate;
					continue;
				}

				final boolean isIntegerActual = isInteger(actualNumber);
				final boolean isIntegerCandidate = isInteger(numberCandidate);

				if (actualNumber.equals(numberCandidate))
				{
					continue;
				}

				else if (isIntegerActual)
				{
					// Maybe the decimal separator was ignored. Use the smaller number.
					if (numberCandidate.compareTo(actualNumber) < 0)
					{
						actualNumber = numberCandidate;
					}
				}
				else if (!isIntegerCandidate)
				{

					// both the actual number and the number candidate have decimal separators.
					// This means that one of them could have used the both separators, for thousands and decimals.
					// Take the greater number in this case
					if (numberCandidate.compareTo(actualNumber) > 0)
					{
						actualNumber = numberCandidate;
					}
				}

			}
			return actualNumber;
		}
		catch (final Exception e)
		{
			// ignore it
		}
		return null;
	}

	private static boolean isInteger(final BigDecimal numberCandidate)
	{
		return numberCandidate.stripTrailingZeros().scale() <= 0;
	}

	private Date extractDate(final Map<String, Object> map, final String key)
	{
		final Object value = getValue(map, key);
		if (value == null)
		{
			return null;
		}
		if (value instanceof Date)
		{
			return (Date)value;
		}

		//
		// Fallback: try parsing the dates from strings using some common predefined formats
		// NOTE: this shall not happen very often because we take care of dates when we are parsing the Excel file.
		final String valueStr = value.toString().trim();
		if (valueStr.isEmpty())
		{
			return null;
		}

		for (final DateFormat dateFormat : dateFormats)
		{
			try
			{
				return dateFormat.parse(valueStr);
			}
			catch (final ParseException e)
			{
				// ignore it
			}
		}

		return null;
	}

	private static <T> T coalesce(final T value, final T defaultValue)
	{
		return value == null ? defaultValue : value;
	}
}
