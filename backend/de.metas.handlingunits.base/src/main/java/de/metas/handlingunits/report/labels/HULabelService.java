package de.metas.handlingunits.report.labels;

import de.metas.handlingunits.process.api.IMHUProcessDAO;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.i18n.ExplainedOptional;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class HULabelService
{
	private final IMHUProcessDAO huProcessDAO = Services.get(IMHUProcessDAO.class);
	private final HULabelConfigService huLabelConfigService;
	private final HUQRCodesService huQRCodesService;

	public HULabelService(
			@NonNull final HULabelConfigService huLabelConfigService,
			@NonNull final HUQRCodesService huQRCodesService)
	{
		this.huLabelConfigService = huLabelConfigService;
		this.huQRCodesService = huQRCodesService;
	}

	public ExplainedOptional<HULabelConfig> getFirstMatching(final HULabelConfigQuery query)
	{
		return huLabelConfigService.getFirstMatching(query);
	}

	public void print(@NonNull final HULabelPrintRequest request)
	{
		HULabelPrintCommand.builder()
				.huLabelConfigService(huLabelConfigService)
				.huProcessDAO(huProcessDAO)
				.huQRCodesService(huQRCodesService)
				.request(request)
				.build()
				.execute();
	}
}
