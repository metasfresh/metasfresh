package de.metas.banking.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import de.metas.banking.BankStatementId;
import de.metas.bpartner.BPartnerId;
import de.metas.costing.ChargeId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.util.lang.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
public class BankStatementLineCreateRequest
{
	@NonNull
	BankStatementId bankStatementId;

	@NonNull
	OrgId orgId;

	int lineNo;

	BPartnerId bpartnerId;
	String importedBillPartnerName;
	String importedBillPartnerIBAN;

	String referenceNo;
	String lineDescription;
	String memo;

	@NonNull
	LocalDate statementLineDate;
	@NonNull
	LocalDate dateAcct;
	@NonNull
	LocalDate valutaDate;

	@NonNull
	Money statementAmt;
	@NonNull
	Money trxAmt;
	@NonNull
	Money bankFeeAmt;
	@NonNull
	Money chargeAmt;
	@NonNull
	Money interestAmt;

	ChargeId chargeId;

	@Builder
	private BankStatementLineCreateRequest(
			@NonNull final BankStatementId bankStatementId,
			@NonNull final OrgId orgId,
			final int lineNo,
			//
			@Nullable final BPartnerId bpartnerId,
			@Nullable final String importedBillPartnerName,
			@Nullable final String importedBillPartnerIBAN,
			//
			@Nullable final String referenceNo,
			@Nullable final String lineDescription,
			@Nullable final String memo,
			//
			@NonNull final LocalDate statementLineDate,
			@Nullable final LocalDate dateAcct,
			@Nullable final LocalDate valutaDate,
			//
			@NonNull final Money statementAmt,
			@Nullable final Money trxAmt,
			@Nullable final Money bankFeeAmt,
			@Nullable final Money chargeAmt,
			@Nullable final Money interestAmt,
			@Nullable final ChargeId chargeId,
			//
			@Nullable final ElectronicFundsTransfer eft)
	{
		this.bankStatementId = bankStatementId;
		this.orgId = orgId;
		this.lineNo = lineNo;
		//
		this.bpartnerId = bpartnerId;
		this.importedBillPartnerName = importedBillPartnerName;
		this.importedBillPartnerIBAN = importedBillPartnerIBAN;
		//
		this.referenceNo = referenceNo;
		this.lineDescription = lineDescription;
		this.memo = memo;
		//
		this.statementLineDate = statementLineDate;
		this.dateAcct = CoalesceUtil.coalesce(dateAcct, statementLineDate);
		this.valutaDate = CoalesceUtil.coalesce(valutaDate, statementLineDate);
		//
		this.statementAmt = statementAmt;
		this.trxAmt = trxAmt != null ? trxAmt : statementAmt.toZero();
		this.bankFeeAmt = bankFeeAmt != null ? bankFeeAmt : statementAmt.toZero();
		this.chargeAmt = chargeAmt != null ? chargeAmt : statementAmt.toZero();
		this.interestAmt = interestAmt != null ? interestAmt : statementAmt.toZero();
		this.chargeId = chargeId;
		Money.getCommonCurrencyIdOfAll(this.statementAmt, this.trxAmt, this.chargeAmt, this.interestAmt);
		//
		this.eft = eft;
	}

	@Nullable
	ElectronicFundsTransfer eft;

	/** EFT */
	@Value
	@Builder
	public static class ElectronicFundsTransfer
	{
		String trxId;
		String trxType;
		String checkNo;
		String reference;
		String memo;
		String payee;
		String payeeAccount;
		LocalDate statementLineDate;
		LocalDate valutaDate;
		String currency;
		BigDecimal amt;
	}
}
