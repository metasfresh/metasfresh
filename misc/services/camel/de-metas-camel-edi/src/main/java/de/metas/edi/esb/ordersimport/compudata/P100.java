/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.ordersimport.compudata;

import java.io.Serializable;

import lombok.Data;

@Data
public class P100 implements Serializable
{
	private static final long serialVersionUID = -4766634448020424455L;

	private String record;
	private String partner;
	private String messageNo;
	private String positionNo;
	private String eanArtNo;
	private String buyerArtNo;
	private String supplierArtNo;
	private String orderQty;
	private String orderUnit;
	private String cUperTU;
	private String currency;
	private String buyerPrice;
	private String buyerPriceTypCode;
	private String buyerPriceTypQual;
	private String buyerPriceBase;
	private String buyerPriceUnit;
	private String detailPrice;
	private String detailPriceTypCode;
	private String detailPriceTypQual;
	private String detailPriceBase;
	private String detailPriceUnit;
	private String actionPrice;
	private String actionPriceTypQual;
	private String deliveryDate;
	private String taxRate;
	private String positionAmount;
	private String articleClass;
	private String customerRefNo;
	private String promoDealNo;
	private String bossWorld;
	private String customerOrderNo;
	private String customerPosNo;
	private String artDescription;
	private String priceCode;
	private String stmQty1;
	private String stmCode1;
	private String actionBeginDate;
	private String actionEndDate;
	private String filialIDEan;
	private String filialQty;
	private String filialQtyUnit;
	private String unitCode;
}
