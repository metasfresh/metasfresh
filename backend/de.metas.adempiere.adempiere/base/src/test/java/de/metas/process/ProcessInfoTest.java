package de.metas.process;

import de.metas.common.util.time.SystemTime;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.AbstractStringAssert;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class ProcessInfoTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Nested
	class filenamePattern
	{
		@BeforeEach
		void beforeEach()
		{
			SystemTime.setFixedTimeSource(LocalDateTime.parse("2023-10-11T12:13:14").atZone(ZoneId.of("Europe/Berlin")));
		}

		private AbstractStringAssert<?> assertProcessWithFilenamePattern(@Nullable final String filenamePattern)
		{
			final I_AD_Process adProcessRecord = InterfaceWrapperHelper.newInstance(I_AD_Process.class);
			adProcessRecord.setAD_Process_ID(1110002);
			adProcessRecord.setValue("MyReportValue");
			adProcessRecord.setName("My Report Name");
			adProcessRecord.setType(ProcessType.Java.getCode());
			adProcessRecord.setFilenamePattern(filenamePattern);
			InterfaceWrapperHelper.save(adProcessRecord);

			final I_AD_PInstance adPInstanceRecord = InterfaceWrapperHelper.newInstance(I_AD_PInstance.class);
			adPInstanceRecord.setAD_PInstance_ID(1234567);
			adPInstanceRecord.setAD_Process_ID(adProcessRecord.getAD_Process_ID());
			InterfaceWrapperHelper.setValue(adPInstanceRecord, I_AD_PInstance.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());
			adPInstanceRecord.setAD_Org_ID(OrgId.MAIN.getRepoId());
			adPInstanceRecord.setAD_User_ID(UserId.METASFRESH.getRepoId());
			adPInstanceRecord.setAD_Role_ID(1234);
			InterfaceWrapperHelper.save(adPInstanceRecord);

			final ProcessInfo processInfo = ProcessInfo.builder()
					.setCtx(Env.getCtx())
					.setPInstanceId(PInstanceId.ofRepoId(adPInstanceRecord.getAD_PInstance_ID()))
					.build();

			final ReportResultDataTarget reportResultDataTarget = processInfo.getReportResultDataTarget();
			return assertThat(reportResultDataTarget.getTargetFilename());
		}

		@Test
		void empty()
		{
			assertProcessWithFilenamePattern(null).isNull();
			assertProcessWithFilenamePattern("").isNull();
			assertProcessWithFilenamePattern("   ").isNull();
		}

		@Test
		void AD_PInstance_ID_and_AD_Process_Name()
		{
			assertProcessWithFilenamePattern("@AD_PInstance_ID@_@AD_Process.Name@.csv").isEqualTo("1234567_My Report Name.csv");
		}

		@Test
		void date()
		{
			assertProcessWithFilenamePattern("Report_@Date@.csv").isEqualTo("Report_2023-10-11_121314000.csv");
		}

		@Test
		void AD_Process_Value()
		{
			assertProcessWithFilenamePattern("Report_@AD_Process.Value@.csv").isEqualTo("Report_MyReportValue.csv");
		}

		@Test
		void AD_Process_ID()
		{
			assertProcessWithFilenamePattern("Report_@AD_Process_ID@.csv").isEqualTo("Report_1110002.csv");
		}
	}
}