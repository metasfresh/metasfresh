package de.metas.document.archive.model.validator;

import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_AD_User;
import de.metas.document.archive.api.IBPartnerBL;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;

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

		final I_AD_User defaultContact = Services.get(IBPartnerDAO.class).retrieveDefaultContactOrNull(bpartner, I_AD_User.class);
		final de.metas.document.archive.model.I_AD_User user = InterfaceWrapperHelper.create(defaultContact, de.metas.document.archive.model.I_AD_User.class);
		final boolean isInvoiceEmailEnabled  = Services.get(IBPartnerBL.class).isInvoiceEmailEnabled(bpartner, user);

		//
		//retrieve latest log
		final I_C_Doc_Outbound_Log docExchange = Services.get(IDocOutboundDAO.class).retrieveLog(new PlainContextAware(ctx), bpartner.getC_BPartner_ID(), Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		//
		// update outbound log accordingly which will trigger a validator <code>C_Doc_Outbound_Log</code> which will create the notification
		docExchange.setIsInvoiceEmailEnabled(isInvoiceEmailEnabled);
		InterfaceWrapperHelper.save(docExchange);
	}

}
