package de.metas.adempiere.form.terminal;

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

import java.math.BigDecimal;
import java.text.ParseException;

import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Created, maintained and invoked by {@link TerminalKeyPanel}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */ class TerminalKeyTextAdapter implements ITerminalKeyListener
{
	private final transient Logger log = LogManager.getLogger(getClass());
	private final ITerminalTextField textField;
	private final IKeyLayout keylayout;

	/* package */  TerminalKeyTextAdapter(final ITerminalTextField textField, final IKeyLayout keylayout)
	{
		this.textField = textField;
		this.keylayout = keylayout;
	}

	@Override
	public void keyReturned(final ITerminalKey key)
	{
		final String head = textField.getTextHead();
		final String tail = textField.getTextTail();

		final String entry = key.getText();
		if (entry != null && !entry.isEmpty())
		{
			if (keylayout.isText())
			{
				if (POSKey.KEY_Backspace.equals(entry))
				{
					if (head != null && head.length() > 0)
					{
						final String head2 = head.substring(0, head.length() - 1);
						textField.setText(head2 + tail);
						textField.setCaretPosition(head2.length());
					}
					else
					{
						; // nothing
					}
				}
				else
				{
					final String head2 = head + entry;
					textField.setText(head2 + tail);
					textField.setCaretPosition(head2.length());
				}
			}
			else if (keylayout.isNumeric())
			{
				if (entry.equals("."))
				{
					textField.setText(head + entry + tail);
				}
				if (entry.equals(","))
				{
					textField.setText(head + entry + tail);
				}
				else if (entry.equals("C"))
				{
					textField.setText("0");
				}
				else
				{
					try
					{
						final int number = Integer.parseInt(entry); // test if number
						if (number >= 0 && number <= 9)
						{
							textField.setText(head + number + tail);
						}
						// greater than 9, add to existing
						else
						{
							final Object current = textField.getValue();
							if (current == null)
							{
								textField.setText(Integer.toString(number));
							}
							else if (current instanceof BigDecimal)
							{
								textField.setText(((BigDecimal)current).add(
										new BigDecimal(Integer.toString(number))).toPlainString());
							}
							else if (current instanceof Integer)
							{
								textField.setText(Integer.toString((Integer)current + number));
							}
							else if (current instanceof Long)
							{
								textField.setText(Long.toString((Long)current + number));
							}
							else if (current instanceof Double)
							{
								textField.setText(Double.toString((Double)current + number));
							}
							else if (current instanceof String)
							{
								final String currentStr = ((String)current).trim();
								if (!currentStr.isEmpty())
								{
									final BigDecimal currentBD = new BigDecimal(currentStr);
									final BigDecimal currentBDNew = currentBD.add(BigDecimal.valueOf(number));
									textField.setText(currentBDNew.toPlainString());
								}
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
					textField.commitEdit();
				}
				catch (final ParseException e)
				{
					log.debug("JFormattedTextField commit failed", e);
				}
			}
		}
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void keyReturning(final ITerminalKey key)
	{
	}

	@Override
	public String toString()
	{
		return "TerminalKeyTextAdapter [textField=" + textField + ", keylayout=" + keylayout + "]";
	}


}
