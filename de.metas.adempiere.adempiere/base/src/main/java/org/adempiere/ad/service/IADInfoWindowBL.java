package org.adempiere.ad.service;

import org.compiere.model.I_AD_InfoWindow;

import de.metas.util.ISingletonService;

public interface IADInfoWindowBL extends ISingletonService
{

	String getSqlFrom(I_AD_InfoWindow infoWindow);

	String getTableName(I_AD_InfoWindow infoWindow);

	String getTreeColumnName(I_AD_InfoWindow infoWindow);

	int getAD_Tree_ID(I_AD_InfoWindow infoWindow);

	boolean isShowTotals(I_AD_InfoWindow infoWindow);

}
