package de.metas.ui.web.receiptSchedule;

import org.springframework.stereotype.Component;

import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.ui.web.handlingunits.HUEditorRowIsProcessedPredicate;
import de.metas.ui.web.handlingunits.HUEditorRowIsProcessedPredicates;
import de.metas.ui.web.handlingunits.HUEditorViewBuilder;
import de.metas.ui.web.handlingunits.HUEditorViewCustomizer;
import de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Transform;

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

@Component
public class HUEditorView_ReceiptScheduleCustomizer implements HUEditorViewCustomizer
{
	@Override
	public String getReferencingTableNameToMatch()
	{
		return I_M_ReceiptSchedule.Table_Name;
	}

	@Override
	public HUEditorRowIsProcessedPredicate getHUEditorRowIsProcessedPredicate()
	{
		return HUEditorRowIsProcessedPredicates.IF_NOT_PLANNING_HUSTATUS;
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
