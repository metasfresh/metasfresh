package org.adempiere.bpartner.process;

import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner_CreditLimit;

import de.metas.event.Event;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;

public class BPartnerCreditLimit_RequestApproval extends JavaProcess implements IProcessPrecondition
{

	@Param(parameterName = I_C_BPartner_CreditLimit.COLUMNNAME_ApprovedBy_ID, mandatory = true)
	private int approvedBy_ID;

	public static final Topic TOPIC_CreditLimitRequestApproval = Topic.builder()
			.name("org.adempiere.bpartner.process.CreditLimit")
			.type(Type.REMOTE)
			.build();

	private static final String MSG_Event_RequestApproval = "org.adempiere.bpartner.process.BPartnerCreditLimit_RequestApproval";
	private final int bpartnerWindowId = 123;

	@Override
	protected String doIt()
	{
		final I_C_BPartner_CreditLimit bpCreditLimit = getRecord(I_C_BPartner_CreditLimit.class);

		final Event event = Event.builder()
				.setDetailADMessage(MSG_Event_RequestApproval)
				.addRecipient_User_ID(approvedBy_ID)
				.setRecord(TableRecordReference.of(bpCreditLimit.getC_BPartner()))
				.setSuggestedWindowId(bpartnerWindowId)
				.build();

		Services.get(IEventBusFactory.class).getEventBus(TOPIC_CreditLimitRequestApproval)
				.postEvent(event);

		return "@Success@";
	}

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
}
