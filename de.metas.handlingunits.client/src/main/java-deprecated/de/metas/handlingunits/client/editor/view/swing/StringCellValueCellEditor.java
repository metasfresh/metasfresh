package de.metas.handlingunits.client.editor.view.swing;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

public class StringCellValueCellEditor extends DefaultCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7484431605561284120L;

	public StringCellValueCellEditor()
	{
		super(new JTextField());
	}
}
