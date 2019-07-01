package de.metas.adempiere.modelvalidator;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MClient;
import org.compiere.model.MOrder;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;

import de.metas.adempiere.callout.OrderFastInput;
import de.metas.adempiere.model.I_C_Order;
import de.metas.freighcost.api.IFreightCostBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.impl.OrderLineBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * @deprecated the code form this class shall be moved a new MV de.metas.modelvalidator.C_OrderLine.
 * @author ts
 *
 */
@Deprecated
public class OrderLine implements ModelValidator
{
	private int ad_Client_ID = -1;

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

		engine.addModelChange(I_C_OrderLine.Table_Name, this);

		// register this service for callouts and model validators
		Services.registerService(IOrderLineBL.class, new OrderLineBL());
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing to do
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		// nothing to do
		return null;
	}

	@Override
	public String modelChange(final PO po, int type)
	{
		onNewAndChangeAndDelete(po, type);
		return null;
	}

	private void onNewAndChangeAndDelete(final PO po, int type)
	{
		if (!(type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE || type == TYPE_AFTER_DELETE))
		{
			return;
		}

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(po, I_C_OrderLine.class);

		//
		// updating the freight cost amount, if necessary
		final MOrder orderPO = (MOrder)ol.getC_Order();

		final String dontUpdateOrder = Env.getContext(po.getCtx(), OrderFastInput.OL_DONT_UPDATE_ORDER + orderPO.get_ID());
		if (Check.isEmpty(dontUpdateOrder) || !"Y".equals(dontUpdateOrder))
		{
			final boolean newOrDelete = type == TYPE_AFTER_NEW || type == TYPE_AFTER_DELETE;
			final boolean linesAmtChanged = po.is_ValueChanged(I_C_OrderLine.COLUMNNAME_LineNetAmt);
			final boolean notFixPrice = !X_C_Order.FREIGHTCOSTRULE_FixPrice.equals(orderPO.getFreightCostRule());
			final boolean isCopy = InterfaceWrapperHelper.isCopy(po); // metas: cg: task US215
			if (!isCopy && (linesAmtChanged || notFixPrice || newOrDelete))
			{
				final IFreightCostBL freighCostBL = Services.get(IFreightCostBL.class);
				
				final ProductId productId = ProductId.ofRepoIdOrNull(ol.getM_Product_ID());
				if (productId != null && freighCostBL.isFreightCostProduct(productId))
				{
					// this ol is a freight cost order line

					final I_C_Order order = InterfaceWrapperHelper.create(orderPO, I_C_Order.class);

					final IOrderBL orderBL = Services.get(IOrderBL.class);
					if (orderBL.updateFreightAmt(po.getCtx(), order, po.get_TrxName()))
					{
						orderPO.saveEx();
					}
				}
			}
		}
	}
}
