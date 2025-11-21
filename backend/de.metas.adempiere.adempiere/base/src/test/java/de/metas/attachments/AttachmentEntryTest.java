/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.attachments;

import com.google.common.collect.ImmutableList;
import de.metas.CreatedUpdatedInfo;
import de.metas.adempiere.model.I_M_Product;
import de.metas.common.util.time.SystemTime;
import de.metas.user.UserId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class AttachmentEntryTest
{
	private I_C_BPartner bpartnerRecord;
	private I_M_Product productRecord;
	private I_M_Warehouse warehouseRecord;
	private CreatedUpdatedInfo createdUpdatedInfo;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_User_ID, 10); // will be in the attachment-entry's CreatedUpdatedInfo

		bpartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bpartnerRecord);

		productRecord = newInstance(I_M_Product.class);
		saveRecord(productRecord);

		warehouseRecord = newInstance(I_M_Warehouse.class);
		saveRecord(warehouseRecord);

		createdUpdatedInfo = CreatedUpdatedInfo.builder().createdBy(UserId.METASFRESH).created(SystemTime.asZonedDateTime()).updatedBy(UserId.METASFRESH).updated(SystemTime.asZonedDateTime()).build();
	}


}
