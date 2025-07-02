/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.util;

import com.google.common.collect.ImmutableSet;
import de.metas.allocation.api.PaymentAllocationId;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.allocation.C_AllocationHdr_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.match_inv.M_MatchInv_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_MatchInv;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class IdentifiersResolver
{
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_Payment_StepDefData paymentTable;
	@NonNull private final C_AllocationHdr_StepDefData allocationTable;
	@NonNull private final M_MatchInv_StepDefData matchInvTable;
	@NonNull private final M_InOut_StepDefData inOutTable;
	@NonNull private final C_Order_StepDefData orderTable;

	@NonNull
	public ImmutableSet<TableRecordReference> getTableRecordReferencesOfCommaSeparatedIdentifiers(@Nullable final String commaSeparatedIdentifiers)
	{
		final String commaSeparatedIdentifiersNorm = StringUtils.trimBlankToNull(commaSeparatedIdentifiers);
		if (commaSeparatedIdentifiersNorm == null || commaSeparatedIdentifiersNorm.equals("-"))
		{
			return ImmutableSet.of();
		}

		return getTableRecordReferences(StepDefDataIdentifier.ofCommaSeparatedString(commaSeparatedIdentifiersNorm));
	}

	@NonNull
	public ImmutableSet<TableRecordReference> getTableRecordReferences(@NonNull final List<StepDefDataIdentifier> identifiers)
	{
		return identifiers.stream()
				.map(this::getTableRecordReference)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public TableRecordReference getTableRecordReference(@NonNull final StepDefDataIdentifier identifier)
	{
		final ArrayList<TableRecordReference> result = new ArrayList<>();
		invoiceTable.getIdOptional(identifier)
				.map(id -> TableRecordReference.of(I_C_Invoice.Table_Name, id))
				.ifPresent(result::add);
		paymentTable.getIdOptional(identifier)
				.map(id -> TableRecordReference.of(I_C_Payment.Table_Name, id))
				.ifPresent(result::add);
		allocationTable.getIdOptional(identifier)
				.map(id -> TableRecordReference.of(I_C_AllocationHdr.Table_Name, id))
				.ifPresent(result::add);
		matchInvTable.getIdOptional(identifier)
				.map(MatchInvId::toRecordRef)
				.ifPresent(result::add);
		inOutTable.getIdOptional(identifier)
				.map(InOutId::toRecordRef)
				.ifPresent(result::add);
		orderTable.getIdOptional(identifier)
				.map(OrderId::toRecordRef)
				.ifPresent(result::add);

		if (result.isEmpty())
		{
			throw new AdempiereException("No known document found for identifier: " + identifier);
		}
		else if (result.size() > 1)
		{
			throw new AdempiereException("More than one document found for identifier " + identifier + ": " + result);
		}
		else
		{
			return result.get(0);
		}
	}

	public Optional<StepDefDataIdentifier> getIdentifier(final TableRecordReference documentRef)
	{
		final String tableName = documentRef.getTableName();
		final int recordId = documentRef.getRecord_ID();

		switch (tableName)
		{
			case I_C_Invoice.Table_Name:
				return invoiceTable.getFirstIdentifierById(InvoiceId.ofRepoId(recordId));
			case I_C_Payment.Table_Name:
				return paymentTable.getFirstIdentifierById(PaymentId.ofRepoId(recordId));
			case I_C_AllocationHdr.Table_Name:
				return allocationTable.getFirstIdentifierById(PaymentAllocationId.ofRepoId(recordId));
			case I_M_MatchInv.Table_Name:
				return matchInvTable.getFirstIdentifierById(MatchInvId.ofRepoId(recordId));
			case I_M_InOut.Table_Name:
				return inOutTable.getFirstIdentifierById(InOutId.ofRepoId(recordId));
			default:
				return Optional.empty();
		}
	}

	public TableRecordReferenceSet getAccountableDocumentRefs()
	{
		final HashSet<TableRecordReference> result = new HashSet<>();

		invoiceTable.streamIds()
				.map(id -> TableRecordReference.of(I_C_Invoice.Table_Name, id))
				.forEach(result::add);
		paymentTable.streamIds()
				.map(id -> TableRecordReference.of(I_C_Payment.Table_Name, id))
				.forEach(result::add);
		allocationTable.streamIds()
				.map(id -> TableRecordReference.of(I_C_AllocationHdr.Table_Name, id))
				.forEach(result::add);
		matchInvTable.streamIds()
				.map(MatchInvId::toRecordRef)
				.forEach(result::add);
		inOutTable.streamIds()
				.map(InOutId::toRecordRef)
				.forEach(result::add);

		return TableRecordReferenceSet.of(result);
	}
}
