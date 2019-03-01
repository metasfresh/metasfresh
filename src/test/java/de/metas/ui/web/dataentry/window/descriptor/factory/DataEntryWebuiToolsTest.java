package de.metas.ui.web.dataentry.window.descriptor.factory;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.user.CreatedUpdatedInfo;
import org.adempiere.user.UserId;
import org.compiere.model.I_AD_Message;
import org.junit.Before;
import org.junit.Test;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.impl.MsgBL;
import de.metas.util.Services;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DataEntryWebuiToolsTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(IMsgBL.class, new MsgBL()); // for this test we need the "real thing"

		final I_AD_Message messageRecord = newInstance(I_AD_Message.class);
		messageRecord.setValue(DataEntryWebuiTools.MSG_CREATED_UPDATED_INFO);
		messageRecord.setMsgText("Erstellt von {0} am {1,dateTime}; Aktualisiert von {2} am {3,dateTime}");
		saveRecord(messageRecord);
	}

	@Test
	public void extractCreatedUpdatedInfo()
	{
		final ZonedDateTime created = ZonedDateTime.now();
		CreatedUpdatedInfo createdUpdatedInfo = CreatedUpdatedInfo.createNew(UserId.ofRepoId(10), created).updated(UserId.ofRepoId(20), created.plusMinutes(10));
		final ITranslatableString result = DataEntryWebuiTools.extractCreatedUpdatedInfo(createdUpdatedInfo);

		assertThat(result).isNotNull();
		// TODO
//		final String resultTrl = result.translate("de_DE");
//		assertThat(resultTrl).isEqualTo("Erstellt von {0} am {1,dateTime}; Aktualisiert von {2} am {3,dateTime}");
	}

}
