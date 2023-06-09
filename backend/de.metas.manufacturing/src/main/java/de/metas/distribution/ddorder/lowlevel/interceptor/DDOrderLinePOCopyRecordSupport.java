package de.metas.distribution.ddorder.lowlevel.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.copy_with_details.CopyRecordSupportTableInfo;
import de.metas.copy_with_details.GeneralCopyRecordSupport;
import org.compiere.model.PO;

import java.util.List;

public class DDOrderLinePOCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po)
	{
		return ImmutableList.of();
	}
}
