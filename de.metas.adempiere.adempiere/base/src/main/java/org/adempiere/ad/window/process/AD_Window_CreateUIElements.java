package org.adempiere.ad.window.process;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;
import org.compiere.model.I_AD_Window;

import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class AD_Window_CreateUIElements extends JavaProcess
{
	@Override
	protected String doIt() throws Exception
	{
		final WindowUIElementsGenerator generator = WindowUIElementsGenerator.forConsumer(new IWindowUIElementsGeneratorConsumer()
		{
			@Override
			public void consume(final I_AD_UI_Section uiSection, final I_AD_Tab parent)
			{
				saveRecord(uiSection);
				addLog("Created {} for {}", uiSection, parent);
			}

			@Override
			public void consume(final I_AD_UI_Column uiColumn, final I_AD_UI_Section parent)
			{
				saveRecord(uiColumn);
				addLog("Created {} (SeqNo={}) for {}", uiColumn, uiColumn.getSeqNo(), parent);
			}

			@Override
			public void consume(final I_AD_UI_ElementGroup uiElementGroup, final I_AD_UI_Column parent)
			{
				saveRecord(uiElementGroup);
				addLog("Created {} for {}", uiElementGroup, parent);
			}

			@Override
			public void consume(final I_AD_UI_Element uiElement, final I_AD_UI_ElementGroup parent)
			{
				saveRecord(uiElement);
				addLog("Created {} (AD_Field={}, seqNo={}) for {}", uiElement, uiElement.getAD_Field(), uiElement.getSeqNo(), parent);
			}

			@Override
			public void consume(final I_AD_UI_ElementField uiElementField, final I_AD_UI_Element parent)
			{
				saveRecord(uiElementField);
				addLog("Created {} (AD_Field={}) for {}", uiElementField, uiElementField.getAD_Field(), parent);
			}
		});

		final I_AD_Window adWindow = getRecord(I_AD_Window.class);
		generator.generate(adWindow);

		return MSG_OK;
	}

}
