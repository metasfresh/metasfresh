package de.metas.pricing.modelvalidator;

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


import java.util.Properties;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.ModelValidator;

@Validator(I_M_ProductPrice.class)
public class M_ProductPrice
{
	@Init
	public void setupCallouts()
	{
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.pricing.callout.M_ProductPrice());
	}

	@ModelChange(timings = {ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE})
	public void setSeqNo(final I_M_ProductPrice productPrice)
	{
		if(productPrice.getSeqNo() <= 0)
		{
			final int lastSeqNo = Services.get(IQueryBL.class)
					.createQueryBuilder(I_M_ProductPrice.class, productPrice)
					.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, productPrice.getM_PriceList_Version_ID())
					.addNotEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_ProductPrice_ID, productPrice.getM_ProductPrice_ID())
					.create()
					.aggregate(I_M_ProductPrice.COLUMNNAME_SeqNo, IQuery.AGGREGATE_MAX, int.class);
			
			final int nextSeqNo = (lastSeqNo <= 0 ? 0 : lastSeqNo) / 10 * 10 + 10;
			productPrice.setSeqNo(nextSeqNo);
		}
	}

	/**
	 * Make sure Scale price and Attribute price are never both used.
	 * 
	 * @param productPrice
	 */
	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW
			, ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_M_ProductPrice.COLUMNNAME_UseScalePrice,
			I_M_ProductPrice.COLUMNNAME_IsAttributeDependant
	})
	public void checkFlags(I_M_ProductPrice productPrice)
	{
		// Should never happen.
		Check.assumeNotNull(productPrice, "Product price not null");

		if (productPrice.isAttributeDependant() && productPrice.isUseScalePrice())
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(productPrice);

			throw new AdempiereException(Services.get(IMsgBL.class).getMsg(ctx, IPricingBL.PRODUCTPRICE_FLAG_ERROR));
		}
	}
}
