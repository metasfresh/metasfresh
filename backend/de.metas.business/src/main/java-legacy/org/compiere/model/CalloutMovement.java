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
 * Contributor(s): Armen Rizal (armen@goodwill.co.id) Bug Fix 1564496 *
 *****************************************************************************/
package org.compiere.model;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.util.Env;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;

/**
 * Inventory Movement Callouts
 *
 * @author Jorg Janke
 * @version $Id: CalloutMovement.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>BF [ 1879568 ] CalloutMouvement QtyAvailable issues
 */
public class CalloutMovement extends CalloutEngine
{
	/**
	 * Product modified
	 * Set Attribute Set Instance
	 *
	 * @return Error message or ""
	 */
	public String product(final ICalloutField calloutField)
	{
		final I_M_MovementLine movementLineRecord = calloutField.getModel(I_M_MovementLine.class);
		final Properties ctx = calloutField.getCtx();
		final int WindowNo = calloutField.getWindowNo();
		final Integer M_Product_ID = movementLineRecord.getM_Product_ID();

		if (M_Product_ID == null || M_Product_ID.intValue() == 0)
		{
			return "";
		}
		// Set Attribute
		if (Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_Product_ID") == M_Product_ID.intValue()
				&& Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID") != 0)
		{
			movementLineRecord.setM_AttributeSetInstance_ID(Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID"));
		}
		else
		{
			movementLineRecord.setM_AttributeSetInstance_ID(0);
		}

		checkQtyAvailable(ctx, calloutField, WindowNo, M_Product_ID, null);
		return "";
	}   // product

	/**
	 * Movement Line - MovementQty modified
	 * called from MovementQty
	 *
	 * @return Error message or ""
	 */
	public String qty(final ICalloutField calloutField)
	{
		final I_M_MovementLine movementLineRecord = calloutField.getModel(I_M_MovementLine.class);
		final Properties ctx = calloutField.getCtx();
		final int WindowNo = calloutField.getWindowNo();

		if (isCalloutActive() || movementLineRecord.getMovementQty() == null)
		{
			return "";
		}

		final int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		checkQtyAvailable(ctx, calloutField, WindowNo, M_Product_ID, movementLineRecord.getMovementQty());
		//
		return "";
	} // qty

	/**
	 * Movement Line - Locator modified
	 *
	 * @return Error message or ""
	 */
	public String locator(final ICalloutField calloutField)
	{
		final I_M_MovementLine movementLineRecord = calloutField.getModel(I_M_MovementLine.class);
		final Properties ctx = calloutField.getCtx();
		final int WindowNo = calloutField.getWindowNo();

		if (movementLineRecord.getM_Locator_ID() <= 0)
		{
			return "";
		}
		final int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		checkQtyAvailable(ctx, calloutField, WindowNo, M_Product_ID, null);
		return "";
	}

	/**
	 * Check available qty
	 *
	 * @param ctx context
	 * @param calloutField Model Tab
	 * @param WindowNo current Window No
	 * @param M_Product_ID product ID
	 * @param MovementQty movement qty (if null will be get from context "MovementQty")
	 */
	private void checkQtyAvailable(final Properties ctx, final ICalloutField calloutField, final int WindowNo, final int M_Product_ID, BigDecimal MovementQty)
	{
		final I_M_MovementLine movementLineRecord = calloutField.getModel(I_M_MovementLine.class);

		if (M_Product_ID != 0
				&& Services.get(IProductBL.class).isStocked(ProductId.ofRepoIdOrNull(M_Product_ID)))
		{
			if (MovementQty == null)
			{
				MovementQty = movementLineRecord.getMovementQty();
			}
			final int M_Locator_ID = Env.getContextAsInt(ctx, WindowNo, "M_Locator_ID");
			// If no locator, don't check anything and assume is ok
			if (M_Locator_ID <= 0)
			{
				return;
			}
			final int M_AttributeSetInstance_ID = Env.getContextAsInt(ctx, WindowNo, "M_AttributeSetInstance_ID");
			BigDecimal available = MStorage.getQtyAvailable(0, M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID, null);
			if (available == null)
			{
				available = ZERO;
			}
			if (available.signum() == 0)
			{
				calloutField.fireDataStatusEEvent("NoQtyAvailable", "0", false);
			}
			else if (available.compareTo(MovementQty) < 0)
			{
				calloutField.fireDataStatusEEvent("InsufficientQtyAvailable", available.toString(), false);
			}
		}
	}
}	// CalloutMove
