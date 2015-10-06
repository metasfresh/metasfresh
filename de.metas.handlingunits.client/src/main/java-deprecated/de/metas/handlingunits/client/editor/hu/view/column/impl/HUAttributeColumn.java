package de.metas.handlingunits.client.editor.hu.view.column.impl;

import java.math.BigDecimal;

import org.adempiere.util.Check;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

import de.metas.handlingunits.attribute.api.IAttributeValue;
import de.metas.handlingunits.attribute.api.IHUAttributeSet;
import de.metas.handlingunits.client.editor.attr.model.HUAttributeSetModel;
import de.metas.handlingunits.client.editor.hu.model.HUEditorModel;
import de.metas.handlingunits.client.editor.view.column.impl.AbstractTreeTableColumn;
import de.metas.handlingunits.tree.node.hu.IHUTreeNode;
import de.metas.handlingunits.tree.node.hu.impl.HUTreeNode;

/**
 * Displays an attribute value in HU Editor, HU Tree Nodes table
 * 
 * @author tsa
 * 
 */
public class HUAttributeColumn extends AbstractTreeTableColumn<IHUTreeNode>
{
	private final I_M_Attribute attribute;
	private final Class<?> typeClass;

	public HUAttributeColumn(final I_M_Attribute attribute, final HUEditorModel model)
	{
		super(attribute.getName());

		Check.assumeNotNull(attribute, "attribute not null");
		this.attribute = attribute;

		final String attributeValueType = attribute.getAttributeValueType();
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{
			this.typeClass = BigDecimal.class;
		}
		else
		{
			this.typeClass = String.class;
		}
	}

	@Override
	public Class<?> getColumnType()
	{
		return typeClass;
	}

	@Override
	public Object getValue(IHUTreeNode node)
	{
		if (node instanceof HUTreeNode)
		{
			final HUTreeNode huNode = (HUTreeNode)node;
			final HUAttributeSetModel attributeSetModel = huNode.getHUAttributeSetModel();
			return getValue(attributeSetModel);
		}
		else
		{
			return null;
		}
	}

	private Object getValue(final HUAttributeSetModel attributeSetModel)
	{
		if (attributeSetModel == null)
		{
			return null;
		}

		final IHUAttributeSet attributeSet = attributeSetModel.getAttributeSet();
		final IAttributeValue attributeValue = attributeSet.getAttributeValueOrNullInstance(attribute);
		if (attributeValue == null)
		{
			return null;
		}

		final Object valueObj = attributeValue.getValue();
		final Object valueConverted = convertToType(valueObj);
		return valueConverted;
	}

	private Object convertToType(final Object valueObj)
	{
		if (valueObj == null)
		{
			return null;
		}
		else if (BigDecimal.class.equals(typeClass))
		{
			if (valueObj instanceof BigDecimal)
			{
				return valueObj;
			}
			else
			{
				return new BigDecimal(valueObj.toString());
			}
		}
		else if (String.class.equals(typeClass))
		{
			return valueObj.toString();
		}
		else
		{
			throw new IllegalStateException("Type class " + typeClass + " not supported");
		}
	}

}
