/* This work is hereby released into the Public Domain.
 * To view a copy of the public domain dedication, visit
 * http://creativecommons.org/licenses/publicdomain/
 */
package org.compiere.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ComboBoxEditor;
import javax.swing.ComboBoxModel;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import org.adempiere.plaf.AdempiereComboBoxUI;
import org.apache.commons.lang3.SystemUtils;

import de.metas.util.Check;
import de.metas.util.StringUtils;

/**
 * Auto completion behavior for a combo box
 *
 * @author phib: this is from http://www.orbital-computer.de/CComboBox with some minor revisions for Adempiere
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <ul>
 *         <li>BF [ 1735043 ] AutoCompletion: drop down box is showed even if i press Caps
 *         <li>FR [ 1820783 ] AutoCompletion: posibility to toggle strict mode
 *         <li>BF [ 1820778 ] ESC(cancel editing) key not working if you are on VComboBox
 *         <li>BF [ 1898001 ] AutoComplete: Exception when selecting a text
 *         <li>FR [ 2552854 ] Combobox AutoCompletion should ignore diacritics
 *         </ul>
 * @author tobi42, www.metas.de
 *         <ul>
 *         <li>BF [ 2861223 ] AutoComplete: Ignoring Whitespace in Search String
 *         </ul>
 */
public class ComboBoxAutoCompletion<E> extends PlainDocument
{
	/**
	 * Enable auto completion for a combo box (strict mode enabled, see {@link #setStrictMode(boolean)} for more info).
	 *
	 * @return ComboBoxAutoCompletion instance, for future configuration (strict mode, string converter etc)
	 */
	public static <E> ComboBoxAutoCompletion<E> enable(final CComboBox<E> comboBox)
	{
		@SuppressWarnings("unchecked")
		ComboBoxAutoCompletion<E> ac = (ComboBoxAutoCompletion<E>)comboBox.getClientProperty(PROPERTY_AutoCompletionInstance);
		if (ac == null)
		{
			// change the editor's document
			ac = new ComboBoxAutoCompletion<E>(comboBox);

			comboBox.putClientProperty(PROPERTY_AutoCompletionInstance, ac);
		}

		// has to be editable
		comboBox.setEditable(true);

		return ac;
	}

	public static <E> void disable(final CComboBox<E> comboBox)
	{
		final ComboBoxAutoCompletion<?> ac = (ComboBoxAutoCompletion<?>)comboBox.getClientProperty(PROPERTY_AutoCompletionInstance);
		if(ac == null)
		{
			return;
		}
		
		comboBox.putClientProperty(PROPERTY_AutoCompletionInstance, null);
		
		ac.dispose();
		
		// disable editing mode
		comboBox.setEditable(false);
	}

	private static final long serialVersionUID = 1449135613844313889L;

	private static final String PROPERTY_AutoCompletionInstance = ComboBoxAutoCompletion.class.getName();

	private final CComboBox<E> comboBox;
	private JTextComponent _editorComp;

	/** Strict mode */
	private boolean m_strictMode = true; // default: true

	/**
	 * Flag to indicate if setSelectedItem has been called subsequent calls to remove/insertString should be ignored
	 */
	private boolean selecting = false;
	private EditingCommand currentEditingCommand = null;
	/** Bug 5100422 on Java 1.5: Editable CComboBox won't hide popup when tabbing out */
	private static final boolean hidePopupOnFocusLoss = SystemUtils.IS_JAVA_1_5;
	private boolean hitBackspace = false;
	private boolean hitBackspaceOnSelection = false;

