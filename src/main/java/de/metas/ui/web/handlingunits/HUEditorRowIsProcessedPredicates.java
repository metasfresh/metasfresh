package de.metas.ui.web.handlingunits;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@UtilityClass
public class HUEditorRowIsProcessedPredicates
{
	public static final HUEditorRowIsProcessedPredicate NEVER = new Never();
	public static final HUEditorRowIsProcessedPredicate IF_NOT_PLANNING_HUSTATUS = new IfNotPlanningHUStatus();

	private static final class Never implements HUEditorRowIsProcessedPredicate
	{
		@Override
		public boolean isProcessed(I_M_HU hu)
		{
			return false;
		}
	}

	private static final class IfNotPlanningHUStatus implements HUEditorRowIsProcessedPredicate
	{
		@Override
		public boolean isProcessed(I_M_HU hu)
		{
			return !X_M_HU.HUSTATUS_Planning.equals(hu.getHUStatus());
		}

	}

}
