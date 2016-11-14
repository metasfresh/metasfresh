/**
 *
 */
package de.metas.adempiere.process;

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
import org.adempiere.exceptions.DBMoreThenOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_Relation;
import org.compiere.model.I_C_Order;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

/**
 * Create BP relation from document.
 *
 * @author tsa
 * @see http
 *      ://dewiki908/mediawiki/index.php/US1010:_unterschiedliche_Liefer-_und_Rechnungsempf%C3%A4nger_im_Auftragskopf_
 *      %282010122110000025%29
 *
 */
public class CreateBPRelationFromDocument extends SvrProcess
{
	public static String PARAM_C_BPartner_ID = I_C_BP_Relation.COLUMNNAME_C_BPartner_ID;
	public static String PARAM_C_BPartner_Location_ID = I_C_BP_Relation.COLUMNNAME_C_BPartner_Location_ID;
	public static String PARAM_C_BPartnerRelation_ID = I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_ID;
	public static String PARAM_C_BPartnerRelation_Location_ID = I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_Location_ID;
	public static String PARAM_IsBillTo = I_C_BP_Relation.COLUMNNAME_IsBillTo;
	public static String PARAM_IsPayFrom = I_C_BP_Relation.COLUMNNAME_IsPayFrom;
	public static String PARAM_IsRemitTo = I_C_BP_Relation.COLUMNNAME_IsRemitTo;
	public static String PARAM_IsShipTo = I_C_BP_Relation.COLUMNNAME_IsShipTo;

