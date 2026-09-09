package de.metas.material.dispo;

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

import de.metas.Profiles;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.reconcile.AtpKeySelectionDrainer;
import de.metas.material.dispo.reconcile.AtpReconciliationCommand;
import de.metas.material.dispo.reconcile.AtpTargetCalculator;
import de.metas.material.dispo.reconcile.UncoveredSourceDocumentService;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the spring wiring of the {@code de.metas.material.dispo.reconcile} package against the one context no other
 * test of that package can see: <b>an application context that does not have the material disposition engine</b>.
 * <p>
 * <b>The concrete failure this prevents.</b> The webapi ({@code WebRestApiApplication}) and the app server
 * ({@code ServerBoot}) both component-scan {@code de.metas}, but only the app server activates the
 * {@link Profiles#PROFILE_MaterialDispo} profile (it reads it from the {@code de.metas.spring.profiles.active}
 * sysconfigs; the webapi reads a different prefix). So a bean of the reconcile package that injects a
 * profile-guarded {@code dispo-service} collaborator - {@link CandidateChangeService} and the engine behind it -
 * without carrying that profile itself is instantiated in the webapi too, where its constructor cannot be
 * satisfied. Spring then aborts webapi startup: the container never comes up, so the entire WebUI is down, not
 * merely this one feature. That happened here once and no test saw it - the unit tests of the reconcile package
 * construct their services by hand, and the cucumber suite boots {@code ServerBoot}, i.e. precisely the context
 * that <i>does</i> have the profile.
 * <p>
 * {@link #webapiLikeContext_startsWithoutTheMaterialDispoEngine()} stands in for that missing coverage: a real
 * context, a real component scan of the package, and no {@code material-dispo} profile - the webapi's situation -
 * which must refresh. {@link #appServerLikeContext_registersTheReconciliationCommand()} is its necessary other
 * half: it proves the profile guard withholds the bean only where the engine is missing, and not on the app
 * server, where the operator process actually runs.
 * <p>
 * <b>This test deliberately lives outside {@code de.metas.material.dispo.reconcile}.</b> {@link ComponentScan} is
 * recursive and does not distinguish main from test classes, so a {@link Configuration} class of this test placed
 * in (or under) the scanned package would be picked up by the scan itself and would hand the context the very bean
 * whose absence is the whole point - which silently blinds the guard. That is not a hypothetical: it happened while
 * this test was being written, and the belt to the braces is
 * {@link #webapiLikeContext_startsWithoutTheMaterialDispoEngine()}'s explicit assertion that no
 * {@link CandidateChangeService} bean exists in that context.
 */
class AtpReconcileContextStartupTest
{
	@BeforeEach
	void beforeEach()
	{
		// SourceDocumentRepository resolves IQueryBL in a field initializer, so the Services registry must be up
		// before the component scan instantiates it
		AdempiereTestHelper.get().init();
	}

	/**
	 * The collaborators the reconcile package needs from OTHER modules. Both are unguarded {@code @Service}s in
	 * their own module ({@code cockpit} / {@code dispo-commons}), i.e. genuinely present in every metasfresh
	 * application - they are mocked here only to keep the component scan off the database, never to stand in for a
	 * bean that a real context would lack.
	 */
	@Configuration
	@ComponentScan("de.metas.material.dispo.reconcile")
	static class ReconcilePackageConfig
	{
		@Bean
		StockRepository stockRepository() {return Mockito.mock(StockRepository.class);}

		@Bean
		CandidateRepositoryRetrieval candidateRepositoryRetrieval() {return Mockito.mock(CandidateRepositoryRetrieval.class);}
	}

	/** Adds the one bean that exists only where the material disposition engine does. */
	@Configuration
	static class MaterialDispoEngineConfig
	{
		@Bean
		CandidateChangeService candidateChangeService() {return Mockito.mock(CandidateChangeService.class);}
	}

	@Test
	void webapiLikeContext_startsWithoutTheMaterialDispoEngine()
	{
		try (final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext())
		{
			// no active profile at all: strictly less than the webapi has, so whatever starts here starts there too
			context.register(ReconcilePackageConfig.class);
			context.refresh(); // must not throw - a throw here is a webapi that will not boot

			assertThat(context.getBeanNamesForType(CandidateChangeService.class))
					.as("precondition: this context must be engine-less, or it cannot stand in for the webapi")
					.isEmpty();

			assertThat(context.getBeanNamesForType(AtpReconciliationCommand.class))
					.as("the write path needs CandidateChangeService, so it must NOT be registered without the engine")
					.isEmpty();

			assertThat(context.getBeanNamesForType(AtpTargetCalculator.class))
					.as("the read-only divergence computation needs nothing from dispo-service, so it stays available")
					.isNotEmpty();

			assertThat(context.getBeanNamesForType(UncoveredSourceDocumentService.class))
					.as("the read-only uncovered-open-document report needs nothing from dispo-service either, so"
							+ " it must be resolvable in the same webapi-like context that the divergence report"
							+ " process runs in")
					.isNotEmpty();

			assertThat(context.getBeanNamesForType(AtpKeySelectionDrainer.class))
					.as("the key drain needs nothing from dispo-service either, so it must be resolvable in the"
							+ " webapi-like context the operator's process runs in")
					.isNotEmpty();
		}
	}

	@Test
	void appServerLikeContext_registersTheReconciliationCommand()
	{
		try (final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext())
		{
			context.getEnvironment().setActiveProfiles(Profiles.PROFILE_MaterialDispo);
			context.register(ReconcilePackageConfig.class, MaterialDispoEngineConfig.class);
			context.refresh();

			assertThat(context.getBeanNamesForType(AtpReconciliationCommand.class))
					.as("where the engine is present, the operator process must find its command bean")
					.isNotEmpty();
		}
	}
}
