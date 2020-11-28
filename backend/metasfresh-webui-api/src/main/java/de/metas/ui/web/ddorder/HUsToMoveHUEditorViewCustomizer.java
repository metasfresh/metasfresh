/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.ui.web.ddorder;

import de.metas.handlingunits.model.I_DD_OrderLine;
import de.metas.ui.web.handlingunits.HUEditorViewBuilder;
import de.metas.ui.web.handlingunits.HUEditorViewCustomizer;
import de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Transform;

final class HUsToMoveHUEditorViewCustomizer implements HUEditorViewCustomizer
{
	public static final transient HUsToMoveHUEditorViewCustomizer instance = new HUsToMoveHUEditorViewCustomizer();

	private HUsToMoveHUEditorViewCustomizer()
	{
	}

	@Override
	public String getReferencingTableNameToMatch()
	{
		return I_DD_OrderLine.Table_Name;
	}

	@Override
	public Boolean isAttributesAlwaysReadonly()
	{
		return Boolean.FALSE;
	}

	@Override
	public void beforeCreate(final HUEditorViewBuilder viewBuilder)
	{
		viewBuilder.setParameter(WEBUI_M_HU_Transform.PARAM_CheckExistingHUsInsideView, true);
	}
}
