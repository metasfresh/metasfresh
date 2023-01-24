package de.metas.acct.vatcode.impl;

import de.metas.acct.vatcode.IVATCodeDAO;
import de.metas.acct.vatcode.VATCode;
import de.metas.acct.vatcode.VATCodeMatchingRequest;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_Tax;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@ExtendWith(AdempiereTestWatcher.class)
public class VATCodeDAOTest
{
	private Properties ctx;
	private IVATCodeDAO vatCodeDAO;

	//
	// Master data
	private int acctSchemaId;
	private I_C_Tax tax1;
	private I_C_Tax tax3;

	private final Date date_1970_01_01 = TimeUtil.getDay(1970, 1, 1);
	private final Date date_2016_01_01 = TimeUtil.getDay(2016, 1, 1);
	@SuppressWarnings("unused")
	private final Date date_2016_02_01 = TimeUtil.getDay(2016, 2, 1);

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		ctx = Env.getCtx();
		vatCodeDAO = Services.get(IVATCodeDAO.class);

		//
		// Master data
		acctSchemaId = 12345;
		tax1 = createTax("tax1");
		createTax("tax2");
		tax3 = createTax("tax3");
	}

	@Test
	public void test_findVATCode_StandardTests()
	{
		// CLogMgt.getLogger(VATCodeDAO.class).setLevel(Level.FINEST);

		final VATCode code1 = VATCode.of("VATCode1", 1);
		final VATCode code2 = VATCode.of("VATCode2", 2);
		final VATCode code3 = VATCode.of("VATCode3", 3);

		newVATCodeBuilder().setC_Tax(tax1).setIsSOTrx(null).setValidFrom(date_1970_01_01).setVATCode(code1).build();
		newVATCodeBuilder().setC_Tax(tax1).setIsSOTrx(false).setValidFrom(date_1970_01_01).setVATCode(code2).build();
		newVATCodeBuilder().setC_Tax(tax1).setIsSOTrx(true).setValidFrom(date_1970_01_01).setVATCode(code3).build();

		assertVATCode(code2, VATCodeMatchingRequest.builder().setC_AcctSchema_ID(acctSchemaId).setC_Tax_ID(tax1.getC_Tax_ID()).setIsSOTrx(false).setDate(date_2016_01_01).build());
		assertVATCode(code3, VATCodeMatchingRequest.builder().setC_AcctSchema_ID(acctSchemaId).setC_Tax_ID(tax1.getC_Tax_ID()).setIsSOTrx(true).setDate(date_2016_01_01).build());

		newVATCodeBuilder().setC_Tax(tax1).setIsSOTrx(null).setValidFrom(date_2016_01_01).setVATCode(code1).build();
		assertVATCode(code1, VATCodeMatchingRequest.builder().setC_AcctSchema_ID(acctSchemaId).setC_Tax_ID(tax1.getC_Tax_ID()).setIsSOTrx(false).setDate(date_2016_01_01).build());
		assertVATCode(code1, VATCodeMatchingRequest.builder().setC_AcctSchema_ID(acctSchemaId).setC_Tax_ID(tax1.getC_Tax_ID()).setIsSOTrx(true).setDate(date_2016_01_01).build());

		// Test not matching
		assertVATCode(null, VATCodeMatchingRequest.builder().setC_AcctSchema_ID(acctSchemaId).setC_Tax_ID(tax3.getC_Tax_ID()).setIsSOTrx(true).setDate(date_2016_01_01).build());
	}

	private void assertVATCode(final VATCode expectedVATCode, final VATCodeMatchingRequest request)
	{
		final VATCode actualVATCode = vatCodeDAO.findVATCode(request).orElse(null);
		assertThat(actualVATCode)
				.as("request=" + request)
				.withFailMessage("Invalid VATCode for " + request)
				.isEqualTo(expectedVATCode);
	}

	private final I_C_Tax createTax(final String name)
	{
		final I_C_Tax tax = InterfaceWrapperHelper.create(ctx, I_C_Tax.class, ITrx.TRXNAME_None);
		tax.setName(name);
		InterfaceWrapperHelper.save(tax);
		return tax;
	}

	private final C_VAT_Code_Builder newVATCodeBuilder()
	{
		return C_VAT_Code_Builder.newBuilder()
				.setC_AcctSchema_ID(acctSchemaId);
	}
}
