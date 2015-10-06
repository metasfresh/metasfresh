package de.metas.handlingunits.client.editor.view.swing;

import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.compiere.grid.ed.VNumber;

public class NumberCellValueEditor extends AbstractCellEditor implements TableCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5690534395420854145L;

	public static final int MAXIMUM_FRACTION_DIGITS_DEFAULT = 2; // TODO: HARDCODED
	private final VNumber editor;

	/**
	 * Assumes default maximum decimal places
	 */
	public NumberCellValueEditor()
	{
		this(NumberCellValueEditor.MAXIMUM_FRACTION_DIGITS_DEFAULT);
	}

	/**
	 * @param maximumFractionDigits maximum decimal places
	 */
	public NumberCellValueEditor(final int maximumFractionDigits)
	{
		super();
		editor = new VNumber();

		final DecimalFormat decimalFormat = editor.getDecimalFormat();
		decimalFormat.setMaximumFractionDigits(maximumFractionDigits);
		editor.setDecimalFormat(decimalFormat);
	}

	@Override
	public Object getCellEditorValue()
	{
		return editor.getValue();
	}

	@Override
	public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int column)
	{
		editor.setValue(value);
		return editor;
	}
}
