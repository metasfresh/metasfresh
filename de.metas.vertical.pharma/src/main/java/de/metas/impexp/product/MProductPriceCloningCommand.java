package de.metas.impexp.product;

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
class MProductPriceCloningCommand
{
	private final int source_PriceList_Version_ID;
	private final int target_PriceList_Version_ID;

	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	public void cloneProductPrice()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final String trxName = ITrx.TRXNAME_None;

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
		final I_M_ProductPrice pp = InterfaceWrapperHelper.copy()
				.setFrom(productPrice)
				.copyToNew(I_M_ProductPrice.class);
		pp.setM_PriceList_Version_ID(target_PriceList_Version_ID);
		cloneASI(productPrice);
		InterfaceWrapperHelper.save(pp);
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
	}
}
