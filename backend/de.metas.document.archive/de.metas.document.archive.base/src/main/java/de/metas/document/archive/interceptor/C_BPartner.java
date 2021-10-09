package de.metas.document.archive.interceptor;

import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.archive.api.IBPartnerBL;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.util.Services;

/**
 * sync flags
 * 
 * @author cg
 * 
 */
@Validator(I_C_BPartner.class)
class C_BPartner
{

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_C_Doc_Outbound_Log.COLUMNNAME_IsInvoiceEmailEnabled)
	public void updateFlag(final I_C_BPartner bpartner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bpartner);

		final org.compiere.model.I_AD_User defaultContact = Services.get(IBPartnerDAO.class).retrieveDefaultContactOrNull(bpartner, I_AD_User.class);
		final I_AD_User user = InterfaceWrapperHelper.create(defaultContact,I_AD_User.class);
		final boolean isInvoiceEmailEnabled = Services.get(IBPartnerBL.class).isInvoiceEmailEnabled(bpartner, user);

		//
		// retrieve latest log
		final I_C_Doc_Outbound_Log docExchange = Services.get(IDocOutboundDAO.class).retrieveLog(new PlainContextAware(ctx), bpartner.getC_BPartner_ID(), Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		
		if (docExchange == null)
		{
			return;
		}
		//
		// update outbound log accordingly which will trigger a validator <code>C_Doc_Outbound_Log</code> which will create the notification
		// update only for invoices
		final int AD_Table_ID = Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name);
		if (AD_Table_ID == docExchange.getAD_Table_ID())
		{
			docExchange.setIsInvoiceEmailEnabled(isInvoiceEmailEnabled);
			InterfaceWrapperHelper.save(docExchange);
		}
	}

}
