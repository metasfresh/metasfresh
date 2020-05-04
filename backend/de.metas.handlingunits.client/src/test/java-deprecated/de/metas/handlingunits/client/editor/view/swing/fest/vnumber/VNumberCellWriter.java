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


import java.awt.Point;

import javax.swing.JTable;

import org.compiere.grid.ed.VNumber;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.AbstractJTableCellWriter;

/**
 * http://docs.codehaus.org/display/FEST/Custom+Cell+Editors
 * 
 * @author al
 */
public class VNumberCellWriter extends AbstractJTableCellWriter
{
	protected final VNumberDriver driver;

	public VNumberCellWriter(final Robot robot)
	{
		super(robot);
		driver = new VNumberDriver(robot);
	}

	@Override
	@RunsInEDT
	public void enterValue(final JTable table, final int row, final int column, final String value)
	{
		final VNumber editor = doStartCellEditing(table, row, column);
		// this.driver.replaceText(editor, value);
		// don't use replace text, but set value directly in the VNumber
		driver.setText(editor, value);
		stopCellEditing(table, row, column);
	}

	@Override
	@RunsInEDT
	public void startCellEditing(final JTable table, final int row, final int column)
	{
		doStartCellEditing(table, row, column);
	}

	@RunsInEDT
	private VNumber doStartCellEditing(final JTable table, final int row, final int column)
	{
		final Point cellLocation = AbstractJTableCellWriter.cellLocation(table, row, column, location);
		VNumber vNumber = null;

		vNumber = activateEditorWithClick(table, row, column, cellLocation);

		cellEditor(AbstractJTableCellWriter.cellEditor(table, row, column));
		return vNumber;
	}

	@RunsInEDT
	private VNumber activateEditorWithClick(final JTable table, final int row, final int column, final Point cellLocation)
	{
		robot.click(table, cellLocation);
		return waitForEditorActivation(table, row, column);
	}

	@RunsInEDT
	private VNumber waitForEditorActivation(final JTable table, final int row, final int column)
	{
		return waitForEditorActivation(table, row, column, VNumber.class);
	}
}
