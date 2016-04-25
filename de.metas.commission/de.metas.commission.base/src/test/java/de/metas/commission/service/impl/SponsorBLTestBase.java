package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
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


import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.BeforeClass;

import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorDAO;

public class SponsorBLTestBase
{
	protected I_C_Sponsor sponsor;

	protected Timestamp date ;

	protected ICommissionType commissionType;

	protected I_C_AdvComSystem system ;

	protected I_M_Product product ;

	protected I_C_AdvCommissionTerm term ;
	
	protected static I_C_AdvComSystem_Type systemType;
	
	
	@BeforeClass
	public final static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
		POJOWrapper.setDefaultStrictValues(false);
	}

	protected ISponsorBL sponsorBL;
	protected ISponsorDAO sponsorDAO;

	protected Properties ctx;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		ctx = Env.getCtx();

	}
}
