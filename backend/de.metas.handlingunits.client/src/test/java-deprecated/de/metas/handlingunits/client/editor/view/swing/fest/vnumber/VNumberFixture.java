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


import org.compiere.grid.ed.VNumber;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.fixture.GenericComponentFixture;

/**
 * http://docs.codehaus.org/display/FEST/Extending+FEST-Swing
 * 
 * @author al
 */
public class VNumberFixture extends GenericComponentFixture<VNumber>
{
	private VNumberDriver driver;

	public VNumberFixture(final Robot robot, final VNumber target)
	{
		super(robot, target);
		createDriver();
	}

	public VNumberFixture(final Robot robot, final ComponentDriver driver, final VNumber target)
	{
		super(robot, driver, target);
		createDriver();
	}

	private void createDriver()
	{
		driver(new VNumberDriver(robot));
	}

	public VNumberFixture deleteText()
	{
		driver.deleteText(target);
		return this;
	}

	public VNumberFixture enterText(final String text)
	{
		driver.enterText(target, text);
		return this;
	}

	public VNumberFixture setText(final String text)
	{
		driver.setText(target, text);
		return this;
	}
}
