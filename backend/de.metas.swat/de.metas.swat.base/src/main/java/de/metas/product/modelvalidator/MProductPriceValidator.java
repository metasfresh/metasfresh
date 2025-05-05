package de.metas.product.modelvalidator;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.pricing.ProductPriceId;
import de.metas.pricing.service.ProductScalePriceService;
import de.metas.pricing.service.ScalePriceUsage;
import de.metas.pricing.service.ScaleProductPrice;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.create;

public class MProductPriceValidator implements ModelValidator
{
	private int ad_Client_ID = -1;

	private final ProductScalePriceService productScalePriceService = SpringContextHolder.instance.getBean(ProductScalePriceService.class);

	@Override
	public String docValidate(PO po, int timing)
	{
		return null;
	}

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}
		engine.addModelChange(I_M_ProductPrice.Table_Name, this);
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (type != ModelValidator.TYPE_AFTER_CHANGE
				&& type != ModelValidator.TYPE_AFTER_NEW
				&& type != ModelValidator.TYPE_BEFORE_DELETE)
		{

			return null;
		}

		if (!po.is_ManualUserAction())
		{
			return null;
		}

		final I_M_ProductPrice productPrice = create(po, I_M_ProductPrice.class);
		final ScalePriceUsage useScalePrice = ScalePriceUsage.ofCode(productPrice.getUseScalePrice());
		if (useScalePrice.isDoNotUseScalePrice())
		{
			return null;
		}

		if (type == ModelValidator.TYPE_BEFORE_DELETE)
		{
			onBeforeDeleteProductPrice(productPrice);
		}
		else
		{
			final ProductPriceId productPriceId = ProductPriceId.ofRepoId(productPrice.getM_ProductPrice_ID());
			productScalePriceService.createScalePriceIfMissing(
					productPriceId,
					ScaleProductPrice.builder()
							.quantityMin(BigDecimal.ZERO)
							.priceStd(productPrice.getPriceStd())
							.priceList(productPrice.getPriceList())
							.priceLimit(productPrice.getPriceLimit())
							.build()
			);
		}

		return null;
	}

	private void onBeforeDeleteProductPrice(final I_M_ProductPrice productPrice)
	{
		final ProductPriceId productPriceId = ProductPriceId.ofRepoIdOrNull(productPrice.getM_ProductPrice_ID());
		if (productPriceId == null)
		{
			return;
		}

		productScalePriceService.deleteByProductPriceId(productPriceId);
	}
}