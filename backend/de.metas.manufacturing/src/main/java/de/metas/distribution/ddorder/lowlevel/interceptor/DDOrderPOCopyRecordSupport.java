package de.metas.distribution.ddorder.lowlevel.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.copy_with_details.CopyRecordSupportTableInfo;
import de.metas.copy_with_details.GeneralCopyRecordSupport;
import org.compiere.model.PO;
import org.eevolution.model.I_DD_OrderLine;

import java.util.List;

public class DDOrderPOCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po)
	{
		return super.getSuggestedChildren(po)
				.stream()
				.filter(childTableInfo -> I_DD_OrderLine.Table_Name.equals(childTableInfo.getTableName()))
				.collect(ImmutableList.toImmutableList());
	}
}
