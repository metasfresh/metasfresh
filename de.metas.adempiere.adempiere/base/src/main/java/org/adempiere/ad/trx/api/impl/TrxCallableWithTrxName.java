package org.adempiere.ad.trx.api.impl;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.TrxCallable;
import org.adempiere.ad.wrapper.IInterfaceWrapperHelper;
import org.compiere.util.TrxRunnable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Legacy adapter interface: it's used to adapt {@link TrxRunnable#run(String)} calls to {@link TrxCallableWithTrxName#call(String)} calls.
 * 
 * This is needed because we want to provide to wrapped {@link TrxRunnable} exactly the same "localTrxName" which it was getting it before introducing the {@link TrxCallable} support.
 * 
 * The main reason for doing this are:
 * <ul>
 * <li>some of the JUnit tests are currently failing if we provide {@link ITrx#TRXNAME_ThreadInherited} instead of the actual "localTrxName"
 * <li>it might be that some other BLs would fail too.
 * </ul>
 * 
 * Main things to check before getting rid of this and always using {@link ITrx#TRXNAME_ThreadInherited}:
 * <ul>
 * <li>{@link IInterfaceWrapperHelper}'s model getters: atm they are String comparing the trxNames in order to find out if the cached model is still valid and can be returned.
 * <li>check all other places where trxNames are String compared and consider replacing that with {@link ITrxManager#isSameTrxName(ITrx, String)} which is handling the
 * {@link ITrx#TRXNAME_ThreadInherited} case
 * </ul>
 * 
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <ResultType>
 */
interface TrxCallableWithTrxName<ResultType> extends TrxCallable<ResultType>
{
	ResultType call(String localTrxName) throws Exception;
}
