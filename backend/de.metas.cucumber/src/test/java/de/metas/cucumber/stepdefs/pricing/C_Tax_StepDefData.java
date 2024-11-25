/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

<<<<<<<< HEAD:backend/de.metas.cucumber/src/test/java/de/metas/cucumber/stepdefs/pricing/C_Tax_StepDefData.java
package de.metas.cucumber.stepdefs.pricing;

import de.metas.cucumber.stepdefs.StepDefData;
import org.compiere.model.I_C_Tax;
========
package de.metas.cucumber.stepdefs.purchasecandidate;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;
>>>>>>>> new_dawn_uat:backend/de.metas.cucumber/src/test/java/de/metas/cucumber/stepdefs/purchasecandidate/C_PurchaseCandidate_Alloc_StepDefData.java

/**
 * Having a dedicated class to help the IOC-framework injecting the right instances, if a step-def needs more than one.
 */
<<<<<<<< HEAD:backend/de.metas.cucumber/src/test/java/de/metas/cucumber/stepdefs/pricing/C_Tax_StepDefData.java
public class C_Tax_StepDefData extends StepDefData<I_C_Tax>
{
	public C_Tax_StepDefData()
	{
		super(I_C_Tax.class);
========
public class C_PurchaseCandidate_Alloc_StepDefData extends StepDefData<I_C_PurchaseCandidate_Alloc>
{
	public C_PurchaseCandidate_Alloc_StepDefData()
	{
		super(I_C_PurchaseCandidate_Alloc.class);
>>>>>>>> new_dawn_uat:backend/de.metas.cucumber/src/test/java/de/metas/cucumber/stepdefs/purchasecandidate/C_PurchaseCandidate_Alloc_StepDefData.java
	}
}