	private int p_C_BPartner_ID = -1;
	private int p_C_BPartner_Location_ID = -1;
	private int p_C_BPartnerRelation_ID = -1;
	private int p_C_BPartnerRelation_Location_ID = -1;
	private boolean p_IsBillTo = false;
	private boolean p_IsPayFrom = false;
	private boolean p_IsRemitTo = false;
	private boolean p_IsShipTo = false;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				;
			}
			else if (name.equals(PARAM_C_BPartner_ID))
			{
				p_C_BPartner_ID = para.getParameterAsInt();
			}
			else if (name.equals(PARAM_C_BPartner_Location_ID))
			{
				p_C_BPartner_Location_ID = para.getParameterAsInt();
			}
			else if (name.equals(PARAM_C_BPartnerRelation_ID))
			{
				p_C_BPartnerRelation_ID = para.getParameterAsInt();
			}
			else if (name.equals(PARAM_C_BPartnerRelation_Location_ID))
			{
				p_C_BPartnerRelation_Location_ID = para.getParameterAsInt();
			}
			else if (name.equals(PARAM_IsBillTo))
			{
				p_IsBillTo = para.getParameterAsBoolean();
			}
			else if (name.equals(PARAM_IsPayFrom))
			{
				p_IsPayFrom = para.getParameterAsBoolean();
			}
			else if (name.equals(PARAM_IsRemitTo))
			{
				p_IsRemitTo = para.getParameterAsBoolean();
			}
			else if (name.equals(PARAM_IsShipTo))
			{
				p_IsShipTo = para.getParameterAsBoolean();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_BP_Relation relation = findRelation(
				p_C_BPartner_ID, p_C_BPartner_Location_ID,
				p_C_BPartnerRelation_ID, p_C_BPartnerRelation_Location_ID,
				true);
		relation.setIsBillTo(p_IsBillTo);
		relation.setIsPayFrom(p_IsPayFrom);
		relation.setIsRemitTo(p_IsRemitTo);
		relation.setIsShipTo(p_IsShipTo);
		InterfaceWrapperHelper.save(relation);

		final String tableName = getTableName();
		if (I_C_Order.Table_Name.equals(tableName))
		{
			updateOrder(relation, getRecord_ID());
		}
		return "OK";
	}

	private void updateOrder(final I_C_BP_Relation rel, final int orderId)
	{
		I_C_Order order = null;
		if (orderId > 0)
		{
			order = InterfaceWrapperHelper.create(getCtx(), orderId, I_C_Order.class, get_TrxName());
		}
		if (order == null)
		{
			throw new AdempiereException("@NotFound@ @C_Order_ID@");
		}

		if (order.isProcessed())
		{
			log.info("Order is processed => not updating it");
			return;
		}

		if (rel.isBillTo() && order.isSOTrx()
				|| rel.isPayFrom() && !order.isSOTrx())
		{
			// never modify Order's C_BPartner_ID and C_BPartner_Location_ID fields:
			// order.setC_BPartner_ID(rel.getC_BPartner_ID());
			// order.setC_BPartner_Location_ID(rel.getC_BPartner_Location_ID());
			//
			order.setBill_BPartner_ID(rel.getC_BPartnerRelation_ID());
			order.setBill_Location_ID(rel.getC_BPartnerRelation_Location_ID());
		}

		InterfaceWrapperHelper.save(order);
	}

	private I_C_BP_Relation findRelation(final int bpartnerId, final int bpLocationId, final int bpRelationId, final int locRelationId, final boolean create)
	{
		if (bpartnerId == bpRelationId)
		{
			throw new AdempiereException("@C_BPartner_ID@ = @C_BPartnerRelation_ID@");
		}

		I_C_BP_Relation relation = null;
		final String whereClause = I_C_BP_Relation.COLUMNNAME_C_BPartner_ID + "=?"
				+ " AND " + I_C_BP_Relation.COLUMNNAME_C_BPartner_Location_ID + "=?"
				+ " AND " + I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_ID + "=?"
				+ " AND " + I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_Location_ID + "=?";
		try
		{
			relation = new Query(getCtx(), I_C_BP_Relation.Table_Name, whereClause, get_TrxName())
					.setParameters(bpartnerId, bpLocationId, bpRelationId, locRelationId)
					.setOnlyActiveRecords(true)
					.firstOnly(I_C_BP_Relation.class);
		}
		catch (final DBMoreThenOneRecordsFoundException e)
		{
			log.warn("More then one relation found for bpartnerId=" + bpartnerId + "bpLocationId=" + bpLocationId + ", bpRelationId=" + bpRelationId + ", locRelationId=" + locRelationId);
			relation = null;
		}

		if (relation == null && create)
		{
			relation = InterfaceWrapperHelper.create(getCtx(), I_C_BP_Relation.class, get_TrxName());
			relation.setC_BPartner_ID(bpartnerId);
			relation.setC_BPartner_Location_ID(bpLocationId);
			relation.setC_BPartnerRelation_ID(bpRelationId);
			relation.setC_BPartnerRelation_Location_ID(locRelationId);
			setName(relation);
		}
		return relation;
	}

	private void setName(final I_C_BP_Relation rel)
	{
		final StringBuffer name = new StringBuffer();

		final String nameFrom = rel.getC_BPartner().getName();
		name.append(nameFrom);

		if (rel.getC_BPartner_Location_ID() > 0)
		{
			final String locFrom = rel.getC_BPartner_Location().getName();
			name.append("(").append(locFrom).append(")");
		}

		name.append("->");

		final String nameTo = rel.getC_BPartnerRelation().getName();
		name.append(nameTo);

		if (rel.getC_BPartnerRelation_Location_ID() > 0)
		{
			final String locTo = rel.getC_BPartnerRelation_Location().getName();
			name.append("(").append(locTo).append(")");
		}
		rel.setName(name.toString());
		makeUniqueName(rel);
	}

	private void makeUniqueName(final I_C_BP_Relation rel)
	{
		int cnt = 1;
		while (cnt < 100)
		{
			final StringBuffer nameCurrent = new StringBuffer(rel.getName());
			if (cnt > 1)
			{
				nameCurrent.append(" (").append(cnt).append(")");
			}
			final String whereClause = I_C_BP_Relation.COLUMNNAME_Name + "=?"
					+ " AND " + I_C_BP_Relation.COLUMNNAME_C_BP_Relation_ID + "<>?";
			final boolean match = new Query(getCtx(), I_C_BP_Relation.Table_Name, whereClause, get_TrxName())
					.setParameters(nameCurrent.toString(), rel.getC_BP_Relation_ID())
					.setClient_ID()
					.match();
			if (!match)
			{
				rel.setName(nameCurrent.toString());
				return;
			}
			cnt++;
		}

		throw new AdempiereException("Cannot make name " + rel.getName() + " unique");
	}
}
