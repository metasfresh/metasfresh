package de.metas.adempiere.modelvalidator;

import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.MClient;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_C_OrderLine;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.IDocumentLocationBL;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.freighcost.FreightCostRule;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.OrderFreightCostsService;
import de.metas.order.impl.OrderBL;
import de.metas.util.Check;
import de.metas.util.Services;

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
				final boolean recreate = MSysConfig.getBooleanValue("RECREATE_SEARCHKEY", true, client_id);
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
	public String modelChange(final PO po, final int typeInt) throws Exception
	{
		final ModelChangeType type = ModelChangeType.valueOf(typeInt);

		// start: c.ghita@metas.ro: 01563
		if (po instanceof X_C_OrderLine)
		{
			if (type.isNew() && type.isBefore() && po.getDynAttribute(PO.DYNATTR_CopyRecordSupport) == null)
			{
				final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(po, I_C_OrderLine.class);
				// bpartner address
				if (orderLine.getC_BPartner_Location_ID() > 0)
				{
					final String BPartnerAddress = orderLine.getBPartnerAddress();
					if (Check.isEmpty(BPartnerAddress, true))
					{
						Services.get(IDocumentLocationBL.class).setBPartnerAddress(orderLine);
					}
				}

				// 01717
				{
					final org.compiere.model.I_C_Order order = orderLine.getC_Order();
					if (order.isDropShip())
					{
						if (orderLine.getC_BPartner_ID() < 0)
						{
							orderLine.setC_BPartner_ID(order.getDropShip_BPartner_ID());
							orderLine.setAD_User_ID(order.getDropShip_User_ID());
							orderLine.setC_BPartner_Location_ID(order.getDropShip_Location_ID());
						}
					}
				}
			}
			return null;
		}
		// end: c.ghita@metas.ro: 01563

		final IOrderBL orderBL = Services.get(IOrderBL.class);
		final I_C_Order order = InterfaceWrapperHelper.create(po, I_C_Order.class);

		// start: c.ghita@metas.ro: 01447

		if (po.getDynAttribute(PO.DYNATTR_CopyRecordSupport) == null && !type.isAfter())
		{
			// BPartner address
			{
				if (order.getC_BPartner_Location_ID() > 0)
				{
					final String BPartnerAddress = order.getBPartnerAddress();
					if (Check.isEmpty(BPartnerAddress, true) || po.is_ValueChanged(I_C_Order.COLUMNNAME_C_BPartner_ID)
							|| po.is_ValueChanged(I_C_Order.COLUMNNAME_C_BPartner_Location_ID)
							|| po.is_ValueChanged(I_C_Order.COLUMNNAME_AD_User_ID))
					{
						Services.get(IDocumentLocationBL.class).setBPartnerAddress(order);
					}
				}
			}
			// bill to address
			{
				if (order.getBill_Location_ID() > 0)
				{
					final String BillToAddress = order.getBillToAddress();
					if (Check.isEmpty(BillToAddress, true) || po.is_ValueChanged(I_C_Order.COLUMNNAME_Bill_BPartner_ID)
							|| po.is_ValueChanged(I_C_Order.COLUMNNAME_Bill_Location_ID)
							|| po.is_ValueChanged(I_C_Order.COLUMNNAME_Bill_User_ID))
					{
						Services.get(IDocumentLocationBL.class).setBillToAddress(order);
					}
				}
			}
			// delivery to address
			{
				if (order.getDropShip_Location_ID() > 0 || order.getM_Warehouse_ID() > 0)
				{
					final String DeliveryToAddress = order.getDeliveryToAddress();
					if (Check.isEmpty(DeliveryToAddress, true) || po.is_ValueChanged(I_C_Order.COLUMNNAME_DropShip_BPartner_ID)
							|| po.is_ValueChanged(I_C_Order.COLUMNNAME_DropShip_Location_ID)
							|| po.is_ValueChanged(I_C_Order.COLUMNNAME_DropShip_User_ID))
					{
						Services.get(IDocumentLocationBL.class).setDeliveryToAddress(order);
					}
				}
			}
			
			// handover address
			{
				if (order.getHandOver_Location_ID() > 0)
				{
					final String handOverAddress = order.getHandOverAddress();
					if (Check.isEmpty(handOverAddress, true) || po.is_ValueChanged(I_C_Order.COLUMNNAME_HandOver_Partner_ID)
							|| po.is_ValueChanged(I_C_Order.COLUMNNAME_HandOver_Location_ID)
							|| po.is_ValueChanged(I_C_Order.COLUMNNAME_HandOver_User_ID))
					{
						Services.get(IDocumentLocationBL.class).setHandOverAddress(order);
					}
				}
			}
		}
		// end: c.ghita@metas.ro: 01447

		// metas: start: task 05899
		if (type.isBeforeSaveTrx() || (type.isBefore() && type.isNewOrChange()))
		{
			final boolean overridePricingSystem = false;
			orderBL.setM_PricingSystem_ID(order, overridePricingSystem);
		}
		// metas: end: task 05899

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
			if (Check.isEmpty(order.getIncoterm()))
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
		return null;
	}
}
