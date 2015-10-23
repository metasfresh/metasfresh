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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.I_C_POSKey;
import org.compiere.model.I_C_POSKeyLayout;
import org.compiere.model.X_C_POSKeyLayout;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

/**
 * On Screen Keyboard
 *
 * @author Paul Bowden (Adaxa Pty Ltd)
 */
public class POSKeyboard extends CDialog implements ActionListener, PosKeyListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3296839634889851637L;

	/** Logger */
	private static final transient CLogger log = CLogger.getCLogger(POSKeyboard.class);

	private final I_C_POSKeyLayout keylayout;

	private PosTextField field;

	/**
	 * Constructor
	 *
	 * @param posPanel POS Panel
	 */
	public POSKeyboard(final PosBasePanel posPanel, final int C_POSKeyLayout_ID, final PosTextField field, final String title)
	{
		this(posPanel, C_POSKeyLayout_ID);

		setTitle(title);
		setPosTextField(field);
	}

	public POSKeyboard(final PosBasePanel posPanel, final int keyLayoutId)
	{
		super(Env.getFrame(posPanel), true);

		keylayout = InterfaceWrapperHelper.create(posPanel.getCtx(), keyLayoutId, I_C_POSKeyLayout.class, ITrx.TRXNAME_None);

		init(keyLayoutId);
	}

	private final JFormattedTextField text = new JFormattedTextField();

	private Map<Integer, I_C_POSKey> keys;

	/**
	 * Initialize
	 *
	 * @param startText
	 * @param POSKeyLayout_ID
	 */
	public void init(final int POSKeyLayout_ID)
	{
		final CPanel panel = new CPanel();
		getContentPane().add(panel);

		// Content
		panel.setLayout(new MigLayout("fill"));

		if (keylayout.getPOSKeyLayoutType().equals(X_C_POSKeyLayout.POSKEYLAYOUTTYPE_Numberpad))
		{
			text.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		panel.add(text, "north, growx, h 30!, wrap, gap 10 10 10 10");

		final PosKeyPanel keys = new PosKeyPanel(POSKeyLayout_ID, this);
		panel.add(keys, "center, growx, growy");

		final ConfirmPanel confirm = ConfirmPanel.builder()
				.withCancelButton(true)
				.withResetButton(true)
				.build();
		confirm.setActionListener(this);

		final Dimension buttonDim = new Dimension(50, 50);
		confirm.getResetButton().setPreferredSize(buttonDim);
		confirm.getOKButton().setPreferredSize(buttonDim);
		confirm.getCancelButton().setPreferredSize(buttonDim);

		panel.add(confirm, "south");

		pack();

		setLocationByPlatform(true);

		text.requestFocusInWindow();
	}

	/**
	 * Dispose - Free Resources
	 */
	@Override
	public void dispose()
	{
		if (keys != null)
		{
			keys.clear();
			keys = null;
		}
		super.dispose();
	}

	/**
	 * Action Listener
	 *
	 * @param e event
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		final String action = e.getActionCommand();
		if (action == null || action.length() == 0)
		{
			return;
		}
		else if (action.equals(ConfirmPanel.A_RESET))
		{
			if (keylayout.getPOSKeyLayoutType().equals(X_C_POSKeyLayout.POSKEYLAYOUTTYPE_Numberpad))
			{
				text.setText("0");
			}
			else
			{
				text.setText("");
			}
			try
			{
				text.commitEdit();
			}
			catch (final ParseException e1)
			{
				log.log(Level.FINE, "JFormattedTextField commit failed");
			}
		}
		else if (action.equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}
		else if (action.equals(ConfirmPanel.A_OK))
		{
			field.setText(text.getText());
			try
			{
				field.commitEdit();
			}
			catch (final ParseException e1)
			{
				log.log(Level.FINE, "JFormattedTextField commit failed");
			}
			dispose();
		}

		log.info("PosSubBasicKeys - actionPerformed: " + action);
	}

	@Override
	public void keyReturned(final I_C_POSKey key)
	{

		final String entry = key.getText();
		final String old = text.getText();
		int caretPos = text.getCaretPosition();
		if (text.getSelectedText() != null)
		{
			caretPos = text.getSelectionStart();
		}
		final String head = old.substring(0, caretPos);
		if (text.getSelectedText() != null)
		{
			caretPos = text.getSelectionEnd();
		}
		final String tail = old.substring(caretPos, old.length());

		if (entry != null && !entry.isEmpty())
		{
			if (keylayout.getPOSKeyLayoutType().equals(X_C_POSKeyLayout.POSKEYLAYOUTTYPE_Keyboard))
			{
				if (key.getText() != null)
				{
					text.setText(head + entry + tail);
				}
			}
			else if (keylayout.getPOSKeyLayoutType().equals(X_C_POSKeyLayout.POSKEYLAYOUTTYPE_Numberpad))
			{
				if (entry.equals("."))
				{
					text.setText(head + entry + tail);
				}
				if (entry.equals(","))
				{
					text.setText(head + entry + tail);
				}
				else if (entry.equals("C"))
				{
					text.setText("0");
				}
				else
				{
					try
					{
						final int number = Integer.parseInt(entry);		// test if number
						if (number >= 0 && number <= 9)
						{
							text.setText(head + number + tail);
						}
						// greater than 9, add to existing
						else
						{
							final Object current = text.getValue();
							if (current == null)
							{
								text.setText(Integer.toString(number));
							}
							else if (current instanceof BigDecimal)
							{
								text.setText(((BigDecimal)current).add(
										new BigDecimal(Integer.toString(number))).toPlainString());
							}
							else if (current instanceof Integer)
							{
								text.setText(Integer.toString((Integer)current + number));
							}
							else if (current instanceof Long)
							{
								text.setText(Long.toString((Long)current + number));
							}
							else if (current instanceof Double)
							{
								text.setText(Double.toString((Double)current + number));
							}
						}

					}
					catch (final NumberFormatException e)
					{
						// ignore non-numbers
					}
				}

				try
				{
					text.commitEdit();
				}
				catch (final ParseException e)
				{
					log.log(Level.FINE, "JFormattedTextField commit failed");
				}
			}
		}
	}

	public void setPosTextField(final PosTextField posTextField)
	{
		field = posTextField;
		text.setFormatterFactory(field.getFormatterFactory());
		text.setText(field.getText());
		text.setValue(field.getValue());
		getContentPane().invalidate();
	}
}
