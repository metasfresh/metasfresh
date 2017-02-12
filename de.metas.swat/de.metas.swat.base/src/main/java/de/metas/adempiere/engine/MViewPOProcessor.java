package de.metas.adempiere.engine;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.PO;

import de.metas.adempiere.model.I_AD_Table_MView;
import de.metas.adempiere.service.ITableMViewBL;
import de.metas.adempiere.service.ITableMViewBL.RefreshMode;
import de.metas.processor.spi.IPOProcessor;

public class MViewPOProcessor implements IPOProcessor
{

	@Override
	public Class<?> getTrxClass()
	{
		return I_AD_Table_MView.class;
	}

	@Override
	public boolean process(PO trx)
	{
		final ITableMViewBL mviewBL = Services.get(ITableMViewBL.class);

		final I_AD_Table_MView mview = InterfaceWrapperHelper.create(trx, I_AD_Table_MView.class);

		if (mview.isStaled())
		{
			mviewBL.refreshEx(mview, (PO)null, RefreshMode.Complete, trx.get_TrxName());
		}

		return true;
	}

}
