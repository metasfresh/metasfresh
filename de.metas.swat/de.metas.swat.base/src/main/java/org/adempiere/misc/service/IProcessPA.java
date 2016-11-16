package org.adempiere.misc.service;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.service.IADProcessDAO;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_PInstance_Para;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.process.SvrProcess;

/**
 *
 * @deprecated Please use {@link IADProcessDAO}
 */
@Deprecated
public interface IProcessPA extends ISingletonService {

	List<? extends I_AD_PInstance> retrieveRunningInstances(final Class<? extends SvrProcess> clazz,
			final String trxName);

	/**
	 * 
	 * @param clazz
	 * @param trxName
	 * @return
	 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
	 */
	int retrieveProcessId(Class<? extends SvrProcess> clazz, String trxName);
	
	I_AD_Process retrieveProcess(Properties ctx, String value, String trxName);

	I_AD_Process_Para retrieveProcessPara(Properties ctx, int adProcessId, String trxName);
	
	I_AD_Process retrieveProcessByForm(Properties ctx, int AD_Form_ID, String trxName);

	/**
	 * Sets given <code>value</code> to <code>pipa</code>. Based on <code>value</code> type, this methods decides which field to set ( {@link I_AD_PInstance_Para#COLUMNNAME_P_String},
	 * {@link I_AD_PInstance_Para#COLUMNNAME_P_Date} etc).
	 * 
	 * @param pipa
	 * @param value
	 * @return true if value was set, false if value was not set because is not supported
	 */
	boolean setPInstanceParaValue(I_AD_PInstance_Para pipa, Object value);
}
