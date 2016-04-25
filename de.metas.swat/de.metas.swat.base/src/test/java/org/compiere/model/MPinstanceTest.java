package org.compiere.model;

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


import java.util.Properties;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.adempiere.util.trxConstraints.api.ITrxConstraints;
import org.compiere.util.DB;
import org.junit.Ignore;
import org.junit.Test;

public class MPinstanceTest
{
	public static final Properties CTX = new Properties();
	
	@Mocked MProcess process;

	@Mocked MProcessPara processPara0;
	
	@Mocked MProcessPara processPara1;
	
	@Mocked MProcessPara processPara2;
	
	@Mocked DB db;
	
	/**
	 * When {@link MPInstance#MPInstance(MProcess, int)}, creates {@link MPInstancePara}s, it doesn't use the SeqNos
	 * from the processe's parameters, but created own seqNos.
	 * 
	 * @see task "02013: Prio: beim ausführen der Finanz-Berichte Summen und saldenlisten kommen drei Fehler hoch (2011081910000045)"
	 */
	@Test
	@Ignore
	public void renumbersParamSeqNos()
	{
		new NonStrictExpectations()
		{
			ITrxConstraints trxConstraints;	
			{
				process.getCtx(); result = CTX;
				
				process.getParameters(); result = new MProcessPara[] { processPara0, processPara1, processPara2 };
					
				processPara0.getSeqNo(); result = 10;
				
				processPara1.getSeqNo(); result = 20;
				
				// para2 has the same seqNo as para1
				processPara2.getSeqNo(); result = 20;
				
				DB.saveConstraints();
				DB.getConstraints(); 								result = trxConstraints;
				trxConstraints.incMaxTrx(anyInt); 					result = trxConstraints;
				trxConstraints.addAllowedTrxNamePrefix(anyString); 	result = trxConstraints;
				DB.restoreConstraints();
			}
		};
		
		final MPInstance pInstance = new MPInstance(process, 1, 23);
		
		new Verifications(){{
			
			// Note: this would be the old, wrong behavior
//			new MPInstancePara(withSameInstance(pInstance), withEqual(10)); times = 1;
//			new MPInstancePara(withSameInstance(pInstance), withEqual(20)); times = 2;
			
			new MPInstancePara(withSameInstance(pInstance), withEqual(10)); times = 1;
			new MPInstancePara(withSameInstance(pInstance), withEqual(20)); times = 1;
			new MPInstancePara(withSameInstance(pInstance), withEqual(30)); times = 1;
		}};
	
	}
}

