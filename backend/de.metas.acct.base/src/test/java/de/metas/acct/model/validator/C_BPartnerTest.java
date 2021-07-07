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

package de.metas.acct.model.validator;

import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AcctSchemaId;
import groovy.lang.Tuple3;
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
	public static final int orgId = 100;
	public static final int clientId = 10;
	public static final int debitorPrefix = 10;
	public static final int creditorPrefix = 70;
	public static final Map<String, Object> orgClientMap = new HashMap<>();
	private I_C_BPartner bpartner;
	private final Tuple3<String, Integer, Integer> valueToDebtorCreditorIds = new Tuple3<>("1234567", 101234567, 701234567);
	private final Tuple3<String, Integer, Integer> longValueToDebtorCreditorIds = new Tuple3<>("1234567890", 0, 0);
	private final Tuple3<String, Integer, Integer> shortValueToDebtorCreditorIds = new Tuple3<>("123", 0, 0);
	private final Tuple3<String, Integer, Integer> nanValueToDebtorCreditorIds = new Tuple3<>("123A", 0, 0);

	static
	{
		orgClientMap.put(I_C_AcctSchema.COLUMNNAME_AD_Client_ID, clientId);
		orgClientMap.put(I_C_AcctSchema.COLUMNNAME_AD_Org_ID, orgId);
	}

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		final AcctSchemaId acctSchemaId = AcctSchemaTestHelper.newAcctSchema().build();

		final I_C_AcctSchema acctSchema = InterfaceWrapperHelper.load(acctSchemaId, I_C_AcctSchema.class);
		acctSchema.setIsAutoSetDebtoridAndCreditorid(true);
		acctSchema.setDebtorIdPrefix(debitorPrefix);
		acctSchema.setCreditorIdPrefix(creditorPrefix);
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

	private void testBpartnerValueChange(final Tuple3<String, Integer, Integer> tuple)
	{
		bpartner.setValue(tuple.getFirst());
		new C_BPartner().beforeSave(bpartner);
		Assertions.assertThat(bpartner.getDebtorId()).isEqualTo(tuple.getSecond());
		Assertions.assertThat(bpartner.getCreditorId()).isEqualTo(tuple.getThird());
	}
}
