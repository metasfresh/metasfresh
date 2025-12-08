package de.metas.workflow.rest_api.activity_features.user_confirmation;

import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityAlwaysAvailableToUser;
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

		@Builder.Default
		@NonNull WFActivityAlwaysAvailableToUser alwaysAvailableToUser = WFActivityAlwaysAvailableToUser.DEFAULT;

		public static UIComponentPropsBuilder builderFrom(final @NonNull WFActivity wfActivity)
		{
			return builder()
					.confirmed(wfActivity.getStatus().isCompleted())
					.alwaysAvailableToUser(wfActivity.getAlwaysAvailableToUser());
		}
	}

	public static UIComponent createUIComponent(@NonNull final UIComponentProps props)
	{
		return UIComponent.builder()
				.type(UIComponentType.CONFIRM_BUTTON)
				.alwaysAvailableToUser(props.getAlwaysAvailableToUser())
				.properties(Params.builder()
						.value("question", props.getQuestion())
						.value("confirmed", props.isConfirmed())
						.build())
				.build();
	}

	public static UIComponent createUIComponent(@NonNull final WFActivity wfActivity)
	{
		return createUIComponent(UIComponentProps.builderFrom(wfActivity).build());
	}

}
