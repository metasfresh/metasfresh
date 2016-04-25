package de.metas.document.service.impl;

/*
 * #%L
 * de.metas.swat.base
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


import static org.adempiere.test.UnitTestTools.mock;
import static org.adempiere.test.UnitTestTools.serviceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.db.IDBService;
import org.junit.Assert;
import org.junit.Test;

import de.metas.document.impl.DocumentPA;

public class DocumentPATest {

	private final String TRX_NAME = "trxName";

	@Test
	public void updateDocuments() {

		final Map<String, Object> mocks = new HashMap<String, Object>();

		final int bPartnerLocationId = 23;

		final DocumentPA documentPA = new DocumentPA();

		final PreparedStatement pstmt0 = mock(PreparedStatement.class,
				"pstmt0", mocks);
		final PreparedStatement pstmt1 = mock(PreparedStatement.class,
				"pstmt1", mocks);
		final PreparedStatement pstmt2 = mock(PreparedStatement.class,
				"pstmt2", mocks);
		final PreparedStatement pstmt3 = mock(PreparedStatement.class,
				"pstmt3", mocks);

		try {
			pstmt0.setInt(1, bPartnerLocationId);
			expect(pstmt0.executeUpdate()).andReturn(1);

			pstmt1.setInt(1, bPartnerLocationId);
			expect(pstmt1.executeUpdate()).andReturn(1);

			pstmt2.setInt(1, bPartnerLocationId);
			expect(pstmt2.executeUpdate()).andReturn(1);

			pstmt3.setInt(1, bPartnerLocationId);
			expect(pstmt3.executeUpdate()).andReturn(1);

		} catch (SQLException e) {
		}

		final IDBService db = serviceMock(IDBService.class, mocks);

		expect(db.mkPstmt(DocumentPA.UPDATE_ORDERS_BILL_LOCATION, TRX_NAME))
				.andStubReturn(pstmt0);
		expect(db.mkPstmt(DocumentPA.UPDATE_ORDERS_BPARTNER_LOCATION, TRX_NAME))
				.andStubReturn(pstmt1);

		expect(db.mkPstmt(DocumentPA.UPDATE_INOUT_BPARTNER_LOCATION, TRX_NAME))
				.andStubReturn(pstmt2);

		expect(
				db.mkPstmt(DocumentPA.UPDATE_INVOICE_BPARTNER_LOCATION,
						TRX_NAME)).andStubReturn(pstmt3);

		db.close(pstmt0);
		db.close(pstmt1);
		db.close(pstmt2);
		db.close(pstmt3);

		replay(mocks.values().toArray());
		final int result = documentPA.updateDocumentAddresses(
				bPartnerLocationId, TRX_NAME);
		verify(mocks.values().toArray());

		Assert.assertEquals(result, 4);
	}

}
