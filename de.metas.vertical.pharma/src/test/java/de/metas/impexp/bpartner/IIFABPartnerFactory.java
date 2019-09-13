package de.metas.impexp.bpartner;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Country;

import de.metas.vertical.pharma.model.I_I_Pharma_BPartner;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * Fluent {@link I_I_Pharma_BPartner factory
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Builder
@Value
/* package */class IIFABPartnerFactory
{

	private final String B00SSATZ; // operation code
	private final String B00ADRNR; // 
	private final String B00NAME1;
	private final String B00NAME2;
	private final String B00NAME3;
	private final String B00LAND;
	private final String B00PLZZU1;
	private final String B00ORTZU;
	private final String B00STR;
	private final String B00HNRV;
	private final String B00HNRVZ;
	private final String B00HNRB;
	private final String B00HNRBZ;
	private final String B00PLZPF1;
	private final String B00ORTPF;
	private final String B00PF1;
	private final String B00TEL1;
	private final String B00TEL2;
	private final String B00FAX1;
	private final String B00FAX2;
	private final String B00EMAIL;
	private final String B00EMAIL2;
	private final String B00HOMEPAG;
	
	public static class IIFABPartnerFactoryBuilder
	{
		public I_I_Pharma_BPartner build()
		{
			return createImportRecord();
		}

		private I_I_Pharma_BPartner createImportRecord()
		{
			final I_I_Pharma_BPartner ifaPartner = InterfaceWrapperHelper.newInstance(I_I_Pharma_BPartner.class);
			ifaPartner.setb00ssatz(B00SSATZ);
			ifaPartner.setb00adrnr(B00ADRNR);
			ifaPartner.setb00name1(B00NAME1);
			ifaPartner.setb00name2(B00NAME2);
			ifaPartner.setb00name3(B00NAME3);
			ifaPartner.setb00land(B00LAND);
			ifaPartner.setb00plzzu1(B00PLZZU1);
			ifaPartner.setb00ortzu(B00ORTZU);
			ifaPartner.setb00str(B00STR);
			ifaPartner.setb00hnrv(B00HNRV);
			ifaPartner.setb00hnrvz(B00HNRVZ);
			ifaPartner.setb00hnrb(B00HNRB);
			ifaPartner.setb00hnrbz(B00HNRBZ);
			ifaPartner.setb00plzpf1(B00PLZPF1);
			ifaPartner.setb00ortpf(B00ORTPF);
			ifaPartner.setb00pf1(B00PF1);
			ifaPartner.setb00tel1(B00TEL1);
			ifaPartner.setb00tel2(B00TEL2);
			ifaPartner.setb00fax1(B00FAX1);
			ifaPartner.setb00fax2(B00FAX2);
			ifaPartner.setb00email(B00EMAIL);
			ifaPartner.setb00email2(B00EMAIL2);
			ifaPartner.setb00homepag(B00HOMEPAG);
			ifaPartner.setC_Country_ID(101);// set an id by default since we can not match it
			InterfaceWrapperHelper.save(ifaPartner);
			return ifaPartner;
		}
	}
	
	public static I_C_Country createCountry(@NonNull final String isoCode)
	{
		final I_C_Country country = InterfaceWrapperHelper.newInstance(I_C_Country.class);
		country.setCountryCode(isoCode);
		country.setName(isoCode);
		InterfaceWrapperHelper.save(country);
		return country;
	}
	
}
