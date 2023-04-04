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

import de.metas.forex.ForexContractRef;
import de.metas.forex.process.utils.ForexContractParameters;
import de.metas.forex.process.utils.ForexContracts;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.process.IProcessDefaultParametersProvider;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
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
	public static String PARA_OnlyApprovedForInvoicing = "OnlyApprovedForInvoicing";
	public static String PARA_IsConsolidateApprovedICs = "IsConsolidateApprovedICs";
	public static String PARA_IgnoreInvoiceSchedule = "IgnoreInvoiceSchedule";
	public static String PARA_DateInvoiced = I_C_Invoice_Candidate.COLUMNNAME_DateInvoiced;
	public static String PARA_SupplementMissingPaymentTermIds = "SupplementMissingPaymentTermIds";
	public static String PARA_DateAcct = I_C_Invoice_Candidate.COLUMNNAME_DateAcct;
	public static String PARA_POReference = I_C_Invoice_Candidate.COLUMNNAME_POReference;
	public static String PARA_Check_NetAmtToInvoice = "Check_NetAmtToInvoice";
	public static String PARA_IsUpdateLocationAndContactForInvoice = "IsUpdateLocationAndContactForInvoice";
	public static String PARA_IsCompleteInvoices = "IsCompleteInvoices";
	public static String PARA_OverrideDueDate= "OverrideDueDate";

	boolean onlyApprovedForInvoicing;
	boolean consolidateApprovedICs;
	boolean ignoreInvoiceSchedule;
	boolean supplementMissingPaymentTermIds;
	boolean storeInvoicesInResult;
	boolean assumeOneInvoice;
	@Nullable LocalDate dateInvoiced;
	@Nullable LocalDate dateAcct;
	@Nullable LocalDate overrideDueDate;
	@Nullable String poReference;
	@Nullable BigDecimal check_NetAmtToInvoice;
	boolean updateLocationAndContactForInvoice;
	@Builder.Default boolean completeInvoices = true; // default=true for backwards-compatibility
	@Nullable ForexContractParameters forexContractParameters;

	@Nullable
	public ForexContractRef getForexContractRef()
	{
		return forexContractParameters != null ? forexContractParameters.getForexContractRef() : null;
	}

	public static InvoicingParams ofParams(@NonNull final IParams params)
	{
		return builder()
				.onlyApprovedForInvoicing(params.getParameterAsBool(PARA_OnlyApprovedForInvoicing))
				.consolidateApprovedICs(params.getParameterAsBool(PARA_IsConsolidateApprovedICs))
				.ignoreInvoiceSchedule(params.getParameterAsBool(PARA_IgnoreInvoiceSchedule))
				.dateInvoiced(params.getParameterAsLocalDate(PARA_DateInvoiced))
				.supplementMissingPaymentTermIds(params.getParameterAsBool(PARA_SupplementMissingPaymentTermIds))
				.dateAcct(params.getParameterAsLocalDate(PARA_DateAcct))
				.poReference(params.getParameterAsString(PARA_POReference))
				.check_NetAmtToInvoice(params.getParameterAsBigDecimal(PARA_Check_NetAmtToInvoice))
				.updateLocationAndContactForInvoice(params.getParameterAsBool(PARA_IsUpdateLocationAndContactForInvoice))
				.completeInvoices(params.getParameterAsBoolean(PARA_IsCompleteInvoices, true /*true for backwards-compatibility*/))
				.forexContractParameters(ForexContractParameters.ofParams(params))
				.overrideDueDate(params.getParameterAsLocalDate(PARA_OverrideDueDate))
				.build();
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
		map.put(PARA_SupplementMissingPaymentTermIds, isSupplementMissingPaymentTermIds());
		map.put(PARA_IsCompleteInvoices, isCompleteInvoices());

		final ForexContractParameters forexContractParameters = getForexContractParameters();
		if (forexContractParameters != null)
		{
			map.putAll(forexContractParameters.toMap());
		}

		return map;
	}

	@Nullable
	public Object getParameterDefaultValue(
			@NonNull final String parameterName,
			@Nullable final ForexContracts contracts)
	{
		switch (parameterName)
		{
			case ForexContractParameters.PARAM_IsFEC:
				return contracts != null && !contracts.isEmpty();
			case ForexContractParameters.PARAM_FEC_Order_Currency_ID:
				return contracts != null ? contracts.getOrderCurrencyId() : null;
			case ForexContractParameters.PARAM_C_ForeignExchangeContract_ID:
				return contracts != null ? contracts.getSingleForexContractIdOrNull() : null;
			case ForexContractParameters.PARAM_FEC_From_Currency_ID:
				return contracts != null ? contracts.suggestFromCurrencyId() : null;
			case ForexContractParameters.PARAM_FEC_To_Currency_ID:
				return contracts != null ? contracts.suggestToCurrencyId() : null;
			case ForexContractParameters.PARAM_FEC_CurrencyRate:
				return contracts != null ? contracts.suggestCurrencyRate() : null;
			default:
				return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

}
