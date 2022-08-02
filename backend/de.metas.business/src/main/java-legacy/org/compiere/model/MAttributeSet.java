/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.LegacyAdapters;
import org.compiere.util.DB;
import org.compiere.util.KeyNamePair;

import com.google.common.collect.ImmutableList;

import de.metas.cache.CCache;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Product Attribute Set
 *
 * @author Jorg Janke
 * @version $Id: MAttributeSet.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>FR [ 2214883 ] Remove SQL code and Replace for Query
 */
public class MAttributeSet extends X_M_AttributeSet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2703536167929259405L;

	/**
	 * Get MAttributeSet from Cache
	 * 
	 * @param ctx context
	 * @param M_AttributeSet_ID id
	 * @return MAttributeSet
	 */
	public static MAttributeSet get(@NonNull final AttributeSetId attributeSetId)
	{
		final I_M_AttributeSet attributeSet = s_cache.getOrLoad(attributeSetId, () -> loadOutOfTrx(attributeSetId, I_M_AttributeSet.class));
		return LegacyAdapters.convertToPO(attributeSet);
	}	// get

	/** Cache */
	private static CCache<AttributeSetId, I_M_AttributeSet> s_cache = CCache.newCache(Table_Name, 20, CCache.EXPIREMINUTES_Never);

	/**
	 * Standard constructor
	 * 
	 * @param ctx context
	 * @param M_AttributeSet_ID id
	 * @param trxName transaction
	 */
	public MAttributeSet(Properties ctx, int M_AttributeSet_ID, String trxName)
	{
		super(ctx, M_AttributeSet_ID, trxName);
		if (is_new())
		{
			// setName (null);
			setIsGuaranteeDate(false);
			setIsGuaranteeDateMandatory(false);
			setIsLot(false);
			setIsLotMandatory(false);
			setIsSerNo(false);
			setIsSerNoMandatory(false);
			setIsInstanceAttribute(false);
			setMandatoryType(MANDATORYTYPE_NotMandatary);
		}
	}	// MAttributeSet

	/**
	 * Load constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MAttributeSet(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MAttributeSet

	/** Entry Exclude */
	private X_M_AttributeSetExclude[] m_excludes = null;
	/** Lot create Exclude */
	private X_M_LotCtlExclude[] m_excludeLots = null;
	/** Serial No create Exclude */
	private X_M_SerNoCtlExclude[] m_excludeSerNos = null;

	/**
	 * @param instanceAttributes true if for instance
	 * @return instance or product(static) attribute
	 */
	public List<I_M_Attribute> getMAttributes(final boolean instanceAttributes)
	{
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);

		final AttributeSetId attributeSetId = AttributeSetId.ofRepoIdOrNone(getM_AttributeSet_ID());
		return attributesRepo.getAttributesByAttributeSetId(attributeSetId)
				.stream()
				.filter(attribute -> attribute.isInstanceAttribute() == instanceAttributes)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Something is Mandatory
	 * 
	 * @return true if something is mandatory
	 */
	public boolean isMandatory()
	{
		return !MANDATORYTYPE_NotMandatary.equals(getMandatoryType())
				|| isLotMandatory()
				|| isSerNoMandatory()
				|| isGuaranteeDateMandatory();
	}	// isMandatory

	/**
	 * Is always mandatory
	 * 
	 * @return mandatory
	 */
	public boolean isMandatoryAlways()
	{
		return MANDATORYTYPE_AlwaysMandatory.equals(getMandatoryType());
	}	// isMandatoryAlways

	/**
	 * Is Mandatory when Shipping
	 * 
	 * @return true if required for shipping
	 */
	public boolean isMandatoryShipping()
	{
		return MANDATORYTYPE_WhenShipping.equals(getMandatoryType());
	}	// isMandatoryShipping

	/**
	 * Exclude entry
	 * 
	 * @param AD_Column_ID column
	 * @param isSOTrx sales order
	 * @return true if excluded
	 */
	public boolean excludeEntry(int AD_Column_ID, boolean isSOTrx)
	{
		if (m_excludes == null)
		{
			final String whereClause = X_M_AttributeSetExclude.COLUMNNAME_M_AttributeSet_ID + "=?";
			List<X_M_AttributeSetExclude> list = new Query(getCtx(), X_M_AttributeSetExclude.Table_Name, whereClause, null)
					.setParameters(new Object[] { get_ID() })
					.setOnlyActiveRecords(true)
					.list(X_M_AttributeSetExclude.class);
			m_excludes = new X_M_AttributeSetExclude[list.size()];
			list.toArray(m_excludes);
		}
		// Find it
		if (m_excludes != null && m_excludes.length > 0)
		{
			MColumn column = MColumn.get(getCtx(), AD_Column_ID);
			for (int i = 0; i < m_excludes.length; i++)
			{
				if (m_excludes[i].getAD_Table_ID() == column.getAD_Table_ID()
						&& m_excludes[i].isSOTrx() == isSOTrx)
					return true;
			}
		}
		return false;
	}	// excludeEntry

	/**
	 * Exclude Lot creation
	 * 
	 * @param AD_Column_ID column
	 * @param isSOTrx SO
	 * @return true if excluded
	 */
	public boolean isExcludeLot(int AD_Column_ID, boolean isSOTrx)
	{
		if (getM_LotCtl_ID() == 0)
			return true;
		if (m_excludeLots == null)
		{
			String whereClause = X_M_LotCtlExclude.COLUMNNAME_M_LotCtl_ID + "=?";
			List<X_M_LotCtlExclude> list = new Query(getCtx(), X_M_LotCtlExclude.Table_Name, whereClause, null)
					.setParameters(new Object[] { getM_LotCtl_ID() })
					.setOnlyActiveRecords(true)
					.list(X_M_LotCtlExclude.class);
			m_excludeLots = new X_M_LotCtlExclude[list.size()];
			list.toArray(m_excludeLots);
		}
		// Find it
		if (m_excludeLots != null && m_excludeLots.length > 0)
		{
			MColumn column = MColumn.get(getCtx(), AD_Column_ID);
			for (int i = 0; i < m_excludeLots.length; i++)
			{
				if (m_excludeLots[i].getAD_Table_ID() == column.getAD_Table_ID()
						&& m_excludeLots[i].isSOTrx() == isSOTrx)
					return true;
			}
		}
		return false;
	}	// isExcludeLot

	/**
	 * Exclude SerNo creation
	 * 
	 * @param AD_Column_ID column
	 * @param isSOTrx SO
	 * @return true if excluded
	 */
	public boolean isExcludeSerNo(int AD_Column_ID, boolean isSOTrx)
	{
		if (getM_SerNoCtl_ID() == 0)
			return true;
		if (m_excludeSerNos == null)
		{
			String whereClause = X_M_SerNoCtlExclude.COLUMNNAME_M_SerNoCtl_ID + "=?";
			List<X_M_SerNoCtlExclude> list = new Query(getCtx(), X_M_SerNoCtlExclude.Table_Name, whereClause, null)
					.setParameters(new Object[] { getM_SerNoCtl_ID() })
					.setOnlyActiveRecords(true)
					.list(X_M_SerNoCtlExclude.class);
			m_excludeSerNos = new X_M_SerNoCtlExclude[list.size()];
			list.toArray(m_excludeSerNos);
		}
		// Find it
		if (m_excludeSerNos != null && m_excludeSerNos.length > 0)
		{
			MColumn column = MColumn.get(getCtx(), AD_Column_ID);
			for (int i = 0; i < m_excludeSerNos.length; i++)
			{
				if (m_excludeSerNos[i].getAD_Table_ID() == column.getAD_Table_ID()
						&& m_excludeSerNos[i].isSOTrx() == isSOTrx)
					return true;
			}
		}
		return false;
	}	// isExcludeSerNo

	/**
	 * Get Lot Char Start
	 * 
	 * @return defined or \u00ab
	 */
	public String getLotCharStart()
	{
		String s = super.getLotCharSOverwrite();
		if (s != null && s.length() == 1 && !s.equals(" "))
			return s;
		return "\u00ab";
	}	// getLotCharStart

	/**
	 * Get Lot Char End
	 * 
	 * @return defined or \u00bb
	 */
	public String getLotCharEnd()
	{
		String s = super.getLotCharEOverwrite();
		if (s != null && s.length() == 1 && !s.equals(" "))
			return s;
		return "\u00bb";
	}	// getLotCharEnd

	public KeyNamePair createLot(@NonNull final ProductId productId)
	{
		int M_LotCtl_ID = getM_LotCtl_ID();
		if (M_LotCtl_ID <= 0)
		{
			return null;
		}

		final MLotCtl ctl = new MLotCtl(getCtx(), M_LotCtl_ID, ITrx.TRXNAME_None);
		final MLot lot = ctl.createLot(productId.getRepoId());
		return new KeyNamePair(lot.getM_Lot_ID(), lot.getName());
	}	// createLot

	/**
	 * Get SerNo Char Start
	 * 
	 * @return defined or #
	 */
	public String getSerNoCharStart()
	{
		String s = super.getSerNoCharSOverwrite();
		if (s != null && s.length() == 1 && !s.equals(" "))
			return s;
		return "#";
	}	// getSerNoCharStart

	/**
	 * Get SerNo Char End
	 * 
	 * @return defined or none
	 */
	public String getSerNoCharEnd()
	{
		String s = super.getSerNoCharEOverwrite();
		if (s != null && s.length() == 1 && !s.equals(" "))
			return s;
		return "";
	}	// getSerNoCharEnd

	public String createSerNo()
	{
		final int M_SerNoCtl_ID = getM_SerNoCtl_ID();
		if (M_SerNoCtl_ID <= 0)
		{
			return null;
		}

		final MSerNoCtl ctl = new MSerNoCtl(getCtx(), M_SerNoCtl_ID, get_TrxName());
		return ctl.createSerNo();
	}

	/**
	 * Before Save.
	 * - set instance attribute flag
	 * 
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (!isInstanceAttribute()
				&& (isSerNo() || isLot() || isGuaranteeDate()))
			setIsInstanceAttribute(true);
		return true;
	}	// beforeSave

	/**
	 * After Save.
	 * - Verify Instance Attribute
	 * 
	 * @param newRecord new
	 * @param success success
	 * @return success
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		// Set Instance Attribute
		if (!isInstanceAttribute())
		{
			String sql = "UPDATE M_AttributeSet mas"
					+ " SET IsInstanceAttribute='Y' "
					+ "WHERE M_AttributeSet_ID=" + getM_AttributeSet_ID()
					+ " AND IsInstanceAttribute='N'"
					+ " AND (IsSerNo='Y' OR IsLot='Y' OR IsGuaranteeDate='Y'"
					+ " OR EXISTS (SELECT * FROM M_AttributeUse mau"
					+ " INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) "
					+ "WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID"
					+ " AND mau.IsActive='Y' AND ma.IsActive='Y'"
					+ " AND ma.IsInstanceAttribute='Y')"
					+ ")";
			int no = DB.executeUpdateAndSaveErrorOnFail(sql, get_TrxName());
			if (no != 0)
			{
				log.warn("Set Instance Attribute");
				setIsInstanceAttribute(true);
			}
		}
		// Reset Instance Attribute
		if (isInstanceAttribute() && !isSerNo() && !isLot() && !isGuaranteeDate())
		{
			String sql = "UPDATE M_AttributeSet mas"
					+ " SET IsInstanceAttribute='N' "
					+ "WHERE M_AttributeSet_ID=" + getM_AttributeSet_ID()
					+ " AND IsInstanceAttribute='Y'"
					+ "	AND IsSerNo='N' AND IsLot='N' AND IsGuaranteeDate='N'"
					+ " AND NOT EXISTS (SELECT * FROM M_AttributeUse mau"
					+ " INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) "
					+ "WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID"
					+ " AND mau.IsActive='Y' AND ma.IsActive='Y'"
					+ " AND ma.IsInstanceAttribute='Y')";
			int no = DB.executeUpdateAndSaveErrorOnFail(sql, get_TrxName());
			if (no != 0)
			{
				log.warn("Reset Instance Attribute");
				setIsInstanceAttribute(false);
			}
		}
		return success;
	}	// afterSave

}	// MAttributeSet
