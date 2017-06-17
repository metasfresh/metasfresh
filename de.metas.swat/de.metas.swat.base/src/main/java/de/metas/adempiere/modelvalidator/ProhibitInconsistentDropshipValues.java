package de.metas.adempiere.modelvalidator;

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


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;
import org.compiere.model.MClient;
import org.compiere.model.MInOut;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.X_C_DocType;

import de.metas.adempiere.model.I_C_Order;
import de.metas.i18n.IMsgBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.modelvalidator.SwatValidator;

/**
 * Makes sure that the IsDropShip checkbox in various tables is consistent with the selected warehouse and other dropship related settings.
 *
 * Note: This model validator is not registered by {@link SwatValidator}, because we want the option to disable it without code change if required
 *
 * @author ts
 *
 */
public class ProhibitInconsistentDropshipValues implements ModelValidator
{
	private static final String ERR_ORDERLINE_INCONSISTENT_DROP_SHIP_SOOL_B_PARTNER_LOCATION_6P = "MOrderLine_InconsistentDropShip_soOl_BPartner_Location";
	private static final String ERR_ORDERLINE_INCONSISTENT_DROP_SHIP_SOOL_WAREHOUSE_6P = "MOrderLine_InconsistentDropShip_soOl_Warehouse";
	private static final String ERR_ORDERLINE_INCONSISTENT_DROP_SHIP_SOOL_PRODUCT_8P = "MOrderLine_InconsistentDropShip_soOl_Product";

	private static final String ERR_INCONSISTENT_DROP_SHIP_MORDER_WAREHOUSE_3P = "InconsistentDropShip_MOrder_Warehouse";
	private static final String ERR_INCONSISTENT_DROP_SHIP_MORDER_DROPSHIP_3P = "InconsistentDropShip_MOrder_Dropship";
	private static final String ERR_INCONSISTENT_DROP_SHIP_PURCHASE_ORDER_DOC_TYPE_2P = "InconsistentDropShip_PurchaseOrder_DocType";
	private static final String ERR_INCONSISTENT_DROP_SHIP_SALES_ORDER_DOC_TYPE_2P = "InconsistentDropShip_SalesOrder_DocType";

	private static final String ERR_INCONSISTENT_DROP_SHIP_MINOUT_NOT_MATERIAL_RECEIPT_2P = "InconsistentDropShip_MInOut_Not_MaterialReceipt";
	private static final String ERR_INCONSISTENT_DROP_SHIP_MINOUT_MISSING_INFOS_3P = "InconsistentDropShip_MInOut_MissingInfos";
	private static final String ERR_INCONSISTENT_DROP_SHIP_MINOUT_WAREHOUSE_3P = "InconsistentDropShip_MInOut_Warehouse";
	private static final String ERR_INCONSISTENT_DROP_SHIP_MINOUT_DROPSHIP_3P = "InconsistentDropShip_MInOut_Dropship";
	private static final String ERR_INCONSISTENT_DROP_SHIP_MINOUT_SOTRX_1P = "InconsistentDropShip_MInOut_SOTrx";
	private static final String ERR_INCONSISTENT_DROP_SHIP_RECEIPT_DOC_TYPE_2P = "InconsistentDropShip_Receipt_DocType";
	private static final String ERR_INCONSISTENT_DROP_SHIP_SHIPMENT_DOC_TYPE_2P = "InconsistentDropShip_Shipment_DocType";

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
		engine.addModelChange(I_C_OrderLine.Table_Name, this);
		engine.addModelChange(I_M_InOut.Table_Name, this);
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
		if (type != TYPE_BEFORE_CHANGE && type != TYPE_BEFORE_NEW)
		{
			return null;
		}

