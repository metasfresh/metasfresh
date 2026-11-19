package de.metas.frontend_testing.masterdata.bpartner;

import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;

@Builder
public class UpdateBPartnerCommand
{
	@NonNull private final JsonUpdateBPartnerRequest request;

	public void execute()
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.load(request.getBpartnerId(), I_C_BPartner.class);
		bpartner.setGRAIRequired(request.getGraiRequired().getCode());
		InterfaceWrapperHelper.saveRecord(bpartner);
	}
}
