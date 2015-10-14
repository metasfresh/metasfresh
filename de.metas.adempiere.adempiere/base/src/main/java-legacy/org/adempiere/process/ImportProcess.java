/**
 * 
 */
package org.adempiere.process;

import java.util.Properties;

import org.compiere.process.ProcessInfo;

/**
 * All processes that are importing data should implement this interface.
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 2788276 ] Data Import Validator
 * 				https://sourceforge.net/tracker/?func=detail&aid=2788276&group_id=176962&atid=879335
 */
public interface ImportProcess
{
	/**
	 * 
	 * @return The Name of Import Table (e.g. I_BPartner)
	 */
	public String getImportTableName();
	
	/**
	 * 
	 * @return SQL WHERE clause to filter records that are candidates for import
	 */
	public String getWhereClause();
	
	/**
	 * Get Process Context
	 * @return context
	 */
	public Properties getCtx();
	
	/**
	 * Get Process Transaction Name 
	 * @return transaction name
	 */
	public String get_TrxName();

	/**
	 * Get Process Info
	 * @return Process Info
	 * @see org.compiere.process.SvrProcess#getProcessInfo()
	 */
	public ProcessInfo getProcessInfo();
}
