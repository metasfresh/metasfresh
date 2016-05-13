package de.metas.ui.web.vaadin.window.descriptor;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTabLayoutMode;
import org.compiere.util.DisplayType;

/*
 * #%L
 * de.metas.ui.web.vaadin
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DataFieldPropertyDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String caption;
	private final String columnName;
	private final int displayType;
	private final Class<?> valueClass;
	private final boolean displayable;
	private final int AD_Column_ID;
	private final int AD_Reference_Value_ID;
	private final int fieldLength;
	private final int includedTabId;
	private DataRowDescriptor includedTabDescriptor; // will be set later
	private final boolean encrypted;
	private final boolean key;
	private final boolean parentLink;

	// Mandatory
	private final boolean mandatory;
	private ILogicExpression mandatoryLogic;

	// Access Info
	private final boolean readOnly;
	private ILogicExpression readOnlyLogic;
	private final boolean updateable;
	private final boolean alwaysUpdateable;

	
	// Display Info
	private final boolean displayedInForm;
	private final boolean displayedInGrid;
	private final ILogicExpression displayLogic;
	private final int seqNoForm;
	private final int seqNoGrid;

	// SQL
	private final boolean sqlColumn;
	private final String sqlColumnCode;
	
	private final DataFieldLookupDescriptor lookupDescriptor;

	public DataFieldPropertyDescriptor(Builder builder)
	{
		super();
		this.caption = builder.caption;
		this.columnName = builder.columnName;
		this.displayType = builder.displayType;
		this.valueClass = builder.valueClass;
		this.AD_Column_ID = builder.AD_Column_ID;
		this.AD_Reference_Value_ID = builder.AD_Reference_Value_ID;
		this.fieldLength = builder.fieldLength;
		this.includedTabId = builder.includedTabId;
		this.encrypted = builder.encrypted;
		this.key = builder.key;
		this.parentLink = builder.parentLink;
		
		// Mandatory
		this.mandatory = builder.mandatory;
		this.mandatoryLogic = builder.mandatoryLogic;
		
		// Access Info
		this.readOnly = builder.readOnly;
		this.readOnlyLogic = builder.readOnlyLogic;
		this.updateable = builder.updateable;
		this.alwaysUpdateable = builder.alwaysUpdateable;

		// Display Info
		this.displayable = builder.displayable;
		this.displayedInForm = builder.displayedInForm;
		this.displayedInGrid = builder.displayedInGrid;
		this.displayLogic = builder.displayLogic;
		this.seqNoForm = builder.seqNoForm;
		this.seqNoGrid = builder.seqNoGrid;
		
		// SQL
		this.sqlColumn = builder.sqlColumn;
		this.sqlColumnCode = builder.sqlColumnCode;
		
		this.lookupDescriptor = builder.getLookupDescriptor();
	}

	public String getCaption()
	{
		return caption;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public int getDisplayType()
	{
		return displayType;
	}

	public Class<?> getValueClass()
	{
		return valueClass;
	}

	public boolean isDisplayable()
	{
		return displayable;
	}

	public boolean isMandatory()
	{
		return mandatory;
	}
	
	public ILogicExpression getMandatoryLogic()
	{
		return mandatoryLogic;
	}
	
	public int getAD_Column_ID()
	{
		return AD_Column_ID;
	}

	public int getAD_Reference_Value_ID()
	{
		return AD_Reference_Value_ID;
	}

	public boolean isLongTextField()
	{
		// TODO: find out the actual logic and refactor
		return displayType == DisplayType.TextLong
				|| (displayType == DisplayType.Text && fieldLength > 40);

	}

	final int getIncludedTabId()
	{
		return includedTabId;
	}

	public DataRowDescriptor getIncludedTabDescriptor()
	{
		return includedTabDescriptor;
	}

	final void setIncludedTabDescriptor(DataRowDescriptor includedTabDescriptor)
	{
		this.includedTabDescriptor = includedTabDescriptor;
	}

	public boolean isKey()
	{
		return key;
	}

	public boolean isParentLink()
	{
		return parentLink;
	}

	public boolean isEncrypted()
	{
		return encrypted;
	}

	public boolean isSqlColumn()
	{
		return sqlColumn;
	}

	public String getSqlColumnCode()
	{
		return sqlColumnCode;
	}
	
	public DataFieldLookupDescriptor getLookupDescriptor()
	{
		return lookupDescriptor;
	}
	
	public boolean isDisplayedInForm()
	{
		return displayedInForm;
	}

	public boolean isDisplayedInGrid()
	{
		return displayedInGrid;
	}
	
	public ILogicExpression getDisplayLogic()
	{
		return displayLogic;
	}

	public int getSeqNoForm()
	{
		return seqNoForm;
	}

	public int getSeqNoGrid()
	{
		return seqNoGrid;
	}
	
	public boolean isReadOnly()
	{
		return readOnly;
	}
	
	public ILogicExpression getReadOnlyLogic()
	{
		return readOnlyLogic;
	}

	public boolean isUpdateable()
	{
		return updateable;
	}

	public boolean isAlwaysUpdateable()
	{
		return alwaysUpdateable;
	}





	public static final class Builder
	{
		private String caption;
		private String columnName;
		private int displayType;
		private Class<?> valueClass;
		private int AD_Column_ID;
		private int AD_Reference_Value_ID;
		private int fieldLength;
		private int includedTabId;
		private boolean encrypted;
		private boolean key;
		private boolean parentLink;

		private boolean readOnly;
		private ILogicExpression readOnlyLogic;
		private boolean updateable;
		private boolean alwaysUpdateable;
		
		private boolean mandatory;
		private ILogicExpression mandatoryLogic;

		private boolean displayable;
		private boolean displayedInForm;
		private boolean displayedInGrid;
		private ILogicExpression displayLogic;
		private int seqNoForm;
		private int seqNoGrid;

		// SQL
		private boolean sqlColumn;
		private String sqlColumnCode;
		
		private DataFieldLookupDescriptor lookupDescriptor;
		
		private Builder()
		{
			super();
		}

		public DataFieldPropertyDescriptor build()
		{
			return new DataFieldPropertyDescriptor(this);
		}

		public Builder initFrom(final GridFieldVO gridFieldVO)
		{
			this.caption = gridFieldVO.getHeader();
			this.columnName = gridFieldVO.getColumnName();
			this.displayType = gridFieldVO.getDisplayType();
			this.AD_Column_ID = gridFieldVO.getAD_Column_ID();
			this.AD_Reference_Value_ID = gridFieldVO.getAD_Reference_Value_ID();
			this.fieldLength = gridFieldVO.FieldLength;
			this.includedTabId = gridFieldVO.Included_Tab_ID;
			this.encrypted = gridFieldVO.IsEncryptedColumn;
			this.key = gridFieldVO.IsKey;
			this.parentLink = gridFieldVO.IsParent;
			
			//
			// Access Info
			this.readOnly = gridFieldVO.IsReadOnly;
			this.readOnlyLogic = gridFieldVO.getReadOnlyLogic();
			this.updateable = gridFieldVO.IsUpdateable;
			this.alwaysUpdateable = gridFieldVO.IsAlwaysUpdateable;
			//
			this.mandatory = gridFieldVO.isMandatory();
			this.mandatoryLogic = gridFieldVO.getMandatoryLogic();

			//
			// Display info
			this.displayedInForm = gridFieldVO.isDisplayed(GridTabLayoutMode.SingleRowLayout);
			this.displayedInGrid = gridFieldVO.isDisplayed(GridTabLayoutMode.Grid);
			this.displayable = displayedInForm;
			this.displayLogic = gridFieldVO.getDisplayLogic();
			this.seqNoForm = gridFieldVO.getSeqNo();
			this.seqNoGrid = gridFieldVO.getSeqNoGrid();

			// SQL
			this.sqlColumn = true;
			this.sqlColumnCode = gridFieldVO.getColumnSQL(false);

			//
			// Lookup
			if (DisplayType.isLookup(displayType))
			{
				this.lookupDescriptor = new DataFieldLookupDescriptor(displayType, columnName, AD_Reference_Value_ID);
			}
			else
			{
				this.lookupDescriptor = null;
			}

			//
			// Value's class
			if (lookupDescriptor != null)
			{
				this.valueClass = lookupDescriptor.getValueClass();
			}
			else
			{
				this.valueClass = DisplayType.getClass(displayType, true);
			}

			return this;
		}
		
		public String getColumnName()
		{
			return columnName;
		}
		
		public boolean isKey()
		{
			return key;
		}
		
		public Builder setKey(boolean key)
		{
			this.key = key;
			return this;
		}
		
		public boolean isParentLink()
		{
			return parentLink;
		}
		
		private DataFieldLookupDescriptor getLookupDescriptor()
		{
			if (DisplayType.isLookup(displayType))
			{
				return new DataFieldLookupDescriptor(displayType, columnName, AD_Reference_Value_ID);
			}
			else
			{
				return null;
			}
		}
	}
}
