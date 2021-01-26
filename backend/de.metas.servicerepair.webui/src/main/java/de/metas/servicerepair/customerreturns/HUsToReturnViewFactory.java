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

package de.metas.servicerepair.customerreturns;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inout.InOutId;
import de.metas.servicerepair.customerreturns.process.HUsToReturn_SelectHU;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorViewBuilder;
import de.metas.ui.web.handlingunits.HUEditorViewFactoryTemplate;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.MediaType;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import org.compiere.util.DB;

import javax.annotation.Nullable;

@ViewFactory(windowId = HUsToReturnViewFactory.Window_ID_String, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class HUsToReturnViewFactory extends HUEditorViewFactoryTemplate
{
	public static final String Window_ID_String = "541011"; // FIXME: hardcoded
	public static final WindowId Window_ID = WindowId.fromJson(Window_ID_String);

	public static final String PARAM_HUsToReturnViewContext = "HUsToReturnViewContext";

	protected HUsToReturnViewFactory()
	{
		super(ImmutableList.of());
	}

	public static CreateViewRequest createViewRequest(@NonNull final InOutId customerReturnsId)
	{
		return CreateViewRequest.builder(Window_ID)
				.setParameter(PARAM_HUsToReturnViewContext, HUsToReturnViewContext.builder()
						.customerReturnsId(customerReturnsId)
						.build())
				.build();
	}

	@Override
	protected void customizeViewLayout(
			@NonNull final ViewLayout.Builder viewLayoutBuilder,
			final JSONViewDataType viewDataType)
	{
		viewLayoutBuilder
				.clearElements()
				.addElementsFromViewRowClassAndFieldNames(HUEditorRow.class,
						viewDataType,
						ViewColumnHelper.ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HUCode).restrictToMediaType(MediaType.SCREEN).build(),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_Locator),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_Product),
						ViewColumnHelper.ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_PackingInfo).restrictToMediaType(MediaType.SCREEN).build(),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_QtyCU),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_UOM),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_SerialNo),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_ServiceContract),
						ViewColumnHelper.ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HUStatus).restrictToMediaType(MediaType.SCREEN).build());
	}

	@Override
	protected void customizeHUEditorView(final HUEditorViewBuilder huViewBuilder)
	{
		huViewBuilder.assertParameterSet(PARAM_HUsToReturnViewContext);

		huViewBuilder.considerTableRelatedProcessDescriptors(false)
				.addAdditionalRelatedProcessDescriptor(createProcessDescriptor(HUsToReturn_SelectHU.class));
	}

	@Nullable
	@Override
	protected String getAdditionalSqlWhereClause()
	{
		return I_M_HU.COLUMNNAME_HUStatus + "=" + DB.TO_STRING(X_M_HU.HUSTATUS_Shipped);
	}

	/**
	 * This view is not configuration dependent always should be false to execute the customizeViewLayout method
	 */
	@Override
	protected boolean isAlwaysUseSameLayout()
	{
		return false;
	}
}
