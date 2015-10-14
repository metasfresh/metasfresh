/* This work is hereby released into the Public Domain.
 * To view a copy of the public domain dedication, visit
 * http://creativecommons.org/licenses/publicdomain/
 */
package org.compiere.grid.ed;

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
import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import org.compiere.swing.CComboBox;

/**
 * Auto completion behaviour for a combo box
 * 
 * @author phib: this is from http://www.orbital-computer.de/JComboBox
 * 					with some minor revisions for Adempiere
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1735043 ] AutoCompletion: drop down box is showed even if i press Caps
 * 			<li>FR [ 1820783 ] AutoCompletion: posibility to toggle strict mode
 * 			<li>BF [ 1820778 ] ESC(cancel editing) key not working if you are on VComboBox
 * 			<li>BF [ 1898001 ] AutoComplete: Exception when selecting a text
 * 			<li>FR [ 2552854 ] Combobox AutoCompletion should ignore diacritics
 * @author tobi42, www.metas.de
 *			<li>BF [ 2861223 ] AutoComplete: Ignoring Whitespace in Search String
 */
public class AutoCompletion extends PlainDocument {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1449135613844313889L;
	CComboBox comboBox;
	ComboBoxModel model;
	JTextComponent editor;
	/**
	 * Flag to indicate if setSelectedItem has been called 
	 * subsequent calls to remove/insertString should be ignored 
	 */
	boolean selecting=false;
	boolean hidePopupOnFocusLoss;
	boolean hitBackspace=false;
	boolean hitBackspaceOnSelection;
	/** Strict mode */
	private boolean m_strictMode = true;

	KeyListener editorKeyListener;
	FocusListener editorFocusListener;