		if (po instanceof MInOut)
		{
			final MInOut io = (MInOut)po;

			final I_C_DocType ioDocType;
			if (io.getC_DocType_ID() > 0)
			{
				ioDocType = io.getC_DocType();
			}
			else
			{
				ioDocType = null;
			}

			if (ioDocType != null && ioDocType.isSOTrx() != io.isSOTrx())
			{
				if (ioDocType.isSOTrx())
				{
					throw new AdempiereException(Services.get(IMsgBL.class).getMsg(io.getCtx(),
							ERR_INCONSISTENT_DROP_SHIP_SHIPMENT_DOC_TYPE_2P,
							new Object[] { io.getDocumentNo(), ioDocType.getName() }));
				}
				else
				{
					throw new AdempiereException(Services.get(IMsgBL.class).getMsg(io.getCtx(),
							ERR_INCONSISTENT_DROP_SHIP_RECEIPT_DOC_TYPE_2P,
							new Object[] { io.getDocumentNo(), ioDocType.getName() }));
				}
			}

			if (io.isSOTrx())
			{
				if (io.isDropShip())
				{
					throw new AdempiereException(Services.get(IMsgBL.class).getMsg(io.getCtx(),
							ERR_INCONSISTENT_DROP_SHIP_MINOUT_SOTRX_1P,
							new Object[] { io.getDocumentNo() }));
				}
			}
			else
			{
				if (io.getM_Warehouse_ID() <= 0)
				{
					// do nothing
					// Actually, for M_InOut, this shall never happen
				}
				else
				{

					final boolean hasDropshipWarehouse = io.getM_Warehouse_ID() == MOrgInfo.get(io.getCtx(), po.getAD_Org_ID(), io.get_TrxName()).getDropShip_Warehouse_ID();
					if (io.isDropShip() != hasDropshipWarehouse)
					{

						if (io.isDropShip())
						{
							throw new AdempiereException(Services.get(IMsgBL.class).getMsg(io.getCtx(),
									ERR_INCONSISTENT_DROP_SHIP_MINOUT_WAREHOUSE_3P,
									new Object[] {
											io.getDocumentNo(),
											io.getM_Warehouse().getName(),
											MOrg.get(io.getCtx(), io.getAD_Org_ID()).getName()
									}));
						}
						else
						{
							throw new AdempiereException(Services.get(IMsgBL.class).getMsg(io.getCtx(),
									ERR_INCONSISTENT_DROP_SHIP_MINOUT_DROPSHIP_3P,
									new Object[] {
											io.getDocumentNo(),
											io.getM_Warehouse().getName(),
											MOrg.get(io.getCtx(), io.getAD_Org_ID()).getName()
									}));
						}
					}
				}
				final boolean hasDropshipInfos = io.getDropShip_BPartner_ID() > 0 && io.getDropShip_Location_ID() > 0;
				if (io.isDropShip() && !hasDropshipInfos)
				{
					throw new AdempiereException(Services.get(IMsgBL.class).getMsg(io.getCtx(),
							ERR_INCONSISTENT_DROP_SHIP_MINOUT_MISSING_INFOS_3P,
							new Object[] {
									io.getDocumentNo(),
									Services.get(IMsgBL.class).translate(io.getCtx(), I_M_InOut.COLUMNNAME_DropShip_BPartner_ID),
									Services.get(IMsgBL.class).translate(io.getCtx(), I_M_InOut.COLUMNNAME_DropShip_Location_ID),
							}));
				}

				if (ioDocType != null && io.isDropShip() && !X_C_DocType.DOCBASETYPE_MaterialReceipt.equals(ioDocType.getDocBaseType()))
				{
					throw new AdempiereException(Services.get(IMsgBL.class).getMsg(io.getCtx(),
							ERR_INCONSISTENT_DROP_SHIP_MINOUT_NOT_MATERIAL_RECEIPT_2P,
							new Object[] {
									io.getDocumentNo(),
									ioDocType.getName() }));
				}
			}
		}
		else if (po instanceof MOrder)
		{
			final MOrder o = (MOrder)po;

			final I_C_DocType oDocType;
			if (o.getC_DocType_ID() > 0)
			{
				oDocType = o.getC_DocType();
			}
			else
			{
				oDocType = null;
			}

			if (oDocType != null && oDocType.isSOTrx() != o.isSOTrx())
			{
				// the doctype's SOTrx-Flag doe not match the order's SOTrx-Flag
				if (o.isSOTrx())
				{
					throw new AdempiereException(Services.get(IMsgBL.class).getMsg(o.getCtx(),
							ERR_INCONSISTENT_DROP_SHIP_SALES_ORDER_DOC_TYPE_2P,
							new Object[] { o.getDocumentNo(), oDocType.getName() }));
				}
				else
				{
					throw new AdempiereException(Services.get(IMsgBL.class).getMsg(o.getCtx(),
							ERR_INCONSISTENT_DROP_SHIP_PURCHASE_ORDER_DOC_TYPE_2P,
							new Object[] { o.getDocumentNo(), oDocType.getName() }));
				}
			}

			// Note: The following check is only performed for purchase orders,
			// because with sales orders, isDropShip has a meaning of "isSpecialDeliveryAddress".
			// Further dropShip warehouses play no role with . They might eventually play a role, but not before
			// warehouses are altogether removed from sales orders.
			final boolean isPurchaseOrder = !o.isSOTrx();

			if (o.getM_Warehouse_ID() <= 0)
			{
				// do nothing
				// warehouse was not set yet, which is a valid case for orders.
			}
			else
			{

				final boolean hasDropshipWarehouse = o.getM_Warehouse_ID() == MOrgInfo.get(o.getCtx(), po.getAD_Org_ID(), o.get_TrxName()).getDropShip_Warehouse_ID();
				if (isPurchaseOrder && o.isDropShip() != hasDropshipWarehouse)
				{

					if (o.isDropShip())
					{
						// 'o' is a dropship-order, but has no dropship-warehouse
						throw new AdempiereException(Services.get(IMsgBL.class).getMsg(o.getCtx(),
								ERR_INCONSISTENT_DROP_SHIP_MORDER_WAREHOUSE_3P,
								new Object[] {
										o.getDocumentNo(),
										o.getM_Warehouse().getName(),
										MOrg.get(o.getCtx(), o.getAD_Org_ID()).getName() }));
					}
					else
					{
						// 'o' is not a dropship-order, but has a dropship-warehouse
						throw new AdempiereException(Services.get(IMsgBL.class).getMsg(o.getCtx(),
								ERR_INCONSISTENT_DROP_SHIP_MORDER_DROPSHIP_3P,
								new Object[] {
										o.getDocumentNo(),
										o.getM_Warehouse().getName(),
										MOrg.get(o.getCtx(), o.getAD_Org_ID()).getName() }));
					}
				}
			}
			final boolean hasDropshipInfos = o.getDropShip_BPartner_ID() > 0 && o.getDropShip_Location_ID() > 0;
			// No need to show a user friendly message from this validator.
			// An "assume" is sufficient, because we made these columns mandatory, if DropShip=Y
			Check.assume(
					!o.isDropShip() || hasDropshipInfos,
					"If " + o + " has DropShip='Y', then DropShip_BPartner_ID and DropShip_Location_ID are gt 0");
		}
		else if (po instanceof MOrderLine)
		{
			final MOrderLine ol = (MOrderLine)po;
			final boolean hasDropshipWarehouse = ol.getM_Warehouse_ID() == MOrgInfo.get(ol.getCtx(), ol.getAD_Org_ID(), ol.get_TrxName()).getDropShip_Warehouse_ID();
			final MOrder parentOrder = ol.getParent();
			final boolean isPOorderline = !parentOrder.isSOTrx();
			if (isPOorderline)
			{
				// this relType somehow doesn't exist anymore. we comment this out..maybe the logic can be an inspiration when we approach this topic once again.
				// @formatter:off
//				final I_AD_RelationType relType = MRelationType.retrieveForInternalName(ol.getCtx(), MMPurchaseSchedule.RELTYPE_SO_LINE_PO_LINE_INT_NAME, ol.get_TrxName());
//				final List<MOrderLine> soOls = MRelation.retrieveDestinations(ol.getCtx(), relType, ol, ol.get_TrxName());
//				for (final MOrderLine soOl : soOls)
//				{
//					final MOrder parent = soOl.getParent();
//
//					//
//					// in case the parent is not set (shall not happen) we need this to avoid NPE.
//					final String parentDocumentNumber = getParentDocNoToUse(parent);
//
//					if (soOl.getM_Warehouse_ID() != ol.getM_Warehouse_ID())
//					{
//						throw new AdempiereException(Services.get(IMsgBL.class).getMsg(ol.getCtx(),
//								ERR_ORDERLINE_INCONSISTENT_DROP_SHIP_SOOL_WAREHOUSE_6P,
//								new Object[] {
//										parentOrder.getDocumentNo(), ol.getLine(), Services.get(IWarehouseAdvisor.class).evaluateWarehouse(ol).getName(),
//										parentDocumentNumber, soOl.getLine(), Services.get(IWarehouseAdvisor.class).evaluateWarehouse(soOl).getName()
//								}));
//					}
//
//					if (hasDropshipWarehouse && soOl.getC_BPartner_Location_ID() != ol.getC_BPartner_Location_ID())
//					{
//						throw new AdempiereException(Services.get(IMsgBL.class).getMsg(ol.getCtx(),
//								ERR_ORDERLINE_INCONSISTENT_DROP_SHIP_SOOL_B_PARTNER_LOCATION_6P,
//								new Object[] {
//										parentOrder.getDocumentNo(),
//										ol.getLine(),
//										ol.getC_BPartner_Location_ID() > 0 ? ol.getC_BPartner_Location().getName() : "<NULL>",
//										parentDocumentNumber,
//										soOl.getLine(),
//										soOl.getC_BPartner_Location_ID() > 0 ? soOl.getC_BPartner_Location().getName() : "<NULL>",
//								}));
//					}
//
//					if (soOl.getM_Product_ID() != ol.getM_Product_ID() || soOl.getM_AttributeSetInstance_ID() != ol.getM_AttributeSetInstance_ID())
//					{
//						if (soOl.getM_AttributeSetInstance_ID() != 0)
//						{
//							throw new AdempiereException(Services.get(IMsgBL.class).getMsg(ol.getCtx(),
//									ERR_ORDERLINE_INCONSISTENT_DROP_SHIP_SOOL_PRODUCT_8P,
//									new Object[] {
//											parentOrder.getDocumentNo(),
//											ol.getLine(),
//											ol.getM_Product_ID() > 0 ? ol.getM_Product().getValue() : "<NULL>",
//											ol.getM_AttributeSetInstance_ID(),
//											parentDocumentNumber,
//											soOl.getLine(),
//											soOl.getM_Product_ID() > 0 ? soOl.getM_Product().getValue() : "<NULL>",
//											soOl.getM_AttributeSetInstance_ID()
//									}));
//						}
//					}
//				}
				// @formatter:on
			}
		}
		return null;
	}

	private String getParentDocNoToUse(final MOrder parent)
	{
		if (parent == null)
		{
			return "<Parent order not set>";
		}

		else
		{
			return parent.getDocumentNo();
		}
	}
}
