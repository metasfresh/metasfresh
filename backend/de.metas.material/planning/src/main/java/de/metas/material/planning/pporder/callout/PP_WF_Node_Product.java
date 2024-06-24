package de.metas.material.planning.pporder.callout;

import de.metas.material.planning.pporder.IPPRoutingRepository;
import de.metas.material.planning.pporder.PPRoutingActivityId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.eevolution.model.I_PP_WF_Node_Product;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Callout(I_PP_WF_Node_Product.class)
@Component
public class PP_WF_Node_Product
{
	private final IPPRoutingRepository ppRoutingRepository = Services.get(IPPRoutingRepository.class);

	@PostConstruct
	public void postConstruct()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_PP_WF_Node_Product.COLUMNNAME_AD_WF_Node_ID)
	public void onAD_WF_Node_ID(final I_PP_WF_Node_Product record)
	{
		final PPRoutingActivityId activityId = PPRoutingActivityId.ofRepoIdOrNull(record.getAD_Workflow_ID(), record.getAD_WF_Node_ID());
		if (activityId == null)
		{
			return;
		}

		final SeqNo nextSeqNo = ppRoutingRepository.getActivityProductNextSeqNo(activityId);
		record.setSeqNo(nextSeqNo.toInt());
	}
}
