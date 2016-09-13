/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/

package org.eevolution.model.wrapper;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.MAttachment;
import org.compiere.model.PO;

/**
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 */
public abstract class AbstractPOWrapper {
	
	protected abstract PO receivePO(Properties ctx, int id, String trxName, String type);
	
	public AbstractPOWrapper(Properties ctx, int id, String trxName, String type) {
		
		po = receivePO(ctx, id, trxName, type);
	}

	protected PO po;

	public PO get() {
		
		return po;
	}
	
	/**
	 *  String representation
	 *  @return String representation
	 */
	@Override
	public String toString() {
		
		return po.toString(); //  toString
	}

	/**
	 * 	Equals based on ID
	 * 	@param cmp comparator
	 * 	@return true if ID the same
	 */
	@Override
	public boolean equals(Object cmp) {
		
		return po.equals(cmp); //	equals
	}

	/**
	 * 	Compare based on DocumentNo, Value, Name, Description
	 *	@param o1 Object 1
	 *	@param o2 Object 2
	 *	@return -1 if o1 < o2
	 */
	public int compare(Object o1, Object o2) {
		
		return po.compare(o1, o2); //	compare
	}

	/**
	 *  Get TableName.
	 *  @return table name
	 */
	public String get_TableName() {
		
		return po.get_TableName(); //  getTableName
	}

	/**
	 *  Return Single Key Record ID
	 *  @return ID or 0
	 */
	public int getID() {
		
		return po.get_ID(); //  getID
	}

	/**
	 *  Return Deleted Single Key Record ID
	 *  @return ID or 0
	 */
	public int getIDOld() {
		
		return po.get_IDOld(); //  getID
	}

	/**
	 * 	Get Context
	 * 	@return context
	 */
	public Properties getCtx() {
		
		return po.getCtx(); //	getCtx
	}

	/**************************************************************************
	 *  Get Value
	 *  @param index index
	 *  @return value
	 */
	public Object get_Value(int index) {
		
		return  po.get_Value(index); //  get_Value
	}

	/**
	 *  Get Value
	 *  @param columnName column name
	 *  @return value or null
	 */
	public Object get_Value(String columnName) {
		
		return  po.get_Value(columnName); //  get_Value
	}

	/**
	 * 	Get Column Value
	 *	@param variableName name
	 *	@return value or ""
	 */
	public String get_ValueAsString(String variableName) {
		
		return  po.get_ValueAsString(variableName); //	get_ValueAsString
	}

	/**
	 *  Get Value of Column
	 *  @param AD_Column_ID column
	 *  @return value or null
	 */
	public Object get_ValueOfColumn(int AD_Column_ID) {
		
		return  po.get_ValueOfColumn(AD_Column_ID); //  get_ValueOfColumn
	}

	/**
	 *  Get Old Value
	 *  @param index index
	 *  @return value
	 */
	public Object get_ValueOld(int index) {
		
		return  po.get_ValueOld(index); //  get_ValueOld
	}

	/**
	 *  Get Old Value
	 *  @param columnName column name
	 *  @return value or null
	 */
	public Object get_ValueOld(String columnName) {
		
		return  po.get_ValueOld(columnName); //  get_ValueOld
	}

	/**
	 *  Is Value Changed
	 *  @param index index
	 *  @return true if changed
	 */
	public boolean is_ValueChanged(int index) {
		
		return po.is_ValueChanged(index); //  is_ValueChanged
	}

	/**
	 *  Is Value Changed
	 *  @param columnName column name
	 *  @return true if changed
	 */
	public boolean is_ValueChanged(String columnName) {
		
		return  po.is_ValueChanged(columnName); //  is_ValueChanged
	}

	/**
	 *  Return new - old.
	 * 	- New Value if Old Value is null
	 * 	- New Value - Old Value if Number 
	 * 	- otherwise null
	 *  @param index index
	 *  @return new - old or null if not appropriate or not changed
	 */
	public Object get_ValueDifference(int index) {
		
		return  po.get_ValueDifference(index); //  get_ValueDifference
	}

	/**
	 *  Return new - old.
	 * 	- New Value if Old Value is null
	 * 	- New Value - Old Value if Number 
	 * 	- otherwise null
	 *  @param columnName column name
	 *  @return new - old or null if not appropriate or not changed
	 */
	public Object get_ValueDifference(String columnName) {
		
		return  po.get_ValueDifference(columnName); //  get_ValueDifference
	}

	/**
	 *  Set Value of Column
	 *  @param AD_Column_ID column
	 *  @param value value
	 */
	public void set_ValueOfColumn(int AD_Column_ID, Object value) {
		
		po.set_ValueOfAD_Column_ID(AD_Column_ID, value);
	}

	/**
	 * 	Set Custom Column
	 *	@param columnName column
	 *	@param value value
	 */
	public void set_CustomColumn(String columnName, Object value) {
		
		po.set_CustomColumn(columnName, value); //	set_CustomColumn
	}

	/**
	 *  Get Column Index
	 *  @param columnName column name
	 *  @return index of column with ColumnName or -1 if not found
	 */
	public int get_ColumnIndex(String columnName) {
		
		return po.get_ColumnIndex(columnName); //  getColumnIndex
	}

	/**
	 *  (re)Load record with m_ID[*]
	 */
	public boolean load(String trxName) {
		
		return po.load(trxName); //  load
	}

