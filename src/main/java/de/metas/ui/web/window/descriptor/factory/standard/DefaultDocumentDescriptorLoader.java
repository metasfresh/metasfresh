package de.metas.ui.web.window.descriptor.factory.standard;

import org.adempiere.util.Check;
import org.compiere.model.GridTabVO;
import org.compiere.model.GridWindowVO;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Stopwatch;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.exceptions.DocumentLayoutBuildException;

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

/* package */ class DefaultDocumentDescriptorLoader
{
	private static final Logger logger = LogManager.getLogger(DefaultDocumentDescriptorLoader.class);

	//
	// Parameters
	private final int AD_Window_ID;

	//
	// Status
	private boolean _executed = false;

	/* package */ DefaultDocumentDescriptorLoader(final int AD_Window_ID)
	{
		super();
		this.AD_Window_ID = AD_Window_ID;
	}

	public DocumentDescriptor load()
	{
		// Mark as executed
		if (_executed)
		{
			throw new IllegalStateException("Already executed");
		}
		_executed = true;

		if (AD_Window_ID <= 0)
		{
			throw new DocumentLayoutBuildException("No window found for AD_Window_ID=" + AD_Window_ID);
		}

		final Stopwatch stopwatch = Stopwatch.createStarted();
		final GridWindowVO gridWindowVO = GridWindowVO.builder()
				.ctx(Env.getCtx())
				.windowNo(0) // TODO: get rid of WindowNo from GridWindowVO
				.adWindowId(AD_Window_ID)
				.adMenuId(-1) // N/A
				.loadAllLanguages(true)
				.applyRolePermissions(false)
				.build();
		Check.assumeNotNull(gridWindowVO, "Parameter gridWindowVO is not null"); // shall never happen

		final DocumentDescriptor.Builder documentBuilder = DocumentDescriptor.builder();
		final DocumentLayoutDescriptor.Builder layoutBuilder = DocumentLayoutDescriptor.builder()
				.setWindowId(WindowId.of(gridWindowVO.getAD_Window_ID()))
				.setStopwatch(stopwatch)
				.putDebugProperty("generator-name", toString());

		//
		// Layout: Create UI sections from main tab
		final GridTabVO mainTabVO = gridWindowVO.getTab(GridTabVO.MAIN_TabNo);
		final LayoutFactory rootLayoutFactory = LayoutFactory.ofMainTab(gridWindowVO, mainTabVO);
		{
			layoutBuilder.setCaption(rootLayoutFactory.getWindowCaption());
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
		// Layout: Create UI details from child tabs
		for (final GridTabVO detailTabVO : gridWindowVO.getChildTabs(mainTabVO.getTabNo()))
		{
			// Skip sort tabs because they are not supported
			if (detailTabVO.IsSortTab)
			{
				continue;
			}
			
			// Skip tabs which were already used/embedded in root layout
			if(rootLayoutFactory.isSkipAD_Tab_ID(detailTabVO.getAD_Tab_ID()))
			{
				continue;
			}

			final LayoutFactory detailLayoutFactory = LayoutFactory.ofIncludedTab(gridWindowVO, mainTabVO, detailTabVO);
			final DocumentLayoutDetailDescriptor.Builder layoutDetail = detailLayoutFactory.layoutDetail();
			layoutBuilder.addDetailIfValid(layoutDetail);

			final DocumentEntityDescriptor.Builder detailEntityBuilder = detailLayoutFactory.documentEntity();
			rootLayoutFactory.documentEntity().addIncludedEntity(detailEntityBuilder.build());
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
