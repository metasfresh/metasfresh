package de.metas.processor.spi;

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


import org.compiere.model.PO;

/**
 * Inbound transaction processor.
 * NOTE: please do not confound with database transaction
 * @author tsa
 *
 */
public interface IPOProcessor
{
	/**
	 * Transaction implementation class
	 * @return
	 */
	public Class<?> getTrxClass();
	
//	/**
//	 * TableName where transactions are stored
//	 * @return
//	 */
//	public String getSourceTable();

//	/**
//	 * If this method returns the internal name of an explicit relation type, then a relation record between the source
//	 * PO and the new order line candidate is automatically created.
//	 * 
//	 * @return
//	 */
//	String getRelationTypeInternalName();

	public boolean process(PO trx);

}
