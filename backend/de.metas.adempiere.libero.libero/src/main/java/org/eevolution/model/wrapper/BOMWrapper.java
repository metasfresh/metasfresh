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

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.reasoner.StorageReasoner;

/**
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 */
public class BOMWrapper extends AbstractPOWrapper
{

	final public static String BOM_TYPE_PRODUCT = "productBOM";
	final public static String BOM_TYPE_ORDER = "orderBOM";

	public static String tableName(String type)
	{

		if (BOM_TYPE_PRODUCT.equals(type))
		{
			return I_PP_Product_BOM.Table_Name;
		}
		else if (BOM_TYPE_ORDER.equals(type))
		{
			return I_PP_Order_BOM.Table_Name;
		}

		return "";
	}

	public static String idColumn(String type)
	{

		String value = null;
		if (BOM_TYPE_PRODUCT.equals(type))
		{
			return I_PP_Product_BOM.Table_Name;
		}
		else if (BOM_TYPE_ORDER.equals(type))
		{
			return I_PP_Order_BOM.Table_Name;
		}

		return value + "_ID";
	}

	public BOMWrapper(Properties ctx, int id, String trxName, String type)
	{

		super(ctx, id, trxName, type);
	}

	@Override
	protected PO receivePO(Properties ctx, int id, String trxName, String type)
	{
		Object model = null;
		if (BOM_TYPE_PRODUCT.equals(type))
		{

			model = InterfaceWrapperHelper.create(ctx, id, I_PP_Product_BOM.class, trxName);
		}
		else if (BOM_TYPE_ORDER.equals(type))
		{

			model = InterfaceWrapperHelper.create(ctx, id, I_PP_Order_BOM.class, trxName);
		}

		final PO po = InterfaceWrapperHelper.getPO(model);
		return po;
	}

	public String getName()
	{

		String name = null;
		if (get() instanceof I_PP_Product_BOM)
		{

			name = ((I_PP_Product_BOM)get()).getName();
		}
		else if (get() instanceof I_PP_Order_BOM)
		{

			name = ((I_PP_Order_BOM)get()).getName();
		}

		return name;
	}

	public String getDescription()
	{

		String name = null;
		if (get() instanceof I_PP_Product_BOM)
		{

			name = ((I_PP_Product_BOM)get()).getDescription();
		}
		else if (get() instanceof I_PP_Order_BOM)
		{

			name = ((I_PP_Order_BOM)get()).getDescription();
		}

		return name;
	}

	public String getRevision()
	{

		String name = null;
		if (get() instanceof I_PP_Product_BOM)
		{

			name = ((I_PP_Product_BOM)get()).getRevision();
		}
		else if (get() instanceof I_PP_Order_BOM)
		{

			name = ((I_PP_Order_BOM)get()).getRevision();
		}

		return name;
	}

	public String getDocumentNo()
	{

		String value = null;
		if (get() instanceof I_PP_Product_BOM)
		{

			value = ((I_PP_Product_BOM)get()).getDocumentNo();
		}
		else if (get() instanceof I_PP_Order_BOM)
		{

			value = ((I_PP_Order_BOM)get()).getDocumentNo();
		}

		return value;
	}

	public int getM_Product_ID()
	{

		int id = 0;
		if (get() instanceof I_PP_Product_BOM)
		{

			id = ((I_PP_Product_BOM)get()).getM_Product_ID();
		}
		else if (get() instanceof I_PP_Order_BOM)
		{

			id = ((I_PP_Order_BOM)get()).getM_Product_ID();
		}

		return id;
	}

	public int getM_AttributeSetInstance_ID()
	{

		int id = 0;
		if (get() instanceof I_PP_Product_BOM)
		{

			id = ((I_PP_Product_BOM)get()).getM_AttributeSetInstance_ID();
		}
		else if (get() instanceof I_PP_Order_BOM)
		{

			id = ((I_PP_Order_BOM)get()).getM_AttributeSetInstance_ID();
		}

		return id;
	}

	public int getC_UOM_ID()
	{

		int id = 0;
		if (get() instanceof I_PP_Product_BOM)
		{

			id = ((I_PP_Product_BOM)get()).getC_UOM_ID();
		}
		else if (get() instanceof I_PP_Order_BOM)
		{

			id = ((I_PP_Order_BOM)get()).getC_UOM_ID();
		}

		return id;
	}

	public Timestamp getValidFrom()
	{

		Timestamp value = null;
		if (get() instanceof I_PP_Product_BOM)
		{

			value = ((I_PP_Product_BOM)get()).getValidFrom();
		}
		else if (get() instanceof I_PP_Order_BOM)
		{

			value = ((I_PP_Order_BOM)get()).getValidFrom();
		}

		return value;
	}

	public Timestamp getValidTo()
	{

		Timestamp value = null;
		if (get() instanceof I_PP_Product_BOM)
		{

			value = ((I_PP_Product_BOM)get()).getValidTo();
		}
		else if (get() instanceof I_PP_Order_BOM)
		{

			value = ((I_PP_Order_BOM)get()).getValidTo();
		}

		return value;
	}

	public String getValue()
	{

		String value = null;
		if (get() instanceof I_PP_Product_BOM)
		{

			value = ((I_PP_Product_BOM)get()).getValue();
		}
		else if (get() instanceof I_PP_Order_BOM)
		{

			value = ((I_PP_Order_BOM)get()).getValue();
		}

		return value;
	}

	public String getBOMType()
	{

		String value = null;
		if (get() instanceof I_PP_Product_BOM)
		{

			value = ((I_PP_Product_BOM)get()).getBOMType();
		}
		else if (get() instanceof I_PP_Order_BOM)
		{

			value = ((I_PP_Order_BOM)get()).getBOMType();
		}

		return value;
	}

	public int getPP_Order_ID()
	{

		int id = 0;

		if (get() instanceof I_PP_Order_BOM)
		{

			I_PP_Order_BOM bom = (I_PP_Order_BOM)get();
			id = bom.getPP_Order_ID();
		}

		return id;
	}

	public BOMLineWrapper[] getLines()
	{

		int[] ids = null;

		String type = null;
		if (get() instanceof I_PP_Product_BOM)
		{

			type = BOM_TYPE_PRODUCT;
		}
		else if (get() instanceof I_PP_Order_BOM)
		{

			type = BOM_TYPE_ORDER;
		}

		StorageReasoner mr = new StorageReasoner();
		ids = mr.getPOIDs(BOMLineWrapper.tableName(type), idColumn(type) + " = " + getID(), null);

		BOMLineWrapper[] lines = new BOMLineWrapper[ids.length];

		for (int i = 0; i < ids.length; i++)
		{

			lines[i] = new BOMLineWrapper(getCtx(), ids[i], null, type);
		}
		return lines;
	}
}
