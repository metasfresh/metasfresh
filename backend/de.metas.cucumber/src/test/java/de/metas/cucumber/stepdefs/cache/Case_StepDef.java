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

package de.metas.cucumber.stepdefs.cache;

<<<<<<< HEAD:backend/de.metas.cucumber/src/test/java/de/metas/cucumber/stepdefs/cache/Case_StepDef.java
import de.metas.cache.CacheMgt;
import io.cucumber.java.en.Then;

public class Case_StepDef
=======
import de.metas.mpackage.PackageId;
import org.compiere.model.I_M_Package;

/**
 * Having a dedicated class to help the IOC-framework injecting the right instances, if a step-def needs more than one.
 */
public class M_Package_StepDefData extends StepDefData<I_M_Package> implements StepDefDataGetIdAware<PackageId, I_M_Package>
>>>>>>> c3f4e747b6 (bugfix: don't use the overall weight of the whole to delivery to each delivery package):backend/de.metas.cucumber/src/test/java/de/metas/cucumber/stepdefs/M_Package_StepDefData.java
{
	@Then("the metasfresh cache is reset")
	public void processMetasfreshResponse()
	{
		CacheMgt.get().reset();
	}
<<<<<<< HEAD:backend/de.metas.cucumber/src/test/java/de/metas/cucumber/stepdefs/cache/Case_StepDef.java
}
=======

	@Override
	public PackageId extractIdFromRecord(final I_M_Package record)
	{
		return PackageId.ofRepoId(record.getM_Package_ID());
	}
}
>>>>>>> c3f4e747b6 (bugfix: don't use the overall weight of the whole to delivery to each delivery package):backend/de.metas.cucumber/src/test/java/de/metas/cucumber/stepdefs/M_Package_StepDefData.java
