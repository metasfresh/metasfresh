package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableMap;
import de.metas.doctextline.DocTextLine;
import de.metas.doctextline.DocTextLineId;
import de.metas.doctextline.TextLineScope;
import de.metas.i18n.AdMessageKey;
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
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Doc_TextLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * A single row of {@link DocTextLinesView}: either an order's article line ({@link RowType#ARTICLE}, read-only,
 * for orientation) or one of the order's {@code C_Doc_TextLine} rows ({@link RowType#TEXT}, inline-editable via
 * {@link #withChanges}). Both row types are loaded and merged by {@link DocTextLinesRowsLoader}; this class
 * carries the per-row data and the per-row editability.
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

	private static final AdMessageKey MSG_ArticleLineCannotBeEditedHere = AdMessageKey.of("DocTextLines_ArticleLineCannotBeEditedHere");

	private static final String ROWID_PREFIX_ARTICLE = "A";
	private static final String ROWID_PREFIX_TEXT = "T";

	/**
	 * The row's real position. Load-bearing: the move/insert arithmetic, the midpoint computation and the
	 * reload de-duplication all read it. It is deliberately NOT rendered -- see {@link #FIELD_LineDisplay}.
	 */
	@Getter
	private final BigDecimal line;

	/**
	 * What the user sees in the "Zeile Nr." column: the article line's own number, and NOTHING for a text row.
	 * A text row's real position is a midpoint (e.g. {@code 15}, {@code 10.5}) or, for a whole-document line
	 * with no run on the document, a large negative head offset -- none of which means anything to the user,
	 * and all of which looked like noise beside the article numbers. {@link #line} keeps the real value for
	 * the arithmetic; only the rendering is suppressed.
	 */
	public static final String FIELD_LineDisplay = "line";
	@ViewColumn(seqNo = 10, fieldName = FIELD_LineDisplay, captionKey = "Line", widgetType = DocumentFieldWidgetType.Number, widgetSize = WidgetSize.Small)
	@Getter
	private final BigDecimal lineDisplay;

	/** Kept so existing references to the rendered column's name keep working. */
	public static final String FIELD_Line = FIELD_LineDisplay;

	public static final String FIELD_Product = "product";
	@ViewColumn(seqNo = 20, fieldName = FIELD_Product, captionKey = "M_Product_ID", widgetType = DocumentFieldWidgetType.Lookup)
	@Getter
	private final LookupValue product;

	public static final String FIELD_Qty = "qty";
	@ViewColumn(seqNo = 30, fieldName = FIELD_Qty, captionKey = "QtyOrdered", widgetType = DocumentFieldWidgetType.Quantity, widgetSize = WidgetSize.Small)
	@Getter
	private final BigDecimal qty;

	public static final String FIELD_TextLine = "textLine";
	// NO layout-level `editor = ALWAYS` here on purpose: the frontend ORs the layout mode with the per-row one
	// (`isCellEditable` in frontend/src/utils/tableHelpers.js), so a layout-level ALWAYS makes every row editable
	// and the per-row NEVER for ARTICLE rows can never win. Editability comes solely from
	// #buildEditorRenderModeByFieldName, which is per-row. ExtraLarge so the text gets the width it needs.
	@ViewColumn(seqNo = 40, fieldName = FIELD_TextLine, captionKey = "TextLine", widgetType = DocumentFieldWidgetType.LongText, widgetSize = WidgetSize.ExtraLarge)
	@Getter
	private final String textLine;

	public static final String FIELD_TextLineScope = "textLineScope";
	// Same as FIELD_TextLine above: no layout-level `editor = ALWAYS`, so the per-row NEVER holds on ARTICLE rows.
	@ViewColumn(seqNo = 50, fieldName = FIELD_TextLineScope, captionKey = "TextLineScope", widgetType = DocumentFieldWidgetType.List,
			listReferenceId = X_C_Doc_TextLine.TEXTLINESCOPE_AD_Reference_ID, widgetSize = WidgetSize.Small)
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

	@Builder(toBuilder = true)
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
		this.lineDisplay = rowType == RowType.ARTICLE ? line : null;
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
		// the merged view -- see #withChanges for the actual patching.
		final ViewEditorRenderMode textFieldsMode = rowType == RowType.TEXT ? ViewEditorRenderMode.ALWAYS : ViewEditorRenderMode.NEVER;
		return ImmutableMap.of(
				FIELD_TextLine, textFieldsMode,
				FIELD_TextLineScope, textFieldsMode);
	}

	public static DocTextLinesRow ofTextLine(@NonNull final DocTextLine textLine)
	{
		return DocTextLinesRow.builder()
				.rowType(RowType.TEXT)
				.line(textLine.getLine())
				.textLineId(textLine.getId())
				.textLine(textLine.getTextLine())
				.textLineScope(textLine.getScope())
				.build();
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

	/**
	 * Applies a user's inline edit. Article rows are never editable -- the row itself refuses the change
	 * rather than silently ignoring it.
	 * <p>
	 * A {@code null} field on {@code userChanges} means "not touched by this patch" -- so an edit to an empty
	 * text is represented as {@code textLine=""}, which is non-null and is therefore applied (an empty text
	 * line is legal and prints as a blank line).
	 */
	public DocTextLinesRow withChanges(@NonNull final DocTextLineRowUserChangeRequest userChanges)
	{
		if (!isTextLine())
		{
			throw new AdempiereException(MSG_ArticleLineCannotBeEditedHere)
					.setParameter("rowId", getId());
		}

		final DocTextLinesRowBuilder rowBuilder = toBuilder();
		if (userChanges.getTextLine() != null)
		{
			rowBuilder.textLine(userChanges.getTextLine());
		}
		if (userChanges.getTextLineScope() != null)
		{
			rowBuilder.textLineScope(userChanges.getTextLineScope());
		}
		return rowBuilder.build();
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
	public ImmutableMap<String, ViewEditorRenderMode> getViewEditorRenderModeByFieldName()
	{
		return editorRenderModeByFieldName;
	}
}
