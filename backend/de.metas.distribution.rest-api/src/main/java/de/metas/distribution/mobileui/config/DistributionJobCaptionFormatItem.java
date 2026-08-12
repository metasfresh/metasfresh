package de.metas.distribution.mobileui.config;

import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DistributionJobCaptionFormatItem
{
	@NonNull DistributionJobCaptionField field;

	public ITranslatableString getCaption() {return field.getCaption();}
}
	