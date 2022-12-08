package de.metas.handlingunits.report.labels;

import de.metas.i18n.ExplainedOptional;
import org.springframework.stereotype.Service;

@Service
public class HULabelService
{
	private final HULabelConfigFromSysConfigProvider sysconfigLabelConfigProvider = new HULabelConfigFromSysConfigProvider();
	private final HULabelConfigRepository huLabelConfigRepository;

	public HULabelService(
			final HULabelConfigRepository huLabelConfigRepository)
	{
		this.huLabelConfigRepository = huLabelConfigRepository;
	}

	public ExplainedOptional<HULabelConfig> getFirstMatching(final HULabelConfigQuery query)
	{
		final HULabelConfig labelConfig = huLabelConfigRepository.getFirstMatching(query).orElse(null);
		if (labelConfig != null)
		{
			return ExplainedOptional.of(labelConfig);
		}

		// Fallback to legacy sysconfig based labels
		return sysconfigLabelConfigProvider.getFirstMatching(query);
	}
}
