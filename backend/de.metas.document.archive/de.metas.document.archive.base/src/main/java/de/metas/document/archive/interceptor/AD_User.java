package de.metas.document.archive.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.archive.api.IBPartnerBL;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.record_access.PermissionIssuer;
import de.metas.security.permissions.record_access.RecordAccessConfigService;
import de.metas.security.permissions.record_access.RecordAccessFeature;
import de.metas.security.permissions.record_access.RecordAccessGrantRequest;
import de.metas.security.permissions.record_access.RecordAccessService;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;

/**
 * sync flags
 *
 * @author cg
 */
@Interceptor(I_AD_User.class)
class AD_User
{
	private final IBPartnerBL archiveBPartnerBL = Services.get(IBPartnerBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	final IDocOutboundDAO docOutboundDAO = Services.get(IDocOutboundDAO.class);
	final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	private final RecordAccessConfigService recordAccessConfigService = SpringContextHolder.instance.getBean(RecordAccessConfigService.class);
	private final RecordAccessService recordAccessService = SpringContextHolder.instance.getBean(RecordAccessService.class);

	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_AD_User.COLUMNNAME_IsInvoiceEmailEnabled, I_AD_User.COLUMNNAME_C_BPartner_ID })
	public void updateFlag(final I_AD_User user)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(user.getC_BPartner_ID());
		final I_C_BPartner bpartner = bpartnerId != null ? bpartnerDAO.getByIdInTrx(bpartnerId, I_C_BPartner.class) : null;
		final boolean isInvoiceEmailEnabled = archiveBPartnerBL.isInvoiceEmailEnabled(bpartner, user);

		//
		// retrieve latest log
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

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void afterSave(@NonNull final I_AD_User user)
	{
		if (recordAccessConfigService.isFeatureEnabled(RecordAccessFeature.BPARTNER_HIERARCHY))
		{
			recordAccessService.grantAccess(
					RecordAccessGrantRequest.builder()
							.recordRef(TableRecordReference.of(I_AD_User.Table_Name, user.getAD_User_ID()))
							.principal(Principal.userId(UserId.ofRepoId(user.getCreatedBy())))
							.permission(Access.READ)
							.permission(Access.WRITE)
							.issuer(PermissionIssuer.AUTO_BP_HIERARCHY)
							.requestedBy(Env.getLoggedUserIdIfExists().orElse(UserId.SYSTEM))
							.description("grant permissions to creator")
							.build());
		}
	}
}
