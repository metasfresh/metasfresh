package de.metas.distribution.ddorder.lowlevel.interceptor;

import com.google.common.collect.ImmutableList;
import org.adempiere.model.CopyRecordSupportTableInfo;
import org.adempiere.model.GeneralCopyRecordSupport;
import org.compiere.model.PO;

import java.util.List;

public class DDOrderLinePOCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	@SuppressWarnings("MethodDoesntCallSuperMethod")
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po, final List<CopyRecordSupportTableInfo> suggestedChildren)
	{
		return ImmutableList.of();
	}
}
