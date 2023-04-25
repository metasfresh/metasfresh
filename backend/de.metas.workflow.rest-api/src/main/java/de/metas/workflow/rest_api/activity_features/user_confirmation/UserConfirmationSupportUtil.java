package de.metas.workflow.rest_api.activity_features.user_confirmation;

import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.adempiere.util.api.Params;

import javax.annotation.Nullable;

@UtilityClass
public class UserConfirmationSupportUtil
{
	@Value
	@Builder
	public static class UIComponentProps
	{
		@Nullable String question;
		boolean confirmed;
	}

	public static UIComponent createUIComponent(@NonNull final UIComponentProps props)
	{
		return UIComponent.builder()
				.type(UIComponentType.CONFIRM_BUTTON)
				.properties(Params.builder()
						.value("question", props.getQuestion())
						.value("confirmed", props.isConfirmed())
						.build())
				.build();
	}

}
