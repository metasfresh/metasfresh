package org.compiere.apps.search;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.awt.Window;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.grid.GridController;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridTabMaxRowsRestrictionChecker;
import org.compiere.model.MQuery;
import org.compiere.util.Env;
import org.compiere.util.SwingUtils;

import de.metas.util.Check;
import lombok.NonNull;

/**
 * Builder which is able to configure and create instances of {@link Find} dialog, {@link FindPanel}, {@link FindPanelContainer}.
 * 
 * @author tsa
 *
 */
public class FindPanelBuilder
{
	private Window parentFrame;
	private int targetWindowNo;
	private GridController gridController = null;
	private GridTab gridTab = null;
	//
	private String title;
	private int AD_Tab_ID;
	private int templateTabId;
	private int targetTabNo = -1; // metas-2009_0021_AP1_G113
	private int AD_Table_ID;
	private String tableName;
	private String whereExtended;
	private MQuery query;
	private GridField[] findFields;
	private int minRecords = 0;
	private boolean small;
	private boolean hideStatusBar = false;
	private boolean searchPanelCollapsed = false;
	private Integer maxQueryRecordsPerTab = null;
	private boolean embedded = false;

	/* package */ FindPanelBuilder()
	{
		super();
	}

	public FindPanel buildFindPanel()
	{
		return new FindPanel(this);
	}

	/**
	 * Build and display the model {@link Find} dialog.
	 * 
	 * @return {@link Find} dialog.
	 */
	public Find buildFindDialog()
	{
		return new Find(this);
	}

	public FindPanelContainer buildFindPanelContainer()
	{
		if (gridController != null && gridController.isAlignVerticalTabsWithHorizontalTabs())
		{
			return new FindPanelContainer_Embedded(this);
		}
		return new FindPanelContainer_Collapsible(this);
	}

	/**
	 * New builder to create {@link GridTabMaxRowsRestrictionChecker}.
	 * 
	 * The builder is already preconfigured with the values from this builder.
	 * 
	 * @return {@link GridTabMaxRowsRestrictionChecker} builder.
	 */
	public GridTabMaxRowsRestrictionChecker.Builder newGridTabMaxRowsRestrictionChecker()
	{
		return GridTabMaxRowsRestrictionChecker.builder()
				.setAD_Tab(getGridTab())
				.setMaxQueryRecordsPerTab(getMaxQueryRecordsPerTab());
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	};

	public FindPanelBuilder setParentFrame(Window parentFrame)
	{
		this.parentFrame = parentFrame;
		return this;
	}

	public Window getParentFrame()
	{
		return parentFrame;
	}

	public FindPanelBuilder setTargetWindowNo(int targetWindowNo)
	{
		this.targetWindowNo = targetWindowNo;
		return this;
	}

	public int getTargetWindowNo()
	{
		return targetWindowNo;
	}

	public FindPanelBuilder setTitle(String title)
	{
		this.title = title;
		return this;
	}

	public String getTitle()
	{
		return title;
	}

	public FindPanelBuilder setAD_Tab_ID(int adTabId)
	{
		this.AD_Tab_ID = adTabId;
		return this;
	}

	public int getAD_Tab_ID()
	{
		return AD_Tab_ID;
	}

	public FindPanelBuilder setTemplateTabId(final int templateTabId)
	{
		this.templateTabId = templateTabId;
		return this;
	}

	public int getTemplateTabId()
	{
		return templateTabId;
	}

	public FindPanelBuilder setTargetTabNo(int targetTabNo)
	{
		this.targetTabNo = targetTabNo;
		return this;
	}

	public int getTargetTabNo()
	{
		return targetTabNo;
	}

	public FindPanelBuilder setAD_Table_ID(int adTableId)
	{
		this.AD_Table_ID = adTableId;
		return this;
	}

	public int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	public FindPanelBuilder setTableName(String tableName)
	{
		this.tableName = tableName;
		return this;
	}

	public String getTableName()
	{
		return this.tableName;
	}

	public FindPanelBuilder setWhereExtended(String whereExtended)
	{
		this.whereExtended = whereExtended;
		return this;
	}

	public String getWhereExtended()
	{
		return whereExtended;
	}

	public FindPanelBuilder setQuery(MQuery query)
	{
		this.query = query;
		return this;
	}

