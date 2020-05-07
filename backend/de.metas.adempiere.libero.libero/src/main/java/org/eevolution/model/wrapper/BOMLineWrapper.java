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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOMLine;

/**
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 */
public class BOMLineWrapper extends AbstractPOWrapper
{

	public static String tableName(String type)
	{

		if (BOMWrapper.BOM_TYPE_PRODUCT.equals(type))
		{

			return I_PP_Product_BOMLine.Table_Name;
		}
		else if (BOMWrapper.BOM_TYPE_ORDER.equals(type))
		{

			return I_PP_Order_BOMLine.Table_Name;
		}

		return "";
	}

	public static String idColumn(String type)
	{

		return tableName(type) + "_ID";
	}

	public BOMLineWrapper(Properties ctx, int id, String trxName, String type)
	{

		super(ctx, id, trxName, type);
	}

	@Override
	protected PO receivePO(Properties ctx, int id, String trxName, String type)
	{

		Object model = null;
		if (BOMWrapper.BOM_TYPE_PRODUCT.equals(type))
		{

			model = InterfaceWrapperHelper.create(ctx, id, I_PP_Product_BOMLine.class, trxName);
		}
		else if (BOMWrapper.BOM_TYPE_ORDER.equals(type))
		{

			model = InterfaceWrapperHelper.create(ctx, id, I_PP_Order_BOMLine.class, trxName);
		}

		final PO po = InterfaceWrapperHelper.getPO(model);
		return po;
	}

	public String getComponentType()
	{

		String type = null;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			type = ((I_PP_Product_BOMLine)get()).getComponentType();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			type = ((I_PP_Order_BOMLine)get()).getComponentType();
		}

