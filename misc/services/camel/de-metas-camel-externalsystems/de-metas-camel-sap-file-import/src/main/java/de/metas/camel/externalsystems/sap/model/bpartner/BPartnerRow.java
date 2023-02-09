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

import de.metas.common.util.Check;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import javax.annotation.Nullable;
import java.util.Optional;

@CsvRecord(separator = "	", skipField = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BPartnerRow
{
	@DataField(pos = 1)
	@NonNull
	@Getter(AccessLevel.NONE)
	private String partnerCode;

	@DataField(pos = 3)
	@NonNull
	private String section;

	@Nullable
	@DataField(pos = 4)
	private String partnerCategory;

	@Nullable
	@DataField(pos = 6)
	private String name1;

	@Nullable
	@DataField(pos = 7)
	private String name2;

	@Nullable
	@DataField(pos = 8)
	private String searchTerm;

	@Nullable
	@DataField(pos = 9)
	private String street;

	@Nullable
	@DataField(pos = 10)
	private String street2;

	@Nullable
	@DataField(pos = 11)
	private String street3;

	@Nullable
	@DataField(pos = 12)
	private String street4;

	@Nullable
	@DataField(pos = 13)
	private String street5;

	@Nullable
	@DataField(pos = 16)
	private String postalCode;

	@Nullable
	@DataField(pos = 17)
	private String city;

	@Nullable
	@DataField(pos = 18)
	private String countryKey;

	@Nullable
	@DataField(pos = 29)
	private String vatRegNo;

	@Nullable
	@DataField(pos = 31)
	private String paymentMethod;

	@Nullable
	@DataField(pos = 36)
	private String salesIncoterms;

	@Nullable
	@DataField(pos = 37)
	private String salesIncoterms2;

	@Nullable
	@DataField(pos = 38)
	private String salesPaymentTerms;

	@Nullable
	@DataField(pos = 40)
	private String purchaseIncoterms;

	@Nullable
	@DataField(pos = 41)
	private String purchaseIncoterms2;

	@Nullable
	@DataField(pos = 42)
	private String purchasePaymentTerms;

	@Nullable
	@DataField(pos = 43)
	private String activeFlag;

	@NonNull
	public PartnerCode getPartnerCode()
	{
		return PartnerCode.of(partnerCode);
	}

	public boolean isDisabled()
	{
		return Optional.ofNullable(activeFlag)
				.filter(Check::isNotBlank)
				.map(activeFlagValue -> activeFlagValue.equalsIgnoreCase("X"))
				.orElse(false);
	}
}
