package de.metas.ui.web.pickingslot.process;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableList;

import de.metas.picking.model.I_M_PickingSlot;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pickingslot.BrowsePickingSlotsViewFactory;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;

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

/**
 * Starts picking slots view in a modal window.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @deprecated To be deleted after https://github.com/metasfresh/metasfresh-webui-frontend/issues/1239
 */
@Deprecated
public class WEBUI_BrowsePickingSlots_Start extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		getResult()
				.setRecordsToOpen(
						TableRecordReference.ofRecordIds(I_M_PickingSlot.Table_Name, ImmutableList.of(1)), // the ID does not matter because we are not checking it
						BrowsePickingSlotsViewFactory.WINDOW_ID.toInt());

		return MSG_OK;
	}

}
