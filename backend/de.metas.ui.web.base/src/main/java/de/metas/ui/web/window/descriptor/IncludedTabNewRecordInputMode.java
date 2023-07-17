package de.metas.ui.web.window.descriptor;

import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_Tab;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum IncludedTabNewRecordInputMode implements ReferenceListAwareEnum
{
	ALL_AVAILABLE_METHODS(X_AD_Tab.INCLUDEDTABNEWRECORDINPUTMODE_ALL_AVAILABLE_METHODS),
	QUICK_INPUT_ONLY(X_AD_Tab.INCLUDEDTABNEWRECORDINPUTMODE_QuickInputOnly);

	@Getter
	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<IncludedTabNewRecordInputMode> index = ReferenceListAwareEnums.index(values());

	public static IncludedTabNewRecordInputMode ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static IncludedTabNewRecordInputMode ofNullableCodeOrAllAvailable(@Nullable final String code)
	{
		return code != null && !Check.isBlank(code)
				? ofCode(code)
				: ALL_AVAILABLE_METHODS;
	}

	public boolean isQuickInputRequired()
	{
		return QUICK_INPUT_ONLY == this;
	}

	public IncludedTabNewRecordInputMode orCompatibleIfAllowQuickInputIs(final boolean hasQuickInputSupport)
	{
		return isQuickInputRequired() && !hasQuickInputSupport
				? ALL_AVAILABLE_METHODS
				: this;
	}
}
