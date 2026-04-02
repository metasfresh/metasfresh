package de.metas.cucumber.stepdefs.util;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.allocation.C_AllocationHdr_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.invoice.InvoiceId;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Simplified version for soft_panda_hotfix - supports invoice, payment, allocationHdr, inOut.
 * Full version on intensive_care_hotfix also supports dunning, matchInv, order.
 */
@RequiredArgsConstructor
public class IdentifiersResolver
{
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_Payment_StepDefData paymentTable;
	@NonNull private final C_AllocationHdr_StepDefData allocationTable;
	@NonNull private final M_InOut_StepDefData inOutTable;

	@NonNull
	public ImmutableSet<TableRecordReference> getTableRecordReferencesOfCommaSeparatedIdentifiers(@Nullable final String commaSeparatedIdentifiers)
	{
		final String norm = StringUtils.trimBlankToNull(commaSeparatedIdentifiers);
		if (norm == null || norm.equals("-"))
		{
			return ImmutableSet.of();
		}
		return getTableRecordReferences(StepDefDataIdentifier.ofCommaSeparatedString(norm));
	}

	@NonNull
	public ImmutableSet<TableRecordReference> getTableRecordReferences(@NonNull final List<StepDefDataIdentifier> identifiers)
	{
		final HashSet<TableRecordReference> result = new HashSet<>();
		for (final StepDefDataIdentifier identifier : identifiers)
		{
			result.add(getTableRecordReference(identifier));
		}
		return ImmutableSet.copyOf(result);
	}

	@NonNull
	public TableRecordReference getTableRecordReference(@NonNull final StepDefDataIdentifier identifier)
	{
		final ArrayList<TableRecordReference> candidates = new ArrayList<>();
		invoiceTable.getOptional(identifier)
				.map(inv -> TableRecordReference.of(I_C_Invoice.Table_Name, inv.getC_Invoice_ID()))
				.ifPresent(candidates::add);
		paymentTable.getOptional(identifier)
				.map(pay -> TableRecordReference.of(I_C_Payment.Table_Name, pay.getC_Payment_ID()))
				.ifPresent(candidates::add);
		allocationTable.getOptional(identifier)
				.map(alloc -> TableRecordReference.of(I_C_AllocationHdr.Table_Name, alloc.getC_AllocationHdr_ID()))
				.ifPresent(candidates::add);
		inOutTable.getOptional(identifier)
				.map(inOut -> TableRecordReference.of(I_M_InOut.Table_Name, inOut.getM_InOut_ID()))
				.ifPresent(candidates::add);

		if (candidates.isEmpty())
		{
			throw new AdempiereException("No record found for identifier: " + identifier);
		}
		if (candidates.size() > 1)
		{
			throw new AdempiereException("Multiple records found for identifier: " + identifier + " => " + candidates);
		}
		return candidates.get(0);
	}

	@NonNull
	public TableRecordReferenceSet getTableRecordReferenceSetOfCommaSeparatedIdentifiers(@Nullable final String commaSeparatedIdentifiers)
	{
		return TableRecordReferenceSet.of(getTableRecordReferencesOfCommaSeparatedIdentifiers(commaSeparatedIdentifiers));
	}

	@NonNull
	public Optional<InvoiceId> resolveOptionalInvoiceId(@Nullable final StepDefDataIdentifier identifier)
	{
		if (identifier == null)
		{
			return Optional.empty();
		}
		return invoiceTable.getOptional(identifier)
				.map(invoice -> InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
	}

	/** Reverse lookup: find the StepDefDataIdentifier for a given TableRecordReference */
	@NonNull
	public Optional<StepDefDataIdentifier> getIdentifier(@NonNull final TableRecordReference ref)
	{
		final String tableName = ref.getTableName();
		final int recordId = ref.getRecord_ID();

		if (I_C_Invoice.Table_Name.equals(tableName))
		{
			return invoiceTable.getIdentifiers().stream()
					.filter(id -> invoiceTable.getOptional(id).map(inv -> inv.getC_Invoice_ID() == recordId).orElse(false))
					.findFirst();
		}
		if (I_C_Payment.Table_Name.equals(tableName))
		{
			return paymentTable.getIdentifiers().stream()
					.filter(id -> paymentTable.getOptional(id).map(pay -> pay.getC_Payment_ID() == recordId).orElse(false))
					.findFirst();
		}
		if (I_C_AllocationHdr.Table_Name.equals(tableName))
		{
			return allocationTable.getIdentifiers().stream()
					.filter(id -> allocationTable.getOptional(id).map(alloc -> alloc.getC_AllocationHdr_ID() == recordId).orElse(false))
					.findFirst();
		}
		return Optional.empty();
	}
}
