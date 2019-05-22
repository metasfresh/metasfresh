package de.metas.impexp.bpartner;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;

import de.metas.util.Check;
import de.metas.util.Services;
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

@UtilityClass
/* package */ class IFABPartnerImportHelper
{

	@Cached(cacheName = I_C_BPartner.Table_Name + "#by#" + I_C_BPartner.COLUMNNAME_IFA_Manufacturer)
	@Nullable public I_C_BPartner fetchManufacturer(@NonNull final String manufacturer_IFA_Value)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner.class)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IFA_Manufacturer, manufacturer_IFA_Value)
				.create()
				.firstOnly(I_C_BPartner.class);
	}

	final public I_C_BPartner importRecord(@NonNull final I_I_Pharma_BPartner importRecord)
	{
		final I_C_BPartner bpartner;
		if (importRecord.getC_BPartner_ID() <= 0)	// Insert new BPartner
		{
			bpartner = createNewBPartner(importRecord);
		}
		else
		{
			bpartner = updateExistingBPartner(importRecord);
		}

		save(bpartner);
		importRecord.setC_BPartner(bpartner);

		return bpartner;
	}

	private I_C_BPartner createNewBPartner(@NonNull final I_I_Pharma_BPartner importRecord)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);

		bpartner.setAD_Org_ID(importRecord.getAD_Org_ID());
		bpartner.setCompanyName(extractCompanyName(importRecord));
		bpartner.setIsCompany(true);
		bpartner.setName(extractCompanyName(importRecord));
		bpartner.setName2(importRecord.getb00name2());
		bpartner.setName3(importRecord.getb00name3());
		bpartner.setIFA_Manufacturer(importRecord.getb00adrnr());
		bpartner.setDescription("IFA " + importRecord.getb00adrnr());
		bpartner.setIsManufacturer(true);
		bpartner.setURL(importRecord.getb00homepag());

		return bpartner;
	}
	
	private String extractCompanyName(@NonNull final I_I_Pharma_BPartner importRecord)
	{
		if (!Check.isEmpty(importRecord.getb00name1(), true))
		{
			return importRecord.getb00name1();
		}
		
		if (!Check.isEmpty(importRecord.getb00email(), true))
		{
			return importRecord.getb00email();
		}
		
		if (!Check.isEmpty(importRecord.getb00email2(), true))
		{
			return importRecord.getb00email2();
		}
		
		return importRecord.getb00adrnr();
	}


	private I_C_BPartner updateExistingBPartner(@NonNull final I_I_Pharma_BPartner importRecord)
	{
		final I_C_BPartner bpartner;
		bpartner = InterfaceWrapperHelper.create(importRecord.getC_BPartner(), I_C_BPartner.class);

		if (!Check.isEmpty(importRecord.getb00name1(), true))
		{
			bpartner.setIsCompany(true);
			bpartner.setCompanyName(importRecord.getb00name1());
			bpartner.setName(importRecord.getb00name1());
		}
		
		if (!Check.isEmpty(importRecord.getb00name2()))
		{
			bpartner.setName2(importRecord.getb00name2());
		}

		if (!Check.isEmpty(importRecord.getb00name3()))
		{
			bpartner.setName3(importRecord.getb00name3());
		}

		if (!Check.isEmpty(importRecord.getb00adrnr()))
		{
			bpartner.setIFA_Manufacturer(importRecord.getb00adrnr());
			bpartner.setDescription("IFA " + importRecord.getb00adrnr());
		}

		bpartner.setIsManufacturer(true);

		if (!Check.isEmpty(importRecord.getb00homepag()))
		{
			bpartner.setURL(importRecord.getb00homepag());
		}

		return bpartner;
	}
}
