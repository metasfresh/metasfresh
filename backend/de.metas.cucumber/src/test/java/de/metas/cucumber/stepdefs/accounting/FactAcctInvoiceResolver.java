package de.metas.cucumber.stepdefs.accounting;

import de.metas.invoice.InvoiceId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_Fact_Acct;

import javax.annotation.Nullable;

/**
 * Resolves the {@link InvoiceId} associated with a {@link I_Fact_Acct} record.
 * <p>
 * Resolution logic:
 * <ul>
 *     <li>If AD_Table_ID = C_Invoice: the Fact_Acct is posted for the invoice itself, so Record_ID is the C_Invoice_ID</li>
 *     <li>If AD_Table_ID = C_AllocationHdr: the Fact_Acct.Line_ID is the C_AllocationLine_ID; load the C_AllocationLine and return its C_Invoice_ID</li>
 *     <li>Otherwise: return {@code null} (C_Invoice_ID resolution not applicable for this document type)</li>
 * </ul>
 */
@UtilityClass
public class FactAcctInvoiceResolver
{
	@Nullable
	public static InvoiceId resolveInvoiceIdOrNull(@NonNull final I_Fact_Acct record)
	{
		final String tableName = TableIdsCache.instance.getTableName(AdTableId.ofRepoId(record.getAD_Table_ID()));

		if (I_C_Invoice.Table_Name.equals(tableName))
		{
			return InvoiceId.ofRepoIdOrNull(record.getRecord_ID());
		}
		else if (I_C_AllocationHdr.Table_Name.equals(tableName))
		{
			final int allocationLineId = record.getLine_ID();
			Check.assume(allocationLineId > 0, "Fact_Acct record for C_AllocationHdr must have a Line_ID (C_AllocationLine_ID): {}", record);

			final I_C_AllocationLine allocationLine = InterfaceWrapperHelper.load(allocationLineId, I_C_AllocationLine.class);
			return InvoiceId.ofRepoIdOrNull(allocationLine.getC_Invoice_ID());
		}
		else
		{
			return null;
		}
	}
}
