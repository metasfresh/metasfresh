/**
 * 
 */
package de.metas.commission.form.zk;

/*
 * #%L
 * de.metas.commission.zkwebui
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


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.POWrapper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.MUser;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.compiere.util.Trx;
import org.zkoss.zul.AbstractTreeModel;

import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvComSponsorPoints_v1;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_AdvComSponsorPoints_v1;
import de.metas.commission.service.ISalesRepFactBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorDAO;
import de.metas.commission.util.HierarchyDescender;

/**
 * @author teo_sarca
 */
public class SponsorTreeModel extends AbstractTreeModel
{
	private static final int C_ADV_COM_SYSTEM_ID_MLM = 1000001;

	private static final int C_ADV_COM_SYSTEM_ID_SLM = 1000000;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6742795741646822834L;

	private static final Logger log = LogManager.getLogger(SponsorTreeModel.class);

	private final int user_id;
	private final int period_id;
	private final Timestamp date;

	private final I_C_AdvComSystem comSystem;

	public SponsorTreeModel(int user_id, int period_id, Timestamp date, final I_C_AdvComSystem comSystem)
	{
		super(new SponsorTreeNode());
		this.user_id = user_id;
		this.period_id = period_id;
		this.date = date;
		this.comSystem = comSystem;
	}

	@Override
	public Object getChild(Object parent, int index)
	{
		List<SponsorTreeNode> children = getChildren((SponsorTreeNode)parent);
		return children.get(index);
	}

	@Override
	public int getChildCount(Object parent)
	{
		final SponsorTreeNode parentNode = (SponsorTreeNode)parent;
		final List<SponsorTreeNode> children = getChildren(parentNode);
		return children.size();
	}

	@Override
	public boolean isLeaf(Object node)
	{
		// log.info("node="+node);
		return getChildCount(node) == 0;
	}

	private List<SponsorTreeNode> getChildren(SponsorTreeNode parent)
	{
		// final long ts = System.currentTimeMillis();
		try
		{
			final List<SponsorTreeNode> children;

			if (parent == getRoot())
			{
				children = getSponsorRoots(user_id, date);
			}
			else
			{
				children = getSponsorChildren(parent, date);
			}
			return children;
		}
		finally
		{
			// System.out.println("***SponsorTreeModel.getChildren("+parent.getSponsorNo()+"-"+parent.getBPName()+") - "+(ts
			// - System.currentTimeMillis())+" ms");
		}
	}

	@Cached
	private List<SponsorTreeNode> getSponsorRoots(final int AD_User_ID, final Timestamp date)
	{

		log.debug("AD_User_ID=" + AD_User_ID + ", date=" + date);

		final MUser user = MUser.get(Env.getCtx(), AD_User_ID);

		final I_C_BPartner bPartner = POWrapper.create(user.getC_BPartner(), I_C_BPartner.class);
	
		final ArrayList<SponsorTreeNode> list = new ArrayList<SponsorTreeNode>();

		boolean isSalesRep = true;
		List<I_C_Sponsor> sponsors = Services.get(ISponsorDAO.class).retrieveForSalesRepAt(bPartner, getDate());
		if (sponsors.isEmpty())
		{
			log.info("No sales rep sponsor found for " + bPartner + " at date=" + date + ". Using customer sponsor.");
			sponsors = Collections.singletonList(Services.get(ISponsorDAO.class).retrieveForBPartner(bPartner, true));
			isSalesRep = false;
		}
		else
		{
			isSalesRep = true;
		}

		for (final I_C_Sponsor sponsor : sponsors)
		{
			final SponsorTreeNode node = new SponsorTreeNode(sponsor.getC_Sponsor_ID(), sponsor.getSponsorNo(), isSalesRep,
					bPartner.getC_BPartner_ID(), bPartner.getName(), bPartner.isProspect(), bPartner.isCustomer());
			node.setTreeModel(this);
			node.setSponsorRoot(true);
			node.setLogicalLevel(0);

			list.add(node);
		}
		return list;
	}

