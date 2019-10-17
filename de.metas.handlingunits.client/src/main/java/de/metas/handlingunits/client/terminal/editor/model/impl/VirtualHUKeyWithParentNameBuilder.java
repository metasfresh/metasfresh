package de.metas.handlingunits.client.terminal.editor.model.impl;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;
import de.metas.util.StringUtils;

/**
 * Will do pretty much the same as the ordinary {@link HUKeyNameBuilder}, but it's supposed to display more relevant information to the user (i.e to what IFCO this VPI belongs to).
 *
 * @author al
 */
/* package */class VirtualHUKeyWithParentNameBuilder extends HUKeyNameBuilder
{
	private String htmlPartHUDisplayName = null;

	public VirtualHUKeyWithParentNameBuilder(final HUKey huKey)
	{
		super(huKey);

		Check.assume(huKey.isVirtualPI(), "huKey is a virtualPI: {}", huKey);
	}

	@Override
	protected final String getHTMLPartHUDisplayName()
	{
		if (htmlPartHUDisplayName == null)
		{
			final I_M_HU parent = handlingUnitsDAO.retrieveParent(getM_HU());

			//
			// Use just the HU's value; do not display the full HU to the user to preserve much-needed space on the key
			final String displayName = parent.getValue(); // handlingUnitsBL.getDisplayName(parent);
			htmlPartHUDisplayName = StringUtils.maskHTML(displayName);
		}
		return htmlPartHUDisplayName;
	}
}
