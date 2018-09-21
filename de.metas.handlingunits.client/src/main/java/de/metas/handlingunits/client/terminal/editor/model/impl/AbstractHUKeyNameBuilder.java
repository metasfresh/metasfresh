package de.metas.handlingunits.client.terminal.editor.model.impl;

import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.ImmutableReference;

import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyNameBuilder;
import de.metas.util.Check;

public abstract class AbstractHUKeyNameBuilder<T extends IHUKey> implements IHUKeyNameBuilder
{
	private final IReference<T> keyRef;

	public AbstractHUKeyNameBuilder(final T key)
	{
		super();

		Check.assumeNotNull(key, "key not null");
		this.keyRef = ImmutableReference.<T>valueOf(key);
	}

	/**
	 *
	 * @return HU Key; never return null
	 */
	protected final T getKey()
	{
		final T key = keyRef.getValue();
		Check.assumeNotNull(key, "key reference not expired");
		return key;
	}
}
