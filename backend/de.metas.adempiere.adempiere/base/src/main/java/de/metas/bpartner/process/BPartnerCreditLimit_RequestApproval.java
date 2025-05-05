package de.metas.bpartner.process;

import de.metas.event.Topic;
import de.metas.i18n.AdMessageKey;
import de.metas.interfaces.I_C_BPartner;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner_CreditLimit;

public class BPartnerCreditLimit_RequestApproval extends JavaProcess implements IProcessPrecondition
{
	private static final String PARAM_ApprovedBy_ID = I_C_BPartner_CreditLimit.COLUMNNAME_ApprovedBy_ID;
	@Param(parameterName = PARAM_ApprovedBy_ID, mandatory = true)
	private int approvedByUserRepoId = -1;

	public static final Topic USER_NOTIFICATIONS_TOPIC = Topic.distributed("de.metas.bpartner.UserNotifications.CreditLimit");

	private static final AdMessageKey MSG_Event_RequestApproval = AdMessageKey.of("org.adempiere.bpartner.process.BPartnerCreditLimit_RequestApproval");

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!I_C_BPartner_CreditLimit.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not running on C_BPartner_CreditLimit table");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final UserId approvedByUserId = UserId.ofRepoIdOrNull(approvedByUserRepoId);
		if (approvedByUserId == null)
		{
			throw new FillMandatoryException(PARAM_ApprovedBy_ID);
		}

		final I_C_BPartner_CreditLimit bpCreditLimit = getRecord(I_C_BPartner_CreditLimit.class);
		final TableRecordReference bpartnerRef = TableRecordReference.of(I_C_BPartner.Table_Name, bpCreditLimit.getC_BPartner_ID());

		Services.get(INotificationBL.class)
				.send(UserNotificationRequest.builder()
						.topic(USER_NOTIFICATIONS_TOPIC)
						.recipientUserId(approvedByUserId)
						.contentADMessage(MSG_Event_RequestApproval)
						.contentADMessageParam(bpartnerRef)
						.targetAction(TargetRecordAction.of(bpartnerRef))
						.build());

		return MSG_OK;
	}
}
