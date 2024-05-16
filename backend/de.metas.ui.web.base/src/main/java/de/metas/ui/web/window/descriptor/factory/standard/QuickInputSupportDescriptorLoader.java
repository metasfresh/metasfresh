package de.metas.ui.web.window.descriptor.factory.standard;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.window.descriptor.QuickInputSupportDescriptor;
import lombok.NonNull;
import org.compiere.model.GridTabVO;

import javax.annotation.Nullable;

class QuickInputSupportDescriptorLoader
{
	private static final AdMessageKey MSG_QuickInput_OpenButton_Caption = AdMessageKey.of("webui.window.batchEntry.caption");
	private static final AdMessageKey MSG_QuickInput_CloseButton_Caption = AdMessageKey.of("webui.window.batchEntryClose.caption");

	@Nullable
	public static QuickInputSupportDescriptor extractFrom(@NonNull final GridTabVO gridTabVO)
	{
		if (!gridTabVO.isAllowQuickInput())
		{
			return null;
		}

		ITranslatableString openButtonCaption = gridTabVO.getQuickInputOpenButtonCaption();
		if (TranslatableStrings.isEmpty(openButtonCaption))
		{
			openButtonCaption = TranslatableStrings.adMessage(MSG_QuickInput_OpenButton_Caption);
		}

		ITranslatableString closeButtonCaption = gridTabVO.getQuickInputCloseButtonCaption();
		if (TranslatableStrings.isEmpty(closeButtonCaption))
		{
			closeButtonCaption = TranslatableStrings.adMessage(MSG_QuickInput_CloseButton_Caption);
		}

		return QuickInputSupportDescriptor.builder()
				.openButtonCaption(openButtonCaption)
				.closeButtonCaption(closeButtonCaption)
				.build();
	}

}
