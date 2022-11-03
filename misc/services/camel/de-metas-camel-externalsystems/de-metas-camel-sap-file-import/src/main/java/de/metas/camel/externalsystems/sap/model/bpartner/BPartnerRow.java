/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap.model.bpartner;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.util.Arrays;

@CsvRecord(separator = "	", skipField =true)
@Getter
public class BPartnerRow
{
	public enum PartnerCategory
	{
		STORAGE_LOCATION("4");

		@Getter
		final String code;

		PartnerCategory(final String code)
		{
			this.code = code;
		}

		public static PartnerCategory ofCode(@NonNull final String code) throws Exception
		{
			final PartnerCategory type = typesByCode.get(code);
			if (type == null)
			{
				throw new Exception("No " + PartnerCategory.class + " found for code: " + code);
			}
			return type;
		}

		private static final ImmutableMap<String, PartnerCategory> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), PartnerCategory::getCode);

	}

	@DataField(pos = 1)
	String partnerCode;

	@DataField(pos = 3)
	String section;

	@DataField(pos = 4)
	String partnerCategory;

	@DataField(pos = 6)
	String name1;

	@DataField(pos = 7)
	String name2;

	@DataField(pos = 8)
	String searchTerm;

	@DataField(pos = 9)
	String street;

	@DataField(pos = 10)
	String street2;

	@DataField(pos = 11)
	String street3;

	@DataField(pos = 12)
	String street4;

	@DataField(pos = 13)
	String street5;

	@DataField(pos = 16)
	String postalCode;

	@DataField(pos = 17)
	String city;

	@DataField(pos = 18)
	String countryKey;

	@DataField(pos = 29)
	String vatRegNo;

	@DataField(pos = 36)
	String salesIncoterms;

	@DataField(pos = 37)
	String salesIncoterms2;

	@DataField(pos = 38)
	String salesPaymentTerms;

	@DataField(pos = 40)
	String purchaseIncoterms;

	@DataField(pos = 41)
	String purchaseIncoterms2;

	@DataField(pos = 42)
	String purchasePaymentTerms;
}
