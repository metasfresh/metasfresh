package de.metas.banking.service;

import de.metas.banking.BankAccountId;
import de.metas.banking.importfile.BankStatementImportFileId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

/*
 * #%L
 * de.metas.banking.base
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

@Value
@Builder
public class BankStatementCreateRequest
{
	@Nullable
	BankStatementImportFileId bankStatementImportFileId;
	
	@NonNull
	OrgId orgId;

	@NonNull
	BankAccountId orgBankAccountId;

	@NonNull
	LocalDate statementDate;

	@NonNull
	String name;

	@Nullable
	String description;
	
	@Nullable 
	BigDecimal beginningBalance;

	@Nullable
	ElectronicFundsTransfer eft;

	@Value
	@Builder
	public static class ElectronicFundsTransfer
	{
		LocalDate statementDate;
		String statementReference;
	}
}
