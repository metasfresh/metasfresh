package org.adempiere.ad.window.process;

import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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


public interface IWindowUIElementsGeneratorConsumer
{
	void consume(I_AD_UI_Section uiSection, I_AD_Tab parent);

	void consume(I_AD_UI_Column uiColumn, I_AD_UI_Section parent);

	void consume(I_AD_UI_ElementGroup uiElementGroup, I_AD_UI_Column parent);

	void consume(I_AD_UI_Element uiElement, I_AD_UI_ElementGroup parent);

	void consume(I_AD_UI_ElementField uiElementField, I_AD_UI_Element parent);
}