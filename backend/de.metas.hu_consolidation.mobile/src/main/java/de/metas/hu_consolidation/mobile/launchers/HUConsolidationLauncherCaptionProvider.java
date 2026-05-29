package de.metas.hu_consolidation.mobile.launchers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.document.location.RenderedAddressProvider;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobReference;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import lombok.Builder;
import lombok.NonNull;

import java.util.OptionalInt;

@Builder
public class HUConsolidationLauncherCaptionProvider
{
	@NonNull private final RenderedAddressProvider renderedAddressProvider;

	@NonNull
	public WorkflowLauncherCaption computeCaption(final @NonNull HUConsolidationJobReference reference)
	{
		final ImmutableList.Builder<String> fieldsInOrder = ImmutableList.builder();
		ImmutableMap.Builder<String, ITranslatableString> fieldValues = ImmutableMap.builder();

		final String address = renderedAddressProvider.getAddress(reference.getBpartnerLocationId());
		fieldsInOrder.add("BPartnerAddress");
		fieldValues.put("BPartnerAddress", TranslatableStrings.anyLanguage(address));

		final OptionalInt countHUs = reference.getCountHUs();
		if (countHUs.isPresent())
		{
			fieldsInOrder.add("CountHUs");
			final int countHUsAsInt = countHUs.getAsInt();
			fieldValues.put("CountHUs", TranslatableStrings.builder()
					.append(countHUsAsInt)
					.append(" ")
					.append(countHUsAsInt == 1 ? "HU" : "HUs")
					.build());
		}

		return WorkflowLauncherCaption.builder()
				.fieldsInOrder(fieldsInOrder.build())
				.fieldValues(fieldValues.build())
				.build();
	}
}
