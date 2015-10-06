package de.metas.handlingunits.client.editor.attr.view.swing;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTable;

import de.metas.handlingunits.client.editor.attr.model.HUAttributeSetModel;

public class HUAttributeSetEditor extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 83667750525646193L;

	private final JXTable table = new JXTable();
	private HUAttributeSetModel model = null;

	private final PropertyChangeListener model2viewSync = new PropertyChangeListener()
	{

		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			updateFromModel();
		}
	};

	public HUAttributeSetEditor()
	{
		super();

		initComponents();
		initLayout();
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		setReadonly(!enabled);
	}
	
	public void setReadonly(boolean readonly)
	{
		super.setEnabled(!readonly);
		table.setEditable(!readonly);
	}

	private void initComponents()
	{
		table.setColumnFactory(new HUAttributeColumnFactory());
		table.setAutoCreateColumnsFromModel(true);
		// table.getTableHeader().setVisible(false);
	}

	private void initLayout()
	{
		final BorderLayout layout = new BorderLayout();

		setLayout(layout);

		final JScrollPane tableScroll = new JScrollPane(table);

		this.add(tableScroll, BorderLayout.CENTER);
	}

	public void setModel(final HUAttributeSetModel model)
	{
		final HUAttributeSetModel modelOld = getModel();
		
		if (modelOld == model)
		{
			// nothing changed
			return;
		}
		
		if (modelOld != null)
		{
			modelOld.getPropertyChangeSupport().removePropertyChangeListener(model2viewSync);
		}
		
		table.setModel(model);
		this.model = model;
		
		if (model != null)
		{
			model.getPropertyChangeSupport().addPropertyChangeListener(model2viewSync);
			updateFromModel();
		}
	}

	public HUAttributeSetModel getModel()
	{
		return model;
	}

	private void updateFromModel()
	{
		final HUAttributeSetModel model = getModel();
		if (model == null)
		{
			// shall not happen
			return;
		}

		setReadonly(model.isReadonly());
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

	public void stopEditing()
	{
		if (table != null && table.getCellEditor() != null)
		{
			table.getCellEditor().stopCellEditing();
		}
	}

}
