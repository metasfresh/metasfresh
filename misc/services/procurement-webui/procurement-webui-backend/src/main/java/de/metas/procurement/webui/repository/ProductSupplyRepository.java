package de.metas.procurement.webui.repository;

import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import lombok.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/*
 * #%L
 * de.metas.procurement.webui
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
public interface ProductSupplyRepository extends AbstractRepository<ProductSupply>
{
	@Nullable
	ProductSupply findByProductAndBpartnerAndDay(final Product product, final BPartner bpartner, final java.sql.Date day);

	List<ProductSupply> findByBpartnerAndDay(BPartner bpartner, java.sql.Date day);

	@Query("select s from ProductSupply s"
			+ " where "
			+ " (s.deleted=false)"
			+ " and (s.bpartner = :bpartner or :bpartner is null)"
			+ " and (s.product = :product or :product is null)"
			+ " and (s.day >= :dayFrom)"
			+ " and (s.day <= :dayTo)")
	List<ProductSupply> findBySelector(
			@Param("bpartner") @Nullable BPartner bpartner,
			@Param("product") @Nullable Product product,
			@Param("dayFrom") Date dayFrom,
			@Param("dayTo") Date dayTo);

	@Query("select s from ProductSupply s"
			+ " where "
			+ " (s.deleted=false)"
			+ " and (s.bpartner = :bpartner)"
			+ " and (s.qty <> s.qtyUserEntered)")
	List<ProductSupply> findUnconfirmed(
			@Param("bpartner") @NonNull BPartner bpartner);

	@Query("select count(s) from ProductSupply s"
			+ " where "
			+ " (s.deleted=false)"
			+ " and (s.bpartner = :bpartner)"
			+ " and (s.qty <> s.qtyUserEntered)")
	long countUnconfirmed(
			@Param("bpartner") @NonNull BPartner bpartner);

}
