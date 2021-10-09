package de.metas.bpartner.impexp;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.impexp.format.ImportTableDescriptorRepository;
import de.metas.impexp.processing.DBFunctionsRepository;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.Mutable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_I_BPartner;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

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
public class BPartnerImportProcess_SimpleCase_Test
{
	private Properties ctx;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		ctx = Env.getCtx();

		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
		SpringContextHolder.registerJUnitBean(new DBFunctionsRepository());
		SpringContextHolder.registerJUnitBean(new ImportTableDescriptorRepository());
	}

	@Test
	public void testSingleBpartner()
	{
		final I_I_BPartner ibpartner = prepareImportSingleBPartner();

		final BPartnerImportProcess importProcess = new BPartnerImportProcess();
		importProcess.setCtx(ctx);
		importProcess.importRecord(new Mutable<>(), ibpartner, false /* isInsertOnly */);

		BPartnerImportTestHelper.assertImported(ibpartner);
	}

	/**
	 * Build a test case for import<br>
	 * <br>
	 * <code>value	 FirstName	LastName  IsShipToContact	IsBillToContact	address1	city	countryCode	groupValue	IsBillToDefault	IsShipToDefault	language</code><br>
	 * <code>G0022	 FNTest1	LNTest1	  Y					N				street 997	Berlin	DE			Standard	Y				N				de_CH</code><br>
	 */
	private I_I_BPartner prepareImportSingleBPartner()
	{
		return IBPartnerFactory.builder()
				.ctx(ctx)
				.value("G0022")
				.groupValue("Standard")
				.language("de_CH")
				//
				// Contact:
				.firstName("FNTest1").lastName("LNTest1")
				//
				// Location:
				.shipToContact(true).billToContact(false)
				.address1("street 997").address2("addr2").address3("addr3").address4("addr4").city("Berlin").region("").countryCode("DE")
				.shipToDefaultAddress(false).billToDefaultAddress(true)
				.locationPhone("phone1")
				.locationPhone2("phone2")
				.locationFax("fax")
				//
				.build();
	}
}
