package de.metas.adempiere.gui.search;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.time.SystemTime;
import org.compiere.apps.search.IInfoQueryCriteria;
import org.compiere.apps.search.IInfoSimple;
import org.compiere.grid.ed.VDate;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.swing.CButton;
import org.compiere.swing.CEditor;
import org.compiere.util.DisplayType;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.TimeUtil;
import org.jdesktop.swingx.JXPanel;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import net.miginfocom.swing.MigLayout;

/**
 * Component for quick date filtering
 *
 * @author al
 */
public class InfoQueryCriteriaDateModifier implements IInfoQueryCriteria
{
	private static final transient Logger logger = LogManager.getLogger(InfoQueryCriteriaDateModifier.class);

	private IInfoSimple _parent;
	private I_AD_InfoColumn _infoColumnDef;

	private String label = null;
	private VDate editor = null;
	private VDate editor2 = null;
	private JXPanel container = null;
	private JXPanel container2 = null;

	private CButton buttonPrevDate = null;
	private CButton buttonNextDate = null;

	private CButton buttonPrevDate2 = null;
	private CButton buttonNextDate2 = null;

	protected final VetoableChangeListener fieldChangedListener = new VetoableChangeListener()
	{
		@Override
		public void vetoableChange(final PropertyChangeEvent evt) throws PropertyVetoException
		{
			// We need to do compare the values again because VetoableChangeSupport/PropertyChangeSupport
			// is not covering the case when both values are null.
			// Equality comparison is sufficient because the rest is covered by VetoableChangeSupport.

			// task 08672: the comment above says it's fine to do "equals", but the code checked for "same"
			// changing the check to equals (because fully agree and further think that equals is *required* to avoid useless refreshes)
			// leaving the "same" check (commented out), just in case
			// if (evt.getOldValue() == evt.getNewValue())
			if (Check.equals(evt.getOldValue(), evt.getNewValue()))
			{
				return; // no refresh is needed
			}

			refreshData(); // execute query each time the date is changed
		}
	};

	private final void refreshData()
	{
		getParent().executeQuery();
	}

	@Override
	public void init(final IInfoSimple parent, final I_AD_InfoColumn infoColumn, final String searchText)
	{
		_parent = parent;
		_infoColumnDef = infoColumn;

		initComponents(searchText);
		initDefaults();
	}

