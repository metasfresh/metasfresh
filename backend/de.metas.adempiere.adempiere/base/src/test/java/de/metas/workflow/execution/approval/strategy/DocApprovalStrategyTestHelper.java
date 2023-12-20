package de.metas.workflow.execution.approval.strategy;

import de.metas.job.JobId;
import de.metas.money.Money;
import de.metas.security.RoleId;
import de.metas.security.TableAccessLevel;
import de.metas.security.permissions.UserPreferenceLevelConstraint;
import de.metas.user.UserId;
import de.metas.util.OptionalBoolean;
import de.metas.util.StringUtils;
import de.metas.util.lang.SeqNoProvider;
import de.metas.workflow.execution.approval.strategy.check_superior_strategy.CheckSupervisorStrategyType;
import de.metas.workflow.execution.approval.strategy.type_handlers.DocApprovalStrategyType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_User_Roles;
import org.compiere.model.I_C_Doc_Approval_Strategy;
import org.compiere.model.I_C_Doc_Approval_Strategy_Line;
import org.compiere.model.I_C_Job;

import javax.annotation.Nullable;

@UtilityClass
public class DocApprovalStrategyTestHelper
{
	@Value
	@Builder
	public static class StrategyLineCreateRequest
	{
		@NonNull DocApprovalStrategyType type;
		@Nullable JobId jobId;
		@NonNull @Builder.Default CheckSupervisorStrategyType supervisorCheckStrategy = CheckSupervisorStrategyType.DoNotCheck;
		@NonNull @Builder.Default OptionalBoolean isProjectManagerSet = OptionalBoolean.UNKNOWN;
		@Nullable Money minimumAmountThatRequiresApproval;
	}

	public DocApprovalStrategyId createApprovalStrategy(DocApprovalStrategyLine.DocApprovalStrategyLineBuilder... lines)
	{
		I_C_Doc_Approval_Strategy headerRecord = InterfaceWrapperHelper.newInstance(I_C_Doc_Approval_Strategy.class);
		headerRecord.setName("test");
		InterfaceWrapperHelper.save(headerRecord);
		final DocApprovalStrategyId docApprovalStrategyId = DocApprovalStrategyId.ofRepoId(headerRecord.getC_Doc_Approval_Strategy_ID());

		final SeqNoProvider nextSeqNo = SeqNoProvider.ofInt(10);
		for (final DocApprovalStrategyLine.DocApprovalStrategyLineBuilder line : lines)
		{
			line.seqNo(nextSeqNo.getAndIncrement());
			createApprovalStrategyLine(line.build(), docApprovalStrategyId);
		}

		return docApprovalStrategyId;
	}

	private void createApprovalStrategyLine(DocApprovalStrategyLine line, DocApprovalStrategyId docApprovalStrategyId)
	{
		final I_C_Doc_Approval_Strategy_Line record = InterfaceWrapperHelper.newInstance(I_C_Doc_Approval_Strategy_Line.class);
		record.setC_Doc_Approval_Strategy_ID(docApprovalStrategyId.getRepoId());
		record.setSeqNo(line.getSeqNo().toInt());
		record.setType(line.getType().getCode());
		record.setC_Job_ID(JobId.toRepoId(line.getJobId()));
		record.setSupervisorCheckStrategy(line.getCheckSupervisorStrategyType().getCode());
		record.setIsProjectManagerSet(StringUtils.ofBoolean(line.getIsProjectManagerSet().toBooleanOrNull()));

		final Money minimumAmountThatRequiresApproval = line.getMinimumAmountThatRequiresApproval();
		if (minimumAmountThatRequiresApproval != null)
		{
			record.setMinimumAmt(minimumAmountThatRequiresApproval.toBigDecimal());
			record.setC_Currency_ID(minimumAmountThatRequiresApproval.getCurrencyId().getRepoId());
		}
		else
		{
			record.setMinimumAmt(null);
			record.setC_Currency_ID(-1);
		}

		InterfaceWrapperHelper.save(record);
	}

	public JobId createJob(final String name)
	{
		I_C_Job record = InterfaceWrapperHelper.newInstance(I_C_Job.class);
		record.setName(name);
		InterfaceWrapperHelper.save(record);

		return JobId.ofRepoId(record.getC_Job_ID());
	}

	@Builder(builderClassName = "$UserBuilder", builderMethodName = "user")
	public UserId createUser(
			@Nullable String name,
			@Nullable JobId jobId,
			@Nullable UserId supervisorId,
			@Nullable RoleId roleId)
	{
		final I_AD_User user = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		user.setName(name);
		user.setC_Job_ID(JobId.toRepoId(jobId));
		user.setSupervisor_ID(UserId.toRepoId(supervisorId));
		InterfaceWrapperHelper.save(user);
		final UserId userId = UserId.ofRepoId(user.getAD_User_ID());

		if (roleId != null)
		{
			final I_AD_User_Roles userRole = InterfaceWrapperHelper.newInstance(I_AD_User_Roles.class);
			userRole.setAD_User_ID(userId.getRepoId());
			userRole.setAD_Role_ID(roleId.getRepoId());
			InterfaceWrapperHelper.save(userRole);
		}

		return userId;
	}

	@Builder(builderClassName = "$RoleBuilder", builderMethodName = "role")
	public RoleId createRole(@Nullable String name, @Nullable Money approvalAmt)
	{
		final I_AD_Role role = InterfaceWrapperHelper.newInstance(I_AD_Role.class);
		role.setName(name);
		role.setUserLevel(TableAccessLevel.ClientPlusOrganization.getUserLevelString());
		role.setPreferenceType(UserPreferenceLevelConstraint.USER.getPreferenceType());
		role.setIsAccessAllOrgs(true);
		if (approvalAmt != null)
		{
			role.setAmtApproval(approvalAmt.toBigDecimal());
			role.setC_Currency_ID(approvalAmt.getCurrencyId().getRepoId());
		}
		InterfaceWrapperHelper.save(role);
		return RoleId.ofRepoId(role.getAD_Role_ID());
	}

	public static void createClient(final ClientId clientId)
	{
		final I_AD_Client client = InterfaceWrapperHelper.newInstance(I_AD_Client.class);
		InterfaceWrapperHelper.setValue(client, "AD_Client_ID", clientId.getRepoId());
		InterfaceWrapperHelper.save(client);

		final I_AD_ClientInfo clientInfo = InterfaceWrapperHelper.newInstance(I_AD_ClientInfo.class);
		InterfaceWrapperHelper.setValue(clientInfo, I_AD_ClientInfo.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
		InterfaceWrapperHelper.save(clientInfo);
	}
}
