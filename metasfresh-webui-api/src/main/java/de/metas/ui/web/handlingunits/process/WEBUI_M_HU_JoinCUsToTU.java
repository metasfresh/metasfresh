package de.metas.ui.web.handlingunits.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Profile;

import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.WebRestApiApplication;
import de.metas.ui.web.handlingunits.HUDocumentView;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.window.datatypes.DocumentId;

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
 * Join selected CUs to a new TU or to an existing TU
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Profile(value = WebRestApiApplication.PROFILE_Webui)
public class WEBUI_M_HU_JoinCUsToTU extends HUViewProcessTemplate implements IProcessPrecondition
{
	private int rowsCount = 0;
	private List<HUDocumentView> rowsCU;
	private List<HUDocumentView> rowsTU;
	private boolean hasOtherRows = false;

	@Override
	protected void setView(final IDocumentViewSelection view, final Set<DocumentId> selectedDocumentIds)
	{
		super.setView(view, selectedDocumentIds);

		rowsCount = 0;
		rowsCU = new ArrayList<>();
		rowsTU = new ArrayList<>();
		hasOtherRows = false;

		view.streamByIds(selectedDocumentIds)
				.map(HUDocumentView::cast)
				.forEach(row -> {
					rowsCount++;

					if (row.isCU())
					{
						rowsCU.add(row);
					}

					else if (row.isTU())
					{
						rowsTU.add(row);
					}
					else
					{
						hasOtherRows = true;
					}
				});
	}

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (rowsCU.isEmpty())
		{
			return ProcessPreconditionsResolution.reject("Select some CUs");
		}

		if (rowsTU.isEmpty())
		{
			return ProcessPreconditionsResolution.reject("Select one TU");
		}
		else if (rowsTU.size() > 1)
		{
			return ProcessPreconditionsResolution.reject("Select ONLY one TU");
		}

		if (hasOtherRows)
		{
			return ProcessPreconditionsResolution.reject("Select only CUs and one TU");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{

		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
		// return MSG_OK;
	}
}
