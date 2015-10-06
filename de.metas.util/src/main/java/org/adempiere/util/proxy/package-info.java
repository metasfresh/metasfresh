/**
 * Contains classes around proxying and interception. This package is here because jboss-aop is not reliable and the two CDI implementations I tried out (wedld and OWB) did you work out of the box with adempiere:
 * <ul>
 * <li>weld: it was able to find some service implementations <i>sometimes</i> and I didn't find out the rules of that game</li>
 * <li>OWB: it scans and tries to treat basically everything a a bean (i guess that would also have been the behavior of weld, had it gotten that far) and this doesn'T work with most out our classes. E.g. your X_* classes need a running DB-connection to do anything</li>
 * </ul> 
 * So i think for both frameworks, we either need to have an export, or go a long way of refactoring and splitting the stuff into different projects/jars, so that we can then only activate CDI for the jars that have the proper classes for it.
 * 
 * @author ts
 *
 */
package org.adempiere.util.proxy;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2015 metas GmbH
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
