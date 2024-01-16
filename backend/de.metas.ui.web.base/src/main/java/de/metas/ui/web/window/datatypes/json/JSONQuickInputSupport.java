package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.ui.web.window.descriptor.QuickInputSupportDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@Builder
@Jacksonized
public class JSONQuickInputSupport
{
	String openButtonCaption;
	String closeButtonCaption;

	public static JSONQuickInputSupport of(@NonNull final QuickInputSupportDescriptor quickInputSupport, @NonNull final String adLanguage)
	{
		return builder()
				.openButtonCaption(quickInputSupport.getOpenButtonCaption().translate(adLanguage))
				.closeButtonCaption(quickInputSupport.getCloseButtonCaption().translate(adLanguage))
				.build();
	}
}
