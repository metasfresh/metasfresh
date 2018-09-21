package org.adempiere.misc.service;

import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;

import de.metas.util.ISingletonService;

public interface ITablePA extends ISingletonService {

	I_AD_Table retrieveTable(int adTableId, String trxName);

	I_AD_Column retrieveColumn(int columnId, String trxName);

	/**
	 * 
	 * @param table
	 * @param recordId
	 * @param trxName
	 * @return a PO instance
	 */
	Object retrievePO(I_AD_Table table, int recordId, String trxName);
}
