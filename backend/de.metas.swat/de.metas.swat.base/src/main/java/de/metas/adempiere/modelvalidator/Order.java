package de.metas.adempiere.modelvalidator;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.freighcost.FreightCostRule;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.OrderFreightCostsService;
import de.metas.order.impl.OrderBL;
import de.metas.order.location.adapter.OrderLineDocumentLocationAdapterFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.MClient;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Env;

/**
 * This model validator checks for each new invoice line if there needs to be an additional invoice line for freight cost.
 *
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Versandkostenermittlung/_-berechnung_(2009_0027_G28)'>DV-Konzept (2009_0027_G28)</a>"
 * @deprecated the code form this class shall be moved to {@link de.metas.order.model.interceptor.C_Order} and a new MV de.metas.modelvalidator.C_OrderLine.
 */
@Deprecated
public class Order implements ModelValidator
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IDocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(IDocumentLocationBL.class);

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

		engine.addModelChange(I_C_Order.Table_Name, this);
		engine.addDocValidate(I_C_Order.Table_Name, this);
		engine.addModelChange(I_C_OrderLine.Table_Name, this);

		// register this service for callouts and model validators
		Services.registerService(IOrderBL.class, new OrderBL());
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
		// 01371
		if (timing == TIMING_AFTER_PREPARE)
		{
			final I_C_Order order = InterfaceWrapperHelper.create(po, I_C_Order.class);
			final org.compiere.model.I_C_BPartner bpartner = order.getC_BPartner();

			final boolean isQuotation = Services.get(IOrderBL.class).isSalesProposalOrQuotation(order);
			final boolean convertToCustomer = order.isSOTrx() && !isQuotation;

			if (bpartner.isProspect() && convertToCustomer)
			{
				bpartner.setIsCustomer(true);
				bpartner.setIsProspect(false);
				//
				final int client_id = Env.getAD_Client_ID(Env.getCtx());
				final int org_id = Env.getAD_Org_ID(Env.getCtx());
				final boolean recreate = Services.get(ISysConfigBL.class).getBooleanValue("RECREATE_SEARCHKEY", true, client_id);
				if (recreate)
				{
					final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
					final String value = documentNoFactory.forTableName(I_C_BPartner.Table_Name, client_id, org_id)
							.build();
					bpartner.setValue(value);
				}

				InterfaceWrapperHelper.save(bpartner);
			}
		}
		return null;
	}

	@Override
	public String modelChange(final PO po, final int typeInt)
	{
		final ModelChangeType type = ModelChangeType.valueOf(typeInt);

		if (InterfaceWrapperHelper.isInstanceOf(po, I_C_OrderLine.class))
		{
			if (type.isNew() && type.isBefore() && !po.isCopying())
			{
				final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(po, I_C_OrderLine.class);
				updateOrderLineAddresses(orderLine);
			}
		}
		else if (InterfaceWrapperHelper.isInstanceOf(po, I_C_Order.class))
		{
			final I_C_Order order = InterfaceWrapperHelper.create(po, I_C_Order.class);

			if (type.isBeforeSaveTrx() || (type.isBefore() && type.isNewOrChange()))
			{
				final boolean overridePricingSystem = false;
				orderBL.setM_PricingSystem_ID(order, overridePricingSystem);
			}

			if (type.isAfter() && type.isNewOrChange())
			{
				// If the Price List is Changed (may be because of a PricingSystem change)
				// the prices in the order lines need to be updated.
				if (po.is_ValueChanged(I_C_Order.COLUMNNAME_M_PriceList_ID))
				{
					final MOrder mOrder = (MOrder)po;
					for (final MOrderLine ol : mOrder.getLines())
					{
						ol.setPrice();
						ol.saveEx();
					}
				}

				//
				// checking if all is okay with this order
				orderBL.checkForPriceList(order);
			}
			else if (type.isBefore() && type.isNewOrChange())
			{
				//
				// Reset IncotermLocation if Incoterm is empty
				if (order.getC_Incoterms_ID() <= 0)
				{
					order.setIncotermLocation("");
				}

				//
				// updating the freight cost amount, if necessary
				final boolean freightCostRuleChanged = po.is_ValueChanged(I_C_Order.COLUMNNAME_FreightCostRule);
				final FreightCostRule freightCostRule = FreightCostRule.ofCode(order.getFreightCostRule());
				if (freightCostRuleChanged && freightCostRule.isNotFixPrice())
				{
					final OrderFreightCostsService orderFreightCostService = Adempiere.getBean(OrderFreightCostsService.class);
					orderFreightCostService.updateFreightAmt(order);
				}
			}
		}

		return null;
	}

	private void updateOrderLineAddresses(final I_C_OrderLine orderLine)
	{
		// bpartner address
		if (orderLine.getC_BPartner_Location_ID() > 0)
		{
			final String bpartnerAddress = orderLine.getBPartnerAddress();
			if (Check.isBlank(bpartnerAddress))
			{
				documentLocationBL.updateRenderedAddressAndCapturedLocation(OrderLineDocumentLocationAdapterFactory.locationAdapter(orderLine));
			}
		}

		// 01717
		final org.compiere.model.I_C_Order order = orderLine.getC_Order();
		if (order.isDropShip())
		{
			if (orderLine.getC_BPartner_ID() < 0)
			{
				OrderLineDocumentLocationAdapterFactory.locationAdapter(orderLine).setFromOrderHeader(order);
			}
		}
	}
}
