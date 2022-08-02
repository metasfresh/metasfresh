package de.metas.ui.web.picking.process;

import com.google.common.collect.ImmutableList;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.packageable.PackageableViewFactory;
import de.metas.ui.web.picking.packageable.filters.PackageableFilterDescriptorProvider;
import de.metas.ui.web.picking.packageable.filters.ProductBarcodeFilterData;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_DIVERGING_LOCATIONS;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_TOO_MANY_PACKAGEABLES_1P;

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

public class WEBUI_Picking_Launcher extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private static final int MAX_ROWS_ALLOWED = 50;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return verifySelectedDocuments();
	}

	@Override
	protected String doIt()
	{
		// repeat the verification from checkPreconditionsApplicable() just to be sure.
		// We had cases where the selected rows of checkPreconditionsApplicable() were not the selected rows of doIt()
		verifySelectedDocuments().throwExceptionIfRejected();

		final List<ShipmentScheduleId> shipmentScheduleIds = getShipmentScheduleIds();
		if (shipmentScheduleIds.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		final IView packageablesView = getViewsRepo().createView(
				PackageableViewFactory.createViewRequest()
						.shipmentScheduleIds(shipmentScheduleIds)
						.barcodeFilterData(getProductBarcodeFilterData().orElse(null))
						.build());

		getResult().setWebuiViewToOpen(ProcessExecutionResult.WebuiViewToOpen.builder()
											   .target(ProcessExecutionResult.ViewOpenTarget.ModalOverlay)
											   .viewId(packageablesView.getViewId().toJson())
											   .build());

		return MSG_OK;
	}

	private ProcessPreconditionsResolution verifySelectedDocuments()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRootDocumentIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final long selectionSize = getSelectionSize(selectedRowIds);
		if (selectionSize > MAX_ROWS_ALLOWED)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_TOO_MANY_PACKAGEABLES_1P, MAX_ROWS_ALLOWED));
		}

		// Make sure that they all have the same C_BPartner and location.
		if (selectionSize > 1)
		{
			final Set<Integer> bpartnerLocationIds = getView().streamByIds(selectedRowIds)
					.flatMap(selectedRow -> selectedRow.getIncludedRows().stream())
					.map(WEBUI_Picking_Launcher::getBPartnerLocationId)
					.collect(Collectors.toSet());
			if (bpartnerLocationIds.size() > 1)
			{
				return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_DIVERGING_LOCATIONS));
			}
		}

		return ProcessPreconditionsResolution.accept();
	}

	private static int getBPartnerLocationId(@NonNull final IViewRow row)
	{
		return row.getFieldValueAsInt(I_M_Packageable_V.COLUMNNAME_C_BPartner_Location_ID, -1);
	}

	private long getSelectionSize(final DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			return getView().size();
		}
		else
		{
			return rowIds.size();
		}
	}

	private DocumentIdsSelection getSelectedRootDocumentIds()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isAll())
		{
			return selectedRowIds;
		}
		else if (selectedRowIds.isEmpty())
		{
			return selectedRowIds;
		}
		else
		{
			return selectedRowIds.stream().filter(DocumentId::isInt).collect(DocumentIdsSelection.toDocumentIdsSelection());
		}
	}

	private Optional<ProductBarcodeFilterData> getProductBarcodeFilterData()
	{
		return PackageableFilterDescriptorProvider.extractProductBarcodeFilterData(getView());
	}

	private List<ShipmentScheduleId> getShipmentScheduleIds()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRootDocumentIds();
		return getView().streamByIds(selectedRowIds)
				.flatMap(selectedRow -> selectedRow.getIncludedRows().stream())
				.map(IViewRow::getId)
				.distinct()
				.map(DocumentId::removeDocumentPrefixAndConvertToInt)
				.map(ShipmentScheduleId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}
}
