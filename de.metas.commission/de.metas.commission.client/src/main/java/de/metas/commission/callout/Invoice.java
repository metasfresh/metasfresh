/******************************************************************************
 * Copyright (C) 2009 Metas                                                   *
 * Copyright (C) 2009 Carlos Ruiz                                             *
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
package de.metas.commission.callout;

/*
 * #%L
 * de.metas.commission.client
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

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Constants;
import org.adempiere.util.Services;
import org.compiere.grid.ICreateFrom;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_DocType;
import org.compiere.util.Ini;
import org.compiere.util.Msg;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.commission.util.CommissionConstants;
import de.metas.document.IDocumentPA;

/**
 * Shipper Transportation Callout
 * 
 * @author Carlos Ruiz
 */
public class Invoice extends CalloutEngine
{

	private static final String MSG_INVOICECORR_CREATEFROM_WRONG_DOCTYPE_1P = "InvoiceCorr_CreateFrom_WrongDocType";

	/**
	 * Invoke the form VCreateFromPackage
	 * 
	 * @param ctx
	 *            context
	 * @param WindowNo
	 *            window no
	 * @param mTab
	 *            tab
	 * @param mField
	 *            field
	 * @param value
	 *            value
	 * @return null or error message
	 */
	public String createFrom(
			final Properties ctx,
			final int WindowNo,
			final GridTab mTab,
			final GridField mField,
			final Object value)
	{

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(mTab, I_C_Invoice.class);
		if (invoice.getC_Invoice_ID() <= 0)
		{
			return "";
		}
		final I_C_DocType dt = invoice.getC_DocTypeTarget();

		if (!Constants.DOCBASETYPE_AEInvoice.equals(dt.getDocBaseType())
				|| !CommissionConstants.COMMISSON_INVOICE_DOCSUBTYPE_CORRECTION.equals(dt.getDocSubType()))
		{
			// nothing to do
			final IDocumentPA docPA = Services.get(IDocumentPA.class);
			final I_C_DocType dtCorr = docPA.retrieve(ctx, invoice.getAD_Org_ID(), Constants.DOCBASETYPE_AEInvoice, CommissionConstants.COMMISSON_INVOICE_DOCSUBTYPE_CORRECTION, true, null);
			if (dtCorr != null)
			{
				final String msg = Msg.getMsg(ctx, MSG_INVOICECORR_CREATEFROM_WRONG_DOCTYPE_1P, new Object[] { dtCorr.getName() });
				return msg;
			}
			else
			{
				throw new AdempiereException("Missing C_DocType with DocBaseType='"
						+ Constants.DOCBASETYPE_AEInvoice + "' and DocSubType='"
						+ CommissionConstants.COMMISSON_INVOICE_DOCSUBTYPE_CORRECTION + "'");
			}
		}

		final String swingclassname = "de.metas.commission.form.VCreateCorrections";
		final String zkclassname = "not.yet.implemented";
		final String classname;
		if (Ini.isClient())
		{
			classname = swingclassname;
		}
		else
		{
			classname = zkclassname;
			return "";
		}

		ICreateFrom cf = null;
		Class cl;
		try
		{
			if (Ini.isClient())
			{
				cl = Class.forName(classname);
			}
			else
			{
				cl = Thread.currentThread().getContextClassLoader().loadClass(classname);
			}
		}
		catch (final ClassNotFoundException e)
		{
			log.error(e.getLocalizedMessage(), e);
			return e.getLocalizedMessage();
		}
		if (cl != null)
		{
			try
			{
				java.lang.reflect.Constructor<? extends ICreateFrom> ctor = cl.getConstructor(I_C_Invoice.class, int.class);
				cf = ctor.newInstance(invoice, WindowNo);
			}
			catch (Throwable e)
			{
				log.error(e.getLocalizedMessage(), e);
				return e.getLocalizedMessage();
			}
		}

		if (cf != null)
		{
			if (cf.isInitOK())
			{
				cf.showWindow();
				cf.closeWindow();
				mTab.dataRefresh();
			}
			else
				cf.closeWindow();
		}

		return "";
	} // createShippingPackages

} // CalloutShipperTransportation
