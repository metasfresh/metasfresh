package de.metas.adempiere.form.terminal.swing;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.compiere.util.DisplayType;
import org.compiere.util.NamePair;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUIAsyncInvoker.IClientUIAsyncExecutor;
import de.metas.adempiere.form.IInputMethod;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IPropertiesPanel;
import de.metas.adempiere.form.terminal.IPropertiesPanelModel;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalCheckboxField;
import de.metas.adempiere.form.terminal.ITerminalComboboxField;
import de.metas.adempiere.form.terminal.ITerminalDateField;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalField;
import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.ITerminalLookup;
import de.metas.adempiere.form.terminal.ITerminalLookupField;
import de.metas.adempiere.form.terminal.ITerminalNumericField;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.ITerminalScrollPane.ScrollPolicy;
import de.metas.adempiere.form.terminal.ITerminalTextField;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.WrongValueException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.event.UIAsyncPropertyChangeListener;
import de.metas.adempiere.form.terminal.field.constraint.ITerminalFieldConstraint;
import de.metas.logging.LogManager;

/* package */final class SwingPropertiesPanel implements IPropertiesPanel
{
	// services
	private static final transient Logger logger = LogManager.getLogger(SwingPropertiesPanel.class);

	private static final float DEFAULT_FONT_SIZE = 12f;
	private static final String DEFAULT_NUMBERIC_BUTTONS_CONSTRAINTS = "";
	private static final String DEFAULT_LABEL_CONSTRAINTS = "right, wmin 50, shrink 100";
	private static final String DEFAULT_EDITOR_CONSTRAINTS = "growx, shrink 0, wrap";
	private static final String DEFAULT_CONTAINER_CONSTARAINTS = "wmax 100%, fillx, align left"
			// Insets [top/all [left] [bottom] [right]]
			+ ", insets 10 0 10 10"; // note: left=0 because it's already spaced there

	private final ITerminalContext terminalContext;

	private IPropertiesPanelModel model;

	private IContainer panel;
	private final ITerminalScrollPane scroll;

	/**
	 * Keeps the last modified text field that was not yet saved
	 * task #857
	 */
	private ITerminalTextField textField;
	/**
	 * Keeps the property name of the last modified text field that was not yet saved
	 * task #857
	 */
	private String propertyName;

	private final Map<String, ITerminalField<?>> propertyName2editors = new HashMap<>();

	private String constraintsLabel = DEFAULT_LABEL_CONSTRAINTS;
	private String constraintsEditor = DEFAULT_EDITOR_CONSTRAINTS;

	private final PropertyChangeListener modelListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			onModelChanged(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
		}

		// @formatter:off
		@Override public String toString() { return "SwingPropertiesPanel[<anonymous modelListener>]"; };
		// @formatter:on
	};

	/* package */ SwingPropertiesPanel(final ITerminalContext terminalContext)
	{
		this(terminalContext, DEFAULT_CONTAINER_CONSTARAINTS);
	}

	/* package */ SwingPropertiesPanel(final ITerminalContext terminalContext, final String containerConstraints)
	{
		super();

		this.terminalContext = terminalContext;

		final ITerminalFactory factory = terminalContext.getTerminalFactory();
		panel = factory.createContainer(containerConstraints);

		scroll = getTerminalFactory().createScrollPane(panel);
		scroll.setHorizontalScrollBarPolicy(ScrollPolicy.NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPolicy.WHEN_NEEDED);

		scroll.setBorderEnabled(false); // hide borders

		terminalContext.addToDisposableComponents(this);
	}

	private void onModelChanged(final String eventName, final Object valueOld, final Object valueNew)
	{
		if (IPropertiesPanelModel.PROPERTY_ContentChanged.equals(eventName))
		{
			// NOTE: to be much more predictable we are not loading this in an invokeLater but we are doing it right now.
			// Else it can be quite uncontrollable when the new editors will be created and if we are using the old ones or the new ones.
			load();
		}
		else if (IPropertiesPanelModel.PROPERTY_ValueChanged.equals(eventName))
		{
			final String propertyName = (String)valueNew;
			final ITerminalField<?> editor = propertyName2editors.get(propertyName);
			if (editor == null)
			{
				// do nothing
				return;
			}

			loadEditor(propertyName, editor);
		}
		else if (IPropertiesPanelModel.PROPERTY_ValidateUIRequest.equals(eventName))
		{
			// task #857
			// validate the last changed text field in case it was not saved
			if (textField != null)
			{
				setValueFromUI(propertyName, textField.getText(), textField);
			}

			validate();
		}
	}

	@Override
	public Object getComponent()
	{
		return scroll.getComponent();
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	public ITerminalFactory getTerminalFactory()
	{
		return terminalContext.getTerminalFactory();
	}

	@Override
	public void setModel(final IPropertiesPanelModel modelNew)
	{
		final IPropertiesPanelModel modelOld = model;
		if (Util.same(modelNew, modelOld))
		{
			// nothing changed
			return;
		}

		if (modelOld != null)
		{
			modelOld.removePropertyChangeListener(modelListener);
		}

		model = modelNew;
		if (model != null)
		{
			model.addPropertyChangeListener(modelListener);
		}

		load();
	}

	@Override
	public IPropertiesPanelModel getModel()
	{
		return model;
	}

	@Override
	public void validate()
	{
		final IPropertiesPanelModel model = getModel();

		// If there is no model set, there is nothing to validate
		if (model == null)
		{
			return;
		}

		if (propertyName2editors == null || propertyName2editors.isEmpty())
		{
			return;
		}

		for (final Entry<String, ITerminalField<?>> e : propertyName2editors.entrySet())
		{
			final String propertyName = e.getKey();
			final ITerminalField<?> editor = e.getValue();
			if (editor == null)
			{
				continue;
			}

			if (!editor.isValid())
			{
				final String name = model.getPropertyDisplayName(propertyName);
				throw new WrongValueException(editor, "@NotValid@ " + name);
			}
		}
	}

	/**
	 * Sync Model Values to UI
	 */
	private void load()
	{
		if (panel == null)
		{
			// already disposed => do nothing
			return;
		}

		panel.removeAll();

		//
		// Clean-up editors
		for (final ITerminalField<?> editor : propertyName2editors.values())
		{
			if (editor != null)
			{
				editor.dispose();
			}
		}
		propertyName2editors.clear();

		loadingEditors.clear();

		final IPropertiesPanelModel model = getModel();
		if (model == null)
		{
			return;
		}

		for (final String propertyName : model.getPropertyNames())
		{
			final int propertyDisplayType = model.getDisplayType(propertyName);
			final String propertyDisplayName = model.getPropertyDisplayName(propertyName);
			final ITerminalFieldConstraint<Object> constraint = model.getConstraint(propertyName);
			final List<IInputMethod<?>> additionalInputMethods = model.getAdditionalInputMethods(propertyName);
			addPropertyLine(propertyName, propertyDisplayType, propertyDisplayName, constraint, additionalInputMethods);
		}

		// NOTE: for fuck's sake, if we don't invoke this method, our panel is not repainted
		SwingTerminalFactory.refresh(panel);

		// NOTE: if we don't invoke "refresh" our scroll bars does not get refreshed
		SwingTerminalFactory.refresh(scroll);
	}

	private final Set<String> loadingEditors = new HashSet<String>();

	private void loadEditor(final String propertyName, final ITerminalField<?> editor)
	{
		final IPropertiesPanelModel model = getModel();
		if (model == null)
		{
			return;
		}

		if (!loadingEditors.add(propertyName))
		{
			// editor is already loading
			return;
		}

		try
		{
			if (editor instanceof ITerminalComboboxField)
			{
				final ITerminalComboboxField comboboxEditor = (ITerminalComboboxField)editor;
				final List<? extends NamePair> availableValues = model.getPropertyAvailableValues(propertyName);
				comboboxEditor.setValues(availableValues);
			}
			else if (editor instanceof ITerminalLookupField)
			{
				final ITerminalLookupField lookupEditor = (ITerminalLookupField)editor;
				final ITerminalLookup lookup = model.getPropertyLookup(propertyName);
				lookupEditor.setLookup(lookup);
			}

			final Object value = model.getPropertyValue(propertyName);
			logger.debug("Setting property={} to value={} at editor={}", propertyName, value, editor);
			editor.setValue(value);

			final boolean editable = model.isEditable(propertyName);
			editor.setEditable(editable);
		}
		finally
		{
			loadingEditors.remove(propertyName);
		}
	}

	private void addPropertyLine(final String propertyName,
			final int propertyDisplayType,
			final String propertyDisplayName,
			final ITerminalFieldConstraint<Object> constraint,
			final List<IInputMethod<?>> addidionalInputMethods)
	{
		final ITerminalFactory factory = getTerminalFactory();
		final IPropertiesPanelModel model = getModel(); // shall not be null at the time this method is called

		final ITerminalLabel label = factory.createLabel(propertyDisplayName, false); // translate=false
		label.setFont(SwingPropertiesPanel.DEFAULT_FONT_SIZE); // font override for properties

		//
		// TODO (workaround)
		// Note that considering the display types are checked below, we don't exactly need to know what we get
		@SuppressWarnings("unchecked")
		final ITerminalField<Object> editor = createEditor(propertyName, propertyDisplayType);

		if (null != constraint)
		{
			editor.addConstraint(constraint);
		}
		propertyName2editors.put(propertyName, editor);

		loadEditor(propertyName, editor);

		panel.add(label, constraintsLabel);
		panel.add(editor, constraintsEditor);
		if (!model.isEditable(propertyName))
		{
			return; // don't add the button
		}

		// task 04966: add an additional button for each additional input method
		for (final IInputMethod<?> inputMethod : addidionalInputMethods)
		{
			final ITerminalButton inputMethodButton = factory.createButtonAction(inputMethod.getAppsAction());
			inputMethodButton.setVisible(true);
			inputMethodButton.setEnabled(true);

			inputMethodButton.addListener(/* add the action's listener */
					new UIAsyncPropertyChangeListener<Object, Void>(inputMethodButton)
					{
						@Override
						public Object runInBackground(final IClientUIAsyncExecutor<PropertyChangeEvent, Object, Void> executor)
						{
							final Object value = inputMethod.invoke();
							logger.debug("inputMethod={} returned value={} in UIAsyncPropertyChangeListener={} in UIAsyncPropertyChangeListener={}", inputMethod, value, this);
							return value;
						};

						@Override
						public void finallyUpdateUI(final IClientUIAsyncExecutor<PropertyChangeEvent, Object, Void> executor, final Object value)
						{
							// Guard against concurrency issues, when the model was changed in meantime.
							// Shall not happen, but better safe then sorry
							final IPropertiesPanelModel modelActual = getModel();
							if (model != modelActual)
							{
								final TerminalException ex = new TerminalException("Internal error: skip setting the value aquired from input method because model changed in meantime."
										+ "\n Model: " + model
										+ "\n Model(now): " + modelActual
										+ "\n Input method: " + inputMethod
										+ "\n Value aquired: " + value);
								logger.warn(ex.getLocalizedMessage(), ex);
								return;
							}
							logger.debug("Set value={} to editor={} ,editor.toString()={} in UIAsyncPropertyChangeListener={}", value, editor.getName(), editor, this);
							//
							// #370: trying to *not* set the field but the model. setting the field will also cause the model to be updated, but eventually from the model updating, the field will be updated a second time
							// editor.setValue(value, true); // fireEvent=true
							getModel().setPropertyValue(propertyName, value);
						}
					}); // addListener

			// NOTE: we are appending the input method buttons INSIDE the editor component
			// mainly because we want the "constraintsEditor" to be applied to the whole editor+inputMethodButtons as a group.
			// TODO: gain more security about our types to a avoid casting related FUD
			((Container)editor.getComponent()).add((Component)inputMethodButton.getComponent());
		}
	}

	/**
	 * Create editing component and registers the change listners to it.
	 *
	 * TODO (workaround): Note that because we can have any number of different display types, we can keep the {@link ITerminalField} raw and pass it through within this implementation.
	 *
	 * @param propertyName
	 * @param displayType
	 * @return ITerminalField (raw)
	 */
	@SuppressWarnings("rawtypes")
	private ITerminalField createEditor(final String propertyName, final int displayType)
	{
		final ITerminalFactory factory = getTerminalFactory();
		if (DisplayType.isNumeric(displayType))
		{
			final ITerminalNumericField editor = factory.createTerminalNumericField(propertyName, displayType,
					SwingPropertiesPanel.DEFAULT_FONT_SIZE,
					true,       // withButtons,
					false,       // withLabel
					SwingPropertiesPanel.DEFAULT_NUMBERIC_BUTTONS_CONSTRAINTS);
			editor.addListener(new PropertyChangeListener()
			{
				@Override
				public void propertyChange(final PropertyChangeEvent evt)
				{
					if (isEventValueChanged(evt))
					{
						final Object value = editor.getValue();
						setValueFromUI(propertyName, value, editor);
					}
				}

				// @formatter:off
				@Override public String toString() { return "SwingPropertiesPanel[<anonymous propertyChangeListener for property=" + propertyName + " and editor=" + editor+">]"; }
				// @formatter:on
			});

			return editor;
		}
		else if (DisplayType.List == displayType)
		{
			final ITerminalComboboxField editor = factory.createTerminalCombobox(propertyName);
			editor.setFontSize(DEFAULT_FONT_SIZE);
			editor.addListener(new PropertyChangeListener()
			{
				@Override
				public void propertyChange(final PropertyChangeEvent evt)
				{
					if (isEventValueChanged(evt))
					{
						final NamePair value = editor.getSelectedValue();
						// NOTE: comboboxes are returning NamePair, but we rely on AttributeStorage to do the conversion correctly
						setValueFromUI(propertyName, value, editor);
					}
				}
			});

			return editor;
		}
		else if (DisplayType.Search == displayType)
		{
			final ITerminalLookup lookup = null; // will be set later
			final ITerminalLookupField editor = factory.createTerminalLookupField(propertyName, lookup);
			editor.addListener(new PropertyChangeListener()
			{
				@Override
				public void propertyChange(final PropertyChangeEvent evt)
				{
					if (isEventValueChanged(evt))
					{
						final NamePair value = editor.getValue();
						setValueFromUI(propertyName, value, editor);
					}
				}
			});

			return editor;
		}
		else if (DisplayType.YesNo == displayType)
		{
			final ITerminalCheckboxField editor = factory.createTerminalCheckbox(null);
			editor.addListener(new PropertyChangeListener()
			{

				@Override
				public void propertyChange(final PropertyChangeEvent evt)
				{
					if (isEventValueChanged(evt))
					{
						final Object value = editor.getValue();
						setValueFromUI(propertyName, value, editor);
					}
				}
			});

			return editor;
		}
		else if (DisplayType.isDate(displayType))
		{
			final ITerminalDateField editor = factory.createTerminalDateField(null);
			editor.addListener(new PropertyChangeListener()
			{

				@Override
				public void propertyChange(final PropertyChangeEvent evt)
				{
					if (isEventValueChanged(evt))
					{
						final Date value = editor.getValue();
						setValueFromUI(propertyName, value, editor);
					}
				}
			});

			return editor;
		}
		else
		{
			final int displayTypeString = DisplayType.isText(displayType) ? displayType : DisplayType.String;
			final ITerminalTextField editor = factory.createTerminalTextField(propertyName, displayTypeString, SwingPropertiesPanel.DEFAULT_FONT_SIZE);
			editor.setShowKeyboardButton(true);

			// NOTE: in case user enters a value in editor and then clicks on other component, we want to commit the value right-away
			editor.setCommitOnValidEdit(true);

			editor.addListener(new PropertyChangeListener()
			{
				@Override
				public void propertyChange(final PropertyChangeEvent evt)
				{
					final boolean textFieldActionPerformed = isTextFieldActionPerformed(evt);

					final boolean fireValueChangedOnFocusLost = isFireValueChangedOnFocusLost() // if configured to save all the time (not disabled when typing)
							|| !editor.isFocusOwner(); // or if the component is not the focus owner anymore (another component was focused - i.e TAB pressed)

					final boolean isValueChanged = isTextFieldTextChanged(evt) || isEventValueChanged(evt); // evaluate any value-change events

					final boolean isFocusLost = isFocusLost(evt); // if the event itself is focus-lost, set value (if changed)

					if (textFieldActionPerformed
							|| fireValueChangedOnFocusLost && isValueChanged
							|| isFocusLost)
					{
						// task #857
						// in case the text field was already saved ( updated from UI) we have no reason to keep the text field for further updating
						setTextField(null);
						setPropertyName(null);
						final Object value = editor.getText();
						setValueFromUI(propertyName, value, editor);
					}
					else
					{
						// task #857
						// in case there were modifications on a text field and they were not saved (updated from UI) make sure the text field is kept
						// together with it's property name.
						// In case the OK button will be pressed before the update from UI is triggered again for this field, it will be saved during validation.
						setPropertyName(propertyName);
						setTextField(editor);
					}
				}
			});

			return editor;
		}
	}

	private boolean _fireValueChangedOnFocusedLost = true;

	private boolean disposed = false;

	@Override
	public final void disableFireValueChangedOnFocusLost()
	{
		_fireValueChangedOnFocusedLost = false;
	}

	private final boolean isFireValueChangedOnFocusLost()
	{
		return _fireValueChangedOnFocusedLost;
	}

	/**
	 * @param evt
	 * @return true if user pressed Enter in text field
	 */
	private final boolean isTextFieldActionPerformed(final PropertyChangeEvent evt)
	{
		return ITerminalTextField.PROPERTY_ActionPerformed.equals(evt.getPropertyName());
	}

	/**
	 * @param evt
	 * @return true if text was changed (mainly programmatically)
	 */
	private final boolean isTextFieldTextChanged(final PropertyChangeEvent evt)
	{
		return ITerminalTextField.PROPERTY_TextChanged.equals(evt.getPropertyName());
	}

	/**
	 * @param evt
	 * @return true if value changed (note that editor is configured to commitEdit on valid value)
	 */
	private final boolean isEventValueChanged(final PropertyChangeEvent evt)
	{
		return ITerminalField.ACTION_ValueChanged.equals(evt.getPropertyName());
	}

	/**
	 * @param evt
	 * @return true if focus was lost
	 */
	private final boolean isFocusLost(final PropertyChangeEvent evt)
	{
		return ITerminalTextField.PROPERTY_FocusLost.equals(evt.getPropertyName());
	}

	/**
	 * Sync UI Value to Model Value
	 *
	 * @param propertyName
	 * @param value
	 */
	private void setValueFromUI(final String propertyName, final Object value, final ITerminalField<?> editor)
	{
		// Don't set the value to model if editor is not editable
		if (!editor.isEditable())
		{
			return;
		}

		if (loadingEditors.contains(propertyName))
		{
			// ignore events because those are fired by editor loading
			return;
		}

		final IPropertiesPanelModel model = getModel();
		model.setPropertyValue(propertyName, value);
	}

	@Override
	public void setLabelConstraints(final String constraintsLabel)
	{
		this.constraintsLabel = constraintsLabel;
	}

	@Override
	public void setEditorConstraints(final String constraintsEditor)
	{
		this.constraintsEditor = constraintsEditor;
	}

	@Override
	public final void dispose()
	{
		setModel(null); // will also reset depending fields

		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	@Override
	public void setVerticalScrollBarPolicy(final ScrollPolicy scrollPolicy)
	{
		scroll.setVerticalScrollBarPolicy(scrollPolicy);
	}

	public ITerminalTextField getTextField()
	{
		return textField;
	}

	public void setTextField(ITerminalTextField textField)
	{
		this.textField = textField;
	}

	public String getPropertyName()
	{
		return propertyName;
	}

	public void setPropertyName(String valueField)
	{
		this.propertyName = valueField;
	}

}
