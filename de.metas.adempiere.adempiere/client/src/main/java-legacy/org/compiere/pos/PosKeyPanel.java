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

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.pos.IPOSKeyKayoutDAO;
import org.compiere.model.I_C_POSKey;
import org.compiere.model.I_C_POSKeyLayout;
import org.compiere.model.MImage;
import org.compiere.print.MPrintColor;
import org.compiere.print.MPrintFont;
import org.compiere.swing.CButton;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Services;

import org.compiere.util.Env;

/**
 * Button panel supporting multiple linked layouts
 * @author Paul Bowden
 * Adaxa Pty Ltd
 *
 */
public class PosKeyPanel extends CPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1773720355288801510L;


	/**
	 * 	Constructor
	 */
	public PosKeyPanel (int C_POSKeyLayout_ID, PosKeyListener caller)
	{
		if (C_POSKeyLayout_ID == 0)
			return;
		
		setLayout(cardLayout);
		add(createCard(C_POSKeyLayout_ID), Integer.toString(C_POSKeyLayout_ID));
		currentLayout = C_POSKeyLayout_ID;
		cardLayout.show(this, Integer.toString(C_POSKeyLayout_ID));
		this.caller = caller;
	}	//	PosSubFunctionKeys
	
	/** layout			*/
	private CardLayout cardLayout = new CardLayout();
	/** Map of map of keys */
	private Map<Integer, Map<Integer, I_C_POSKey>> keymap = new HashMap<>();
	/** Currently displayed layout	*/
	int currentLayout;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(PosKeyPanel.class);
	/** Caller			*/
	private PosKeyListener caller;


	/**
	 * @return
	 */
	private CPanel createCard(int C_POSKeyLayout_ID) {
		
		// already added
		if ( keymap.containsKey(C_POSKeyLayout_ID) )
		{
			return null;
		}
		
		CPanel card = new CPanel();
		card.setLayout(new MigLayout("fill, ins 0"));
		final I_C_POSKeyLayout keyLayout = InterfaceWrapperHelper.create(Env.getCtx(), C_POSKeyLayout_ID, I_C_POSKeyLayout.class, ITrx.TRXNAME_None);

		Color stdColor = Color.lightGray;
		if (keyLayout.getAD_PrintColor_ID() != 0)
		{
			MPrintColor color = MPrintColor.get(Env.getCtx(), keyLayout.getAD_PrintColor_ID());
			stdColor = color.getColor();
		}
		Font stdFont = AdempierePLAF.getFont_Field();
		if (keyLayout.getAD_PrintFont_ID() != 0)
		{
			MPrintFont font = MPrintFont.get(keyLayout.getAD_PrintFont_ID());
			stdFont = font.getFont();
		}
		
		if (keyLayout.getC_POSKeyLayout_ID() <= 0)
			return null;
		final List<I_C_POSKey> keys = Services.get(IPOSKeyKayoutDAO.class).retrievePOSKeys(keyLayout);
		
		Map<Integer, I_C_POSKey> map = new HashMap<>(keys.size());

		keymap.put(C_POSKeyLayout_ID, map);
		
		int COLUMNS = 3;	//	Min Columns
		int ROWS = 3;		//	Min Rows
		int noKeys = keys.size();
		int cols = keyLayout.getColumns();
		if ( cols == 0 )
			cols = COLUMNS;
		
		int buttons = 0;
		
		log.debug( "PosSubFunctionKeys.init - NoKeys=" + noKeys 
			+ ", Cols=" + cols);
		//	Content
		CPanel content = new CPanel (new MigLayout("fill, wrap " + Math.max(cols, 3)));
		String buttonSize = "h 50, w 50, growx, growy, sg button,";
		for (final I_C_POSKey key :  keys)
		{
			
			if ( key.getSubKeyLayout_ID() > 0 )
			{
				CPanel subCard = createCard(key.getSubKeyLayout_ID());
				if ( subCard != null )
					add(subCard, Integer.toString(key.getSubKeyLayout_ID()));
			}
			
			map.put(key.getC_POSKey_ID(), key);
			Color keyColor = stdColor;
			Font keyFont = stdFont;
			StringBuffer buttonHTML = new StringBuffer("<html><p>");
			if (key.getAD_PrintColor_ID() != 0)
			{
				MPrintColor color = MPrintColor.get(Env.getCtx(), key.getAD_PrintColor_ID());
				keyColor = color.getColor();
			}
			
			if ( key.getAD_PrintFont_ID() != 0)
			{
				MPrintFont font = MPrintFont.get(key.getAD_PrintFont_ID());
				keyFont = font.getFont();
			}
			
			buttonHTML.append(key.getName());
			buttonHTML.append("</p></html>");
			log.debug( "#" + map.size() + " - " + keyColor); 
			CButton button = new CButton(buttonHTML.toString());
			button.setBackground(keyColor);
			button.setFont(keyFont);

			if ( key.getAD_Image_ID() != 0 )
			{
				MImage image = MImage.get(Env.getCtx(), key.getAD_Image_ID());
				Icon icon = image.getIcon();
				button.setIcon(icon);
				button.setVerticalTextPosition(SwingConstants.BOTTOM);
				button.setHorizontalTextPosition(SwingConstants.CENTER);
			}
			button.setFocusable(false);
			if ( !key.isActive() )
				button.setEnabled(false);
			button.setActionCommand(String.valueOf(key.getC_POSKey_ID()));
			button.addActionListener(this);
			String constraints = buttonSize;
			int size = 1;
			if ( key.getSpanX() > 1 )
			{
				constraints += "spanx " + key.getSpanX() + ",";
				size = key.getSpanX();
			}
			if ( key.getSpanY() > 1 )
			{
				constraints += "spany " + key.getSpanY() + ",";
				size = size*key.getSpanY();
			}
			buttons = buttons + size;
			content.add (button, constraints);
		}
		
		int rows = Math.max ((buttons / cols), ROWS);
		if ( buttons % cols > 0 )
			rows = rows + 1;
		
		for (int i = buttons; i < rows*cols; i++)
		{
			CButton button = new CButton("");
			button.setFocusable(false);
			button.setReadWrite(false);
			content.add (button, buttonSize);
		}
		
		CScrollPane scroll = new CScrollPane(content);
		// scroll.setPreferredSize(new Dimension( 600 - 20, 400-20));
		card.add (scroll, "growx, growy");
		// increase scrollbar width for touchscreen
		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(30, 0));
		scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0,30));
		return card;
	}

	/**
	 * 	Action Listener
	 *	@param e event
	 */
	public void actionPerformed (ActionEvent e)
	{
		String action = e.getActionCommand();
		if (action == null || action.length() == 0 || keymap == null)
			return;
		log.info( "PosSubFunctionKeys - actionPerformed: " + action);
		Map<Integer, I_C_POSKey> currentKeymap = keymap.get(currentLayout);
		
		try
		{
			int C_POSKey_ID = Integer.parseInt(action);
			I_C_POSKey key = currentKeymap.get(C_POSKey_ID);
			// switch layout
			if ( key.getSubKeyLayout_ID() > 0 )
			{
				currentLayout = key.getSubKeyLayout_ID();
				cardLayout.show(this, Integer.toString(key.getSubKeyLayout_ID()));
			}
			else
			{
				caller.keyReturned(key);
			}
		}
		catch (Exception ex)
		{
		}
		
	}	//	actionPerformed

}
