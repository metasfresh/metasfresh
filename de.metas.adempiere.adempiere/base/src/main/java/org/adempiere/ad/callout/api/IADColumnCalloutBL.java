package org.adempiere.ad.callout.api;

import org.compiere.model.I_AD_Column;

import de.metas.util.ISingletonService;

public interface IADColumnCalloutBL extends ISingletonService
{

	/**
	 * Copy all column callouts from fromColumn_ID to targetColumn
	 * 
	 * @param targetColumn
	 * @param fromColumn_ID
	 */
	void copyAllFrom(I_AD_Column targetColumn, int fromColumn_ID);

}
