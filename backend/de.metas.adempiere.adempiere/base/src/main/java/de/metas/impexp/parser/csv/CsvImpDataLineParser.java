<<<<<<<< HEAD:backend/de.metas.adempiere.adempiere/base/src/main/java/de/metas/impexp/parser/csv/CsvImpDataLineParser.java
package de.metas.impexp.parser.csv;

import de.metas.impexp.parser.ImpDataCell;

import java.util.List;

========
>>>>>>>> new_dawn_uat:backend/de.metas.adempiere.adempiere/base/src/main/java/org/adempiere/ad/ui/spi/TabCallout.java
/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
<<<<<<<< HEAD:backend/de.metas.adempiere.adempiere/base/src/main/java/de/metas/impexp/parser/csv/CsvImpDataLineParser.java
 * Copyright (C) 2019 metas GmbH
========
 * Copyright (C) 2022 metas GmbH
>>>>>>>> new_dawn_uat:backend/de.metas.adempiere.adempiere/base/src/main/java/org/adempiere/ad/ui/spi/TabCallout.java
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

<<<<<<<< HEAD:backend/de.metas.adempiere.adempiere/base/src/main/java/de/metas/impexp/parser/csv/CsvImpDataLineParser.java
interface CsvImpDataLineParser
{

	List<ImpDataCell> parseDataCells(String line);
========
package org.adempiere.ad.ui.spi;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Component
public @interface TabCallout
{
	/**
	 * model class
	 */
	Class<?> value();
>>>>>>>> new_dawn_uat:backend/de.metas.adempiere.adempiere/base/src/main/java/org/adempiere/ad/ui/spi/TabCallout.java
}
