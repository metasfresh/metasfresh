/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs;

<<<<<<< HEAD:backend/de.metas.cucumber/src/test/java/de/metas/cucumber/stepdefs/DHL_ShipmentOrder_StepDefData.java
import de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrder;
=======
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.tax.api.TaxCategoryId;
import org.compiere.model.I_C_TaxCategory;
>>>>>>> ce978dd873 (improve readability of cucumber tests):backend/de.metas.cucumber/src/test/java/de/metas/cucumber/stepdefs/pricing/C_TaxCategory_StepDefData.java

/**
 * Having a dedicated class to help the IOC-framework injecting the right instances, if a step-def needs more than one.
 */
<<<<<<< HEAD:backend/de.metas.cucumber/src/test/java/de/metas/cucumber/stepdefs/DHL_ShipmentOrder_StepDefData.java
public class DHL_ShipmentOrder_StepDefData extends StepDefData<I_DHL_ShipmentOrder>
=======
public class C_TaxCategory_StepDefData extends StepDefData<I_C_TaxCategory> implements StepDefDataGetIdAware<TaxCategoryId, I_C_TaxCategory>
>>>>>>> ce978dd873 (improve readability of cucumber tests):backend/de.metas.cucumber/src/test/java/de/metas/cucumber/stepdefs/pricing/C_TaxCategory_StepDefData.java
{
	public DHL_ShipmentOrder_StepDefData()
	{
		super(I_DHL_ShipmentOrder.class);
	}

	@Override
	public TaxCategoryId extractIdFromRecord(final I_C_TaxCategory record)
	{
		return TaxCategoryId.ofRepoId(record.getC_TaxCategory_ID());
	}
}