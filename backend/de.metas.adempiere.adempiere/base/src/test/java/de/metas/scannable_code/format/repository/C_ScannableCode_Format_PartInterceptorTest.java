package de.metas.scannable_code.format.repository;

import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.scannable_code.format.repository.interceptor.C_ScannableCode_Format_Part;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_ScannableCode_Format_Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class C_ScannableCode_Format_PartInterceptorTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_ScannableCode_Format_Part());
	}

	private static I_C_ScannableCode_Format_Part save(final Integer endNo)
	{
		final I_C_ScannableCode_Format_Part record = InterfaceWrapperHelper.newInstance(I_C_ScannableCode_Format_Part.class);
		record.setStartNo(33);
		record.setDataType(ScannableCodeFormatPartType.LotNo.getCode());
		if (endNo != null)
		{
			record.setEndNo(endNo);
		}
		InterfaceWrapperHelper.saveRecord(record);
		InterfaceWrapperHelper.refresh(record);
		return record;
	}

	private static Object endNoOrNull(final I_C_ScannableCode_Format_Part record)
	{
		return InterfaceWrapperHelper.getValueOrNull(record, I_C_ScannableCode_Format_Part.COLUMNNAME_EndNo);
	}

	@Test
	void zero_isSavedAsBlank()
	{
		assertThat(endNoOrNull(save(0))).isNull();
	}

	@Test
	void filledValue_isKept()
	{
		assertThat(endNoOrNull(save(38))).isEqualTo(38);
	}

	@Test
	void blank_staysBlank()
	{
		assertThat(endNoOrNull(save(null))).isNull();
	}
}
