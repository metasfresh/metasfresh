/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.process;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MBPartner;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.model.Query;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater.BPartnerStatisticsUpdateRequest;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.i18n.Msg;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Services;

/**
 * Validate Business Partner
 *
 * @author Jorg Janke
 * @version $Id: BPartnerValidate.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 *          FR: [ 2214883 ] Remove SQL code and Replace for Query - red1, teo_sarca
 */
public class BPartnerValidate extends JavaProcess
{
	private BPartnerId p_C_BPartner_ID;
	private BPGroupId p_C_BP_Group_ID;

	/**
	 * Prepare
	 */
	@Override
	protected void prepare()
	{
		p_C_BPartner_ID = BPartnerId.ofRepoIdOrNull(getRecord_ID());
		
		final ProcessInfoParameter[] para = getParametersAsArray();
		for (final ProcessInfoParameter element : para)
		{
			final String name = element.getParameterName();
			if (element.getParameter() == null)
			{
				;
			}
			else if (name.equals("C_BPartner_ID"))
			{
				p_C_BPartner_ID = BPartnerId.ofRepoIdOrNull(element.getParameterAsInt());
			}
			else if (name.equals("C_BP_Group_ID"))
			{
				p_C_BP_Group_ID = BPGroupId.ofRepoIdOrNull(element.getParameterAsInt());
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (p_C_BPartner_ID == null && p_C_BP_Group_ID == null)
		{
			throw new AdempiereException("No Business Partner/Group selected");
		}

		if (p_C_BP_Group_ID == null)
		{
			final I_C_BPartner bp = Services.get(IBPartnerDAO.class).getByIdInTrx(p_C_BPartner_ID);
			checkBP(bp);
		}
		else
		{
			final String whereClause = "C_BP_Group_ID=?";
			final Iterator<MBPartner> it = new Query(getCtx(), MBPartner.Table_Name, whereClause, get_TrxName())
					.setParameters(new Object[] { p_C_BP_Group_ID })
					.setOnlyActiveRecords(true)
					.iterate(null, false); // metas: guaranteed = false because we are just simple querying all bpartners
			while (it.hasNext())
			{
				checkBP(it.next());
			}
		}
		//
		return MSG_OK;
	}	// doIt

	/**
	 * Check BP
	 *
	 * @param bp bp
	 * @throws SQLException
	 */
	private void checkBP(final I_C_BPartner bp) throws SQLException
	{
		final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);

		final BPartnerStats stats = bpartnerStatsDAO.getCreateBPartnerStats(bp);

		addLog(0, null, null, bp.getName() + ":");
		// See also VMerge.postMerge
		checkPayments(bp);
		checkInvoices(bp);

		//
		// task FRESH-152.Update bpartner stats
		Services.get(IBPartnerStatisticsUpdater.class)
				.updateBPartnerStatistics(BPartnerStatisticsUpdateRequest.builder()
						.bpartnerId(bp.getC_BPartner_ID())
						.build());

		//
		// if (bp.getSO_CreditUsed().signum() != 0)
		addLog(0, null, stats.getSoCreditUsed(), Msg.getElement(getCtx(), "SO_CreditUsed"));
		addLog(0, null, stats.getOpenItems(), Msg.getElement(getCtx(), "OpenItems"));
		addLog(0, null, stats.getActualLifeTimeValue(), Msg.getElement(getCtx(), "ActualLifeTimeValue"));
		//
		commitEx();
	}	// checkBP

	/**
	 * Check Payments
	 *
	 * @param bp business partner
	 */
	private void checkPayments(final I_C_BPartner bp)
	{
		// See also VMerge.postMerge
		int changed = 0;
		final MPayment[] payments = MPayment.getOfBPartner(getCtx(), bp.getC_BPartner_ID(), get_TrxName());
		for (final MPayment payment : payments)
		{
			if (payment.testAllocation())
			{
				payment.save();
				changed++;
			}
		}
		if (changed != 0)
		{
			addLog(0, null, new BigDecimal(payments.length),
					Msg.getElement(getCtx(), "C_Payment_ID") + " - #" + changed);
		}
	}	// checkPayments

	/**
	 * Check Invoices
	 *
	 * @param bp business partner
	 */
	private void checkInvoices(final I_C_BPartner bp)
	{
		// See also VMerge.postMerge
		int changed = 0;
		final MInvoice[] invoices = MInvoice.getOfBPartner(getCtx(), bp.getC_BPartner_ID(), get_TrxName());
		for (final MInvoice invoice : invoices)
		{
			if (invoice.testAllocation())
			{
				invoice.save();
				changed++;
			}
		}
		if (changed != 0)
		{
			addLog(0, null, new BigDecimal(invoices.length),
					Msg.getElement(getCtx(), "C_Invoice_ID") + " - #" + changed);
		}
	}	// checkInvoices

}	// BPartnerValidate
