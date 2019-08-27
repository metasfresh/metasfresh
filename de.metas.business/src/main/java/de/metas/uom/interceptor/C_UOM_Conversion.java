package de.metas.uom.interceptor;

import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Interceptor(I_C_UOM_Conversion.class)
@Component
public class C_UOM_Conversion
{
	private static final String SYSCONFIG_ProductUOMConversionUOMValidate = "ProductUOMConversionUOMValidate";
	private static final String SYSCONFIG_ProductUOMConversionRateValidate = "ProductUOMConversionRateValidate";
	private static final String MSG_ProductUOMConversionUOMError = "ProductUOMConversionUOMError";
	private static final String MSG_ProductUOMConversionRateError = "ProductUOMConversionRateError";

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_UOM_Conversion uomConversion, final ModelChangeType changeType)
	{
		if (uomConversion.getC_UOM_ID() == uomConversion.getC_UOM_To_ID())
		{
			throw new AdempiereException("@C_UOM_ID@ = @C_UOM_To_ID@");
		}

		//
		// Nothing to convert
		if (uomConversion.getMultiplyRate().signum() <= 0)
		{
			throw new AdempiereException("@MultiplyRate@ <= 0");
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(uomConversion.getM_Product_ID());

		//
		// Enforce Product UOM
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		if (sysConfigBL.getBooleanValue(SYSCONFIG_ProductUOMConversionUOMValidate, true))
		{
			if (productId != null
					&& (changeType.isNew() || InterfaceWrapperHelper.isValueChanged(uomConversion, I_C_UOM_Conversion.COLUMNNAME_M_Product_ID)))
			{
				final UomId fromUomId = UomId.ofRepoId(uomConversion.getC_UOM_ID());
				final UomId productStockingUomId = Services.get(IProductBL.class).getStockUOMId(productId);
				if (!productStockingUomId.equals(fromUomId))
				{
					final I_C_UOM uom = Services.get(IUOMDAO.class).getById(productStockingUomId);
					throw new AdempiereException("@" + MSG_ProductUOMConversionUOMError + "@ " + uom.getName());
				}
			}
		}

		//
		// The Product UoM needs to be the smallest UoM - Multiplier must be < 0; Divider must be > 0
		if (sysConfigBL.getBooleanValue(SYSCONFIG_ProductUOMConversionRateValidate, true))
		{
			if (productId != null && uomConversion.getDivideRate().signum() < 0)
			{
				throw new AdempiereException("@" + MSG_ProductUOMConversionRateError + "@");
			}
		}
	}
}
