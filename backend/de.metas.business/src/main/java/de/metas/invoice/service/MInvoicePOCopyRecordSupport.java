/**
 * 
 */
package de.metas.invoice.service;

import java.util.List;

import org.adempiere.model.CopyRecordSupportTableInfo;
import org.adempiere.model.GeneralCopyRecordSupport;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.PO;

import com.google.common.collect.ImmutableList;

/**
 * @author Cristina Ghita, METAS.RO
 *         version for copy an invoice
 */
public class MInvoicePOCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po, final List<CopyRecordSupportTableInfo> suggestedChildren)
	{
		return super.getSuggestedChildren(po, suggestedChildren)
				.stream()
				.filter(childTableInfo -> I_C_InvoiceLine.Table_Name.equals(childTableInfo.getTableName()))
				.collect(ImmutableList.toImmutableList());
	}
}
