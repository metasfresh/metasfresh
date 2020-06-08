package de.metas.payment.esr.api.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.banking.api.BankRepository;
import de.metas.payment.esr.api.IESRBPBankAccountBL;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class ESRBPBankAccountBLTest
{
	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void getService()
	{
		final ESRBPBankAccountBL esrBankAccountBL = new ESRBPBankAccountBL(new BankRepository());
		SpringContextHolder.registerJUnitBean(IESRBPBankAccountBL.class, esrBankAccountBL);

		final IESRBPBankAccountBL service = Services.get(IESRBPBankAccountBL.class);
		assertThat(service).isSameAs(esrBankAccountBL);
	}
}
