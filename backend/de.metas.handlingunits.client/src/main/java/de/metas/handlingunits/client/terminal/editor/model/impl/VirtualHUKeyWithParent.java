package de.metas.handlingunits.client.terminal.editor.model.impl;

import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyNameBuilder;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;

/**
 * {@link HUKey} with a {@link VirtualHUKeyWithParentNameBuilder} to display more relevant information to the user.
 *
 * @author al
 */
public class VirtualHUKeyWithParent extends HUKey
{
	public VirtualHUKeyWithParent(final IHUKeyFactory keyFactory, final I_M_HU hu, final IHUDocumentLine documentLine)
	{
		super(keyFactory, hu, documentLine);

		Check.assume(hu.getM_HU_Item_Parent_ID() > 0, "hu has a parent ({})", hu);
	}

	@Override
	protected IHUKeyNameBuilder createHUKeyNameBuilder()
	{
		return new VirtualHUKeyWithParentNameBuilder(this);
	}
}
