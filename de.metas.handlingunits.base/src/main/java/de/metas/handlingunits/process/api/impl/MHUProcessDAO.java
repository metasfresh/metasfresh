package de.metas.handlingunits.process.api.impl;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;

import de.metas.handlingunits.model.I_M_HU_Process;
import de.metas.handlingunits.process.api.IMHUProcessDAO;

public class MHUProcessDAO implements IMHUProcessDAO
{

	@Override
	@Cached(cacheName = I_M_HU_Process.Table_Name + "#by#" + I_M_HU_Process.COLUMNNAME_AD_Process_ID)
	public I_M_HU_Process retrieveHUProcess(final int adProcessId)
	{
		return Services.get(IQueryBL.class).createQueryBuilderOutOfTrx(I_M_HU_Process.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Process.COLUMNNAME_AD_Process_ID, adProcessId)
				.create()
				.firstOnly(I_M_HU_Process.class); // There should be only one M_HU_Process active entry for an AD_Process_ID
	}

}
