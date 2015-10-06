package de.metas.handlingunits.client.editor.attr.model.action;

import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.client.editor.attr.model.HUAttributeSetModel;
import de.metas.handlingunits.client.editor.command.model.action.IAction;

public class SetAttributeValueAction implements IAction
{
	private final HUAttributeSetModel attributeSetModel;

	private final I_M_Attribute attribute;
	private final Object newValue;
	private final Object oldValue;

	private final boolean isPropagated;

	public SetAttributeValueAction(final HUAttributeSetModel attributeSetModel, final I_M_Attribute attribute, final Object newValue, final boolean isPropagated)
	{
		this.attributeSetModel = attributeSetModel;
		this.attribute = attribute;

		oldValue = attributeSetModel.getAttributeSet().getValue(attribute);
		this.newValue = newValue;

		this.isPropagated = isPropagated;

	}

	@Override
	public String getActionName()
	{
		return "Set Attribute Value";
	}

	@Override
	public void doIt()
	{
		attributeSetModel.setValueAction(attribute, newValue, isPropagated);
	}

	@Override
	public void undoIt()
	{
		attributeSetModel.setValueAction(attribute, oldValue, isPropagated);
	}
}