	private final void initComponents(final String searchText)
	{
		final IInfoSimple parent = getParent();
		final I_AD_InfoColumn infoColumn = getAD_InfoColumn();

		// final Properties ctx = parent.getCtx();
		// final int windowNo = parent.getWindowNo();
		final int displayType = infoColumn.getAD_Reference_ID();
		Check.assume(DisplayType.isDate(displayType), "displayType is Date or DateTime, but was {}", displayType);

		final String columnName = infoColumn.getAD_Element().getColumnName();

		final ImageIcon prevIcon = Images.getImageIcon2("Next16");
		final ImageIcon nextIcon = Images.getImageIcon2("Previous16");

		final int modifierButtonWidth = 30;

		editor = createDateEditor(columnName, infoColumn.getName(), displayType);
		buttonPrevDate = new CButton(prevIcon);
		buttonPrevDate.setPreferredSize(new Dimension(modifierButtonWidth, (int)editor.getPreferredSize().getHeight()));
		buttonPrevDate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				onPrevPressed(editor);
			}
		});

		buttonNextDate = new CButton(nextIcon);
		buttonNextDate.setPreferredSize(new Dimension(modifierButtonWidth, (int)editor.getPreferredSize().getHeight()));
		buttonNextDate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				onNextPressed(editor);
			}
		});

		if (infoColumn.isRange())
		{
			editor2 = createDateEditor(columnName + "_2", infoColumn.getName(), displayType);
			buttonPrevDate2 = new CButton(prevIcon);
			buttonPrevDate2.setPreferredSize(new Dimension(modifierButtonWidth, (int)editor2.getPreferredSize().getHeight()));
			buttonPrevDate2.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(final ActionEvent e)
				{
					onPrevPressed(editor2);
				}
			});

			buttonNextDate2 = new CButton(nextIcon);
			buttonNextDate2.setPreferredSize(new Dimension(modifierButtonWidth, (int)editor2.getPreferredSize().getHeight()));
			buttonNextDate2.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(final ActionEvent e)
				{
					onNextPressed(editor2);
				}
			});
		}

		label = Services.get(IMsgBL.class).translate(parent.getCtx(), getAD_InfoColumn().getAD_Element().getColumnName());
	}

	private void initDefaults()
	{
		try
		{
			initDefaults0();
		}
		catch (final Exception e)
		{
			// Exception was thrown mainly because DefaultValue expression could not be evaluated.
			// It's not such a big deal, so a lower logging level shall be fine
			logger.info(e.getLocalizedMessage(), e);
		}
	}

	private void initDefaults0()
	{
		//
		// If there is no editor, there is no point to compute default value
		if (editor == null)
		{
			return;
		}

		//
		// Get DefaultValue expression string
		final I_AD_InfoColumn infoColumn = getAD_InfoColumn();
		final String defaultValueExpressionStr = infoColumn.getDefaultValue();
		if (Check.isEmpty(defaultValueExpressionStr, true))
		{
			return;
		}

		//
		// Evaluate DefaultValue expression in parent's context
		final IInfoSimple parent = getParent();
		final Evaluatee evalCtx = Evaluatees.ofCtx(parent.getCtx(), parent.getWindowNo(), false); // onlyWindow=false
		final IStringExpression defaultValueExpression = Services.get(IExpressionFactory.class).compile(defaultValueExpressionStr, IStringExpression.class);
		final String defaultValueStr = defaultValueExpression.evaluate(evalCtx, OnVariableNotFound.ReturnNoResult);
		if (defaultValueExpression.isNoResult(defaultValueStr))
		{
			// expression could not be evaluated
			return;
		}

		//
		// Convert evaluated expression string to it's DisplayType java class
		final String columnName = infoColumn.getColumnName();
		final int displayType = infoColumn.getAD_Reference_ID();
		final Object defaultValue = DisplayType.convertToDisplayType(defaultValueStr, columnName, displayType);

		//
		// Set value to editor
		editor.setValue(defaultValue);
	}

	private void onPrevPressed(final VDate dateField)
	{
		final Timestamp date = dateField.getValue();
		final Timestamp prevDay = TimeUtil.getPrevDay(date);
		dateField.setValue(prevDay);

		refreshData(); // execute query each time the date is changed
	}

	private void onNextPressed(final VDate dateField)
	{
		final Timestamp date = dateField.getValue();
		final Timestamp nextDay = TimeUtil.getNextDay(date);
		dateField.setValue(nextDay);

		refreshData(); // execute query each time the date is changed
	}

	private VDate createDateEditor(final String columnName, final String title, final int displayType)
	{
		final boolean mandatory = true; // 08329 making it mandatory, because in our only current application (MRP Product Info), there is no point in having no data (from a functional POV).
		final boolean readOnly = false;
		final boolean updateable = true;

		final VDate vd = new VDate(columnName, mandatory, readOnly, updateable, displayType, title);
		vd.setName(columnName);
		vd.addVetoableChangeListener(fieldChangedListener);
		return vd;
	}

	public IInfoSimple getParent()
	{
		return _parent;
	}

	@Override
	public I_AD_InfoColumn getAD_InfoColumn()
	{
		return _infoColumnDef;
	}

	@Override
	public int getParameterCount()
	{
		return 1;
	}

	@Override
	public String getLabel(final int index)
	{
		if (index == 0)
		{
			return label;
		}
		return null;
	}

	@Override
	public Object getParameterComponent(final int index)
	{
		if (index != 0)
		{
			return null;
		}

		if (container != null)
		{
			return container;
		}
		container = createComponent(editor, buttonPrevDate, buttonNextDate);
		return container;
	}

	@Override
	public Object getParameterToComponent(final int index)
	{
		if (index != 0)
		{
			return null;
		}

		if (container2 != null)
		{
			return container2;
		}
		container2 = createComponent(editor2, buttonPrevDate2, buttonNextDate2);
		return container2;
	}

	private final JXPanel createComponent(final JComponent editor, final JComponent prev, final JComponent next)
	{
		if (editor == null)
		{
			return null;
		}
		final JXPanel container = new JXPanel();

		//
		// Force 0 padding, wrap after 3rd component, force fill space with component and 5lp gap between components
		final LayoutManager layout = new MigLayout("insets 0, wrap 3", "[fill]5px");
		container.setLayout(layout);

		container.add(editor);
		container.add(prev);
		container.add(next);

		container.setAlignmentX(Component.LEFT_ALIGNMENT);

		return container;
	}

	@Override
	public Object getParameterValue(final int index, final boolean returnValueTo)
	{
		final Object field;
		if (returnValueTo)
		{
			field = index == 0 ? editor2 : null;
		}
		else
		{
			field = index == 0 ? editor : null;
		}

		if (field == null)
		{
			return null;
		}

		if (field instanceof CEditor)
		{
			final CEditor editor = (CEditor)field;
			return editor.getValue();
		}
		else
		{
			throw new AdempiereException("Component type not supported - " + field);
		}
	}

	@Override
	public String[] getWhereClauses(final List<Object> params)
	{
		final I_AD_InfoColumn infoColumn = getAD_InfoColumn();

		if (infoColumn.isTree())
		{
			return null;
		}

		final Object value = getParameterValue(0, false);

		//
		// Set Context Value
		{
			final String column = infoColumn.getName();

			final IInfoSimple parent = getParent();
			if (value == null)
			{
				final int displayType = infoColumn.getAD_Reference_ID();
				if (DisplayType.Date == displayType)
				{
					parent.setCtxAttribute(column, SystemTime.asDayTimestamp());
				}
				else if (DisplayType.Time == displayType)
				{
					parent.setCtxAttribute(column, SystemTime.asTimestamp());
				}
				else if (DisplayType.DateTime == displayType)
				{
					parent.setCtxAttribute(column, SystemTime.asDate());
				}
			}
			else
			{
				parent.setCtxAttribute(column, value);
			}
		}

		//
		// create the actual where clause
		// task: 08329: as of now, the date is a real column of the underlying view/table
		{
			final StringBuilder where = new StringBuilder();
			where.append(infoColumn.getSelectClause());
			where.append("=?");

			params.add(value);

			return new String[] { where.toString() };
		}
	}

	@Override
	public String getText()
	{
		if (editor == null)
		{
			return null;
		}

		final Object value = editor.getValue();
		if (value == null)
		{
			return null;
		}

		return value.toString();
	}
}
