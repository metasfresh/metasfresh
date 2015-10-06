package de.metas.handlingunits.client.editor.attr.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.X_M_Attribute;

import de.metas.handlingunits.attribute.api.IHUAttributeSet;
import de.metas.handlingunits.attribute.api.IHUAttributesDAO;
import de.metas.handlingunits.client.editor.attr.model.action.SetAttributeValueAction;
import de.metas.handlingunits.client.editor.command.model.action.IAction;
import de.metas.handlingunits.client.editor.command.model.history.IHistory;
import de.metas.handlingunits.client.editor.command.model.history.impl.NullHistory;

public class HUAttributeSetModel extends AbstractTableModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8149953810450743388L;

	private static final int COLUMN_COUNT = 2;
	public static final int COLUMN_PropertyName = 0;
	public static final int COLUMN_PropertyValue = 1;

	private final IHUAttributeSet attributeSet;
	private IHistory actionExecutor = new NullHistory();

	private boolean readonly = false;

	public static final String PROPERTY_Readonly = "Readonly";
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public HUAttributeSetModel(final IHUAttributeSet attributeSet)
	{
		this(attributeSet, false); // readonly = false
	}

	public HUAttributeSetModel(final IHUAttributeSet attributeSet, final boolean readonly)
	{
		super();

		Check.assumeNotNull(attributeSet, "attributeSet not null");
		this.attributeSet = attributeSet;

		setReadonly(readonly);
	}

	public void setActionExecutor(final IHistory actionExecutor)
	{
		Check.assumeNotNull(actionExecutor, "actionExecutor not null");
		this.actionExecutor = actionExecutor;
	}

	@Override
	public String getColumnName(final int columnIndex)
	{
		if (columnIndex == HUAttributeSetModel.COLUMN_PropertyName)
		{
			return "Name";
		}
		else if (columnIndex == HUAttributeSetModel.COLUMN_PropertyValue)
		{
			return "Value";
		}
		else
		{
			throw new IllegalArgumentException("Invalid columnIndex: " + columnIndex);
		}
	}

	@Override
	public int getRowCount()
	{
		return attributeSet.getAttributes().size();
	}

	@Override
	public int getColumnCount()
	{
		return HUAttributeSetModel.COLUMN_COUNT;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex)
	{
		final I_M_Attribute attribute = getM_Attribute(rowIndex);

		if (columnIndex == HUAttributeSetModel.COLUMN_PropertyName)
		{
			return attribute.getName();
		}
		else if (columnIndex == HUAttributeSetModel.COLUMN_PropertyValue)
		{
			return getValue(attribute);
		}
		else
		{
			throw new IllegalArgumentException("Invalid columnIndex: " + columnIndex);
		}
	}

	public I_M_Attribute getM_Attribute(final int rowIndex)
	{
		final List<I_M_Attribute> attributes = new ArrayList<I_M_Attribute>(attributeSet.getAttributes());
		return attributes.get(rowIndex);
	}

	public Object getValue(final I_M_Attribute attribute)
	{
		Object value = attributeSet.getValue(attribute);
		if (value == null)
		{
			return null;
		}

		if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attribute.getAttributeValueType()))
		{
			value = Services.get(IHUAttributesDAO.class).retrieveAttributeValue(attribute, value.toString());
		}

		return value;
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex)
	{
		if (columnIndex != HUAttributeSetModel.COLUMN_PropertyValue)
		{
			return false;
		}

		// Static (not instance) attributes are readonly
		final I_M_Attribute attribute = getM_Attribute(rowIndex);
		if (!attribute.isInstanceAttribute())
		{
			return false;
		}

		if (attributeSet.isPropagatedValue(attribute))
		{
			return false;
		}

		return true;
	}

	@Override
	public void setValueAt(final Object value, final int rowIndex, final int columnIndex)
	{
		if (HUAttributeSetModel.COLUMN_PropertyValue == columnIndex)
		{
			final I_M_Attribute attribute = getM_Attribute(rowIndex);
			setValue(attribute, value, true);
		}
		else
		{
			throw new IllegalArgumentException("Invalid columnIndex: " + columnIndex);
		}
	}

	private void setValue(final I_M_Attribute attribute, final Object value, final boolean isPropagated)
	{
		Check.assumeNotNull(actionExecutor, "actionExecutor not null");

		final IAction action = new SetAttributeValueAction(this, attribute, value, isPropagated);
		actionExecutor.execute(action);
	}

	/**
	 * NOTE: Called by {@link IAction}. Never call directly.
	 * 
	 * @param attribute
	 * @param value
	 * @param isPropagated
	 */
	public void setValueAction(final I_M_Attribute attribute, final Object value, final boolean isPropagated)
	{
		attributeSet.setValue(attribute, value, isPropagated);
		fireTableDataChanged();
	}

	public List<I_M_AttributeValue> retrieveAttributeValues(final I_M_Attribute attribute)
	{
		final List<I_M_AttributeValue> attributeValues = Services.get(IHUAttributesDAO.class).retrieveAttributeValues(attribute);
		return attributeValues;
	}

	public IHUAttributeSet getAttributeSet()
	{
		return attributeSet;
	}

	public PropertyChangeSupport getPropertyChangeSupport()
	{
		return pcs;
	}

	public void setReadonly(final boolean readonly)
	{
		final boolean readonlyOld = this.readonly;

		if (readonlyOld == readonly)
		{
			return;
		}

		this.readonly = readonly;
		pcs.firePropertyChange(PROPERTY_Readonly, readonlyOld, this.readonly);
	}

	public boolean isReadonly()
	{
		return this.readonly;
	}
}
