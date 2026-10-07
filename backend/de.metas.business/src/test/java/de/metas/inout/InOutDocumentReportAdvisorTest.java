/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.inout;

import de.metas.bpartner.service.BPartnerPrintFormatRepository;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.inout.impl.InOutBL;
import de.metas.order.IOrderDAO;
import de.metas.order.impl.OrderDAO;
import de.metas.report.DefaultPrintFormatsRepository;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.DocumentReportInfo;
import de.metas.report.PrintFormatId;
import de.metas.report.PrintFormatRepository;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_AD_PrintFormat;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.compiere.model.X_C_DocType.DOCBASETYPE_MaterialDelivery;

/**
 * Boundary test for {@link InOutDocumentReportAdvisor} that pins the caller-side null-order guard.
 * <p>
 * {@link DocumentReportAdvisorUtil#isDropShip} now requires a {@code @NonNull} order. A manual shipment
 * has no {@code C_Order} ({@code C_Order_ID = 0}), so {@link InOutDocumentReportAdvisor} must guard the
 * missing order (treat it as not-a-drop-ship) BEFORE calling {@code isDropShip}. Without that guard, the
 * lombok {@code @NonNull} contract makes {@code getDocumentReportInfo} throw a {@code NullPointerException}
 * for a manual shipment -- this test would then fail (it is the RED->GREEN pin for the guard). The
 * "null order -> not a drop-ship" semantic used to live inside {@code isDropShip} and its base-module test.
 */
public class InOutDocumentReportAdvisorTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);

		Services.registerService(IInOutBL.class, new InOutBL());
		Services.registerService(IOrderDAO.class, new OrderDAO());
	}

	@Test
	public void manualShipmentWithoutOrder_isNotDropShip_andBuildsReportInfo()
	{
		final InOutDocumentReportAdvisor advisor = new InOutDocumentReportAdvisor(createUtil());

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		save(bpartner);
		final I_C_BPartner_Location bpartnerLocation = newInstance(I_C_BPartner_Location.class);
		bpartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		save(bpartnerLocation);

		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(DOCBASETYPE_MaterialDelivery);
		docType.setDocumentCopies(1);
		save(docType);

		// an AD_PrintFormat carrying a report (Jasper) process, so the advisor resolves the report process id
		final I_AD_PrintFormat printFormat = newInstance(I_AD_PrintFormat.class);
		printFormat.setJasperProcess_ID(540001);
		save(printFormat);
		final PrintFormatId printFormatId = PrintFormatId.ofRepoId(printFormat.getAD_PrintFormat_ID());

		// a MANUAL shipment: no C_Order at all
		final I_M_InOut manualShipment = newInstance(I_M_InOut.class);
		manualShipment.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		manualShipment.setC_BPartner_Location_ID(bpartnerLocation.getC_BPartner_Location_ID());
		manualShipment.setC_DocType_ID(docType.getC_DocType_ID());
		manualShipment.setC_Order_ID(0);
		manualShipment.setDocumentNo("MANUAL-1");
		save(manualShipment);

		final TableRecordReference recordRef = TableRecordReference.of(I_M_InOut.Table_Name, manualShipment.getM_InOut_ID());

		// must NOT throw (would NPE via @NonNull isDropShip if the caller did not guard the null order)
		final DocumentReportInfo reportInfo = advisor.getDocumentReportInfo(recordRef, printFormatId, null);

		Assertions.assertThat(reportInfo).isNotNull();
		Assertions.assertThat(reportInfo.getDocumentNo()).isEqualTo("MANUAL-1");
		// no matching C_BP_PrintFormat row -> auto-print not suppressed (a manual shipment is not a drop-ship)
		Assertions.assertThat(reportInfo.isSuppressAutoPrint()).isFalse();
	}

	private DocumentReportAdvisorUtil createUtil()
	{
		return new DocumentReportAdvisorUtil(
				new BPartnerBL(new UserRepository()),
				new PrintFormatRepository(),
				new DefaultPrintFormatsRepository(),
				new BPartnerPrintFormatRepository());
	}
}
