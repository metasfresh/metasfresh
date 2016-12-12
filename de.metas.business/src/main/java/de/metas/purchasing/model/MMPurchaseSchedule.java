package de.metas.purchasing.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.I_AD_Relation;
import org.adempiere.model.I_AD_RelationType;
import org.adempiere.model.MRelation;
import org.adempiere.model.MRelationType;
import org.adempiere.model.RelationTypeZoomProvider;
import org.adempiere.model.RelationTypeZoomProvidersFactory;
import org.adempiere.util.Services;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrgInfo;
import org.compiere.model.Query;
import org.slf4j.Logger;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;

public class MMPurchaseSchedule extends X_M_PurchaseSchedule
{

	private static final Logger logger = LogManager.getLogger(MMPurchaseSchedule.class);

	public static final String RELTYPE_CURRENT_PO_INT_NAME = "M_PurchaseSchedule_C_OrderLine_PO";

	public static final String RELTYPE_CURRENT_SO_INT_NAME = "C_OrderLine_SO_M_PurchaseSchedule";

	public static final String RELTYPE_SO_LINE_PO_LINE_INT_NAME = "C_OrderLine_SO_C_OrderLine_PO";

	public static final String RELTYPE_SO_PO_INT_NAME = "C_Order_SO_C_Order_PO";

	public static final String RELTYPE_REQ_INT_NAME = "M_RequisitionLine_M_PurchaseSchedule";

	/**
	 * 
	 */
	private static final long serialVersionUID = 5442667841408936468L;

	public MMPurchaseSchedule(final Properties ctx, final int M_PurchaseSchedule_ID, final String trxName)
	{
		super(ctx, M_PurchaseSchedule_ID, trxName);
	}

