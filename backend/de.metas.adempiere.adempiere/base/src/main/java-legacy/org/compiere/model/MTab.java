package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;

import de.metas.util.Check;

/**
 *	Tab Model
 *	
 *  @author Jorg Janke
 *  @author victor.perez@e-evolution.com, e-Evolution
 * <li>RF [2826384] The Order and Included Columns should be to fill mandatory
 * <li>http://sourceforge.net/tracker/?func=detail&atid=879335&aid=2826384&group_id=176962
 */
public class MTab extends X_AD_Tab
{
	public MTab (final Properties ctx, final int AD_Tab_ID, final String trxName)
	{
		super (ctx, AD_Tab_ID, trxName);
		if (is_new())
		{
			setEntityType (ENTITYTYPE_UserMaintained);	// U
			setHasTree (false);
			setIsReadOnly (false);
			setIsSingleRow (false);
			setIsSortTab (false);	// N
			setIsTranslationTab (false);
			setSeqNo (0);
			setTabLevel (0);
			setIsInsertRecord(true);
			setIsAdvancedTab(false);
		}
	}

	public MTab (final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave (final boolean newRecord)
	{
	//	UPDATE AD_Tab SET IsInsertRecord='N' WHERE IsInsertRecord='Y' AND IsReadOnly='Y'
		if (isReadOnly() && isInsertRecord())
			setIsInsertRecord(false);
		//RF[2826384]
		if(isSortTab())
		{
			if(getAD_ColumnSortOrder_ID() == 0)
			{
				throw new FillMandatoryException("AD_ColumnSortOrder_ID");	
			}			
		}

		// Prevent adding more wrong cases.
		if(is_ValueChanged(COLUMNNAME_OrderByClause) && !Check.isEmpty(getOrderByClause(), true))
		{
			throw new AdempiereException("OrderByClause shall be empty. See https://github.com/metasfresh/metasfresh/issues/412");
		}
		
		return true;
	}
}
