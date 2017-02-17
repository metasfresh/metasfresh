package org.adempiere.ad.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.util.ILoggable;
import org.compiere.model.I_AD_Table;

public interface ITableSequenceChecker
{

	/**
	 * If set to <code>true</code>, then it will verify that the <code>currentNext</code> value is fine.
	 * <p>
	 * <b>IMPORTANT</b> (task 08607): if native sequences are enabled, then it will <b>always</b> do a sequence check (no matter what was set here), because it will reset the native sequence and needs
	 * to make sure to reset it to a value that is not already in use.
	 * 
	 * @param sequenceRangeCheck
	 * @return
	 * @see org.compiere.util.DB#isUseNativeSequences()
	 */
	ITableSequenceChecker setSequenceRangeCheck(boolean sequenceRangeCheck);

	void run();

	ITableSequenceChecker setTables(List<I_AD_Table> tables);

	ITableSequenceChecker setTable(I_AD_Table table);

	ITableSequenceChecker setFailOnFirstError(boolean failOnFirstError);

	ITableSequenceChecker setTrxName(String trxName);

}
