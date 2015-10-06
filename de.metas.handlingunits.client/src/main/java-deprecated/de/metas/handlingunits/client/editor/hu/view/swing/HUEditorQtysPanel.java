package de.metas.handlingunits.client.editor.hu.view.swing;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTable;

import de.metas.handlingunits.client.editor.hu.model.HUEditorQtysModel;

/**
 * Displays Product/Qtys summary
 * 
 * @author tsa
 * 
 */
public class HUEditorQtysPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2692293086005768291L;

	private final JXTable table = new JXTable();
	private HUEditorQtysModel model = null;

	public HUEditorQtysPanel()
	{
		this(null);
	}

	public HUEditorQtysPanel(final HUEditorQtysModel model)
	{
		super();

		initComponents();
		initLayout();

		setModel(model);
	}

	private void initComponents()
	{
		table.setAutoCreateColumnsFromModel(true);
	}

	private void initLayout()
	{
		final BorderLayout layout = new BorderLayout();

		setLayout(layout);

		final JScrollPane tableScroll = new JScrollPane(table);

		this.add(tableScroll, BorderLayout.CENTER);
	}

	public void setModel(final HUEditorQtysModel model)
	{
		table.setModel(model);
		this.model = model;
	}

	public HUEditorQtysModel getModel()
	{
		return model;
	}

	/**
	 * Please do not use it. We introduced it only for testing purposes.
	 * 
	 * @return underlying table
	 */
	public JXTable getTable()
	{
		return table;
	}
}
