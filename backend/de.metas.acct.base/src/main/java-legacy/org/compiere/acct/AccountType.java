package org.compiere.acct;

/*
 * #%L
 * de.metas.acct.base
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

public enum AccountType
{
	/** Invoice - Charge */
	Charge,
	/** Invoice - AR */
	C_Receivable,
	/** Invoice - AP */
	V_Liability,
	/** Invoice - AP Service */
	V_Liability_Services,
	/** Invoice - AR Service */
	C_Receivable_Services,

	/** Payment - Unallocated */
	UnallocatedCash,
	/** Payment - Transfer */
	BankInTransit,
	/** Payment - Selection */
	PaymentSelect,
	/** Payment - Prepayment */
	C_Prepayment,
	/** Payment - Prepayment */
	V_Prepayment,

	/** Cash - Asset */
	CashAsset,
	/** Cash - Transfer */
	CashTransfer,
	/** Cash - Expense */
	CashExpense,
	/** Cash - Receipt */
	CashReceipt,
	/** Cash - Difference */
	CashDifference,

	/** Allocation - Discount Expense (AR) */
	DiscountExp,
	/** Allocation - Discount Revenue (AP) */
	DiscountRev,
	/** Allocation - Write Off */
	WriteOff,
	/** Allocation - Pay Bank Fee */
	PayBankFee,

	/** Bank Statement - Asset */
	BankAsset,
	/** Bank Statement - Interest Revenue */
	InterestRev,
	/** Bank Statement - Interest Exp */
	InterestExp,

	/** Inventory Accounts - Differences */
	InvDifferences,
	/** Inventory Accounts - NIR */
	NotInvoicedReceipts,

	/** Project Accounts - Assets */
	ProjectAsset,
	/** Project Accounts - WIP */
	ProjectWIP,

	/** GL Accounts - PPV Offset */
	PPVOffset,
	// /** GL Accounts - Commitment Offset */
	// CommitmentOffset,
	// /** GL Accounts - Commitment Offset Sales */
	// CommitmentOffsetSales,
}
