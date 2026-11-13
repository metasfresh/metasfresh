package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.match_inv.M_MatchInv_StepDefData;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StepDefDataTest
{
	@Test
	void getTableName_modelInterface()
	{
		assertThat(new C_BPartner_StepDefData().getTableName()).isEqualTo(I_C_BPartner.Table_Name);
	}

	@Test
	void getTableName_pojo()
	{
		assertThat(new M_MatchInv_StepDefData().getTableName()).isNull();
	}

	@Test
	void putRecord_pojo_failsClearly()
	{
		final M_MatchInv_StepDefData matchInvTable = new M_MatchInv_StepDefData();
		final StepDefDataIdentifier identifier = StepDefDataIdentifier.ofString("matchInv");
		final TableRecordReference recordRef = TableRecordReference.of("M_MatchInv", 1);

		assertThatThrownBy(() -> matchInvTable.putRecord(identifier, recordRef))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("holds POJOs");
	}
}
