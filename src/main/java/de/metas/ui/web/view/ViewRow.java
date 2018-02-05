package de.metas.ui.web.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import lombok.NonNull;
import lombok.ToString;

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

public final class ViewRow implements IViewRow
{
	public static final Builder builder(final WindowId windowId)
	{
		return new Builder(windowId);
	}

	public static final ViewRow cast(final IViewRow row)
	{
		return (ViewRow)row;
	}

	public static enum DefaultRowType implements IViewRowType
	{
		Row
		{
			@Override
			public String getName()
			{
				// FIXME: use some proper name and icon
				return HUEditorRowType.LU.getName();
			}
		},
		Line
		{
			@Override
			public String getName()
			{
				// FIXME: use some proper name and icon
				return HUEditorRowType.TU.getName();
			}
		},
	}

	private final DocumentPath documentPath;
	private final DocumentId rowId;
	private final DocumentId parentRowId;
	private final IViewRowType type;
	private final boolean processed;

	private final Map<String, Object> values;

	private final List<IViewRow> includedRows;

	private ViewRow(final Builder builder)
	{
		documentPath = builder.getDocumentPath();
		rowId = documentPath.getDocumentId();
		parentRowId = builder.getParentRowId();
		type = builder.getType();
		processed = builder.isProcessed();

		values = ImmutableMap.copyOf(builder.getValues());

		includedRows = builder.buildIncludedRows();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", rowId)
				.add("type", type)
				.add("parentId", parentRowId)
				.add("values", values)
				.add("includedRows.count", includedRows.size())
				.add("processed", processed)
				.toString();
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	@Override
	public DocumentId getId()
	{
		return rowId;
	}

	public DocumentId getParentId()
	{
		return parentRowId;
	}

	@Override
	public IViewRowType getType()
	{
		return type;
	}

	@Override
	public boolean isProcessed()
	{
		return processed;
	}
	
	@Override
	public Map<String, Object> getFieldNameAndJsonValues()
	{
		return values;
	}

	@Override
	public boolean hasAttributes()
	{
		return false;
	}

	@Override
	public IViewRowAttributes getAttributes()
	{
		throw new EntityNotFoundException("row does not support attributes");
	}

	@Override
	public List<IViewRow> getIncludedRows()
	{
		return includedRows;
	}

	public static final DocumentId convertToRowId(@NonNull final Object jsonRowIdObj)
	{
		if (jsonRowIdObj instanceof DocumentId)
		{
			return (DocumentId)jsonRowIdObj;
		}
		else if (jsonRowIdObj instanceof Integer)
		{
			return DocumentId.of((Integer)jsonRowIdObj);
		}
		else if (jsonRowIdObj instanceof String)
		{
			return DocumentId.of(jsonRowIdObj.toString());
		}
		else if (jsonRowIdObj instanceof JSONLookupValue)
		{
			// case: usually this is happening when a view's column which is Lookup is also marked as KEY.
			final JSONLookupValue jsonLookupValue = (JSONLookupValue)jsonRowIdObj;
			return DocumentId.of(jsonLookupValue.getKey());
		}
		else
		{
			throw new IllegalArgumentException("Cannot convert id '" + jsonRowIdObj + "' (" + jsonRowIdObj.getClass() + ") to integer");
		}
	}

	//
	//
	//
	//
	//
	@ToString
	public static final class Builder
	{
		private final WindowId windowId;
		private DocumentId rowId;
		private DocumentId _rowIdEffective; // lazy
		private DocumentId parentRowId;
		private IViewRowType type;
		private Boolean processed;
		private final Map<String, Object> values = new LinkedHashMap<>(); // preserve the insertion order of fields
		private List<IViewRow> includedRows = null;

		private Builder(@NonNull final WindowId windowId)
		{
			this.windowId = windowId;
		}

		public ViewRow build()
		{
			return new ViewRow(this);
		}

		private DocumentPath getDocumentPath()
		{
			final DocumentId documentId = getRowId();
			return DocumentPath.rootDocumentPath(windowId, documentId);
		}

		private Builder setRowId(final DocumentId rowId)
		{
			this.rowId = rowId;
			_rowIdEffective = null;
			return this;
		}

		public Builder setRowIdFromObject(final Object jsonRowIdObj)
		{
			setRowId(convertToRowId(jsonRowIdObj));
			return this;
		}

		/** @return view row ID; never null */
		public DocumentId getRowId()
		{
			if (_rowIdEffective == null)
			{
				if (rowId == null)
				{
					throw new IllegalStateException("No rowId was provided for " + this);
				}

				if (isRootRow())
				{
					_rowIdEffective = rowId;
				}
				else
				{
					// NOTE: we have to do this because usually, the root row can have the same ID as one of the included rows,
					// because the root/aggregated rows are build on demand and they don't really exist in database.
					// Also see https://github.com/metasfresh/metasfresh-webui-frontend/issues/835#issuecomment-307783959
					_rowIdEffective = DocumentId.ofString(DocumentId.DOCUMENT_ID_PREFIX + rowId.toJson());
				}
			}
			
			return _rowIdEffective;
		}

		public Builder setParentRowId(final DocumentId parentRowId)
		{
			this.parentRowId = parentRowId;
			_rowIdEffective = null;
			return this;
		}

		private DocumentId getParentRowId()
		{
			return parentRowId;
		}

		public boolean isRootRow()
		{
			return getParentRowId() == null;
		}

		private IViewRowType getType()
		{
			return type;
		}

		public Builder setType(final IViewRowType type)
		{
			this.type = type;
			return this;
		}

		public Builder setProcessed(final boolean processed)
		{
			this.processed = processed;
			return this;
		}

		private boolean isProcessed()
		{
			if (processed == null)
			{
				// NOTE: don't take the "Processed" field if any, because in frontend we will end up with a lot of grayed out completed sales orders, for example.
				// return DisplayType.toBoolean(values.getOrDefault("Processed", false));
				return false;
			}
			else
			{
				return processed.booleanValue();
			}
		}

		public Builder putFieldValue(final String fieldName, final Object jsonValue)
		{
			if (jsonValue == null)
			{
				values.remove(fieldName);
			}
			else
			{
				values.put(fieldName, jsonValue);
			}

			return this;
		}

		private Map<String, Object> getValues()
		{
			return values;
		}
		
		public Object getFieldValue(final String fieldName)
		{
			return values.get(fieldName);
		}

		public Builder addIncludedRow(final IViewRow includedRow)
		{
			if (includedRows == null)
			{
				includedRows = new ArrayList<>();
			}

			includedRows.add(includedRow);

			return this;
		}

		private List<IViewRow> buildIncludedRows()
		{
			if (includedRows == null || includedRows.isEmpty())
			{
				return ImmutableList.of();
			}

			return ImmutableList.copyOf(includedRows);
		}

	}
}
