package de.metas.handlingunits.attribute.storage;

import java.util.List;
import java.util.Map;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IMutableHUTransactionAttribute;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.impl.AbstractAttributeStorage;
import de.metas.handlingunits.attribute.storage.impl.NullAttributeStorage;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Wraps an {@link I_M_AttributeSetInstance}.
 */
public class ASIAttributeStorage extends AbstractAttributeStorage
{

	public static IAttributeStorage createNew(
			@NonNull final IAttributeStorageFactory attributeStorageFactory,
			@NonNull final I_M_AttributeSetInstance attributeSetInstance)
	{
		return new ASIAttributeStorage(attributeStorageFactory, attributeSetInstance);
	}

	private final String id;
	private final I_M_AttributeSetInstance asi;

	private ASIAttributeStorage(
			final IAttributeStorageFactory storageFactory,
			@NonNull final I_M_AttributeSetInstance asi)
	{
		super(Check.assumeNotNull(storageFactory, "storageFactory"));

		this.asi = asi;
		this.id = I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID + "=" + asi.getM_AttributeSetInstance_ID();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public IAttributeStorage getParentAttributeStorage()
	{
		return NullAttributeStorage.instance;
	}

	/**
	 * Always returns an empty collection.
	 */
	@Override
	public final List<IAttributeStorage> getChildAttributeStorages(boolean loadIfNeeded_IGNORED)
	{
		return ImmutableList.of();
	}

	@Override
	protected void toString(final ToStringHelper stringHelper)
	{
		stringHelper
				.add("id", id)
				.add("asi", asi);
	}

	@Override
	protected List<IAttributeValue> loadAttributeValues()
	{
		final IAttributeDAO attributesDAO = Services.get(IAttributeDAO.class);

		final ImmutableList.Builder<IAttributeValue> result = ImmutableList.builder();

		final List<I_M_AttributeInstance> attributeInstances = attributesDAO.retrieveAttributeInstances(asi);
		for (final I_M_AttributeInstance attributeInstance : attributeInstances)
		{
			final IAttributeValue attributeValue = new AIAttributeValue(this, attributeInstance);
			result.add(attributeValue);
		}
		return result.build();
	}

	@Override
	protected List<IAttributeValue> generateAndGetInitialAttributes(final IAttributeValueContext attributesCtx, final Map<AttributeId, Object> defaultAttributesValue)
	{
		throw new UnsupportedOperationException("Generating initial attributes not supported for " + this);
	}

	@Override
	public void updateHUTrxAttribute(final IMutableHUTransactionAttribute huTrxAttribute, final IAttributeValue fromAttributeValue)
	{
		huTrxAttribute.setReferencedObject(asi);
	}

	/**
	 * Method not supported.
	 *
	 * @throws UnsupportedOperationException
	 */
	@Override
	protected void addChildAttributeStorage(final IAttributeStorage childAttributeStorage)
	{
		throw new UnsupportedOperationException("Child attribute storages are not supported for " + this);
	}

	/**
	 * Method not supported.
	 *
	 * @throws UnsupportedOperationException
	 */
	@Override
	protected IAttributeStorage removeChildAttributeStorage(final IAttributeStorage childAttributeStorage)
	{
		throw new UnsupportedOperationException("Child attribute storages are not supported for " + this);
	}

	@Override
	public void saveChangesIfNeeded()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSaveOnChange(boolean saveOnChange)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getQtyUOMTypeOrNull()
	{
		// ASI attribute storages does not support Qty Storage
		return null;
	}
}
