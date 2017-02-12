/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.bpartner.service.IBPartnerStats;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.exceptions.BPartnerNoAddressException;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Dunning Run Entry Model
 * 
 * @author Jorg Janke
 * @version $Id: MDunningRunEntry.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 * 
 * @author Teo Sarca - BF [ 1739022 ], BF [ 1739096 ]
 * 
 *         FR 2872010 - Dunning Run for a complete Dunning (not just level) - Developer: Carlos Ruiz - globalqss - Sponsor: Metas
 */
public class MDunningRunEntry extends X_C_DunningRunEntry
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3838792682143065656L;

	/** Logger */
	private static Logger s_log = LogManager.getLogger(MDunningRunEntry.class);

	/**
	 * Standard Constructor
	 * 
	 * @param ctx context
	 * @param C_DunningRunEntry_ID id
	 * @param trxName transaction
	 */
	public MDunningRunEntry(Properties ctx, int C_DunningRunEntry_ID, String trxName)
	{
		super(ctx, C_DunningRunEntry_ID, trxName);
		if (C_DunningRunEntry_ID == 0)
		{
			// setC_BPartner_ID (0);
			// setC_BPartner_Location_ID (0);
			// setAD_User_ID (0);

			// setSalesRep_ID (0);
			// setC_Currency_ID (0);
			setAmt(Env.ZERO);
			setQty(Env.ZERO);
			setProcessed(false);
		}
	}	// MDunningRunEntry

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MDunningRunEntry(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MDunningRunEntry

	/**
	 * Parent Constructor
	 * 
	 * @param parent parent
	 */
	public MDunningRunEntry(MDunningRun parent)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setC_DunningRun_ID(parent.getC_DunningRun_ID());
		m_parent = parent;
	}	// MDunningRunEntry

	/** Parent */
	private MDunningRun m_parent = null;

	/**
	 * Set BPartner
	 * 
	 * @param bp partner
	 * @param isSOTrx SO
	 */
	public void setBPartner(MBPartner bp, boolean isSOTrx)
	{
		setC_BPartner_ID(bp.getC_BPartner_ID());
		MBPartnerLocation[] locations = bp.getLocations(false);
		// Location
		if (locations.length == 1)
			setC_BPartner_Location_ID(locations[0].getC_BPartner_Location_ID());
		else
		{
			MBPartnerLocation firstActive = null;
			MBPartnerLocation firstBillTo = null;
			for (int i = 0; i < locations.length; i++)
			{
				MBPartnerLocation location = locations[i];
				if (!location.isActive())
				{
					continue;
				}
				else if (firstActive == null)
				{
					firstActive = location;
				}
				if ((location.isPayFrom() && isSOTrx)
						|| (location.isRemitTo() && !isSOTrx))
				{
					setC_BPartner_Location_ID(location.getC_BPartner_Location_ID());
					break;
				}
				else if (firstBillTo == null && location.isBillTo())
				{
					firstBillTo = location;
				}
			}

			// If IsPayFrom or isRemitTo is not found among the BP Locations, check IsBillTo
			// if that isn't available, we should use any active location
			if (getC_BPartner_Location_ID() == 0)
			{
				if (firstBillTo != null)
				{
					setC_BPartner_Location_ID(firstBillTo.getC_BPartner_Location_ID());
				}
				else if (firstActive != null)
				{
					String msg = "@C_BPartner_ID@ " + bp.getName();
					if (isSOTrx)
						msg += " @No@ @IsPayFrom@";
					else
						msg += " @No@ @IsRemitTo@";
					msg += " & @IsBillTo@";
					log.info(msg);
					setC_BPartner_Location_ID(firstActive.getC_BPartner_Location_ID());
				}
			}
		}
		if (getC_BPartner_Location_ID() == 0)
		{
			throw new BPartnerNoAddressException(bp);
		}
		// User with location
		MUser[] users = MUser.getOfBPartner(getCtx(), bp.getC_BPartner_ID(), get_TrxName());
		if (users.length == 1)
			setAD_User_ID(users[0].getAD_User_ID());
		else
		{
			for (int i = 0; i < users.length; i++)
			{
				MUser user = users[i];
				if (user.getC_BPartner_Location_ID() == getC_BPartner_Location_ID())
				{
					setAD_User_ID(users[i].getAD_User_ID());
					break;
				}
			}
		}
		//
		int SalesRep_ID = bp.getSalesRep_ID();
		if (SalesRep_ID != 0)
			setSalesRep_ID(SalesRep_ID);
	}	// setBPartner

	/**
	 * Get Lines
	 * 
	 * @return Array of all lines for this Run
	 */
	public MDunningRunLine[] getLines()
	{
		return getLines(false);
	}	// getLines

	/**
	 * Get Lines
	 * 
	 * @param onlyInvoices only with invoices
	 * @return Array of all lines for this Run
	 */
	public MDunningRunLine[] getLines(boolean onlyInvoices)
	{
		List<Object> params = new ArrayList<Object>();
		StringBuffer whereClause = new StringBuffer();

		whereClause.append("C_DunningRunEntry_ID=?");
		params.add(getC_DunningRunEntry_ID());

		if (onlyInvoices)
			whereClause.append(" AND C_Invoice_ID IS NOT NULL");

		List<MDunningRunLine> list = new Query(getCtx(), I_C_DunningRunLine.Table_Name, whereClause.toString(), get_TrxName())
				.setParameters(params)
				.setOrderBy(I_C_DunningRunLine.COLUMNNAME_C_DunningRunLine_ID)
				.list();
		//
		MDunningRunLine[] retValue = new MDunningRunLine[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getLines

	/**
	 * Check whether has Invoices
	 * 
	 * @return true if it has Invoices
	 */
	public boolean hasInvoices()
	{
		final String whereClause = "C_DunningRunEntry_ID=? AND C_Invoice_ID IS NOT NULL";
		return new Query(getCtx(), I_C_DunningRunLine.Table_Name, whereClause, get_TrxName())
				.setParameters(getC_DunningRunEntry_ID())
				.match();
	}	// hasInvoices

	/**
	 * Get Parent
	 * 
	 * @return Dunning Run
	 */
	private MDunningRun getParent()
	{
		if (m_parent == null)
			m_parent = new MDunningRun(getCtx(), getC_DunningRun_ID(), get_TrxName());
		return m_parent;
	}	// getParent

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

		I_C_DunningLevel level = getC_DunningLevel();
		// Set Amt
		if (isProcessed())
		{
			MDunningRunLine[] theseLines = getLines();
			for (int i = 0; i < theseLines.length; i++)
			{
				theseLines[i].setProcessed(true);
				theseLines[i].saveEx(get_TrxName());
			}
			if (level.isSetCreditStop() || level.isSetPaymentTerm())
			{
				MBPartner thisBPartner = MBPartner.get(getCtx(), getC_BPartner_ID());

				final IBPartnerStats stats =bpartnerStatsDAO.retrieveBPartnerStats(thisBPartner);
				
				if (level.isSetCreditStop())
				{
					// set this particular credit status in the bp stats
					bpartnerStatsDAO.setSOCreditStatus(stats, X_C_BPartner_Stats.SOCREDITSTATUS_CreditStop);
				}
				if (level.isSetPaymentTerm())
				{
					thisBPartner.setC_PaymentTerm_ID(level.getC_PaymentTerm_ID());
				}
				thisBPartner.save();
			}
		}
		return true;
	}	// beforeSave

}	// MDunningRunEntry
