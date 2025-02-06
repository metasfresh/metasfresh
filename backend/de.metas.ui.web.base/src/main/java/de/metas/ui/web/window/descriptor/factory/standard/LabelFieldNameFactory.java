package de.metas.ui.web.window.descriptor.factory.standard;

import de.metas.common.util.WindowConstants;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdUIElementId;
import org.adempiere.exceptions.AdempiereException;

import java.util.HashMap;
import java.util.HashSet;

class LabelFieldNameFactory
{
	private final HashMap<AdUIElementId, String> fieldNamesById = new HashMap<>();
	private final HashSet<String> fieldNames = new HashSet<>();

	public String getFieldName(@NonNull final AdUIElementId uiElementId)
	{
		final String fieldName = fieldNamesById.get(uiElementId);
		if (fieldName == null)
		{
			throw new AdempiereException("No label field name registered for " + uiElementId);
		}
		return fieldName;
	}

	public String createFieldName(@NonNull final AdUIElementId uiElementId, @NonNull final String labelsTableName)
	{
		String existingFieldName = fieldNamesById.get(uiElementId);
		if (existingFieldName != null)
		{
			return existingFieldName;
		}

		for (int i = 1; i <= 100; i++)
		{
			String fieldName;
			if (i == 1)
			{
				fieldName = WindowConstants.FIELDNAME_Labels_Prefix + labelsTableName;
			}
			else
			{
				fieldName = WindowConstants.FIELDNAME_Labels_Prefix + labelsTableName + "_" + i;
			}

			if (!fieldNames.contains(fieldName))
			{
				registerFieldName(uiElementId, fieldName);
				return fieldName;
			}

		}

		String fieldName = WindowConstants.FIELDNAME_Labels_Prefix + uiElementId.getRepoId();
		registerFieldName(uiElementId, fieldName);
		return fieldName;
	}

	private void registerFieldName(final @NonNull AdUIElementId uiElementId, final String fieldName)
	{
		fieldNamesById.put(uiElementId, fieldName);
		fieldNames.add(fieldName);
	}
}
