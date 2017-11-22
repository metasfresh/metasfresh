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
package org.compiere.process;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.compiere.model.I_M_Product;
import org.compiere.model.MBOM;
import org.compiere.model.MBOMProduct;
import org.compiere.model.MProduct;
import org.compiere.model.MProductBOM;
import org.eevolution.model.I_PP_Product_BOM;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;

/**
 * Validate BOM
 *
 * @author Jorg Janke
 * @version $Id: BOMValidate.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 */
public class BOMValidate extends JavaProcess implements IProcessPrecondition
{

	@Param(parameterName = "p_M_Product_Category_ID", mandatory = false)
	private int p_M_Product_Category_ID;

	@Param(parameterName = "IsReValidate", mandatory = true)
	private boolean p_IsReValidate;

	private int p_M_Product_ID = 0;
	private I_M_Product m_product = null;
	private ArrayList<I_M_Product> m_products = null;

	@Override
	protected void prepare()
	{
		p_M_Product_ID = getM_Product_ID();
	}

	private int getM_Product_ID()
	{
		final String tableName = getTableName();
		if (I_M_Product.Table_Name.equals(tableName))
		{
			return getRecord_ID();
		}
		else if (I_PP_Product_BOM.Table_Name.equals(tableName))
		{
			final I_PP_Product_BOM bom = getRecord(I_PP_Product_BOM.class);
			return bom.getM_Product_ID();
		}
		else
		{
			throw new AdempiereException(StringUtils.formatMessage("Table {} has not yet been implemented to support BOM validation.", tableName));
		}

	}


	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		if (!(I_M_Product.Table_Name.equals(context.getTableName()) || I_PP_Product_BOM.Table_Name.equals(context.getTableName())))
		{
			return ProcessPreconditionsResolution.reject();
		}

		if (context.getSelectionSize()>1)
		{
			return ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

	/**
	 * Process
	 *
	 * @return Info
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		if (p_M_Product_ID != 0)
		{
			return validateProduct(new MProduct(getCtx(), p_M_Product_ID, get_TrxName()));
		}

		final IQueryBuilder<I_M_Product> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product.class, getCtx(), get_TrxName())
				.addEqualsFilter(I_M_Product.COLUMNNAME_IsBOM, true);

		if (p_M_Product_Category_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_Category_ID, p_M_Product_Category_ID);
		}
		else
		{
			queryBuilder.addOnlyContextClient();
		}

		if (!p_IsReValidate)
		{
			queryBuilder.addNotEqualsFilter(I_M_Product.COLUMNNAME_IsVerified, true);
		}

		queryBuilder.orderBy()
				.addColumn(I_M_Product.COLUMNNAME_Name)
				.endOrderBy();

		final AtomicInteger counter = new AtomicInteger(0);

		queryBuilder.create()
				.stream()
				.forEach(product -> {
					String info = validateProduct(product);
					addLog(0, null, null, info);
					counter.incrementAndGet();
				});


		return "#" + counter.get();
	}	// doIt


	/**
	 * Validate Product
	 *
	 * @param product product
	 * @return Info
	 */
	private String validateProduct(@NonNull final I_M_Product product)
	{
		if (!product.isBOM())
		{
			return product.getName() + " @NotValid@ @M_BOM_ID@";
		}
		m_product = product;

		// Check Old Product BOM Structure
		m_products = new ArrayList<>();
		if (!validateOldProduct(m_product))
		{
			saveVerifiedProduct(m_product, false);
			return m_product.getName() + " @NotValid@";
		}

		// New Structure
		MBOM[] boms = MBOM.getOfProduct(getCtx(), p_M_Product_ID, get_TrxName(), null);
		for (int i = 0; i < boms.length; i++)
		{
			m_products = new ArrayList<>();
			if (!validateBOM(boms[i]))
			{
				saveVerifiedProduct(m_product, false);
				return m_product.getName() + " " + boms[i].getName() + " @NotValid@";
			}
		}

		// OK
		saveVerifiedProduct(m_product, true);
		return m_product.getName() + " @IsValid@";
	}	// validateProduct

	/**
	 * Validate Old BOM Product structure
	 *
	 * @param product product
	 * @return true if valid
	 */
	private boolean validateOldProduct(@NonNull final I_M_Product product)
	{
		if (!product.isBOM() || m_products.contains(product))
		{
			return true;
		}

		m_products.add(product);

		final MProductBOM[] productsBOMs = MProductBOM.getBOMLines(InterfaceWrapperHelper.getPO(product));
		for (int i = 0; i < productsBOMs.length; i++)
		{
			MProductBOM productsBOM = productsBOMs[i];
			final I_M_Product pp = InterfaceWrapperHelper.create(getCtx(), productsBOM.getM_ProductBOM_ID(), I_M_Product.class, get_TrxName());
			if (pp.isBOM() && !validateOldProduct(pp))
			{
				return false;
			}
		}
		return true;
	}

	private void saveVerifiedProduct(@NonNull final I_M_Product product, final boolean isVerified)
	{
		product.setIsVerified(isVerified);
		save(product);
	}

	/**
	 * Validate BOM
	 *
	 * @param bom bom
	 * @return true if valid
	 */
	private boolean validateBOM(@NonNull final MBOM bom)
	{
		final MBOMProduct[] BOMproducts = MBOMProduct.getOfBOM(bom);
		for (int i = 0; i < BOMproducts.length; i++)
		{
			final MBOMProduct BOMproduct = BOMproducts[i];
			final I_M_Product pp = InterfaceWrapperHelper.create(getCtx(), BOMproduct.getM_BOMProduct_ID(), I_M_Product.class, get_TrxName());
			if (pp.isBOM())
			{
				return validateProduct(pp, bom.getBOMType(), bom.getBOMUse());
			}
		}
		return true;
	}

	/**
	 * Validate Product
	 *
	 * @param product product
	 * @param BOMType type
	 * @param BOMUse use
	 * @return true if valid
	 */
	private boolean validateProduct(@NonNull final I_M_Product product, String BOMType, String BOMUse)
	{
		if (!product.isBOM())
		{
			return true;
		}

		final String restriction = "BOMType='" + BOMType + "' AND BOMUse='" + BOMUse + "'";
		final MBOM[] boms = MBOM.getOfProduct(getCtx(), p_M_Product_ID, get_TrxName(), restriction);
		if (boms.length != 1 || m_products.contains(product))
		{
			return false;
		}

		m_products.add(product);
		final MBOM bom = boms[0];
		return validateBOM(bom);
	}
}
