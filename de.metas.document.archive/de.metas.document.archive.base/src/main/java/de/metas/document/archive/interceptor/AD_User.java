package de.metas.document.archive.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;

import de.metas.document.archive.api.IBPartnerBL;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_AD_User;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.util.Services;

/**
 * sync flags
 *
 * @author cg
 *
 */
@Interceptor(I_AD_User.class)
class AD_User
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_AD_User.COLUMNNAME_IsInvoiceEmailEnabled, I_AD_User.COLUMNNAME_C_BPartner_ID })
	public void updateFlag(final I_AD_User user)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(user.getC_BPartner(), I_C_BPartner.class);
		final boolean isInvoiceEmailEnabled = Services.get(IBPartnerBL.class).isInvoiceEmailEnabled(bpartner, user);

		//
		// retrieve latest log
		final IDocOutboundDAO docOutboundDAO = Services.get(IDocOutboundDAO.class);
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final int AD_Table_ID = adTableDAO.retrieveTableId(I_C_Invoice.Table_Name);

		final I_C_Doc_Outbound_Log docOutboundLogRecord = docOutboundDAO.retrieveLog(
				PlainContextAware.newOutOfTrx(),
				user.getC_BPartner_ID(),
				AD_Table_ID);
		if (docOutboundLogRecord == null)
		{
			return;
		}
		//
		// update outbound log accordingly which will trigger a validator <code>C_Doc_Outbound_Log</code> which will create the notification
		// update only for invoices
		docOutboundLogRecord.setIsInvoiceEmailEnabled(isInvoiceEmailEnabled);
		InterfaceWrapperHelper.save(docOutboundLogRecord);
	}
}