		return type;
	}

	public BigDecimal getAssay()
	{

		BigDecimal assay = null;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			assay = ((I_PP_Product_BOMLine)get()).getAssay();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			assay = ((I_PP_Order_BOMLine)get()).getAssay();
		}

		return assay;
	}

	public int getM_ChangeNotice_ID()
	{

		int M_ChangeNotice_ID = 0;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			M_ChangeNotice_ID = ((I_PP_Product_BOMLine)get()).getM_ChangeNotice_ID();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			M_ChangeNotice_ID = ((I_PP_Order_BOMLine)get()).getM_ChangeNotice_ID();
		}

		return M_ChangeNotice_ID;
	}

	public String getHelp()
	{

		String Help = null;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			Help = ((I_PP_Product_BOMLine)get()).getHelp();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			Help = ((I_PP_Order_BOMLine)get()).getHelp();
		}

		return Help;
	}

	public BigDecimal getQtyBatch()
	{

		BigDecimal qty = null;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			qty = ((I_PP_Product_BOMLine)get()).getQtyBatch();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			qty = ((I_PP_Order_BOMLine)get()).getQtyBatch();
		}

		return qty;
	}

	public BigDecimal getForecast()
	{

		BigDecimal fc = null;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			fc = ((I_PP_Product_BOMLine)get()).getForecast();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			fc = ((I_PP_Order_BOMLine)get()).getForecast();
		}

		return fc;
	}

	public Integer getLeadTimeOffset()
	{

		Integer offset = null;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			offset = ((I_PP_Product_BOMLine)get()).getLeadTimeOffset();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			offset = ((I_PP_Order_BOMLine)get()).getLeadTimeOffset();
		}

		return offset;
	}

	public boolean isQtyPercentage()
	{

		boolean percentage = false;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			percentage = ((I_PP_Product_BOMLine)get()).isQtyPercentage();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			percentage = ((I_PP_Order_BOMLine)get()).isQtyPercentage();
		}

		return percentage;
	}

	public boolean isCritical()
	{

		boolean critical = false;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			critical = ((I_PP_Product_BOMLine)get()).isCritical();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			critical = ((I_PP_Order_BOMLine)get()).isCritical();
		}

		return critical;
	}

	public String getIssueMethod()
	{

		String issue = null;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			issue = ((I_PP_Product_BOMLine)get()).getIssueMethod();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			issue = ((I_PP_Order_BOMLine)get()).getIssueMethod();
		}

		return issue;
	}

	public int getLine()
	{

		int line = 0;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			line = ((I_PP_Product_BOMLine)get()).getLine();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			line = ((I_PP_Order_BOMLine)get()).getLine();
		}

		return line;
	}

	public String getDescription()
	{

		String type = null;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			type = ((I_PP_Product_BOMLine)get()).getDescription();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			type = ((I_PP_Order_BOMLine)get()).getDescription();
		}

		return type;
	}

	public int getM_Product_ID()
	{

		int id = 0;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			id = ((I_PP_Product_BOMLine)get()).getM_Product_ID();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			id = ((I_PP_Order_BOMLine)get()).getM_Product_ID();
		}

		return id;
	}

	public int getPP_Order_ID()
	{

		int id = 0;

		if (get() instanceof I_PP_Order_BOMLine)
		{

			I_PP_Order_BOMLine line = (I_PP_Order_BOMLine)get();
			id = line.getPP_Order_ID();
		}

		return id;
	}

	public int getPP_BOM_ID()
	{

		int id = 0;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			id = ((I_PP_Product_BOMLine)get()).getPP_Product_BOM_ID();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			id = ((I_PP_Order_BOMLine)get()).getPP_Order_BOM_ID();
		}

		return id;
	}

	public int getM_AttributeSetInstance_ID()
	{

		int id = 0;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			id = ((I_PP_Product_BOMLine)get()).getM_AttributeSetInstance_ID();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			id = ((I_PP_Order_BOMLine)get()).getM_AttributeSetInstance_ID();
		}

		return id;
	}

	public void setM_AttributeSetInstance_ID(int id)
	{

		if (get() instanceof I_PP_Product_BOMLine)
		{

			((I_PP_Product_BOMLine)get()).setM_AttributeSetInstance_ID(id);
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			((I_PP_Order_BOMLine)get()).setM_AttributeSetInstance_ID(id);
		}
	}

	public void setQtyBOM(BigDecimal qty)
	{

		if (get() instanceof I_PP_Product_BOMLine)
		{

			((I_PP_Product_BOMLine)get()).setQtyBOM(qty);
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			((I_PP_Order_BOMLine)get()).setQtyBOM(qty);
		}
	}

	public BigDecimal getQtyBOM()
	{

		BigDecimal value = null;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			value = ((I_PP_Product_BOMLine)get()).getQtyBOM();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			value = ((I_PP_Order_BOMLine)get()).getQtyBOM();
		}

		return value;
	}

	public int getC_UOM_ID()
	{

		int value = 0;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			value = ((I_PP_Product_BOMLine)get()).getC_UOM_ID();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			value = ((I_PP_Order_BOMLine)get()).getC_UOM_ID();
		}

		return value;
	}

	public int getPo()
	{

		int value = 0;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			value = ((I_PP_Product_BOMLine)get()).getLine();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			value = ((I_PP_Order_BOMLine)get()).getLine();
		}

		return value;
	}

	public BigDecimal getScrap()
	{

		BigDecimal value = new BigDecimal(0);
		if (get() instanceof I_PP_Product_BOMLine)
		{

			value = ((I_PP_Product_BOMLine)get()).getScrap();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			value = ((I_PP_Order_BOMLine)get()).getScrap();
		}

		return value;
	}

	public Timestamp getValidFrom()
	{

		Timestamp value = null;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			value = ((I_PP_Product_BOMLine)get()).getValidFrom();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			value = ((I_PP_Order_BOMLine)get()).getValidFrom();
		}

		return value;
	}

	public Timestamp getValidTo()
	{

		Timestamp value = null;
		if (get() instanceof I_PP_Product_BOMLine)
		{

			value = ((I_PP_Product_BOMLine)get()).getValidTo();
		}
		else if (get() instanceof I_PP_Order_BOMLine)
		{

			value = ((I_PP_Order_BOMLine)get()).getValidTo();
		}

		return value;
	}
}
