package de.metas.material.dispo.reconcile.process;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.reconcile.AtpReconciliationCommand;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfo;
import de.metas.user.UserId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Exercises {@link MD_Candidate_Reconcile_ATP#doIt()} directly. {@code doIt()} is {@code protected}, which a test
 * in the same package may call directly; {@link StockRepository} is swapped for a Mockito mock registered via
 * {@link SpringContextHolder#registerJUnitBean(Class, Object)} so no real DB or Spring context is needed - this
 * mirrors the JavaProcess unit-test pattern already used in this codebase (see
 * {@code de.metas.bpartner.process.CBPartnerUpdateMemoTest}: {@code process.init(ProcessInfo...)} then reflection
 * onto the {@code @Param} field, then {@code doIt()} called directly).
 * <p>
 * The pagination of the key selection is no longer asserted here: the drain moved into
 * {@code AtpKeySelectionDrainer}, and so did its coverage - see {@code AtpKeySelectionDrainerTest}.
 */
class MD_Candidate_Reconcile_ATPTest
{
	private StockRepository stockRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);

		stockRepository = Mockito.mock(StockRepository.class);
		SpringContextHolder.registerJUnitBean(StockRepository.class, stockRepository);
	}

	/**
	 * A run in a JVM without the material disposition engine must fail fast even when the selection matches no key
	 * at all - never report the misleading "Reconciled 0 of 0 matching key(s)" success that a per-key-only bean
	 * resolution would produce (the drain's per-key callback never runs, so the bean would never be resolved and
	 * the missing engine would never surface).
	 * <p>
	 * Reproducing the defect needs {@link SpringContextHolder#instance}{@code
	 * .getBean(AtpReconciliationCommand.class)} to hit its {@code catch (NoSuchBeanDefinitionException e)} branch in
	 * {@code reconciliationCommand()} - and {@link SpringContextHolder#getBean} only ever throws that specific
	 * exception when a real, non-null {@link org.springframework.context.ApplicationContext} is registered on the
	 * holder and genuinely lacks the bean; with no context registered at all it throws a plain,
	 * uncaught {@link AdempiereException} instead ("This unit test requires a spring ApplicationContext"), which
	 * would escape {@code doIt()} without ever exercising the fix.
	 * <p>
	 * So this test builds a real, profile-less {@link AnnotationConfigApplicationContext} scanning
	 * {@code de.metas.material.dispo.reconcile} - the webapi's actual situation, exactly as
	 * {@link de.metas.material.dispo.AtpReconcileContextStartupTest#webapiLikeContext_startsWithoutTheMaterialDispoEngine()}
	 * proves it for the package as a whole - and registers it on {@link SpringContextHolder#instance} for the
	 * duration of the call. {@link AtpReconciliationCommand} is {@code @Profile}-guarded, so that context genuinely
	 * has no such bean, and {@code context.getBean(AtpReconciliationCommand.class)} throws Spring's own
	 * {@code NoSuchBeanDefinitionException} - the exact branch {@code reconciliationCommand()} is written to catch.
	 * <p>
	 * <b>Why the mocks are registered as plain singletons, not via a {@code @Configuration} class of this test's
	 * own.</b> {@link de.metas.material.dispo.AtpReconcileContextStartupTest}'s Javadoc records that its first
	 * version placed such a config <i>inside</i> the scanned package, where the recursive
	 * {@link org.springframework.context.annotation.ComponentScan} picked it up and silently supplied the very bean
	 * whose absence the test exists to prove. This test class sits in {@code
	 * de.metas.material.dispo.reconcile.process} - itself under the scanned package, because {@code doIt()} is only
	 * callable from the same package - so a {@code @Configuration}/{@code @Component} class declared here would fall
	 * into exactly that trap. Registering the collaborator mocks as plain (unannotated) singleton beans instead means
	 * nothing in this file carries an annotation the scan could ever find.
	 */
	@Test
	void profileLessJvm_failsFastEvenWhenSelectionMatchesNothing() throws Exception
	{
		when(stockRepository.retrieveKeys(any(), any(), any(), anyInt(), anyInt()))
				.thenReturn(ImmutableList.of());

		try (final AnnotationConfigApplicationContext profileLessContext = newProfileLessReconcilePackageContext())
		{
			SpringContextHolder.instance.setApplicationContext(profileLessContext);
			try
			{
				final MD_Candidate_Reconcile_ATP process = newProcess();

				assertThatThrownBy(process::doIt)
						.as("a profile-less JVM must fail fast even on an empty selection - never reach the"
								+ " '0 of 0' success return")
						.isInstanceOf(AdempiereException.class)
						.hasMessageContaining(Profiles.PROFILE_MaterialDispo);
			}
			finally
			{
				SpringContextHolder.instance.clearApplicationContext();
			}
		}
	}

	/**
	 * @return a real, profile-less context scanning {@code de.metas.material.dispo.reconcile} - see this test's own
	 * Javadoc for why its two collaborator mocks are registered as plain singletons rather than through a
	 * {@code @Configuration} class of this test's own.
	 */
	private static AnnotationConfigApplicationContext newProfileLessReconcilePackageContext()
	{
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getBeanFactory().registerSingleton("stockRepository", Mockito.mock(StockRepository.class));
		context.getBeanFactory().registerSingleton("candidateRepositoryRetrieval", Mockito.mock(CandidateRepositoryRetrieval.class));
		context.scan("de.metas.material.dispo.reconcile");
		context.refresh();
		return context;
	}

	private static MD_Candidate_Reconcile_ATP newProcess() throws Exception
	{
		final MD_Candidate_Reconcile_ATP process = new MD_Candidate_Reconcile_ATP();
		process.init(ProcessInfo.builder().setCtx(Env.getCtx()).build());

		final Field isDryRunField = MD_Candidate_Reconcile_ATP.class.getDeclaredField("p_IsDryRun");
		isDryRunField.setAccessible(true);
		isDryRunField.set(process, false);

		return process;
	}
}
