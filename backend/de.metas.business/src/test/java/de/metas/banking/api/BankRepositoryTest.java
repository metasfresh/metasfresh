package de.metas.banking.api;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.banking.Bank;
import de.metas.banking.BankId;
import lombok.Builder;

/*
 * #%L
 * de.metas.business
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

public class BankRepositoryTest
{
	private BankRepository bankRepository;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		bankRepository = new BankRepository();
	}

	@Builder(builderMethodName = "bankRecord", builderClassName = "$BankRecordBuilder")
	private BankId createBankRecord(
			final String swiftCode,
			final boolean cashBank)
	{
		final I_C_Bank record = newInstance(I_C_Bank.class);
		record.setSwiftCode(swiftCode);
		record.setIsCashBank(cashBank);
		saveRecord(record);
		return BankId.ofRepoId(record.getC_Bank_ID());
	}

	@Nested
	public class getById
	{
		@Test
		public void test()
		{
			final BankId bankId = bankRecord()
					.swiftCode("BIC1")
					.cashBank(true)
					.build();

			assertThat(bankRepository.getById(bankId))
					.isEqualTo(Bank.builder()
							.bankId(bankId)
							.swiftCode("BIC1")
							.cashBank(true)
							.build());
		}
	}

	@Nested
	public class getBankIdBySwiftCode
	{
		@Test
		public void test()
		{
			final BankId bankId1 = bankRecord().swiftCode("BIC1").build();
			final BankId bankId2 = bankRecord().swiftCode("BIC2").build();

			assertThat(bankRepository.getBankIdBySwiftCode("BIC1")).contains(bankId1);
			assertThat(bankRepository.getBankIdBySwiftCode("BIC2")).contains(bankId2);
			assertThat(bankRepository.getBankIdBySwiftCode("BIC3")).isEmpty();
		}
	}

	@Nested
	public class isCashBank
	{
		@Test
		public void notCashBank()
		{
			final BankId bankId = bankRecord()
					.cashBank(false)
					.build();

			assertThat(bankRepository.isCashBank(bankId)).isFalse();
		}

		@Test
		public void cashBank()
		{
			final BankId bankId = bankRecord()
					.cashBank(true)
					.build();

			assertThat(bankRepository.isCashBank(bankId)).isTrue();
		}
	}
}
