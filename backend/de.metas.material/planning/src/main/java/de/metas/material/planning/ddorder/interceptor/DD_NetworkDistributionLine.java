package de.metas.material.planning.ddorder.interceptor;

import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.springframework.stereotype.Component;

@Interceptor(I_DD_NetworkDistributionLine.class)
@Component
public class DD_NetworkDistributionLine
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(@NonNull final I_DD_NetworkDistributionLine record, @NonNull final ModelChangeType changeType)
	{
		if (changeType.isChange())
		{
			DistributionNetworkRepository.fromRecord(record); // validate
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(@NonNull final I_DD_NetworkDistributionLine record, @NonNull final ModelChangeType changeType)
	{
		if (changeType.isNew())
		{
			DistributionNetworkRepository.fromRecord(record); // validate
		}
	}
}
