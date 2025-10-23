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
package org.compiere.process;

import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

/**
 * Validate Payment Term and Schedule
 *
 * @author Jorg Janke
 * @version $Id: PaymentTermValidate.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class PaymentTermValidate extends JavaProcess
{
	@NonNull final PaymentTermService paymentTermService = SpringContextHolder.instance.getBean(PaymentTermService.class);

	/**
	 * Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParametersAsArray();
		for (final ProcessInfoParameter processInfoParameter : para)
		{
			final String name = processInfoParameter.getParameterName();
			if (processInfoParameter.getParameter() != null)
			{
				log.error("Unknown Parameter: " + name);
			}

		}
	}    //	prepare

	/**
	 * Perform process.
	 *
	 * @return Message
	 * @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		log.info("C_PaymentTerm_ID=" + getRecord_ID());

		final PaymentTermId paymentTermId = PaymentTermId.ofRepoId(getRecord_ID());
		final boolean isValid = paymentTermService.validate(paymentTermId);
		paymentTermService.setPaymentTermIsValidAndSave(paymentTermId, isValid);
		//
		return isValid ? "@OK@" : "@Invalid@ @C_PaymentTerm_ID@=" + paymentTermId;
	}    //	doIt

}    //	PaymentTermValidate
