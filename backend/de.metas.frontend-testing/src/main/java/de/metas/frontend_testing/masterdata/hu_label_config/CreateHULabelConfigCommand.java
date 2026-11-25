package de.metas.frontend_testing.masterdata.hu_label_config;

import de.metas.bpartner.BPartnerId;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.report.labels.HULabelConfigCreateRequest;
import de.metas.handlingunits.report.labels.HULabelConfigRepository;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;

@Builder
public class CreateHULabelConfigCommand
{
	@NonNull private final HULabelConfigRepository huLabelConfigRepository;
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonHULabelConfigRequest request;

	public void execute()
	{
		huLabelConfigRepository.create(HULabelConfigCreateRequest.builder()
				.orgId(MasterdataContext.ORG_ID)
				.seqNo(SeqNo.ofInt(10))
				.bpartnerId(context.getId(request.getBpartner(), BPartnerId.class))
				.sourceDocType(request.getSourceDocType())
				.huUnitType(request.getHuUnitType())
				.printFormatProcessId(request.getProcess())
				.autoPrint(request.isAutoPrint())
				.build());
	}
}
