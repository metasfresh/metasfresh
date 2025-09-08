package de.metas.ui.web.window.descriptor.factory.standard;

import com.google.common.base.Stopwatch;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.ui.web.attributes_included_tab.AttributesIncludedTabLoader;
import de.metas.ui.web.dataentry.window.descriptor.factory.DataEntrySubTabBindingDescriptorBuilder;
import de.metas.ui.web.dataentry.window.descriptor.factory.DataEntryTabLoader;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.GridWindowVO;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Builder
class DefaultDocumentDescriptorLoader
{
	//
	// Services
	@NonNull private static final Logger logger = LogManager.getLogger(DefaultDocumentDescriptorLoader.class);
	@NonNull private final LayoutFactoryProvider layoutFactoryProvider;
	@NonNull private final DataEntrySubTabBindingDescriptorBuilder dataEntrySubTabBindingDescriptorBuilder;

	//
	// Parameters
	@NonNull private final AdWindowId adWindowId;

	//
	// Status
	private boolean _executed;

	public DocumentDescriptor load()
	{
		// Mark as executed
		if (_executed)
		{
			throw new IllegalStateException("Already executed");
		}
		_executed = true;

		final Stopwatch stopwatch = Stopwatch.createStarted();

		final GridWindowVO gridWindowVO = GridWindowVO.builder()
				.ctx(Env.getCtx())
				.windowNo(0) // TODO: get rid of WindowNo from GridWindowVO
				.adWindowId(adWindowId)
				.adMenuId(-1) // N/A
				.loadAllLanguages(true)
				.applyRolePermissions(false) // must be false, unless we know that we do have #AD_User_ID in the context (which oftentimes we don't)
				.build();

		final DocumentDescriptor.Builder documentBuilder = DocumentDescriptor.builder();

		final DocumentLayoutDescriptor.Builder layoutBuilder = DocumentLayoutDescriptor.builder()
				.setWindowId(WindowId.of(gridWindowVO.getAdWindowId()))
				.setStopwatch(stopwatch)
				.putDebugProperty("generator-name", toString());

		//
		// Layout: Create UI sections from main tab
		final LayoutFactory rootLayoutFactory = layoutFactoryProvider.ofMainTab(gridWindowVO);
		{
			final ITranslatableString windowCaption = TranslatableStrings.ofMap(gridWindowVO.getNameTrls(), gridWindowVO.getName());
			layoutBuilder.setCaption(windowCaption);

			layoutBuilder.setSingleRowLayout(rootLayoutFactory.layoutSingleRow());
			layoutBuilder.setGridView(rootLayoutFactory.layoutGridView());
			layoutBuilder.setSideListView(rootLayoutFactory.layoutSideListView());

			// Set special field names
			// IMPORTANT: do this after you created all layouts
			layoutBuilder
					.setDocumentSummaryElement(rootLayoutFactory.createSpecialElement_DocumentSummary())
					.setDocActionElement(rootLayoutFactory.createSpecialElement_DocStatusAndDocAction());
		}

		//
		// Standard tabs loader
		for (final LayoutFactory detailLayoutFactory : rootLayoutFactory.getIncludedTabLayouts())
		{
			detailLayoutFactory.layoutDetail()
					.map(DocumentLayoutDetailDescriptor.Builder::build)
					.ifPresent(layoutBuilder::addDetail);

			final DocumentEntityDescriptor.Builder detailEntityBuilder = detailLayoutFactory.documentEntity();
			rootLayoutFactory.documentEntity().addIncludedEntity(detailEntityBuilder.build());
		}

		//
		// DataEntry tabs loader
		{
			final DataEntryTabLoader dataEntryTabLoader = DataEntryTabLoader.builder()
					.adWindowId(adWindowId)
					.windowId(rootLayoutFactory.documentEntity().getWindowId())
					.dataEntrySubTabBindingDescriptorBuilder(dataEntrySubTabBindingDescriptorBuilder)
					.build();
			final List<DocumentLayoutDetailDescriptor> layoutDescriptors = dataEntryTabLoader.loadDocumentLayout();
			for (final DocumentLayoutDetailDescriptor descriptor : layoutDescriptors)
			{
				layoutBuilder.addDetail(descriptor);
			}

			final List<DocumentEntityDescriptor> entityDescriptors = dataEntryTabLoader.loadDocumentEntity();
			for (final DocumentEntityDescriptor descriptor : entityDescriptors)
			{
				rootLayoutFactory.documentEntity().addIncludedEntity(descriptor);
			}
		}

		//
		// Attributes tabs loader
		{
			AttributesIncludedTabLoader.builder()
					.adWindowId(adWindowId)
					.rootEntityDescriptor(rootLayoutFactory.documentEntity())
					.layoutBuilder(layoutBuilder)
					.build()
					.load();
		}

		//
		// Build & return the final descriptor
		final DocumentDescriptor descriptor = documentBuilder
				.setLayout(layoutBuilder.build())
				.setEntityDescriptor(rootLayoutFactory.documentEntity().build())
				.build();
		logger.debug("Descriptor loaded in {}: {}", stopwatch, descriptor);
		return descriptor;
	}

}
