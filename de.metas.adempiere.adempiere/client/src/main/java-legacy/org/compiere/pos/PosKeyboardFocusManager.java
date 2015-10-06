/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/

package org.compiere.pos;

import java.awt.DefaultKeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import org.compiere.util.CLogger;

/**
 * POS Keyboard Focus Manager
 * 
 * @author Comunidad de Desarrollo OpenXpertya 
 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
 *         *Copyright (c) Jorg Janke
 * @version $Id: PosKeyboardFocusManager.java,v 1.2 2004/07/11 19:50:12 jjanke
 *          Exp $
 */
public class PosKeyboardFocusManager extends DefaultKeyboardFocusManager
		implements ActionListener {

	/**
	 * PosKeyboardFocusManager
	 */
	public PosKeyboardFocusManager() {
		super();
	} //	PosKeyboardFocusManager

	/** FirstIn First Out List */
	private LinkedList<KeyEvent>	m_fifo = new LinkedList<KeyEvent>();

	/** Last Key Type */
	private long m_lastWhen = 0;

	public long getLastWhen() {
		return m_lastWhen;
	}

	/** Timer */
	private javax.swing.Timer m_timer = null;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(PosKeyboardFocusManager.class);

	/**
	 * Dispose
	 */
	public void dispose() {
		if (m_timer != null)
			m_timer.stop();
		m_timer = null;
		if (m_fifo != null)
			m_fifo.clear();
		m_fifo = null;
	} //	dispose

	/**
	 * Start Timer
	 */
	public void start() {
		//	Unqueue time - 200 ms
		int delay = 200;
		log.fine("PosKeyboardFocusManager.start - " + delay); 
		if (m_timer == null)
			m_timer = new javax.swing.Timer(delay, this);
		if (!m_timer.isRunning())
			m_timer.start();
	} //	start

	/**
	 * Stop Timer
	 */
	public void stop() {
		log.fine("PosKeyboardFocusManager.stop - " + m_timer); 

		if (m_timer != null)
			m_timer.stop();
	} //	stop

	/***************************************************************************
	 * Dispatch Key Event - queue
	 * 
	 * @param eevent
	 *            event
	 * @return true
	 */
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getID() == KeyEvent.KEY_PRESSED) {
			//	Keyboard Repeat: 485 - then 31
		//	log.fine( "PosKeyboardFocusManager.dispatchKeyEvent - " 
			// - "
			//		+ event.getWhen() + " - " + (event.getWhen() - m_lastWhen));
			m_lastWhen = event.getWhen();
		}
		if (m_timer == null)
			super.dispatchKeyEvent(event);
		else
			m_fifo.add(event);
		return true;
	} //	displatchEvent

	/**
	 * Action Performed - unqueue
	 * 
	 * @param e
	 *            event
	 */
	public void actionPerformed(ActionEvent e) {
		if (m_timer == null)
			return;
	//	log.fine( "actionPerformed - " + m_fifo.size()); 
		while (m_fifo.size() > 0) {
			KeyEvent event = (KeyEvent) m_fifo.removeFirst();
			super.dispatchKeyEvent(event);
		}
	} //	actionPerformed

} //	PosKeyboardFocusManager
