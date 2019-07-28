package de.metas.ui.web.handlingunits.process;

import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.HuId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.process.adprocess.WebuiProcess;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.vertical.pharma.securpharm.product.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.service.SecurPharmHUAttributesScanner;
import de.metas.vertical.pharma.securpharm.service.SecurPharmHUAttributesScannerResult;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@WebuiProcess(layoutType = PanelLayoutType.SingleOverlayField)
public class WEBUI_M_HU_SecurPharmScan extends HUEditorProcessTemplate implements IProcessPrecondition
{
	@Autowired
	private SecurPharmService securPharmService;

	static final String PARAM_Barcode = "Barcode";
	@Param(mandatory = true, parameterName = PARAM_Barcode)
	private String dataMatrixString;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!securPharmService.hasConfig())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no SecurPharm config");
		}

		if (!selectedRowIds.isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final HUEditorRow row = getSingleSelectedRow();
		final HuId huId = row.getHuId();

		final SecurPharmHUAttributesScanner scanner = securPharmService.newHUScanner();
		if (!scanner.isEligible(huId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("HU not eligible");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final SecurPharmHUAttributesScanner scanner = securPharmService.newHUScanner();

		final SecurPharmHUAttributesScannerResult result = scanner.scanAndUpdateHUAttributes(getDataMatrix(), getSelectedHuId());

		//
		// Update view
		final HUEditorView view = getView();
		if (result.getExtractedCUId() != null)
		{
			view.addHUId(result.getExtractedCUId());
		}
		view.invalidateAll();

		return result.getResultMessageAndCode();
	}

	private HuId getSelectedHuId()
	{
		return getSingleSelectedRow().getHuId();
	}

	private final DataMatrixCode getDataMatrix()
	{
		return DataMatrixCode.ofString(dataMatrixString);
	}
}
