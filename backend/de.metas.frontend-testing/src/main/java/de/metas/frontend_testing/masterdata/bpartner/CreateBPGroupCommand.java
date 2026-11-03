package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_Group;

/**
 * Creates one per-run {@link I_C_BP_Group} (name/value uniquified, so runs on a shared DB never collide).
 */
@Builder
public class CreateBPGroupCommand
{
	@NonNull private final IBPGroupDAO bpGroupDAO = Services.get(IBPGroupDAO.class);

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonBPGroupRequest request;
	@NonNull private final Identifier identifier;

	public JsonBPGroupResponse execute()
	{
		final String requestName = StringUtils.trimBlankToNull(request.getName());
		final String value = requestName != null
				? Identifier.ofString(requestName).toUniqueString()
				: identifier.toUniqueString();

		final I_C_BP_Group record = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		record.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		record.setIsActive(true);
		record.setValue(value);
		record.setName(value);
		bpGroupDAO.save(record);

		final BPGroupId bpGroupId = BPGroupId.ofRepoId(record.getC_BP_Group_ID());
		context.putIdentifier(identifier, bpGroupId);

		return JsonBPGroupResponse.builder().id(bpGroupId).build();
	}
}
