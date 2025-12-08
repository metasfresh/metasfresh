package org.adempiere.ad.persistence;

import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * Convenient model's dynamic attribute accessor. This instance provides type-safe access to a model's dynamic attributes.
 * It's recommended to use it rather that directly calling {@link InterfaceWrapperHelper#setDynAttribute(Object, String, Object)} etc.
 * Neither the model not the attribute value is referenced by instance of this class.
 *
 * @param <ModelType>
 * @param <AttributeType>
 * @author tsa
 */
@ToString
public final class ModelDynAttributeAccessor<ModelType, AttributeType>
{
	@Getter private final String attributeName;
	private final Class<AttributeType> attributeType;

	public ModelDynAttributeAccessor(@NonNull final Class<AttributeType> attributeTypeClass)
	{
		this.attributeName = attributeTypeClass.getName();
		this.attributeType = attributeTypeClass;
	}

	public ModelDynAttributeAccessor(
			@NonNull final String attributeName,
			@NonNull final Class<AttributeType> attributeTypeClass)
	{
		this.attributeName = attributeName;
		this.attributeType = attributeTypeClass;
	}

	public ModelDynAttributeAccessor(final String attributeGroupName, final String attributeName, final Class<AttributeType> attributeTypeClass)
	{
		this(attributeGroupName + "#" + attributeName // attributeName
				, attributeTypeClass);
	}

	public Class<AttributeType> getAttributeType()
	{
		Check.assumeNotNull(attributeType, "attributeType not null");
		return attributeType;
	}

	public AttributeType getValue(final ModelType model)
	{
		return InterfaceWrapperHelper.getDynAttribute(model, attributeName);
	}

	public AttributeType getValue(final ModelType model, final AttributeType defaultValueIfNull)
	{
		final AttributeType attributeValue = getValue(model);
		if (attributeValue == null)
		{
			return defaultValueIfNull;
		}
		return attributeValue;
	}

	public Optional<AttributeType> getValueIfExists(final ModelType model)
	{
		final AttributeType attributeValue = getValue(model);
		return Optional.ofNullable(attributeValue);
	}

	public void setValue(final ModelType model, @Nullable final AttributeType attributeValue)
	{
		InterfaceWrapperHelper.setDynAttribute(model, attributeName, attributeValue);
	}

	public IAutoCloseable temporarySetValue(final ModelType model, final AttributeType value)
	{
		final AttributeType valueOld = getValue(model);
		setValue(model, value);
		return () -> setValue(model, valueOld);
	}

	public boolean isSet(final ModelType model)
	{
		final Object attributeValue = InterfaceWrapperHelper.getDynAttribute(model, attributeName);
		return attributeValue != null;
	}

	public boolean isNull(final ModelType model)
	{
		final Object attributeValue = InterfaceWrapperHelper.getDynAttribute(model, attributeName);
		return attributeValue == null;
	}

	/**
	 * @return true if given <code>model</code>'s attribute equals with <code>expectedValue</code>
	 */
	public boolean is(final ModelType model, final AttributeType expectedValue)
	{
		final Object attributeValue = InterfaceWrapperHelper.getDynAttribute(model, attributeName);
		return Objects.equals(attributeValue, expectedValue);
	}

	public void reset(final ModelType model)
	{
		setValue(model, null);
	}
}
