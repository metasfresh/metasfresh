package de.metas.procurement.webui.repository;

import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Rfq;
import lombok.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import java.util.List;

/*
 * #%L
 * metasfresh-procurement-webui
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

@Repository
@Transactional
public interface RfqRepository extends AbstractRepository<Rfq>
{
	@Query("select rfq from Rfq rfq"
			+ " where "
			+ " rfq.deleted = false"
			+ " and rfq.closed = false"
			+ " and (rfq.bpartner = :bpartner or :bpartner is null)")
	List<Rfq> findActive(
			@Param("bpartner") @Nullable final BPartner bpartner);

	@Query("select rfq from Rfq rfq"
			+ " where "
			+ " rfq.deleted = false"
			+ " and rfq.bpartner = :bpartner"
			+ " and rfq.closed = false"
			+ " and rfq.confirmedByUser = false")
	List<Rfq> findUnconfirmed(
			@Param("bpartner") @NonNull BPartner bpartner);

	@Query("select count(rfq) from Rfq rfq"
			+ " where "
			+ " rfq.deleted = false"
			+ " and rfq.bpartner = :bpartner"
			+ " and rfq.closed = false"
			+ " and rfq.confirmedByUser = false")
	long countUnconfirmed(
			@Param("bpartner") @NonNull BPartner bpartner);
}
