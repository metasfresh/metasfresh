package de.metas.process;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Supplier;

import org.junit.Test;

import lombok.Getter;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class ProcessPreconditionsResolutionTest
{
	@Test
	public void test_firstRejectOrElseAccept_with_3rejections()
	{
		final MockedProcessPreconditionsResolutionSupplier[] suppliers = new MockedProcessPreconditionsResolutionSupplier[] {
				MockedProcessPreconditionsResolutionSupplier.rejectWithInternalReason("reject0"),
				MockedProcessPreconditionsResolutionSupplier.rejectWithInternalReason("reject1"),
				MockedProcessPreconditionsResolutionSupplier.rejectWithInternalReason("reject2")
		};
		final ProcessPreconditionsResolution result = ProcessPreconditionsResolution.firstRejectOrElseAccept(suppliers);
		assertThat(result).isSameAs(suppliers[0].getValue());
		assertThat(suppliers[0].getCallsCount()).isEqualTo(1);
		assertThat(suppliers[1].getCallsCount()).isEqualTo(0);
		assertThat(suppliers[2].getCallsCount()).isEqualTo(0);
	}

	@Test
	public void test_firstRejectOrElseAccept_with_firstIsAccept()
	{
		final MockedProcessPreconditionsResolutionSupplier[] suppliers = new MockedProcessPreconditionsResolutionSupplier[] {
				MockedProcessPreconditionsResolutionSupplier.accept(),
				MockedProcessPreconditionsResolutionSupplier.rejectWithInternalReason("reject1")
		};
		final ProcessPreconditionsResolution result = ProcessPreconditionsResolution.firstRejectOrElseAccept(suppliers);
		assertThat(result).isSameAs(suppliers[1].getValue());
		assertThat(suppliers[0].getCallsCount()).isEqualTo(1);
		assertThat(suppliers[1].getCallsCount()).isEqualTo(1);
	}

	@Test
	public void test_firstRejectOrElseAccept_with_Accepts()
	{
		final MockedProcessPreconditionsResolutionSupplier[] suppliers = new MockedProcessPreconditionsResolutionSupplier[] {
				MockedProcessPreconditionsResolutionSupplier.accept(),
				MockedProcessPreconditionsResolutionSupplier.accept(),
				MockedProcessPreconditionsResolutionSupplier.accept()
		};
		final ProcessPreconditionsResolution result = ProcessPreconditionsResolution.firstRejectOrElseAccept(suppliers);
		assertThat(result).isEqualTo(ProcessPreconditionsResolution.accept());
		assertThat(suppliers[0].getCallsCount()).isEqualTo(1);
		assertThat(suppliers[1].getCallsCount()).isEqualTo(1);
		assertThat(suppliers[2].getCallsCount()).isEqualTo(1);
	}

	private static class MockedProcessPreconditionsResolutionSupplier implements Supplier<ProcessPreconditionsResolution>
	{
		public static MockedProcessPreconditionsResolutionSupplier rejectWithInternalReason(final String reasonStr)
		{
			return new MockedProcessPreconditionsResolutionSupplier(ProcessPreconditionsResolution.rejectWithInternalReason(reasonStr));
		}

		public static MockedProcessPreconditionsResolutionSupplier accept()
		{
			return new MockedProcessPreconditionsResolutionSupplier(ProcessPreconditionsResolution.accept());
		}

		@Getter
		private final ProcessPreconditionsResolution value;
		@Getter
		private int callsCount = 0;

		private MockedProcessPreconditionsResolutionSupplier(final ProcessPreconditionsResolution value)
		{
			this.value = value;
		}

		@Override
		public ProcessPreconditionsResolution get()
		{
			callsCount++;
			return value;
		}
	}
}
