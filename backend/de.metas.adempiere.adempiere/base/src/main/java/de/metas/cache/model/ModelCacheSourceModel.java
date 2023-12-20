package de.metas.cache.model;

import de.metas.util.NumberUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;

@EqualsAndHashCode
@ToString
class ModelCacheSourceModel implements ICacheSourceModel
{
	private final Object model;

	ModelCacheSourceModel(@NonNull final Object model)
	{
		this.model = model;
	}

	@Override
	public String getTableName()
	{
		return InterfaceWrapperHelper.getModelTableName(model);
	}

	@Override
	public int getRecordId()
	{
		return InterfaceWrapperHelper.getId(model);
	}

	@Override
	public TableRecordReference getRootRecordReferenceOrNull()
	{
		return null;
	}

	@Override
	public Integer getValueAsInt(final String columnName, final Integer defaultValue)
	{
		final Object valueObj = InterfaceWrapperHelper.getValueOrNull(model, columnName);
		return NumberUtils.asInteger(valueObj, defaultValue);
	}

	@Override
	public boolean isValueChanged(final String columnName)
	{
		return InterfaceWrapperHelper.isValueChanged(model, columnName);
	}
}
