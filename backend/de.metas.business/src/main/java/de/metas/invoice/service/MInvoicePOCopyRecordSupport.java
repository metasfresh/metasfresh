/**
 * 
 */
package de.metas.invoice.service;

import com.google.common.collect.ImmutableList;
import de.metas.copy_with_details.CopyRecordSupportTableInfo;
import de.metas.copy_with_details.GeneralCopyRecordSupport;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.PO;

import java.util.List;

/**
 * @author Cristina Ghita, METAS.RO
 *         version for copy an invoice
 */
public class MInvoicePOCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po)
	{
		return super.getSuggestedChildren(po)
				.stream()
				.filter(childTableInfo -> I_C_InvoiceLine.Table_Name.equals(childTableInfo.getTableName()))
				.collect(ImmutableList.toImmutableList());
	}
}
