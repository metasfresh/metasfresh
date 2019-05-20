package de.metas.impexp.bpartner;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import de.metas.vertical.pharma.model.I_C_BPartner;
import de.metas.vertical.pharma.model.I_I_Pharma_BPartner;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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
 * A collection of IFA bpartner import test helpers.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
/* package */class IFABPartnerImportTestHelper
{

	final public void assertIFABPartnerImported(@NonNull final I_I_Pharma_BPartner record)
	{
		assertBPartnerImported(record);
		assertLocationImported(record);
	}
	
	private void assertBPartnerImported(@NonNull final I_I_Pharma_BPartner record)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(record.getC_BPartner(), I_C_BPartner.class);
		assertThat(bpartner).isNotNull();
		assertThat(bpartner.getName()).isNotNull();
		assertThat(bpartner.getName()).isEqualTo(record.getb00name1());
		assertThat(bpartner.getCompanyName()).isNotNull();
		assertThat(bpartner.getCompanyName()).isEqualTo(record.getb00name1());
		assertThat(bpartner.getName2()).isNotNull();
		assertThat(bpartner.getName2()).isEqualTo(record.getb00name2());
		assertThat(bpartner.getName3()).isNotNull();
		assertThat(bpartner.getName3()).isEqualTo(record.getb00name3());
		assertThat(bpartner.getIFA_Manufacturer()).isEqualTo(record.getb00adrnr());
		assertThat(bpartner.getURL()).isNotNull();
		assertThat(bpartner.getURL()).isEqualTo(record.getb00homepag());
		assertThat(bpartner.isManufacturer()).isTrue();
		assertThat(bpartner.isVendor()).isFalse();
		assertThat(bpartner.isCustomer()).isFalse();
	}

	private void assertLocationImported(final I_I_Pharma_BPartner record)
	{
		final I_C_BPartner_Location bplocation = record.getC_BPartner_Location();
		assertThat(bplocation).isNotNull();
		assertThat(bplocation.getC_Location_ID()).isGreaterThan(0);
		assertThat(bplocation.isShipTo()).isTrue();
		
		
		assertThat(bplocation.getPhone()).isEqualTo(record.getb00tel1());
		assertThat(bplocation.getPhone2()).isEqualTo(record.getb00tel2());
		assertThat(bplocation.getFax()).isEqualTo(record.getb00fax1());
		assertThat(bplocation.getFax2()).isEqualTo(record.getb00fax2());
		assertThat(bplocation.getEMail()).isEqualTo(record.getb00email());
		assertThat(bplocation.getEMail2()).isEqualTo(record.getb00email2());

		// Location
		final I_C_Location location = bplocation.getC_Location();
		assertThat(location).isNotNull();
		assertThat(location.getAddress1()).isEqualTo(IFABPartnerLocationImportHelper.buildAddress1(record));
		assertThat(location.getAddress2()).isEqualTo(IFABPartnerLocationImportHelper.buildAddress2(record));
		assertThat(location.getPOBox()).isEqualTo(IFABPartnerLocationImportHelper.buildPOBox(record));
		assertThat(location.getPostal()).isEqualTo(record.getb00plzzu1());
		assertThat(location.getCity()).isEqualTo(record.getb00ortzu());
		assertThat(location.getC_Country_ID()).isEqualTo(record.getC_Country_ID());
	}
}
