/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.contract.flatrate.view;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryId;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryRepo;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryService;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.i18n.ITranslatableString;
import de.metas.process.IADProcessDAO;
import de.metas.ui.web.contract.flatrate.model.DataEntryDetailsRow;
import de.metas.ui.web.contract.flatrate.model.DataEntryDetailsRowsData;
import de.metas.ui.web.contract.flatrate.model.DataEntryDetailsRowsLoader;
import de.metas.ui.web.contract.flatrate.process.WEBUI_C_Flatrate_DataEntry_Detail_Launcher;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner_Department;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@ViewFactory(windowId = DataEntryDedailsViewFactory.WINDOW_ID_STRING)
public class DataEntryDedailsViewFactory implements IViewFactory, IViewsIndexStorage
{
	private static final String PARAM_RecordRef = "recordRef";
	static final String WINDOW_ID_STRING = "dataEntryDedails";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	private final Cache<ViewId, DataEntryDetailsView> views = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.HOURS)
			.build();

	private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);

	private final FlatrateDataEntryService flatrateDataEntryService;

	private final FlatrateDataEntryRepo flatrateDataEntryRepo;

	public DataEntryDedailsViewFactory(
			@NonNull final FlatrateDataEntryService flatrateDataEntryService,
			@NonNull final FlatrateDataEntryRepo flatrateDataEntryRepo)
	{
		this.flatrateDataEntryService = flatrateDataEntryService;
		this.flatrateDataEntryRepo = flatrateDataEntryRepo;
	}

	public final CreateViewRequest createViewRequest(@NonNull final TableRecordReference recordRef)
	{
		return CreateViewRequest.builder(getWindowId())
				.setParameter(PARAM_RecordRef, recordRef)
				.build();
	}

	@Override
	public final void setViewsRepository(final IViewsRepository viewsRepository)
	{
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		final DataEntryDetailsRowsData rowsData = loadRowsData(request);

		return DataEntryDetailsView.builder()
				.windowId(getWindowId())
				.rowsData(rowsData)
				.processes(ImmutableList.of())
				.build();
	}

	private DataEntryDetailsRowsData loadRowsData(@NonNull final CreateViewRequest request)
	{
		final TableRecordReference recordRef = getRecordReference(request);
		final I_C_Flatrate_DataEntry record = recordRef.getModel(I_C_Flatrate_DataEntry.class);

		final FlatrateDataEntryId flatrateDataEntryId = FlatrateDataEntryId.ofRepoId(
				FlatrateTermId.ofRepoId(record.getC_Flatrate_Term_ID()),
				record.getC_Flatrate_DataEntry_ID());

		final LookupDataSource departmentLookup = LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_C_BPartner_Department.Table_Name);
		final LookupDataSource uomLookup = LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_C_UOM.Table_Name);

		return DataEntryDetailsRowsLoader.builder()
				.departmentLookup(departmentLookup)
				.uomLookup(uomLookup)
				.flatrateDataEntryService(flatrateDataEntryService)
				.flatrateDataEntryRepo(flatrateDataEntryRepo)
				.flatrateDataEntryId(flatrateDataEntryId)
				.build()
				.load();

	}

	private TableRecordReference getRecordReference(final CreateViewRequest request)
	{
		final TableRecordReference recordRef = request.getParameterAs(PARAM_RecordRef, TableRecordReference.class);
		if (recordRef == null)
		{
			throw new AdempiereException("Invalid request, parameter " + PARAM_RecordRef + " is not set: " + request);
		}
		return recordRef;
	}

	@Override
	public ViewLayout getViewLayout(
			@NonNull final WindowId windowId,
			@NonNull final JSONViewDataType viewDataType,
			final ViewProfileId profileId)
	{
		final ITranslatableString caption = processDAO
				.retrieveProcessNameByClassIfUnique(WEBUI_C_Flatrate_DataEntry_Detail_Launcher.class)
				.orElse(null);

		return ViewLayout.builder()
				.setWindowId(windowId)
				.setCaption(caption)
				//.allowViewCloseAction(ViewCloseAction.CANCEL)
				.allowViewCloseAction(ViewCloseAction.DONE)
				//
				.setFocusOnFieldName(DataEntryDetailsRow.FIELD_Qty)
				.addElementsFromViewRowClassAndFieldNames(
						DataEntryDetailsRow.class,
						viewDataType,
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(DataEntryDetailsRow.FIELD_Department),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(DataEntryDetailsRow.FIELD_ASI),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(DataEntryDetailsRow.FIELD_Qty),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(DataEntryDetailsRow.FIELD_UOM))
				//
				.setAllowOpeningRowDetails(false)

				.build();
	}

	@Override
	public WindowId getWindowId()
	{
		return WINDOW_ID;
	}

	@Override
	public final void put(@NonNull final IView view)
	{
		views.put(view.getViewId(), DataEntryDetailsView.cast(view));
	}

	@Nullable
	@Override
	public IView getByIdOrNull(final ViewId viewId)
	{
		return views.getIfPresent(viewId);
	}

	@Override
	public void closeById(final ViewId viewId, final ViewCloseAction closeAction)
	{

	}

	@Override
	public Stream<IView> streamAllViews()
	{
		return Stream.empty();
	}

	@Override
	public void invalidateView(final ViewId viewId)
	{

	}
}
