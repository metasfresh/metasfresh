package org.compiere.grid.ed;

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


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import org.compiere.util.CLogger;

/**
 * @author Santhosh Kumar T - santhosh@in.fiorano.com
 * 			<li>Initial contribution - http://www.jroller.com/santhosh/date/20050620#file_path_autocompletion
 * @author Teo Sarca , www.arhipac.ro
 * 			<li>added timed triggering
 * 			<li>refactored
 * @author Cristina Ghita , www.arhipac.ro
 * 			<li>refactored
 */
public abstract class AutoCompleter implements MouseListener
{ 

	private static final long serialVersionUID = -5135462631871597277L;
	private static final String AUTOCOMPLETER = "AUTOCOMPLETER"; //NOI18N
	private static final int PopupDelayMillis = 500;

	protected final CLogger log = CLogger.getCLogger(getClass()); 

	final JList listBox = new JList(); 
	final JTextComponent textBox;
	final private JPopupMenu popup = new JPopupMenu();
	private boolean m_empty = false; 
	
	private final Timer timer = new Timer(PopupDelayMillis, new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			showPopup();
		}
	});

	public AutoCompleter(JTextComponent comp)
	{ 
		textBox = comp; 
		textBox.putClientProperty(AUTOCOMPLETER, this);

		JScrollPane scroll = new JScrollPane(listBox); 
		scroll.setBorder(null); 

		listBox.setFocusable( false ); 
		listBox.addMouseListener(this);
		scroll.getVerticalScrollBar().setFocusable( false ); 
		scroll.getHorizontalScrollBar().setFocusable( false );

		popup.setBorder(BorderFactory.createLineBorder(Color.black)); 
		popup.add(scroll); 

		if(textBox instanceof JTextField)
		{ 
			textBox.registerKeyboardAction(showAction, KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), JComponent.WHEN_FOCUSED);
			textBox.getDocument().addDocumentListener(documentListener); 
		}

		textBox.registerKeyboardAction(upAction, KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), JComponent.WHEN_FOCUSED); 
		textBox.registerKeyboardAction(hidePopupAction, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_FOCUSED); 
		popup.addPopupMenuListener(new PopupMenuListener() { 
			public void popupMenuWillBecomeVisible(PopupMenuEvent e)
			{ 
			} 
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
			{ 
				textBox.unregisterKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0)); 
			} 
			public void popupMenuCanceled(PopupMenuEvent e)
			{ 
			} 
		}); 
		listBox.setRequestFocusEnabled(false); 
	} 

	public boolean isEmpty()
	{
		return m_empty;
	}

	public void setEmpty(boolean empty)
	{
		m_empty = empty;
	}
	
	private static final Action acceptAction = new AbstractAction()
	{ 
		private static final long serialVersionUID = -3950389799318995148L;
		public void actionPerformed(ActionEvent e)
		{ 
			JComponent tf = (JComponent)e.getSource();
			AutoCompleter completer = (AutoCompleter)tf.getClientProperty(AUTOCOMPLETER); 
			completer.popup.setVisible(false); 
			if (completer.listBox.getSelectedValue() == null)
			{
				String txt = completer.textBox.getText();
				ListModel lm = completer.listBox.getModel();
				for (int index = 0; index < lm.getSize(); index++)
				{
					if (startsWithIgnoreCase(lm.getElementAt(index).toString(), txt))
					{
						completer.acceptedListItem(lm.getElementAt(index));
						break;
					}
				}
			}
			else
			{
				completer.acceptedListItem(completer.listBox.getSelectedValue());
			}
		} 
	};

	private final DocumentListener documentListener = new DocumentListener()
	{ 
		public void insertUpdate(DocumentEvent e)
		{ 
			showPopupDelayed();
		} 
		public void removeUpdate(DocumentEvent e)
		{ 
			showPopupDelayed(); 
		} 
		public void changedUpdate(DocumentEvent e)
		{
		} 
	}; 

	private void showPopupDelayed()
	{
		log.finest("showPopupDelayed..");
		timer.setRepeats(false);
		timer.start();
	}

	private void showPopup()
	{ 
		log.finest("showPopup");
		popup.setVisible(false); 
		if (textBox.isEnabled() && updateListData() && listBox.getModel().getSize() != 0)
		{ 
			setEmpty(false);
			if(!(textBox instanceof JTextField))
			{
				textBox.getDocument().addDocumentListener(documentListener);
			}
			textBox.registerKeyboardAction(acceptAction, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED); 
			int size = listBox.getModel().getSize(); 
			listBox.setVisibleRowCount(size<10 ? size : 10); 

			int x = 0; 
			try{ 
				int pos = Math.min(textBox.getCaret().getDot(), textBox.getCaret().getMark()); 
				x = textBox.getUI().modelToView(textBox, pos).x; 
			} catch(BadLocationException e){ 
				// this should never happen!!! 
				e.printStackTrace(); 
			} 
			popup.show(textBox, x, textBox.getHeight()); 
		}
		else
		{
			popup.setVisible(false);
			setEmpty(true);
		}
		textBox.requestFocus(); 
	} 

	static Action showAction = new AbstractAction()
	{ 
		private static final long serialVersionUID = 8868536979000734628L;
		public void actionPerformed(ActionEvent e)
		{ 
			JComponent tf = (JComponent)e.getSource(); 
			AutoCompleter completer = (AutoCompleter)tf.getClientProperty(AUTOCOMPLETER); 
			if(tf.isEnabled())
			{ 
				if(completer.popup.isVisible()) 
					completer.selectNextPossibleValue(); 
				else 
					completer.showPopup();
			} 
		} 
	}; 

	private static final Action upAction = new AbstractAction()
	{ 
		private static final long serialVersionUID = 2200136359410394434L;

		public void actionPerformed(ActionEvent e)
		{ 
			JComponent tf = (JComponent)e.getSource(); 
			AutoCompleter completer = (AutoCompleter)tf.getClientProperty(AUTOCOMPLETER); 
			if(tf.isEnabled())
			{ 
				if(completer.popup.isVisible()) 
					completer.selectPreviousPossibleValue(); 
			} 
		} 
	}; 

	private static final Action hidePopupAction = new AbstractAction()
	{ 
		private static final long serialVersionUID = -5683983067872135654L;

		public void actionPerformed(ActionEvent e)
		{ 
			JComponent tf = (JComponent)e.getSource(); 
			AutoCompleter completer = (AutoCompleter)tf.getClientProperty(AUTOCOMPLETER); 
			if(tf.isEnabled()) 
				completer.popup.setVisible(false); 
		} 
	}; 

	/** 
	 * Selects the next item in the list.  It won't change the selection if the 
	 * currently selected item is already the last item. 
	 */ 
	protected void selectNextPossibleValue()
	{ 
		int si = listBox.getSelectedIndex(); 

		if(si < listBox.getModel().getSize() - 1){ 
			listBox.setSelectedIndex(si + 1); 
			listBox.ensureIndexIsVisible(si + 1); 
		} 
	} 

	/** 
	 * Selects the previous item in the list.  It won't change the selection if the 
	 * currently selected item is already the first item. 
	 */ 
	protected void selectPreviousPossibleValue()
	{ 
		int si = listBox.getSelectedIndex(); 

		if(si > 0){ 
			listBox.setSelectedIndex(si - 1); 
			listBox.ensureIndexIsVisible(si - 1); 
		} 
	}

	/**
	 * Checks if str1 starts with str2 (ignores case, trim whitespaces, strip diacritics)
	 * @param str1
	 * @param str2
	 * @return true if str1 starts with str2
	 */ 
	protected static boolean startsWithIgnoreCase(String str1, String str2)
	{
		String s1 = org.adempiere.util.StringUtils.stripDiacritics(str1.toUpperCase()).trim();
		String s2 = org.adempiere.util.StringUtils.stripDiacritics(str2.toUpperCase()).trim();
		return s1.startsWith(s2);
	}

	
	/**
	 * Update list model depending on the data in textfield 
	 * @return
	 */
	protected abstract boolean updateListData(); 

	/**
	 * User has selected some item in the list. Update textfield accordingly... 
	 * @param selected
	 */
	protected abstract void acceptedListItem(Object selected); 
}