	public MQuery getQuery()
	{
		return query;
	}

	public FindPanelBuilder setFindFields(GridField[] findFields)
	{
		this.findFields = findFields;
		return this;
	}

	public GridField[] getFindFields()
	{
		if (findFields == null)
		{
			final int tabNo = 0;
			findFields = GridField.createSearchFields(Env.getCtx(), getTargetWindowNo(), tabNo, getAD_Tab_ID(), getTemplateTabId());
		}

		return findFields;
	}

	public FindPanelBuilder setMinRecords(int minRecords)
	{
		this.minRecords = minRecords;
		return this;
	}

	public int getMinRecords()
	{
		return minRecords;
	}

	public FindPanelBuilder setSmall(boolean small)
	{
		this.small = small;
		return this;
	}

	public boolean isSmall()
	{
		return small;
	}

	/**
	 * Sets grid controller to be used.
	 * 
	 * NOTE: this method will configure ALL other parameters, so make sure, if you want to tune some of them, you are calling the setters AFTER this method.
	 * 
	 * @param gridController
	 */
	public FindPanelBuilder setGridController(final GridController gridController)
	{
		Check.assumeNotNull(gridController, "gridController not null");
		this.gridController = gridController;

		setParentFrame(SwingUtils.getFrame(gridController));

		final GridTab gridTab = gridController.getMTab();
		setGridTab(gridTab);

		return this;
	}

	public GridController getGridController()
	{
		return gridController;
	}

	/**
	 * Initialize the parameters based on given gridTab.
	 * 
	 * NOTE: this method will configure ALL other parameters, so make sure, if you want to tune some of them, you are calling the setters AFTER this method.
	 * 
	 * @param gridTab
	 */
	public FindPanelBuilder setGridTab(@NonNull final GridTab gridTab)
	{
		this.gridTab = gridTab;

		final int targetWindowNo = gridTab.getWindowNo();
		setTitle(gridTab.getName()); // title
		setAD_Tab_ID(gridTab.getAD_Tab_ID());
		setTemplateTabId(gridTab.getTemplateTabId());
		setMaxQueryRecordsPerTab(gridTab.getMaxQueryRecords());
		setTargetWindowNo(targetWindowNo);
		setTargetTabNo(gridTab.getTabNo()); // metas-2009_0021_AP1_G113
		setAD_Table_ID(gridTab.getAD_Table_ID());
		setTableName(gridTab.getTableName());
		setWhereExtended(gridTab.getWhereExtended());
		setQuery(gridTab.getQuery());
		setSearchPanelCollapsed(gridTab.isSearchCollapsed());

		// NOTE: not setting them because they will be rendered on request
		// final GridField[] findFields = GridField.createFields(Env.getCtx(), targetWindowNo, 0, adTabId);
		// setFindFields(findFields);

		return this;
	}

	public GridTab getGridTab()
	{
		return gridTab;
	}

	/**
	 * Sets if the find panel will be embedded in the window.
	 */
	public FindPanelBuilder setEmbedded(final boolean embedded)
	{
		this.embedded = embedded;
		return this;
	}

	/** @return true if the find panel will be embedded in the window */
	public boolean isEmbedded()
	{
		return this.embedded;
	}

	public FindPanelBuilder setHideStatusBar(boolean hideStatusBar)
	{
		this.hideStatusBar = hideStatusBar;
		return this;
	}

	public boolean isHideStatusBar()
	{
		return hideStatusBar;
	}

	public FindPanelBuilder setSearchPanelCollapsed(final boolean searchPanelCollapsed)
	{
		this.searchPanelCollapsed = searchPanelCollapsed;
		return this;
	}

	public boolean isSearchPanelCollapsed()
	{
		return searchPanelCollapsed;
	}

	public FindPanelBuilder setMaxQueryRecordsPerTab(final int maxQueryRecordsPerTab)
	{
		this.maxQueryRecordsPerTab = maxQueryRecordsPerTab;
		return this;
	}

	public int getMaxQueryRecordsPerTab()
	{
		if (maxQueryRecordsPerTab != null)
		{
			return maxQueryRecordsPerTab;
		}
		else if (gridTab != null)
		{
			return gridTab.getMaxQueryRecords();
		}

		return 0;
	}
}
