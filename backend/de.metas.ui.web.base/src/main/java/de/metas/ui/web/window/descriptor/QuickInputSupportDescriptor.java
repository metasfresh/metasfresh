package de.metas.ui.web.window.descriptor;

import de.metas.i18n.ITranslatableString;
import de.metas.quickinput.config.QuickInputConfigLayout;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Preliminary quick input (aka batch entry) support informations.
 * Those tells if we shall render a quick input button, how to call it etc.
 * <p>
 * What fields shall be rendered in a quick input and how they shall be layouted is descried in {@link de.metas.ui.web.quickinput.QuickInputDescriptor}
 */
@Value
@Builder
public class QuickInputSupportDescriptor
{
	@NonNull ITranslatableString openButtonCaption;
	@NonNull ITranslatableString closeButtonCaption;
}
