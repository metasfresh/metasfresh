package org.eevolution.mrp.api.impl;

import de.metas.util.Check;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPQueryBuilder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Properties;

public class MRPDAO implements IMRPDAO
{
	@Override
	public IMRPQueryBuilder createMRPQueryBuilder()
	{
		return new MRPQueryBuilder();
	}

	@Override
	public IMRPQueryBuilder retrieveQueryBuilder(final Object model, final String typeMRP, final String orderType)
	{
		final Optional<Integer> adClientId = InterfaceWrapperHelper.getValue(model, "AD_Client_ID");
		Check.assume(adClientId.isPresent(), "param 'model'={} has an AD_Client_ID", model);

		final IMRPQueryBuilder queryBuilder = createMRPQueryBuilder()
				.setContextProvider(model) // use context from model
				.setAD_Client_ID(adClientId.get()) // use model's AD_Client_ID
				// Only those MRP records which are referencing our model
				.setReferencedModel(model)
				// Filter by TypeMRP (Demand/Supply)
				.setTypeMRP(typeMRP)
				// Filter by OrderType (i.e. like document base type)
				.setOrderType(orderType);

		//
		// In case we query for PP_Order and TypeMRP=Supply, we need to make sure only header Supply is returned (because that's what is expected).
		// The TypeMRP=D/S filter is not enough since a BOM Line can produce a supply (e.g. co-product)
		// TODO: get rid of this HARDCODED/particular case
		if (InterfaceWrapperHelper.isInstanceOf(model, I_PP_Order.class)
				&& X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
		{
			queryBuilder.setPP_Order_BOMLine_Null();
		}

		//
		// Return the query builder
		return queryBuilder;
	}

	protected BigDecimal getQtyOnHand(final Properties ctx, final int M_Warehouse_ID, final int M_Product_ID, final String trxName)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);

		final String sql = "SELECT COALESCE(bomQtyOnHand (M_Product_ID,?,0),0) FROM M_Product"
				+ " WHERE AD_Client_ID=? AND M_Product_ID=?";
		final BigDecimal qtyOnHand = DB.getSQLValueBDEx(trxName, sql, M_Warehouse_ID, adClientId, M_Product_ID);
		if (qtyOnHand == null)
		{
			return BigDecimal.ZERO;
		}
		return qtyOnHand;
	}

	@Override
	public BigDecimal getQtyOnHand(final IContextAware context, final I_M_Warehouse warehouse, final I_M_Product product)
	{
		final Properties ctx = context.getCtx();
		final String trxName = context.getTrxName();
		final int warehouseId = warehouse.getM_Warehouse_ID();
		final int productId = product.getM_Product_ID();
		return getQtyOnHand(ctx, warehouseId, productId, trxName);
	}
}