	/** ComboBox action listener: highlight the whole text when user presses enter in a combobox */
	private final ActionListener comboBoxActionListener = new ActionListener()
	{
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			if (!selecting)
			{
				highlightCompletedText(0);
			}
		}
	};

	/** ComboBox editor changed listener: when combobox's editor is changed, configure the new editor */
	private PropertyChangeListener comboBoxEditorChangedListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(final PropertyChangeEvent e)
		{
			configureEditor();
		}
	};

	/** {@link ComboBoxEditor} key listener */
	private final KeyListener editorKeyListener = new KeyAdapter()
	{
		/** Resets the flags related to what key a user pressed */
		private void resetFlags()
		{
			hitBackspace = false;
			hitBackspaceOnSelection = false;
		}

		@Override
		public void keyPressed(final KeyEvent e)
		{
			// Ignore keys that do not alter the text - teo_sarca [ 1735043 ]
			if (e.getKeyChar() == KeyEvent.CHAR_UNDEFINED)
			{
				return;
			}
			// Ignore ESC key - teo_sarca BF [ 1820778 ]
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				return;
			}

			if (comboBox.isDisplayable())
			{
				comboBox.setPopupVisible(true);
			}
			if (!m_strictMode)
			{
				return;
			}

			resetFlags();
			switch (e.getKeyCode())
			{
			// determine if the pressed key is backspace (needed by the remove method)
				case KeyEvent.VK_BACK_SPACE:
					final JTextComponent editor = getComboBoxEditorComponent();
					hitBackspace = true;
					hitBackspaceOnSelection = editor != null && editor.getSelectionStart() != editor.getSelectionEnd();
					break;
				// ignore delete key
				case KeyEvent.VK_DELETE:
					e.consume();
					UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
					break;
			}
		}

		@Override
		public void keyReleased(final KeyEvent e)
		{
			// Make sure we reset the flags after user is releasing the key
			resetFlags();
		}
	};

	/** {@link ComboBoxEditor} focus listener: Highlight whole text when gaining focus */
	private final FocusListener editorFocusListener = new FocusAdapter()
	{
		@Override
		public void focusGained(final FocusEvent e)
		{
			highlightCompletedText(0);
		}

		@Override
		public void focusLost(final FocusEvent e)
		{
			// Workaround for Bug 5100422 - Hide Popup on focus loss
			if (hidePopupOnFocusLoss)
			{
				comboBox.setPopupVisible(false);
			}
		}
	};

	private final InputVerifier editorInputVerifier = new InputVerifier()
	{

		@Override
		public boolean verify(final JComponent input)
		{
			return true;
		}

		@Override
		public boolean shouldYieldFocus(final JComponent input)
		{
			// Don't lose focus if user is currently writting this this box
			if (currentEditingCommand != null)
			{
				return false;
			}

			return super.shouldYieldFocus(input);
		};
	};

	/**
	 * Constructor for AutoCompletion disabling
	 */
	private ComboBoxAutoCompletion(final CComboBox<E> comboBox)
	{
		super();
		Check.assumeNotNull(comboBox, "comboBox not null");

		//
		// Bind combobox
		this.comboBox = comboBox;
		comboBox.addActionListener(comboBoxActionListener);
		// When combobox's editor is changed, configure the new editor
		comboBox.addPropertyChangeListener("editor", comboBoxEditorChangedListener);

		//
		// Setup the combobox editor
		configureEditor();

		//
		// Handle initially selected object
		final E selectedItem = comboBox.getSelectedItem();
		if (selectedItem != null)
		{
			setText(valueToString(selectedItem));
		}
		highlightCompletedText(0);
	}
	
	public void dispose()
	{
		destroyEditorComponent();
		
		//
		// Unbind combobox
		if (comboBox != null)
		{
			comboBox.removeActionListener(comboBoxActionListener);
			comboBox.removePropertyChangeListener("editor", comboBoxEditorChangedListener);
		}
	}
	
	/**
	 * Remove all the listeners from the editor component and destroy it
	 */
	private final void destroyEditorComponent()
	{
		if (_editorComp != null)
		{
			_editorComp.removeKeyListener(editorKeyListener);
			_editorComp.removeFocusListener(editorFocusListener);
			if (_editorComp.getInputVerifier() == editorInputVerifier)
			{
				_editorComp.setInputVerifier(null);
			}
			_editorComp = null;
		}
	}


	/**
	 * Set strict mode. If the strict mode is enabled, you can't enter any other values than the ones from combo box list.
	 *
	 * @param mode true if strict mode
	 * @return
	 */
	public ComboBoxAutoCompletion<E> setStrictMode(final boolean mode)
	{
		m_strictMode = mode;
		return this;
	}

	private ComboBoxModel<E> getComboBoxModel()
	{
		return comboBox.getModel();
	}

	private JTextComponent getComboBoxEditorComponent()
	{
		return _editorComp;
	}

	private final synchronized void configureEditor()
	{
		if (_configureEditorRunning)
		{
			return;
		}
		_configureEditorRunning = true;
		try
		{
			if (_editorComp != null)
			{
				// destroy the old editor component in order to create a new one
				destroyEditorComponent();
			}

			final ComboBoxEditor newEditor = comboBox.getEditor();
			_editorComp = (JTextComponent)(newEditor == null ? null : newEditor.getEditorComponent());
			if (_editorComp != null)
			{
				_editorComp.addKeyListener(editorKeyListener);
				_editorComp.addFocusListener(editorFocusListener);
				_editorComp.setDocument(ComboBoxAutoCompletion.this);
				_editorComp.setInputVerifier(editorInputVerifier);
			}
		}
		finally
		{
			_configureEditorRunning = false;
		}
	}

	private boolean _configureEditorRunning = false;

	@Override
	public void remove(int offs, final int len) throws BadLocationException
	{
		// return immediately when selecting an item
		if (selecting)
		{
			return;
		}
		if (hitBackspace)
		{
			// user hit backspace => move the selection backwards
			// old item keeps being selected
			if (offs > 0)
			{
				if (hitBackspaceOnSelection)
				{
					offs--;
				}
			}
			else
			{
				// User hit backspace with the cursor positioned on the start => beep
				UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
			}
			highlightCompletedText(offs);
		}
		else
		{
			super.remove(offs, len);
		}
	}

	@Override
	public void insertString(int offs, final String str, final AttributeSet a) throws BadLocationException
	{
		if (selecting)
		{
			return;
		}

		if (currentEditingCommand != null)
		{
			return; // shall not happen
		}

		currentEditingCommand = new EditingCommand();
		try
		{
			super.insertString(offs, str, a);

			// lookup and select a matching item
			E item = lookupItem(getText(0, getLength()));

			if (item != null)
			{
				// setSelectedItem(item);
				currentEditingCommand.setItemToSelect(item);
			}
			else
			{
				if (m_strictMode)
				{
					if (offs == 0)
					{
						// null is valid for non-mandatory fields
						// so if cursor is at start of line allow it
						// otherwise keep old item selected if there is no better match
						// setSelectedItem(null);
						currentEditingCommand.setItemToSelect(null);
					}
					else
					{
						item = comboBox.getSelectedItem();
					}
					// undo the insertion as there isn't a valid match
					offs = offs - str.length();
					UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
				}
				else
				{
					return;
				}
			}

			if (item != null)
			{
				// setText(valueToString(item));
				currentEditingCommand.setTextToSet(valueToString(item));
			}
			else
			{
				// setText("");
				currentEditingCommand.setTextToSet("");
			}

			// select the completed part so it can be overwritten easily
			// highlightCompletedText(offs + str.length());
			currentEditingCommand.setHighlightTextStartPosition(offs + str.length());

			performEditingCommand(currentEditingCommand);
		}
		finally
		{
			currentEditingCommand = null;
		}
	}

	private final void performEditingCommand(final EditingCommand cmd)
	{
		if (cmd.isDoSelectItem())
		{
			setSelectedItem(cmd.getItemToSelect());
		}
		if (cmd.isDoSetText())
		{
			setText(cmd.getTextToSet());
		}
		if (cmd.isDoHighlightText())
		{
			highlightCompletedText(cmd.getHighlightTextStartPosition());
		}
	}

	/**
	 * Set text
	 *
	 * @param text
	 */
	private void setText(final String text)
	{
		try
		{
			// remove all text and insert the completed string
			super.remove(0, getLength());
			super.insertString(0, text, null);
		}
		catch (final BadLocationException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void highlightCompletedText(final int start)
	{
		final JTextComponent editor = getComboBoxEditorComponent();
		final int length = getLength();
		editor.select(start, length);
	}

	private void setSelectedItem(final Object item)
	{
		selecting = true;
		try
		{
			final ComboBoxModel<E> model = getComboBoxModel();
			model.setSelectedItem(item);
		}
		finally
		{
			selecting = false;
		}
	}

	private E getSelectedItem()
	{
		final ComboBoxModel<E> model = getComboBoxModel();

		@SuppressWarnings("unchecked")
		final E selectedItem = (E)model.getSelectedItem();
		return selectedItem;
	}

	private E lookupItem(final String pattern)
	{
		final E selectedItem = getSelectedItem();

		// only search for a different item if the currently selected does not match
		if (selectedItem != null && startsWithIgnoreCase(valueToString(selectedItem), pattern))
		{
			return selectedItem;
		}
		else
		{
			final ComboBoxModel<E> model = getComboBoxModel();

			// iterate over all items
			for (int i = 0, n = model.getSize(); i < n; i++)
			{
				final E currentItem = model.getElementAt(i);
				// current item starts with the pattern?
				if (currentItem != null && startsWithIgnoreCase(valueToString(currentItem), pattern))
				{
					return currentItem;
				}
			}
		}

		return null;
	}

	/**
	 * Checks if str1 starts with str2 (ignores case, trim leading whitespaces, strip diacritics)
	 *
	 * @param str1
	 * @param str2
	 * @return true if str1 starts with str2
	 */
	private static final boolean startsWithIgnoreCase(final String str1, final String str2)
	{
		final String s1 = StringUtils.stripDiacritics(str1.toUpperCase()).replaceAll("^\\s+", "");
		final String s2 = StringUtils.stripDiacritics(str2.toUpperCase()).replaceAll("^\\s+", "");
		return s1.startsWith(s2);
	}

	/**
	 * Converts the combo value to string.
	 *
	 * This method tries to fetch the text as is rendered by the renderer if possible. We do this because the value's toString() might be different from what is rendered, and user sees and writes what
	 * is rendered.
	 * 
	 * If it's not possible it will fallback to "toString".
	 *
	 * @param value
	 */
	private final String valueToString(final E value)
	{
		if (value == null)
		{
			return "";
		}

		final ListCellRenderer<? super E> renderer = comboBox.getRenderer();
		if (renderer == null)
		{
			return value.toString();
		}

		final JList<E> list = getJList();
		if (list == null)
		{
			return value.toString();
		}

		final int index = 0; // NOTE: most of the renderers are not using it. Also it is known that is not correct.
		final Component valueComp = renderer.getListCellRendererComponent(list, value, index, false, false);
		if (valueComp instanceof JLabel)
		{
			return ((JLabel)valueComp).getText();
		}

		return value.toString();
	}

	/** @return combobox's inner JList (if available) */
	private final JList<E> getJList()
	{
		final ComboPopup comboPopup = AdempiereComboBoxUI.getComboPopup(comboBox);
		if (comboPopup == null)
		{
			return null;
		}

		@SuppressWarnings("unchecked")
		final JList<E> list = comboPopup.getList();
		return list;
	}

	private final class EditingCommand
	{
		private boolean doSelectItem = false;
		private E itemToSelect = null;

		private boolean doSetText = false;
		private String textToSet = null;

		private boolean doHighlightText = false;
		private int highlightTextStartPosition = 0;

		public void setItemToSelect(E itemToSelect)
		{
			this.itemToSelect = itemToSelect;
			this.doSelectItem = true;
		}

		public boolean isDoSelectItem()
		{
			return doSelectItem;
		}

		public E getItemToSelect()
		{
			return itemToSelect;
		}

		public void setTextToSet(String textToSet)
		{
			this.textToSet = textToSet;
			this.doSetText = true;
		}

		public boolean isDoSetText()
		{
			return doSetText;
		}

		public String getTextToSet()
		{
			return textToSet;
		}

		public void setHighlightTextStartPosition(int highlightTextStartPosition)
		{
			this.highlightTextStartPosition = highlightTextStartPosition;
			this.doHighlightText = true;
		}

		public boolean isDoHighlightText()
		{
			return doHighlightText;
		}

		public int getHighlightTextStartPosition()
		{
			return highlightTextStartPosition;
		}
	};
}