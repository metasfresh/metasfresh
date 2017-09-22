package org.adempiere.impexp;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_I_BPartner;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Fluent {@link I_I_BPartner} factory
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Builder
@Value
/* package */class IBPartnerFactory
{
	private static final int C_Country_ID = 101;

	private final Properties ctx;
	private final String value;
	private final String firstName;
	private final String lastName;
	private final String address1;
	private final String address2;
	private final String city;
	private final String region;
	private final String countryCode;
	private final String groupValue;
	private final String language;
	private final boolean shipToContact;
	private final boolean billToContact;
	private final boolean billToDefaultAddress;
	private final boolean shipToDefaultAddress;

	public static class IBPartnerFactoryBuilder
	{
		public I_I_BPartner createImportRecord()
		{
			final I_I_BPartner ibpartner = InterfaceWrapperHelper.create(ctx, I_I_BPartner.class, ITrx.TRXNAME_None);
			ibpartner.setValue(value);
			ibpartner.setFirstname(firstName);
			ibpartner.setLastname(lastName);
			ibpartner.setAddress1(address1);
			ibpartner.setAddress2(address2);
			ibpartner.setCity(city);
			ibpartner.setRegionName(region);
			ibpartner.setGroupValue(groupValue);
			ibpartner.setCountryCode(countryCode);
			ibpartner.setAD_Language(language);
			ibpartner.setIsBillToContact_Default(billToContact);
			ibpartner.setIsShipToContact_Default(shipToContact);
			ibpartner.setIsBillToDefault(billToDefaultAddress);
			ibpartner.setIsShipToDefault(shipToDefaultAddress);
			ibpartner.setC_Country_ID(C_Country_ID);
			InterfaceWrapperHelper.save(ibpartner);

			return ibpartner;
		}

		@Deprecated
		public I_I_BPartner build()
		{
			return createImportRecord();
		}

	}
}
