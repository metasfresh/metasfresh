package de.metas.handlingunits.client.editor.view.swing.fest.vnumber;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.regex.Pattern;

import javax.swing.text.DefaultEditorKit;

import org.compiere.grid.ed.VNumber;
import org.fest.assertions.Assertions;
import org.fest.assertions.Description;
import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.ComponentStateValidator;
import org.fest.swing.driver.JComponentDriver;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.util.Strings;

/**
 * Copy-paste from {@link org.fest.swing.driver.JTextComponentDriver}
 * 
 * Package level static methods which had to be overridden are also here.
 * 
 * @author al
 */
public class VNumberDriver extends JComponentDriver
{
	private static final String EDITABLE_PROPERTY = "editable";
	private static final String TEXT_PROPERTY = "text";

	public VNumberDriver(final Robot robot)
	{
		super(robot);
	}

	@RunsInEDT
	public void deleteText(final VNumber vNumber)
	{
		selectAll(vNumber);
		invokeAction(vNumber, DefaultEditorKit.deletePrevCharAction);
	}

	@RunsInEDT
	public void replaceText(final VNumber vNumber, final String text)
	{
		if (Strings.isEmpty(text))
		{
			throw new IllegalArgumentException("The text to enter should not be empty");
		}
		selectAll(vNumber);
		enterText(vNumber, text);
	}

	@RunsInEDT
	public void selectAll(final VNumber vNumber)
	{
		// VNumberDriver.validateAndScrollToPosition(vNumber, 0);
		VNumberDriver.selectAllText(vNumber);
	}

	@RunsInEDT
	public void enterText(final VNumber vNumber, final String text)
	{
		focusAndWaitForFocusGain(vNumber);
		robot.enterText(text);
	}

	@RunsInEDT
	public void setText(final VNumber vNumber, final String text)
	{
		focusAndWaitForFocusGain(vNumber);
		VNumberDriver.setTextIn(vNumber, text);
		robot.waitForIdle();
	}

	@RunsInEDT
	private static int indexOfText(final VNumber vNumber, final String text)
	{
		return GuiActionRunner.execute(new GuiQuery<Integer>()
		{
			@Override
			protected Integer executeInEDT()
			{
				ComponentStateValidator.validateIsEnabledAndShowing(vNumber);
				final String actualText = vNumber.getValue().toString();
				if (Strings.isEmpty(actualText))
				{
					return -1;
				}
				return actualText.indexOf(text);
			}
		});
	}

	@RunsInCurrentThread
	private static boolean isRectangleVisible(final VNumber vNumber, final Rectangle r)
	{
		final Rectangle visible = vNumber.getVisibleRect();
		return visible.contains(r.x, r.y);
	}

	@RunsInEDT
	public void requireText(final VNumber vNumber, final String expected)
	{
		TextAssertCP.verifyThat(textOf(vNumber)).as(VNumberDriver.textProperty(vNumber)).isEqualOrMatches(expected);
	}

	@RunsInEDT
	public void requireText(final VNumber vNumber, final Pattern pattern)
	{
		TextAssertCP.verifyThat(textOf(vNumber)).as(VNumberDriver.textProperty(vNumber)).matches(pattern);
	}

	@RunsInEDT
	public void requireEmpty(final VNumber vNumber)
	{
		Assertions.assertThat(textOf(vNumber)).as(VNumberDriver.textProperty(vNumber)).isEmpty();
	}

	@RunsInEDT
	private static Description textProperty(final VNumber vNumber)
	{
		return ComponentDriver.propertyName(vNumber, VNumberDriver.TEXT_PROPERTY);
	}

	@RunsInEDT
	public void requireEditable(final VNumber vNumber)
	{
		assertEditable(vNumber, true);
	}

	@RunsInEDT
	public void requireNotEditable(final VNumber vNumber)
	{
		assertEditable(vNumber, false);
	}

	@RunsInEDT
	private void assertEditable(final VNumber vNumber, final boolean editable)
	{
		Assertions.assertThat(VNumberDriver.isEditable(vNumber)).as(VNumberDriver.editableProperty(vNumber)).isEqualTo(editable);
	}

	@RunsInEDT
	private static Description editableProperty(final VNumber vNumber)
	{
		return ComponentDriver.propertyName(vNumber, VNumberDriver.EDITABLE_PROPERTY);
	}

	@RunsInEDT
	public static void selectAllText(final VNumber vNumber)
	{
		GuiActionRunner.execute(new GuiTask()
		{
			@Override
			protected void executeInEDT()
			{
				vNumber.selectAll();
			}
		});
	}

	@RunsInEDT
	private static void setTextIn(final VNumber vNumber, final String text)
	{
		GuiActionRunner.execute(new GuiTask()
		{
			@Override
			protected void executeInEDT()
			{
				// wrap the String in a BigDecimal
				final Number textNumber = new BigDecimal(text);
				vNumber.setValue(textNumber);
			}
		});
	}

	@RunsInEDT
	private static boolean isEditable(final VNumber vNumber)
	{
		return GuiActionRunner.execute(new GuiQuery<Boolean>()
		{
			@Override
			protected Boolean executeInEDT()
			{
				return vNumber.isEnabled();
			}
		});
	}

	@RunsInEDT
	public String textOf(final VNumber vNumber)
	{
		return GuiActionRunner.execute(new GuiQuery<String>()
		{
			@Override
			protected String executeInEDT()
			{
				return vNumber.getValue().toString();
			}
		});
	}
}
