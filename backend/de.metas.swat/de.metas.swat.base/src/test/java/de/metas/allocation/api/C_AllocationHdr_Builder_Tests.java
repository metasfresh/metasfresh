package de.metas.allocation.api;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.util.Services;

public class C_AllocationHdr_Builder_Tests
{
	private ITrxManager trxManager;

	@BeforeEach
	public void before()
	{
		AdempiereTestHelper.get().init();

		this.trxManager = Services.get(ITrxManager.class);
	}

	@Nested
	public class testConstructorChecksTrx
	{
		@Test
		public void threadInheritedTrx_exists()
		{
			final String trxName = trxManager.createTrxName("DUMMY");
			trxManager.setThreadInheritedTrxName(trxName);

			new C_AllocationHdr_Builder(); // does not throw exception
		}

		@Test
		public void threadInheritedTrx_doesNotExist()
		{
			trxManager.setThreadInheritedTrxName(null);

			assertThatThrownBy(() -> new C_AllocationHdr_Builder())
					.isInstanceOf(AdempiereException.class);
		}
	}
}
