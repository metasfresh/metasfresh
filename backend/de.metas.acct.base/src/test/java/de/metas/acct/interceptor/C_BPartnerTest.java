/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.acct.interceptor;

import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class C_BPartnerTest
{
	public static final int ORG_ID = 100;
	public static final int CLIENT_ID = 10;
	public static final int DEBTOR_PREFIX = 10;
	public static final int CREDITOR_PREFIX = 70;
	public static final Map<String, Object> orgClientMap = new HashMap<>();
	private I_C_BPartner bpartner;
	private final BpartnerValueToDebtorCreditorIds valueToDebtorCreditorIds = new BpartnerValueToDebtorCreditorIds("1234567", 101234567, 701234567);
	private final BpartnerValueToDebtorCreditorIds longValueToDebtorCreditorIds = new BpartnerValueToDebtorCreditorIds("1234567890", 0, 0);
	private final BpartnerValueToDebtorCreditorIds shortValueToDebtorCreditorIds = new BpartnerValueToDebtorCreditorIds("123", 0, 0);
	private final BpartnerValueToDebtorCreditorIds nanValueToDebtorCreditorIds = new BpartnerValueToDebtorCreditorIds("123A", 0, 0);

	static
	{
		orgClientMap.put(I_C_AcctSchema.COLUMNNAME_AD_Client_ID, CLIENT_ID);
		orgClientMap.put(I_C_AcctSchema.COLUMNNAME_AD_Org_ID, ORG_ID);
	}

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		final AcctSchemaId acctSchemaId = AcctSchemaTestHelper.newAcctSchema().build();

		final I_C_AcctSchema acctSchema = InterfaceWrapperHelper.load(acctSchemaId, I_C_AcctSchema.class);
		acctSchema.setIsAutoSetDebtoridAndCreditorid(true);
		acctSchema.setDebtorIdPrefix(DEBTOR_PREFIX);
		acctSchema.setCreditorIdPrefix(CREDITOR_PREFIX);
		InterfaceWrapperHelper.save(acctSchema);
		AcctSchemaTestHelper.registerAcctSchemaDAOWhichAlwaysProvides(AcctSchemaId.ofRepoId(acctSchema.getC_AcctSchema_ID()));
		bpartner = createBPartner();
	}

	private I_C_BPartner createBPartner()
	{
		final I_C_BPartner newObject = InterfaceWrapperHelper.create(Env.getCtx(), I_C_BPartner.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.setValues(newObject, orgClientMap);
		InterfaceWrapperHelper.save(newObject);
		return newObject;
	}

	@Test
	public void testBpartnerValueChange()
	{
		testBpartnerValueChange(valueToDebtorCreditorIds);
	}

	@Test
	public void testBpartnerLongValueChange()
	{
		testBpartnerValueChange(longValueToDebtorCreditorIds);
	}

	@Test
	public void testBpartnerShortValueChange()
	{
		testBpartnerValueChange(shortValueToDebtorCreditorIds);
	}

	@Test
	public void testBpartnerNaNValueChange()
	{
		testBpartnerValueChange(nanValueToDebtorCreditorIds);
	}

	private void testBpartnerValueChange(final BpartnerValueToDebtorCreditorIds tuple)
	{
		bpartner.setValue(tuple.getValue());
		new C_BPartner().beforeSave(bpartner);
		Assertions.assertThat(bpartner.getDebtorId()).isEqualTo(tuple.getDebtorId());
		Assertions.assertThat(bpartner.getCreditorId()).isEqualTo(tuple.getCreditorId());
	}
}
