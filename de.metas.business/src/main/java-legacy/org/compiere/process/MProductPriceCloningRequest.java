package org.compiere.process;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_ProductPrice;

import lombok.Builder;

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Builder
public class MProductPriceCloningRequest
{
	private final int source_PriceList_Version_ID;
	private final int target_PriceList_Version_ID;

	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	public void cloneProductPrice()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final IQuery<I_M_ProductPrice> existentProductPrices = queryBL.createQueryBuilder(I_M_ProductPrice.class, trxName)
				.addEqualsFilter(I_M_ProductPrice.COLUMN_M_PriceList_Version_ID, target_PriceList_Version_ID)
				.create();

		queryBL.createQueryBuilder(I_M_ProductPrice.class, trxName)
				.addEqualsFilter(I_M_ProductPrice.COLUMN_M_PriceList_Version_ID, source_PriceList_Version_ID)
				.addNotInSubQueryFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, I_M_ProductPrice.COLUMNNAME_M_Product_ID, existentProductPrices)
				.create()
				.iterateAndStream()
				.forEach(this::createProductPrice);
	}

	private void createProductPrice(final I_M_ProductPrice productPrice)
	{
		final I_M_ProductPrice pp = newInstance(I_M_ProductPrice.class, productPrice);
		pp.setM_PriceList_Version_ID(target_PriceList_Version_ID);
		pp.setM_Product_ID(productPrice.getM_Product_ID());
		pp.setPriceLimit(productPrice.getPriceLimit());
		pp.setPriceList(productPrice.getPriceList());
		pp.setPriceStd(productPrice.getPriceStd());
		pp.setC_TaxCategory_ID(productPrice.getC_TaxCategory_ID());
		pp.setC_UOM_ID(productPrice.getC_UOM_ID());
		pp.setIsSeasonFixedPrice(productPrice.isSeasonFixedPrice());
		pp.setM_DiscountSchemaLine_ID(productPrice.getM_DiscountSchemaLine_ID());
		pp.setIsAttributeDependant(productPrice.isAttributeDependant());
		pp.setM_AttributeSetInstance_ID(productPrice.getM_AttributeSetInstance_ID());
		InterfaceWrapperHelper.save(pp);

		cloneASI(productPrice);
	}

	private void cloneASI(final I_M_ProductPrice productPrice)
	{
		if (!productPrice.isAttributeDependant())
		{
			return;
		}

		final I_M_AttributeSetInstance sourceASI = productPrice.getM_AttributeSetInstance();
		final I_M_AttributeSetInstance targetASI = sourceASI == null ? null : attributeDAO.copy(sourceASI);

		productPrice.setM_AttributeSetInstance(targetASI);
		InterfaceWrapperHelper.save(productPrice);
	}
}
