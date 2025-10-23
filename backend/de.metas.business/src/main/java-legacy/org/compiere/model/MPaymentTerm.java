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

import de.metas.invoice.InvoiceId;
import de.metas.order.paymentschedule.InvoicePayScheduleService;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.sql.ResultSet;
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
	@NonNull final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
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

	/*************************************************************************
	 * 	Apply Payment Term to Invoice -
	 *    @param C_Invoice_ID invoice
	 *    @return true if payment schedule is valid
	 */
	public boolean apply(final int C_Invoice_ID)
	{
		final MInvoice invoice = new MInvoice(getCtx(), C_Invoice_ID, get_TrxName());
		if ( invoice.get_ID() <= 0)
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

		if (!isValid() && !isComplex())
			return applyNoSchedule(invoice);
		//

		if (!isComplex() && !paymentTermRepository.hasPaySchedule(PaymentTermId.ofRepoId(getC_PaymentTerm_ID())))
			return applyNoSchedule(invoice);
		else    //	only if valid
			return invoicePayScheduleService.validate(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()), invoice.getGrandTotal());
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

}    //	MPaymentTerm
