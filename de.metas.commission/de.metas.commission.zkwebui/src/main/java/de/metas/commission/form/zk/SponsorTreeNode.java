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


import java.math.BigDecimal;

import org.compiere.util.Env;

import de.metas.commission.model.I_C_AdvComSponsorPoints_v1;

/**
 * @author teo_sarca
 */
public class SponsorTreeNode
{
	public static final String SPONSORTYPENAME_INT = "INT";
	public static final String SPONSORTYPENAME_VP = "VP";
	public static final String SPONSORTYPENAME_ENDK = "ENDK";
	
	private SponsorTreeModel treeModel = null;

	private final int sponsor_id;
	private final String sponsorNo;
	private final boolean isSalesRep;
	private final int bpartner_id;
	private final String bpName;
	private final boolean isProspect;
	private final boolean isCustomer;
	/** True if the content of this node was exploded in same parent */
	private boolean isExploded = false;
	private BigDecimal revenueAmt = Env.ZERO;
	private int logicalLevel = 0;

	private I_C_AdvComSponsorPoints_v1 points = null;
	private int pointsPeriod_ID = -1;

	private boolean sponsorRoot = false;

	public SponsorTreeNode()
	{
		this(-1, null, false, -1, null, false, false);
	}

	public SponsorTreeNode(int sponsor_id, String sponsorNo, boolean isSalesRep,
			int bpartner_id, String bpName, boolean isProspect, boolean isCustomer)
	{
		this.sponsor_id = sponsor_id;
		this.sponsorNo = sponsorNo;
		this.isSalesRep = isSalesRep;
		this.bpartner_id = bpartner_id;
		this.bpName = bpName;
		this.isProspect = isProspect;
		this.isCustomer = isCustomer;
	}

	public int getSponsor_ID()
	{
		return sponsor_id;
	}

	public String getSponsorNo()
	{
		return sponsorNo;
	}

	public boolean isSalesRep()
	{
		return isSalesRep;
	}
	
	public int getBPartner_ID()
	{
		return bpartner_id;
	}

	public String getBPName()
	{
		return bpName;
	}
	
	public boolean isProspect()
	{
		return isProspect;
	}

	public boolean isExploded()
	{
		return isExploded;
	}

	public void setExploded(boolean isExploded)
	{
		this.isExploded = isExploded;
	}

	public SponsorTreeModel getTreeModel()
	{
		return treeModel;
	}

	public void setTreeModel(SponsorTreeModel treeModel)
	{
		this.treeModel = treeModel;
	}

	public BigDecimal getRevenueAmt()
	{
		return revenueAmt;
	}

	public void setRevenueAmt(BigDecimal revenueAmt)
	{
		this.revenueAmt = revenueAmt;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (isSalesRep ? 1231 : 1237);
		result = prime * result + ((sponsorNo == null) ? 0 : sponsorNo.hashCode());
		result = prime * result + sponsor_id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SponsorTreeNode other = (SponsorTreeNode)obj;
		if (isSalesRep != other.isSalesRep)
			return false;
		if (sponsorNo == null)
		{
			if (other.sponsorNo != null)
				return false;
		}
		else if (!sponsorNo.equals(other.sponsorNo))
			return false;
		if (sponsor_id != other.sponsor_id)
			return false;
		return true;
	}

	public String getSponsorTypeName()
	{
		// see http://dewiki908/mediawiki/index.php/01931:_Interessenten_in_B2B_Downline_Navigator_kennzeichnen_%282011080310000075%29#Detailbeschreibung_fachlich
		if (isSalesRep)
		{
			return SPONSORTYPENAME_VP;
		}
		else
		{
			if (isCustomer)
			{
				if (isProspect)
					return SPONSORTYPENAME_INT;
				else
					return SPONSORTYPENAME_ENDK;
			}
			else
			{
				if (isProspect)
					return SPONSORTYPENAME_INT;
				else
					return "";
			}
		}
	}

	public void setLogicalLevel(int logicalLevel)
	{
		this.logicalLevel = logicalLevel;
	}

	public int getLogicalLevel()
	{
		return logicalLevel;
	}

	public I_C_AdvComSponsorPoints_v1 getPoints()
	{
		if (treeModel == null || treeModel.getC_Period_ID() <= 0)
		{
			return null;
		}

		// Points were already checked for period and not found => no need to retry it again
		if (points == null && pointsPeriod_ID == treeModel.getC_Period_ID())
		{
			return null;
		}

		if (points == null || points.getC_Period_ID() != treeModel.getC_Period_ID())
		{
			points = treeModel.getPoints(this);
			pointsPeriod_ID = treeModel.getC_Period_ID();
		}

		return points;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(sponsorNo);
		sb.append(" - ").append(getSponsorTypeName());
		sb.append(" - ").append(bpName);
		sb.append(" (");
		sb.append(revenueAmt);
		if (treeModel != null)
		{
			int count = treeModel.getChildCount(this);
			sb.append(" / ").append(count);
		}
		sb.append(")");
		return sb.toString();
	}

	public void setSponsorRoot(boolean b)
	{
		this.sponsorRoot = b;
	}

	public boolean isSponsorRoot()
	{
		return sponsorRoot;
	}
}
