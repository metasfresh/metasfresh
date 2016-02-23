/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IMDiscountSchemaBL;
import org.adempiere.pricing.api.IMDiscountSchemaDAO;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * Discount Schema Model
 *
 * @author Jorg Janke
 * @version $Id: MDiscountSchema.java,v 1.3 2006/07/30 00:51:04 jjanke Exp $
 */
public class MDiscountSchema extends X_M_DiscountSchema
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3314884382853756019L;

	/**
	 * Get Discount Schema from Cache
	 *
	 * @param ctx context
	 * @param M_DiscountSchema_ID id
	 * @return MDiscountSchema
	 */
	public static MDiscountSchema get(Properties ctx, int M_DiscountSchema_ID)
	{
		Integer key = new Integer(M_DiscountSchema_ID);
		MDiscountSchema retValue = (MDiscountSchema)s_cache.get(key);
		if (retValue != null)
			return retValue;
		retValue = new MDiscountSchema(ctx, M_DiscountSchema_ID, null);
		if (retValue.get_ID() != 0)
			s_cache.put(key, retValue);
		return retValue;
	}	// get

	/** Cache */
	private static CCache<Integer, MDiscountSchema> s_cache = new CCache<Integer, MDiscountSchema>("M_DiscountSchema", 20);

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param M_DiscountSchema_ID id
	 * @param trxName transaction
	 */
	public MDiscountSchema(Properties ctx, int M_DiscountSchema_ID, String trxName)
	{
		super(ctx, M_DiscountSchema_ID, trxName);
		if (M_DiscountSchema_ID == 0)
		{
			// setName();
			setDiscountType(DISCOUNTTYPE_FlatPercent);
			setFlatDiscount(Env.ZERO);
			setIsBPartnerFlatDiscount(false);
			setIsQuantityBased(true);	// Y
			setCumulativeLevel(CUMULATIVELEVEL_Line);
			// setValidFrom (new Timestamp(System.currentTimeMillis()));
		}
	}	// MDiscountSchema

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MDiscountSchema(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MDiscountSchema

	/**
	 * Get Breaks
	 *
	 * @param reload reload
	 * @return breaks
	 */
	 @Deprecated
	 public MDiscountSchemaBreak[] getBreaks(boolean reload)
	 {
	 final I_M_DiscountSchema schemaInstance = InterfaceWrapperHelper.create(this, I_M_DiscountSchema.class);
	
	 final List<I_M_DiscountSchemaBreak> breaks = Services.get(IMDiscountSchemaDAO.class).retrieveBreaks(schemaInstance);
	
	 return breaks.toArray(new MDiscountSchemaBreak[breaks.size()]);
	
	 } // getBreaks

	/**
	 * Get Lines
	 *
	 * @param reload reload
	 * @return lines
	 */
	 @Deprecated
	 public MDiscountSchemaLine[] getLines(boolean reload)
	 {
	 final I_M_DiscountSchema schemaInstance = InterfaceWrapperHelper.create(this, I_M_DiscountSchema.class);
	
	 final List<I_M_DiscountSchemaLine> lines = Services.get(IMDiscountSchemaDAO.class).retrieveLines(schemaInstance);
	
	 return lines.toArray(new MDiscountSchemaLine[lines.size()]);
	 } // getBreaks

	/**
	 * Calculate Discounted Price
	 *
	 * @param Qty quantity
	 * @param Price price
	 * @param M_Product_ID product
	 * @param M_Product_Category_ID category
	 * @param BPartnerFlatDiscount flat discount
	 * @return discount or zero
//	 */
//	@Deprecated
//	public BigDecimal calculatePrice(BigDecimal Qty, BigDecimal Price,
//			int M_Product_ID, int M_Product_Category_ID,
//			BigDecimal BPartnerFlatDiscount)
//	{
//		final I_M_DiscountSchema schemaInstance = InterfaceWrapperHelper.create(this, I_M_DiscountSchema.class);
//		return Services.get(IMDiscountSchemaBL.class).calculatePrice(
//				schemaInstance,
//				Qty,
//				Price,
//				M_Product_ID,
//				M_Product_Category_ID,
//				BPartnerFlatDiscount);
//
//	}	// calculatePrice

	/**
	 * Calculate Discount Percentage
	 *
	 * @param Qty quantity
	 * @param Price price
	 * @param M_Product_ID product
	 * @param M_Product_Category_ID category
	 * @param BPartnerFlatDiscount flat discount
	 * @return discount or zero
	 */
//	@Deprecated
//	public BigDecimal calculateDiscount(BigDecimal Qty, BigDecimal Price,
//			int M_Product_ID, int M_Product_Category_ID,
//			BigDecimal BPartnerFlatDiscount)
//	{
//		final I_M_DiscountSchema schemaInstance = InterfaceWrapperHelper.create(this, I_M_DiscountSchema.class);
//		
//		return Services.get(IMDiscountSchemaBL.class).calculateDiscount(
//				schemaInstance,
//				Qty,
//				Price,
//				M_Product_ID,
//				M_Product_Category_ID,
//				BPartnerFlatDiscount);
//	}	// calculateDiscount

	/**
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true
	 */
	protected boolean beforeSave(boolean newRecord)
	{
		if (getValidFrom() == null)
			setValidFrom(TimeUtil.getDay(null));

		return true;
	}	// beforeSave

	/**
	 * Renumber
	 *
	 * @return lines updated
	 */
	@Deprecated
	public int reSeq()
	{
		final I_M_DiscountSchema schemaInstance = InterfaceWrapperHelper.create(this, I_M_DiscountSchema.class);

		return Services.get(IMDiscountSchemaBL.class).reSeq(schemaInstance);
	}	// reSeq

}	// MDiscountSchema
