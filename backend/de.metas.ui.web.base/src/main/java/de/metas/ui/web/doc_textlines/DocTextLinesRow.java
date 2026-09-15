package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableMap;
import de.metas.doctextline.DocTextLineId;
import de.metas.doctextline.TextLineScope;
import de.metas.order.OrderLineId;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.model.X_C_Doc_TextLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * A single row of {@link DocTextLinesView}: either an order's article line ({@link RowType#ARTICLE}, read-only,
 * for orientation) or one of the order's {@code C_Doc_TextLine} rows ({@link RowType#TEXT}). Both row types are
 * loaded and merged by {@link DocTextLinesRowsLoader} per DESIGN.md § D-C / D-E; this class only carries the
 * per-row data and the per-row editability (article rows never editable, text rows editable once a future task
 * wires {@code IEditableRowsData} on top of {@link DocTextLinesRows}).
 */
@ToString(exclude = "values")
public final class DocTextLinesRow implements IViewRow
{
	public static DocTextLinesRow cast(final IViewRow row)
	{
		return (DocTextLinesRow)row;
	}

	public enum RowType
	{
		ARTICLE,
		TEXT
	}

	private static final String ROWID_PREFIX_ARTICLE = "A";
	private static final String ROWID_PREFIX_TEXT = "T";

	public static final String FIELD_Line = "line";
	@ViewColumn(seqNo = 10, fieldName = FIELD_Line, captionKey = "Line", widgetType = DocumentFieldWidgetType.Number, widgetSize = WidgetSize.Small)
	@Getter
	private final BigDecimal line;

	public static final String FIELD_Product = "product";
	@ViewColumn(seqNo = 20, fieldName = FIELD_Product, captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup)
	@Getter
	private final LookupValue product;

	public static final String FIELD_Qty = "qty";
	@ViewColumn(seqNo = 30, fieldName = FIELD_Qty, captionKey = "QtyOrdered", widgetType = DocumentFieldWidgetType.Quantity, widgetSize = WidgetSize.Small)
	@Getter
	private final BigDecimal qty;

	public static final String FIELD_TextLine = "textLine";
	@ViewColumn(seqNo = 40, fieldName = FIELD_TextLine, captionKey = "TextLine", widgetType = DocumentFieldWidgetType.LongText, editor = ViewEditorRenderMode.ALWAYS)
	@Getter
	private final String textLine;

	public static final String FIELD_TextLineScope = "textLineScope";
	@ViewColumn(seqNo = 50, fieldName = FIELD_TextLineScope, captionKey = "TextLineScope", widgetType = DocumentFieldWidgetType.List,
			listReferenceId = X_C_Doc_TextLine.TEXTLINESCOPE_AD_Reference_ID, widgetSize = WidgetSize.Small, editor = ViewEditorRenderMode.ALWAYS)
	@Getter
	private final TextLineScope textLineScope;

	//
	//
	//

	private final DocumentId rowId;
	@Getter
	private final RowType rowType;

	/** Set only for {@link RowType#ARTICLE} rows. */
	@Getter
	@Nullable
	private final OrderLineId orderLineId;

	/** Set only for {@link RowType#TEXT} rows. */
	@Getter
	@Nullable
	private final DocTextLineId textLineId;

	private final ViewRowFieldNameAndJsonValuesHolder<DocTextLinesRow> values;
	private final ImmutableMap<String, ViewEditorRenderMode> editorRenderModeByFieldName;

	@Builder
	private DocTextLinesRow(
			@NonNull final RowType rowType,
			@NonNull final BigDecimal line,
			@Nullable final OrderLineId orderLineId,
			@Nullable final LookupValue product,
			@Nullable final BigDecimal qty,
			@Nullable final DocTextLineId textLineId,
			@Nullable final String textLine,
			@Nullable final TextLineScope textLineScope)
	{
		this.rowType = rowType;
		this.line = line;
		this.orderLineId = orderLineId;
		this.product = product;
		this.qty = qty;
		this.textLineId = textLineId;
		this.textLine = textLine;
		this.textLineScope = textLineScope;

		this.rowId = rowType == RowType.ARTICLE ? articleRowId(orderLineId) : textRowId(textLineId);

		this.values = ViewRowFieldNameAndJsonValuesHolder.newInstance(DocTextLinesRow.class);
		this.editorRenderModeByFieldName = buildEditorRenderModeByFieldName(rowType);
	}

	private static ImmutableMap<String, ViewEditorRenderMode> buildEditorRenderModeByFieldName(@NonNull final RowType rowType)
	{
		// article rows are read-only (for orientation/positioning only); text rows are the editable half of
		// the merged view (DESIGN.md § D-E) -- the actual patching is wired by a later task.
		final ViewEditorRenderMode textFieldsMode = rowType == RowType.TEXT ? ViewEditorRenderMode.ALWAYS : ViewEditorRenderMode.NEVER;
		return ImmutableMap.of(
				FIELD_TextLine, textFieldsMode,
				FIELD_TextLineScope, textFieldsMode);
	}

	public static DocumentId articleRowId(@NonNull final OrderLineId orderLineId)
	{
		return DocumentId.ofString(ROWID_PREFIX_ARTICLE + orderLineId.getRepoId());
	}

	public static DocumentId textRowId(@NonNull final DocTextLineId textLineId)
	{
		return DocumentId.ofString(ROWID_PREFIX_TEXT + textLineId.getRepoId());
	}

	public boolean isArticleLine()
	{
		return rowType == RowType.ARTICLE;
	}

	public boolean isTextLine()
	{
		return rowType == RowType.TEXT;
	}

	@Override
	public DocumentId getId()
	{
		return rowId;
	}

	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Nullable
	@Override
	public DocumentPath getDocumentPath()
	{
		return null;
	}

	@Override
	public Set<String> getFieldNames()
	{
		return values.getFieldNames();
	}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues()
	{
		return values.get(this);
	}

	@Override
	public Map<String, ViewEditorRenderMode> getViewEditorRenderModeByFieldName()
	{
		return editorRenderModeByFieldName;
	}
}
