package org.adempiere.inout.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import de.metas.adempiere.model.I_AD_User;
import de.metas.common.util.time.SystemTime;
import de.metas.inout.IInOutBL;
import de.metas.inout.impl.InOutBL;
import de.metas.interfaces.I_C_BPartner;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_R_Request;
import org.compiere.model.I_R_RequestType;
import org.compiere.model.X_R_Request;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

public class InOutBLTest
{
	private InOutBL inoutBL;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		POJOWrapper.setDefaultStrictValues(false); // we will want to return "null"

		inoutBL = new InOutBL();
		Services.registerService(IInOutBL.class, inoutBL);
	}

	@Test
	public void test_isReversal_NoReversal()
	{
		final I_M_InOut record = InterfaceWrapperHelper.create(Env.getCtx(), I_M_InOut.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(record);

		Assert.assertEquals("", false, inoutBL.isReversal(record));
	}

	@Test
	public void test_isReversal_HasReversal()
	{
		final I_M_InOut record = InterfaceWrapperHelper.create(Env.getCtx(), I_M_InOut.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(record);

		final I_M_InOut reversal = InterfaceWrapperHelper.create(Env.getCtx(), I_M_InOut.class, ITrx.TRXNAME_None);
		reversal.setReversal_ID(InterfaceWrapperHelper.getId(record));
		InterfaceWrapperHelper.save(reversal);

		record.setReversal_ID(InterfaceWrapperHelper.getId(reversal));
		InterfaceWrapperHelper.save(record);

		Assert.assertEquals(false, inoutBL.isReversal(record));
		Assert.assertEquals(true, inoutBL.isReversal(reversal));
	}

	@Test(expected = AdempiereException.class)
	public void test_isReversal_SelfReferencing()
	{
		final I_M_InOut record = InterfaceWrapperHelper.create(Env.getCtx(), I_M_InOut.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(record);

		record.setReversal_ID(InterfaceWrapperHelper.getId(record));
		InterfaceWrapperHelper.save(record);

		final boolean isReversalActual = inoutBL.isReversal(record);
		Assert.fail("This test shall throw exception because self referencing is not ok, but instead returned: " + isReversalActual);
	}

	@Test
	public void test_createRequestFromInOut()
	{
		final de.metas.inout.model.I_M_InOut inout = createInOut();

		final I_R_RequestType soRequestType = createRequestType("RequestType");

		final I_R_Request request = inoutBL.createRequestFromInOut(inout);

		assertThat(request.getAD_Org_ID()).isEqualTo(inout.getAD_Org_ID());
		assertThat(request.getR_RequestType_ID()).isEqualTo(soRequestType.getR_RequestType_ID());
		assertThat(request.getAD_Table_ID()).isEqualTo(getTableId(de.metas.inout.model.I_M_InOut.class));
		assertThat(request.getRecord_ID()).isEqualTo(inout.getM_InOut_ID());
		assertThat(request.getC_BPartner_ID()).isEqualTo(inout.getC_BPartner_ID());
		assertThat(request.getAD_User_ID()).isEqualTo(inout.getAD_User_ID());
		assertThat(request.getDateDelivered()).isEqualTo(inout.getMovementDate());
		assertThat(request.getSummary()).isEqualTo(" ");
		assertThat(request.getConfidentialType()).isEqualTo(X_R_Request.CONFIDENTIALTYPE_Internal);
		assertThat(request.getPerformanceType()).isNullOrEmpty();
	}

	private I_R_RequestType createRequestType(final String name)
	{
		final I_R_RequestType requestType = newInstance(I_R_RequestType.class);
		requestType.setName(name);
		requestType.setInternalName("A_CustomerComplaint");

		save(requestType);
		return requestType;
	}

	private de.metas.inout.model.I_M_InOut createInOut()
	{
		final de.metas.inout.model.I_M_InOut inout = newInstance(de.metas.inout.model.I_M_InOut.class);

		inout.setIsSOTrx(true);
		inout.setC_BPartner_ID(createPartner("Partner 1").getC_BPartner_ID());
		inout.setAD_User_ID(createUser("User1").getAD_User_ID());
		inout.setMovementDate(SystemTime.asDayTimestamp());
		inout.setC_DocType_ID(createDocType().getC_DocType_ID());

		save(inout);

		return inout;
	}

	private I_C_DocType createDocType()
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);

		save(docType);
		return docType;
	}

	private I_AD_User createUser(final String name)
	{
		final I_AD_User user = newInstance(I_AD_User.class);
		user.setName(name);
		save(user);
		return user;

	}

	private I_C_BPartner createPartner(final String name)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setName(name);
		save(partner);

		return partner;
	}
}
