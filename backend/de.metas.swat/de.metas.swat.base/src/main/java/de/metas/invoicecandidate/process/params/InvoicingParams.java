package de.metas.invoicecandidate.process.params;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.banking.BankAccountId;
import de.metas.forex.ForexContractRef;
import de.metas.forex.process.utils.ForexContractParameters;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.adempiere.util.api.IParams;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Value
@Builder
public class InvoicingParams
{
	public static final String PARA_OnlyApprovedForInvoicing = "OnlyApprovedForInvoicing";
	public static final String PARA_IsConsolidateApprovedICs = "IsConsolidateApprovedICs";
	public static final String PARA_IgnoreInvoiceSchedule = "IgnoreInvoiceSchedule";
	public static final String PARA_DateInvoiced = I_C_Invoice_Candidate.COLUMNNAME_DateInvoiced;
	public static final String PARA_DateAcct = I_C_Invoice_Candidate.COLUMNNAME_DateAcct;
	public static final String PARA_POReference = I_C_Invoice_Candidate.COLUMNNAME_POReference;
	public static final String PARA_Check_NetAmtToInvoice = "Check_NetAmtToInvoice";
	public static final String PARA_IsUpdateLocationAndContactForInvoice = "IsUpdateLocationAndContactForInvoice";
	public static final String PARA_IsCompleteInvoices = "IsCompleteInvoices";
	public static final String PARA_OverrideDueDate = "OverrideDueDate";
	public static final String PARA_IsInvoicingSingleBPartner = "IsInvoicingSingleBPartner";
	public static final String PARA_BP_BankAccountId = "C_BP_BankAccount_ID";

	boolean onlyApprovedForInvoicing;
	boolean consolidateApprovedICs;
	boolean ignoreInvoiceSchedule;
	boolean storeInvoicesInResult;
	boolean assumeOneInvoice;
	boolean isInvoicingSingleBPartner;
	@Nullable LocalDate dateInvoiced;
	@Nullable LocalDate dateAcct;
	@Nullable String poReference;
	@Nullable BigDecimal check_NetAmtToInvoice;
	boolean updateLocationAndContactForInvoice;
	@Builder.Default boolean completeInvoices = true; // default=true for backwards-compatibility
	@Nullable ForexContractParameters forexContractParameters;
	@NonFinal @Nullable LocalDate overrideDueDate;
	@Nullable BankAccountId bankAccountId;

	public static InvoicingParams ofParams(@NonNull final IParams params)
	{
		return builder()
				.onlyApprovedForInvoicing(params.getParameterAsBool(PARA_OnlyApprovedForInvoicing))
				.consolidateApprovedICs(params.getParameterAsBool(PARA_IsConsolidateApprovedICs))
				.ignoreInvoiceSchedule(params.getParameterAsBool(PARA_IgnoreInvoiceSchedule))
				.dateInvoiced(params.getParameterAsLocalDate(PARA_DateInvoiced))
				.dateAcct(params.getParameterAsLocalDate(PARA_DateAcct))
				.poReference(params.getParameterAsString(PARA_POReference))
				.check_NetAmtToInvoice(params.getParameterAsBigDecimal(PARA_Check_NetAmtToInvoice))
				.updateLocationAndContactForInvoice(params.getParameterAsBool(PARA_IsUpdateLocationAndContactForInvoice))
				.completeInvoices(params.getParameterAsBoolean(PARA_IsCompleteInvoices, true /*true for backwards-compatibility*/))
				.forexContractParameters(ForexContractParameters.ofParams(params))
				.overrideDueDate(params.getParameterAsLocalDate(PARA_OverrideDueDate))
				.isInvoicingSingleBPartner(params.getParameterAsBoolean(PARA_IsInvoicingSingleBPartner, false))
				.bankAccountId(params.getParameterAsId(PARA_BP_BankAccountId, BankAccountId.class))
				.build();
	}

	@Nullable
	public ForexContractRef getForexContractRef()
	{
		return forexContractParameters != null ? forexContractParameters.getForexContractRef() : null;
	}

	public Map<String, ?> toMap()
	{
		final HashMap<String, Object> map = new HashMap<>();

		if (getCheck_NetAmtToInvoice() != null)
		{
			map.put(PARA_Check_NetAmtToInvoice, getCheck_NetAmtToInvoice()); // during enqueuing this map might be overwritten by a specific value
		}
		if (getDateAcct() != null)
		{
			map.put(PARA_DateAcct, getDateAcct());
		}
		if (getDateInvoiced() != null)
		{
			map.put(PARA_DateInvoiced, getDateInvoiced());
		}
		if (getPoReference() != null)
		{
			map.put(PARA_POReference, getPoReference());
		}
		if (getOverrideDueDate() != null)
		{
			map.put(PARA_OverrideDueDate, getOverrideDueDate());
		}

		map.put(PARA_IgnoreInvoiceSchedule, isIgnoreInvoiceSchedule());
		map.put(PARA_IsConsolidateApprovedICs, isConsolidateApprovedICs());
		map.put(PARA_IsUpdateLocationAndContactForInvoice, isUpdateLocationAndContactForInvoice());
		map.put(PARA_OnlyApprovedForInvoicing, isOnlyApprovedForInvoicing());
		map.put(PARA_IsCompleteInvoices, isCompleteInvoices());

		final ForexContractParameters forexContractParameters = getForexContractParameters();
		if (forexContractParameters != null)
		{
			map.putAll(forexContractParameters.toMap());
		}

		return map;
	}

	public void updateOnDateInvoicedParameterChanged(@NonNull final LocalDate dueDate) {this.overrideDueDate = dueDate;}
}
