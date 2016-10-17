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

import java.awt.Color;
import java.awt.Component;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.JTextComponent;

import org.adempiere.images.Images;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.swing.CButton;
import org.compiere.util.DisplayType;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.ITerminalField;
import de.metas.adempiere.form.terminal.ITerminalTextField;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.logging.LogManager;
import net.miginfocom.swing.MigLayout;

/**
 * Formatted Text field with on-screen keyboard support
 *
 * @author Teo Sarca
 *
 */
/* package */class SwingTerminalTextField
		extends de.metas.adempiere.form.terminal.AbstractTerminalTextField
		implements IComponentSwing
{
	private static final transient Logger logger = LogManager.getLogger(SwingTerminalTextField.class);

	private JPanel panel;
	private JTextComponent textComponent;
	private CButton keyboardButton;

	private TextFieldActionListener textFieldActionListener;
	private TextFieldFocusListener textFieldFocusListener;
	private TextFieldKeyListener textFieldKeyListener;
	private TextFieldValueChangedListener textFieldValueChangedListener;
	private TextFieldMouseListener textFieldMouseListener;
	private TabKeyAsFocusLostDispatcher tabKeyAsFocusLostDispatcher;

	private static final int KEYBOARD_ShowDelayMillis = 500;

	/**
	 * Used to display the soft-keyboard after {@link #KEYBOARD_ShowDelayMillis}.
	 * <p>
	 * Note: the timer is reusable, i.e it's not required to recreate it after it was used. See http://www.math.uni-hamburg.de/doc/java/tutorial/uiswing/misc/timer.html
	 *
	 * @see #showKeyboardDelayed()
	 */
	private Timer timer = new Timer(KEYBOARD_ShowDelayMillis, new ActionListener()
	{
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			// show keyboard now!
			showKeyboard();
		}
	});

	private class TextFieldMouseListener extends MouseAdapter
	{
		@Override
		public void mouseClicked(final MouseEvent e)
		{
			showKeyboard();
		}
	};

	private class TextFieldActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			firePropertyChanged(ITerminalTextField.PROPERTY_ActionPerformed, null, getValue());
		}
	};

	private class TextFieldFocusListener implements FocusListener
	{
		@Override
		public void focusLost(final FocusEvent e)
		{
			// Don't fire focus lost if it's just temporary
			if (e.isTemporary())
			{
				return;
			}

			// We shall fire FocusLost event only if component does not have any Active Keyboard.
			// In case we have an active keyboard this event shall not be fired because it will trigger "false" focus lost events
			// when user not even finished to input his/her text/number
			if (getActiveKeyboard() != null)
			{
				return;
			}

			// Execute focus lost event
			onFocusLost();
		}

		@Override
		public void focusGained(final FocusEvent e)
		{
			// Don't fire focus gained if it's just temporary
			if (e.isTemporary())
			{
				return;
			}

			firePropertyChanged(ITerminalTextField.PROPERTY_FocusGained, null, getValue());
		}
	};

	private class TextFieldKeyListener implements KeyListener
	{

		private String text = null;

		private boolean isTextChanged(final String textNew)
		{
			if (Util.same(text, textNew))
			{
				return false;
			}
			if (text != null)
			{
				return !text.equals(textNew);
			}
			else
			{
				return true;
			}
		}

		@Override
		public void keyPressed(final KeyEvent e)
		{
			text = getText();
		}

		@Override
		public void keyReleased(final KeyEvent e)
		{
			// Don't automatically popup keyword because we do have the keyboard button, so the user is supposed to open it when required.
			if (isShowKeyboardButton())
			{
				return;
			}

			// If text is not initialized, it means that keyPressed event happened in another component
			if (text == null)
			{
				return;
			}

			final String textNew = getText();
			if (!isTextChanged(textNew))
			{
				return;
			}

			text = null;

			showKeyboardDelayed();
		}

		@Override
		public void keyTyped(final KeyEvent e)
		{
			// nothing
		}
	}

	private class TextFieldValueChangedListener implements PropertyChangeListener
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			firePropertyChanged(ITerminalField.ACTION_ValueChanged, evt.getOldValue(), evt.getNewValue());
		}
	}

	private class TabKeyAsFocusLostDispatcher implements KeyEventDispatcher
	{
		@Override
		public boolean dispatchKeyEvent(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_TAB
					&& e.getModifiers() == 0)
			{
				onFocusLost();
				return true; // take no further action
			}

			return false; // let others handle this
		}
	}

	public SwingTerminalTextField(final ITerminalContext tc, final String title, final int displayType)
	{
		super(tc, title, displayType);
	}

	public SwingTerminalTextField(final ITerminalContext tc, final String title, final int displayType, final float fontSize)
	{
		super(tc, title, displayType, fontSize);
	}

	public SwingTerminalTextField(final ITerminalContext tc, final String title)
	{
		super(tc, title);
	}

	@Override
	protected void createUI()
	{
		//
		// Create the right text component, based on display type
		final int displayType = getDisplayType();
		if (displayType == ITerminalTextField.TYPE_Password)
		{
			textComponent = new JPasswordField();
			keyboardButton = new CButton();
			keyboardButton.setIcon(Images.getImageIcon2("Keyboard16"));
			panel = new JPanel(new MigLayout("", "[grow]", "[grow]"));
			panel.add(textComponent, "growx, w 150");
			panel.add(keyboardButton);
		}
		else if (DisplayType.TextLong == displayType || DisplayType.Memo == displayType)
		{
			final JTextArea textArea = new JTextArea();
			// textArea.setColumns(columns); // info not available
			textArea.setRows(4); // NOTE: atm we are hardcoding this

			// NOTE: having the LineWrap=true will cause weird resizing issues with MigLayout, each time when we set a new value.
			textArea.setLineWrap(false);

			// NOTE: if we want vertical scroll bars here, we shall use JScrollPane

			this.textComponent = textArea;
		}
		else
		{
			textFieldValueChangedListener = new TextFieldValueChangedListener();
			final Format format = getFormat();
			if (format != null)
			{
				textComponent = new JFormattedTextField(format);
			}
			else
			{
				final DefaultFormatter formatter = new DefaultFormatter();
				textComponent = new JFormattedTextField(formatter);
			}
			textComponent.addPropertyChangeListener("value", textFieldValueChangedListener);

			panel = null;
		}

		// Add action listener if applicable
		textFieldActionListener = new TextFieldActionListener();
		if (textComponent instanceof JTextField)
		{
			((JTextField)textComponent).addActionListener(textFieldActionListener);
		}
		//
		textFieldFocusListener = new TextFieldFocusListener();
		textComponent.addFocusListener(textFieldFocusListener);
		textFieldKeyListener = new TextFieldKeyListener();
		textComponent.addKeyListener(textFieldKeyListener);

		// if (keyLayoutId > 0)
		textFieldMouseListener = new TextFieldMouseListener();
		if (displayType == ITerminalTextField.TYPE_Password)
		{
			keyboardButton.addMouseListener(textFieldMouseListener);
		}
		else
		{
			textComponent.addMouseListener(textFieldMouseListener);
		}

		if (DisplayType.isNumeric(displayType)
				&& textComponent instanceof JTextField)
		{
			((JTextField)textComponent).setHorizontalAlignment(SwingConstants.TRAILING);
		}

		// override the font size
		final float fontSize = getFontSize();
		if (fontSize != 0f)
		{
			textComponent.setFont(textComponent.getFont().deriveFont(fontSize));
		}
	}

	@Override
	public Component getComponent()
	{
		final int displayType = getDisplayType();

		if (displayType == ITerminalTextField.TYPE_Password && ITerminalTextField.ACTION_Nothing.equals(getAction()))
		{
			return panel;
		}
		else
		{
			return textComponent;
		}
	}

	@Override
	public String getText()
	{
		return textComponent.getText();
	}

	@Override
	public String getTextHead()
	{
		final String old = textComponent.getText();
		int caretPos = textComponent.getCaretPosition();
		if (textComponent.getSelectedText() != null)
		{
			caretPos = textComponent.getSelectionStart();
		}
		final String head = old.substring(0, caretPos);

		return head;
	}

	@Override
	public String getTextTail()
	{
		final String old = textComponent.getText();
		int caretPos = textComponent.getCaretPosition();
		if (textComponent.getSelectedText() != null)
		{
			caretPos = textComponent.getSelectionEnd();
		}
		final String tail = old.substring(caretPos, old.length());
		return tail;
	}

	@Override
	public void setEditable(final boolean editable)
	{
		// NOTE: in case the text field is not editable we want to prevent the Focus Lost event which can fire a change in this text component
		textComponent.setFocusable(editable);

		textComponent.setEditable(editable);
	}

	@Override
	public boolean isEditable()
	{
		return textComponent != null && textComponent.isEnabled() && textComponent.isEditable();
	}

	@Override
	public final boolean isFocusOwner()
	{
		// If no textField (i.e. component was disposed), for sure this is not the focus owner
		if (textComponent == null)
		{
			return false;
		}

		//
		// If the on screen keybord is active, for sure it has the focus (also because is modal),
		// but we can consider like our component has the focus.
		//
		// NOTE: before changing this please check all callers because some of them strongly depends on this logic
		// and if you break them you could introduce huge performance issues!
		if (isKeyboardActive())
		{
			return true;
		}

		//
		// Ask the underlying swing text field if it owns the focus
		return textComponent.isFocusOwner();
	}

	/**
	 * Call {@link JTextComponent#setText(String)} on our <code>textComponent</code> member.
	 * <p>
	 * Note that if our <code>textComponent</code> is a {@link JFormattedTextField}, then the value with we set here is the displayed one, but might be different from the field's <i>value</i>,
	 * see the <a href="http://docs.oracle.com/javase/tutorial/uiswing/components/formattedtextfield.html">How to Use Formatted Text Fields</a> tutorial for details.<br>
	 * Quote: "A formatted text field's text and its value are two different properties, and the value often lags behind the text."
	 */
	@Override
	public void setText(final String text)
	{
		final String textOld = textComponent.getText();

		logger.debug("this-ID={}, name={}, text={}, textOld={}, isEventDispatchThread={}; calling setText() on textComponent={}; this={}",
				System.identityHashCode(this), getName(), text, textOld, SwingUtilities.isEventDispatchThread(), textComponent, this);

		textComponent.setText(text);

		// TODO: gh #370: remove the commitEdit call (also un-edit the javadoc!) if this change doesn't help either
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final boolean commit = sysConfigBL.getBooleanValue("de.metas.adempiere.form.terminal.swing.SwingTerminalTextField.directCommitEditOnSetText", true);
		if (commit)
		{
			try
			{
				commitEdit();
			}
			catch (final ParseException e)
			{
				// don't do anything about the error. we just want to make sure that *if* the text is set from the outside and *if* that's successful, *then* the value is directly updated too.
				logger.debug("this-ID={}, name={}, text={}, textOld={}, isEventDispatchThread={}; commitEdit() after setText() failed with exception={}, errorOffSet={} on textComponent={}; this={}",
						System.identityHashCode(this), getName(), text, textOld, SwingUtilities.isEventDispatchThread(), e, e.getErrorOffset(), textComponent, this);
			}
		}
		firePropertyChanged(ITerminalTextField.PROPERTY_TextChanged, textOld, text);
	}

	@Override
	protected String getFieldValue()
	{
		if (textComponent instanceof JFormattedTextField)
		{
			final JFormattedTextField formattedTextField = (JFormattedTextField)textComponent;
			if (formattedTextField.getFormatter() == null)
			{
				// NOTE: if formatter is null, then "commitEdit" is doing nothing, so value is not changed
				// that's why we use "getText" instead of "getValue"
				return formattedTextField.getText();
			}
			else
			{
				final Object value = formattedTextField.getValue();
				return convertValueToType(value);
			}
		}
		else
		{
			return textComponent.getText();
		}
	}

	/**
	 * Sets the "inner" value of our <code>textComponent</code> member. Also see {@link #setText(String)}.
	 */
	@Override
	protected void setFieldValue(final String value, final boolean fireEvent)
	{
		logger.debug("this-ID={}, setFieldValue with value={}, fireEvent={}, isEventDispatchThread={}, on this={}",
				System.identityHashCode(this), value, fireEvent, SwingUtilities.isEventDispatchThread(), this);

		if (textComponent instanceof JFormattedTextField)
		{
			Object valueToUse = value;
			final Object valueOld = ((JFormattedTextField)textComponent).getValue();
			try
			{
				final Format format = getFormat();
				if (format instanceof DecimalFormat)
				{
					final DecimalFormat df = (DecimalFormat)format;
					df.setParseBigDecimal(true);
					valueToUse = df.parse(value.toString());
				}
			}
			catch (final Exception e)
			{
				logger.info("Error parsing text: " + value, e);
			}

			logger.debug("this-ID={}, name={}, value={}, fireEvent={}, valueToUse={}, isEventDispatchThread={}; in method setFieldValue(), calling setValue() on textComponent={}; this={}",
					System.identityHashCode(this), getName(), value, fireEvent, valueToUse, SwingUtilities.isEventDispatchThread(), textComponent, this);
			((JFormattedTextField)textComponent).setValue(valueToUse);

			if (fireEvent)
			{
				firePropertyChanged(ITerminalTextField.PROPERTY_TextChanged, valueOld, valueToUse);
			}
		}
		else
		{
			logger.debug("this-ID={}, name={}, value={}, fireEvent={}, isEventDispatchThread={}; in method setFieldValue(), calling setText() on textComponent={}; this={}",
					System.identityHashCode(this), getName(), value, fireEvent, SwingUtilities.isEventDispatchThread(), textComponent, this);
			setText(value == null ? null : value.toString());
		}
	}

	@Override
	public void commitEdit() throws ParseException
	{
		if (textComponent instanceof JFormattedTextField)
		{
			((JFormattedTextField)textComponent).commitEdit();
		}
	}

	public void setFormatterFactory(final AbstractFormatterFactory formatter)
	{
		if (textComponent instanceof JFormattedTextField)
		{
			((JFormattedTextField)textComponent).setFormatterFactory(formatter);
		}
	}

	public AbstractFormatterFactory getFormatterFactory()
	{
		if (textComponent instanceof JFormattedTextField)
		{
			return ((JFormattedTextField)textComponent).getFormatterFactory();
		}
		else
		{
			return null;
		}
	}

	@Override
	public void requestFocus()
	{
		final boolean selectAllText = true;
		requestFocus(selectAllText);
	}

	@Override
	public void requestFocus(final boolean selectAllText)
	{
		textComponent.requestFocusInWindow();

		// Select all text on focus gained (especially helpful for POSes) if it wasn't already selected
		if (selectAllText)
		{
			final String selectedText = textComponent.getSelectedText();
			final String text = textComponent.getText();
			if (!Check.equals(text, selectedText))
			{
				textComponent.selectAll();
			}
		}
	}

	@Override
	public void setCaretPosition(final int position)
	{
		if (textComponent != null)
		{
			textComponent.setCaretPosition(position);
		}
	}

	@Override
	public void setBackground(final Color color)
	{
		if (textComponent != null)
		{
			textComponent.setBackground(color);
		}
	}

	@Override
	public void processPendingEvents()
	{
		if (textComponent instanceof JTextField)
		{
			((JTextField)textComponent).postActionEvent();
		}
	}

	@Override
	public void setShowKeyboardButton(final boolean isShowKeyboardButton)
	{
		super.setShowKeyboardButton(isShowKeyboardButton);
		if (keyboardButton != null)
		{
			keyboardButton.setVisible(isShowKeyboardButton);
			keyboardButton.setEnabled(isShowKeyboardButton);
		}
	}

	private final void showKeyboardDelayed()
	{
		textComponent.isFocusOwner();
		timer.setRepeats(false);
		timer.start();
	}

	@Override
	public void setCommitOnValidEdit(final boolean commit)
	{
		if (textComponent instanceof JFormattedTextField)
		{
			final JFormattedTextField formattedTextField = (JFormattedTextField)textComponent;
			final AbstractFormatter formatter = formattedTextField.getFormatter();
			if (formatter instanceof DefaultFormatter)
			{
				final DefaultFormatter defaultFormatter = (DefaultFormatter)formatter;
				defaultFormatter.setCommitsOnValidEdit(commit);
				return;
			}
		}
		else if (textComponent instanceof JTextArea)
		{
			// ignore it for now
		}
		else
		{
			throw new UnsupportedOperationException("setCommitOnValidValue not supported for " + this);
		}
	}

	@Override
	public void dispose()
	{
		super.dispose();

		if (tabKeyAsFocusLostDispatcher != null)
		{
			KeyboardFocusManager.getCurrentKeyboardFocusManager()
					.removeKeyEventDispatcher(tabKeyAsFocusLostDispatcher);
			tabKeyAsFocusLostDispatcher = null;
		}

		if (timer != null)
		{
			timer.stop();
			timer = null;
		}

		if (textComponent != null)
		{
			if (textComponent instanceof JTextField)
			{
				((JTextField)textComponent).removeActionListener(textFieldActionListener);
			}
			textComponent.removeFocusListener(textFieldFocusListener);
			textComponent.removeKeyListener(textFieldKeyListener);
			textComponent.removePropertyChangeListener("value", textFieldValueChangedListener);
			textComponent.removeMouseListener(textFieldMouseListener);
			textComponent = null;
		}

		if (keyboardButton != null)
		{
			keyboardButton.removeMouseListener(textFieldMouseListener);
			keyboardButton = null;
		}

		if (panel != null)
		{
			panel.removeAll();
			panel = null;
		}
	}

	/**
	 * Execute focus lost event: commit edit, set text and fire {@link ITerminalTextField#PROPERTY_FocusLost}.
	 *
	 */
	private final void onFocusLost()
	{
		//
		// Make sure we have the latest value
		final String value = getValue();
		try
		{
			commitEdit();
		}
		catch (final ParseException ex)
		{
			// Revert text to last good value
			setText(value);
			// ignore the exception: throw new TerminalException(ex);
		}

		firePropertyChanged(ITerminalTextField.PROPERTY_FocusLost, null, getValue());
	}

	@Override
	public void enableHandleTabKeyAsFocusLost()
	{
		// Make sure our dispatcher was not already installed
		if (tabKeyAsFocusLostDispatcher != null)
		{
			return;
		}

		tabKeyAsFocusLostDispatcher = new TabKeyAsFocusLostDispatcher();
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(tabKeyAsFocusLostDispatcher);
	}
}
