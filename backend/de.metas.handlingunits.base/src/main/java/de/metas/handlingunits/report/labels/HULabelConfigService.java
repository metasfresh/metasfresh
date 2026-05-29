package de.metas.handlingunits.report.labels;

import de.metas.i18n.ExplainedOptional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HULabelConfigService
{
	@NonNull private final HULabelConfigFromSysConfigProvider sysconfigLabelConfigProvider = new HULabelConfigFromSysConfigProvider();
	@NonNull private final HULabelConfigRepository huLabelConfigRepository;

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
