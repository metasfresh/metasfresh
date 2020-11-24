package de.metas.handlingunits.client.terminal.mmovement.model.split.impl;

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


import java.math.BigDecimal;


import de.metas.handlingunits.client.terminal.editor.model.ISplittableHUKey;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.mmovement.model.ILTCUModel;
import de.metas.handlingunits.client.terminal.mmovement.model.impl.AbstractLTCUModelTest;
import de.metas.handlingunits.model.I_M_HU;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests keys and their quantities in correlation to the LTCU model
 *
 * @author al
 */
// Ignored for now (not working, not completed)
@Disabled
public class HUSplitDefaultQtyHandlerTest extends AbstractLTCUModelTest
{
	public HUSplitDefaultQtyHandlerTest()
	{
		super();
	}

	@Test
	public void test01()
	{
		//
		// Create 1 x LU [9 x TU]
		final I_M_HU huToSplit = createIncomingLoadingUnit(huItemIFCO_10, materialItemProductTomato_10, CU_QTY_85, BigDecimal.valueOf(100)); // 85 x Tomato

		//
		// Create a key to split (mimic HU Editor selection)
		final ISplittableHUKey huToSplitKey = new HUKey(keyFactory, huToSplit, NULL_DocumentLine);

		final ILTCUModel model = new HUSplitModel(keyFactory.getTerminalContext(), huToSplitKey);
		setLTCUModel(model);

		//
		// Create a split qty handler and calculate defaults for the model
		final HUSplitDefaultQtyHandler defaultQtyHandler = new HUSplitDefaultQtyHandler(huToSplitKey, false);
		defaultQtyHandler.calculateDefaultQtys(getLTCUModel());
		
		// FIXME: finish it!
	}
}
