package de.metas.cucumber.stepdefs.allocation;

import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.X_C_DocType;

import java.util.List;

/**
 * Looks up the payment bonus credit memos („Zahlungsbonus-Gutschrift“) that book the bonus a customer deducted from a sales invoice.
 */
public final class PaymentBonusCreditMemoQueries
{
	private PaymentBonusCreditMemoQueries()
	{
	}

	/**
	 * @return the completed payment bonus credit memos that reference the given sales invoice
	 */
	@NonNull
	public static List<I_C_Invoice> retrieveCompletedCreditMemos(@NonNull final InvoiceId salesInvoiceId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_C_Invoice.class)
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_Ref_Invoice_ID, salesInvoiceId)
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_DocStatus, DocStatus.Completed.getCode())
				.addInSubQueryFilter(I_C_Invoice.COLUMNNAME_C_DocType_ID,
						I_C_DocType.COLUMNNAME_C_DocType_ID,
						queryBL.createQueryBuilder(I_C_DocType.class)
								.addEqualsFilter(I_C_DocType.COLUMNNAME_DocBaseType, X_C_DocType.DOCBASETYPE_ARCreditMemo)
								.addEqualsFilter(I_C_DocType.COLUMNNAME_DocSubType, X_C_DocType.DOCSUBTYPE_PaymentBonusCreditMemo)
								.create())
				.create()
				.list();
	}
}