	public AutoCompletion(final CComboBox comboBox) {
		this.comboBox = comboBox;
		model = comboBox.getModel();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!selecting) highlightCompletedText(0);
			}
		});
		comboBox.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				if (e.getPropertyName().equals("editor")) configureEditor((ComboBoxEditor) e.getNewValue());
				if (e.getPropertyName().equals("model")) model = (ComboBoxModel) e.getNewValue();
			}
		});
		editorKeyListener = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				//	Ignore keys that do not alter the text - teo_sarca [ 1735043 ]
				if (e.getKeyChar() == KeyEvent.CHAR_UNDEFINED) {
					return;
				}
				// Ignore ESC key - teo_sarca BF [ 1820778 ]
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					return;
				}

				if (comboBox.isDisplayable())
					comboBox.setPopupVisible(true);
				if(!m_strictMode)
					return;
				hitBackspace=false;
				switch (e.getKeyCode()) {
					// determine if the pressed key is backspace (needed by the remove method)
					case KeyEvent.VK_BACK_SPACE :
						hitBackspace=true;
						hitBackspaceOnSelection=editor.getSelectionStart()!=editor.getSelectionEnd();
						break;
					// ignore delete key
					case KeyEvent.VK_DELETE :
						e.consume();
						UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
						break;
				}
			}
		};
		// Bug 5100422 on Java 1.5: Editable JComboBox won't hide popup when tabbing out
		hidePopupOnFocusLoss=System.getProperty("java.version").startsWith("1.5");
		// Highlight whole text when gaining focus
		editorFocusListener = new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				highlightCompletedText(0);
			}
			public void focusLost(FocusEvent e) {
				// Workaround for Bug 5100422 - Hide Popup on focus loss
				if (hidePopupOnFocusLoss) comboBox.setPopupVisible(false);
			}
		};
		configureEditor(comboBox.getEditor());
		// Handle initially selected object
		Object selected = comboBox.getSelectedItem();
		if (selected!=null) setText(selected.toString());
		highlightCompletedText(0);
	}

	/**
	 * Enable auto completion for a combo box (strict mode)  
	 * @param comboBox
	 */
	public static void enable(CComboBox comboBox) {
		enable(comboBox, true);
	}

	/**
	 * Enable auto completion for a combo box 
	 * @param comboBox
	 * @param strictMode true if you want to set strict mode
	 */
	public static void enable(CComboBox comboBox, boolean strictMode) {
		// has to be editable
		comboBox.setEditable(true);
		// change the editor's document
		AutoCompletion ac = new AutoCompletion(comboBox);
		// set strict mode
		ac.setStrictMode(strictMode);
	}

	void configureEditor(ComboBoxEditor newEditor) {
		if (editor != null) {
			editor.removeKeyListener(editorKeyListener);
			editor.removeFocusListener(editorFocusListener);
		}

		if (newEditor != null) {
			editor = (JTextComponent) newEditor.getEditorComponent();
			editor.addKeyListener(editorKeyListener);
			editor.addFocusListener(editorFocusListener);
			editor.setDocument(this);
		}
	}

	@Override
	public void remove(int offs, int len) throws BadLocationException {
		// return immediately when selecting an item
		if (selecting) return;
		if (hitBackspace) {
			// user hit backspace => move the selection backwards
			// old item keeps being selected
			if (offs>0) {
				if (hitBackspaceOnSelection) offs--;
			} else {
				// User hit backspace with the cursor positioned on the start => beep
				UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
			}
			highlightCompletedText(offs);
		} else {
			super.remove(offs, len);
		}
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

		if (selecting) return;

		super.insertString(offs, str, a);

		// lookup and select a matching item
		Object item = lookupItem(getText(0, getLength()));

		if (item != null) {
			setSelectedItem(item);
		} else {
			if (m_strictMode) {
				if ( offs == 0 )
					setSelectedItem(null); //null is valid for non-mandatory fields
				//so if cursor is at start of line allow it
				// otherwise keep old item selected if there is no better match
				else 
					item = comboBox.getSelectedItem();
				// undo the insertion as there isn't a valid match
				offs = offs-str.length();
				UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
			}
			else {
				return;
			}
		}

		if (item != null)
			setText(item.toString());
		else setText("");
		// select the completed part so it can be overwritten easily
		highlightCompletedText(offs+str.length());
	}

	/**
	 * Set text
	 * @param text
	 */
	public void setText(String text) {
		try {
			// remove all text and insert the completed string
			super.remove(0, getLength());
			super.insertString(0, text, null);
		} catch (BadLocationException e) {
			throw new RuntimeException(e.toString());
		}
	}

	private void highlightCompletedText(int start) {
		editor.setCaretPosition(getLength());
		if (getLength() < start)
			start = getLength();
		editor.moveCaretPosition(start);
	}

	private void setSelectedItem(Object item) {
		selecting = true;
		model.setSelectedItem(item);
		selecting = false;
	}

	private Object lookupItem(String pattern) {
		Object selectedItem = model.getSelectedItem();
		// only search for a different item if the currently selected does not match
		if (selectedItem != null && startsWithIgnoreCase(selectedItem.toString(), pattern)) {
			return selectedItem;
		} else {
			// iterate over all items
			for (int i=0, n=model.getSize(); i < n; i++) {
				Object currentItem = model.getElementAt(i);
				// current item starts with the pattern?
				if (currentItem != null && startsWithIgnoreCase(currentItem.toString(), pattern)) {
					return currentItem;
				}
			}
		}

		return null;
	}

	/**
	 * Set strict mode. If the strict mode is enabled, you can't enter any other values than the
	 * ones from combo box list. 
	 * @param mode true if strict mode
	 */
	public void setStrictMode (boolean mode) {
		m_strictMode = mode;
	}
	
	/**
	 * Checks if str1 starts with str2 (ignores case, trim leading whitespaces, strip diacritics)
	 * @param str1
	 * @param str2
	 * @return true if str1 starts with str2
	 */ 
	protected boolean startsWithIgnoreCase(String str1, String str2) {
		String s1 = org.adempiere.util.StringUtils.stripDiacritics(str1.toUpperCase()).replaceAll("^\\s+", "");;
		String s2 = org.adempiere.util.StringUtils.stripDiacritics(str2.toUpperCase()).replaceAll("^\\s+", "");;
		return s1.startsWith(s2);
	}

}