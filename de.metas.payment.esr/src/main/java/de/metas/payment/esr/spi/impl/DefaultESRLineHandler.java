/**
 * 
 */
package de.metas.payment.esr.spi.impl;

import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;

import de.metas.i18n.IMsgBL;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.dataimporter.ESRDataLoaderUtil;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.spi.IESRLineHandler;
import lombok.NonNull;

/**
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class DefaultESRLineHandler implements IESRLineHandler
{

	final private ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Override
	public boolean matchBPartnerOfInvoice(final I_C_Invoice invoice, final I_ESR_ImportLine esrLine)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);

		if (sysConfigBL.getBooleanValue(ESRConstants.SYSCONFIG_MATCH_ORG, true))
		{
			final I_C_BPartner invoicePartner = invoice.getC_BPartner();
			if (invoicePartner.getAD_Org_ID() > 0  // task 09852: a partner that has no org at all does not mean an inconsistency and is therefore OK
					&& invoicePartner.getAD_Org_ID() != esrLine.getAD_Org_ID())
			{
				ESRDataLoaderUtil.addMatchErrorMsg(esrLine,
						Services.get(IMsgBL.class).getMsg(ctx, ESRDataLoaderUtil.ESR_UNFIT_BPARTNER_ORG));
				return false;
			}
		}
		else
		{
			// check the org: should not match with invoices from other orgs
			if (invoice.getAD_Org_ID() != esrLine.getAD_Org_ID())
			{
				ESRDataLoaderUtil.addMatchErrorMsg(esrLine,
						Services.get(IMsgBL.class).getMsg(ctx, ESRDataLoaderUtil.ESR_UNFIT_INVOICE_ORG));
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean matchBPartner(
			@NonNull final I_C_BPartner bPartner, 
			@NonNull final I_ESR_ImportLine esrLine)
	{
		if (sysConfigBL.getBooleanValue(ESRConstants.SYSCONFIG_MATCH_ORG, true))
		{
			if (bPartner.getAD_Org_ID() > 0 // task 09852: a partner that has no org at all does not mean an inconsistency and is therefore OK
					&& bPartner.getAD_Org_ID() != esrLine.getAD_Org_ID())
			{
				final Properties ctx = InterfaceWrapperHelper.getCtx(esrLine);
				final IMsgBL msgBL = Services.get(IMsgBL.class);

				ESRDataLoaderUtil.addMatchErrorMsg(esrLine,
						msgBL.getMsg(ctx, ESRDataLoaderUtil.ESR_UNFIT_BPARTNER_ORG));

				return false;
			}
		}

		return true;
	}

}
