package de.metas.manufacturing.order.weighting.run.quickinput;

import com.google.common.collect.ImmutableSet;
import de.metas.manufacturing.order.weighting.run.PPOrderWeightingRunCheckId;
import de.metas.manufacturing.order.weighting.run.PPOrderWeightingRunId;
import de.metas.manufacturing.order.weighting.run.PPOrderWeightingRunService;
import de.metas.ui.web.quickinput.IQuickInputProcessor;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.window.datatypes.DocumentId;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.util.Set;

public class PPOrderWeightingCheckQuickInputProcessor implements IQuickInputProcessor
{
	private final PPOrderWeightingRunService weightingRunService = SpringContextHolder.instance.getBean(PPOrderWeightingRunService.class);

	@Override
	public Set<DocumentId> process(final QuickInput quickInput)
	{
		final PPOrderWeightingRunId weightingRunId = quickInput.getRootDocument().getDocumentId().toId(PPOrderWeightingRunId::ofRepoId);
		final BigDecimal weightBD = quickInput.getQuickInputDocumentAs(PPOrderWeightingCheckQuickInput.class).getWeight();
		final PPOrderWeightingRunCheckId weightingRunCheckId = weightingRunService.addRunCheck(weightingRunId, weightBD);

		return ImmutableSet.of(DocumentId.of(weightingRunCheckId));
	}
}
