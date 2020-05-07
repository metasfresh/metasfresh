package org.adempiere.util.db;

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

/*
 * To use this code, uncomment the spring dependencies in ivy.xml
 */

//import org.compiere.util.Trx;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionException;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.SimpleTransactionStatus;

/**
 * This is an attempt to allow usage of spring transaction management into
 * ADempiere with its 'trxName' based transaction management. The expected
 * result of using spring is less repetition of boilerplate code.
 * 
 * @author ts
 * 
 */
public final class ADempiereTrxManager /*implements PlatformTransactionManager */ {

//	private String trxName;
//
//	public ADempiereTrxManager(final String trxName) {
//
//		this.trxName = trxName;
//	}

//	public void commit(TransactionStatus status) throws TransactionException {
//
//		Trx.get(trxName, true).commit();
//	}
//
//	public TransactionStatus getTransaction(TransactionDefinition def)
//			throws TransactionException {
//
//		Trx.get(def.getName(), true);
//
//		final TransactionStatus status = new SimpleTransactionStatus(true);
//		return status;
//	}
//
//	public void rollback(TransactionStatus status) throws TransactionException {
//
//		Trx.get(trxName, true).rollback();
//	}

}
