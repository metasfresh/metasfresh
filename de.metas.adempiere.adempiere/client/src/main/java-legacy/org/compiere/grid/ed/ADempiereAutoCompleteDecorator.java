/*
 * $Id: AutoCompleteDecorator.java,v 1.9 2007/01/29 08:52:45 Bierhance Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
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

import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

import org.jdesktop.swingx.autocomplete.AbstractAutoCompleteAdaptor;
import org.jdesktop.swingx.autocomplete.AutoCompleteDocument;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;
import org.jdesktop.swingx.autocomplete.TextComponentAdaptor;

/**
 * Adds Ctrl+Tab invoked action to run through all potential auto completion values.
 */
public class ADempiereAutoCompleteDecorator
{    
    /**
     * Enables automatic completion for the given JTextComponent based on the
     * items contained in the given <tt>List</tt>.
     * @param textComponent the text component that will be used for automatic
     * completion.
     * @param items contains the items that are used for autocompletion
     * @param strictMatching <tt>true</tt>, if only given items should be allowed to be entered
     */
    public static void decorate(JTextComponent textComponent, List items, boolean strictMatching) {
        decorate(textComponent, items, strictMatching, ObjectToStringConverter.DEFAULT_IMPLEMENTATION);
    }
    
    /**
     * Enables automatic completion for the given JTextComponent based on the
     * items contained in the given <tt>List</tt>.
     * @param items contains the items that are used for autocompletion
     * @param textComponent the text component that will be used for automatic
     * completion.
     * @param strictMatching <tt>true</tt>, if only given items should be allowed to be entered
     * @param stringConverter the converter used to transform items to strings
     */
    public static void decorate(JTextComponent textComponent, List items, boolean strictMatching, ObjectToStringConverter stringConverter) {
        AbstractAutoCompleteAdaptor adaptor = new TextComponentAdaptor(textComponent, items);
        AutoCompleteDocument document = new AutoCompleteDocument(adaptor, strictMatching, stringConverter);
        decorate(textComponent, document, adaptor);
    }
    
    /**
     * Decorates a given text component for automatic completion using the
     * given AutoCompleteDocument and AbstractAutoCompleteAdaptor.
     * 
     * 
     * @param textComponent a text component that should be decorated
     * @param document the AutoCompleteDocument to be installed on the text component
     * @param adaptor the AbstractAutoCompleteAdaptor to be used
     */
    public static void decorate(JTextComponent textComponent, AutoCompleteDocument document, final AbstractAutoCompleteAdaptor adaptor) {
        // install the document on the text component
        textComponent.setDocument(document);
        
        // mark entire text when the text component gains focus
        // otherwise the last mark would have been retained which is quiet confusing
        textComponent.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                JTextComponent textComponent = (JTextComponent) e.getSource();
                adaptor.markEntireText();
            }
        });
        
        // Tweak some key bindings
        InputMap editorInputMap = textComponent.getInputMap();
        if (document.isStrictMatching()) {
            // move the selection to the left on VK_BACK_SPACE
            editorInputMap.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_BACK_SPACE, 0), DefaultEditorKit.selectionBackwardAction);
            // ignore VK_DELETE and CTRL+VK_X and beep instead when strict matching
            editorInputMap.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0), errorFeedbackAction);
            editorInputMap.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK), errorFeedbackAction);
        } else {
            ActionMap editorActionMap = textComponent.getActionMap();
            // leave VK_DELETE and CTRL+VK_X as is
            // VK_BACKSPACE will move the selection to the left if the selected item is in the list
            // it will delete the previous character otherwise
            editorInputMap.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_BACK_SPACE, 0), "nonstrict-backspace");
            editorActionMap.put("nonstrict-backspace", new NonStrictBackspaceAction(
                    editorActionMap.get(DefaultEditorKit.deletePrevCharAction),
                    editorActionMap.get(DefaultEditorKit.selectionBackwardAction),
                    adaptor));
            editorInputMap.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_TAB, java.awt.event.KeyEvent.CTRL_DOWN_MASK), "NextMatchAction");
            editorActionMap.put("NextMatchAction", new NextMatchAction(textComponent, document, adaptor));
        }
    }
    
    static class NonStrictBackspaceAction extends TextAction {
        /**
		 * 
		 */
		private static final long serialVersionUID = 7260685268274438388L;
		Action backspace;
        Action selectionBackward;
        AbstractAutoCompleteAdaptor adaptor;
        
        public NonStrictBackspaceAction(Action backspace, Action selectionBackward, AbstractAutoCompleteAdaptor adaptor) {
            super("nonstrict-backspace");
            this.backspace = backspace;
            this.selectionBackward = selectionBackward;
            this.adaptor = adaptor;
        }
        
        public void actionPerformed(ActionEvent e) {
            if (adaptor.listContainsSelectedItem()) {
                selectionBackward.actionPerformed(e);
            } else {
                backspace.actionPerformed(e);
            }
        }
    }
    
    static class NextMatchAction extends TextAction {
    	/**
		 * 
		 */
		private static final long serialVersionUID = 2987063701364646859L;
		JTextComponent textComponent;
    	AutoCompleteDocument document;
    	final AbstractAutoCompleteAdaptor adaptor;
    	final List<String> items;
    	int currentIndex = 0;
    	
        public NextMatchAction(JTextComponent textComponent, AutoCompleteDocument document, final AbstractAutoCompleteAdaptor adaptor) {
            super("NextMatchAction");
            this.textComponent = textComponent;
            this.document = document;
            this.adaptor = adaptor;
            items = new ArrayList<String>(adaptor.getItemCount());
            for (int i = 0; i < adaptor.getItemCount(); i++) {
            	Object o = adaptor.getItem(i);
				items.add((o!=null)?(String) adaptor.getItem(i):"");
			}
            Collections.sort(items);
        }
        
        private String getNextMatch(String start) {
        	for (int i = currentIndex; i < items.size(); i++) {
				if(items.get(i).toLowerCase().startsWith(start.toLowerCase())) {
					currentIndex = i+1;
					return items.get(i);
				}
			}
        	currentIndex=0;
        	return start;
        }
        
        /**
         * Shows the next match.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
        	 JTextComponent target = getTextComponent(e);
             if ((target != null) && (e != null)) {
            	 if ((! target.isEditable()) || (! target.isEnabled())) {
            		 UIManager.getLookAndFeel().provideErrorFeedback(target);
            		 return;
            	 }
                 String content = target.getText();
                 if (content != null && target.getSelectionStart()>0) {
                	 content = content.substring(0,target.getSelectionStart());
                 }
                 if (content != null) {
                    	target.setText(getNextMatch(content));
						adaptor.markText(content.length());
                 }
             }
         }
    }
    
    /**
     * A TextAction that provides an error feedback for the text component that invoked
     * the action. The error feedback is most likely a "beep".
     */
    static Object errorFeedbackAction = new TextAction("provide-error-feedback") {
        /**
		 * 
		 */
		private static final long serialVersionUID = 6251452041316544686L;

		public void actionPerformed(ActionEvent e) {
            UIManager.getLookAndFeel().provideErrorFeedback(getTextComponent(e));
        }
    };
    
}
