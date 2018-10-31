package org.adempiere.acct.api;

import org.compiere.model.I_C_AcctSchema_Element;

import de.metas.util.ISingletonService;

public interface IAcctSchemaBL extends ISingletonService
{
	/**
	 * Get Default element value. The implementation is copied from <code>org.compiere.model.MAcctSchemaElement.getDefaultValue()</code>.
	 * 
	 * @return default
	 */
	public int getDefaultValue(I_C_AcctSchema_Element ase);

	/**
	 * Get Acct Fact ColumnName. Convenience method which calls {@link #getColumnName(String)} for the given <code>ase</code>.The implementation is copied from
	 * <code>org.compiere.model.MAcctSchemaElement.getColumnName()</code> .
	 * 
	 * @param ase
	 * @return
	 */
	String getColumnName(I_C_AcctSchema_Element ase);

	/**
	 * Get Column Name of ELEMENTTYPE.The implementation is copied from <code>org.compiere.model.MAcctSchemaElement.getColumnName()</code>.
	 * 
	 * @return column name or "" if not found
	 * @return
	 */
	String getColumnName(String elementType);

	/**
	 * Get Display ColumnName. The implementation is copied from <code>org.compiere.model.MAcctSchemaElement.getDisplayColumnName()</code>.
	 * 
	 * @param ase
	 * @return
	 */
	String getDisplayColumnName(I_C_AcctSchema_Element ase);
}
