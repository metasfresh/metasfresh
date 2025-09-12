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

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
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
	public static String PARA_DateAcct = I_C_Invoice_Candidate.COLUMNNAME_DateAcct;
	public static String PARA_POReference = I_C_Invoice_Candidate.COLUMNNAME_POReference;
	public static String PARA_Check_NetAmtToInvoice = "Check_NetAmtToInvoice";
	public static String PARA_IsUpdateLocationAndContactForInvoice = "IsUpdateLocationAndContactForInvoice";
	public static String PARA_SupplementMissingPaymentTermIds = "SupplementMissingPaymentTermIds";
	public static final String PARA_IsCompleteInvoices = "IsCompleteInvoices";

	boolean onlyApprovedForInvoicing;
	boolean consolidateApprovedICs;
	boolean ignoreInvoiceSchedule;
	boolean storeInvoicesInResult;
	boolean assumeOneInvoice;
	@Builder.Default boolean supplementMissingPaymentTermIds = true; // default true because is legacy code which no longer exists in master
	@Nullable LocalDate dateInvoiced;
	@Nullable LocalDate dateAcct;
	@Nullable String poReference;
	@Nullable BigDecimal check_NetAmtToInvoice;
	boolean updateLocationAndContactForInvoice;
	@Builder.Default boolean completeInvoices = true; // default=true for backwards-compatibility

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
				.supplementMissingPaymentTermIds(params.getParameterAsBool(PARA_SupplementMissingPaymentTermIds))
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

		map.put(PARA_IgnoreInvoiceSchedule, isIgnoreInvoiceSchedule());
		map.put(PARA_IsConsolidateApprovedICs, isConsolidateApprovedICs());
		map.put(PARA_IsUpdateLocationAndContactForInvoice, isUpdateLocationAndContactForInvoice());
		map.put(PARA_OnlyApprovedForInvoicing, isOnlyApprovedForInvoicing());
		map.put(PARA_SupplementMissingPaymentTermIds, isSupplementMissingPaymentTermIds());
		map.put(PARA_IsCompleteInvoices, isCompleteInvoices());

		return map;
	}
}
