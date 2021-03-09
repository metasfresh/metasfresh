package de.metas.procurement.webui.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.NonNull;

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

@Entity
@Table(name = "user_product" //
, uniqueConstraints = @UniqueConstraint(name = "user_product_uq", columnNames = { "user_id", "product_id" })  //
)
public class UserProduct extends AbstractEntity
{
	public static UserProduct build(final User user, final Product product)
	{
		final UserProduct userProduct = new UserProduct();
		userProduct.setUser(user);
		userProduct.setProduct(product);
		return userProduct;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@NonNull
	private User user;

	@ManyToOne
	@NonNull
	private Product product;

	public User getUser()
	{
		return user;
	}

	private void setUser(final User user)
	{
		this.user = user;
	}

	public Product getProduct()
	{
		return product;
	}

	private void setProduct(final Product product)
	{
		this.product = product;
	}
}
