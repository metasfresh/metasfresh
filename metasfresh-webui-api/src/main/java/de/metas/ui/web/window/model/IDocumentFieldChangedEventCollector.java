package de.metas.ui.web.window.model;

import java.util.List;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IDocumentFieldChangedEventCollector
{
	Set<String> getFieldNames();

	boolean isEmpty();

	List<DocumentFieldChangedEvent> toEventsList();

	void collectValueChanged(DocumentField documentField, ReasonSupplier reason);

	void collectReadonlyChanged(DocumentField documentField, ReasonSupplier reason);

	void collectMandatoryChanged(DocumentField documentField, ReasonSupplier reason);

	void collectDisplayedChanged(DocumentField documentField, ReasonSupplier reason);

	void collectLookupValuesStaled(DocumentField documentField, ReasonSupplier reason);

	void collectFrom(IDocumentFieldChangedEventCollector fromCollector);

	void collectFrom(Document document, ReasonSupplier reason);

	@FunctionalInterface
	interface ReasonSupplier
	{
		/**
		 * @return actual reason string
		 */
		String get();
	}
}