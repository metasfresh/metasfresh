package de.metas.util.lang;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import de.metas.JsonObjectMapperHolder;
import de.metas.audit.data.model.DataExportAuditLogId;
import de.metas.contracts.commission.mediated.model.MediatedCommissionSettingsLineId;
import de.metas.externalsystem.other.ExternalSystemOtherConfigId;
import de.metas.invoice.InvoiceVerificationRunId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.fresh.base
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

public class All_RepoIdAware_Classes_Test
{
	private static final SkipRules skipRules = new SkipRules()
			.skip(de.metas.bpartner.BPartnerLocationId.class)
			.skip(de.metas.bpartner.BPartnerContactId.class)
			.skip(de.metas.bpartner.BPartnerBankAccountId.class)
			.skip(de.metas.bpartner.user.role.UserAssignedRoleId.class)
			.skip(de.metas.bpartner.department.BPartnerDepartmentId.class)
			//
			.skip(de.metas.calendar.PeriodId.class)
			.skip(de.metas.calendar.YearId.class)
			//
			.skip(de.metas.contracts.commission.licensefee.model.LicenseFeeSettingsLineId.class)
			.skip(de.metas.contracts.commission.mediated.model.MediatedCommissionSettingsLineId.class)
			.skip(de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryId.class)
			.skip(de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryDetailId.class)
			.skip(de.metas.contracts.pricing.trade_margin.CustomerTradeMarginLineId.class)
			//
			.skip(de.metas.externalsystem.IExternalSystemChildConfigId.class)
			.skip(de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfigProductMappingId.class)
			//
			.skip(de.metas.invoice.InvoiceLineId.class)
			//
			.skip(de.metas.phonecall.PhonecallSchemaVersionId.class)
			.skip(de.metas.phonecall.PhonecallSchemaVersionLineId.class)
			//
			.skip(org.adempiere.warehouse.LocatorId.class)
			//
			.skip(de.metas.customs.CustomsInvoiceLineId.class)
			.skip(de.metas.shipment.ShipmentDeclarationLineId.class)
			//
			.skip(org.eevolution.api.PPOrderRoutingActivityId.class)
			.skip(org.eevolution.api.PPOrderRoutingProductId.class)
			.skip(de.metas.material.planning.pporder.PPRoutingActivityId.class)
			.skip(de.metas.material.planning.pporder.PPRoutingProductId.class)
			//
			.skip(de.metas.printing.HardwareTrayId.class)
			//
			.skip(ServiceRepairProjectCostCollectorId.class)
			.skip(ServiceRepairProjectTaskId.class)
			.skip(InvoiceVerificationRunId.class)

			.skip(DataExportAuditLogId.class)
			//
			.skip(ExternalSystemOtherConfigId.class)
			//
			.skip(MediatedCommissionSettingsLineId.class)
			;

	private static ObjectMapper jsonMapper;

	@BeforeAll
	public static void beforeAll()
	{
		jsonMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	@ParameterizedTest
	@ArgumentsSource(RepoIdAwareArgumentsProvider.class)
	public void test(@NonNull final Class<? extends RepoIdAware> repoIdClass) throws Exception
	{
		skipRules.assumeNotSkipped(repoIdClass);

		RepoIdAwares.ofRepoId(100, repoIdClass);
		RepoIdAwares.ofRepoIdOrNull(100, repoIdClass);

		testEquals(repoIdClass);
		testSerializableAsNumber(repoIdClass);
	}

	private void testEquals(final Class<? extends RepoIdAware> repoIdClass)
	{
		final RepoIdAware repoId1 = RepoIdAwares.ofRepoId(100, repoIdClass);
		final RepoIdAware repoId2 = RepoIdAwares.ofRepoId(100, repoIdClass);
		if (!Objects.equals(repoId1, repoId2))
		{
			throw Check.newException(repoIdClass + ": " + repoId1 + " not equals with " + repoId2);
		}
	}

	private void testSerializableAsNumber(@NonNull final Class<? extends RepoIdAware> repoIdClass) throws Exception
	{
		final RepoIdAware repoId = RepoIdAwares.ofRepoId(100, repoIdClass);
		final String json = jsonMapper.writeValueAsString(repoId);
		final String expectedJson = String.valueOf(repoId.getRepoId());
		if (!Objects.equals(json, expectedJson))
		{
			throw Check.newException(repoIdClass + ": Got wrong JSON for " + repoId + ". Expected `" + expectedJson + "` but got `" + json + "`");
		}

		final RepoIdAware repoId2 = jsonMapper.readValue(json, repoIdClass);
		if (!Objects.deepEquals(repoId, repoId2))
		{
			throw Check.newException(repoIdClass + ": Got wrong deserialization object for JSON `" + json + "`. Expected `" + repoId + "` but got `" + repoId2 + "`");
		}
	}

	//
	//
	//
	//
	//

	@ToString
	private static class SkipRules
	{
		private final HashSet<String> classNames = new HashSet<>();

		public void assumeNotSkipped(final Class<? extends RepoIdAware> repoIdClass)
		{
			final boolean skipped = isSkip(repoIdClass);
			Assumptions.assumeTrue(!skipped, "skipped");
		}

		public boolean isSkip(@NonNull final Class<? extends RepoIdAware> repoIdClass)
		{
			final String className = repoIdClass.getName();

			return classNames.contains(className);
		}

		public SkipRules skip(@NonNull final Class<? extends RepoIdAware> repoIdClass)
		{
			return skip(repoIdClass.getName());
		}

		public SkipRules skip(@NonNull final String className)
		{
			classNames.add(className);
			return this;
		}
	}

	//
	//
	//

	public static class RepoIdAwareArgumentsProvider implements ArgumentsProvider
	{
		@Override
		public Stream<? extends Arguments> provideArguments(final ExtensionContext context)
		{
			return provideClasses().map(Arguments::of);
		}

		private Stream<Class<? extends RepoIdAware>> provideClasses()
		{
			final Stopwatch stopwatch = Stopwatch.createStarted();

			final Reflections reflections = new Reflections(new ConfigurationBuilder()
					.addUrls(ClasspathHelper.forClassLoader())
					.setScanners(new SubTypesScanner()));

			final Set<Class<? extends RepoIdAware>> classes = reflections.getSubTypesOf(RepoIdAware.class);

			if (classes.isEmpty())
			{
				throw new RuntimeException("No classes found. Might be because for some reason Reflections does not work correctly with maven surefire plugin."
						+ "\n See https://github.com/metasfresh/metasfresh/issues/4773.");
			}

			stopwatch.stop();
			System.out.println("Found " + classes.size() + " classes implementing " + RepoIdAware.class + ". Took " + stopwatch + ". ");

			return classes.stream()
					.sorted(Comparator.comparing(Class::getName));
		}
	}

}
