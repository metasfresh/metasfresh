package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.allocation.C_AllocationHdr_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class IdentifiersResolver
{
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_Payment_StepDefData paymentTable;
	@NonNull private final C_AllocationHdr_StepDefData allocationTable;

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
}