	/**
	 * 	Get AD_Client
	 * 	@return AD_Client_ID
	 */
	public int getAD_Client_ID() {
		
		return po.getAD_Client_ID(); //	getAD_Client_ID
	}

	/**
	 * 	Get AD_Org
	 * 	@return AD_Org_ID
	 */
	public int getAD_Org_ID() {
		
		return po.getAD_Org_ID(); //	getAD_Org_ID
	}

	/**
	 * 	Set Active
	 * 	@param active active
	 */
	public void setIsActive(boolean active) {
		
		po.setIsActive(active); //	setActive
	}

	/**
	 *	Is Active
	 *  @return is active
	 */
	public boolean isActive() {
		
		return po.isActive(); //	isActive
	}

	/**
	 * 	Get Created
	 * 	@return created
	 */
	public Timestamp getCreated() {
		
		return po.getCreated(); //	getCreated
	}

	/**
	 * 	Get Updated
	 *	@return updated
	 */
	public Timestamp getUpdated() {
		
		return po.getUpdated(); //	getUpdated
	}

	/**
	 * 	Get CreatedBy
	 * 	@return AD_User_ID
	 */
	public int getCreatedBy() {
		
		return po.getCreatedBy(); //	getCreateddBy
	}

	/**
	 * 	Get UpdatedBy
	 * 	@return AD_User_ID
	 */
	public int getUpdatedBy() {
		
		return po.getUpdatedBy(); //	getUpdatedBy
	}

	/**************************************************************************
	 *  Update Value or create new record.
	 * 	To reload call load() - not updated
	 *  @return true if saved
	 */
	public boolean save() {
		
		return po.save(); //	save
	}

	/**
	 *  Update Value or create new record.
	 * 	To reload call load() - not updated
	 *	@param trxName transaction
	 *  @return true if saved
	 */
	public boolean save(String trxName) {
		
		return po.save(trxName); //	save
	}

	/**
	 * 	Is there a Change to be saved?
	 *	@return true if record changed
	 */
	public boolean is_Changed() {
		
		return po.is_Changed(); //	is_Change
	}

	/**
	 * 	Create Single/Multi Key Where Clause
	 * 	@param withValues if true uses actual values otherwise ?
	 * 	@return where clause
	 */
	public String get_WhereClause(boolean withValues) {
		
		return po.get_WhereClause(withValues); //	getWhereClause
	}

	/**************************************************************************
	 * 	Delete Current Record
	 * 	@param force delete also processed records
	 * 	@return true if deleted
	 */
	public boolean delete(boolean force) {
		
		return po.delete(force); //	delete
	}

	/**
	 * 	Delete Current Record
	 * 	@param force delete also processed records
	 *	@param trxName transaction
	 */
	public boolean delete(boolean force, String trxName) {
		
		return po.delete(force, trxName); //	delete
	}

	/**************************************************************************
	 * 	Lock it.
	 * 	@return true if locked
	 */
	public boolean lock() {
		
		return po.lock(); //	lock
	}

	/**
	 * 	UnLock it
	 * 	@return true if unlocked (false only if unlock fails)
	 */
	public boolean unlock(String trxName) {
		
		return po.unlock(trxName); //	unlock
	}

	/**
	 * 	Set Trx
	 *	@param trxName transaction
	 */
	public void set_TrxName(String trxName) {
		
		po.set_TrxName(trxName); //	setTrx
	}

	/**
	 * 	Get Trx
	 *	@return transaction
	 */
	public String get_TrxName() {
		
		return po.get_TrxName(); //	getTrx
	}

	/**************************************************************************
	 * 	Get Attachments.
	 * 	An attachment may have multiple entries
	 *	@return Attachment or null
	 */
	public MAttachment getAttachment() {
		
		return po.getAttachment(); //	getAttachment
	}

	/**
	 * 	Get Attachments
	 * 	@param requery requery
	 *	@return Attachment or null
	 */
	public MAttachment getAttachment(boolean requery) {
		
		return po.getAttachment(requery); //	getAttachment
	}

	/**
	 * 	Create/return Attachment for PO.
	 * 	If not exist, create new
	 *	@return attachment
	 */
	public MAttachment createAttachment() {
		
		return po.createAttachment(); //	createAttachment
	}

	/**
	 * 	Do we have a Attachment of type
	 * 	@param extension extension e.g. .pdf
	 * 	@return true if there is a attachment of type
	 */
	public boolean isAttachment(String extension) {
		
		return po.isAttachment(extension); //	isAttachment
	}

	/**
	 * 	Get Attachment Data of type
	 * 	@param extension extension e.g. .pdf
	 *	@return data or null
	 */
	public byte[] getAttachmentData(String extension) {
		
		return po.getAttachmentData(extension); //	getAttachmentData
	}

	/**
	 * 	Do we have a PDF Attachment
	 * 	@return true if there is a PDF attachment
	 */
	public boolean isPdfAttachment() {
		
		return po.isPdfAttachment(); //	isPdfAttachment
	}

	/**
	 * 	Get PDF Attachment Data
	 *	@return data or null
	 */
	public byte[] getPdfAttachment() {
		
		return po.getPdfAttachment(); //	getPDFAttachment
	}

	/**************************************************************************
	 *  Dump Record
	 */
	public void dump() {
		
		po.dump(); //  dump
	}

	/**
	 *  Dump column
	 *  @param index index
	 */
	public void dump(int index) {
		
		po.dump(index); //  dump
	}

}
