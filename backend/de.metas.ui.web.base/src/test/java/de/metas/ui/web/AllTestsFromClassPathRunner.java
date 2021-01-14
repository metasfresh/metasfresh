package de.metas.ui.web;

import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.runner.RunWith;

import de.metas.i18n.IMsgBL;
import de.metas.util.Services;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Known issue: the runner initializes all classes while unit test mode is not yet enabled.
 *
 * This means that if you have constants where {@link Services} are used (for example {@link IMsgBL}),
 * those constants are initialized with the non-"plain" service implementations.
 *
 * Apparently this can't be solved with the <code>@BeforeSuite</code> annotation.
 */
@RunWith(ClasspathSuite.class)
public class AllTestsFromClassPathRunner
{
}
