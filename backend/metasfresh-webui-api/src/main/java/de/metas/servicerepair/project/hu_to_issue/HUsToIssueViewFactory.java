/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.servicerepair.project.hu_to_issue;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.product.ProductId;
import de.metas.servicerepair.project.process.HUsToIssueView_IssueHUs;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.handlingunits.HUEditorViewBuilder;
import de.metas.ui.web.handlingunits.HUEditorViewFactoryTemplate;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import lombok.NonNull;

@ViewFactory(windowId = HUsToIssueViewFactory.Window_ID_String)
public class HUsToIssueViewFactory extends HUEditorViewFactoryTemplate
{
	public static final String Window_ID_String = "husToIssueOnProject";
	public static final WindowId Window_ID = WindowId.fromJson(Window_ID_String);

	public static final String PARAM_HUsToIssueViewContext = "HUsToIssueViewContext";

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	protected HUsToIssueViewFactory()
	{
		super(ImmutableList.of());
	}

	public CreateViewRequest createViewRequest(@NonNull final HUsToIssueViewContext context)
	{
		return CreateViewRequest.builder(Window_ID)
				.addStickyFilters(createEligibleHUsFilter(context.getProductId()))
				.setParameter(PARAM_HUsToIssueViewContext, context)
				.build();
	}

	private DocumentFilter createEligibleHUsFilter(
			@NonNull final ProductId productId)
	{
		return HUIdsFilterHelper.createFilter(
				handlingUnitsBL.createHUQueryBuilder()
						.addOnlyWithProductId(productId)
						.addHUStatusToInclude(X_M_HU.HUSTATUS_Active)
						.setExcludeReserved());
	}

	@Override
	protected void customizeHUEditorView(final HUEditorViewBuilder huViewBuilder)
	{
		huViewBuilder.assertParameterSet(PARAM_HUsToIssueViewContext);

		huViewBuilder.considerTableRelatedProcessDescriptors(false)
				.addAdditionalRelatedProcessDescriptor(createProcessDescriptor(HUsToIssueView_IssueHUs.class));
	}

}
