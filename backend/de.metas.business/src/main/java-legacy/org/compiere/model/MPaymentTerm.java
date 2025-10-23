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

import de.metas.i18n.Msg;
import de.metas.invoice.InvoiceId;
import de.metas.order.paymentschedule.InvoicePayScheduleService;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import static java.math.BigDecimal.ZERO;

/**
 * Payment Term Model
 *
 * @author Jorg Janke
 * @version $Id: MPaymentTerm.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MPaymentTerm extends X_C_PaymentTerm
{
	@NonNull final InvoicePayScheduleService invoicePayScheduleService = SpringContextHolder.instance.getBean(InvoicePayScheduleService.class);
	/**
	 *
	 */
	private static final long serialVersionUID = 2494915482340569386L;

	/**
	 * Standard Constructor
	 *
	 * @param ctx              context
	 * @param C_PaymentTerm_ID id
	 * @param trxName          transaction
	 */
	public MPaymentTerm(final Properties ctx, final int C_PaymentTerm_ID, final String trxName)
	{
		super(ctx, C_PaymentTerm_ID, trxName);
		if (C_PaymentTerm_ID == 0)
		{
			setAfterDelivery(false);
			setNetDays(0);
			setDiscount(ZERO);
			setDiscount2(ZERO);
			setDiscountDays(0);
			setDiscountDays2(0);
			setGraceDays(0);
			setIsDueFixed(false);
			setIsValid(false);
		}
	}    //	MPaymentTerm

	/**
	 * Load Constructor
	 *
	 * @param ctx     context
	 * @param rs      result set
	 * @param trxName transaction
	 */
	public MPaymentTerm(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}    //	MPaymentTerm

	/**
	 * Payment Schedule children
	 */
	private MPaySchedule[] m_schedule;

	/**
	 * Get Payment Schedule
	 *
	 * @param requery if true re-query
	 * @return array of schedule
	 */
	public MPaySchedule[] getSchedule(final boolean requery)
	{
		if (m_schedule != null && !requery)
			return m_schedule;
		final String sql = "SELECT * FROM C_PaySchedule WHERE C_PaymentTerm_ID=? AND IsActive='Y' ORDER BY NetDays";
		final ArrayList<MPaySchedule> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getC_PaymentTerm_ID());
			final ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				final MPaySchedule ps = new MPaySchedule(getCtx(), rs, get_TrxName());
				ps.setParent(this);
				list.add(ps);
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (final Exception e)
		{
			log.error("getSchedule", e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (final Exception e)
		{
			pstmt = null;
		}

		m_schedule = new MPaySchedule[list.size()];
		list.toArray(m_schedule);
		return m_schedule;
	}    //	getSchedule

	/**
	 * Validate Payment Term & Schedule
	 *
	 * @return Validation Message @OK@ or error
	 */
	public String validate()
	{
		getSchedule(true);
		if (m_schedule.length == 0)
		{
			setIsValid(true);
			return "@OK@";
		}
		if (m_schedule.length == 1)
		{
			setIsValid(false);
			if (m_schedule[0].isValid())
			{
				m_schedule[0].setIsValid(false);
				m_schedule[0].save();
			}
			return "@Invalid@ @Count@ # = 1 (@C_PaySchedule_ID@)";
		}

		//	Add up
		BigDecimal total = ZERO;
		for (final MPaySchedule mPaySchedule : m_schedule)
		{
			final BigDecimal percent = mPaySchedule.getPercentage();
			if (percent != null)
				total = total.add(percent);
		}
		final boolean valid = total.compareTo(Env.ONEHUNDRED) == 0;
		setIsValid(valid);
		for (final MPaySchedule mPaySchedule : m_schedule)
		{
			if (mPaySchedule.isValid() != valid)
			{
				mPaySchedule.setIsValid(valid);
				mPaySchedule.save();
			}
		}
		String msg = "@OK@";
		if (!valid)
			msg = "@Total@ = " + total + " - @Difference@ = " + Env.ONEHUNDRED.subtract(total);
		return Msg.parseTranslation(getCtx(), msg);
	}    //	validate

	/*************************************************************************
	 * 	Apply Payment Term to Invoice -
	 *    @param C_Invoice_ID invoice
	 *    @return true if payment schedule is valid
	 */
	public boolean apply(final int C_Invoice_ID)
	{
		MInvoice invoice = new MInvoice(getCtx(), C_Invoice_ID, get_TrxName());
		if (invoice == null || invoice.get_ID() == 0)
		{
			log.error("apply - Not valid C_Invoice_ID=" + C_Invoice_ID);
			return false;
		}
		return apply(invoice);
	}    //	apply

	/**
	 * Apply Payment Term to Invoice
	 *
	 * @param invoice invoice
	 * @return true if payment schedule is valid
	 */
	public boolean apply(@Nullable final I_C_Invoice invoice)
	{
		if (invoice == null || invoice.getC_Invoice_ID() == 0)
		{
			log.error("No valid invoice - " + invoice);
			return false;
		}

		if (!isValid())
			return applyNoSchedule(invoice);
		//
		getSchedule(true);
		if (m_schedule.length <= 1)
			return applyNoSchedule(invoice);
		else    //	only if valid
			return invoicePayScheduleService.validate(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
	}    //	apply

	/**
	 * Apply Payment Term without schedule to Invoice
	 *
	 * @param invoice invoice
	 * @return false as no payment schedule
	 */
	private boolean applyNoSchedule(@NonNull final I_C_Invoice invoice)
	{
		deleteInvoicePaySchedule(invoice.getC_Invoice_ID());
		//	updateInvoice
		if (invoice.getC_PaymentTerm_ID() != getC_PaymentTerm_ID())
			invoice.setC_PaymentTerm_ID(getC_PaymentTerm_ID());
		if (invoice.isPayScheduleValid())
			invoice.setIsPayScheduleValid(false);
		return false;
	}    //	applyNoSchedule

	/**
	 * Delete existing Invoice Payment Schedule
	 *
	 * @param C_Invoice_ID id
	 */
	private void deleteInvoicePaySchedule(final int C_Invoice_ID)
	{
		final String sql = "DELETE FROM C_InvoicePaySchedule WHERE C_Invoice_ID=" + C_Invoice_ID;
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
		log.debug("C_Invoice_ID=" + C_Invoice_ID + " - #" + no);
	}    //	deleteInvoicePaySchedule

	/**************************************************************************
	 * 	String Representation
	 *    @return info
	 */
	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer("MPaymentTerm[");
		sb.append(get_ID()).append("-").append(getName())
				.append(",Valid=").append(isValid())
				.append("]");
		return sb.toString();
	}    //	toString

	/**
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (isDueFixed())
		{
			int dd = getFixMonthDay();
			if (dd < 1 || dd > 31)
			{
				throw new AdempiereException("@Invalid@ @FixMonthDay@");
			}
			dd = getFixMonthCutoff();
			if (dd < 1 || dd > 31)
			{
				throw new AdempiereException("@Invalid@ @FixMonthCutoff@");
			}
		}

		if (!newRecord || !isValid())
			validate();
		return true;
	}    //	beforeSave

}    //	MPaymentTerm