	@Cached
	private List<SponsorTreeNode> getSponsorChildren(final SponsorTreeNode rootNode, final Timestamp date)
	{
		final Properties ctx = Env.getCtx();
		final I_C_Sponsor rootSponsor = InterfaceWrapperHelper.create(ctx, rootNode.getSponsor_ID(), I_C_Sponsor.class, Trx.TRXNAME_None);

		final Map<Integer, SponsorTreeNode> sponsorId2treeNode = new HashMap<Integer, SponsorTreeNode>();

		final List<SponsorTreeNode> list = new ArrayList<SponsorTreeNode>();

		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		if (comSystem.getC_AdvComSystem_ID() == C_ADV_COM_SYSTEM_ID_SLM)
		{
			final I_C_BPartner bPartnerRoot = sponsorBL.retrieveSalesRepAt(
							InterfaceWrapperHelper.getCtx(rootSponsor),
							date, rootSponsor, false,
							InterfaceWrapperHelper.getTrxName(rootSponsor));
			if (bPartnerRoot != null && !rootNode.isSponsorRoot())
			{
				// sponsor is a sales rep, but not the one that is currently logged in
				// don't return any children
				return Collections.emptyList();
			}
		}

		new HierarchyDescender()
		{
			@Override
			public Result actOnLevel(final I_C_Sponsor sponsorCurrentLevel, final int logicalLevel,
					final int hierarchyLevel, final Map<String, Object> contextInfo)
			{
				if (sponsorCurrentLevel.getC_Sponsor_ID() == rootSponsor.getC_Sponsor_ID())
				{
					// sponsorCurrentLevel belongs to rootNode -> not a child
					sponsorId2treeNode.put(rootSponsor.getC_Sponsor_ID(), rootNode);
					return Result.GO_ON;
				}

				//
				// retrieve sponsorCurrentLevel's bPartner
				I_C_BPartner bPartner = sponsorBL.retrieveSalesRepAt(
								InterfaceWrapperHelper.getCtx(sponsorCurrentLevel),
								date, sponsorCurrentLevel, false,
								InterfaceWrapperHelper.getTrxName(sponsorCurrentLevel));
				boolean isSalesRep = true;

				if (bPartner == null)
				{
					bPartner = POWrapper.create(sponsorCurrentLevel.getC_BPartner(), I_C_BPartner.class);
					isSalesRep = false;
				}

				//
				// create the child treeNode
				final String sponsorNo = sponsorCurrentLevel.getSponsorNo();

				final SponsorTreeNode node = 
						new SponsorTreeNode(sponsorCurrentLevel.getC_Sponsor_ID(), sponsorNo, isSalesRep,
								bPartner.getC_BPartner_ID(), bPartner.getName(), bPartner.isProspect(), bPartner.isCustomer());

				sponsorId2treeNode.put(sponsorCurrentLevel.getC_Sponsor_ID(), node);
				node.setTreeModel(SponsorTreeModel.this);
				list.add(node);

				//
				// set the node's logical level
				final I_C_Sponsor parentSponsor = (I_C_Sponsor)contextInfo.get(CTX_PREDECESSOR_SPONSOR);
				final SponsorTreeNode parentNode = sponsorId2treeNode.get(parentSponsor.getC_Sponsor_ID());

				final int parentLevel = parentNode.getLogicalLevel();

				//
				// set the logical level, depending on whether the current sponsor has at least rank 2a
				// note: logicalLevel is based on the Result returned for sponsorCurrentLevel's *parents*
				// (whether they were at least 2a), but we need to set this node's logical level based on
				// sponsorCurrent itself.
				final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);
				final boolean hasOwnLogicalLevel;
				if (comSystem.getC_AdvComSystem_ID() == C_ADV_COM_SYSTEM_ID_MLM)
				{
					hasOwnLogicalLevel =
							srfBL.isRankGE(comSystem, "2a", sponsorCurrentLevel, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant, getDate());
				}
				else
				{
					hasOwnLogicalLevel = true;
				}

				if (hasOwnLogicalLevel)
				{
					node.setLogicalLevel(parentLevel + 1);
				}
				else
				{
					node.setLogicalLevel(parentLevel);
				}
				return Result.GO_ON;
			}
		}.setDate(getDate()).climb(rootSponsor, 1);

		return list;
	}

	public int getC_Period_ID()
	{
		return this.period_id;
	}

	public Timestamp getDate()
	{
		return this.date;
	}

	public int getMonth()
	{
		Timestamp date = getDate();
		if (date == null)
			return -1;
		GregorianCalendar cal = new GregorianCalendar(Language.getLoginLanguage().getLocale());
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	public int getYear()
	{
		Timestamp date = getDate();
		if (date == null)
			return -1;
		GregorianCalendar cal = new GregorianCalendar(Language.getLoginLanguage().getLocale());
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	protected I_C_AdvComSponsorPoints_v1 getPoints(final SponsorTreeNode node)
	{

		final String whereClause =
				I_C_AdvComSponsorPoints_v1.COLUMNNAME_C_Sponsor_ID + "=?"
						+ " AND " + I_C_AdvComSponsorPoints_v1.COLUMNNAME_C_Period_ID + "=?"
						+ " AND " + I_C_AdvComSponsorPoints_v1.COLUMNNAME_C_AdvComSystem_ID + "=?";
		
		final X_C_AdvComSponsorPoints_v1 points = 
			new Query(Env.getCtx(), I_C_AdvComSponsorPoints_v1.Table_Name, whereClause, null)
				.setParameters(node.getSponsor_ID(), this.getC_Period_ID(), this.comSystem.getC_AdvComSystem_ID())
				.firstOnly();
		return points;
	}
}