	public MMPurchaseSchedule(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Creates a purchase schedule for the given ol. Note: method does not create a relation ol<->sched.
	 * 
	 * @param ctx
	 * @param ol
	 * @param trxName
	 * @return
	 */
	public static MMPurchaseSchedule createForSalesOL(final Properties ctx, final I_C_OrderLine ol, final String trxName)
	{
		final MMPurchaseSchedule schedForOl = new MMPurchaseSchedule(ctx, 0, trxName);

		schedForOl.setClientOrg(ol.getAD_Client_ID(), ol.getAD_Org_ID());
		schedForOl.updateFromSalesOl(ctx, ol, trxName);
		schedForOl.saveEx();

		return schedForOl;
	}

	private void updateFromSalesOl(
			final Properties ctx,
			final I_C_OrderLine ol,
			final String trxName)
	{
		assert getAD_Client_ID() == ol.getAD_Client_ID() : this + "; " + ol;
		assert getAD_Org_ID() == ol.getAD_Org_ID() : this + "; " + ol;

		setM_Warehouse_ID(Services.get(IWarehouseAdvisor.class).evaluateWarehouse(ol).getM_Warehouse_ID());
		setM_Product_ID(ol.getM_Product_ID());
		setM_AttributeSetInstance_ID(ol.getM_AttributeSetInstance_ID());

		setIsIndividualPOSchedule(ol.isIndividualPOSchedule());
		if (ol.isIndividualPOSchedule())
		{
			setC_OrderLine_Individual_ID(ol.getC_OrderLine_ID());
		}

		// don't rely on 'ol.isDropShip()'
		final boolean isDropShip = retrieveIsDropship(ctx, trxName);
		setIsDropShip(isDropShip);
		if (isDropShip)
		{
			setDropShip_BPartner_ID(ol.getC_BPartner_ID());
			setDropShip_User_ID(ol.getAD_User_ID());
			setDropShip_Location_ID(ol.getC_BPartner_Location_ID());
		}
	}

	private boolean retrieveIsDropship(final Properties ctx, final String trxName)
	{
		return getM_Warehouse_ID() == MOrgInfo.get(ctx, getAD_Org_ID(), trxName).getDropShip_Warehouse_ID();
	}

	public static MMPurchaseSchedule createForPurchaseOL(final Properties ctx, final I_C_OrderLine ol, final String trxName)
	{
		final MMPurchaseSchedule schedForOl = new MMPurchaseSchedule(ctx, 0, trxName);

		schedForOl.setClientOrg(ol.getAD_Client_ID(), ol.getAD_Org_ID());
		schedForOl.updateFromPurchaseOl(ctx, ol, trxName);
		schedForOl.saveEx();

		return schedForOl;
	}

	private void updateFromPurchaseOl(
			final Properties ctx,
			final I_C_OrderLine ol,
			final String trxName)
	{
		assert getAD_Client_ID() == ol.getAD_Client_ID() : this + "; " + ol;
		assert getAD_Org_ID() == ol.getAD_Org_ID() : this + "; " + ol;

		setM_Warehouse_ID(ol.getM_Warehouse_ID());
		setM_Product_ID(ol.getM_Product_ID());
		setM_AttributeSetInstance_ID(ol.getM_AttributeSetInstance_ID());

		setIsIndividualPOSchedule(ol.isIndividualPOSchedule());

		final boolean dropShip = retrieveIsDropship(ctx, trxName);
		setIsDropShip(dropShip);

		if (dropShip)
		{
			final I_C_Order order = ol.getC_Order();

			setDropShip_BPartner_ID(order.getDropShip_BPartner_ID());
			setDropShip_User_ID(order.getDropShip_User_ID());
			setDropShip_Location_ID(order.getDropShip_Location_ID());
		}
	}

	@Override
	public String toString()
	{

		final StringBuffer sb = new StringBuffer();

		sb.append("MPurchaseSchedule[").append(get_ID());
		sb.append(", M_Product_ID=").append(getM_Product_ID());
		sb.append(", M_Warehouse_ID=").append(getM_Warehouse_ID());
		sb.append(", Qty=").append(getQty());
		sb.append(", QtyToOrder=").append(getQtyToOrder());
		sb.append(", QtyOnHand=").append(getQtyOnHand());
		sb.append(", QtyOrdered=").append(getQtyOrdered());
		sb.append(", QtyReserved=").append(getQtyReserved());
		sb.append("]");

		return sb.toString();
	}

	/**
	 * Retrieves the purchase schedules for a given order line.
	 * 
	 * Note: for one order line, there can be at most one purchase schedule line.
	 * 
	 * @param ctx
	 * @param ol
	 * @param trxName
	 * @return
	 */
	public static MMPurchaseSchedule retrieveForSOLine(
			final Properties ctx,
			final I_C_OrderLine ol,
			final String trxName)
	{
		final RelationTypeZoomProvider relType = RelationTypeZoomProvidersFactory.instance.getZoomProviderBySourceTableNameAndInternalName(I_C_OrderLine.Table_Name, RELTYPE_CURRENT_SO_INT_NAME);
		final List<MMPurchaseSchedule> result = MRelation.retrieveDestinations(ctx, relType, I_C_OrderLine.Table_Name, ol.getC_OrderLine_ID(), MMPurchaseSchedule.class, trxName);

		if (result.isEmpty())
		{
			return null;
		}
		assert result.size() == 1 : "Expected one purchase schedule; result=" + result;
		return result.get(0);
	}

	public static List<MMPurchaseSchedule> retrieveFor(
			final Properties ctx,
			final int productId,
			final int warehouseId,
			final String trxName)
	{
		final String wc = COLUMNNAME_M_Product_ID + "=? AND " + COLUMNNAME_M_Warehouse_ID + "=?";

		return new Query(ctx, Table_Name, wc, trxName)
				.setParameters(productId, warehouseId)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(COLUMNNAME_M_PurchaseSchedule_ID)
				.list();
	}

	public static MMPurchaseSchedule retrieveFor(
			final Properties ctx,
			final int productId,
			final int warehouseId,
			final BigDecimal pricePO,
			final String trxName)
	{
		final String wc = COLUMNNAME_M_Product_ID + "=? AND " + COLUMNNAME_M_Warehouse_ID + "=? AND " + COLUMNNAME_PricePO + "=? AND " + COLUMNNAME_IsDropShip + "'N'";

		return new Query(ctx, Table_Name, wc, trxName)
				.setParameters(productId, warehouseId, pricePO)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly();
	}

	public List<MOrderLine> retrieveSOls()
	{
		return retrieveSOls(getCtx(), this, get_TrxName());
	}

	public static List<MOrderLine> retrieveSOls(
			final Properties ctx,
			final I_M_PurchaseSchedule schedule,
			final String trxName)
	{
		final RelationTypeZoomProvider relType = RelationTypeZoomProvidersFactory.instance.getZoomProviderBySourceTableNameAndInternalName(I_M_PurchaseSchedule.Table_Name, RELTYPE_CURRENT_SO_INT_NAME);

		return MRelation.retrieveDestinations(ctx, relType, I_M_PurchaseSchedule.Table_Name, schedule.getM_PurchaseSchedule_ID(), MOrderLine.class, trxName);
	}

	public static PurchaseScheduleQuery mkQuery(final Properties ctx, final String trxName)
	{
		return new PurchaseScheduleQuery(ctx, trxName);
	}

	public static void updateRelationsFromSOLines(
			final Properties ctx,
			final I_C_OrderLine ol,
			final I_M_PurchaseSchedule oldSched,
			final I_M_PurchaseSchedule newSched,
			final String trxName)
	{
		if (oldSched == newSched)
		{
			// nothing to do
			return;
		}

		final I_AD_RelationType relType = MRelationType.retrieveForInternalName(ctx, RELTYPE_CURRENT_SO_INT_NAME, trxName);

		if (oldSched != null)
		{
			final String wc =
					I_AD_Relation.COLUMNNAME_AD_RelationType_ID + "=? AND "
							+ I_AD_Relation.COLUMNNAME_Record_Source_ID + "=? AND "
							+ I_AD_Relation.COLUMNNAME_Record_Target_ID + "=?";

			final List<MRelation> relsToDelete = new Query(ctx, I_AD_Relation.Table_Name, wc, trxName)
					.setParameters(relType.getAD_RelationType_ID(), ol.getC_OrderLine_ID(), oldSched.getM_PurchaseSchedule_ID())
					.list();

			for (final MRelation relToDelete : relsToDelete)
			{
				relToDelete.deleteEx(false);
			}
			logger.debug(" Deleted " + relsToDelete.size() + " MRelation records for " + relType + " and " + oldSched);
		}
		if (newSched != null)
		{
			// add new relation
			MRelation.add(ctx, relType, ol.getC_OrderLine_ID(), newSched.getM_PurchaseSchedule_ID(), trxName);
		}
	}

	public void addRelationFromReqLine(final I_M_RequisitionLine reqLine)
	{
		final I_AD_RelationType relType = MRelationType.retrieveForInternalName(getCtx(), RELTYPE_CURRENT_SO_INT_NAME, get_TrxName());

		MRelation.add(getCtx(), relType, reqLine.getM_RequisitionLine_ID(), this.get_ID(), get_TrxName());
	}

	public void addRelationToPOLine(final I_C_OrderLine pol)
	{
		final I_AD_RelationType relType = MRelationType.retrieveForInternalName(getCtx(), RELTYPE_CURRENT_PO_INT_NAME, get_TrxName());

		final String whereClause =
				I_AD_Relation.COLUMNNAME_AD_RelationType_ID + "=? AND " + I_AD_Relation.COLUMNNAME_Record_Source_ID + "=? AND " + I_AD_Relation.COLUMNNAME_Record_Target_ID + "=?";

		final MRelation existingRelation = new Query(getCtx(), I_AD_Relation.Table_Name, whereClause, get_TrxName())
				.setParameters(relType.getAD_RelationType_ID(), this.get_ID(), pol.getC_OrderLine_ID())
				.setOnlyActiveRecords(true)
				.first();
		if (existingRelation != null)
		{
			return;
		}

		MRelation.add(getCtx(), relType, this.get_ID(), pol.getC_OrderLine_ID(), get_TrxName());
	}

	public void addRelationSOLineToPOLine(final I_C_OrderLine salesOrderLine, final I_C_OrderLine purchaseOrderLine)
	{
		final I_AD_RelationType relType = MRelationType.retrieveForInternalName(getCtx(), RELTYPE_SO_LINE_PO_LINE_INT_NAME, get_TrxName());

		final String whereClause =
				I_AD_Relation.COLUMNNAME_AD_RelationType_ID + "=? AND " + I_AD_Relation.COLUMNNAME_Record_Source_ID + "=? AND " + I_AD_Relation.COLUMNNAME_Record_Target_ID + "=?";

		final MRelation existingRelation = new Query(getCtx(), I_AD_Relation.Table_Name, whereClause, get_TrxName())
				.setParameters(relType.getAD_RelationType_ID(), salesOrderLine.getC_OrderLine_ID(), purchaseOrderLine.getC_OrderLine_ID())
				.setOnlyActiveRecords(true)
				.first();
		if (existingRelation != null)
		{
			return;
		}

		MRelation.add(getCtx(), relType, salesOrderLine.getC_OrderLine_ID(), purchaseOrderLine.getC_OrderLine_ID(), get_TrxName());
	}

	public final static class PurchaseScheduleQuery
	{

		private Integer productId;

		private Integer warehouseId;

		private Integer asi;

		private BigDecimal pricePO;

		private Boolean isIndividualPOSchedule;

		private Boolean isPricePONull;

		private Boolean includeInPO;

		private Integer individualOrderLineId;

		private Integer orderPOId;

		private Integer dropshipPartner;

		private Integer dropshipLocation;

		private Integer dropshipUser;

		private final Properties ctx;
		private String trxName;

		private PurchaseScheduleQuery(final Properties ctx, final String trxName)
		{
			this.ctx = ctx;
			this.trxName = trxName;
		}

		public PurchaseScheduleQuery setProductId(int productId)
		{
			this.productId = productId;
			return this;
		}

		public PurchaseScheduleQuery setWarehouseId(int warehouseId)
		{
			this.warehouseId = warehouseId;
			return this;
		}

		public PurchaseScheduleQuery setAsi(int asi)
		{
			this.asi = asi;
			return this;
		}

		public PurchaseScheduleQuery setPricePO(BigDecimal pricePO)
		{
			this.pricePO = pricePO;
			return this;
		}

		public PurchaseScheduleQuery setIsIndividualPOSchedule(final boolean isIndividualPOSchedule)
		{
			this.isIndividualPOSchedule = isIndividualPOSchedule;
			return this;
		}

		public PurchaseScheduleQuery setIsPricePONull(final boolean isPricePONull)
		{
			this.isPricePONull = isPricePONull;
			return this;
		}

		public PurchaseScheduleQuery setIsIncludeInPO(final boolean includeInPO)
		{
			this.includeInPO = includeInPO;
			return this;
		}

		public PurchaseScheduleQuery setIndividualOrderLineId(final int individualOrderLineId)
		{
			this.individualOrderLineId = individualOrderLineId;
			return this;
		}

		public PurchaseScheduleQuery setOrderPOId(int orderId)
		{
			this.orderPOId = orderId;
			return this;
		}

		public PurchaseScheduleQuery setDropshipPartner(int dropshipPartner)
		{
			this.dropshipPartner = dropshipPartner;
			return this;
		}

		public PurchaseScheduleQuery setDropshipLocation(int dropshipLocation)
		{
			this.dropshipLocation = dropshipLocation;
			return this;
		}

		public PurchaseScheduleQuery setDropshipUser(int dropshipUser)
		{
			this.dropshipUser = dropshipUser;
			return this;
		}

		public List<MMPurchaseSchedule> list()
		{
			return mkQuery().list();
		}

		public MMPurchaseSchedule first()
		{
			return mkQuery().first();
		}

		private Query mkQuery()
		{
			final StringBuilder whereClause = new StringBuilder("TRUE");
			final List<Object> params = new ArrayList<Object>();

			mkWhereClause(whereClause, params);

			final Query query = new Query(ctx, Table_Name, whereClause.toString(), trxName)
					.setParameters(params)
					.setOnlyActiveRecords(true)
					.setOrderBy(COLUMNNAME_M_PurchaseSchedule_ID)
					.setClient_ID();
			return query;
		}

		private void mkWhereClause(final StringBuilder whereClause, final List<Object> params)
		{
			if (productId != null)
			{
				whereClause.append(" AND " + COLUMNNAME_M_Product_ID + "=?");
				params.add(productId);
			}
			if (warehouseId != null)
			{
				whereClause.append(" AND " + COLUMNNAME_M_Warehouse_ID + "=?");
				params.add(warehouseId);
			}
			if (asi != null)
			{
				whereClause.append(" AND " + COLUMNNAME_M_AttributeSetInstance_ID + "=?");
				params.add(asi);
			}
			if (pricePO != null)
			{
				whereClause.append(" AND " + COLUMNNAME_PricePO + "=?");
				params.add(pricePO);
			}
			if (isPricePONull != null)
			{
				if (isPricePONull)
				{
					whereClause.append(" AND " + COLUMNNAME_PricePO + " IS NULL");
				}
				else
				{
					whereClause.append(" AND " + COLUMNNAME_PricePO + " IS NOT NULL");
				}
			}
			if (isIndividualPOSchedule != null)
			{
				whereClause.append(" AND " + COLUMNNAME_IsIndividualPOSchedule + "=?");
				params.add(isIndividualPOSchedule);
			}
			if (includeInPO != null)
			{
				whereClause.append(" AND " + COLUMNNAME_IncludeInPurchase + "=?");
				params.add(includeInPO);
			}
			if (individualOrderLineId != null)
			{
				whereClause.append(" AND " + COLUMNNAME_C_OrderLine_Individual_ID + "=?");
				params.add(individualOrderLineId);
			}
			if (orderPOId != null)
			{
				whereClause.append(" AND " + COLUMNNAME_C_OrderPO_ID + "=?");
				params.add(orderPOId);
			}
			if (dropshipPartner != null)
			{
				whereClause.append(" AND " + COLUMNNAME_DropShip_BPartner_ID + "=?");
				params.add(dropshipPartner);
			}
			if (dropshipLocation != null)
			{
				whereClause.append(" AND " + COLUMNNAME_DropShip_Location_ID + "=?");
				params.add(dropshipLocation);
			}
			if (dropshipUser != null)
			{
				whereClause.append(" AND " + COLUMNNAME_DropShip_User_ID + "=?");
				params.add(dropshipUser);
			}
		}

	}

}
