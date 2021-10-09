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
import de.metas.servicerepair.customerreturns.process.HUsToReturn_CreateShippedHU;
import de.metas.servicerepair.customerreturns.process.HUsToReturn_SelectHU;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorViewBuilder;
import de.metas.ui.web.handlingunits.HUEditorViewFactoryTemplate;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper.ClassViewColumnOverrides;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.MediaType;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import org.compiere.util.DB;

import javax.annotation.Nullable;

@ViewFactory(windowId = HUsToReturnViewFactory.Window_ID_String, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class HUsToReturnViewFactory extends HUEditorViewFactoryTemplate
{
	static final String Window_ID_String = "541011"; // FIXME: hardcoded
	private static final WindowId Window_ID = WindowId.fromJson(Window_ID_String);

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
														  ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HUCode).restrictToMediaType(MediaType.SCREEN).build(),
														  ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_Locator),
														  ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_Product),
														  ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_PackingInfo).restrictToMediaType(MediaType.SCREEN).build(),
														  ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_QtyCU),
														  ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_UOM),
														  ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_SerialNo),
														  ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_ServiceContract),
														  ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HUStatus).restrictToMediaType(MediaType.SCREEN).build());
	}

	@Override
	protected void customizeHUEditorView(final HUEditorViewBuilder huViewBuilder)
	{
		huViewBuilder.assertParameterSet(PARAM_HUsToReturnViewContext);

		huViewBuilder.considerTableRelatedProcessDescriptors(false)
				.addAdditionalRelatedProcessDescriptor(createProcessDescriptor(HUsToReturn_SelectHU.class))
				.addAdditionalRelatedProcessDescriptor(createProcessDescriptor(HUsToReturn_CreateShippedHU.class));
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
